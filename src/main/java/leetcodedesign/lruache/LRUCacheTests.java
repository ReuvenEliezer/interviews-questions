package leetcodedesign.lruache;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;

public class LRUCacheTests {

    @Test
    public void test() {
        LRUCache lruCache = new LRUCacheImpl2(2);
//        LRUCache lruCache = new LRUCacheImpl(2);
        lruCache.put(1, 1); // cache is {1=1}
        lruCache.put(2, 2); // cache is {1=1, 2=2}
        Assert.assertEquals(1, lruCache.get(1));
        lruCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
        Assert.assertEquals(-1, lruCache.get(2));    // returns -1 (not found)
        lruCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
        Assert.assertEquals(-1, lruCache.get(1));    // return -1 (not found)
        Assert.assertEquals(3, lruCache.get(3));    // return 3
        Assert.assertEquals(4, lruCache.get(4));    // return 4
    }

    @Test
    public void test1() {
        LRUCache lruCache = new LRUCacheImpl2(2);
//        LRUCache lruCache = new LRUCacheImpl(2);
        Assert.assertEquals(-1, lruCache.get(2));
        lruCache.put(2, 6);
        Assert.assertEquals(-1, lruCache.get(1));
        lruCache.put(1, 5);
        lruCache.put(1, 2);
        Assert.assertEquals(2, lruCache.get(1));
        Assert.assertEquals(6, lruCache.get(2));
    }

    @Test
    public void test2() {
        LRUCache lruCache = new LRUCacheImpl2(2);
//        LRUCache lruCache = new LRUCacheImpl(2);
        lruCache.put(2, 1);
        lruCache.put(1, 1);
        lruCache.put(2, 3);
        lruCache.put(4, 1);
        Assert.assertEquals(-1, lruCache.get(1));
        Assert.assertEquals(3, lruCache.get(2));
    }

    @Test
    public void queueTest() {
        PriorityBlockingQueue<RecentlyValueUsed> queue =
                new PriorityBlockingQueue(2, Comparator.comparing(RecentlyValueUsed::getLocalDateTime).reversed());

        RecentlyValueUsed recentlyUsedValue1 = new RecentlyValueUsed(1, 1);
        queue.add(recentlyUsedValue1);
        RecentlyValueUsed recentlyUsedValue2 = new RecentlyValueUsed(2, 2);
        queue.add(recentlyUsedValue2);
        recentlyUsedValue2.setLocalDateTime(LocalDateTime.now());

        queue.remove(recentlyUsedValue2);
        queue.add(recentlyUsedValue2);

        Iterator<RecentlyValueUsed> iterator = queue.iterator();
        while (iterator.hasNext()) {
            RecentlyValueUsed next = iterator.next();
            System.out.println(next);
        }
    }
}
