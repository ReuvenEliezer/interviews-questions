package com.interviews.questions;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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
        Node node = mergeKLists(nodes);
        Node node1 = mergeKLists(new Node[]{});
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
                .toList();
    }

    @Test
    public void sortBinaryArrTest() {
        int[] arr = {0, 1, 0, 1, 0, 0, 1};
        assertThat(sortBinaryArr(arr))
                .containsExactly(0, 0, 0, 0, 1, 1, 1);
    }

    private int[] sortBinaryArr(int[] arr) {
        int negativeCounter = 0;
        for (int j : arr) {
            if (j == 0) {
                negativeCounter++;
            }
        }

        int[] result = new int[arr.length];
        for (int i = 0; i <= negativeCounter; i++) {
            result[i] = 0;
        }
        for (int i = negativeCounter; i < arr.length; i++) {
            result[i] = 1;
        }
        return result;
    }

    @Test
    public void mergeOverlapWindowsTest() {
        /**
         *         1-3 , 2-4 -> 1-4
         *          1-3, 3-4 ->  1-4
         *         1-4, 2-3 -> 1-4
         *            1-3, 4-5   -> 1-3, 4-5
         */
        List<Window> windows = List.of(
                new Window(2, 4),
                new Window(1, 3),
                new Window(5, 6));

        List<Window> pairsResult = mergeOverlapWindows(windows);
        assertThat(pairsResult).hasSize(2);
//        Assertions.assertThat(pairsResult).contains(2);

        assertThat(mergeOverlapWindows(List.of(
                new Window(1, 4),
                new Window(2, 3))))
                .hasSize(1);

    }

    private List<Window> mergeOverlapWindows(List<Window> windowList) {
        /**
         * ###
         *  ###
         *  ##
         *      ##
         *  #####
         *
         * Result<List>
         * steps:
         * 1. Pair start,end
         * 2. comparing by int
         * pair_to_add
         * 3. for each:
         * 	pair_to_add.start
         * 	getY ->
         * 	go to next element
         * 	if (next.start>end)
         * 		result.add(new Pair(prev.start, prev.end)
         * 	else {
         * 	pair_to_add.start
         *        }
         */
        List<Window> result = new ArrayList<>();
        //TODO validation
        List<Window> pairsSortedList = new ArrayList<>(windowList).stream()
                .sorted(Comparator.comparing(Window::start))
                .toList();
        int start = pairsSortedList.get(0).start;
        int end = pairsSortedList.get(0).end;
        for (int i = 1; i < pairsSortedList.size(); i++) {
            Window window = pairsSortedList.get(i);
            if (window.start > end || window.end <= end) {
                result.add(new Window(start, end));
                start = window.start;
            }
            end = window.end;
        }
        int endLastWindow = result.get(result.size() - 1).end;
        if (start > endLastWindow) {
            result.add(new Window(start, end));
        }
        return result;
    }

    record Window(int start, int end) {
    }

    @Test
    public void sunbitPostLikesTest() {
        /**
         * A problem
         *  Suppose, we have to implement a part of a social network to be able to fetch top posts in a
         *  minimal time. The posts should be counted by unique likes from different users.
         *  Implement a class with two methods:
         * like(postId, userId)- record a like event on the post postId given by the user userId--
         *  Notes:
         *  unlike(postId, userId)- remove a like on the post postId given by the user userId
         *  top(n)- get top n posts by likes count in descending order
         *  The content is managed outside. LikesTracker should work with ids only.
         *  Wewant the “top” method to be the minimum run time. There are no space complexity
         *  limitations.
         *  Skeleton and run example:
         *  class LikesTracker {
         *  fun like(postId: Int, userId: Int) {
         *  }
         *  fun unlike(postId: Int, userId: Int) {
         *  }
         *  fun top(n: Int): List<Int> {
         *  }
         *  }
         *  fun main() {
         *  val likesTracker = LikesTracker()
         *  likesTracker.like(1, 1)
         *  likesTracker.like(2, 1)
         *  likesTracker.like(1, 2)
         *  likesTracker.like(1, 1)
         *  likesTracker.like(3, 1)
         *  likesTracker.top(3) // should return 1, 2, 3
         *  likesTracker.like(1, 1)
         *  likesTracker.like(2, 3)
         *  likesTracker.like(2, 4)
         *  likesTracker.like(3, 1)
         *  likesTracker.top(2) // should return 2, 1
         *  likesTracker.like(4, 1)
         *  likesTracker.unlike(3, 1)
         *  likesTracker.top(3) // should return 2, 1, 4
         */

        LikesTracker likesTracker = new LikesTracker();
        likesTracker.like(1, 1);
        likesTracker.like(2, 1);
        likesTracker.like(1, 2);
        likesTracker.like(1, 1);
        likesTracker.like(3, 1);
        assertThat(likesTracker.top(3))
                .containsExactly(1, 2, 3); // should return 2, 1,
        likesTracker.like(1, 1);
        likesTracker.like(2, 3);
        likesTracker.like(2, 4);
        likesTracker.like(3, 1);
        assertThat(likesTracker.top(2))
                .containsExactly(2, 1); // should return 2, 1,
        likesTracker.like(4, 1);
        likesTracker.unlike(3, 1);
        assertThat(likesTracker.top(3))
                .containsExactly(2, 1, 4); // should return 2, 1,

        LikesTracker likesTracker1 = new LikesTracker();
        likesTracker1.like(11, 111);
        likesTracker1.like(22, 222);
        assertThat(likesTracker1.top(3))
                .containsExactlyInAnyOrder(11, 22); // should return 11, 22,
    }

    static class LikesTracker {

        private final Map<Integer, Set<Integer>> postIdToUserIdsMap = new ConcurrentHashMap<>();
        private final Map<Integer, Set<Integer>> likeCountToPostIdsMap = new TreeMap<>(Comparator.reverseOrder());

        public synchronized void like(int postId, int userId) {
            //Returns: true if this set did not already contain the specified element
            if (postIdToUserIdsMap.computeIfAbsent(postId, k -> new HashSet<>()).add(userId)) {

                // update likeCountToPostsMap :
                Set<Integer> likesCount = postIdToUserIdsMap.get(postId);

                //increase the new likesCounter
                likeCountToPostIdsMap.computeIfAbsent(likesCount.size(), k -> new HashSet<>()).add(postId);

                //decrease the prev likesCounter
                likeCountToPostIdsMap.computeIfPresent(likesCount.size() - 1, (k, v) -> {
                    v.remove(postId);
                    return v;
                });
                Set<Integer> likesCountPrev = likeCountToPostIdsMap.get(likesCount.size() - 1);

                //optimization remove if empty
                if (likesCountPrev != null && likesCountPrev.isEmpty()) {
                    likeCountToPostIdsMap.remove(likesCount.size() - 1);
                }
            }
        }

        public synchronized void unlike(int postId, int userId) {
            if (postIdToUserIdsMap.containsKey(postId) && postIdToUserIdsMap.get(postId).remove(userId)) {
                Set<Integer> totalLikesAfterUnlike = postIdToUserIdsMap.get(postId);
                if (totalLikesAfterUnlike.isEmpty()) {
                    postIdToUserIdsMap.remove(postId);
                }
                // update likeCountToPostsMap
                int totalLikesBeforeUnlike = totalLikesAfterUnlike.size() + 1;
                likeCountToPostIdsMap.get(totalLikesBeforeUnlike).remove(postId);
                //optimization remove if empty
                if (likeCountToPostIdsMap.get(totalLikesBeforeUnlike).isEmpty()) {
                    likeCountToPostIdsMap.remove(totalLikesBeforeUnlike);
                }
            }
        }

        public List<Integer> top(int top) {
            return likeCountToPostIdsMap.entrySet().stream()
                    .flatMap(entry -> entry.getValue().stream())
                    .limit(top)
                    .toList();
        }
    }

    @Test
    public void isSumPairTest() {
        int[] arr = {1, 2, 1};
        Assert.assertTrue(isSumPair(arr, 2));
        Assert.assertFalse(isSumPair(arr, 1));
        Assert.assertFalse(isSumPair(arr, 4));
    }

    private boolean isSumPair(int[] arr, int target) {
        Set<Integer> values = new HashSet<>(arr.length);
        for (int i : arr) {
            if (values.contains(target - i)) {
                return true;
            }
            values.add(i);
        }
        return false;
    }

    @Test
    public void findElementFromTheEndOfLinkedListBySlidingWindowTest() {
        Node head = new Node(1);
        Node current = head;
        int totalNodes = 10;
        for (int value = 2; value <= totalNodes; value++) {
            current.next = new Node(value);
            current = current.next;
        }

        assertThat(findElementFromEndWithOutAdditionalMemoryBySlidingWindowAlgo(head, 3)).isEqualTo(8);
        assertThatIllegalArgumentException().isThrownBy(() -> findElementFromEndWithOutAdditionalMemoryBySlidingWindowAlgo(head, totalNodes + 1));

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

    private static int findElementFromEndWithOutAdditionalMemoryBySlidingWindowAlgo(Node head, int k) {
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
