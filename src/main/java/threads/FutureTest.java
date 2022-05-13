package threads;

import lombok.SneakyThrows;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FutureTest {

    ExecutorService executorService = Executors.newFixedThreadPool(10);


    @SneakyThrows
    @Test
    public void thread1IncorrectResultValuesTest() {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        int total = 10;

        List<Future<?>> exportItemLines1 = IntStream.range(0, total)
                .mapToObj(index -> executorService.submit(() -> System.out.println(String.format("%s executing task1 [%s] [%s] out of [%s]", LocalDateTime.now(), atomicInteger.getAndIncrement(), index + 1, total))))
                .collect(Collectors.toList());


//        List<Future<?>> exportItemLines2 = IntStream.range(total + 1, total * 2)
//                .mapToObj(index -> executorService.submit(() -> System.out.println(String.format("%s executing task2 [%s] [%s] out of [%s]", LocalDateTime.now(), atomicInteger.getAndIncrement(), index + 1, total))))
//                .collect(Collectors.toList());

        for (Future<?> future : exportItemLines1) {
            future.get();
        }
//        for (Future<?> future : exportItemLines2) {
//            future.get();
//        }
    }

    @SneakyThrows
    @Test
    public void thread2correctResultValuesTest() {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        int total = 10;

        List<Future<?>> exportItemLines1 = IntStream.range(0, total)
                .mapToObj(index -> {
                    int value = atomicInteger.getAndIncrement();
                    return executorService.submit(() -> System.out.println(String.format("%s executing task1 [%s] [%s] out of [%s]", LocalDateTime.now(), value, index + 1, total)));
                }).collect(Collectors.toList());

//        List<Future<?>> exportItemLines2 = IntStream.range(total , total * 2)
//                .mapToObj(index -> {
//                    int value = atomicInteger.getAndIncrement();
//                    return executorService.submit(() -> System.out.println(String.format("%s executing task2 [%s] [%s] out of [%s]", LocalDateTime.now(), value, index + 1, total)));
//                }).collect(Collectors.toList());

        for (Future<?> future : exportItemLines1) {
            future.get();
        }
//        for (Future<?> future : exportItemLines2) {
//            future.get();
//        }
    }
}
