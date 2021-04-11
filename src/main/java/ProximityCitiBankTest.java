import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class ProximityCitiBankTest {

    @Test
    public void isAnagramTwoStrings() {
        String s1 = "abc";
//        String s1 = "agc";
        String s2 = "acb";
        boolean isAnagram = isAnagram(s1, s2);
        boolean isAnagram2 = isAnagramUsingOneMap(s1, s2);
        //spring scope - with multithreaded
//        exception handling
//        Evict controller
    }

    @Test
    public void isAnagramTwoArr() {
        long arr1[] = {1, 2, 5, 4, 4};
        long arr2[] = {2, 4, 5, 4, 1};
        Assert.assertTrue(isAnagramArrays(arr1, arr2));
    }

    private boolean isAnagramArrays(long[] arr1, long[] arr2) {
        Map<Long, Long> map = new HashMap<>();
        for (Long aLong : arr1) {
            Long count = map.get(aLong);
            if (count == null) {
                count = 0l;
            }
            map.put(aLong, ++count);
        }

        for (Long aLong : arr2) {
            Long count = map.get(aLong);
            if (count == null) return false;
            if (count == 1) {
                map.remove(aLong);
            } else {
                map.put(aLong, count - 1);
            }
        }
        return map.isEmpty();
    }


    @Test
    public void test2() {
        /**
         * https://practice.geeksforgeeks.org/problems/print-anagrams-together/1/?track=hashing-interview&batchId=117
         */

        List<String> stringList1 = Arrays.asList("ron", "nor", "onr");
        List<String> stringList2 = Arrays.asList("fdf");
        List<String> stringList3 = Arrays.asList("avi", "iva");
        List<String> allStr = new ArrayList<>();
        allStr.addAll(stringList1);
        allStr.addAll(stringList2);
        allStr.addAll(stringList3);

        List<List<String>> result = anagrams(allStr);
        System.out.println(Arrays.toString(result.toArray()));
        Assert.assertEquals(3, result.size());
        Assert.assertEquals(stringList1, result.get(0));
        Assert.assertEquals(stringList2, result.get(1));
        Assert.assertEquals(stringList3, result.get(2));


        stringList1 = Arrays.asList("act", "cat", "tac");
        stringList2 = Arrays.asList("god", "dog");
        allStr.clear();
        allStr.addAll(stringList1);
        allStr.addAll(stringList2);
        result = anagrams(allStr);
        System.out.println(Arrays.toString(result.toArray()));
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(stringList1, result.get(0));
        Assert.assertEquals(stringList2, result.get(1));

        stringList1 = Arrays.asList("bfj", "tro", "ffa", "rph");
        allStr.clear();
        allStr.addAll(stringList1);
        result = anagrams(allStr);
        System.out.println(Arrays.toString(result.toArray()));
        Assert.assertEquals(4, result.size());
    }

    private List<List<String>> anagrams(List<String> stringList) {
        //use map as key and value original string list<String>
        Map<Map<Integer, Integer>, List<String>> mainMap = new HashMap<>();
        for (String s : stringList) {
            Map<Integer, Integer> map = getCharsCounts(s);
            List<String> strings = mainMap.getOrDefault(map, new ArrayList<>());
            strings.add(s);
            mainMap.put(map, strings);
        }

        List<List<String>> result = new ArrayList<>();
        for (Map.Entry<Map<Integer, Integer>, List<String>> mapListEntry : mainMap.entrySet()) {
            // if (mapListEntry.getValue().size() > 1) { //add only if duplicated
            List<String> inner = new ArrayList<>();
            inner.addAll(mapListEntry.getValue());
            result.add(inner);
            // }
        }
        return result;
    }

    private boolean isAnagram(String str1, String str2) {
        if (str1.length() != str2.length())
            return false;

        Map<Integer, Integer> map1 = getCharsCounts(str1);
        Map<Integer, Integer> map2 = getCharsCounts(str2);

        for (Map.Entry<Integer, Integer> entry : map1.entrySet()) {
            Integer key = entry.getKey();
            Integer str2Count = map2.get(key);
            if (!entry.getValue().equals(str2Count))
                return false;
        }

        return true;
    }

    //memory optimization
    // TODO use one map: reduce map count from map1 and if map1 is empty= return true else - return false.
    private boolean isAnagramUsingOneMap(String s1, String s2) {
        if (s1.length() != s2.length())
            return false;

        Map<Integer, Integer> map = getCharsCounts(s1);
        for (int charValue : s2.toCharArray()) {
            Integer instanceCount = map.get(charValue);
            if (instanceCount == null)
                return false;
            if (instanceCount == 1) {
                map.remove(charValue);
            } else {
                map.put(charValue, instanceCount - 1);
            }
        }

        return map.isEmpty();

//        return map.entrySet().stream()
//                .parallel()
//                .filter(e -> e.getValue() > 0)
//                .count() == 0;
    }

    private Map<Integer, Integer> getCharsCounts(String str) {
        Map<Integer, Integer> charCodeToCountMap = new HashMap<>();

        for (int charValue : str.toCharArray()) {
            Integer instanceCount = charCodeToCountMap.get(charValue);
            if (instanceCount == null) {
                charCodeToCountMap.put(charValue, 1);
            } else {
                charCodeToCountMap.put(charValue, instanceCount + 1);
            }
        }
        return charCodeToCountMap;
    }

    @Test
    public void remAnagramsTest() {
        /**
         * https://practice.geeksforgeeks.org/problems/anagram-of-string/1/?track=string-interview&batchId=117
         */
        String s1 = "agafa";
        String s2 = "jjbvvd";
        Assert.assertEquals(11, remAnagrams(s1, s2));

        Assert.assertEquals(2, remAnagrams("cddgk", "gcd"));
        Assert.assertEquals(3, remAnagrams("bcadeh", "hea"));

    }

    public int remAnagrams(String s1, String s2) {
        String big;
        String small;
        if (s1.length() > s2.length()) {
            big = s1;
            small = s2;
        } else {
            big = s2;
            small = s1;
        }

        int smallToRemove = 0;
        Map<Integer, Integer> map = getCharsCounts(big);
        for (int charValue : small.toCharArray()) {
            Integer instanceCount = map.get(charValue);
            if (instanceCount == null) {
                smallToRemove++;
            } else if (instanceCount == 1) {
                map.remove(charValue);
            } else {
                map.put(charValue, instanceCount - 1);
            }
        }

        return map.values().stream().mapToInt(Integer::intValue).sum() + smallToRemove;
    }

    @Test
    public void findMinimumIndexedCharacterTest() {
//        https://practice.geeksforgeeks.org/problems/minimum-indexed-character0221/1/?category[]=Hash&category[]=Hash&page=1&query=category[]Hashpage1category[]Hash#
        StringBuffer stringBuffer = printMinIndexChar("vsizcnmjilegtiugfxqtkeggknxxojrlczmgozrykxgefdmkadfmjotvdsuremfgnroeqfeddljkqvvqacejszfwszpwnue", "douhezn");
        Assert.assertEquals("z", stringBuffer.toString());
    }

    public static StringBuffer printMinIndexChar(String str, String patt) {
        HashSet<Integer> characterHashSet = new LinkedHashSet<>();
        for (int c : patt.toCharArray()) {
            characterHashSet.add(c);
        }

        for (char charInt : str.toCharArray()) {
            if (characterHashSet.contains((int) charInt)) {
                return new StringBuffer().append(charInt);
            }
        }
        return new StringBuffer().append("$");
    }

}
