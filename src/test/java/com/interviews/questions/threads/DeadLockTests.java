package com.interviews.questions.threads;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runners.model.TestTimedOutException;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeadLockTests {
    //    https://javarevisited.blogspot.com/2018/08/how-to-avoid-deadlock-in-java-threads.html
    @Test
    public void deadLockTest() throws InterruptedException {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
        executorService.scheduleWithFixedDelay(() -> {
            assertTrue(detectDeadlock());
        }, 0, 1, TimeUnit.SECONDS);

        DeadLock deadLock = new DeadLock();

        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                deadLock.method1();
                System.out.println(" deadLock.method1()");
            });
        }

        executorService.submit(() -> {
            deadLock.method2();
            System.out.println(" deadLock.method2()");
        });
        Thread.sleep(10000 + 10);
        executorService.shutdownNow();
    }

    private static boolean detectDeadlock() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] threadIds = threadMXBean.findDeadlockedThreads();

        if (threadIds == null || threadIds.length == 0) {
            return false;
        }
        System.out.println("Deadlock detected:");
        ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadIds);
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println(threadInfo.toString());
        }
        return true;
    }

    @Test
    public void deadLockFixedTest() throws InterruptedException {

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
        executorService.scheduleWithFixedDelay(() -> {
            assertFalse(detectDeadlock());
        }, 0, 10, TimeUnit.SECONDS);

        DeadLockFixed deadLockFixed = new DeadLockFixed();

        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                assertFalse(detectDeadlock());
                deadLockFixed.method1();
                System.out.println(" deadLock.method1()");
            });
        }

        executorService.submit(() -> {
            deadLockFixed.method2();
            System.out.println(" deadLock.method2()");
        });
        Thread.sleep(10000 + 10);
        executorService.shutdownNow();
//        executorService.awaitTermination(60, TimeUnit.SECONDS);
    }
}
