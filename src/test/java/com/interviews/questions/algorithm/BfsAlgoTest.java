package com.interviews.questions.algorithm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BfsAlgoTest {

    private final static Logger logger = LogManager.getLogger(BfsAlgoTest.class);

    /**
     * https://www.baeldung.com/java-breadth-first-search
     */
    @Test
    public void bfsAlgoTreeTest() {
        Tree<Integer> root = new Tree(10);
        Tree<Integer> rootFirstChild = root.addChild(2);
        Tree<Integer> depthMostChild = rootFirstChild.addChild(3);
        Tree<Integer> rootSecondChild = root.addChild(4);
        printBfs(root);

//        Optional<Tree<Integer>> search = search(4, root);
//        Tree<Integer> integerTree = search.get();
    }

    private <T extends Comparable<T>> void printBfs(Tree<T> root) {
        Queue<Tree<T>> queue = new ArrayDeque<>();
        queue.add(root);

        Tree<T> currentNode;

        while (!queue.isEmpty()) {
            currentNode = queue.remove();
            logger.debug("Visited node with value: {}", currentNode.getValue());
            queue.addAll(currentNode.getChildren());
        }
    }

    @Test
    public void dfsAlgoTreeRemoveElementMprestTest() {
//        Tree<String> root = createTree();
        /**
         * https://stackoverflow.com/questions/67297901/how-to-de-link-an-element-from-a-tree/67298783#67298783
         * @param <T>
         */
        Tree<String> root = new Tree<>("A1");

        Tree<String> b1Child = root.addChild("B1");
        Tree<String> b2Child = root.addChild("B2");
        Tree<String> b3Child = root.addChild("B3");

        Tree<String> c1Child = b1Child.addChild("C1");
        Tree<String> c2Child = b3Child.addChild("C2");

        Tree<String> d1Child = c1Child.addChild("D1");
        Tree<String> d2Child = c1Child.addChild("D2");
        Tree<String> d3Child = c2Child.addChild("D3");

        logger.info("printBfs");
        printBfs(root);
        logger.info("printDfs");
        printDfs(root);
        logger.info("remove C1");
        deLinkElement("C1", root);
        logger.info("printDfs after remove C1");
        printDfs(root);
        List<Tree<String>> children = new ArrayList<>(root.getChildren());
        Assert.assertTrue(children.containsAll(Stream.of(b1Child, b2Child, b3Child).collect(Collectors.toSet())));
        Assert.assertEquals(3, children.size());
        Collections.sort(children, Comparator.comparing(Tree<String>::getValue));

        Tree<String> b1 = children.get(0);
        Tree<String> b2 = children.get(1);
        Tree<String> b3 = children.get(2);
        Assert.assertTrue(b1.getChildren().containsAll(Stream.of(d1Child, d2Child).collect(Collectors.toSet())));
        Assert.assertTrue(b2.getChildren().containsAll(Collections.EMPTY_LIST));
        Assert.assertTrue(b3.getChildren().contains(c2Child));
    }

    private Tree<String> createTree() {
        Tree<String> root = new Tree("A1");

        Tree<String> b1Child = root.addChild("B1");
        Tree<String> b2Child = root.addChild("B2");
        Tree<String> b3Child = root.addChild("B3");

        Tree<String> c1Child = b1Child.addChild("C1");
        Tree<String> c2Child = b3Child.addChild("C2");

        Tree<String> d1Child = c1Child.addChild("D1");
        Tree<String> d2Child = c1Child.addChild("D2");
        Tree<String> d3Child = c2Child.addChild("D3");
        return root;
    }

    public static <T extends Comparable<T>> Tree<T> search(T value, Tree<T> root) {
        Queue<Tree<T>> queue = new ArrayDeque<>();
        queue.add(root);

        Tree<T> currentNode;

        while (!queue.isEmpty()) {
            currentNode = queue.remove();
            logger.debug("Visited node with value: {}", currentNode.getValue());

            if (currentNode.getValue().equals(value)) {
                return currentNode;
            } else {
                queue.addAll(currentNode.getChildren());
            }
        }

        return null;
    }

    public static <T extends Comparable<T>> void deLinkElement(T value, Tree<T> root) {
        /**
         * https://stackoverflow.com/questions/67297901/how-to-de-link-an-element-from-a-tree/67298783#67298783
         */
        if (root.getValue().equals(value)) {
            throw new IllegalArgumentException("unable to delete root value of tree");
        }
        Stack<Tree<T>> stack = new Stack<>();
        stack.push(root);
        Tree<T> current;
        while (!stack.isEmpty()) {
            current = stack.pop();
            logger.debug("Visited node with value: {}", current.getValue());

            if (current.getValue().equals(value)) {
                for (Tree<T> child : current.getChildren()) {
                    child.setParent(current.getParent());
                    stack.add(child);

                    //remove current node form the parent node children list
                    current.getParent().getChildren().remove(current);

                    //add child of current note to the parent of current node as a child
                    current.getParent().getChildren().add(child);
                }

                //remove all children from current note
//                current.getChildren().clear();
            } else {
                stack.addAll(current.getChildren());
            }
        }
    }

    public static <T extends Comparable<T>> void printDfs(Tree<T> root) {
        Stack<Tree<T>> stack = new Stack<>();
        Tree<T> current;
        stack.push(root);

        while (!stack.isEmpty()) {
            current = stack.pop();
            logger.debug("Visited node with value: {}", current.getValue());
            stack.addAll(current.getChildren());
        }
    }

    @Test
    public void bfsAlgoGraphTest() {
        Node<Integer> start = new Node<>(10);
        Node<Integer> firstNeighbor = new Node<>(2);
        start.connect(firstNeighbor);

        Node<Integer> firstNeighborNeighbor = new Node<>(3);
        firstNeighbor.connect(firstNeighborNeighbor);
        firstNeighborNeighbor.connect(start);

        Node<Integer> secondNeighbor = new Node<>(4);
        start.connect(secondNeighbor);

        Optional<Node<Integer>> search = search(4, firstNeighborNeighbor);
        Node<Integer> integerNode = search.get();
    }

    public static <T> Optional<Node<T>> search(T value, Node<T> start) {
        Queue<Node<T>> queue = new ArrayDeque<>();
        queue.add(start);

        Node<T> currentNode;
        Set<Node<T>> alreadyVisited = new HashSet<>();
        while (!queue.isEmpty()) {
            currentNode = queue.remove();
            logger.info("Visited node with value: {}", currentNode.getValue());

            if (currentNode.getValue().equals(value)) {
                return Optional.of(currentNode);
            } else {
                alreadyVisited.add(currentNode);
                queue.addAll(currentNode.getNeighbors());
                queue.removeAll(alreadyVisited);
            }
        }

        return Optional.empty();
    }



}
