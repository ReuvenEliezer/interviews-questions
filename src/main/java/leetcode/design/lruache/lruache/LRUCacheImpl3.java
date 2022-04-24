package leetcode.design.lruache.lruache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacheImpl3<K, V> extends LinkedHashMap<K, V> {
    private LinkedHashMap<K, V> map;

    public LRUCacheImpl3(int capacity) {
        this.map = new LinkedHashMap<>() {
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > capacity;
            }
        };
    }

    @Override
    public V get(Object key) {
        if (!map.containsKey(key))
            return null;
        //When put updates, it does not update the index, so remove and add back
        V val = map.get(key);
        put((K) key, val);
        return val;
    }

    @Override
    public V put(K key, V value) {
        map.remove(key);
        return map.put(key, value);
    }
}