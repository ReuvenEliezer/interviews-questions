package com.interviews.questions;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Datos {

    static class Singleton {
        private Singleton INSTANCE;

        private Singleton() {
            //TODO
        }

        public Singleton getInstance() {
            if (INSTANCE == null) {
                synchronized (Singleton.class) {
                    if (INSTANCE == null)
                        INSTANCE = new Singleton();
                }
            }
            return INSTANCE;
        }
    }

    @Test
    public void replace2NumberWithoutAdditionalVariable() {
        int x = 2;
        int y = 5;
        replace(x, y);
    }

    @Test
    public void nodeTest() {
        Node node = new Node(1);
        node.next = new Node(2);
        node.next.next = new Node(3);
        node.next.next.next = new Node(4);
        node.next.next.next.next = new Node(5);
        Node mid = findMid(node);
        System.out.println("the mid is: " + mid.value);
    }

    private Node findMid(Node node) {
        List<Node> nodeList = new ArrayList<>();
        addToList(node, nodeList);
        return nodeList.get(nodeList.size() / 2);
    }

    private void addToList(Node node, List<Node> nodeList) {
        if (node == null) {
            return;
        }
        nodeList.add(node);
        addToList(node.next, nodeList);
    }

    private void replace(int x, int y) {
        System.out.println(String.format("before changes: x=%s, y=%s", x, y));
        x = x + y;
        y = x - y;
        x = x - y;
        System.out.println(String.format("after changes: x=%s, y=%s", x, y));
    }

    class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }

    }

}
