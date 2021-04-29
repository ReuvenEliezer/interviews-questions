import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

public class HItCounterTest {
    /**
     * return the request size in the last 5 min from now
     */

    private Queue<LocalDateTime> queue = new PriorityBlockingQueue<>();
    private Duration saveReqTime = Duration.ofMinutes(5);

    @Test
    public void hitCounter() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime now1 = LocalDateTime.now().minus(Duration.ofMinutes(1));
        LocalDateTime oldTime = LocalDateTime.now().minus(Duration.ofMinutes(10));
        registerHit(now);
        registerHit(oldTime);
        registerHit(now1);
        Assert.assertEquals(2, getLast5MinHits(now));
    }

    public void registerHit(LocalDateTime localDateTime) {
        queue.add(localDateTime);
    }

    public int getLast5MinHits(LocalDateTime localDateTime) {
        while (!queue.isEmpty() && localDateTime.minus(saveReqTime).isAfter(queue.peek())) {
            queue.remove();
        }
        return queue.size();
    }

}
