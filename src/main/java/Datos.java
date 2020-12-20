import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Datos {

    static class Singleton {
        private Singleton INSTANCE;
        private Object lockObject = new Object();

        private Singleton() {
            //TODO
        }

        public Singleton getInstance() {
            if (INSTANCE != null) return INSTANCE;
            synchronized (lockObject) {
                if (INSTANCE == null)
                    INSTANCE = new Singleton();
                return INSTANCE;
            }
        }
    }

    @Test
    public void singletonTest() {
//        Singleton instance = Singleton.getInstance();
        int x = 0;
        int y = 0;
        replace(x, y);
    }

    @Test
    public void test() {
        int x = 0;
        int y = 0;
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
        x = y + x;
        y = x - y;
        x = y - x;

    }

    class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }

    }

}
