package leetcode.design.lruache.lruache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacheImpl implements LRUCache {
    private LinkedHashMap<Integer, Integer> map;

    public LRUCacheImpl(int capacity) {
        this.map = new LinkedHashMap<Integer, Integer>() {
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > capacity;
            }
        };
    }

    @Override
    public int get(int key) {
        if (!map.containsKey(key))
            return -1;

        int val = map.get(key);
        //When put updates, it does not update the index, so remove and add back
        put(key, val);
        return val;
    }

    @Override
    public void put(int key, int value) {
        map.remove(key);
        map.put(key, value);
    }
}