import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class MyInternalMapTest {

    @ParameterizedTest()
    @MethodSource({"mapArgumentsProvider"})
    public void mapTest(Map<Integer, String> map) {
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

    @ParameterizedTest()
    @MethodSource({"mapArgumentsProvider"})
    public void mapSameHashCodeTest(Map<Integer, String> map) {
        assertNull(map.put(1, "1"));
        assertEquals("1", map.get(1));
        assertNull(map.put(17, "2"));
        assertEquals("1", map.get(1));
        assertEquals("2", map.get(17));
        assertEquals("1", map.get(1));
        assertTrue(map.containsValue("1"));
        assertTrue(map.containsValue("2"));
        assertFalse(map.containsValue("3"));
    }

    private static Stream<Arguments> mapArgumentsProvider() {
        return Stream.of(
                Arguments.of(new MyInternalMap<>()),
                Arguments.of(new HashMap<>())
        );
    }

}
