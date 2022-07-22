package core.app.update;

import com.google.inject.Inject;
import core.app.screen.BaseCamera;
import core.chunk.Chunk;
import core.chunk.ChunkRange;
import core.common.Clock;
import core.common.GameStore;
import core.common.events.EventService;
import core.entity.ActiveEntityManager;
import core.networking.client.ClientNetworkHandle;
import core.networking.events.EventTypeFactory;
import core.networking.events.types.outgoing.SubscriptionOutgoingEventType;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientUpdateTask extends UpdateTask {

  static final Logger LOGGER = LogManager.getLogger();
  @Inject public Clock clock;
  @Inject public GameStore gameStore;
  public ExecutorService executor;
  @Inject EventService eventService;
  @Inject BaseCamera baseCamera;
  @Inject ActiveEntityManager activeEntityManager;
  @Inject ClientNetworkHandle clientNetworkHandle;
  @Inject EventTypeFactory eventTypeFactory;

  public ClientUpdateTask() {
    executor = Executors.newCachedThreadPool();
  }

  @Override
  public void run() {
    /*
    -create requested chunks and
    -delete chunks no longer needs
    -send subscription requests
     */
    this.clock.tick();

    Set<ChunkRange> requiredChunkRanges = new HashSet<>();
    // get the set of onscreen chunks
    requiredChunkRanges.addAll(baseCamera.getChunkRangeOnScreen());
    // get the set of active entities. get their chunks
    requiredChunkRanges.addAll(activeEntityManager.getActiveChunkRanges());
    // get chunks that exist

    Set<ChunkRange> existingChunkRanges = gameStore.getChunkRangeList();

    // to delete = existingChunkRanges - requiredChunkRanges
    Set<ChunkRange> toDeleteSet = new HashSet<>(existingChunkRanges);
    toDeleteSet.removeAll(requiredChunkRanges);

    // to request = requiredChunkRanges - existingChunkRanges
    Set<ChunkRange> toRequestSet = new HashSet<>(requiredChunkRanges);
    toRequestSet.removeAll(existingChunkRanges);

    // delete all the not needed.
    for (ChunkRange toDelete : toDeleteSet) {
      gameStore.removeChunk(toDelete);
    }

    // request all needed
    for (ChunkRange toRequest : toRequestSet) {
      clientNetworkHandle.requestChunkAsync(toRequest);
    }

    // update the subscriptions on the server
    if (toRequestSet.size() > 0 || toDeleteSet.size() > 0) {
      // currently, not subscribing to new requests because the requestChunkAsync will sub on the
      // server
      // this could cause a problem if it errors.
      SubscriptionOutgoingEventType subscriptionOutgoing =
          eventTypeFactory.createSubscriptionOutgoingEvent(new LinkedList<>(requiredChunkRanges));
      this.clientNetworkHandle.send(subscriptionOutgoing.toNetworkEvent());
    }

    try {
      Set<Chunk> listChunk = this.gameStore.getChunkOnClock(this.clock.getCurrentTick());
      LOGGER.debug("Updating " + listChunk.size() + " chunks.");
      executor.invokeAll(listChunk);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    this.eventService.firePostUpdateEvents();
  }
}
