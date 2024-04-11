package com.interviews.questions.lruache.lruache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacheImpl3<K, V> extends LinkedHashMap<K, V> {
    private LinkedHashMap<K, V> map;

    public LRUCacheImpl3(final int capacity) {
        /**
         *
         accessOrder – the ordering mode - true for access-order, false for insertion-order
         */
        map = new LinkedHashMap<>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return super.size() > capacity;
            }
        };
    }

    @Override
    public V get(Object key) {
        //When put updates, it does not update the index, so remove and add back
        V val = map.get(key);
        if (val != null) {
            put((K) key, val);
        }
        return val;
    }

    @Override
    public V put(K key, V value) {
//        map.remove(key);
        return map.put(key, value);
    }
}