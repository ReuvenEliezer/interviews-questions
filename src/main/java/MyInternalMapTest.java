import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MyInternalMapTest {

    @Test
    public void myInternalMapTest() {
        MyInternalMap<Integer, String> map = new MyInternalMap<>();
        assertNull(map.get(0));
        assertNull(map.get(null));
        assertNull(map.remove(0));
        assertNull(map.remove(null));
        assertNull(map.remove(1));
        assertEquals(0, map.size());

        assertNull(map.put(1, "1"));
        assertEquals("1", map.put(1, "2"));
        assertEquals(1, map.size());
        assertEquals("2", map.get(1));
        assertEquals(1, map.size());
        assertNull(map.put(2, "3"));
        assertEquals(2, map.size());

        assertEquals("2", map.remove(1));
        assertEquals(1, map.size());
        assertNull(map.remove(1));

        assertEquals("3", map.remove(2));
        assertEquals(0, map.size());
    }

    @Test
    public void originalMapTest() {
        Map<Integer, String> map = new HashMap<>();
        assertNull(map.get(0));
        assertNull(map.get(null));

        assertNull(map.remove(0));
        assertNull(map.remove(null));
        assertNull(map.remove(1));
        assertEquals(0, map.size());

        assertNull(map.put(1, "1"));
        assertEquals(1, map.size());
        assertEquals("1", map.put(1, "2"));
        assertEquals(1, map.size());
        assertEquals("2", map.get(1));
        assertEquals(1, map.size());
        assertNull(map.put(2, "3"));
        assertEquals(2, map.size());

        assertEquals("2", map.remove(1));
        assertEquals(1, map.size());
        assertNull(map.remove(1));

        assertEquals("3", map.remove(2));
        assertEquals(0, map.size());

    }

    @Test
    public void myInternalMapSameHashCodeTest() {
        MyInternalMap<Integer, String> map = new MyInternalMap<>();
        assertEquals(null, map.put(1, "1"));
        assertEquals("1", map.get(1));
        assertEquals(null, map.put(17, "2"));
        assertEquals("1", map.get(1));
        assertEquals("2", map.get(17));
        assertEquals("1", map.get(1));
    }

    @Test
    public void originalMapSameHashCodeTest() {
        Map<Integer, String> map = new HashMap<>();
        assertEquals(null, map.put(1, "1"));
        assertEquals("1", map.get(1));
        assertEquals(null, map.put(17, "2"));
        assertEquals("1", map.get(1));
        assertEquals("2", map.get(17));
        assertEquals("1", map.get(1));
    }


}
