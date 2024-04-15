package com.interviews.questions.threads;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class BlockingQueueTest {
    @Test
    public void test() throws InterruptedException, ExecutionException {
        ExecutorService executorService = new ScheduledThreadPoolExecutor(2);
        BlockingQueue<Integer> blockingQueue = new BlockingQueue<>(2);
        Future<?> future = executorService.submit(blockingQueue::get);
        Future<?> future1 = executorService.submit(() -> blockingQueue.put(1));
        future.get();
        future1.get();
    }


}
