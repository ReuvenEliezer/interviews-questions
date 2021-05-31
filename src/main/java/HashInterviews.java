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

    public int largButMinFreq(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer i : arr) {
            map.merge(i, 1, Integer::sum);
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
    public void findSubArraySum() {
        /**
         * https://practice.geeksforgeeks.org/problems/subarrays-with-sum-k/1/?category[]=Hash&category[]=Hash&problemStatus=unsolved&problemType=functional&difficulty[]=1&page=1&query=category[]HashproblemStatusunsolvedproblemTypefunctionaldifficulty[]1page1category[]Hash
         */
        Assert.assertEquals(3, findSubArraySum(new int[]{10, 2, -2, -20, 10}, -10));
        Assert.assertEquals(2, findSubArraySum(new int[]{9, 4, 20, 3, 10, 5}, 33));

        Assert.assertEquals(3, findSubArraySum1(new int[]{10, 2, -2, -20, 10}, -10));
        Assert.assertEquals(2, findSubArraySum1(new int[]{9, 4, 20, 3, 10, 5}, 33));
    }

    private int findSubArraySum1(int[] arr, int k) {
        int result = 0;
        List<Pair> pairList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {

            int sum = 0;
            for (int j = i; j < arr.length; j++) {

                // calculate required sum
                sum += arr[j];

                // check if sum is equal to
                // required sum
                if (sum == k) {
                    result++;
                    pairList.add(new Pair(i, j));
                }
            }
        }
        return result;
    }

    private int findSubArraySum(int[] arr, int sum) {
        HashMap<Integer, Integer> prevSum = new HashMap<>();

        int res = 0;

        // Sum of elements so far.
        int currsum = 0;

        for (int j : arr) {

            // Add current element to sum so far.
            currsum += j;

            // If currsum is equal to desired sum,
            // then a new subarray is found. So
            // increase count of subarrays.
            if (currsum == sum)
                res++;

            // currsum exceeds given sum by currsum
            //  - sum. Find number of subarrays having
            // this sum and exclude those subarrays
            // from currsum by increasing count by
            // same amount.
            if (prevSum.containsKey(currsum - sum))
                res += prevSum.get(currsum - sum);

            // Add currsum value to count of
            // different values of sum.
            prevSum.merge(currsum, 1, Integer::sum);
        }

        return res;
    }

    @Test
    public void findMissing() {
        /**
         *  https://practice.geeksforgeeks.org/problems/in-first-but-second5423/1/?category[]=Hash&category[]=Hash&problemStatus=unsolved&problemType=functional&difficulty[]=1&page=1&sortBy=accuracy&query=category[]HashproblemStatusunsolvedproblemTypefunctionaldifficulty[]1page1sortByaccuracycategory[]Hash
         */
        Assert.assertEquals(Arrays.asList(4, 10), findMissing(new long[]{1, 2, 3, 4, 5, 10}, new long[]{2, 3, 1, 0, 5}));
        Assert.assertEquals(Arrays.asList(5), findMissing(new long[]{4, 3, 5, 9, 11}, new long[]{4, 9, 3, 11, 10}));
    }

    private List<Long> findMissing(long[] a, long[] b) {
        Map<Long, Integer> map = new HashMap<>();

        for (long l : b) {
            map.merge(l, 1, Integer::sum);
        }

        List<Long> list = new ArrayList<>();
        for (long l : a) {
            Integer integer = map.get(l);
            if (integer == null) {
                list.add(l);
            }
        }
        return list;
    }

    @Test
    public void minInsAndDel() {
        /**
         * https://practice.geeksforgeeks.org/problems/minimum-insertions-to-make-two-arrays-equal/1/?category[]=Hash&category[]=Hash&problemType=functional&difficulty[]=2&page=1&query=category[]HashproblemTypefunctionaldifficulty[]2page1category[]Hash
         */
        Assert.assertEquals(4, minInsAndDel(new int[]{1, 2, 5, 3, 1}, new int[]{1, 3, 5}));
        Assert.assertEquals(0, minInsAndDel(new int[]{1, 4}, new int[]{1, 4}));
        Assert.assertEquals(3, minInsAndDel(new int[]{1, 4, 2, 5}, new int[]{1, 2, 4}));
    }

    private int minInsAndDel(int[] a, int[] b) {
        //b is sorted.
        Map<Integer, Integer> valueToIndexMap = new HashMap<>();
//        for (int i = 0; i < a.length; i++) {
//            valueToIndexMap.
//        }

        int result = b.length - a.length;
        for (int i = 0; i < b.length; i++) {
            if (a.length - 1 < i || b[i] != a[i]) {
                result += 2;
            }
        }

        return result;
    }

    @Test
    public void returnIndexOfSumOf2Elements() {
        /**
         *https://www.geeksforgeeks.org/given-an-array-a-and-a-number-x-check-for-pair-in-a-with-sum-as-x/
         */
        int[] array = {1, 3, 5, 7};
        List<Pair> pairList = returnIndexOfSumOf2Elements(array, 6);
        Assert.assertEquals(1, pairList.size());
        Assert.assertEquals(Arrays.asList(0, 2), Arrays.asList(pairList.get(0).getKey(), pairList.get(0).getValue()));
    }

    private List<Pair> returnIndexOfSumOf2Elements(int[] arr, int sum) {
        List<Pair> pairList = new ArrayList<>();
        Map<Integer, Integer> valueToIndexMap = new HashMap<>();
        for (int i = 0; i < arr.length; ++i) {
            int temp = sum - arr[i];

            // checking for condition
            if (valueToIndexMap.containsKey(temp)) {
                System.out.println(
                        "Pair with given sum "
                                + sum + " is (" + arr[i]
                                + ", " + temp + ")");
                pairList.add(new Pair(valueToIndexMap.get(temp), i));
            }
            valueToIndexMap.put(arr[i], i);
        }
        return pairList;
    }

    private int[] returnIndexOfSumOf2Elements1(int[] arr, int sum) {
        Set<Integer> s = new HashSet<>();
        for (int i = 0; i < arr.length; ++i) {
            int temp = sum - arr[i];

            // checking for condition
            if (s.contains(temp)) {
                System.out.println(
                        "Pair with given sum "
                                + sum + " is (" + arr[i]
                                + ", " + temp + ")");
            }
            s.add(arr[i]);
        }
        return null;
    }

    @Test
    public void findDuplicate() {
        /**
         * https://practice.geeksforgeeks.org/problems/smallest-number-repeating-k-times3239/1/?problemStatus=unsolved&problemType=functional&sortBy=accuracy&category[]=Hash&page=1&query=problemStatusunsolvedproblemTypefunctionalsortByaccuracycategory[]Hashpage1
         */
        Assert.assertEquals(1, findDuplicate(new int[]{2, 2, 1, 3, 1}, 2));
        Assert.assertEquals(2, findDuplicate(new int[]{3, 5, 3, 2}, 1));
    }

    private int findDuplicate(int[] arr, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : arr) {
            map.merge(i, 1, Integer::sum);
        }
        Integer keyResult = null;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            Integer instances = entry.getValue();
            if (instances.equals(k)) {
                if (keyResult == null || entry.getKey() < keyResult) {
                    keyResult = entry.getKey();
                }
            }
        }
//        return keyResult;
        return map.entrySet()
                .stream()
                .filter(e -> e.getValue().equals(k))
                .map(Map.Entry::getKey)
                .min(Comparator.comparing(Integer::intValue))
                .get();
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

    private List<Integer> sortByFreq(int[] arr) {
        List<Integer> result = new ArrayList<>();
//        Map<Integer, AtomicInteger> map = new HashMap<>();
        Map<Integer, Integer> map = new HashMap<>();

        for (Integer integer : arr) {
//            map.computeIfAbsent(integer, v -> new AtomicInteger()).incrementAndGet();
            map.merge(integer, 1, Integer::sum);

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

    private ArrayList<Integer> sortByFreq1(int[] arr) {
        ArrayList<Integer> result = new ArrayList<>();
//        Map<Integer, AtomicInteger> map = new HashMap<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer integer : arr) {
            map.merge(integer, 1, Integer::sum);
//            map.computeIfAbsent(integer, v -> new AtomicInteger()).incrementAndGet();
        }
//        ValueComparator bvc = new ValueComparator(map);
//        TreeMap<Integer, Integer> sorted_map = new TreeMap<>(bvc);
//        sorted_map.putAll(map);
        List<Map.Entry<Integer, Integer>> entries = new ArrayList(map.entrySet());
        Comparator<Map.Entry<Integer, Integer>> comparing1 = Comparator.comparing(Map.Entry<Integer, Integer>::getValue).reversed();
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
        @Override
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
            patternMap.merge(i, 1, Integer::sum);
        }

        for (String s : dict) {
            if (s.length() != pattern.length())
                continue;
            Map<Integer, Integer> map = new HashMap<>();
            for (int i : s.toCharArray()) {
                map.merge(i, 1, Integer::sum);
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
            Pair<Integer, Integer> integerPair = instanceToIndexesMap.get(arr[i]);
            if (integerPair == null) {
                instanceToIndexesMap.put(arr[i], new Pair<>(i, null));
            } else {
                Integer minIndex = integerPair.getKey();
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

    private static class Pair<A, B> {

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
