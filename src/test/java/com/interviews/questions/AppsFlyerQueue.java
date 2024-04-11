package com.interviews.questions;

import org.junit.Test;

import java.util.Queue;

public class AppsFlyerQueue<E> {
//    https://www.geeksforgeeks.org/queue-linked-list-implementation/

    //    you need to create a Queue (FIFO)
//    implememt push & pop funcfions that will run with time complexity of o(1)
//
//    push(2) -> push(4) -> push(7)
//    pop() 2 -> pop() 4 -> pop() 7
    private Node front;
    private Node rear;

    public class Node {
        E value;
        Node next;

        public Node(E value) {
            this.value = value;
        }
    }


    public void add(E i) {
        Node currentNote = new Node(i);
        if (rear == null) {
            front = rear = currentNote;
        } else {
            rear.next = currentNote;
            rear = currentNote;
        }
    }

    public E peek() {
        validateQueue();
        return front.value;
    }

    public E poll() {
        validateQueue();
        E value = front.value;
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
        AppsFlyerQueue<Integer> appsFlyerQueue = new AppsFlyerQueue<>();
        appsFlyerQueue.add(1);
        Integer pop1 = appsFlyerQueue.peek();
        appsFlyerQueue.add(2);
        Integer pop2 = appsFlyerQueue.peek();
        appsFlyerQueue.add(3);
        Integer pop3 = appsFlyerQueue.peek();

        Integer poll1 =appsFlyerQueue. poll();
        Integer pop22 =appsFlyerQueue. peek();
        Integer poll2 =appsFlyerQueue. poll();
        Integer pop33 =appsFlyerQueue. peek();

    }
}
