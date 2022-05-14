package threads;

import lombok.SneakyThrows;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class BlockingQueue<T> {
    /**
     * https://www.geeksforgeeks.org/blockingqueue-interface-in-java/
     */

    private List<T> list = new LinkedList<>();

    public synchronized void put(T t) {
        System.out.println("put value: " + t);
        list.add(t);
        System.out.println("notifyAll");
        notifyAll(); // u can to mark if using wait(timeout) with a timeout value
    }

    public synchronized T get() throws InterruptedException {
        System.out.println("getting...");
        while (list.isEmpty()) {
            System.out.println("waiting...");
            wait();
//            wait(300);
        }
        T value = list.remove(0);
        System.out.println("get value: " + value);
        return value;
    }

    @Test
    public void test() throws InterruptedException, ExecutionException {
        ExecutorService executorService = new ScheduledThreadPoolExecutor(2);
        BlockingQueue<Integer> blockingQueue = new BlockingQueue();
        Future<?> get = executorService.submit(blockingQueue::get);
        Thread.sleep(5000);
        Future<?> put = executorService.submit(() -> blockingQueue.put(1));
        Object o = get.get();
        put.get();
    }

}
