package networking.translation;

import chunk.Chunk;
import chunk.ChunkFactory;
import chunk.ChunkRange;
import com.google.inject.Guice;
import com.google.inject.Injector;
import common.Coordinates;
import common.exceptions.SerializationDataMissing;
import configuration.ClientConfig;
import entity.Entity;
import entity.EntityFactory;
import org.junit.Test;

public class testChunkSerialization {

    Injector injector = Guice.createInjector(new ClientConfig());
    NetworkDataDeserializer networkDataSerialization;

    @Test
    public void testChunkSerialization() throws SerializationDataMissing {
        networkDataSerialization = injector.getInstance(NetworkDataDeserializer.class);
        ChunkFactory chunkFactory = injector.getInstance(ChunkFactory.class);
        Chunk chunk1 = chunkFactory.create(new ChunkRange(new Coordinates(0, 0)));
        EntityFactory entityFactory = injector.getInstance(EntityFactory.class);

        for (int i = 0; i < 10; i++) {
            Entity entity = entityFactory.createEntity();
            chunk1.addEntity(entity);
        }

        Chunk chunk2 = networkDataSerialization.createChunk(chunk1.toNetworkData());

        assert chunk1.equals(chunk2);
    }

    @Test
    public void testChunkSerializationChunkRange() {

        networkDataSerialization = injector.getInstance(NetworkDataDeserializer.class);

        ChunkRange chunkRange = new ChunkRange(new Coordinates(0, 0));
        assert chunkRange.equals(networkDataSerialization.createChunkRange(chunkRange.toNetworkData()));

        ChunkRange chunkRange2 = new ChunkRange(new Coordinates(-1, 0));
        assert chunkRange2.equals(networkDataSerialization.createChunkRange(chunkRange2.toNetworkData()));
    }
}
