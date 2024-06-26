package com.interviews.questions.lruache.lruache;

public interface LRUCache<K, V> {

    V get(K key);

    void put(K key, V value);
}
