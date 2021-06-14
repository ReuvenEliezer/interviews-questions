package tipalti;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TipaltiTest {

    @Test
    public void genericLinkedListWithString() {
        CustomLinkedList<String> customLinkedList = new CustomLinkedList<>();
        customLinkedList.add("first");
        customLinkedList.add("second");
        customLinkedList.dump();
    }

    @Test
    public void genericLinkedListWithObject() {
        CustomLinkedList<Name> customLinkedList = new CustomLinkedList<>();
        customLinkedList.add(new Name("first name-2", "last name-2"));
        customLinkedList.add(new Name("first name-1", "last name-1"));
        customLinkedList.dump();
    }

    class Name {
        String firstName;
        String lastName;

        public Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        public String toString() {
            return "Name{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    '}';
        }
    }

    class Person {
        Name name;
        int age;

        public Person(Name name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name=" + name +
                    ", age=" + age +
                    '}';
        }
    }

    public class SimplePerson {
        private String name;
        private int age;

        public SimplePerson(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "SimplePerson{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    @Test
    public void genericLinkedListWithDiffObjects() {
        /**
         * https://www.baeldung.com/java-reflection
         */
        CustomLinkedList<Object> customLinkedList = new CustomLinkedList<>();
        Name name = new Name("first name-2", "last name-2");
        customLinkedList.add(name);
        customLinkedList.add(new Person(name, 50));
        customLinkedList.dump();
        customLinkedList.dump1();
    }

    @Test
    public void givenObject_whenGetsFieldNamesAtRuntime_thenCorrect() throws ClassNotFoundException {
        Object person = new SimplePerson("SimplePerson", 20);
        Field[] fields = person.getClass().getDeclaredFields();
        String typeName = person.getClass().getTypeName();
        Class<?> goatClass = Class.forName(typeName);
        Field[] declaredFields = goatClass.getDeclaredFields();
        List<String> actualFieldNames1 = getFieldNames(declaredFields);
        List<String> actualFieldNames2 = getFieldNames(fields);

        StringBuilder result = new StringBuilder();

        result.append(this.getClass().getName());
        result.append(" Object {");
        result.append(System.lineSeparator());

        //print field names paired with their values
        for (Field field : fields) {
            result.append("  ");
            try {
                result.append(field.getName());
                result.append(": ");
                //requires access to private field:
                result.append(field.get(SimplePerson.class));
            } catch (IllegalAccessException ex) {
                System.out.println(ex);
            }
            result.append(System.lineSeparator());
        }
        result.append("}");


        Assert.assertTrue(actualFieldNames1.toString(), Arrays.asList("name", "age")
                .containsAll(actualFieldNames1));
    }

    private static List<String> getFieldNames(Field[] fields) {
        List<String> fieldNames = new ArrayList<>();
        for (Field field : fields)
            fieldNames.add(field.getName());
        return fieldNames;
    }
}
