import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.*;

public class Tests {

    @Test
    public void test() {
        int[] arr = {12, 28, 83, 4, 25, 26, 25, 2, 25, 25, 25, 12};

        int i = minSubArrayLen(213, arr);
    }

    @Test
    public void newTest() {
    }

    @Test
    @Ignore
    public void ignoreTest() {
    }

    @Test
    @Ignore
    public void testFail() {
        Assertions.fail("bla bla");
    }


    public int minSubArrayLen(int s, int[] nums) {
//        ListMultimap<Integer, Integer> multimap = ArrayListMultimap.create();
//        multimap.put(1, 1);
//        multimap.put(1, 1);

        Map<Integer, Integer> valueToIndexMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            valueToIndexMap.put(i, nums[i]);
        }

        LinkedHashMap<Integer, Integer> mapSortedByValue = sortMapByValue(valueToIndexMap);


        Arrays.sort(nums);
        int result = 0;
        int accumulation = 0;
        for (int i = 0; i < nums.length; i++) {
            accumulation += nums[i];
            result++;
            if (accumulation >= s) {
                break;
            }
        }
        return result + 1;
    }

    public static LinkedHashMap<Integer, Integer> sortMapByValue(Map<Integer, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<Integer, Integer>> list = new LinkedList<>(hm.entrySet());

        // Sort the list
        Collections.sort(list, Comparator.comparing(Map.Entry::getValue));

        // put data from sorted list to hashmap
        LinkedHashMap<Integer, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

}
