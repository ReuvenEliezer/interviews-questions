import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Collectors;

public class QueueInterviews {

    @Test
    public void minRopesCost() {
        /**
         *  https://practice.geeksforgeeks.org/problems/minimum-cost-of-ropes-1587115620/1
         */

        Assert.assertEquals(29, minCost(new long[]{4, 3, 2, 6}));
        Assert.assertEquals(62, minCost(new long[]{4, 2, 7, 6, 9}));
    }

    @Test
    public void generateBinaryNumbers() {
        /**
         * https://practice.geeksforgeeks.org/problems/generate-binary-numbers-1587115620/1
         */
        ArrayList<String> generateBinaryNumbers = generateBinaryNumbers(12);
        ArrayList<String> generateBinaryNumbersByQueue = generateBinaryNumbersByQueue(12);
        Assert.assertEquals(generateBinaryNumbers, generateBinaryNumbersByQueue);
    }


    private long minCost(long arr[]) {
        Queue<Long> queue = new PriorityBlockingQueue<>();
        queue.addAll(Arrays.stream(arr).boxed().collect(Collectors.toList()));

        long totalCost = 0;
        while (queue.size() > 1) {
            Long first = queue.poll();
            Long second = queue.poll();
            totalCost += (second + first);
            queue.add(second + first);
        }
        return totalCost;
    }

    private ArrayList<String> generateBinaryNumbers(int n) {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        for (int i = 1; i < n; i++) {
            String prev = strings.get(strings.size() - 1);
            String current;
            if (prev.contains("0")) {
                current = replaceLastZeroWithOne(prev);
            } else {
                current = replaceAllOneAfterFirstToZero(prev) + "0";
            }
            strings.add(current);
        }
        return strings;
        // Your code here
    }

    private static String replaceAllOneAfterFirstToZero(String s) {
        if (s.length() == 1)
            return s;
        String substring = s.substring(1);
        return 1 + substring.replaceAll("1", "0");
    }

    private static String replaceLastZeroWithOne(String s) {
        char[] chars = s.toCharArray();
        int foundIndex = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char aChar = chars[i];
            if (aChar == '0') {
                chars[i] = '1';
                foundIndex = i;
                break;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= foundIndex; i++) {
            sb.append(chars[i]);
        }
        String substring = s.substring(foundIndex + 1);
        sb.append(substring.replaceAll("1", "0"));
        return sb.toString();
    }

    private ArrayList<String> generateBinaryNumbersByQueue(int n) {
        //using a list to store the answer.
        ArrayList<String> responseList = new ArrayList<>();

        //using a queue of string which helps in generating binary numbers.
        Queue<String> q = new LinkedList<>();

        //pushing first binary number i.e. 1 in queue.
        q.add("1");

        while (n-- > 0) {
            //storing the front element of queue and popping it.
            String prevValue = q.poll();

            //adding the popped element in answer.
            responseList.add(prevValue);
            //generating the next two binary numbers by adding â€œ0â€ and â€œ1â€ to
            //the existing strings s1 and s2 and pushing them into queue.
            q.add(prevValue + '0');
            q.add(prevValue + '1');
        }
        //returning the list.
        return responseList;

    }
}
