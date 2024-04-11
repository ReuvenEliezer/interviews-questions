package com.interviews.questions.lruache.lruache;

import java.time.LocalDateTime;

public class RecentlyValueUsed {//} implements Comparator<LocalDateTime>{
    private int key;
    private int value;
    private LocalDateTime localDateTime;

    public RecentlyValueUsed(int key, int value) {
        this.key = key;
        this.value = value;
        this.localDateTime = LocalDateTime.now();
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public int getValue() {
        return value;
    }

//        @Override
//        public int compare(LocalDateTime o1, LocalDateTime o2) {
//            return o1.compareTo(o2);
//        }

    @Override
    public String toString() {
        return "RecentlyUsedValue{" +
                "key=" + key +
                ", value=" + value +
                ", localDateTime=" + localDateTime +
                '}';
    }
}