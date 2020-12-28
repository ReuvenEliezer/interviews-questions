import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

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
        idsOfSongs = getIDsOfSongsOptimistic(rideDuration, songDurations);
        Assert.assertEquals(2, idsOfSongs.size());
        Assert.assertEquals(Arrays.asList(1, 2), idsOfSongs.stream().sorted().collect(Collectors.toList()));

        songDurations.clear();
        songDurations.addAll(Arrays.asList(1, 10, 25, 35, 60));
        rideDuration = 90;
        idsOfSongs = getIDsOfSongs(rideDuration, songDurations);

        Assert.assertEquals(2, idsOfSongs.size());
        Assert.assertEquals(Arrays.asList(2, 3), idsOfSongs.stream().sorted().collect(Collectors.toList()));

        idsOfSongs = getIDsOfSongsOptimistic(rideDuration, songDurations);
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
        Assert.assertTrue(pair1.equals(pair2));
        Assert.assertEquals(6, expected.size());
        Assert.assertEquals(expected, pairValues);
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
        Assert.assertTrue(pair1.equals(pair2));
        Assert.assertEquals(6, expected.size());
        Assert.assertEquals(expected, pairIndexes);
    }


    private HashSet<Pair> getPairValues(List<Integer> integerList) {
        HashSet<Pair> pairHashSet = new HashSet<>();
        for (int i = 0; i < integerList.size() - 1; i++) {
            for (int j = i + 1; j < integerList.size(); j++) {
                pairHashSet.add(new Pair(integerList.get(i), integerList.get(j)));
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

    class Pair {
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
            return null;
        return Collections.max(maxSongToResult.entrySet(), Comparator.comparingInt(Map.Entry::getKey)).getValue();
    }

    private List<Integer> getIDsOfSongsOptimistic(int rideDuration, List<Integer> songDurations) {
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
        return new ArrayList<>(Arrays.asList(firstSongIndex, secondSongIndex).stream().sorted().collect(Collectors.toList()));
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

