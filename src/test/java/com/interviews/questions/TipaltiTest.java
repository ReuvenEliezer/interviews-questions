package com.interviews.questions;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class TipaltiTest {

    @Test
    public void findMinRelationLevelTest() {
        Person graceHopper = new Person(new Name("Grace", "Hopper"), new Address("New York"));
        Person alanTuring1 = new Person(new Name("Alan", "Turing"), new Address("Bletchley Park"));
        Person joanClarke1 = new Person(new Name("Joan", "Clarke"), new Address("Bletchley Park"));
        Person alanTuring2 = new Person(new Name("Alan", "Turing"), new Address("Cambridge"));
        Person joanClarke2 = new Person(new Name("Joan", "Clarke"), new Address("London"));

        Person anotherMen = new Person(new Name("Eliezer", "Reuven"), new Address("London"));
        Person mePt = new Person(new Name("Eliezer", "Reuven"), new Address("Petah-Tikva"));
        Person morPt = new Person(new Name("Moriya", "Reuven"), new Address("Petah-Tikva"));
        Person nadavCambridge = new Person(new Name("Nadav", "Rabinovich"), new Address("Cambridge"));

        init(new Person[]{graceHopper, alanTuring1, joanClarke1, alanTuring2, joanClarke2, anotherMen, mePt, morPt, nadavCambridge});

        assertEquals(-1, findMinRelationLevel(graceHopper, alanTuring1));
        assertEquals(-1, findMinRelationLevel(alanTuring1, alanTuring1));
        assertEquals(1, findMinRelationLevel(alanTuring1, alanTuring2));
        assertEquals(2, findMinRelationLevel(alanTuring1, joanClarke2));
        assertEquals(2, findMinRelationLevel(joanClarke2, alanTuring1));
        assertEquals(3, findMinRelationLevel(anotherMen, alanTuring1));
        assertEquals(4, findMinRelationLevel(mePt, alanTuring1));
        assertEquals(5, findMinRelationLevel(morPt, alanTuring1));
        assertEquals(1, findMinRelationLevel(nadavCambridge, alanTuring2));
        assertEquals(2, findMinRelationLevel(nadavCambridge, alanTuring1));

        assertEquals(-1, findMinRelationLevelByQueue(graceHopper, alanTuring1));
        assertEquals(-1, findMinRelationLevelByQueue(alanTuring1, alanTuring1));
        assertEquals(1, findMinRelationLevelByQueue(alanTuring1, alanTuring2));
        assertEquals(2, findMinRelationLevelByQueue(alanTuring1, joanClarke2));
        assertEquals(2, findMinRelationLevelByQueue(joanClarke2, alanTuring1));
        assertEquals(3, findMinRelationLevelByQueue(anotherMen, alanTuring1));
        assertEquals(4, findMinRelationLevelByQueue(mePt, alanTuring1));
        assertEquals(5, findMinRelationLevelByQueue(morPt, alanTuring1));
        assertEquals(1, findMinRelationLevelByQueue(nadavCambridge, alanTuring2));
        assertEquals(2, findMinRelationLevelByQueue(nadavCambridge, alanTuring1));
    }


    Map<Person, List<Person>> personToOneLevelPersonsMap = new HashMap<>();

    private void init(Person[] personList) {
        List<Person> people = Arrays.asList(personList);
        people.forEach(person -> {
            List<Person> peopleListLevelOne = people.parallelStream()
                    .filter(innerPerson -> innerPerson != person && (innerPerson.fullName.equals(person.fullName) || innerPerson.address.equals(person.address)))
                    .toList();
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

        return findMinRelationLevel(relatedPeople, personB, new HashSet<>(), 0);
    }

    private int findMinRelationLevel(List<Person> relatedPeople, Person personB, Set<Person> visited, int level) {
        if (visited.contains(personB)) return ++level;
        visited.add(personB);
        Person personFind = relatedPeople.parallelStream()
                .filter(person -> person.equals(personB))
                .findAny()
                .orElse(null);

        if (personFind != null) {
            return ++level;
        }
        //do recursive for each neighbors
        for (Person person : relatedPeople) {
            return findMinRelationLevel(personToOneLevelPersonsMap.get(personB), person, visited, ++level);
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
        List<Person> people = new ArrayList<>();
        people.add(personA);
        personPathList.add(people);
        while (!queue.isEmpty()) {
            Person currentPerson = queue.poll();
            System.out.println("search for : " + currentPerson.toString());
            relatedPeople = personToOneLevelPersonsMap.get(currentPerson);
            if (relatedPeople.isEmpty())
                return -1;
            relatedPeople = relatedPeople.stream().filter(p -> !visited.contains(p)).toList();
            System.out.printf("relatedPeople size: %s, %s%n", relatedPeople.size(), relatedPeople);
            Person personFind = relatedPeople.parallelStream()
                    .filter(relatedPerson -> relatedPerson.equals(personB))
                    .findAny()
                    .orElse(null);

            if (personFind != null) {
                return personPathList.stream().max(Comparator.comparingInt(List::size)).get().size();
            }

            duplicatePathByLastPerson(relatedPeople, personPathList, currentPerson);
            queue.addAll(relatedPeople);
            visited.add(currentPerson);
        }
        return -1;
    }

    private void duplicatePathByLastPerson(List<Person> relatedPeople, List<List<Person>> personPathList, Person currentPerson) {
        List<List<Person>> listToAdd = new ArrayList<>();
        for (Person relatedPerson : relatedPeople) {
            for (List<Person> personPath : personPathList) {
                if (personPath.get(personPath.size() - 1).equals(currentPerson)) {
                    List<Person> listToDuplicate = new ArrayList<>(personPath);
                    listToDuplicate.add(relatedPerson);
                    listToAdd.add(listToDuplicate);
                    System.out.printf("create new path for relatedPerson %s on list: %s %n", relatedPerson, listToDuplicate);
                    break;
                }
            }
        }
        personPathList.addAll(listToAdd);
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

    record Person(Name fullName, Address address) {
    }

    record Name(String firstName, String lastName) {
    }

    record Address(String city,String street) {
        Address(String city) {
            this(city, null);
        }
    }

}
