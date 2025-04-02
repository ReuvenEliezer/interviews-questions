package com.interviews.questions.lruache.lruache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.collections4.map.LRUMap;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

import static org.junit.Assert.*;

public class LRUCacheTests {

    @Test
    public void test() {
        com.interviews.questions.lruache.lruache.LRUCache lruCache = new com.interviews.questions.lruache.lruache.LRUCacheImpl2(2);
//        LRUCache lruCache = new LRUCacheImpl(2);
        lruCache.put(1, 1); // cache is {1=1}
        lruCache.put(2, 2); // cache is {1=1, 2=2}
        assertEquals(1, lruCache.get(1));
        lruCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
        assertEquals(-1, lruCache.get(2));    // returns -1 (not found)
        lruCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
        assertEquals(-1, lruCache.get(1));    // return -1 (not found)
        assertEquals(3, lruCache.get(3));    // return 3
        assertEquals(4, lruCache.get(4));    // return 4
    }

    @Test
    public void test1() {
        com.interviews.questions.lruache.lruache.LRUCache lruCache = new com.interviews.questions.lruache.lruache.LRUCacheImpl2(2);
//        LRUCache lruCache = new LRUCacheImpl(2);
        assertEquals(-1, lruCache.get(2));
        lruCache.put(2, 6);
        assertEquals(-1, lruCache.get(1));
        lruCache.put(1, 5);
        lruCache.put(1, 2);
        assertEquals(2, lruCache.get(1));
        assertEquals(6, lruCache.get(2));
    }

    @Test
    public void test2() {
        LRUCache lruCache = new LRUCacheImpl2(2);
//        LRUCache lruCache = new LRUCacheImpl(2);
        lruCache.put(2, 1);
        lruCache.put(1, 1);
        lruCache.put(2, 3);
        lruCache.put(4, 1);
        assertEquals(-1, lruCache.get(1));
        assertEquals(3, lruCache.get(2));
    }

    @Test
    public void queueTest() {
        PriorityBlockingQueue<com.interviews.questions.lruache.lruache.RecentlyValueUsed> queue =
                new PriorityBlockingQueue<>(2, Comparator.comparing(com.interviews.questions.lruache.lruache.RecentlyValueUsed::getLocalDateTime).reversed());

        com.interviews.questions.lruache.lruache.RecentlyValueUsed recentlyUsedValue1 = new com.interviews.questions.lruache.lruache.RecentlyValueUsed(1, 1);
        queue.add(recentlyUsedValue1);
        com.interviews.questions.lruache.lruache.RecentlyValueUsed recentlyUsedValue2 = new com.interviews.questions.lruache.lruache.RecentlyValueUsed(2, 2);
        queue.add(recentlyUsedValue2);
        recentlyUsedValue2.setLocalDateTime(LocalDateTime.now());

        queue.remove(recentlyUsedValue2);
        queue.add(recentlyUsedValue2);

        Iterator<com.interviews.questions.lruache.lruache.RecentlyValueUsed> iterator = queue.iterator();
        while (iterator.hasNext()) {
            RecentlyValueUsed next = iterator.next();
            System.out.println(next);
        }
    }

    @Test
    public void test4() {
        com.interviews.questions.lruache.lruache.LRUCacheImpl4<Integer, Integer> lruCache = new LRUCacheImpl4<Integer, Integer>(2);
        lruCache.put(1, 1); // cache is {1=1}
        lruCache.put(2, 2); // cache is {1=1, 2=2}
        assertEquals(Integer.valueOf(1), lruCache.get(1));
        lruCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
        assertEquals(null, lruCache.get(2));    // returns -1 (not found)
        lruCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
        assertEquals(null, lruCache.get(1));    // return -1 (not found)
        assertEquals(Integer.valueOf(3), lruCache.get(3));    // return 3
        assertEquals(Integer.valueOf(4), lruCache.get(4));    // return 4
    }

    @Test
    public void test5() {
        LRUCacheImpl5<Integer, Integer> lruCache = new LRUCacheImpl5<>(2);
        lruCache.put(1, 1); // cache is {1=1}
        lruCache.put(2, 2); // cache is {1=1, 2=2}
        assertEquals(Integer.valueOf(1), lruCache.get(1));
        lruCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
        assertEquals(null, lruCache.get(2));    // returns -1 (not found)
        lruCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
        assertEquals(null, lruCache.get(1));    // return -1 (not found)
        assertEquals(Integer.valueOf(3), lruCache.get(3));    // return 3
        assertEquals(Integer.valueOf(4), lruCache.get(4));    // return 4
    }

    @Test
    public void test6() {
        LRUMap<Integer, Integer> lruCache = new LRUMap<>(2);
        lruCache.put(1, 1); // cache is {1=1}
        lruCache.put(2, 2); // cache is {1=1, 2=2}
        assertEquals(Integer.valueOf(1), lruCache.get(1));
        lruCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
        assertEquals(null, lruCache.get(2));    // returns -1 (not found)
        lruCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
        assertEquals(null, lruCache.get(1));    // return -1 (not found)
        assertEquals(Integer.valueOf(3), lruCache.get(3));    // return 3
        assertEquals(Integer.valueOf(4), lruCache.get(4));    // return 4
    }


    @Test
    public void test7() {
        Lock lock1 = new Lock(1, 2, 3);
        Lock lock2 = new Lock(1, 3, 1);
//        boolean equals = lock1.equals(lock2);
        Set<Lock> locks = new HashSet<>();
        locks.add(lock1);
        locks.add(lock2);

    }

    @AllArgsConstructor
    @Getter
    class Lock {
        Integer current;
        Integer prev;
        Integer next;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Lock lock = (Lock) o;
            return Objects.equals(current, lock.current) && Objects.equals(prev, lock.prev) && Objects.equals(next, lock.next);
        }

        @Override
        public int hashCode() {
            return Objects.hash(current, prev, next);
        }
    }
}

