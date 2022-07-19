package core.entity.pathfinding;

import com.google.inject.Guice;
import com.google.inject.Injector;
import core.chunk.ChunkRange;
import core.common.GameStore;
import core.configuration.StandAloneConfig;
import core.entity.Entity;
import core.entity.EntityFactory;
import core.entity.attributes.msc.Coordinates;
import core.entity.pathfinding.edge.AbstractEdge;
import core.generation.ChunkBuilderFactory;
import org.junit.Test;

public class testEdgeStore {

  @Test
  public void testEdgeStore() throws Exception {
    Injector injector = Guice.createInjector(new StandAloneConfig());

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

    Entity entity = entityFactory.createEntity(new Coordinates(0.5f, 1));
    gameStore.addEntity(entity);

    EdgeRegistration edgeRegistration = injector.getInstance(EdgeRegistration.class);
    edgeRegistration.templateEdgeRegistration();

    for (AbstractEdge edge : edgeStore.getEdgeList()) {
      //      System.out.println(edge.isAvailable(entity.getBody()));
      System.out.println(edge);
    }
  }

  @Test
  public void testEdgeStoreCount() throws Exception {
    Injector injector = Guice.createInjector(new StandAloneConfig());

    EdgeStore edgeStore = injector.getInstance(EdgeStore.class);

    EdgeRegistration edgeRegistration = injector.getInstance(EdgeRegistration.class);
    edgeRegistration.horizontalGreedyRegisterEdges();

    System.out.println(edgeStore.getEdgeList().size());
  }
}
