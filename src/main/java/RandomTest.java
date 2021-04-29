import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class RandomTest {

    @Test
    public void Test() {
        LinkedList<String> linkedList = new LinkedList<>();
        if (!linkedList.isEmpty()) {
            String last = linkedList.getLast();
            System.out.println("last is: " + last);
        }
    }

    @Test
    public void _Test() {
        int size = 100000;
        int iteration = 10;

        HashSet<Integer> selectedSet = new HashSet<>();

        Random random = new Random();

        for (int i = 0; i < iteration; i++) {
            int randomValue = random.nextInt(size);
            System.out.println("randomValue: " + randomValue);
            if (!selectedSet.contains(randomValue))
                selectedSet.add(randomValue);
            else selectedSet.add(randomValue + 1);
            System.out.println("randomValue+1: " + (randomValue + 1));
        }
        selectedSet.stream().forEach(System.out::println);
    }

    @Test
    public void RandomOnLinkedList_Test() {
        int linkedListSize = 1000000;

        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < linkedListSize; i++) {
            linkedList.add(i);
        }
        Random random = new Random();

        for (int i = 0; i < linkedListSize; i++) {
            int r = random.nextInt(linkedList.size());
//            System.out.println("linkedList.size():" + linkedList.size());
//            System.out.println(linkedList.get(r));
            linkedList.remove(r);
        }
    }

    @Test
    public void RandomOnList_Test() {
        int listSize = 1000000;

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            list.add(i);
        }
        Random random = new Random();

        for (int i = 0; i < listSize; i++) {
            int r = random.nextInt(list.size());
//            System.out.println("List.size():" + list.size());
//            System.out.println(list.get(r));
            list.remove(r); //remove index
        }
    }

    @Test
    public void timeRandomSortTime_Test() {
        int numOfElements = 6000000;
        HashMap<Integer, Integer> indexToValueMap = new HashMap<>();
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < numOfElements; i++) {
            indexToValueMap.put(i, i);
        }
        Random random = new Random();

        int mapSizeInitialized = indexToValueMap.size();
        for (int i = 0; i < mapSizeInitialized; i++) {
            int currentMapSize = indexToValueMap.size();
            int randomValue = random.nextInt(currentMapSize);
            Integer value = indexToValueMap.get(randomValue);
            integerList.add(value);

            //move the last key to current random index selected and remove the last index in the map
            indexToValueMap.replace(randomValue, indexToValueMap.get(currentMapSize - 1));
            indexToValueMap.remove(currentMapSize - 1);
        }

        LocalDateTime start = LocalDateTime.now();
        System.out.println("start at: " + start);
        Collections.sort(integerList);
        LocalDateTime end = LocalDateTime.now();
        System.out.println("end at: " + end);
        System.out.println("total time: " + Duration.between(start, end));
    }

    @Test
    public void RandomOnMap_Test() {
        int mapSize = 5;

        HashMap<Integer, Integer> indexToValueMap = new HashMap<>();
        for (int i = 0; i < mapSize; i++) {
            indexToValueMap.put(i, i);
        }
        Random random = new Random();

        int mapSizeInitialized = indexToValueMap.size();
        for (int i = 0; i < mapSizeInitialized; i++) {
            int currentMapSize = indexToValueMap.size();
            System.out.println("map.size:" + currentMapSize);
            int randomValue = random.nextInt(currentMapSize);
            Integer value = indexToValueMap.get(randomValue);
            System.out.println(value);

            //move the last key to current random index selected and remove the last index in the map
            indexToValueMap.replace(randomValue, indexToValueMap.get(currentMapSize - 1));
            indexToValueMap.remove(currentMapSize - 1);
        }
    }

    @Test
    public void RandomOnMap_Test1() {
        int mapSize = 10;

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < mapSize; i++) {
            map.put(i, i);
        }
        Random random = new Random();

        int size = map.size();
        for (int i = 0; i < size; i++) {
            int currentMapSize = map.size();
            System.out.println("map.size:" + currentMapSize);
            int r = random.nextInt(currentMapSize - i);
            Integer value = map.get(r);
            System.out.println(value);
            map.replace(r, map.get(currentMapSize - (i + 1)));
        }
    }

    @Test
    public void RandomOnMap_Test2() {
//        תן דרך איך להשמיע רשימה של שירים בצורה רגדומלית מתוך מערך ובלי לחזור על על אף שיר פעמיים .
//                בזמןהקצר ביותר
        String[] songArr = {"song1", "song2", "song3", "song4"};

        HashMap<Integer, Integer> indexToRandomMap = new HashMap<>();
        for (int i = 0; i < songArr.length; i++) {
            indexToRandomMap.put(i, i);
        }
        Random random = new Random();

        String[] result = new String[songArr.length];
        int mapSize = indexToRandomMap.size();
        for (int i = 0; i < mapSize; i++) {
            int r = random.nextInt(mapSize - i);
            Integer value = indexToRandomMap.get(r);
            result[i] = songArr[value];
            System.out.println(value);
            indexToRandomMap.replace(r, indexToRandomMap.get(mapSize - (i + 1)));
        }

        System.out.println("the song random order result is: " + Arrays.toString(result));
    }


}