package com.interviews.questions.threads;

import java.util.concurrent.*;

public class Throttling {
    // The maximum number of tasks that can be executed
    // concurrently
    private static final int MAX_TASKS = 3;
    // The maximum number of tasks that can be queued for
    // execution
    private static final int MAX_QUEUE = 3;

    public static void main(String[] args) throws InterruptedException {
        // Create a ThreadPoolExecutor with the desired
        // settings
        ExecutorService executor = new ThreadPoolExecutor(
                MAX_TASKS, MAX_TASKS, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(MAX_QUEUE));
        // Create a Semaphore with the desired maximum
        // number of permits
        Semaphore semaphore = new Semaphore(MAX_TASKS);
        // Submit tasks to the executor, acquiring a permit
        // from the semaphore before each submission
        for (int i = 0; i < 100; i++) {
            semaphore.acquire();
            int finalI = i;
            executor.submit(() -> {
                try {
                    System.out.println(finalI +" starting");
                    Thread.sleep(1000);
                    // Do some work here
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    // Release the permit when the task
                    // is finished
                    semaphore.release();
                    System.out.println(finalI +" finished");
                }
            });
        }
    }
}
