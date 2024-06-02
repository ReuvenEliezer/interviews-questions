package com.interviews.questions.algorithm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
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
        com.interviews.questions.algorithm.Tree<Integer> root = new com.interviews.questions.algorithm.Tree(10);
        com.interviews.questions.algorithm.Tree<Integer> rootFirstChild = root.addChild(2);
        com.interviews.questions.algorithm.Tree<Integer> depthMostChild = rootFirstChild.addChild(3);
        com.interviews.questions.algorithm.Tree<Integer> rootSecondChild = root.addChild(4);
        printBfs(root);

//        Optional<Tree<Integer>> search = search(4, root);
//        Tree<Integer> integerTree = search.get();
    }

    private <T extends Comparable<T>> void printBfs(com.interviews.questions.algorithm.Tree<T> root) {
        Queue<com.interviews.questions.algorithm.Tree<T>> queue = new ArrayDeque<>();
        queue.add(root);

        com.interviews.questions.algorithm.Tree<T> currentNode;

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
        com.interviews.questions.algorithm.Tree<String> root = new com.interviews.questions.algorithm.Tree<>("A1");

        com.interviews.questions.algorithm.Tree<String> b1Child = root.addChild("B1");
        com.interviews.questions.algorithm.Tree<String> b2Child = root.addChild("B2");
        com.interviews.questions.algorithm.Tree<String> b3Child = root.addChild("B3");

        com.interviews.questions.algorithm.Tree<String> c1Child = b1Child.addChild("C1");
        com.interviews.questions.algorithm.Tree<String> c2Child = b3Child.addChild("C2");

        com.interviews.questions.algorithm.Tree<String> d1Child = c1Child.addChild("D1");
        com.interviews.questions.algorithm.Tree<String> d2Child = c1Child.addChild("D2");
        com.interviews.questions.algorithm.Tree<String> d3Child = c2Child.addChild("D3");

        logger.info("printBfs");
        printBfs(root);
        logger.info("printDfs");
        printDfs(root);
        logger.info("remove C1");
        deLinkElement("C1", root);
        logger.info("printDfs after remove C1");
        printDfs(root);
        List<com.interviews.questions.algorithm.Tree<String>> children = root.getChildren().stream().toList();
        Assert.assertTrue(children.containsAll(Stream.of(b1Child, b2Child, b3Child).collect(Collectors.toSet())));
        Assert.assertEquals(3, children.size());
        Collections.sort(children, Comparator.comparing(com.interviews.questions.algorithm.Tree<String>::getValue));

        com.interviews.questions.algorithm.Tree<String> b1 = children.get(0);
        com.interviews.questions.algorithm.Tree<String> b2 = children.get(1);
        com.interviews.questions.algorithm.Tree<String> b3 = children.get(2);
        Assert.assertTrue(b1.getChildren().containsAll(Stream.of(d1Child, d2Child).collect(Collectors.toSet())));
        Assert.assertTrue(b2.getChildren().containsAll(Collections.EMPTY_LIST));
        Assert.assertTrue(b3.getChildren().containsAll(Collections.singleton(c2Child)));
    }

    private com.interviews.questions.algorithm.Tree<String> createTree() {
        com.interviews.questions.algorithm.Tree<String> root = new com.interviews.questions.algorithm.Tree("A1");

        com.interviews.questions.algorithm.Tree<String> b1Child = root.addChild("B1");
        com.interviews.questions.algorithm.Tree<String> b2Child = root.addChild("B2");
        com.interviews.questions.algorithm.Tree<String> b3Child = root.addChild("B3");

        com.interviews.questions.algorithm.Tree<String> c1Child = b1Child.addChild("C1");
        com.interviews.questions.algorithm.Tree<String> c2Child = b3Child.addChild("C2");

        com.interviews.questions.algorithm.Tree<String> d1Child = c1Child.addChild("D1");
        com.interviews.questions.algorithm.Tree<String> d2Child = c1Child.addChild("D2");
        com.interviews.questions.algorithm.Tree<String> d3Child = c2Child.addChild("D3");
        return root;
    }

    public static <T extends Comparable<T>> com.interviews.questions.algorithm.Tree<T> search(T value, com.interviews.questions.algorithm.Tree<T> root) {
        Queue<com.interviews.questions.algorithm.Tree<T>> queue = new ArrayDeque<>();
        queue.add(root);

        com.interviews.questions.algorithm.Tree<T> currentNode;

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

    public static <T extends Comparable<T>> void deLinkElement(T value, com.interviews.questions.algorithm.Tree<T> root) {
        /**
         * https://stackoverflow.com/questions/67297901/how-to-de-link-an-element-from-a-tree/67298783#67298783
         */
        if (root.getValue().equals(value)) {
            throw new IllegalArgumentException("unable to delete root value of tree");
        }
        Stack<com.interviews.questions.algorithm.Tree<T>> stack = new Stack<>();
        stack.push(root);
        com.interviews.questions.algorithm.Tree<T> current;
        while (!stack.isEmpty()) {
            current = stack.pop();
            logger.debug("Visited node with value: {}", current.getValue());

            if (current.getValue().equals(value)) {
                for (com.interviews.questions.algorithm.Tree<T> child : current.getChildren()) {
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

    public static <T extends Comparable<T>> void printDfs(com.interviews.questions.algorithm.Tree<T> root) {
        Stack<com.interviews.questions.algorithm.Tree<T>> stack = new Stack<>();
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
        com.interviews.questions.algorithm.Node<Integer> start = new com.interviews.questions.algorithm.Node<>(10);
        com.interviews.questions.algorithm.Node<Integer> firstNeighbor = new com.interviews.questions.algorithm.Node<>(2);
        start.connect(firstNeighbor);

        com.interviews.questions.algorithm.Node<Integer> firstNeighborNeighbor = new com.interviews.questions.algorithm.Node<>(3);
        firstNeighbor.connect(firstNeighborNeighbor);
        firstNeighborNeighbor.connect(start);

        com.interviews.questions.algorithm.Node<Integer> secondNeighbor = new com.interviews.questions.algorithm.Node<>(4);
        start.connect(secondNeighbor);

        Optional<com.interviews.questions.algorithm.Node<Integer>> search = search(4, firstNeighborNeighbor);
        com.interviews.questions.algorithm.Node<Integer> integerNode = search.get();
    }

    public static <T> Optional<com.interviews.questions.algorithm.Node<T>> search(T value, com.interviews.questions.algorithm.Node<T> start) {
        Queue<com.interviews.questions.algorithm.Node<T>> queue = new ArrayDeque<>();
        queue.add(start);

        com.interviews.questions.algorithm.Node<T> currentNode;
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
