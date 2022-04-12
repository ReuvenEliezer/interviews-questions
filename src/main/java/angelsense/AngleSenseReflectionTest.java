package angelsense;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

public class AngleSenseReflectionTest {

    private static final String TAB = "    "; // 4 spaces

    @Test
    public void angleSenseReflectionTest() throws Exception {
        List<Name> friends = new ArrayList<>();
        friends.add(new Name("A", "AA"));
        friends.add(new Name("B", "BB"));
        Person person = new Person(35, new Name("Doron", "Somer"), new String[]{"address1", "address2"}, friends);
        printObject(person);
    }

    private static void printObject(Object o) throws Exception {
        Field[] fields = o.getClass().getDeclaredFields();
        String className = o.getClass().getName();
        StringBuilder sb = new StringBuilder(className)
                .append(System.lineSeparator())
                .append("-----------")
                .append(tabs(1, true));
        for (Field field : fields) {
            field.setAccessible(true); // You might want to set modifier to public first.
            Object value = field.get(o);
            sb.append(field.getName());
            sb.append("=");
            if (value instanceof Object[]) {
                writeArr(sb, (Object[]) value);
            } else if (value instanceof Collection) {
                writeCollection(sb, (Collection<Object>) value);
            } else {
                sb.append(value);
            }
            sb.append(tabs(1, true));
        }
        System.out.println(sb);
    }

    private static void writeCollection(StringBuilder sb, Collection<Object> value) {
        sb.append("[");
        Iterator<Object> iterator = value.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext())
                sb.append(", ");
        }
        sb.append("]");
    }

    private static void writeArr(StringBuilder sb, Object[] value) {
        sb.append("{");
        for (int i = 0; i < value.length; i++) {
            Object o2 = value[i];
            sb.append(o2);
            if (i < value.length - 1)
                sb.append(", ");
        }
        sb.append("}");
    }
    public static StringBuilder tabs(int count, boolean prefixWithLineSeparator) {
        StringBuilder result = new StringBuilder();
        if (prefixWithLineSeparator)
            result.append(System.lineSeparator());
        IntStream.range(0, count).mapToObj(i -> TAB).forEach(result::append);
        return result;
    }

}
