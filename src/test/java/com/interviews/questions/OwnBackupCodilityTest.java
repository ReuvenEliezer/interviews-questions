package com.interviews.questions;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class OwnBackupCodilityTest {

    @Test
    public void codilityTask1() {
        int a = 1, b = 1, c = 2, d = 3;
//        int a = 2, b = 4, c = 2, d = 4;
//        int a = -1, b = -1, c = -22, d = -3;

        int maxCoordinator = findMaxSquaredDistanceBetweenPoints(a, b, c, d);
        System.out.println("max coordinator result is: " + maxCoordinator);
    }

    @Test
    public void codilityTask2() {
        int[] arr1 = {1, 2, 1};
        assertEquals(2, findMinMovedToMakeArrDistinctNum(arr1));

        int[] arr2 = {1, 1, 1};
        assertEquals(3, findMinMovedToMakeArrDistinctNum(arr2));

        int[] arr3 = {2, 2, 2};
        assertEquals(2, findMinMovedToMakeArrDistinctNum(arr3));

        int[] arr33 = {3, 3, 3};
        assertEquals(3, findMinMovedToMakeArrDistinctNum(arr33));

        int[] arr4 = {2, 1, 4, 4};
        assertEquals(1, findMinMovedToMakeArrDistinctNum(arr4));

        int[] arr5 = {6, 2, 3, 5, 6, 3};
        assertEquals("min total moves to make the arr as distinct not as expected",
                4, findMinMovedToMakeArrDistinctNum(arr5));
        int[] arr6 = {6, 2, 3, 6, 6, 3};
        assertEquals("min total moves to make the arr as distinct not as expected",
                5, findMinMovedToMakeArrDistinctNum(arr6));

        int[] arr7 = IntStream.range(1, 200000).parallel().toArray();
        assertEquals("min total moves to make the arr as distinct not as expected",
                0, findMinMovedToMakeArrDistinctNum(arr7));

        int[] arr8 = IntStream.range(1, 99999).parallel().toArray();
        int[] arr9 = IntStream.range(1, 50).parallel().toArray();
        int[] arr10 = ArrayUtils.addAll(arr8, arr9);
        assertEquals("min total moves to make the arr as distinct not as expected",
                4899902, findMinMovedToMakeArrDistinctNum(arr10));


        int[] arr11 = IntStream.range(1, 20001).toArray();

        for (int i = 0; i < 20000; i++) {
            arr11[i] = 1;
        }
        assertEquals("min total moves to make the arr as distinct not as expected",
                199990000, findMinMovedToMakeArrDistinctNum(arr11));
    }

    private int findMinMovedToMakeArrDistinctNum(int[] arr) {
        if (arr.length < 2)
            return 0;

        Map<Integer, Integer> numToTotalCountInstancesMap = new HashMap<>();
        Set<Integer> duplicateNumberHashSet = new HashSet<>();
        for (int value : arr) {
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

        List<Integer> duplicateNumberSortedList = duplicateNumberHashSet.stream().sorted().toList();

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

    private static int findMaxSquaredDistanceBetweenPoints(int a, int b, int c, int d) {
        Map<List<Integer>, Integer> possibleCoordinatorToSumPoints = new HashMap();
        List<Integer> allPointsList = Arrays.asList(a, b, c, d);
        for (int i = 0; i < allPointsList.size(); i++) {
            Integer integer1 = allPointsList.get(i);
            for (int j = i + 1; j < allPointsList.size(); j++) {
                Integer integer2 = allPointsList.get(j);
                List<Integer> sortedIntList = Stream.of(integer1, integer2).sorted().toList(); // for unique a key map
                possibleCoordinatorToSumPoints.put(sortedIntList, sortedIntList.stream().mapToInt(Integer::intValue).sum());
            }
        }

        return possibleCoordinatorToSumPoints.values().stream().max(Comparator.comparing(Integer::intValue)).get();
    }


    /**
     * Write a function:
     * <p>
     * class Solution { public int solution(int[] A); }
     * <p>
     * that, given an array A of N integers, returns the smallest positive integer (greater than 0) that does not occur in A.
     * <p>
     * For example, given A = [1, 3, 6, 4, 1, 2], the function should return 5.
     * <p>
     * Given A = [1, 2, 3], the function should return 4.
     * <p>
     * Given A = [−1, −3], the function should return 1.
     * <p>
     * Write an efficient algorithm for the following assumptions:
     * <p>
     * N is an integer within the range [1..100,000];
     * each element of array A is an integer within the range [−1,000,000..1,000,000].
     */

    @Test
    public void findSmallestPositive() {
        assertEquals(5, solution(new int[]{1, 3, 6, 4, 1, 2}));
        assertEquals(4, solution(new int[]{1, 2, 3}));
        assertEquals(1, solution(new int[]{-1, -3}));
    }

    public int solution(int[] a) {
        Set<Integer> collect = Arrays.stream(a).boxed().collect(Collectors.toSet());

        for (int i = 1; i < a.length; i++) {
            if (!collect.contains(i)) {
                return i;
            }
        }

        return a.length + 1;
    }
}
