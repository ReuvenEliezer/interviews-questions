package leetcode.design.lruache.lruache;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LRUCacheImpl5<K, V> extends LinkedHashMap<K, V> {//implements LRUCache<K, V> {
    /**
     * https://www.geeksforgeeks.org/design-a-data-structure-for-lru-cache/?ref=lbp
     * https://aaronice.gitbook.io/lintcode/data_structure/lru_cache
     * <p>
     * <p>
     * /**
     * LRU cache with o1
     * get
     * put
     * <p>
     * <p>
     * DoubleLinkedHashMap<K,V> map = new LinkedHashMap()
     * <p>
     * V get(K k){
     * 1. V v = get(k)
     * 2. put(k,v)
     * return v;
     * }
     * void put (K, k, V v ){
     * 1. remove (k)
     * 2. super.put(k,v)
     * }
     * <p>
     * multi threaded 2 options:
     * בגלל שהמימוש הוא LINKEDHASHMAP יכול להיווצר מצב של RACE CONDITION:
     * 1. Thread1 -> get -> remove -> before Thread1 do the put action, the Thread2 -> get the near element of Thread1
     * 1. נעילה ברמת הK שנועל גם את 2 האיברים הסמוכים שלו
     * 2. או THREAD נפרד שמבצע את PUT
     * שם את המידע בQUEUE ויש THREAD שרץ ברקע וקורא מהQUEUE ומבצע את הREMOVE & PUT
     * והתשובה חוזרת מיד ליוזר GET של GET (הפנימי)
     * מימוש זה מניח שהסדר בין 2 Threads סמוכים לא יהיה מובטח בסדר במפה, היות ויכול להיות מצב שT1 מבצע GET REMOVE
     * ואז T2 מבצע GET REMOVE PUT ואז T1 מקבל שוב זמן CPU ומבצע את הPUT (הפנימי)   כך שהוא האיבר האחרון עכשיו במפה במקום האיבר שT2 עשה עליו GET. מה שאומר שהוא יימחק מהמפה לפני הGETשל האיבר של T1 במקום אחריו (כי הוא התחיל אחריו
     */

    private Map<K, Lock> mapLock = new ConcurrentHashMap<>();
    private final Map<K, Node> map = new ConcurrentHashMap<>();
    private final int capacity;
    private int count;
    private final Node head;
    private final Node tail;

    @AllArgsConstructor
    @Getter
    class Lock {
        K current;
        K prev;
        K next;
    }

    public LRUCacheImpl5(final int capacity) {
        //        super(Collections.synchronizedMap(new LinkedHashMap()));
        this.capacity = capacity;
        head = new Node(null, null);
        tail = new Node(null, null);
        head.next = tail;
        tail.pre = head;
    }

    @Override
    public V get(Object key) {
        Node node = map.get(key);
        if (node != null) {
            V result = node.value;
            deleteNode(node);
            addToHead(node);
            return result;
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        V prevValue = null;
        Node node = map.get(key);
        if (node != null) {
            prevValue = node.value;
            node.value = value;
            deleteNode(node);
        } else {
            node = new Node(key, value);
            map.put(key, node);
            if (count < capacity) {
                count++;
            } else {
                map.remove(tail.pre.key);
                deleteNode(tail.pre);
            }
        }
        addToHead(node);
        return prevValue;
    }

    public void deleteNode(Node node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }

    public void addToHead(Node node) {
        node.next = head.next;
        node.next.pre = node;
        node.pre = head;
        head.next = node;
    }

    class Node {
        K key;
        V value;
        Node pre;
        Node next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
