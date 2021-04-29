import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public class MprestTest {

    private Map<Integer, Queue<LocalDateTime>> userReqTimeMap = new ConcurrentHashMap<>();
    private Duration timeInterval = Duration.ofSeconds(1);
    private int totalAllowReqInIntervalTime = 10;

    @Test
    public void yad2AttackTest() throws InterruptedException {
        /**
         * suppose we are having an attack on yad2.
         * we want to throw away ant user who attempts to connect to yad2 at least 10 times in one second.
         * you have an interface that contains a function. that function gets a user and a URL as its parameters.
         * write your code.
         */
        for (int i = 0; i < 10; i++) {
            Assert.assertTrue(connectRequest(1, "URL"));
        }
        Assert.assertFalse(connectRequest(1, "URL"));
        Assert.assertFalse(connectRequest(1, "URL"));
        Assert.assertTrue(connectRequest(2, "URL"));
        Thread.sleep(Duration.ofSeconds(1).toMillis());
        for (int i = 0; i < 10; i++) {
            Assert.assertTrue(connectRequest(2, "URL"));
        }
        Assert.assertFalse(connectRequest(2, "URL"));
        Thread.sleep(Duration.ofMillis(500).toMillis());
        Assert.assertFalse(connectRequest(2, "URL"));
        Thread.sleep(Duration.ofMillis(500).toMillis());
        Assert.assertTrue(connectRequest(2, "URL"));
    }

    @Test
    public void priorityBlockingQueueTest() {
        Queue<LocalDateTime> localDateTimesQueue = new PriorityBlockingQueue<>();
        localDateTimesQueue.add(LocalDateTime.now());
        localDateTimesQueue.add(LocalDateTime.now().minusDays(1));
        localDateTimesQueue.add(LocalDateTime.now().plusDays(1));
        while (!localDateTimesQueue.isEmpty()) {
            LocalDateTime remove = localDateTimesQueue.remove();
            System.out.println(remove);
        }
    }

    @Test
    public void yad2AttackTest2() throws InterruptedException {
        /**
         * suppose we are having an attack on yad2.
         * we want to throw away ant user who attempts to connect to yad2 at least 10 times in one second.
         * you have an interface that contains a function. that function gets a user and a URL as its parameters.
         * write your code.
         */
        for (int i = 0; i < 10; i++) {
            Assert.assertTrue(connectRequest(1, "URL"));
            Thread.sleep(Duration.ofMillis(50).toMillis());
        }
        Assert.assertFalse(connectRequest(1, "URL"));

        Thread.sleep(Duration.ofSeconds(1).toMillis());
        Assert.assertTrue(connectRequest(1, "URL"));
    }

    private boolean connectRequest(int userId, String url) {
        LocalDateTime now = LocalDateTime.now();
        Queue<LocalDateTime> lastReqTimeQueue = userReqTimeMap.get(userId);
        boolean isValid;
        if (lastReqTimeQueue == null) {
            lastReqTimeQueue = new PriorityBlockingQueue<>();
            userReqTimeMap.put(userId, lastReqTimeQueue);
            isValid = true;
        } else if (lastReqTimeQueue.size() >= totalAllowReqInIntervalTime
                && Duration.between(lastReqTimeQueue.remove(), now).minus(timeInterval).isNegative()) {
            isValid = false;
        } else {
            isValid = true;
        }

        lastReqTimeQueue.add(now);

        //for multi threaded         //TODO add lock obj for each userID
        while (lastReqTimeQueue.size() > totalAllowReqInIntervalTime) {
            System.out.println("remove: " + lastReqTimeQueue.remove());
        }
        return isValid;
    }

}
