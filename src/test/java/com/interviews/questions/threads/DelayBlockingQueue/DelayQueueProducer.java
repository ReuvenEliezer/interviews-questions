package com.interviews.questions.threads.DelayBlockingQueue;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

@AllArgsConstructor
public class DelayQueueProducer implements Runnable {

    private static final Logger logger = LogManager.getLogger(DelayQueueProducer.class);

    private BlockingQueue<com.interviews.questions.threads.DelayBlockingQueue.DelayObject> queue;
    private Integer numberOfElementsToProduce;
    private Integer delayOfEachProducedMessageMilliseconds;

    @Override
    public void run() {
        for (int i = 0; i < numberOfElementsToProduce; i++) {
            com.interviews.questions.threads.DelayBlockingQueue.DelayObject delayObject = new DelayObject(UUID.randomUUID().toString(), delayOfEachProducedMessageMilliseconds);
            logger.info("Put object: {}", delayObject);
            try {
                queue.put(delayObject);
                Thread.sleep(delayOfEachProducedMessageMilliseconds);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}