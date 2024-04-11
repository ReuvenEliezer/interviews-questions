package com.interviews.questions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PingTest {


    /**
     * Provide the implementation (code) for the following function:
     * <p>
     * void generateTable(int x, int n)
     * <p>
     * The expected result will be an output with numbers like a table where x - number of columns, and n cells containing numbers from 1 to n.
     * <p>
     * Example
     * for x = 3 and n = 8, the following result is expected
     * <p>
     * 1   2   3
     * 4   5   6
     * 7   8
     * <p>
     * <p>
     * select * from grades ;
     */

    @Test
    public void test1() {
        generateTable(3, 8);
    }


    private void generateTable(int colNum, int values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= values; i++) {
            sb.append(i);
            if (i % colNum == 0) {
                sb.append(System.lineSeparator());
            }
            if (i < values)
                sb.append(" ");
        }

        System.out.print(sb);
    }


    @Test
    public void test() throws InterruptedException {
        /**
         *write a service that provide an interface with:
         * מקבל TASK להריץ בעוד X זמן מכל מיני שירותים ומבצע אותם כשמגיע הזמן
         */

        TaskScheduler taskScheduler = new TaskSchedulerImpl();
        taskScheduler.schedule(() -> System.out.println(2), LocalDateTime.now());
        taskScheduler.schedule(this::doSomething, LocalDateTime.now().plusSeconds(5));
        taskScheduler.schedule(() -> System.out.println(1), LocalDateTime.now().minusDays(10));

        Thread.sleep(10000);
    }

    private void doSomething() {
        System.out.println(3);
    }

    @FunctionalInterface
    public interface TaskScheduler {
        void schedule(Runnable runnable, LocalDateTime localDateTime);
    }

    public class TaskSchedulerImpl implements TaskScheduler {
        private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
        private PriorityQueue<TaskTime> priorityQueue = new PriorityQueue(Comparator.comparing(TaskTime::scheduleTime));

        public TaskSchedulerImpl() {
            executorService.scheduleWithFixedDelay(this::init, 0, 3, TimeUnit.SECONDS);
        }

        @Override
        public void schedule(Runnable runnable, LocalDateTime localDateTime) {
            priorityQueue.add(new TaskTime(runnable, localDateTime));
        }

        private void init() {
            while (!priorityQueue.isEmpty()) {
                TaskTime taskTime = priorityQueue.peek();
                if (taskTime.scheduleTime.isAfter(LocalDateTime.now())) {
                    break;
                }
                taskTime = priorityQueue.poll();
                System.out.println(taskTime.scheduleTime);
                executorService.submit(taskTime.runnable);
            }
        }
    }

    record TaskTime(
            Runnable runnable,
            LocalDateTime scheduleTime) {
    }

//    @FunctionalInterface
//    public interface CreateCallback {
//        void invoke(Runnable t);
//    }
//
//    public class ExecutorTask {
//        private Runnable callback;
//
//        public Runnable getCallback() {
//            return callback;
//        }
//
//        public void setCallback(Runnable callback) {
//            this.callback = callback;
//        }
//    }
//
//    public class CreateCallbackImpl1 extends ExecutorTask implements CreateCallback {
//        public CreateCallbackImpl1(Runnable runnable, TaskScheduler taskScheduler, LocalDateTime localDateTime) {
//            taskScheduler.schedule(runnable, localDateTime);
//        }
//
////        @Override
////        public void invoke(Runnable runnable) {
////            System.out.println(runnable);
////        }
//
//    }
//
//    public class CreateCallbackImpl2 implements CreateCallback {
////        private TaskSchedulerImpl taskScheduler;
//
//        public CreateCallbackImpl2(Runnable runnable, TaskSchedulerImpl taskScheduler, LocalDateTime localDateTime) {
////            this.taskScheduler = taskScheduler;
//            taskScheduler.schedule(runnable, localDateTime);
//        }
//
//        @Override
//        public void invoke(Runnable runnable) {
//            System.out.println(runnable);
//        }
//    }


}
