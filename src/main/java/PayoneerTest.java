import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class PayoneerTest {

    /**
     *  design question:
     *  design a registration service:
     *  - fields: first-name, last-name, email (username), country.
     *
     */


    /**
     * write a logger that accumulate a messages from any threads/services and write it only when the limited size (config) of message is reached or by expired time
     */
    @Test
    public void myLoggerTest() throws InterruptedException {
        MyLogger myLogger = MyLogger.getLogger();
        myLogger.writeLog("a");
        myLogger.writeLog("b");
        Thread.sleep(11000);
        IntStream.range(0, 25).forEach(value -> myLogger.writeLog(String.valueOf(value)));
        Thread.sleep(11000);
    }

    public static class MyLogger {
        private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        private static final Duration maxTimeDurationToWriteLogs = Duration.ofSeconds(5);
        private static final long MAX_MESSAGES = 10;
        private static final Object lock = new Object();
        private List<MyMessage> messages = new ArrayList<>();
        private LocalDateTime lastPrintMessagesTime = LocalDateTime.now(ZoneOffset.UTC);


        //singleton
        private MyLogger() {
            executorService.scheduleWithFixedDelay(this::scheduleLogger, maxTimeDurationToWriteLogs.toMillis(), Duration.ofMillis(100).toMillis(), TimeUnit.MILLISECONDS);
        }

        public void writeLog(String message) {
            //acquired lock
            synchronized (lock) {
                messages.add(new MyMessage(message));
                if (messages.size() > MAX_MESSAGES) {
                    writeAllLogs();
                }
            }
            //release lock
        }

        private void writeAllLogs() {
            //acquired lock
            synchronized (lock) {
                // write all
                messages.forEach(message -> System.out.printf("%s: %s%n", LocalDateTime.now(ZoneOffset.UTC), message));

                // clean messages until now
                messages = new ArrayList<>();

                // reset timer
                lastPrintMessagesTime = LocalDateTime.now(ZoneOffset.UTC);
            }
            //release lock
        }

        //        @Scheduled(fixedRate = 100, timeUnit = TimeUnit.MILLISECONDS)
        public void scheduleLogger() {
            synchronized (lock) {
                LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
                if (lastPrintMessagesTime.plus(maxTimeDurationToWriteLogs).isBefore(now)) {
                    writeAllLogs();
                }
            }
        }


        private static final Object loggerInstanceLock = new Object();
        private static MyLogger INSTANCE;

        public static MyLogger getLogger() {
            // make it singleton
            if (INSTANCE == null) {
                synchronized (loggerInstanceLock) {
                    if (INSTANCE == null)
                        INSTANCE = new MyLogger();
                }
            }
            return INSTANCE;
        }

        @Getter
        @AllArgsConstructor
        @ToString
        static class MyMessage {
            private final LocalDateTime time;
            private final String message;

            public MyMessage(String message) {
                this.time = LocalDateTime.now(ZoneOffset.UTC);
                this.message = message;
            }
        }

    }


}
