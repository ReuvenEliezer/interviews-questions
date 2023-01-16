package angelsense;

import org.apache.commons.lang3.ClassUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.IntStream;

public class AngleSenseReflectionTest {

    private static final String TAB = "    "; // 4 spaces

    @Test
    public void angleSenseReflectionTest() throws Exception {
        List<Name> friends = new ArrayList<>();
        friends.add(new Name("A", "AA", List.of("a", "b")));
        friends.add(new Name("B", "BB", List.of("a", "b")));
        Person person = new Person(35, new Name("Doron", "Somer", List.of("a", "b")), new String[]{"address1", "address2"}, friends);
        System.out.println(printObject(person));
    }

    @Test
    public void nodeTest() throws Exception {
        Node node = new Node(1, List.of("a", "b"));
        node.next = new Node(2, List.of("c", "d"));
        node.next.next = new Node(3);
        node.next.next.next = new Node(4);
        node.next.next.next.next = new Node(5);
        System.out.println(printObject(node));
        System.out.println(node);

    }

    /**
     * https://stackoverflow.com/questions/3905382/recursively-print-an-objects-details
     */
    private static final List LEAVES = Arrays.asList(
            Boolean.class, Character.class, Byte.class, Short.class,
            Integer.class, Long.class, Float.class, Double.class, Void.class,
            String.class);

    private static String printObject(Object o) throws Exception {
        if (o == null)
            return "";
        if (LEAVES.contains(o.getClass()))
            return o.toString();

        Field[] fields = o.getClass().getDeclaredFields();
        String className = o.getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className)
                .append(System.lineSeparator())
//                .append("-----------")
//                .append(tabs(1, true)
                ;
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers()))
                continue;
            field.setAccessible(true); // You might want to set modifier to public first.
            Object value = field.get(o);
            sb.append(field.getName()).append("=");
            if (value instanceof Object[]) {
                writeArr(sb, (Object[]) value);
            } else if (value instanceof Collection) {
                writeCollection(sb, (Collection<Object>) value);
            } else if (value != null && ClassUtils.isPrimitiveOrWrapper(value.getClass())) {
                sb.append(printObject(value))
                        .append(" ");
//                printObject(value);
            } else {
                sb.append(value);
            }
            sb.append(System.lineSeparator());

        }
        return sb.toString();
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
        IntStream.range(0, count).forEach(i -> result.append(TAB));
        return result;
    }


    @Test
    public void test1() throws Exception {
        A a = new A();
        System.out.println(printObject(a));
        System.out.println(toStringRecursive(a));
    }

    class A {
        int i = 5;
        B obj = new B();
        String str = "hello";

        public String toString() {
            return String.format("A: [i: %d, obj: %s, str: %s]", i, obj, str);
        }
    }

    class B {
        int j = 17;

        public String toString() {
            return String.format("B: [j: %d]", j);
        }
    }

    public static String toStringRecursive(Object o) throws Exception {

        if (o == null)
            return "null";

        if (LEAVES.contains(o.getClass()))
            return o.toString();

        StringBuilder sb = new StringBuilder();
        sb.append(o.getClass().getSimpleName()).append(": [");
        for (Field f : o.getClass().getDeclaredFields()) {
            if (Modifier.isStatic(f.getModifiers()))
                continue;
            f.setAccessible(true);
            sb.append(f.getName()).append(": ");
            sb.append(toStringRecursive(f.get(o))).append(" ");
        }
        sb.append("]");
        return sb.toString();
    }


}
