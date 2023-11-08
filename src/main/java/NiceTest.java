
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;

public class NiceTest {

    private static int counter = 0;

    private static Thread t1;

    private static Thread t2;

    private static volatile boolean isThread2DoneToReadLastIteration = false;

    private static volatile boolean isThread1DoneToWriteFirstIteration = false;

    private static final int TOTAL_ITERATIONS = 10;

    private static final CountDownLatch countDownLatchTread1 = new CountDownLatch(TOTAL_ITERATIONS);
    private static final CountDownLatch countDownLatchTread2 = new CountDownLatch(TOTAL_ITERATIONS);
    private static final CountDownLatch didGetValueAtFirstTime = new CountDownLatch(2);

    @Test
    public void threadTest() throws InterruptedException {

        /**
         * t1 get value (counter = 0)
         * t2 get value (counter = 0)
         * t2 set value - 9 iterations out of 10 - from i=0 until i=8 (counter = 9)
         * t1 set value from i=0 until i=1 (counter = 1)
         * t2 get value from i=9 (counter = 1)
         * t1 set value from i=1 until i=9 (complete loop iteration) (counter = 10)
         * t2 set value for the last iteration (counter = 2)
         */

        t1 = new Thread(this::increaseIntT1);
        t2 = new Thread(this::increaseIntT2);


        t1.start();
        t2.start();

        countDownLatchTread1.await();
        countDownLatchTread2.await();

        boolean alive = t2.isAlive();


        boolean alive2 = t2.isAlive();
        t1.join();
        t2.join();
        boolean alive1 = t1.isAlive();

        System.out.println("----------------  the final counter value is: " + counter + "  ----------------");
    }

    @SneakyThrows
    private void increaseIntT1() {
        for (int i = 0; i < TOTAL_ITERATIONS; i++) {
            int value = counter;
            System.out.println("T1: read counter: " + counter);
            didGetValueAtFirstTime.countDown();
            while (didGetValueAtFirstTime.getCount() > 0) {
//                System.out.println("T1: waiting until T2 read the first iteration (counter = 0)");
//                Thread.sleep(50);
            }

            while (countDownLatchTread2.getCount() > 1) {
//                System.out.println("T1: waiting until T2 will done to set value - 9 iterations out of 10 - from i=0 until i=8 (counter = 9)");
//                Thread.sleep(50);
            }

            while (countDownLatchTread1.getCount() == 9 && !isThread2DoneToReadLastIteration) {
//                System.out.println("T1: waiting until T2 read the last iteration");
            }


            increaseCounterT1(value);
            while (!isThread2DoneToReadLastIteration) {

            }
        }
        System.out.println("T1: done to increase counter value by: " + TOTAL_ITERATIONS);

    }

    private static synchronized void increaseCounterT1(int value) {
        System.out.printf("T1: increase counter from %s to %s%n", counter, counter + 1);
        counter = value + 1;
        countDownLatchTread1.countDown();
        isThread1DoneToWriteFirstIteration = true;
    }


    @SneakyThrows
    private void increaseIntT2() {
        for (int i = 0; i < TOTAL_ITERATIONS; i++) {
            int value = counter;
            System.out.println("T2: read counter: " + counter);
            didGetValueAtFirstTime.countDown();
            while (didGetValueAtFirstTime.getCount() > 0) {
//                System.out.println("T2: waiting until T1 read the first iteration (counter = 0)");
//                Thread.sleep(50);
            }


            while (countDownLatchTread2.getCount() == 1 && !isThread1DoneToWriteFirstIteration) {
//                System.out.println("T2: waiting until T1 set counter to 1 and reading num (counter = 1)");
                value = counter;
//                System.out.println("T2: read counter: " + value);
                isThread2DoneToReadLastIteration = true;
            }


            while (isThread2DoneToReadLastIteration && countDownLatchTread1.getCount() > 0) {
//                System.out.println("T2: waiting until T1 done to increase counter");
//                Thread.sleep(50);
            }

            increaseCounterT2(value);
        }
        System.out.println("T2: done to increase counter value by: " + TOTAL_ITERATIONS);
    }

    private static synchronized void increaseCounterT2(int value) {
        System.out.printf("T2: increase counter from %s to %s%n", value, value + 1);
        counter = value + 1;
        countDownLatchTread2.countDown();
    }


    /**
     * Given an array of 2 integer arrays representing x,y coordinates, write a function to calculate the S amount of closest points to origin (0,0).
     * <p>
     * For example: [[1,2], [3,5], [-1,1], [7,9]], if we would want to get the 2 closest points to origin the output would be: [1,2], [-1,1]
     */

    @Test
    public void test() {
        double[][] arr = new double[][]{{1, 2}, {3, 5}, {-1, 1}, {7, 9}};
        List<Coordinates> result = findRange(arr, 2, 0);
        assertThat(result).containsExactlyInAnyOrderElementsOf(Lists.newArrayList(new Coordinates(1, 2), new Coordinates(-1, 1)));
    }

    private List<Coordinates> findRange(double[][] arr, int nums, double calcFrom) {
        if (nums >= arr.length) {
            return Arrays.stream(arr)
                    .map(pairValues -> new Coordinates(pairValues[0], pairValues[1]))
                    .toList();
        }

        List<Result> results = new ArrayList<>();
//        double[][] resultArr = new double[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            double[] ints = arr[i];
            Result result = calcDistance(ints, calcFrom);
//            resultArr[i][0] = result.getCoordinates().getX();
//            resultArr[i][1] = result.getCoordinates().getY();
            results.add(result);
        }

        return results.stream()
                .sorted(Comparator.comparing(Result::distance))
                .map(Result::coordinates)
                .limit(nums)
                .toList();
    }

    private static Result calcDistance(double[] arr, double calcFrom) {
        double distanceX = Math.abs(arr[0]) - calcFrom;
        double distanceY = Math.abs(arr[1]) - calcFrom;
        return new Result(new Coordinates(arr[0], arr[1]), distanceX + distanceY);
    }

    record Result(Coordinates coordinates, double distance) {
    }

    record Coordinates(double x, double y) {
    }


    @Test
    public void stackTest() {
        /**
         * • Implement stack using Linked List as a backing structure
         * • Note: do not use LinkedList from SDK, create own Node class
         * and implement linked list principle in stack
         */

        StackUsingLinkedlist<Integer> stack = new StackUsingLinkedlist<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);

        Assertions.assertThat(stack.size()).isEqualTo(4);
        Assertions.assertThat(stack.peek()).isEqualTo(4);
        Assertions.assertThat(stack.size()).isEqualTo(4);
        Assertions.assertThat(stack.pop()).isEqualTo(4);
        Assertions.assertThat(stack.size()).isEqualTo(3);
        Assertions.assertThat(stack.pop()).isEqualTo(3);
        Assertions.assertThat(stack.peek()).isEqualTo(2);
        Assertions.assertThat(stack.size()).isEqualTo(2);
    }

    static class StackUsingLinkedlist<T> {
        Node<T> head;

        int size;


        public void push(T data) {
            Node<T> node = new Node<>(data);
            node.next = head;
            head = node;
            size++;
        }

        public boolean isEmpty() {
            return head == null;
        }

        public T pop() {
            if (head == null) {
                return null;
            }

            T data = head.data;
            head = head.next;
            size--;
            return data;
        }

        public T peek() {
            if (head == null) {
                return null;
            }
            return head.data;
        }

        public int size() {
            return size;
        }
    }

    static class Node<T> {
        T data;
        Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }


}
