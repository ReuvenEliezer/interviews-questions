package com.interviews.questions.threads.DelayBlockingQueue;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.concurrent.*;

public class DelayBlockingQueueTest {

    /**
     * https://www.baeldung.com/java-delay-queue
     * @throws InterruptedException
     */

    @Test
    public void givenDelayQueue_whenProduceElement_thenShouldConsumeAfterGivenDelay() throws InterruptedException {
        // given
        ExecutorService executor = Executors.newFixedThreadPool(2);


        BlockingQueue<DelayObject> queue = new DelayQueue<>();
        int numberOfElementsToProduce = 2;
        int delayOfEachProducedMessageMilliseconds = 1500;
        com.interviews.questions.threads.DelayBlockingQueue.DelayQueueProducer producer = new DelayQueueProducer(queue, numberOfElementsToProduce, delayOfEachProducedMessageMilliseconds);
        com.interviews.questions.threads.DelayBlockingQueue.DelayQueueConsumer consumer = new DelayQueueConsumer(queue, numberOfElementsToProduce);

        // when
        executor.submit(producer);
        executor.submit(consumer);

        // then
        executor.awaitTermination(delayOfEachProducedMessageMilliseconds * numberOfElementsToProduce + 100, TimeUnit.MILLISECONDS);
        executor.shutdown();

        Assertions.assertThat(consumer.getNumberOfConsumedElements().get()).isEqualTo(numberOfElementsToProduce);
    }
}
