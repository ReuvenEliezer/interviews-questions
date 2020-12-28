import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OwnBackupCodilityTest {

    @Test
    public void codilityTask1() {
//        int a = 1, b = 1, c = 2, d = 3;
        int a = 2, b = 4, c = 2, d = 4;
//        int a = -1, b = -1, c = -22, d = -3;



        int maxCoordinator = findMinMovedToMakeArrDistinctNum(a, b, c, d);
        System.out.println("max coordinator result is: " + maxCoordinator);
    }

    @Test
    public void codilityTask2() {
        int[] arr1 = {1, 2, 1};
        Assert.assertEquals(2, findMinMovedToMakeArrDistinctNum(arr1));

        int[] arr2 = {1, 1, 1};
        Assert.assertEquals(3, findMinMovedToMakeArrDistinctNum(arr2));

        int[] arr3 = {2, 2, 2};
        Assert.assertEquals(2, findMinMovedToMakeArrDistinctNum(arr3));

        int[] arr33 = {3, 3, 3};
        Assert.assertEquals(3, findMinMovedToMakeArrDistinctNum(arr33));

        int[] arr4 = {2, 1, 4, 4};
        Assert.assertEquals(1, findMinMovedToMakeArrDistinctNum(arr4));

        int[] arr5 = {6, 2, 3, 5, 6, 3};
        Assert.assertEquals("min total moves to make the arr as distinct not as expected",
                4, findMinMovedToMakeArrDistinctNum(arr5));
        int[] arr6 = {6, 2, 3, 6, 6, 3};
        Assert.assertEquals("min total moves to make the arr as distinct not as expected",
                5, findMinMovedToMakeArrDistinctNum(arr6));

        int[] arr7 = IntStream.range(1, 200000).parallel().toArray();
        Assert.assertEquals("min total moves to make the arr as distinct not as expected",
                0, findMinMovedToMakeArrDistinctNum(arr7));

        int[] arr8 = IntStream.range(1, 99999).parallel().toArray();
        int[] arr9 = IntStream.range(1, 50).parallel().toArray();
        int[] arr10 = ArrayUtils.addAll(arr8, arr9);
        Assert.assertEquals("min total moves to make the arr as distinct not as expected",
                4899902, findMinMovedToMakeArrDistinctNum(arr10));


        int[] arr11 = IntStream.range(1, 20001).toArray();

        for (int i = 0; i < 20000; i++) {
            arr11[i] = 1;
        }
        Assert.assertEquals("min total moves to make the arr as distinct not as expected",
                199990000, findMinMovedToMakeArrDistinctNum(arr11));
    }

    private int findMinMovedToMakeArrDistinctNum(int[] arr) {
        if (arr.length < 2)
            return 0;

        Map<Integer, Integer> numToTotalCountInstancesMap = new HashMap<>();
        Set<Integer> duplicateNumberHashSet = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            int value = arr[i];
            Integer sumOfValue = numToTotalCountInstancesMap.get(value);
            if (sumOfValue == null) {
                numToTotalCountInstancesMap.put(value, 1);
            } else {
                numToTotalCountInstancesMap.put(value, sumOfValue + 1);
                duplicateNumberHashSet.add(value);
            }
        }

        if (duplicateNumberHashSet.isEmpty())
            return 0;

        LinkedList<Integer> missingNumbersList = new LinkedList<>();
        for (int i = 1; i <= arr.length; i++) {
            if (!numToTotalCountInstancesMap.containsKey(i))
                missingNumbersList.add(i);
        }

        List<Integer> duplicateNumberSortedList = duplicateNumberHashSet.stream().sorted().collect(Collectors.toList());

        long totalMoves = 0;
        for (Integer duplicateNumber : duplicateNumberSortedList) {
            int totalNumInstances = numToTotalCountInstancesMap.get(duplicateNumber);
            int numOfInstanceToMove = totalNumInstances - 1;

            for (int i = 0; i < numOfInstanceToMove; i++) {
                Integer missingNumber = missingNumbersList.getFirst();
                int delta = Math.abs(missingNumber - duplicateNumber);
                totalMoves += delta;
                if (totalMoves > 1000000000)
                    return -1;
                missingNumbersList.removeFirst();
            }
        }

        return (int) totalMoves;
    }

    private int findMinMovedToMakeArrDistinctNum(int a, int b, int c, int d) {
        Map<List<Integer>, Integer> possibleCoordinatorToSumPoints = new HashMap();
        List<Integer> allPointsList = Arrays.asList(a, b, c, d);
        for (int i = 0; i < allPointsList.size(); i++) {
            Integer integer1 = allPointsList.get(i);
            for (int j = i + 1; j < allPointsList.size(); j++) {
                Integer integer2 = allPointsList.get(j);
                List<Integer> sortedIntList = Arrays.asList(integer1, integer2).stream().sorted().collect(Collectors.toList()); // for unique a key map
                possibleCoordinatorToSumPoints.put(sortedIntList, sortedIntList.stream().collect(Collectors.summingInt(Integer::intValue)));
            }
        }

        return possibleCoordinatorToSumPoints.values().stream().max(Comparator.comparing(Integer::intValue)).get();
    }
}
