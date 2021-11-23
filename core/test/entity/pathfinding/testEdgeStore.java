package entity.pathfinding;

import chunk.ChunkRange;
import com.google.inject.Guice;
import com.google.inject.Injector;
import common.Coordinates;
import common.GameStore;
import configuration.SoloConfig;
import entity.Entity;
import entity.EntityFactory;
import entity.pathfinding.edge.AbstractEdge;
import generation.ChunkBuilderFactory;
import org.junit.Test;

public class testEdgeStore {

  @Test
  public void testEdgeStore() throws Exception {
    Injector injector = Guice.createInjector(new SoloConfig());

    EdgeStore edgeStore = injector.getInstance(EdgeStore.class);

    EntityFactory entityFactory = injector.getInstance(EntityFactory.class);

    ChunkBuilderFactory chunkBuilderFactory = injector.getInstance(ChunkBuilderFactory.class);
    GameStore gameStore = injector.getInstance(GameStore.class);

    chunkBuilderFactory.create(new ChunkRange(new Coordinates(0, 0))).call();
    chunkBuilderFactory.create(new ChunkRange(new Coordinates(5, 0))).call();
    chunkBuilderFactory.create(new ChunkRange(new Coordinates(0, 5))).call();
    chunkBuilderFactory.create(new ChunkRange(new Coordinates(0, -1))).call();
    chunkBuilderFactory.create(new ChunkRange(new Coordinates(-1, 0))).call();
    chunkBuilderFactory.create(new ChunkRange(new Coordinates(-1, -1))).call();

    Entity entity = entityFactory.createEntity();
    entity.coordinates = new Coordinates(0.5f, 1);
    gameStore.addEntity(entity);

    System.out.println(entity.getBody());

    EdgeRegistration edgeRegistration = injector.getInstance(EdgeRegistration.class);
    edgeRegistration.templateEdgeRegistration();

    for (AbstractEdge edge : edgeStore.getEdgeList()) {
      //      System.out.println(edge.isAvailable(entity.getBody()));
      System.out.println(edge);
    }
  }

  @Test
  public void testEdgeStoreCount() throws Exception {
    Injector injector = Guice.createInjector(new SoloConfig());

    EdgeStore edgeStore = injector.getInstance(EdgeStore.class);

    EdgeRegistration edgeRegistration = injector.getInstance(EdgeRegistration.class);
    edgeRegistration.horizontalGreedyRegisterEdges();

    System.out.println(edgeStore.getEdgeList().size());
  }
}