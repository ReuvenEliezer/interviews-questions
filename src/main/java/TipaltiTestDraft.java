//import bfs.algorithm.DfsTest;
//import org.junit.Assert;
//import org.junit.Test;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class TipaltiTest2 {
//
//    @Test
//    public void findMinRelationLevelTest() {
//        Person graceHopper = new Person(new Name("Grace", "Hopper"), new Address("New York"));
//        Person alanTuring1 = new Person(new Name("Alan", "Turing"), new Address("Bletchley Park"));
//        Person joanClarke1 = new Person(new Name("Joan", "Clarke"), new Address("Bletchley Park"));
//        Person alanTuring2 = new Person(new Name("Alan", "Turing"), new Address("Cambridge"));
//        Person joanClarke2 = new Person(new Name("Joan", "Clarke"), new Address("London"));
//
//        Person JoanA = new Person(new Name("oan", "a"), new Address("London"));
//        Person JoanA1 = new Person(new Name("oan", "a"), new Address("7"));
//        Person JoanA11 = new Person(new Name("oan", "a1"), new Address("7"));
//
////        init(new Person[]{ alanTuring1, joanClarke1, alanTuring2, joanClarke2,JoanA,JoanA1});
//        init(new Person[]{graceHopper, alanTuring1, joanClarke1, alanTuring2, joanClarke2, JoanA, JoanA1, JoanA11});
//
//        Assert.assertEquals(-1, findMinRelationLevel(graceHopper, alanTuring1));
//        Assert.assertEquals(-1, findMinRelationLevel(alanTuring1, alanTuring1));
//        Assert.assertEquals(1, findMinRelationLevel(alanTuring1, alanTuring2));
//        Assert.assertEquals(2, findMinRelationLevel(alanTuring1, joanClarke2));
//        Assert.assertEquals(2, findMinRelationLevel(joanClarke2, alanTuring1));
//        Assert.assertEquals(3, findMinRelationLevel(JoanA, alanTuring1));
//        Assert.assertEquals(4, findMinRelationLevel(JoanA1, alanTuring1));
//        Assert.assertEquals(5, findMinRelationLevel(JoanA11, alanTuring1));
//
//    }
//
//    private int findMinRelationLevel(Person personA, Person personB) {
//        if (personA == personB)
//            return -1;
//        return 0;
//    }
//
//
//    Map<Person, List<Person>> personToOneLevelPersonsMap = new HashMap<>();
//
//    private void init(Person[] personList) {
//        List<Person> people = Arrays.asList(personList);
//        people.forEach(person -> {
//            List<Person> peopleListLevelOne = people.parallelStream()
//                    .filter(innerPerson -> innerPerson != person && (innerPerson.equals(person)))
//                    .collect(Collectors.toList());
//            personToOneLevelPersonsMap.put(person, peopleListLevelOne);
//        });
//
//        for (Map.Entry<Person, List<Person>> entry : personToOneLevelPersonsMap.entrySet()) {
//            Person key = entry.getKey();
//            Vertex A = new Vertex(key);
//            for (Person person:entry.getValue()) {
//                A.adjacencies.add(new Edge(person, 8));
//            }
//
//        }
//    }
//
//    class Vertex implements Comparable<Vertex> {
//        public final Person person;
//        public List<Edge> adjacencies=new ArrayList<>();
//        public double minDistance = Double.POSITIVE_INFINITY;
//
//        public Vertex previous;
//
//        public Vertex(Person Person) {
//            person = Person;
//        }
//
//        public int compareTo(Vertex other) {
//            return Double.compare(minDistance, other.minDistance);
//        }
//
//    }
//    class Edge {
//        public final Vertex target;
//
//        public final double weight;
//        public Edge(Vertex argTarget, double argWeight) {
//            target = argTarget;
//            weight = argWeight;
//        }
//
//    }
//
//    private class Person {
//        Name fullName;
//        Address address;
//
//        public Person(Name fullName, Address address) {
//            this.fullName = fullName;
//            this.address = address;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            Person person = (Person) o;
//            return Objects.equals(fullName, person.fullName) && Objects.equals(address, person.address);
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(fullName, address);
//        }
//
//        @Override
//        public String toString() {
//            return "Person{" +
//                    "fullName=" + fullName +
//                    ", address=" + address +
//                    '}';
//        }
//    }
//
//    private class Name {
//        String firstName;
//        String lastName;
//
//        public Name(String firstName, String lastName) {
//            this.firstName = firstName;
//            this.lastName = lastName;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            Name name = (Name) o;
//            return Objects.equals(firstName, name.firstName) && Objects.equals(lastName, name.lastName);
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(firstName, lastName);
//        }
//
//        @Override
//        public String toString() {
//            return "Name{" +
//                    "firstName='" + firstName + '\'' +
//                    ", lastName='" + lastName + '\'' +
//                    '}';
//        }
//    }
//
//    private class Address {
//        String street;
//        String city;
//
//        public Address(String city) {
//            this.city = city;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            Address address = (Address) o;
//            return Objects.equals(street, address.street) && Objects.equals(city, address.city);
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(street, city);
//        }
//
//        @Override
//        public String toString() {
//            return "Address{" +
//                    "street='" + street + '\'' +
//                    ", city='" + city + '\'' +
//                    '}';
//        }
//    }
//
//}
