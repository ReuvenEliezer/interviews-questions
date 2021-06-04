import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class TipaltiTest {

    @Test
    public void findMinRelationLevelTest() {
        Person graceHopper = new Person(new Name("Grace", "Hopper"), new Address("New York"));
        Person alanTuring1 = new Person(new Name("Alan", "Turing"), new Address("Bletchley Park"));
        Person joanClarke1 = new Person(new Name("Joan", "Clarke"), new Address("Bletchley Park"));
        Person alanTuring2 = new Person(new Name("Alan", "Turing"), new Address("Cambridge"));
        Person joanClarke2 = new Person(new Name("Joan", "Clarke"), new Address("London"));

        Person anotherMen = new Person(new Name("Eliezer", "Reuven"), new Address("London"));
        Person me = new Person(new Name("Eliezer", "Reuven"), new Address("Petah-Tikva"));
        Person nadavPT = new Person(new Name("Nadav", "Rabinovich"), new Address("Petah-Tikva"));

        init(new Person[]{graceHopper, alanTuring1, joanClarke1, alanTuring2, joanClarke2, anotherMen, me, nadavPT});

        Assert.assertEquals(-1, findMinRelationLevel(graceHopper, alanTuring1));
        Assert.assertEquals(-1, findMinRelationLevel(alanTuring1, alanTuring1));
        Assert.assertEquals(1, findMinRelationLevel(alanTuring1, alanTuring2));
        Assert.assertEquals(2, findMinRelationLevel(alanTuring1, joanClarke2));
        Assert.assertEquals(2, findMinRelationLevel(joanClarke2, alanTuring1));
        Assert.assertEquals(3, findMinRelationLevel(anotherMen, alanTuring1));
        Assert.assertEquals(4, findMinRelationLevel(me, alanTuring1));
//        Assert.assertEquals(5, findMinRelationLevel(nadavPT, alanTuring1));


        Assert.assertEquals(-1, findMinRelationLevelByQueue(graceHopper, alanTuring1));
        Assert.assertEquals(-1, findMinRelationLevelByQueue(alanTuring1, alanTuring1));
        Assert.assertEquals(1, findMinRelationLevelByQueue(alanTuring1, alanTuring2));
        Assert.assertEquals(2, findMinRelationLevelByQueue(alanTuring1, joanClarke2));
        Assert.assertEquals(2, findMinRelationLevelByQueue(joanClarke2, alanTuring1));
        Assert.assertEquals(3, findMinRelationLevelByQueue(anotherMen, alanTuring1));
        Assert.assertEquals(4, findMinRelationLevelByQueue(me, alanTuring1));
        Assert.assertEquals(5, findMinRelationLevelByQueue(nadavPT, alanTuring1));
    }


    Map<Person, List<Person>> personToOneLevelPersonsMap = new HashMap<>();

    private void init(Person[] personList) {
        List<Person> people = Arrays.asList(personList);
        people.forEach(person -> {
            List<Person> peopleListLevelOne = people.parallelStream()
                    .filter(innerPerson -> innerPerson != person && (innerPerson.fullName.equals(person.fullName) || innerPerson.address.equals(person.address)))
                    .collect(Collectors.toList());
            personToOneLevelPersonsMap.put(person, peopleListLevelOne);
        });
    }

    private int findMinRelationLevel(Person personA, Person personB) {
        System.out.println(System.lineSeparator() + "findMinRelationLevel between : " + personA.toString() + " and " + personB.toString());
        if (personA == personB)
            return -1;
        if (!personToOneLevelPersonsMap.containsKey(personA) || !personToOneLevelPersonsMap.containsKey(personB)) {
            System.out.println("person not exist in map");
            return -1;
        }
        List<Person> relatedPeople = personToOneLevelPersonsMap.get(personA);
        if (relatedPeople.isEmpty())
            return -1;
        return findMinRelationLevel(relatedPeople, personB, 0);
    }

    private int findMinRelationLevel(List<Person> relatedPeople, Person personB, int level) {
        Person personFind = relatedPeople.parallelStream()
                .filter(person -> person.equals(personB))
                .findAny()
                .orElse(null);
        if (personFind != null) {
            return ++level;
        }
        //do recursive for each neighbors
        for (Person person : relatedPeople) {
            return findMinRelationLevel(personToOneLevelPersonsMap.get(personB), person, ++level);
        }
        return -1;
    }

    private int findMinRelationLevelByQueue(Person personA, Person personB) {
        System.out.println(System.lineSeparator() + "findMinRelationLevel between : " + personA.toString() + " and " + personB.toString());
        if (personA == personB)
            return -1;
        if (!personToOneLevelPersonsMap.containsKey(personA) || !personToOneLevelPersonsMap.containsKey(personB)) {
            System.out.println("person not exist in map");
            return -1;
        }

        Queue<Person> queue = new ArrayDeque<>();
        queue.add(personA);
        List<Person> relatedPeople;
        Set<Person> visited = new HashSet<>();
        List<List<Person>> personPathList = new ArrayList<>();
        LinkedList<Person> people = new LinkedList<>();
        people.add(personA);
        personPathList.add(people);
        while (!queue.isEmpty()) {
            Person person = queue.poll();
            System.out.println("search for : " + person.toString());
            relatedPeople = personToOneLevelPersonsMap.get(person);
            if (relatedPeople.isEmpty())
                return -1;
            relatedPeople = relatedPeople.stream().filter(p -> !visited.contains(p)).collect(Collectors.toList());
            System.out.println("relatedPeople size: " + relatedPeople.size() + " " + relatedPeople);
            Person personFind = relatedPeople.parallelStream()
                    .filter(relatedPerson -> relatedPerson.equals(personB))
                    .findAny()
                    .orElse(null);

            if (personFind != null) {
                int maxSize = 0;
                for (List<Person> linkedLists : personPathList) {
                    System.out.println(linkedLists.toString());
                    if (linkedLists.size() > maxSize) {
                        maxSize = linkedLists.size();
                    }
                }
                return maxSize;
            }


            //duplicate path by last person
            for (int i = 0; i < relatedPeople.size(); i++) {
                List<Person> list = null;
                for (List<Person> personPath : personPathList) {
                    if (personPath.get(personPath.size() - 1).equals(person)) {
                        list = new ArrayList<>(personPath);
                        list.add(relatedPeople.get(i));
                        break;
                    }
                }
                if (list != null)
                    personPathList.add(list);
            }
            queue.addAll(relatedPeople);
            visited.add(person);
        }
        return -1;
    }

//    class CustomArrayDeque<E> extends ArrayDeque<E> {
//        int level;
//
//        @Override
//        public boolean addAll(Collection e) {
//            level++;
//            System.out.println("level: " + level);
//            return super.addAll(e);
//        }
//    }

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

        @Override
        public String toString() {
            return "Person{" +
                    "fullName=" + fullName +
                    ", address=" + address +
                    '}';
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

        @Override
        public String toString() {
            return "Name{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    '}';
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

        @Override
        public String toString() {
            return "Address{" +
                    "street='" + street + '\'' +
                    ", city='" + city + '\'' +
                    '}';
        }
    }

}
