import org.junit.Test;

import java.awt.*;
import java.time.Period;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class HashMapConcurrency {

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
    public void concurrentHashMap_test() {
        testIt(new HashMap<>());
        testIt(new ConcurrentHashMap<>());
    }

    private static void testIt(Map<Integer, Integer> map) {
        IntStream.range(0, 2000).parallel().forEach(i -> map.put(i, -1));
        System.out.println(map.size());
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
