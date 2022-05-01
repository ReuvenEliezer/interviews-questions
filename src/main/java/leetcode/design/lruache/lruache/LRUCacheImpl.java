package leetcode.design.lruache.lruache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacheImpl implements LRUCache<Integer, Integer> {
    private LinkedHashMap<Integer, Integer> map;

    public LRUCacheImpl(int capacity) {
        this.map = new LinkedHashMap<Integer, Integer>() {
            @Override
            public boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > capacity;
            }
        };
    }

    @Override
    public Integer get(Integer key) {
        if (!map.containsKey(key))
            return -1;

        int val = map.get(key);
        //When put updates, it does not update the index, so remove and add back
        this.put(key, val);
        return val;
    }

    @Override
    public void put(Integer key, Integer value) {
        map.remove(key);
        map.put(key, value);
    }
}