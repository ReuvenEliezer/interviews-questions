package com.interviews.questions;

import org.junit.Ignore;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class Earnix {

    @Test
    @Ignore
    public void setAllInO1Test() {
        //write a collection utils that support the following condition:
        //set will be set the given value into the given index
        //setAll will be set the given value into all indexes
        //get func will be retrieve the value in o(1) complexity
        MyCollection<String> myCollection = new MyCollection<>();

        assertThat(myCollection.get(0)).isNull();

        myCollection.set(0, "a");

        assertThat(myCollection.get(0)).isEqualTo("a");
        assertThat(myCollection.get(1)).isNull();

        myCollection.setAll("b");
        assertThat(myCollection.get(0)).isEqualTo("b");
        assertThat(myCollection.get(1)).isEqualTo("b");
        assertThat(myCollection.get(2)).isEqualTo("b");

        myCollection.set(1, "c");
        assertThat(myCollection.get(0)).isEqualTo("b");
        assertThat(myCollection.get(1)).isEqualTo("c");
        assertThat(myCollection.get(2)).isEqualTo("b");

        for (int i = 0; i < 5; i++) {
            System.out.println(myCollection.get(i));
        }

    }


    static class MyCollection<T> {

        private Map<Integer, ValueTime<T>> indexToElementValueMap;
        private ValueTime<T> valueTime = new ValueTime<>(null, LocalDateTime.now());

        public MyCollection() {
            this.indexToElementValueMap = new HashMap<>();
        }

        public MyCollection(int initialCapacity) {
            this.indexToElementValueMap = new HashMap<>(initialCapacity);
        }

        public T set(Integer key, T value) {
            ValueTime<T> prevValue = indexToElementValueMap.put(key, new ValueTime<>(value, LocalDateTime.now()));
            return prevValue == null ? null : prevValue.element;
        }

        public void setAll(T value) {
            this.valueTime = new ValueTime<>(value, LocalDateTime.now());
        }

        public T get(Integer key) {
            ValueTime<T> valueTime = indexToElementValueMap.get(key);
            if (valueTime == null) {
                return this.valueTime.element;
            } else if (this.valueTime.element == null) {
                return valueTime.element;
            } else {
                LocalDateTime updateTime = valueTime.currentTime();
                LocalDateTime globalUpdateTime = this.valueTime.currentTime;
                System.out.println("updateTime: " + updateTime);
                System.out.println("globalUpdateTime: " + globalUpdateTime);
                return updateTime.isAfter(globalUpdateTime) ? valueTime.element : this.valueTime.element;


//            return updateTime.toInstant(ZoneOffset.UTC).getNano() > globalUpdateTime.toInstant(ZoneOffset.UTC).getNano() ? tValueTime.element : valueTime.element;
//            Duration deltaTime = Duration.between(globalUpdateTime, updateTime);
//            return deltaTime.getNano() > 0 ? tValueTime.element : valueTime.element;
            }
        }
    }

    record ValueTime<T>(T element, LocalDateTime currentTime) {
    }

}
