package com.interviews.questions.lruache;

import org.junit.Test;

import java.util.*;

public class LeetCodeTest {

    @Test
    public void test() {
        String[] strings = {"flower", "flow", "flight"};
        String s = longestCommonPrefixByRecursive(strings);

        String[] strings1 = {"flower", "flower", "flower", "flower"};
        String s1 = longestCommonPrefixByRecursive(strings1);
    }

    public String longestCommonPrefix(String[] strings) {
        if (strings == null) {
            return "";
        }
        if (strings.length == 1) {
            return strings[0];
        }

        Arrays.sort(strings, Comparator.comparingInt(String::length));
        Set<String> stringSet = new HashSet<>();
        String shortest = strings[0];
        int minLength = shortest.length();
        for (String str : strings) {
            if (str.length() > minLength) {
                // take
                String substring = str.substring(0, minLength);
                stringSet.add(substring);
            } else {
                stringSet.add(str);
            }
        }

        StringBuilder commonStr = new StringBuilder();
        for (int i = 0; i < minLength; i++) {
            char char1 = shortest.toCharArray()[i];
            Iterator<String> iterator = stringSet.iterator();
            while (iterator.hasNext()) {
                String string = iterator.next();
                char char2 = string.toCharArray()[i];
                if (char1 != char2) {
                    return commonStr.toString();
                }
            }
            commonStr.append(char1);
        }
        return commonStr.toString();
    }
    public String longestCommonPrefixByRecursive(String[] strings) {
        if (strings == null) {
            return "";
        }
        if (strings.length == 1) {
            return strings[0];
        }

        Arrays.sort(strings, Comparator.comparingInt(String::length));

        //remove duplicate by using Set
        Set<String> stringSet = new HashSet<>();
        String shortest = strings[0];
        int minLength = shortest.length();//Integer.MAX_VALUE;
        for (String str : strings) {
            if (str.length() > minLength) {
                // take
                String substring = str.substring(0, minLength);
                stringSet.add(substring);
            } else {
                stringSet.add(str);
            }
        }

        return doRecursive(stringSet).iterator().next();


//        StringBuilder commonStr = new StringBuilder();
//        for (int i = 0; i < minLength; i++) {
//            char char1 = shortest.toCharArray()[i];
//            Iterator<String> iterator = stringSet.iterator();
//            while (iterator.hasNext()) {
//                String string = iterator.next();
//                char char2 = string.toCharArray()[i];
//                if (char1 != char2) {
//                    return commonStr.toString();
//                }
//            }
//            commonStr.append(char1);
//        }
//
//        return commonStr.toString();

//        SortedSet<String> stringSet = Arrays.stream(strings).collect(
//                Collectors.toCollection(
//                        () -> new TreeSet<>(Comparator.comparingInt(String::length))
//                ));
//        List<String> list = new ArrayList<>(stringSet);
//        Collections.sort(list);
//        Arrays.sort(strings, Comparator.comparingInt(String::length));
//        Iterator<String> iterator = stringSet.iterator();
//        String first = iterator.next();
//        StringBuilder commonStr = new StringBuilder();
//        int minChars = first.length();
//        for (int i = 0; i < minChars; i++) {
//            char char1 = first.toCharArray()[i];
//            while (iterator.hasNext()) {
//                String string = iterator.next();
//                char char2 = string.toCharArray()[i];
//                if (char1 != char2) {
//                    return commonStr.toString();
//                }
//            }
//            commonStr.append(char1);
//        }
//
//        return commonStr.toString();
    }

    private Set<String> doRecursive(Set<String> stringSet) {
        if (stringSet.size() == 1) {
            return stringSet;
        }
        Iterator<String> iterator = stringSet.iterator();
        Set<String> strings = new HashSet<>();
        while (iterator.hasNext()) {
            String next = iterator.next();
            String substring = next.substring(0, next.length() - 1);
            strings.add(substring);
        }
        return doRecursive(strings);
    }
}
