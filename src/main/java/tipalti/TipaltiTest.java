package tipalti;

import org.junit.Test;

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

    @Test
    public void genericLinkedListWithDiffObjects() {
        CustomLinkedList<Object> customLinkedList = new CustomLinkedList<>();
        Name name = new Name("first name-2", "last name-2");
        customLinkedList.add(name);
        customLinkedList.add(new Person(name, 50));
        customLinkedList.dump();
        customLinkedList.dump1();
    }
}
