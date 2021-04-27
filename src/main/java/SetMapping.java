import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetMapping {

    @Test
    public void test() {
        List<String> lines = Arrays.asList("A:5", "B:7", "G:2", "A:6", "D:2");
        List<String> synonyms = Arrays.asList("A:G", "D:B", "G:A");//, "B:D");
        Map<String, Set<Integer>> sets = getSets(lines, synonyms);
        Assert.assertEquals(4, sets.size());
        Assert.assertEquals(sets.get("A"), Stream.of(5, 6, 2).collect(Collectors.toSet()));
        Assert.assertEquals(sets.get("B"), Collections.singleton(7));
        Assert.assertEquals(sets.get("G"), Stream.of(5, 6, 2).collect(Collectors.toSet()));
        Assert.assertEquals(sets.get("D"), Collections.singleton(2));
    }

    public Map<String, Set<Integer>> getSets(List<String> lines, List<String> synonyms) {
        Map<String, Set<Integer>> result = new HashMap<>();
        for (String s : lines) {
            String[] split = s.split(":");
            Set<Integer> integers = result.get(split[0]);
            if (integers == null) {
                integers = new HashSet<>();
                result.put(split[0], integers);
            }
            integers.add(Integer.valueOf(split[1]));
        }

        Map<String, String> bothDirectionMap = getBothDirectionMapUsingIterator(synonyms);
//        Map<String, String> bothDirectionMap = getBothDirectionMapUsingConcurrentHashMap(synonyms);

        //add bothDirectionMap
        for (Map.Entry<String, String> entry : bothDirectionMap.entrySet()) {
            result.get(entry.getValue()).addAll(result.get(entry.getKey()));
        }

        return result;
    }

    private Map<String, String> getBothDirectionMapUsingConcurrentHashMap(List<String> synonyms) {
        Map<String, String> bothDirectionMap = new ConcurrentHashMap<>();
        for (String s : synonyms) {
            String[] split = s.split(":");
            bothDirectionMap.put(split[0], split[1]);
        }

        for (Map.Entry<String, String> entry : bothDirectionMap.entrySet()) {
            if (!bothDirectionMap.containsKey(entry.getValue()))
                bothDirectionMap.remove(entry.getKey());
        }
        return bothDirectionMap;
    }

    private Map<String, String> getBothDirectionMapUsingIterator(List<String> synonyms) {
        Map<String, String> bothDirectionMap = new HashMap<>();
        for (String s : synonyms) {
            String[] split = s.split(":");
            bothDirectionMap.put(split[0], split[1]);
        }

        Iterator<Map.Entry<String, String>> iterator = bothDirectionMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (!bothDirectionMap.containsKey(entry.getValue()))
                iterator.remove();
        }
        return bothDirectionMap;
    }
}
