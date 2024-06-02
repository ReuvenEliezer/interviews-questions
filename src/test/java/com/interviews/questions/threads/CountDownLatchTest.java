package com.interviews.questions.threads;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CountDownLatchTest {

    //https://www.baeldung.com/java-countdown-latch
    @Test
    public void whenParallelProcessing_thenMainThreadWillBlockUntilCompletion() throws InterruptedException {

        List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch countDownLatch = new CountDownLatch(5);
        List<Thread> workers = Stream
                .generate(() -> new Thread(new Worker(outputScraper, countDownLatch)))
//                .limit(10)
                .limit(5)
                .toList();

        workers.forEach(Thread::start);
        countDownLatch.await(3, TimeUnit.SECONDS);
        outputScraper.add("Latch released");

        Assert.assertEquals(outputScraper,
                Arrays.asList(
                        "Counted down",
                        "Counted down",
                        "Counted down",
                        "Counted down",
                        "Counted down",
                        "Latch released"
                ));
    }

    @Test
    public void whenDoingLotsOfThreadsInParallel_thenStartThemAtTheSameTime() throws InterruptedException {

        List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch readyThreadCounter = new CountDownLatch(5);
        CountDownLatch callingThreadBlocker = new CountDownLatch(1);
        CountDownLatch completedThreadCounter = new CountDownLatch(5);
        List<Thread> workers = Stream
                .generate(() -> new Thread(new WaitingWorker(
                        outputScraper, readyThreadCounter, callingThreadBlocker, completedThreadCounter)))
                .limit(5)
                .toList();

        workers.forEach(Thread::start);
        readyThreadCounter.await(3, TimeUnit.SECONDS);
        outputScraper.add("Workers ready");
        callingThreadBlocker.countDown();
        completedThreadCounter.await(3, TimeUnit.SECONDS);
        outputScraper.add("Workers complete");

        Assert.assertEquals(outputScraper,
                Arrays.asList(
                        "Workers ready",
                        "Counted down",
                        "Counted down",
                        "Counted down",
                        "Counted down",
                        "Counted down",
                        "Workers complete"
                ));
    }

    class Worker implements Runnable {
        private List<String> outputScraper;
        private CountDownLatch countDownLatch;

        public Worker(List<String> outputScraper, CountDownLatch countDownLatch) {
            this.outputScraper = outputScraper;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            doSomeWork();
            outputScraper.add("Counted down");
            countDownLatch.countDown();
        }


    }

    private void doSomeWork() {
        System.out.println("doSomeWork");
    }

    class WaitingWorker implements Runnable {

        private List<String> outputScraper;
        private CountDownLatch readyThreadCounter;
        private CountDownLatch callingThreadBlocker;
        private CountDownLatch completedThreadCounter;

        public WaitingWorker(
                List<String> outputScraper,
                CountDownLatch readyThreadCounter,
                CountDownLatch callingThreadBlocker,
                CountDownLatch completedThreadCounter) {

            this.outputScraper = outputScraper;
            this.readyThreadCounter = readyThreadCounter;
            this.callingThreadBlocker = callingThreadBlocker;
            this.completedThreadCounter = completedThreadCounter;
        }

        @Override
        public void run() {
            readyThreadCounter.countDown();
            try {
                callingThreadBlocker.await(3, TimeUnit.SECONDS);
                doSomeWork();
                outputScraper.add("Counted down");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                completedThreadCounter.countDown();
            }
        }
    }
}
