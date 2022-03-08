package networking.events.consumer.server.outgoing;

import app.user.UserID;
import chunk.ActiveChunkManager;
import com.google.inject.Inject;
import common.events.types.EventType;
import java.util.function.Consumer;
import networking.events.types.outgoing.RemoveEntityOutgoingEventType;
import networking.server.ServerNetworkHandle;

public class RemoveEntityOutgoingConsumerServer implements Consumer<EventType> {
  @Inject ActiveChunkManager activeChunkManager;
  @Inject ServerNetworkHandle serverNetworkHandle;

  @Override
  public void accept(EventType eventType) {
    RemoveEntityOutgoingEventType outgoing = (RemoveEntityOutgoingEventType) eventType;
    for (UserID userID : activeChunkManager.getChunkRangeUsers(outgoing.getChunkRange())) {
      serverNetworkHandle.send(userID, outgoing.toNetworkEvent());
    }
  }
}
