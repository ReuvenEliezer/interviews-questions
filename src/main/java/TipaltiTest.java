import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TipaltiTest {

    @Test
    public void removeObjFromListTest() {
        List<Integer> integerList = Arrays.asList(1, 3, 5, 7).stream().collect(Collectors.toList());
        integerList.remove(1);
        integerList.remove(new Integer(7));
        System.out.println(integerList);

    }

    @Test
    public void findMinRelationLevelTest() {
        Person graceHopper = new Person(new Name("Grace", "Hopper"), new Address("New York"));
        Person alanTuring1 = new Person(new Name("Alan", "Turing"), new Address("Bletchley Park"));
        Person joanClarke1 = new Person(new Name("Joan", "Clarke"), new Address("Bletchley Park"));
        Person alanTuring2 = new Person(new Name("Alan", "Turing"), new Address("Cambridge"));
        Person joanClarke2 = new Person(new Name("Joan", "Clarke"), new Address("London"));

        init(Arrays.asList(graceHopper, alanTuring1, joanClarke1, alanTuring2, joanClarke2));
        Assert.assertEquals(-1, findMinRelationLevel(graceHopper, alanTuring1, 0));
        Assert.assertEquals(-1, findMinRelationLevel(alanTuring1, alanTuring1, 0));
        Assert.assertEquals(1, findMinRelationLevel(alanTuring1, alanTuring2, 0));
        Assert.assertEquals(2, findMinRelationLevel(alanTuring1, joanClarke2, 0));
        Assert.assertEquals(2, findMinRelationLevel(joanClarke2, alanTuring1, 0));
    }

    Map<Person, Set<Person>> personToPersonsMap = new HashMap<>();

    private void init(List<Person> personList) {
        personList.forEach(person -> {
            personList.forEach(innerPerson -> {
                if (!person.equals(innerPerson)) {
                    if (innerPerson.address.equals(person.address) || innerPerson.fullName.equals(person.fullName)) {
                        personToPersonsMap.computeIfAbsent(person, p -> new HashSet<>()).add(innerPerson);
                    }
                } else {
                    personToPersonsMap.computeIfAbsent(person, p -> new HashSet<>());
                }
            });
        });
    }

    public int findMinRelationLevel(Person personA, Person personB, int result) {
        if (personA.equals(personB))
            return -1;
        Set<Person> people = personToPersonsMap.get(personA);
        if (!people.isEmpty()) {
            Stream<Person> personStream = people.stream().filter(person -> person.equals(personB));
            if (personStream.count() == 1) {
                return result + 1;
            }
            //do recursive for each neighbors
            for (Person person : people) {
                return findMinRelationLevel(personA, person, result + 1);
            }
        }

        return -1;
    }

    private class Person {
        Name fullName;
        Address address;

        public Person(Name fullName, Address address) {
            this.fullName = fullName;
            this.address = address;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return Objects.equals(fullName, person.fullName) && Objects.equals(address, person.address);
        }

        @Override
        public int hashCode() {
            return Objects.hash(fullName, address);
        }
    }

    private class Name {
        String firstName;
        String lastName;

        public Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Name name = (Name) o;
            return Objects.equals(firstName, name.firstName) && Objects.equals(lastName, name.lastName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(firstName, lastName);
        }
    }

    private class Address {
        String street;
        String city;

        public Address(String city) {
            this.city = city;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Address address = (Address) o;
            return Objects.equals(street, address.street) && Objects.equals(city, address.city);
        }

        @Override
        public int hashCode() {
            return Objects.hash(street, city);
        }
    }


}

