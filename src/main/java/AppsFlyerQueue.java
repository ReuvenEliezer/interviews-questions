import org.junit.Test;

public class AppsFlyerQueue {
//    https://www.geeksforgeeks.org/queue-linked-list-implementation/

//    you need to create a Queue (FIFO)
//    implememt push & pop funcfions that will run with time complexity of o(1)
//
//    push(2) -> push(4) -> push(7)
//    pop() 2 -> pop() 4 -> pop() 7
    private Node front;
    private Node rear;

    public class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public void add(int i) {
        Node currentNote = new Node(i);
        if (rear == null) {
            front = rear = currentNote;
        } else {
            rear.next = currentNote;
            rear = currentNote;
        }
    }

    public int peek() {
        validateQueue();
        return front.value;
    }

    public int poll() {
        validateQueue();
        int value = front.value;
        front = front.next;

        if (front == null)
            rear = null;
        return value;
    }

    private void validateQueue() {
        if (front == null)
            throw new RuntimeException("queue is empty");
    }

    @Test
    public void test() {
        add(1);
        int pop1 = peek();
        add(2);
        int pop2 = peek();
        add(3);
        int pop3 = peek();

        int poll1 = poll();
        int pop22 = peek();
        int poll2 = poll();
        int pop33 = peek();

    }
}
