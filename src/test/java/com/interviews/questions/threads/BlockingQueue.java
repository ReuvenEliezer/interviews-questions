package com.interviews.questions.threads;

import org.junit.Test;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class BlockingQueue<T> {
    /**
     * https://www.geeksforgeeks.org/blockingqueue-interface-in-java/
     */

    private final Long limitation;
    private LinkedList<T> list = new LinkedList<>();

    public BlockingQueue() {
        limitation = null;
    }

    public BlockingQueue(long limitation) {
        this.limitation = limitation;
    }

    public synchronized void put(T t) {
        if (limitation != null && list.size() == limitation) {
            System.out.println("queue is full (limitation: " + list.size());
            throw new IllegalStateException();
        }
        System.out.println("put value: " + t);
        list.add(t);
        System.out.println("notifyAll");
        notifyAll(); // u can to mark it, if using wait(timeout) with a timeout value
    }

    public synchronized T get() throws InterruptedException {
        System.out.println("getting...");
        while (list.isEmpty()) {
            System.out.println("waiting...");
            wait();
//            wait(300);
        }
        T value = list.removeFirst();
        System.out.println("get value: " + value);
        return value;
    }

}
