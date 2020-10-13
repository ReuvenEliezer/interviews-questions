package threads;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DeadLockTests {
//    https://javarevisited.blogspot.com/2018/08/how-to-avoid-deadlock-in-java-threads.html
    @Test
    public void deadLockTest() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        DeadLock deadLock = new DeadLock();

        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                deadLock.method1();
                System.out.println(" deadLock.method1()");
            });
        }

        System.out.println("deadLock.method2");
        deadLock.method2();
        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);
    }
    @Test
    public void deadLockFixedTest() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        DeadLockFixed deadLockFixed = new DeadLockFixed();

        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                deadLockFixed.method1();
                System.out.println(" deadLock.method1()");
            });
        }

        System.out.println("deadLock.method2");
        deadLockFixed.method2();
        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);
    }
}
