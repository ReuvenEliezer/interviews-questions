package com.interviews.questions.lruache.trie;

import org.junit.Assert;
import org.junit.Test;

public class TrieTest {

    @Test
    public void test() {
        Trie trie = new Trie();

        trie.insert("apple");
        Assert.assertTrue(trie.search("apple"));   // returns true
        Assert.assertFalse(trie.search("app"));   // returns false
        Assert.assertTrue(trie.startsWith("app"));   // returns true
        trie.insert("app");
        Assert.assertTrue(trie.search("app"));   // returns true
    }
}
