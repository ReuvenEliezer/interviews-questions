import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

public class LinkedListTest {


    @Test
    public void findElementFromTheEndOfLinkedListTest() {
        Node head = new Node(1);
        Node current = head;
        for (int value = 2; value <= 10; value++) {
            current.next = new Node(value);
            current = current.next;
        }

        assertThat(findElementFromEnd(head, 3)).isEqualTo(8);
        assertThatIllegalArgumentException().isThrownBy(() -> findElementFromEnd(head, 11));
        assertThat(findElementFromEnd1(head, 3)).isEqualTo(8);
        assertThatIllegalArgumentException().isThrownBy(() -> findElementFromEnd1(head, 11));
    }

    private static int findElementFromEnd(Node head, int k) {
        if (k < 0) {
            throw new IllegalArgumentException(String.format("k=%s must not be a negative value", k));
        }
        if (head == null) {
            throw new IllegalArgumentException("head must be not null");
        }
        /**
         * sliding windows of K size
         */
        Node startWindow = head;
        Node endWindow = head;

        // Move the fast pointer K steps ahead
        for (int i = 0; i < k; i++) {
            if (endWindow == null) {
                throw new IllegalArgumentException("K is larger than the list size");
            }
            endWindow = endWindow.next;
        }
        //now we found the pointer to k element from the end

        // Move both pointers together until the fast pointer reaches the end
        while (endWindow != null) {
            startWindow = startWindow.next;
            endWindow = endWindow.next;
        }

        return startWindow.value;
    }

    private static int findElementFromEnd1(Node head, int k) {
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
