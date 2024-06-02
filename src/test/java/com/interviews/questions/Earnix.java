package com.interviews.questions;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class Earnix {

    @Test
    public void setAllInO1Test() throws InterruptedException {
        //write a collection utils that support the following condition:
        //set will be set the given value into the given index
        //setAll will be set the given value into all indexes
        //get func will be retrieve the value in o(1) complexity
        MyCollection<Integer, String> myCollection = new MyCollection<>();

        assertThat(myCollection.get(0)).isNull();

        Thread.sleep(1);
        myCollection.set(0, "a");

        assertThat(myCollection.get(0)).isEqualTo("a");
        assertThat(myCollection.get(1)).isNull();

        Thread.sleep(1);
        myCollection.setAll("b");

        assertThat(myCollection.get(0)).isEqualTo("b");
        assertThat(myCollection.get(1)).isEqualTo("b");
        assertThat(myCollection.get(2)).isEqualTo("b");

        Thread.sleep(1);
        myCollection.set(1, "c");
        assertThat(myCollection.get(0)).isEqualTo("b");
        assertThat(myCollection.get(1)).isEqualTo("c");
        assertThat(myCollection.get(2)).isEqualTo("b");

        for (int i = 0; i < 5; i++) {
            System.out.println(myCollection.get(i));
        }

    }

    @Test
    public void imprevaTest() throws InterruptedException {
        MyCollection<Integer, Integer> mapCom = new MyCollection<>();

        mapCom.set(6, 20);
        Thread.sleep(1);
        mapCom.set(14, 25);
        Thread.sleep(1);
        mapCom.set(6, 25);
        Thread.sleep(1);
        mapCom.setAll(1);
        Thread.sleep(1);
        mapCom.setAll(17);
        Thread.sleep(1);
        mapCom.set(14, 13);

        assertThat(mapCom.get(14)).isEqualTo(13);
        assertThat(mapCom.get(6)).isEqualTo(17);
        assertThat(mapCom.get(3)).isEqualTo(17);
    }


    static class MyCollection<K, V> {

        private Map<K, ValueTime<V>> map;
        private ValueTime<V> globalValueTime = new ValueTime<>(null, LocalDateTime.now());

        public MyCollection() {
            this.map = new HashMap<>();
        }

        public MyCollection(int initialCapacity) {
            this.map = new HashMap<>(initialCapacity);
        }

        public V set(K key, V value) {
            ValueTime<V> prevValue = map.put(key, new ValueTime<>(value, LocalDateTime.now()));
            return prevValue == null ? null : prevValue.value;
        }

        public void setAll(V value) {
            this.globalValueTime = new ValueTime<>(value, LocalDateTime.now());
        }

        public V get(K key) {
            ValueTime<V> valueTime = map.get(key);

            if (valueTime != null) {
                if (valueTime.currentTime.isAfter(this.globalValueTime.currentTime)) {
                    return valueTime.value;
                }
            }
            return this.globalValueTime.value();
        }

    }

    record ValueTime<V>(V value, LocalDateTime currentTime) {
    }

}
