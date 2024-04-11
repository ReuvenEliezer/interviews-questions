package com.interviews.questions.threads;

public class SynchronizedCounter {
    private int count = 0;

    // Synchronized Method
    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
