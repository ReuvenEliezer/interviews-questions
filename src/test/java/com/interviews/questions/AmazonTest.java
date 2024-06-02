package com.interviews.questions;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class AmazonTest {

    @Test
    public void amazonGeneralizedGCDTest() {
        /**
         * the greatest common divisor (GTC), also called highest common factor (HCF) of N numbers is the largest positive integer that divides all numbers without giving a remainder.
         * https://www.geeksforgeeks.org/gcd-two-array-numbers/
         */
        assertEquals(2, generalizedGCD(new int[]{2, 4, 6, 8, 10}));
        assertEquals(2, generalizedGCD(new int[]{10, 8, 6, 4, 2}));
        assertEquals(1, generalizedGCD(new int[]{2, 3, 4, 5, 6}));

    }


    private int generalizedGCD(int[] arr) {
        int result = arr[0];
        for (int i = 1; i < arr.length; i++)
            result = gcd(arr[i], result);

        return result;
    }

    static int gcd(int a, int b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }


    @Test
    public void amazonCellCompareTest() {
        /**
         * Eight houses, represented as cells are arranged in a straight line.
         * Each day every cell competes with its adjacent cells (neighbors).
         * An integer value of 1 represents an active cell and a value of 0 represents an inactive cell.
         * If the neighbors on both the sides of a cell are either active or inactive, the cell becomes inactive on the next day; otherwise the cell becomes active.
         * The two cells on each end have a single adjacent cell, so assume that the unoccupied space on the opposite side in an inactive cell.
         * Even after updating the cell state, consider its previous state when updating the state of other cells.
         * The state information of all cells should be updated simultaneously.
         *
         * write an algorithm to output the state of the cells after the given number of days.
         * input:
         * the input to the function/method consists of two arguments:
         * states, a list of integers representing the current state of cells;
         * days, an integer representing the number of days.
         * output:
         * return a list of integers representing the state og the cells after the given number of days.
         * note:
         * the elements of the list states contains 0s and 1s only
         */
        Assert.assertArrayEquals(new int[]{0, 1, 0, 0, 1, 0, 1, 0}, cellCompare(new int[]{1, 0, 0, 0, 0, 1, 0, 0}, 1));
        Assert.assertArrayEquals(new int[]{1, 0, 1, 1, 0, 0, 0, 1}, cellCompare(new int[]{0, 1, 0, 0, 1, 0, 1, 0}, 1));
        Assert.assertArrayEquals(new int[]{1, 0, 1, 1, 0, 0, 0, 1}, cellCompare(new int[]{1, 0, 0, 0, 0, 1, 0, 0}, 2));
        Assert.assertArrayEquals(new int[]{0, 0, 0, 0, 0, 1, 1, 0}, cellCompare(new int[]{1, 1, 1, 0, 1, 1, 1, 1}, 2));
    }

    private int[] cellCompare(int[] cells, int days) {
        /**
         * use clone(), if no - the reference will be copy !!
         * https://stackoverflow.com/questions/5785745/make-copy-of-an-array
         */
        int result[] = cells.clone();
        for (int i = 0; i < days; i++) {
            for (int j = 0; j < cells.length; j++) {
                int leftNeighbor;
                int rightNeighbor;
                if (j == 0) {
                    leftNeighbor = 0;
                } else {
                    leftNeighbor = cells[j - 1];
                }
                if (j == cells.length - 1) {
                    rightNeighbor = 0;
                } else {
                    rightNeighbor = cells[j + 1];
                }

                if ((leftNeighbor == 0 && rightNeighbor == 0) || (leftNeighbor == 1 && rightNeighbor == 1)) {
                    result[j] = 0;
                } else {
                    result[j] = 1;
                }
            }
            /**
             * use clone(), if no - the reference will be copy !!
             * https://stackoverflow.com/questions/5785745/make-copy-of-an-array
             */
            if (i < days - 1) { //only for optimization: I do clone only if the current iteration is not last
                cells = result.clone();
            }
        }
        return result;
    }

    @Test
    public void amazonIdsOfSongs() {
        /**
         * Amazon Music is worldng on a new feature to pair songs together
         * to play while on the bus The goal of this feature is to select two songs from a list
         * that will end exactly 30 seconds before the listener reaches their stop You are tasked with witing the method
         * that selects the songs from a list.
         * Each song is assigned a unique ID, numbered from 0 to N-1 Assumptions
         * 1. You will pick exactly 2 songs
         * 2. You cannot pick the same song twice
         * 3.1f you have multiple pairs with the same total time, select the pair with the longest song.
         * Input The input to the functionmethod consits of two arguments-
         * rideDuration an integer representing the duration of the ride in seconds songDurations,
         * a list of integers roprestoo the duration of the songs. Output Return a list of integers repr
         * finish exactly 30 seconds beteh sorigs whose combined runtime will dos Example Input rideDuration=90 o s = suoneingbuos anding
         * 12.31 During the ride duration of 90 seconds, the nider listens to the thirdID-2) and fourthiD-3)
         * song which end exactly 30 sconds before the bus arrives at their stop. uoneuejdg
         */
        int rideDuration = 250;
        List<Integer> songDurations = new ArrayList<>(Arrays.asList(100, 180, 40, 120, 10));
        //https://stackoverflow.com/questions/2965747/why-do-i-get-an-unsupportedoperationexception-when-trying-to-remove-an-element-f
//        songDurations.clear();

//        List<Integer> idsOfSongs = getIDsOfSongs(rideDuration, songDurations);
        List<Integer> idsOfSongs = getIDsOfSongsMemoryFinalOptimistic(rideDuration, songDurations);

        assertEquals(2, idsOfSongs.size());
        assertEquals(Arrays.asList(1, 2), idsOfSongs.stream().sorted().toList());
        idsOfSongs = getIDsOfSongsMemoryOptimistic(rideDuration, songDurations);
        assertEquals(2, idsOfSongs.size());
        assertEquals(Arrays.asList(1, 2), idsOfSongs.stream().sorted().toList());

        songDurations.clear();
        songDurations.addAll(Arrays.asList(1, 10, 25, 35, 60));
        rideDuration = 90;
        idsOfSongs = getIDsOfSongs(rideDuration, songDurations);


        assertEquals(2, idsOfSongs.size());
        assertEquals(Arrays.asList(2, 3), idsOfSongs.stream().sorted().toList());

        idsOfSongs = getIDsOfSongsMemoryOptimistic(rideDuration, songDurations);
        assertEquals(2, idsOfSongs.size());
        assertEquals(Arrays.asList(2, 3), idsOfSongs.stream().sorted().toList());
    }

    private List<Integer> getIDsOfSongsMemoryFinalOptimistic(int rideDurationInSec, List<Integer> songDurations) {
        /**
         * https://www.geeksforgeeks.org/given-an-array-a-and-a-number-x-check-for-pair-in-a-with-sum-as-x/
         */
        int timeBeforeArrivedBusInSeconds = 30;
        int requiredTotalTimeSongs = rideDurationInSec - timeBeforeArrivedBusInSeconds;

        SongData songData1 = null;
        SongData songData2 = null;
        Map<Integer, Integer> songDurationToIndexMap = new HashMap<>();
        for (int i = 0; i < songDurations.size(); i++) {
            songDurationToIndexMap.put(songDurations.get(i), i);
        }

        Set<Integer> songDurationSet = new HashSet<>();
        for (int index = 0; index < songDurations.size(); index++) {
            int songDurationTemp = requiredTotalTimeSongs - songDurations.get(index);
            if (songDurationSet.contains(songDurationTemp)) {
                //if (currentMax>prevMax replace result)
                if (songData1 == null || songData2 == null || Math.max(songDurations.get(index), songDurationTemp) > Math.max(songData1.duration, songData2.duration)) {
                    songData1 = new SongData(songDurationToIndexMap.get(songDurationTemp), songDurations.get(index));
                    songData2 = new SongData(index, songDurationTemp);
                }
            } else {
                songDurationSet.add(songDurations.get(index));
            }
        }
        if (songData1 != null && songData2 != null)
            return Arrays.asList(songData1.index, songData2.index);
        return new ArrayList<>();
    }

    record PairSong(SongData songData1, SongData songData2) {
    }

    record SongData(int index, int duration) {
    }

    @Test
    public void pairValuesTest() {
//        https://stackoverflow.com/questions/45928822/pair-object-overriding-equals-so-that-reverse-pairs-are-also-the-same
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4);
        Set<Pair> pairValues = getPairValues(integerList);
        Set<Pair> expected = new HashSet<>();
        expected.add(new Pair(1, 2));
        expected.add(new Pair(1, 3));
        expected.add(new Pair(1, 4));
        expected.add(new Pair(2, 3));
        expected.add(new Pair(2, 4));
        expected.add(new Pair(3, 4));
        expected.add(new Pair(4, 3));

        Pair pair1 = new Pair(3, 4);
        Pair pair2 = new Pair(4, 3);
        assertEquals(pair1, pair2);
        assertEquals(6, expected.size());
        assertEquals(expected, pairValues);
    }


    @Test
    public void findAllFourSumNumbersTest() {
        //        https://practice.geeksforgeeks.org/problems/find-all-four-sum-numbers1732/1/?track=hashing-interview&batchId=117
        int[] arr = new int[]{0, 0, 2, 1, 1};

        List<List<Integer>> arrayLists = findAllFourSumNumbers(arr, 3);
        assertEquals(1, arrayLists.size());
        assertEquals(4, arrayLists.get(0).size());
        assertEquals(Arrays.asList(0, 0, 1, 2), arrayLists.get(0));

        int[] arr1 = new int[]{10, 2, 3, 4, 5, 7, 8};
        arrayLists = findAllFourSumNumbers(arr1, 23);
        assertEquals(3, arrayLists.size());
        assertEquals(Arrays.asList(2, 3, 8, 10), arrayLists.get(0));
        assertEquals(Arrays.asList(2, 4, 7, 10), arrayLists.get(1));
        assertEquals(Arrays.asList(3, 5, 7, 8), arrayLists.get(2));

        ArrayList<List<Integer>> arrayLists1 = new ArrayList<>();
        arrayLists1.add(Arrays.asList(2, 3, 8, 10));
        arrayLists1.add(Arrays.asList(3, 5, 7, 8));
        arrayLists1.add(Arrays.asList(2, 4, 7, 10));
        boolean equals = arrayLists1.containsAll(arrayLists);


        int[] arr11 = new int[]{88, 84, 3, 51, 54, 99, 32, 60, 76, 68, 39, 12, 26, 86, 94, 39, 95, 70, 34, 78, 67, 1, 97, 2, 17, 92, 52};
        arrayLists = findAllFourSumNumbers(arr11, 179);
        System.out.println(Arrays.toString(arrayLists.toArray()));
    }


    @Test
    public void test() {
        String actual = "1 2 84 92 $1 3 76 99 $1 3 78 97 $1 12 67 99 $1 12 78 88 $1 17 67 94 $1 26 60 92 $1 26 68 84 $1 32 51 95 $1 32 52 94 $1 32 54 92 $1 32 60 86 $1 32 68 78 $1 32 70 76 $1 34 52 92 $1 34 60 84 $1 34 68 76 $1 39 51 88 $1 51 60 67 $2 3 86 88 $2 12 68 97 $2 12 70 95 $2 17 68 92 $2 17 76 84 $2 26 52 99 $2 26 54 97 $2 26 67 84 $2 32 51 94 $2 32 67 78 $2 34 51 92 $2 34 67 76 $2 39 39 99 $2 39 52 86 $2 39 54 84 $2 39 60 78 $2 39 68 70 $3 12 67 97 $3 12 70 94 $3 12 76 88 $3 12 78 86 $3 17 60 99 $3 17 67 92 $3 26 51 99 $3 32 52 92 $3 32 60 84 $3 32 68 76 $3 34 54 88 $3 39 51 86 $3 39 67 70 $3 52 54 70 $12 17 51 99 $12 32 51 84 $12 32 67 68 $12 34 39 94 $12 39 52 76 $12 39 60 68 $17 26 39 97 $17 26 52 84 $17 26 60 76 $17 32 52 78 $17 32 54 76 $17 32 60 70 $17 34 52 76 $17 34 60 68 $17 39 39 84 $26 32 51 70 $26 32 54 67 $26 34 51 68 $26 34 52 67 $26 39 54 60 $34 39 39 67 $34 39 52 54 $";
        String expected = "2 3 86 88 $3 34 54 88 $3 12 76 88 $12 39 60 68 $1 34 52 92 $17 39 39 84 $1 32 54 92 $2 39 39 99 $12 39 52 76 $1 39 51 88 $1 12 78 88 $1 32 51 95 $12 34 39 94 $3 32 60 84 $12 32 51 84 $1 3 76 99 $3 12 67 97 $3 32 68 76 $17 32 60 70 $3 17 60 99 $3 12 70 94 $34 39 52 54 $17 26 39 97 $2 12 68 97 $2 34 51 92 $2 39 52 86 $2 34 67 76 $17 32 52 78 $2 17 68 92 $2 39 60 78 $26 32 54 67 $3 12 78 86 $1 17 67 94 $12 17 51 99 $2 39 54 84 $17 26 52 84 $3 32 52 92 $12 32 67 68 $1 34 68 76 $1 51 60 67 $3 39 51 86 $1 12 67 99 $26 32 51 70 $1 32 68 78 $17 34 60 68 $1 3 78 97 $1 26 60 92 $26 39 54 60 $3 17 67 92 $1 34 60 84 $17 34 52 76 $34 39 39 67 $2 12 70 95 $3 52 54 70 $17 26 60 76 $2 26 54 97 $1 2 84 92 $2 17 76 84 $2 32 67 78 $26 34 52 67 $2 26 67 84 $3 26 51 99 $1 32 52 94 $1 26 68 84 $2 32 51 94 $17 32 54 76 $1 32 60 86 $1 32 70 76 $2 39 68 70 $26 34 51 68 $3 39 67 70 $2 26 52 99 $";
        List<List<Integer>> actualList = extracted(actual);
        List<List<Integer>> expectedList = extracted(expected);

        assertNotEquals(actualList, expectedList);
        assertTrue(actualList.containsAll(expectedList));
        assertTrue(expectedList.containsAll(actualList));
    }

    private List<List<Integer>> extracted(String s1) {
        List<List<Integer>> result = new ArrayList<>();
        for (String a : s1.split("\\$")) {
            List<Integer> innerList = new ArrayList<>();
            for (String b : a.split(" ")) {
                innerList.add(Integer.parseInt(b));
            }
            result.add(innerList);
        }
        return result;
    }

    public List<List<Integer>> findAllFourSumNumbers(int[] arr, int k) {
        int fourNum = 4;
        if (arr.length < fourNum) return null;

        HashMap<Integer, Pair> sumPairToIndexesMap = new HashMap<>();
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++)
                sumPairToIndexesMap.put(arr[i] + arr[j], new Pair(i, j));
        }

        List<List<Integer>> result = new ArrayList<>();
        Set<Four> fourIntegerHashSet = new HashSet<>();
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                int sum = arr[i] + arr[j];

                Pair pair = sumPairToIndexesMap.get(k - sum);
                // If X - sum is present in hash table,
                if (pair != null) {
                    Set<Integer> fourArrTemp = new HashSet<>();
                    fourArrTemp.addAll(Arrays.asList(i, j, pair.a, pair.b));
                    if (fourArrTemp.size() == 4) {
                        int[] fourArr = new int[]{arr[i], arr[j], arr[pair.a], arr[pair.b]};
//                    if (Arrays.stream(fourArr).sum() == k) {
                        Four four = new Four(fourArr);
                        if (!fourIntegerHashSet.contains(four)) {
                            fourIntegerHashSet.add(four);
                            ArrayList<Integer> inner = new ArrayList<>();
                            inner.add(four.a);
                            inner.add(four.b);
                            inner.add(four.c);
                            inner.add(four.d);
                            result.add(inner);
                        }
                    }
                }
            }
        }

//        Map<Integer, Integer> temp = new HashMap();
//        AtomicInteger counter = new AtomicInteger();
//        temp.putAll(Arrays.stream(arr).boxed().collect(Collectors.toMap((c) -> counter.incrementAndGet(), (c) -> c)));
//
//        for (int a = 0; a < arr.length - (fourNum - 1); a++) {
//            for (int b = a + 1; b < arr.length - (fourNum - 2); b++) {
//                for (int c = b + 1; c < arr.length - (fourNum - 3); c++) {
////                    for (int d = c + 1; d < arr.length; d++) {
//                    int[] fourArr = new int[]{arr[a], arr[b], arr[c], temp.get(c)};
//                    if (Arrays.stream(fourArr).sum() == k) {
//                        Four four = new Four(fourArr);
//                        if (!fourIntegerHashSet.contains(four)) {
//                            fourIntegerHashSet.add(four);
//                            ArrayList<Integer> inner = new ArrayList<>();
//                            inner.add(four.a);
//                            inner.add(four.b);
//                            inner.add(four.c);
//                            inner.add(four.d);
//                            result.add(inner);
//                        }
////                        }
//                    }
//                }
//            }
//        }

//        List<Four> collect = fourIntegerHashMap.entrySet().stream().filter(e -> e.getValue() == k).map(e -> e.getKey()).toList();

//        Comparator<ArrayList<Integer>> comparator1 = (o1, o2) -> o1.get(0).compareTo(o2.get(0));
//        Comparator<ArrayList<Integer>> comparator2 = (o1, o2) -> o1.get(1).compareTo(o2.get(1));
//        Comparator<ArrayList<Integer>> comparator3 = (o1, o2) -> o1.get(2).compareTo(o2.get(2));
//        Comparator<ArrayList<Integer>> comparator4 = (o1, o2) -> o1.get(3).compareTo(o2.get(3));
//
//        Collections.sort(result, comparator1.thenComparing(comparator2).thenComparing(comparator3).thenComparing(comparator4));
/**
 * https://stackoverflow.com/questions/35761864/java-sort-list-of-lists
 */
//        result = (ArrayList<ArrayList<Integer>>) result.stream().sorted((o1, o2) -> {
//            for (int i = 0; i < Math.min(o1.size(), o2.size()); i++) {
//                int c = o1.get(i).compareTo(o2.get(i));
//                if (c != 0) {
//                    return c;
//                }
//            }
//            return Integer.compare(o1.size(), o2.size());
//        }).toList();

        result.sort(new ListComparator<>());
//        Collections.sort(result, new ListComparator<>());
//        result.sort(new Comparator<ArrayList<Integer>>() {
//            @Override
//            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
//                return o1.get(0).compareTo(o2.get(0));
//            }
//        });
        return result;
    }


    /**
     * https://stackoverflow.com/questions/35761864/java-sort-list-of-lists
     *
     * @param <T>
     */
    static class ListComparator<T extends Comparable<T>> implements Comparator<List<T>> {

        @Override
        public int compare(List<T> o1, List<T> o2) {
            for (int i = 0; i < Math.min(o1.size(), o2.size()); i++) {
                int c = o1.get(i).compareTo(o2.get(i));
                if (c != 0) {
                    return c;
                }
            }
            return Integer.compare(o1.size(), o2.size());
        }

    }

    @Test
    public void fourValuesTest() {
        Set<Four> hashSet = new HashSet<>();
        Four four = new Four(0, 1, 2, 3);
        Four four1 = new Four(0, 2, 1, 3);
        assertEquals(four, four1);
        hashSet.add(four);
        hashSet.add(four1);
        assertEquals(1, hashSet.size());
    }


    @Test
    public void pairIndexesTest() {
//        https://stackoverflow.com/questions/45928822/pair-object-overriding-equals-so-that-reverse-pairs-are-also-the-same
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4);
        Set<Pair> pairIndexes = getPairIndexes(integerList);
        Set<Pair> expected = new HashSet<>();
        expected.add(new Pair(0, 1));
        expected.add(new Pair(0, 2));
        expected.add(new Pair(0, 3));
        expected.add(new Pair(1, 2));
        expected.add(new Pair(1, 3));
        expected.add(new Pair(2, 3));
        expected.add(new Pair(3, 2));

        Pair pair1 = new Pair(3, 4);
        Pair pair2 = new Pair(4, 3);
        assertEquals(pair1, pair2);
        assertEquals(6, expected.size());
        assertEquals(expected, pairIndexes);
    }


    private HashSet<Pair> getPairValues(List<Integer> integerList) {
        HashSet<Pair> pairHashSet = new HashSet<>();
        for (int i = 0; i < integerList.size() - 1; i++) {
            Integer integer = integerList.get(i);
            for (int j = i + 1; j < integerList.size(); j++) {
                pairHashSet.add(new Pair(integer, integerList.get(j)));
            }
        }
        return pairHashSet;
    }

    private HashSet<Pair> getPairValues(int[] arr) {
        HashSet<Pair> pairHashSet = new HashSet<>();
        for (int i = 0; i < arr.length - 1; i++) {
            int integer = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                pairHashSet.add(new Pair(integer, arr[j]));
            }
        }
        return pairHashSet;
    }

    private HashSet<Pair> getPairIndexes(List<Integer> integerList) {
        HashSet<Pair> pairHashSet = new HashSet<>();
        for (int i = 0; i < integerList.size() - 1; i++) {
            for (int j = i + 1; j < integerList.size(); j++) {
                pairHashSet.add(new Pair(i, j));
            }
        }
        return pairHashSet;
    }

    static class Pair {
        int a;
        int b;

        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return (a == pair.a && b == pair.b) || (a == pair.b && b == pair.a);
        }

        @Override
        public int hashCode() {
            return Objects.hash(a + b);
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "a=" + a +
                    ", b=" + b +
                    '}';
        }
    }

    static class Four {
        int a;
        int b;
        int c;
        int d;

        public Four(int[] fourNumToSort) {
            Arrays.sort(fourNumToSort);
            a = fourNumToSort[0];
            b = fourNumToSort[1];
            c = fourNumToSort[2];
            d = fourNumToSort[3];
        }

        public Four(int a, int b, int c, int d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Four four = (Four) o;
            return (a == four.a && b == four.b && c == four.c && d == four.d)
                    || (a == four.b && b == four.a && c == four.c && d == four.d)
                    || (a == four.c && b == four.b && c == four.a && d == four.d)
                    || (a == four.d && b == four.b && c == four.a && d == four.a)

                    || (a == four.a && b == four.c && c == four.b && d == four.d)
                    || (a == four.a && b == four.d && c == four.c && d == four.b)


                    || (a == four.a && b == four.b && c == four.d && d == four.c)

                    ;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a + b + c + d);
        }

        @Override
        public String toString() {
            return "Four{" +
                    "a=" + a +
                    ", b=" + b +
                    ", c=" + c +
                    ", d=" + d +
                    '}';
        }

    }


    @Deprecated
    private List<Integer> getIDsOfSongs(int rideDuration, List<Integer> songDurations) {
        int timeBeforeArrivedBusInSeconds = 30;
        int totalSongTime = rideDuration - timeBeforeArrivedBusInSeconds;
        Map<Integer, List<Integer>> maxSongToResult = new HashMap<>();
        for (int i = 0; i < songDurations.size(); i++) {
            Integer firstSong = songDurations.get(i);
            for (int j = 0; j < songDurations.size(); j++) {
                if (j == i)
                    continue;
                Integer secondSong = songDurations.get(j);
                if (firstSong + secondSong == totalSongTime) {
                    int maxSongDuration = Math.max(firstSong, secondSong);
                    if (!maxSongToResult.containsKey(maxSongDuration)) {
                        List<Integer> result = new ArrayList<>();
                        result.add(i);
                        result.add(j);
                        maxSongToResult.put(maxSongDuration, result);
                    }
                }
            }
        }
        if (maxSongToResult.isEmpty())
            return new ArrayList<>();
        return Collections.max(maxSongToResult.entrySet(), Comparator.comparingInt(Map.Entry::getKey)).getValue();
    }

    @Test
    public void sumExistsTest() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int sumOf2Elements = 14;
        assertEquals(1, isSumExists(arr, sumOf2Elements));
    }

    public int isSumExists(int[] arr, int sum) {
        for (int i = 0; i < arr.length - 1; i++) {
            Integer integer = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                if (integer + arr[j] == sum)
                    return 1;
            }
        }
        return 0;
    }

    @Deprecated
    private List<Integer> getIDsOfSongsMemoryOptimistic(int rideDuration, List<Integer> songDurations) {
        int timeBeforeArrivedBusInSeconds = 30;
        int totalSongTime = rideDuration - timeBeforeArrivedBusInSeconds;

        Integer firstSongIndex = null;
        Integer secondSongIndex = null;
        for (int i = 0; i < songDurations.size() - 1; i++) {
            int tempFirst = songDurations.get(i);
            for (int j = i + 1; j < songDurations.size(); j++) {
                int tempSecond = songDurations.get(j);
                if (tempFirst + tempSecond == totalSongTime) {
                    if (firstSongIndex == null && secondSongIndex == null) {
                        firstSongIndex = i;
                        secondSongIndex = j;
                    } else {
                        if (tempFirst > firstSongIndex || tempFirst > secondSongIndex || tempSecond > firstSongIndex || tempSecond > secondSongIndex) {
                            //need to replace
                            firstSongIndex = i;
                            secondSongIndex = j;
                        }
                    }
                }
            }
        }
        return Stream.of(firstSongIndex, secondSongIndex).sorted().toList();
    }

    @Test
    @Ignore
    public void amazonMinimumCostToRepair() {
        /**
         *In the country of Techlandia, there are N cities connected by bidirectional roads.
         * Each city in Techlandia is connected to every other city, either directly or through other cities. This year's winter was unusually harsh, causing some of the roads to be damaged and unusable. This has presenteda number of distribution challenges since many of Amazon's customers live in Techlandia To overcome these distribution challenges, Amazon is collaborating with a construction company to repair the roads such that one can travel (and deliver packages) from one city to any other city again. A recent estimate from the construction company indicates that the cost to repair one road can be different from the cost to repair another road. You have been tasked with assisting the construction companin their planning of repairs. Your task is to help identify a set of roads such that the cost involved in repairing the roads is minimized and all the cities are connected to each other either directly or via some other cities.
         * Using sets of roads, broken roads, and repair costs, write an algorithm to calculate the minimum total.cost to repair some of the roads so that all the cities are once again accessible from each other.
         * Input:
         * the input to the function/method consists of five arguments numTotalAvailableCities, an integerrepresenting the total number of cities (N) (eg. if N 3 the cities are represented as,23 num TotalAvalableRoads, an integer representing the total number of roads roadsAvailable, a list of integers where each element of the list consists of a pair representing the cities directly connected by a road numRoadsToBeRepaired an integer representing the total number of roads that are CostRoadsToBeRepaired a list of integers where each element of the list consists of a triplet representing the pair of cities between which a road is currently unusable and the cost of repairing that road, respectively feg. 1.3, 10] means to repair a road between cities1 and 3, the cost would be 10).
         * Problem costRoadsToBeRepaired a list of Integers where each element of the list consists of a triplet representing the pair of cities between whicha road is currently unusable and the cost of repairing that road, respectively (eg. L3, 10) means to repair a road between cities ) and 3, the cost would be 10). andano Return an integer representing the minimum total cost to repair some roads so that all the cities are accessible from each other. Constraints Os numTotalAvailableCities s 50 Os costRoadsToBeRepairedi(2] s 1000 Osie numRoadsToBeRepaired Example anduj numTotalAvailableCities 5 numTotalAvailableRoads=5 roadsAvailable = [[1. 2). [2. 3). [3. 4). [4. 5). (1, S1] numRoadsToBeRepaired = 3 costRoadsToBeRepaired = [[L. 2. 12]. [3, 4, 30]. [1. 5. 8]] andino  : There are three networks due to the unusable roads [1]. [2. 31 and [4, 5]. We can connect these networks inlo a single network by repairing the road between cities 1 and 2. and cities l and 5 at a minimum.cost of 12 + 8 So, the output is 20.
         */
        int numOfTotalAvailableCities = 5;
        int numOfTotalAvailableRoads = 5;
        List<List<Integer>> roadsAvailable = new ArrayList<>();
        roadsAvailable.add(Arrays.asList(1, 2));
        roadsAvailable.add(Arrays.asList(2, 3));
        roadsAvailable.add(Arrays.asList(3, 4));
        roadsAvailable.add(Arrays.asList(4, 5));
        roadsAvailable.add(Arrays.asList(1, 5));

        int numRoadsToBeRepaired = 3;

        List<List<Integer>> costRoadsToBeRepaired = new ArrayList<>();
        costRoadsToBeRepaired.add(Arrays.asList(1, 2, 12));
        costRoadsToBeRepaired.add(Arrays.asList(3, 4, 30));
        costRoadsToBeRepaired.add(Arrays.asList(1, 5, 8));


        int minimumCostToRepair = getMinimumCostToRepair(
                numOfTotalAvailableCities,
                numOfTotalAvailableRoads,
                roadsAvailable,
                numRoadsToBeRepaired,
                costRoadsToBeRepaired);

        assertEquals(20, minimumCostToRepair);
    }

    @Test
    public void findAllSubArray() {
        //https://practice.geeksforgeeks.org/problems/sub-array-sum-divisible-by-k2617/1
        long[] arr = new long[]{4, 5, 0, -2, -3, 1};
        assertEquals(7, subArraySumDivisibleByK(arr, 5));
        assertEquals(7, subArraySumDivisibleByK1(arr, 5));
    }

    private int subArraySumDivisibleByK(long[] arr, int divide) {
        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int total = 0;
                for (int k = i; k <= j; k++) {
                    total += arr[k];
                }
                if (total % divide == 0) {
                    result++;
                }
            }
        }
        return result;
    }

    public int subArraySumDivisibleByK1(long arr[], int k) {
        Map<Long, Integer> map = new HashMap<>();
        map.put(0l, 1);
        int count = 0;
        long sum = 0;
        for (long a : arr) {
            sum = (sum + a) % k;
            if (sum < 0)
                sum += k;  // Because -1 % 5 = -1, but we need the positive mod 4
            count += map.getOrDefault(sum, 0);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;

//        Map<Integer, Integer> modulusCountMap = new HashMap<>();
//        int[] consecSum = new int[arr.length];
//        consecSum[0] = arr[0];
//
//        for (int i = 1; i < arr.length; i++) {
//            consecSum[i] = consecSum[i - 1] + arr[i];
//        }
//
//        for (int i = 0; i < arr.length; i++) {
//            consecSum[i] = consecSum[i] % k;
//
//            if (consecSum[i] == 0 && modulusCountMap.get(consecSum[i]) == null) {
//                modulusCountMap.put(consecSum[i], 2);
//            } else {
//                modulusCountMap.put(consecSum[i], modulusCountMap.get(consecSum[i]) == null ? 1 : modulusCountMap.get(consecSum[i]) + 1);
//            }
//
//        }
//
//        int count = 0;
//
//        for (Integer val : modulusCountMap.values()) {
//            count = count + (val * (val - 1)) / 2;
//        }
//
//        return count;
    }

    private int getMinimumCostToRepair(int numOfTotalAvailableCities,
                                       int numOfTotalAvailableRoads,
                                       List<List<Integer>> roadsAvailable,
                                       int numRoadsToBeRepaired,
                                       List<List<Integer>> costRoadsToBeRepair) {

        return -1;
    }
}