import org.junit.Test;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class HashMapConcurrency {
    @Test
    public void printArrayTest() {
        Employee employee = new Employee(1, "d", 10, LocalDateTime.now());
        employee.setList(Arrays.asList("a", "b"));
       System.out.println(employee.toString());
    }
    @Test
    public void hashMapConcurrency_Test() throws InterruptedException {
        Map<String, Integer> cricketTeamScore = new HashMap<>();
        cricketTeamScore.put("India", 0);

        // Create an ExecutorService with a Thread Pool of size 10
        ExecutorService executorService = Executors.newFixedThreadPool(1000);

        // Create a Runnable object that increments the value associated with a given key in the HashMap by one.
        Runnable task = () -> {
            incrementTeamScore(cricketTeamScore, "India");
        };

        // Submit the Runnable object to the executorService 100 times to test concurrent modifications
        for (int i = 0; i < 1000; i++) {
            executorService.submit(task);
        }

        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);

        System.out.println("Final Score of Team India : " + cricketTeamScore.get("India"));
    }

    private static void incrementTeamScore(Map<String, Integer> cricketTeamScore, String team) {
        Integer score = cricketTeamScore.get(team);
        cricketTeamScore.put(team, score + 1);
    }

    @Test
    public void forEachVsStreamVsParallelStream_Test() {
        IntStream range = IntStream.range(Integer.MIN_VALUE, Integer.MAX_VALUE);
        StopWatch stopWatch = new StopWatch();

        stopWatch.start("for each");
        int forEachResult = 0;
        for (int i = Integer.MIN_VALUE; i < Integer.MAX_VALUE; i++) {
            if (i % 15 == 0)
                forEachResult++;
        }
        stopWatch.stop();


        stopWatch.start("stream");
        long streamResult = range
                .filter(v -> (v % 15 == 0))
                .count();
        stopWatch.stop();


        range = IntStream.range(Integer.MIN_VALUE, Integer.MAX_VALUE);
        stopWatch.start("parallel stream");
        long parallelStreamResult = range
                .parallel()
                .filter(v -> (v % 15 == 0))
                .count();
        stopWatch.stop();

        System.out.println(String.format("forEachResult: %s%s" +
                        "parallelStreamResult: %s%s" +
                        "streamResult: %s%s",
                forEachResult, System.lineSeparator(),
                parallelStreamResult, System.lineSeparator(),
                streamResult, System.lineSeparator()));

        System.out.println("prettyPrint: " + stopWatch.prettyPrint());
        System.out.println("Time Elapsed: " + stopWatch.getTotalTimeSeconds());
    }


    @Test
    public void concurrentHashMap_test() {
        testIt(new HashMap<>());
        testIt(new ConcurrentHashMap<>());
    }

    @Test(expected = OutOfMemoryError.class)
    public void outOfMemoryError_test() {
        long[][] ary = new long[Integer.MAX_VALUE][Integer.MAX_VALUE];
    }

    private static void testIt(Map<Integer, Integer> map) {
        IntStream.range(0, 2000)
                .parallel()
                .forEach(i -> map.put(i, -1));
        System.out.println(map.size());
    }

    @Test
    public void concurrentHashMap(){
        ConcurrentHashMap<Integer,String> concurrentHashMap=new ConcurrentHashMap();
        String s = concurrentHashMap.putIfAbsent(1, "1");
        String s1 = concurrentHashMap.putIfAbsent(1, "1");
    }

    @Test
    public void hashSet_test() {
        testIt(new HashSet<>());
    }

    @Test
    public void list_test() {
        testIt(new ArrayList<>());
    }

    private static void testIt(Collection<Integer> list) {
        IntStream.range(0, 2000).parallel().forEach(i -> list.add(i));
        System.out.println(list.size());
    }



}
