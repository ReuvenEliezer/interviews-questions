import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AmazonTest {

    @Test
    public void AmazonReal1() {
        int rideDuration = 250;
        List<Integer> songDurations = new ArrayList<>(Arrays.asList(100, 180, 40, 120, 10));
        //https://stackoverflow.com/questions/2965747/why-do-i-get-an-unsupportedoperationexception-when-trying-to-remove-an-element-f
//        songDurations.clear();

        List<Integer> idsOfSongs = getIDsOfSongs(rideDuration, songDurations);

        Assert.assertEquals(2, idsOfSongs.size());
        Assert.assertEquals(Arrays.asList(1, 2), idsOfSongs.stream().sorted().collect(Collectors.toList()));
        idsOfSongs = getIDsOfSongsMemoryOptimistic(rideDuration, songDurations);
        Assert.assertEquals(2, idsOfSongs.size());
        Assert.assertEquals(Arrays.asList(1, 2), idsOfSongs.stream().sorted().collect(Collectors.toList()));

        songDurations.clear();
        songDurations.addAll(Arrays.asList(1, 10, 25, 35, 60));
        rideDuration = 90;
        idsOfSongs = getIDsOfSongs(rideDuration, songDurations);


        Assert.assertEquals(2, idsOfSongs.size());
        Assert.assertEquals(Arrays.asList(2, 3), idsOfSongs.stream().sorted().collect(Collectors.toList()));

        idsOfSongs = getIDsOfSongsMemoryOptimistic(rideDuration, songDurations);
        Assert.assertEquals(2, idsOfSongs.size());
        Assert.assertEquals(Arrays.asList(2, 3), idsOfSongs.stream().sorted().collect(Collectors.toList()));
    }

    @Test
    public void pairValuesTest() {
//        https://stackoverflow.com/questions/45928822/pair-object-overriding-equals-so-that-reverse-pairs-are-also-the-same
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4);
        HashSet<Pair> pairValues = getPairValues(integerList);
        HashSet<Pair> expected = new HashSet<>();
        expected.add(new Pair(1, 2));
        expected.add(new Pair(1, 3));
        expected.add(new Pair(1, 4));
        expected.add(new Pair(2, 3));
        expected.add(new Pair(2, 4));
        expected.add(new Pair(3, 4));
        expected.add(new Pair(4, 3));

        Pair pair1 = new Pair(3, 4);
        Pair pair2 = new Pair(4, 3);
        Assert.assertEquals(pair1, pair2);
        Assert.assertEquals(6, expected.size());
        Assert.assertEquals(expected, pairValues);
    }


    @Test
    public void findAllFourSumNumbersTest() {
        //        https://practice.geeksforgeeks.org/problems/find-all-four-sum-numbers1732/1/?track=hashing-interview&batchId=117
        int[] arr = new int[]{0, 0, 2, 1, 1};

        ArrayList<ArrayList<Integer>> arrayLists = findAllFourSumNumbers(arr, 3);
        Assert.assertEquals(1, arrayLists.size());
        Assert.assertEquals(4, arrayLists.get(0).size());
        Assert.assertEquals(Arrays.asList(0, 0, 1, 2), arrayLists.get(0));

        int[] arr1 = new int[]{10, 2, 3, 4, 5, 7, 8};
        arrayLists = findAllFourSumNumbers(arr1, 23);
        Assert.assertEquals(3, arrayLists.size());
        Assert.assertEquals(Arrays.asList(2, 3, 8, 10), arrayLists.get(0));
        Assert.assertEquals(Arrays.asList(2, 4, 7, 10), arrayLists.get(1));
        Assert.assertEquals(Arrays.asList(3, 5, 7, 8), arrayLists.get(2));

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

        Assert.assertNotEquals(actualList, expectedList);
        Assert.assertTrue(actualList.containsAll(expectedList));
        Assert.assertTrue(expectedList.containsAll(actualList));
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

    public ArrayList<ArrayList<Integer>> findAllFourSumNumbers(int[] arr, int k) {
        int fourNum = 4;
        if (arr.length < fourNum) return null;
        Set<Four> fourIntegerHashSet = new HashSet<>();
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        for (int a = 0; a < arr.length - (fourNum - 1); a++) {
            for (int b = a + 1; b < arr.length - (fourNum - 2); b++) {
                for (int c = b + 1; c < arr.length - (fourNum - 3); c++) {
                    for (int d = c + 1; d < arr.length; d++) {
                        int[] fourArr = new int[]{arr[a], arr[b], arr[c], arr[d]};
                        if (Arrays.stream(fourArr).sum() == k) {
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
        }

//        List<Four> collect = fourIntegerHashMap.entrySet().stream().filter(e -> e.getValue() == k).map(e -> e.getKey()).collect(Collectors.toList());

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
//        }).collect(Collectors.toList());

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
        Assert.assertEquals(four, four1);
        hashSet.add(four);
        hashSet.add(four1);
        Assert.assertEquals(1, hashSet.size());
    }


    @Test
    public void pairIndexesTest() {
//        https://stackoverflow.com/questions/45928822/pair-object-overriding-equals-so-that-reverse-pairs-are-also-the-same
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4);
        HashSet<Pair> pairIndexes = getPairIndexes(integerList);
        HashSet<Pair> expected = new HashSet<>();
        expected.add(new Pair(0, 1));
        expected.add(new Pair(0, 2));
        expected.add(new Pair(0, 3));
        expected.add(new Pair(1, 2));
        expected.add(new Pair(1, 3));
        expected.add(new Pair(2, 3));
        expected.add(new Pair(3, 2));

        Pair pair1 = new Pair(3, 4);
        Pair pair2 = new Pair(4, 3);
        Assert.assertEquals(pair1, pair2);
        Assert.assertEquals(6, expected.size());
        Assert.assertEquals(expected, pairIndexes);
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
        Assert.assertEquals(1, isSumExists(arr, sumOf2Elements));
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
        return Stream.of(firstSongIndex, secondSongIndex).sorted().collect(Collectors.toList());
    }


    @Test
    public void AmazonReal2() {
        int numOfTotalAvailableCities = 5;
        int numOfTotalAvailableRoads = 5;
        List<Integer> roadsAvailableList = new ArrayList<>();
        roadsAvailableList.add(1);
        roadsAvailableList.add(2);

        roadsAvailableList.add(2);
        roadsAvailableList.add(3);

        roadsAvailableList.add(3);
        roadsAvailableList.add(4);

        roadsAvailableList.add(4);
        roadsAvailableList.add(5);

        roadsAvailableList.add(1);
        roadsAvailableList.add(5);
        List<List<Integer>> roadsAvailable = new ArrayList<>();
        roadsAvailable.add(roadsAvailableList);

        int numRoadsToBeRepaired = 3;

        List<Integer> costRoadsToBeRepairList = new ArrayList<>();
        costRoadsToBeRepairList.add(1);
        costRoadsToBeRepairList.add(2);
        costRoadsToBeRepairList.add(12);

        costRoadsToBeRepairList.add(3);
        costRoadsToBeRepairList.add(4);
        costRoadsToBeRepairList.add(30);

        costRoadsToBeRepairList.add(1);
        costRoadsToBeRepairList.add(5);
        costRoadsToBeRepairList.add(8);

        List<List<Integer>> costRoadsToBeRepair = new ArrayList<>();
        costRoadsToBeRepair.add(costRoadsToBeRepairList);


        int minimumCostToRepair = getMinimumCostToRepair(
                numOfTotalAvailableCities,
                numOfTotalAvailableRoads,
                roadsAvailable,
                numRoadsToBeRepaired,
                costRoadsToBeRepair);
        System.out.println("result: " + minimumCostToRepair);
    }

    private int getMinimumCostToRepair(int numOfTotalAvailableCities,
                                       int numOfTotalAvailableRoads,
                                       List<List<Integer>> roadsAvailable,
                                       int numRoadsToBeRepaired,
                                       List<List<Integer>> costRoadsToBeRepair) {

        return -1;
    }
}