package com.interviews.questions.threads.DelayBlockingQueue;


import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class DelayQueueConsumer implements Runnable {

    private final static Logger logger = LogManager.getLogger(DelayQueueConsumer.class);

    private final BlockingQueue<DelayObject> queue;
    private final Integer numberOfElementsToTake;
    @Getter
    private static final AtomicInteger numberOfConsumedElements = new AtomicInteger();

    public DelayQueueConsumer(BlockingQueue<com.interviews.questions.threads.DelayBlockingQueue.DelayObject> queue, Integer numberOfElementsToTake) {
        this.queue = queue;
        this.numberOfElementsToTake = numberOfElementsToTake;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfElementsToTake; i++) {
            try {
                DelayObject object = queue.take();
                numberOfConsumedElements.incrementAndGet();
                logger.info("Consumer take: " + object);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}