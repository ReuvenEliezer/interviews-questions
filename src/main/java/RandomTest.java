import org.junit.Test;

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
    public void RandomOnLinkedList_Test() {
        int linkedListSize = 100000;

        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < linkedListSize; i++) {
            linkedList.add(i);
        }

        for (int i = 0; i < linkedListSize; i++) {
            Random random = new Random();
            int r = random.nextInt(linkedList.size());
            System.out.println("linkedList.size():" + linkedList.size());
            System.out.println(linkedList.get(r));
            linkedList.remove(r);
        }
    }

    @Test
    public void RandomOnList_Test() {
        int linkedListSize = 10;

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < linkedListSize; i++) {
            list.add(i);
        }

        for (int i = 0; i < linkedListSize; i++) {
            Random random = new Random();
            int r = random.nextInt(list.size());
            System.out.println("List.size():" + list.size());
            System.out.println(list.get(r));
            list.remove(r); //remove index
        }
    }

    @Test
    public void RandomOnMap_Test() {
        int mapSize = 100000;

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < mapSize; i++) {
            map.put(i, i);
        }

        int size = map.size();
        for (int i = 0; i < size; i++) {
            Random random = new Random();
            int currentMapSize = map.size();
            System.out.println("map.size:" + currentMapSize);
            int r = random.nextInt(currentMapSize);
            Integer value = map.get(r);
            System.out.println(value);
            map.replace(r, map.get(currentMapSize - 1));
            map.remove(currentMapSize - 1);
        }
    }

    @Test
    public void RandomOnMap_Test1() {
        int mapSize = 100000;

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < mapSize; i++) {
            map.put(i, i);
        }

        int size = map.size();
        for (int i = 0; i < size; i++) {
            Random random = new Random();
            int currentMapSize = map.size();
            System.out.println("map.size:" + currentMapSize);
            int r = random.nextInt(currentMapSize - i);
            Integer value = map.get(r);
            System.out.println(value);
            map.replace(r, map.get(currentMapSize - (i + 1)));
        }
    }


}