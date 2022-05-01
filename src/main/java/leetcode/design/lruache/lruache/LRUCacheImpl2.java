package leetcode.design.lruache.lruache;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class LRUCacheImpl2 implements LRUCache<Integer, Integer> {
    private Map<Integer, RecentlyValueUsed> map;
    private final int maxSize;
    private PriorityQueue<RecentlyValueUsed> queue;

    public LRUCacheImpl2(int capacity) {
        map = new HashMap<>(capacity);
        maxSize = capacity;
        queue = new PriorityQueue(capacity, Comparator.comparing(RecentlyValueUsed::getLocalDateTime));
    }

    @Override
    public Integer get(Integer key){
        RecentlyValueUsed recentlyUsedValue = map.get(key);
        if (recentlyUsedValue == null)
            return -1;
        queue.remove(recentlyUsedValue);
        recentlyUsedValue.setLocalDateTime(LocalDateTime.now());
        queue.add(recentlyUsedValue);
        return recentlyUsedValue.getValue();
    }

    @Override
    public void put(Integer key, Integer value)  {

        if (map.size() == maxSize && !map.containsKey(key)) {
            RecentlyValueUsed poll = queue.remove();
            int toRemove = poll.getKey();
            map.remove(toRemove);
        } else {
            RecentlyValueUsed recentlyValueUsed = map.get(key);
            if (recentlyValueUsed != null) {
                queue.remove(recentlyValueUsed);
            }
        }
        RecentlyValueUsed recentlyUsedValue = new RecentlyValueUsed(key, value);
        queue.add(recentlyUsedValue);
        map.put(key, recentlyUsedValue);
    }


}
