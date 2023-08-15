import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LinkedListTest {



    @Test
    public void findElementFromTheEndOfLinkedListTest() {
        ListNode head = new ListNode(1);
        ListNode current = head;
        for (int value = 2; value <= 10; value++) {
            current.next = new ListNode(value);
            current = current.next;
        }

        int k = 3;
        int result = findElementFromEnd(head, k);
        if (result != -1) {
            System.out.println("The " + k + "th element from the end is: " + result);
        } else {
            System.out.println("The list has less than " + k + " elements.");
        }

    }

    private static int findElementFromEnd(ListNode head, int k) {
        ListNode slowPointer = head;
        ListNode fastPointer = head;

        // Move the fast pointer K steps ahead
        for (int i = 0; i < k; i++) {
            if (fastPointer == null) {
                return -1;  // K is larger than the list size
            }
            fastPointer = fastPointer.next;
        }

        // Move both pointers together until the fast pointer reaches the end
        while (fastPointer != null) {
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next;
        }

        return slowPointer.value;
    }


    static class ListNode {
        int value;
        ListNode next;

        ListNode(int value) {
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
