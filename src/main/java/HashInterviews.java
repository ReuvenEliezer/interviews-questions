import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class HashInterviews {

    @Test
    public void frequencyGame() {
        /**
         * https://practice.geeksforgeeks.org/problems/frequency-game/1/?problemType=functional&page=1&query=problemTypefunctionalpage1
         */
        Assert.assertEquals(50, largButMinFreq(new int[]{2, 2, 5, 50, 1}));
    }

    public int largButMinFreq(int arr[]) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer i : arr) {
            map.put(i, map.get(i) == null ? 1 : map.get(i) + 1);
        }
        return Collections.max(map.entrySet(), Comparator.comparingInt(Map.Entry::getKey)).getKey();
    }

    @Test
    public void isIsogram() {
        Assert.assertTrue(isIsogram("machine"));
        Assert.assertFalse(isIsogram("geeks"));
    }

    private boolean isIsogram(String data) {
        Set<Integer> set = new HashSet<>();
        for (int i : data.toCharArray()) {
            if (set.contains(i))
                return false;
            set.add(i);
        }
        return true;
    }

    @Test
    public void uniqueNumbers() {
        Assert.assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9), uniqueNumbers(1, 9));
        Assert.assertEquals(Arrays.asList(10, 12, 13, 14, 15, 16, 17, 18, 19, 20), uniqueNumbers(10, 20));
    }

    private ArrayList<Integer> uniqueNumbers(int l, int r) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = l; i <= r; i++) {
            if (isIsogram(String.valueOf(i)))
                list.add(i);
        }
        return list;
    }

    @Test
    public void sortByFreq() {
        /**
         * https://practice.geeksforgeeks.org/problems/sorting-elements-of-an-array-by-frequency-1587115621/1/?category[]=Hash&category[]=Hash&problemStatus=unsolved&problemType=functional&difficulty[]=1&page=1&query=category[]HashproblemStatusunsolvedproblemTypefunctionaldifficulty[]1page1category[]Hash
         */
        Assert.assertEquals(Arrays.asList(4, 4, 5, 5, 6), sortByFreq(new int[]{5, 5, 4, 6, 4}));
        Assert.assertEquals(Arrays.asList(9, 9, 9, 2, 5), sortByFreq(new int[]{9, 9, 9, 2, 5}));

        Assert.assertEquals(Arrays.asList(4, 4, 5, 5, 6), sortByFreq1(new int[]{5, 5, 4, 6, 4}));
        Assert.assertEquals(Arrays.asList(9, 9, 9, 2, 5), sortByFreq1(new int[]{9, 9, 9, 2, 5}));
    }

    private List<Integer> sortByFreq(int arr[]) {
        List<Integer> result = new ArrayList<>();
//        Map<Integer, AtomicInteger> map = new HashMap<>();
        Map<Integer, Integer> map = new HashMap<>();

        for (Integer integer : arr) {
//            map.computeIfAbsent(integer, v -> new AtomicInteger()).incrementAndGet();
            map.put(integer, map.get(integer) == null ? 1 : map.get(integer) + 1);

        }

        Comparator<Map.Entry<Integer, Integer>> comparing1 = Comparator.comparing(Map.Entry<Integer, Integer>::getValue).reversed();
        Comparator<Map.Entry<Integer, Integer>> comparing2 = Comparator.comparing(Map.Entry<Integer, Integer>::getKey);

        List<Integer> collect = map.entrySet().stream()
                .sorted(comparing1.thenComparing(comparing2))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        for (Integer key : collect) {
            Integer totalInstances = map.get(key);
            for (int i = 0; i < totalInstances; i++) {
                result.add(key);
            }
        }
        return result;
    }

    private ArrayList<Integer> sortByFreq1(int arr[]) {
        ArrayList<Integer> result = new ArrayList<>();
//        Map<Integer, AtomicInteger> map = new HashMap<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer integer : arr) {
            map.put(integer, map.get(integer) == null ? 1 : map.get(integer) + 1);
//            map.computeIfAbsent(integer, v -> new AtomicInteger()).incrementAndGet();
        }
//        ValueComparator bvc = new ValueComparator(map);
//        TreeMap<Integer, Integer> sorted_map = new TreeMap<>(bvc);
//        sorted_map.putAll(map);
        List<Map.Entry<Integer, Integer>> entries = new ArrayList(map.entrySet());
        Comparator<Map.Entry<Integer, Integer>> comparing1  = Comparator.comparing(Map.Entry<Integer, Integer>::getValue).reversed();
        Comparator<Map.Entry<Integer, Integer>> comparing2 = Comparator.comparing(Map.Entry<Integer, Integer>::getKey);
//        Collections.sort(entries, comparing1.thenComparing(comparing2));
        entries.sort(comparing1.thenComparing(comparing2));

//        List<Integer> list = map.entrySet()
//                .stream().sorted(comparing1.thenComparing(comparing2))
//                .map(Map.Entry::getKey)
//                .collect(Collectors.toList());

        for (Map.Entry<Integer, Integer> entry : entries) {
            Integer key = entry.getKey();
            Integer totalInstances = entry.getValue();
            for (int i = 0; i < totalInstances; i++) {
                result.add(key);
            }
        }
        return result;
    }

    class ValueComparator implements Comparator<Integer> {
        Map<Integer, Integer> base;

        public ValueComparator(Map<Integer, Integer> base) {
            this.base = base;
        }

        // Note: this comparator imposes orderings that are inconsistent with
        // equals.
        public int compare(Integer a, Integer b) {
            if (base.get(a) >= base.get(b)) {
                return -1;
            } else {
                return 1;
            } // returning 0 would merge keys
        }
    }

    class ValueComparator1 implements Comparator<Integer> {
        Map<Integer, AtomicInteger> base;

        public ValueComparator1(Map<Integer, AtomicInteger> base) {
            this.base = base;
        }

        // Note: this comparator imposes orderings that are inconsistent with
        // equals.
        public int compare(Integer a, Integer b) {
            if (base.get(a).get() >= base.get(b).get()) {
                return -1;
            } else {
                return 1;
            } // returning 0 would merge keys
        }
    }

    @Test
    public void findMatchedWords() {
        /**
         * https://practice.geeksforgeeks.org/problems/match-specific-pattern/1/?category[]=Hash&category[]=Hash&problemStatus=unsolved&problemType=functional&page=1&query=category[]HashproblemStatusunsolvedproblemTypefunctionalpage1category[]Hash
         */
        Assert.assertEquals(Arrays.asList("abb", "xyy"), findMatchedWords(Arrays.asList("abb", "abc", "xyz", "xyy"), "foo"));
    }

    private List<String> findMatchedWords(List<String> dict, String pattern) {
        List<String> result = new ArrayList<>();

        Map<Integer, Integer> patternMap = new HashMap<>();
        for (int i : pattern.toCharArray()) {
            patternMap.put(i, patternMap.get(i) == null ? 1 : patternMap.get(i) + 1);
        }

        for (String s : dict) {
            if (s.length() != pattern.length())
                continue;
            Map<Integer, Integer> map = new HashMap<>();
            for (int i : s.toCharArray()) {
                map.put(i, map.get(i) == null ? 1 : map.get(i) + 1);
            }
            if (map.size() == patternMap.size())
                result.add(s);
        }

        return result;
    }

    @Test
    public void maxDistance() {
        /**
         *         https://practice.geeksforgeeks.org/problems/max-distance-between-same-elements/1/?category[]=Hash&category[]=Hash&problemStatus=unsolved&problemType=functional&page=1&query=category[]HashproblemStatusunsolvedproblemTypefunctionalpage1category[]Hash
         */
        Assert.assertEquals(5, maxDistance(new int[]{1, 1, 2, 2, 2, 1}));
        Assert.assertEquals(10, maxDistance(new int[]{3, 2, 1, 2, 1, 4, 5, 8, 6, 7, 4, 2}));

        Assert.assertEquals(5, maxDistanceLessMemory(new int[]{1, 1, 2, 2, 2, 1}));
        Assert.assertEquals(10, maxDistanceLessMemory(new int[]{3, 2, 1, 2, 1, 4, 5, 8, 6, 7, 4, 2}));
    }

    private int maxDistance(int[] arr) {
        Map<Integer, ArrayList<Integer>> instanceToIndexesMap = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            instanceToIndexesMap.computeIfAbsent(arr[i], v -> new ArrayList<>()).add(i);
        }
        int maxDistance = 0;
        for (Map.Entry<Integer, ArrayList<Integer>> entry : instanceToIndexesMap.entrySet()) {
            if (entry.getValue().size() >= 2) {
                List<Integer> indexesList = entry.getValue();
                Collections.sort(indexesList);
                int distance = indexesList.get(indexesList.size() - 1) - indexesList.get(0);
                if (distance > maxDistance) {
                    maxDistance = distance;
                }
            }
        }
        return maxDistance;
    }

    private int maxDistanceLessMemory(int[] arr) {
        Map<Integer, Pair<Integer, Integer>> instanceToIndexesMap = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            Pair<Integer, Integer> integerIntegerPair = instanceToIndexesMap.get(arr[i]);
            if (integerIntegerPair == null) {
                instanceToIndexesMap.put(arr[i], new Pair<>(i, null));
            } else {
                Integer minIndex = integerIntegerPair.getKey();
                instanceToIndexesMap.put(arr[i], new Pair<>(minIndex, i));
            }
        }
        int maxDistance = 0;
        for (Map.Entry<Integer, Pair<Integer, Integer>> entry : instanceToIndexesMap.entrySet()) {
            Pair<Integer, Integer> pair = entry.getValue();
            if (pair.getValue() != null) {
                int distance = pair.getValue() - pair.getKey();
                if (distance > maxDistance) {
                    maxDistance = distance;
                }
            }
        }
        return maxDistance;
    }

    private class Pair<A, B> {

        A key;
        B value;

        public Pair(A key, B value) {
            this.key = key;
            this.value = value;
        }

        public A getKey() {
            return key;
        }

        public B getValue() {
            return value;
        }
    }

}
