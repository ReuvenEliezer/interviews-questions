package com.interviews.questions;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class LinkedListTest {


    //https://leetcode.com/problems/merge-k-sorted-lists/
    @Test
    public void mergeKListsTest() {
        Node head1 = new Node(1);
        Node current = head1;
        for (int value = 2; value <= 10; value++) {
            current.next = new Node(value);
            current = current.next;
        }

        Node head2 = new Node(1);
        Node current1 = head2;
        for (int value = 4; value <= 7; value++) {
            current1.next = new Node(value);
            current1 = current1.next;
        }

        Node[] nodes = {head1, head2};
        Node node = mergeKLists(new Node[]{});
    }

    private Node mergeKLists(Node[] nodes) {
        if (nodes == null || nodes.length == 0) {
            return null;
        }

        if (nodes.length == 1) {
            return nodes[0];
        }
        List<List<Integer>> integerLists = new ArrayList<>();
        for (Node node : nodes) {
            List<Integer> integers = new ArrayList<>();
            Node head = node;
            while (head != null) {
                integers.add(head.value);
                head = head.next;
            }
            integerLists.add(integers);
        }

        List<Integer> result = mergeSortedLists(integerLists);
        if (result.isEmpty()) {
            return null;
        }
        //build note result
        Node noteResult = new Node(result.get(0));
        Node current = noteResult;
        for (int value = 1; value < result.size(); value++) {
            current.next = new Node(result.get(value));
            current = current.next;
        }

        return noteResult;
    }

    private List<Integer> mergeSortedLists(List<List<Integer>> lists) {
        Map<Integer, Integer> valueToCountMap = new TreeMap<>();
        for (List<Integer> list : lists) {
            for (Integer value : list) {
                valueToCountMap.merge(value, 1, Integer::sum);
            }
        }

//        List<Integer> result = new ArrayList<>();
//        valueToCountMap.forEach((key, value) -> {
//            for (int i = 0; i < value; i++) {
//                result.add(key);
//            }
//        });
//        return result;

        return valueToCountMap.entrySet()
                .stream()
                .flatMap(entry -> Collections.nCopies(entry.getValue(), entry.getKey()).stream())
                .collect(Collectors.toList());
    }


    @Test
    public void findElementFromTheEndOfLinkedListTest() {
        Node head = new Node(1);
        Node current = head;
        for (int value = 2; value <= 10; value++) {
            current.next = new Node(value);
            current = current.next;
        }

        assertThat(findElementFromEndWithOutAdditionalMemory(head, 3)).isEqualTo(8);
        assertThatIllegalArgumentException().isThrownBy(() -> findElementFromEndWithOutAdditionalMemory(head, 11));

        assertThat(findElementFromEndNaiveSolution(head, 3)).isEqualTo(8);
        assertThatIllegalArgumentException().isThrownBy(() -> findElementFromEndNaiveSolution(head, 11));


        assertThat(findElementFromEndByStack(head, 3)).isEqualTo(8);
        assertThatIllegalArgumentException().isThrownBy(() -> findElementFromEndByStack(head, 11));

        assertThat(findElementFromEndByQueue(head, 3)).isEqualTo(8);
        assertThatIllegalArgumentException().isThrownBy(() -> findElementFromEndByQueue(head, 11));
    }

    private int findElementFromEndByQueue(Node head, int k) {
        validate(head, k);
        //keep the queue with k size. and when the queue size is bigger that k -> we can to remove the old element in order to keep it small.
        Queue<Node> nodesQueue = new ArrayDeque<>();
        Node node = head;
        while (node != null) {
            nodesQueue.add(node);
            node = node.next;
            if (nodesQueue.size() > k) {
                nodesQueue.remove();
            }
        }

        int nodePlace = nodesQueue.size();
        if (nodePlace < k) {
            throw new IllegalArgumentException("K is larger than the list size");
        }

        //return the first element in queue
        return nodesQueue.poll().value;
    }

    private static void validate(Node head, int k) {
        if (k < 0) {
            throw new IllegalArgumentException(String.format("k=%s must not be a negative value", k));
        }
        if (head == null) {
            throw new IllegalArgumentException("head must be not null");
        }
    }

    private int findElementFromEndByStack(Node head, int k) {
        validate(head, k);
        Stack<Node> nodesStack = new Stack<>();
        Node node = head;
        while (node != null) {
            nodesStack.add(node);
            node = node.next;
        }

        int nodeElementPlaceFromStart = nodesStack.size() - k;
        if (nodeElementPlaceFromStart < 0) {
            throw new IllegalArgumentException("K is larger than the list size");
        }

        //get out until we are going to k
        for (int i = 1; i < k; i++) {
            nodesStack.pop();
        }
        return nodesStack.pop().value;
    }

    private static int findElementFromEndWithOutAdditionalMemory(Node head, int k) {
        validate(head, k);
        /**
         * sliding windows of K size
         */
        Node startWindow = head;
        Node endWindow = head;

        // Move the endWindow pointer K steps ahead until we found the pointer to k element from the end
        for (int i = 0; i < k; i++) {
            if (endWindow == null) {
                throw new IllegalArgumentException("K is larger than the list size");
            }
            endWindow = endWindow.next;
        }

        // Move both pointers together until the endWindow pointer go to the end of nodes. so -> return back the startWindow (because of the )
        while (endWindow != null) {
            startWindow = startWindow.next;
            endWindow = endWindow.next;
        }

        return startWindow.value;
    }

    private static int findElementFromEndNaiveSolution(Node head, int k) {
        if (head == null) {
            throw new IllegalArgumentException("head must be not null");
        }
        Node node = head;
        Map<Integer, Node> nodeToIndexMap = new HashMap<>();
        int nodesCounter = 0;
        while (node != null) {
            nodeToIndexMap.put(nodesCounter, node);
            node = node.next;

//            optimistic memory : remove all prev value for n-k -> save in every iteration only the n-k elements range.
            nodeToIndexMap.remove(nodesCounter - k);


            nodesCounter++;
        }
        if (k > nodesCounter) {
            throw new IllegalArgumentException("K is larger than the list size");
        }
        return nodeToIndexMap.get(nodesCounter - k).value;
    }

    static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    @Test
    public void reverseLastElementToFirstTest() {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5, 6);
        System.out.println(integerList);
        List<Integer> reverse = reverseLastElementToFirst(integerList);
        System.out.println(reverse);
        List<Integer> reverse2 = reverseLastElementToFirst(reverse);
        System.out.println(reverse2);

        /**
         * [1, 2, 3, 4, 5, 6]
         * [6, 1, 2, 3, 4, 5]
         * [5, 6, 1, 2, 3, 4]
         */
    }

    private List<Integer> reverseLastElementToFirst(List<Integer> integerList) {
        List<Integer> result = new ArrayList<>();
        result.add(integerList.get(integerList.size() - 1));
        for (int i = 0; i < integerList.size() - 1; i++) {
            result.add(integerList.get(i));
        }
        return result;
    }

    @Test
    public void reverseLastElementToFirstTest1() {
        LinkedList<Integer> integerList = new LinkedList<>();
        integerList.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));
        System.out.println(integerList);
        reverseLastElementToFirst1(integerList);
        System.out.println(integerList);
        reverseLastElementToFirst1(integerList);
        System.out.println(integerList);
    }

    private void reverseLastElementToFirst1(LinkedList<Integer> integerList) {
        Integer last = integerList.removeLast();
        integerList.addFirst(last);
    }

}
