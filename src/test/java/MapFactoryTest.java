

import org.example.MapFactory;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import static org.junit.jupiter.api.Assertions.*;

class MapFactoryTest {
    @Test
    void testCrearHashMap() {
        Map<String, Object> map = MapFactory.crearMap(1);
        assertTrue(map instanceof HashMap);
    }

    @Test
    void testCrearTreeMap() {
        Map<String, Object> map = MapFactory.crearMap(2);
        assertTrue(map instanceof TreeMap);
    }

    @Test
    void testCrearLinkedHashMap() {
        Map<String, Object> map = MapFactory.crearMap(3);
        assertTrue(map instanceof LinkedHashMap);
    }

    @Test
    void testDefaultMap() {
        Map<String, Object> map = MapFactory.crearMap(99);
        assertTrue(map instanceof HashMap);
    }
}