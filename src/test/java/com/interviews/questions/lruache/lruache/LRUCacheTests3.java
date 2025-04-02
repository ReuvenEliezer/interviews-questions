package com.interviews.questions.lruache.lruache;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.PriorityBlockingQueue;

import static org.junit.Assert.*;

public class LRUCacheTests3 {

    @Test
    public void test() {
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();
        LRUCacheImpl3 lruCache = new LRUCacheImpl3<>(2);

//        LRUCache lruCache = new LRUCacheImpl2(2);
//        LRUCache lruCache = new LRUCacheImpl(2);
        lruCache.put(1, 1); // cache is {1=1}
        lruCache.put(2, 2); // cache is {1=1, 2=2}
        assertEquals(1, lruCache.get(1));
        lruCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
        assertEquals(null, lruCache.get(2));    // returns null (not found)
        lruCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
        assertEquals(null, lruCache.get(1));    // return null (not found)
        assertEquals(3, lruCache.get(3));    // return 3
        assertEquals(4, lruCache.get(4));    // return 4
    }

    @Test
    public void test1() {
        LRUCacheImpl3<Integer, Integer> lruCache = new LRUCacheImpl3<>(2);
//        LRUCache lruCache = new LRUCacheImpl2(2);
//        LRUCache lruCache = new LRUCacheImpl(2);
        assertEquals(null, lruCache.get(2));
        lruCache.put(2, 6);
        assertEquals(null, lruCache.get(1));
        lruCache.put(1, 5);
        lruCache.put(1, 2);
        assertEquals(2, lruCache.get(1).intValue());
        assertEquals(6, lruCache.get(2).intValue());
    }

    @Test
    public void test2() {
        LRUCacheImpl3<Integer, Integer> lruCache = new LRUCacheImpl3<>(2);
//        LRUCache lruCache = new LRUCacheImpl2(2);
//        LRUCache lruCache = new LRUCacheImpl(2);
        lruCache.put(2, 1);
        lruCache.put(1, 1);
        lruCache.put(2, 3);
        lruCache.put(4, 1);
        assertEquals(null, lruCache.get(1));
        assertEquals(3, lruCache.get(2).intValue());
    }

    @Test
    public void queueTest() {
        PriorityBlockingQueue<RecentlyValueUsed> queue =
                new PriorityBlockingQueue<>(2, Comparator.comparing(RecentlyValueUsed::getLocalDateTime).reversed());

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
