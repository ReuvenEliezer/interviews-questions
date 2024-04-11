package com.interviews.questions.lruache.lruache;

import org.apache.commons.collections4.map.LinkedMap;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacheImpl4<K, V> extends LinkedHashMap<K, V> {//implements LRUCache<K, V> {

    /**
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
     *      1. Thread1 -> get -> remove -> before Thread1 do the put action, the Thread2 -> get the near element of Thread1
     * 1. נעילה ברמת הK שנועל גם את 2 האיברים הסמוכים שלו
     * 2. או THREAD נפרד שמבצע את PUT
     * שם את המידע בQUEUE ויש THREAD שרץ ברקע וקורא מהQUEUE ומבצע את הREMOVE & PUT
     * והתשובה חוזרת מיד ליוזר GET של GET (הפנימי)
     *  מימוש זה מניח שהסדר בין 2 Threads סמוכים לא יהיה מובטח בסדר במפה, היות ויכול להיות מצב שT1 מבצע GET REMOVE
     *  ואז T2 מבצע GET REMOVE PUT ואז T1 מקבל שוב זמן CPU ומבצע את הPUT (הפנימי)   כך שהוא האיבר האחרון עכשיו במפה במקום האיבר שT2 עשה עליו GET. מה שאומר שהוא יימחק מהמפה לפני הGETשל האיבר של T1 במקום אחריו (כי הוא התחיל אחריו
     */

//   private DoubleLinkedHashMap<K,V> map = new LinkedHashMap()


    /**
     * https://www.geeksforgeeks.org/design-a-data-structure-for-lru-cache/?ref=lbp
     */
    private int maxEntries;

    public LRUCacheImpl4(final int maxEntries) {
        this.maxEntries = maxEntries;
    }

    @Override
    public V get(Object key) {
        V v = super.get(key);
        if (v != null) {
            this.put((K) key, v);
        }
        return v;
    }

    @Override
    public V put(K key, V value) {
        super.remove(key);
        V v = super.put(key, value);
        if (super.size() > maxEntries) {
            removeEldestEntry();
        }
        return v;
    }

    private void removeEldestEntry() {
        Iterator<Map.Entry<K, V>> iterator = super.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<K, V> next = iterator.next();
            iterator.remove();
        }
    }

}
