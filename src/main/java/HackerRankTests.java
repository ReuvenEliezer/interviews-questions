import org.junit.Test;

public class HackerRankTests {

    @Test
    public void deleteNodeTest() {
        Node head = new Node(0);
        head.next = new Node(1);
        head.next.next = new Node(2);
        head.next.next.next = new Node(3);
        printList(head);
        Node node = deleteNode(head, 2);
        printList(node);
    }

    @Test
    public void reverseNodeTest() {
        Node head = new Node(0);
        head.next = new Node(1);
        head.next.next = new Node(2);
        head.next.next.next = new Node(3);
        printList(head);
        Node node = reverseNode(head);
        printList(node);
    }

    void printList(Node node) {
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    private Node reverseNode(Node head) {
        Node prev = null;
        Node current = head;
        Node next;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        head = prev;
        return head;
    }

    public class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    private Node deleteNode(Node head, int index) {
        Node temp = head;
        if (temp != null && index == 0) {
            return head.next;
        }

        int currentIndex = 0;
        Node prev = head;
        while (temp != null && currentIndex < index) {
            prev = temp;
            temp = temp.next;
            currentIndex++;
        }
        if (temp == null || temp.next == null)
            return head;

        prev.next = temp.next;
        return head;

    }
}
