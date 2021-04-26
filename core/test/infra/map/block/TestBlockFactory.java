package infra.map.block;

import com.google.inject.Guice;
import com.google.inject.Injector;
import configure.TestApp;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestBlockFactory {
    @Test
    public void checkSize() {
        Injector injector = Guice.createInjector(new TestApp());
        BlockFactory factory = injector.getInstance(BlockFactory.class);
        Block testBlock = factory.createBlock(15, 21);
        assertNotNull(testBlock);
        assertEquals(testBlock.size, 15);
        assertNotNull(testBlock);
        assertEquals(testBlock.coordinate.getX(), 15);
        assertEquals(testBlock.coordinate.getY(), 21);
    }
}