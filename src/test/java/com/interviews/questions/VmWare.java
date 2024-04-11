package com.interviews.questions;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class VmWare {
    private MyMap root = new MyMap();

    //https://www.geeksforgeeks.org/auto-complete-feature-using-trie/
    @Test
    public void autoCompleteFeatureUsingTrie() {
        fillMap(words);
        Collection<String> result = autoCompleteOptimize("car");
    }

    public void fillMap(Collection<String> words) {
        root = new MyMap();
        for (String word : words)
            root.insert(word);
    }

    public Collection<String> autoCompleteOptimize(String phrase) {
        Collection<String> set = new HashSet<>();
        MyMap root = this.root;
        for (char aChar : phrase.toCharArray()) {
            root = root.childrenMap.get(aChar);
            if (root == null)
                return Collections.EMPTY_SET;
        }

        suggestHelper(root, set, new StringBuilder(phrase));
        return set;
    }

    public void suggestHelper(MyMap root, Collection<String> set, StringBuilder curr) {
        if (root.isWord) {
            set.add(curr.toString());
        }

        if (root.childrenMap == null || root.childrenMap.isEmpty())
            return;

        for (MyMap child : root.childrenMap.values()) {
            suggestHelper(child, set, curr.append(child.aChar));
            curr.setLength(curr.length() - 1);
        }
    }

    private static int resultLimit = 3;

    private static final Collection<String> words = new HashSet<>();

    static {
        words.add("cat");
        words.add("car");
        words.add("can");
        words.add("card");
//        words.add("cart");
        words.add("carrot");
//        words.add("cartoon");
//        words.add("cactus");
//
//        words.add("dell");
//        words.add("demo");
//        words.add("delta");
//        words.add("delete");
//
//        words.add("madagascar");
//        words.add("model");
    }

    public static Collection<String> autoComplete(final String phrase) {
        if (phrase == null || phrase.length() < 1)
            throw new IllegalArgumentException("message...");
        return words.parallelStream()
                .filter(e -> e.startsWith(phrase) && !e.equals(phrase))
                .sorted()
                .limit(resultLimit)
                .collect(Collectors.toSet());
    }

}

class MyMap {

    Map<Character, MyMap> childrenMap = new HashMap<>();
    char aChar;
    boolean isWord;

    public MyMap(char aChar) {
        this.aChar = aChar;
    }

    public MyMap() {
    }

    public void insert(String word) {
        if (word == null || word.isEmpty())
            return;
        char firstChar = word.charAt(0);
        MyMap child = childrenMap.get(firstChar);
        if (child == null) {
            child = new MyMap(firstChar);
            childrenMap.put(firstChar, child);
        }

        if (word.length() > 1)
            child.insert(word.substring(1));
        else
            child.isWord = true;
    }
}

