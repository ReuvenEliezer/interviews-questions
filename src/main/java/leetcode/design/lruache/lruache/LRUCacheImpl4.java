package leetcode.design.lruache.lruache;

import org.apache.commons.collections4.map.LinkedMap;

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
     * multi thearded 2 options:
     * 1. נעילה ברמת הK שנועל גם את 2 האיברים הסמוכים שלו
     * 2. או THREAD נפרד שמבצע את PUT
     * שם את המידע בQUEUE ויש THREAD שרץ ברקע וקורא משם
     * והתשובה חוזרת מיד ליוזר GET של GET (הפנימי)
     */

//   private DoubleLinkedHashMap<K,V> map = new LinkedHashMap()

    private int maxEntries;

    public LRUCacheImpl4(int maxEntries) {
        this.maxEntries = maxEntries;
    }

    @Override
    public V get(Object key) {
        V v = super.get(key);
        if (v != null) {
            put((K) key, v);
        }
        return v;
    }

    @Override
    public V put(K key, V value) {
        super.remove(key);
        V v = super.put(key, value);
        if (super.size() > maxEntries) {
            Iterator<Map.Entry<K, V>> iterator = super.entrySet().iterator();
            if (iterator.hasNext()) {
                Map.Entry<K, V> next = iterator.next();
                iterator.remove();
            }
        }
        return v;
    }

}
