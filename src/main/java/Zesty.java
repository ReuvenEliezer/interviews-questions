import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;

public class Zesty {

    /**
     * From Zesty to Everyone 08:35 PM
     * A numeric array of length N is given. We need to design a function that finds all positive numbers
     * in the array that have their opposites in it as well.
     * Describe approaches for solving optimal worst case and optimal average case performance, respectively.
     * <p>
     * -7 4 -3 2 2 -8 -2 3 3 7 -2 3 -2
     * ->
     * 7,3,2,2
     */
    @Test
    public void test() {
        int[] ints = new int[]{-7, 4, -3, 2, 2, -8, -2, 3, 3, 7, -2, 3, -2};
        Assert.assertEquals(Arrays.asList(7, 3, 2, 2).stream().sorted().collect(Collectors.toList()),
                calcResult(ints).stream().sorted().collect(Collectors.toList()));

    }

    private List<Integer> calcResult(int[] arr) {
        List<Integer> result = new ArrayList<>();

        //collect all negative numbers in arr and put it to map
        Map<Integer, Integer> negativeValueToInstanceMap = new HashMap<>();
        for (Integer integer : arr) {
            if (integer < 0) {
                negativeValueToInstanceMap.merge(integer, 1, Integer::sum);
//                Integer instanceNum = negativeValueToInstanceMap.get(integer);
//                if (instanceNum == null) {
//                    negativeValueToInstanceMap.put(integer, 1);
//                } else {
//                    negativeValueToInstanceMap.put(integer, ++instanceNum);
//                }
            }
        }

        //find the positive number in the map (by -) and if found -> reduce the instance from negative map.
        for (Integer integer : arr) {
            if (integer > 0) {
                Integer instanceNum = negativeValueToInstanceMap.computeIfPresent(-integer, (a, b) -> b - 1);
                if (instanceNum != null && instanceNum >= 0) {
                    result.add(integer);
                }
//                Integer instanceNum = negativeValueToInstanceMap.get(-integer);
//                if (instanceNum != null) {
//                    result.add(integer);
//                    if (instanceNum == 1) {
//                        negativeValueToInstanceMap.remove(-integer);
//                    } else {
//                        negativeValueToInstanceMap.put(-integer, --instanceNum);
//                    }
//                }
            }
        }
        return result;
    }

    @Test
    public void mapReferenceTest() {
        Map<Integer, String> integerStringMap = new HashMap<>();
        integerStringMap.put(1, "one");
        Set<Integer> integers = integerStringMap.keySet();
        integers.remove(1);
        Assert.assertTrue(integerStringMap.isEmpty());

        integerStringMap.put(1, "one");
        Set<Integer> integers1 = new HashSet<>(integerStringMap.keySet());
        integers1.remove(1);
        assertFalse(integerStringMap.isEmpty());

    }
}
