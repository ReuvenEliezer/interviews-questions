import org.junit.Test;

import java.util.*;

public class ProxymitiTest {

    @Test
    public void test1() {
        String s1 = "abc";
//        String s1 = "agc";
        String s2 = "acb";
        boolean check = check(s1, s2);
        boolean check2 = checkUsingOneMap(s1, s2);
        //spring scope - with multithreaded
//        exception handling
//        Evict controller
    }


    @Test
    public void test2() {
        List<String> stringList = Arrays.asList("ron", "nor", "onr", "avi", "avdsdsa", "iva");
        List<Set<String>> result = solution(stringList);
        System.out.println(Arrays.toString(result.toArray()));
    }

    private List<Set<String>> solution(List<String> stringList) {
        //use map as key and value original string list<String>
        Map<Map<Integer, Integer>, Set<String>> mainMap = new HashMap<>();
        for (String s : stringList) {
            Map<Integer, Integer> map = getCharsCounts(s);
            Set<String> strings = mainMap.get(map);
            if (strings == null) {
                strings = new HashSet<>();
            }
            strings.add(s);
            mainMap.put(map, strings);
        }

        List<Set<String>> result = new ArrayList<>();
        for (Map.Entry<Map<Integer, Integer>, Set<String>> mapListEntry : mainMap.entrySet()) {
            if (mapListEntry.getValue().size() > 1) { //add only if duplicated
                Set<String> inner = new HashSet<>();
                inner.addAll(mapListEntry.getValue());
                result.add(inner);
            }
        }
        return result;
    }

    private boolean check(String str1, String str2) {
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
    private boolean checkUsingOneMap(String s1, String s2) {
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
}
