package com.interviews.questions.threads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SynchronizedArrayListExample {

    private static int value;
    public static void main(String[] args) throws InterruptedException {
        List<Integer> safeArrayList = Collections.synchronizedList(new ArrayList<>());
        safeArrayList.add(1);
        safeArrayList.add(2);
        safeArrayList.add(3);

        // Create a thread pool of size 10
        ExecutorService executorService = Executors.newFixedThreadPool(10000);
        AtomicInteger atomicValue = new AtomicInteger();
        // Create a Runnable task that increments each element of the ArrayList by one
        Runnable task = () ->{
            incrementArrayList(safeArrayList);
            value++;
            atomicValue.getAndIncrement();
        };

        // Submit the task to the executor service 100 times.
        // All the tasks will modify the ArrayList concurrently
        for (int i = 0; i < 1000000; i++) {
            executorService.submit(task);
        }

        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);
        System.out.println(value);
        System.out.println("atomicValue: "+atomicValue);

        System.out.println(safeArrayList);
//        safeArrayList.forEach(System.out::println);
    }

    // Increment all the values in the ArrayList by one
    private static void incrementArrayList(List<Integer> safeArrayList) {
        synchronized (safeArrayList) {
            for (int i = 0; i < safeArrayList.size(); i++) {
                Integer value = safeArrayList.get(i);
                safeArrayList.set(i, value + 1);
            }
        }
    }
}