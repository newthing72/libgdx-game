package infra.app;

import com.google.inject.Inject;
import infra.chunk.ChunkFactory;
import infra.chunk.ChunkRange;
import infra.client.BaseCamera;
import infra.common.GameStore;
import infra.common.networkobject.Coordinates;
import infra.entity.Entity;
import infra.generation.ChunkGenerationManager;

import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class Game {

  @Inject GameStore gameStore;

  @Inject GameScreen gameScreen;

  @Inject BaseCamera camera;

  @Inject UpdateLoop updateLoop;

  @Inject
  ChunkFactory chunkFactory;

  Timer timer;

  @Inject
  public Game(GameStore gameStore,ChunkFactory chunkFactory, ChunkGenerationManager chunkGenerationManager) throws Exception {
    gameStore.addChunk(chunkFactory.create(new ChunkRange(new Coordinates(0,0))));
    chunkGenerationManager.generate(new ChunkRange(new Coordinates(0,0))).call();
  }

  void start() {
    //    updateLoop = new UpdateLoop();
    timer = new Timer(true);
    timer.scheduleAtFixedRate(updateLoop, 0, 16);
  }

  void stop() {
    // TODO stop updateLoop
    timer.cancel();
  }

  List<Entity> getEntityListInRange(int x1, int y1, int x2, int y2) {
    return this.gameStore.getEntityListInRange(x1, y1, x2, y2);
  }
}