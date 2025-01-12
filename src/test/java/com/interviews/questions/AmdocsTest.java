package com.interviews.questions;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class AmdocsTest {

    class Node {
        int value;
        Node right;
        Node left;

        public Node(int value) {
            this.value = value;
        }
    }

    @Test
    public void findMaxDepthTreeNode() {
        TreeNode root = new TreeNode(1);
        TreeNode nodeB = new TreeNode(2);
        TreeNode nodeC = new TreeNode(2);
        TreeNode nodeD = new TreeNode(3);
        TreeNode nodeE = new TreeNode(3);

        root.addChildren(List.of(nodeB, nodeC));
        nodeB.addChildren(List.of(nodeD, nodeE));

        int maxDepth1 = findMaxDepthByQueue(root);
        int maxDepth2 = findMaxDepthRecursive(root);
        System.out.println("The maximum depth is: " + maxDepth1);
        System.out.println("The maximum depth is: " + maxDepth2);
    }

    public static int findMaxDepthRecursive(TreeNode node) {
        if (node == null) {
            return 0;
        }

        if (node.children == null || node.children.isEmpty()) {
            return 1;
        }

        int maxDepth = 0;
        for (TreeNode child : node.children) {
            maxDepth = Math.max(maxDepth, findMaxDepthRecursive(child));
        }

        return maxDepth + 1;
    }

    public static int findMaxDepthByQueue(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int depth = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); //equal to nodes number in given level
            for (int i = 0; i < levelSize; i++) {
                TreeNode currentNode = queue.poll();
                if (currentNode.children != null && !currentNode.children.isEmpty()) {
                    queue.addAll(currentNode.children);
                }
            }
            depth++;
        }
        return depth;
    }

    public record TreeNode(int value, List<TreeNode> children) {
        public TreeNode(int value) {
            this(value, new ArrayList<>());
        }

        public void addChild(TreeNode child) {
            children.add(child);
        }

        public void addChildren(List<TreeNode> children) {
            this.children.addAll(children);
        }
    }

    private void mirror(TreeNode node) {
        if (node == null || node.children == null)
            return;
        //replace children
        for (int i = 0; i < node.children.size() / 2; i++) {
            TreeNode treeNode = node.children.get(i);
            TreeNode last = node.children.get(node.children.size() - (i + 1));
            node.children.set(i, last);
            node.children.set(node.children.size() - (i + 1), treeNode);
        }

        for (TreeNode child : node.children) {
            mirror(child);
        }

    }

    @Test
    public void evolvenMirrorTreeWithMultipleChildTest() {
        TreeNode node = new TreeNode(1);
        node.addChildren(Arrays.asList(new TreeNode(2), new TreeNode(3), new TreeNode(4)));
        node.children.get(0).addChildren(Arrays.asList(new TreeNode(21), new TreeNode(22), new TreeNode(23), new TreeNode(24), new TreeNode(25)));
        node.children.get(1).addChildren(Arrays.asList(new TreeNode(31), new TreeNode(32), new TreeNode(33)));
        node.children.get(2).addChildren(Arrays.asList(new TreeNode(41), new TreeNode(42), new TreeNode(43)));

        mirror(node);

        assertEquals(4, node.children.get(0).value);
        assertEquals(3, node.children.get(1).value);
        assertEquals(2, node.children.get(2).value);

        assertEquals(43, node.children.get(0).children.get(0).value);
        assertEquals(42, node.children.get(0).children.get(1).value);
        assertEquals(41, node.children.get(0).children.get(2).value);

    }

    @Test
    public void mirrorNodeTest() {
        Node node = new Node(2);
        node.left = new Node(1);
        node.right = new Node(3);
        node.right.left = new Node(5);
        node.right.right = new Node(4);
        mirror(node);
    }

    private void mirror(Node node) {
        if (node == null)
            return;
        Node rightTemp = node.right;
        node.right = node.left;
        node.left = rightTemp;
        mirror(node.left);
        mirror(node.right);
    }

    @Test
    public void nodeTest() {
        Node node = new Node(1);
        node.left = new Node(2);
        node.right = new Node(3);
        node.right.left = new Node(4);

        printNodes(node);

        System.out.println();
        //הדפס רק את העומק של העץ הנתון
        printNodes(node, 1, 0);
        printNodesByQueue(node, 1);
    }

    private void printNodesByQueue(Node node, int depthLevel) {
        Queue<NodeLevel> queue = new ArrayDeque<>();
        queue.add(new NodeLevel(node, 0));
        while (!queue.isEmpty() && queue.peek().level <= depthLevel) {
            NodeLevel nodeLevel = queue.poll();
            if (nodeLevel.level == depthLevel) {
                System.out.println(nodeLevel.node.value);
            }
            if (nodeLevel.node.left != null) {
                queue.add(new NodeLevel(nodeLevel.node.left, nodeLevel.level + 1));
            }
            if (nodeLevel.node.right != null) {
                queue.add(new NodeLevel(nodeLevel.node.right, nodeLevel.level + 1));
            }
        }
    }

    record NodeLevel(Node node, int level) {
    }

    private void printNodes(Node node) {
        if (node == null)
            return;
        printNodes(node.left);
        printNodes(node.right);
        System.out.println(node.value);
    }

    private void printNodes(Node node, int depthLevel, int currentLevel) {
        if (node == null || currentLevel > depthLevel)
            return;
        printNodes(node.left, depthLevel, currentLevel + 1);
        printNodes(node.right, depthLevel, currentLevel + 1);
        if (currentLevel == depthLevel)
            System.out.println(node.value);
    }

    @Test
    public void immutableStringTest() {
        String a = "a";
        String b = "a";
        assertTrue(a.equals(b));
        assertTrue(a == b);
        String c = new String("a");
        assertTrue(a.equals(c));
        assertFalse(a == c);
    }

    @Test
    public void atomicInteger() {
        List<String> stringList = Arrays.asList("a", "b");
        AtomicInteger counter = new AtomicInteger();
        Map<Integer, String> map = stringList
                .stream()
                .collect(Collectors.toMap((c) -> counter.incrementAndGet(), (c) -> c));
        System.out.println(map);
    }

    @Test
    public void getMaxLogMessageInLastTimeTest() {
        LogSystem logSystem = new LogSystem();
        logSystem.addLog("log1");
        logSystem.addLog("log1");
        logSystem.addLog("log2");
        String mostFrequentLog = logSystem.getMostFrequentLog(Duration.ofMinutes(5));
        String mostFrequentLog1 = logSystem.getMostFrequentLog(Duration.ofMinutes(5));
        String mostFrequentLog2 = logSystem.getMostFrequentLog(Duration.ofMinutes(5));
        assertThat(mostFrequentLog).isEqualTo("log1");
        assertThat(mostFrequentLog1).isEqualTo("log1");
        assertThat(mostFrequentLog2).isEqualTo("log1");
    }


    static class LogSystem {

        private final Map<String, Stack<LocalDateTime>> logData = new HashMap<>();

        public void addLog(String logMessage) {
            logData.computeIfAbsent(logMessage, k -> new Stack<>()).push(LocalDateTime.now());
        }

        public String getMostFrequentLog(Duration duration) {
            LocalDateTime now = LocalDateTime.now();

            int instancesInLastTime = 0;
            String logMessageResult = null;

            for (Map.Entry<String, Stack<LocalDateTime>> entry : logData.entrySet()) {
                String logMessage = entry.getKey();
                Stack<LocalDateTime> logTimesStack = new Stack<>();
                logTimesStack.addAll(entry.getValue());
                int stackSize = logTimesStack.size();
                int result = 0;
                while (!logTimesStack.isEmpty() && stackSize > instancesInLastTime && now.minus(duration).isBefore(logTimesStack.peek())) {
                    logTimesStack.pop();
                    result++;
                }

                if (result > instancesInLastTime) {
                    instancesInLastTime = result;
                    logMessageResult = logMessage;
                }
            }
            return logMessageResult;
        }

    }


    @Test
    @Ignore
    public void yairTest() throws IOException {
        Runtime.getRuntime().exec("notepad");//will open a new notepad
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 100; i++) {
            String s = String.valueOf(i);
            sb.append(i + ":");
            boolean three = false;
            boolean fine = false;
            boolean seven = false;
            for (String c : s.split("")) {
                if (!three && (c.equals("3") || i % 3 == 0)) {
                    sb.append(" bim");
                    three = true;
                }

                if (!fine && (c.equals("5") || i % 5 == 0)) {
                    sb.append(" bam");
                    fine = true;
                }

                if (!seven && (c.equals("7") || i % 7 == 0)) {
                    sb.append(" bum");
                    seven = true;
                }
            }
            sb.append(System.lineSeparator());
        }
        System.out.println(sb);
    }

    @Test
    public void scheduleTest() {

    }

    public class Schedule {
        //        private Map<Long, List<Task>> taskToTimeMap = new HashMap<>();
//        private long elapsedTime;
        private PriorityBlockingQueue<TaskTime> queue = new PriorityBlockingQueue(2, Comparator.comparing(TaskTime::getTaskExecutionTime));

        void schedule(Task task, LocalDateTime localDateTime) {
            queue.add(new TaskTime(task, localDateTime));
        }

        public void tick() {//this is called every second
            TaskTime taskTime = queue.peek();
            if (taskTime.getTaskExecutionTime().isBefore(LocalDateTime.now())) {
                queue.remove(taskTime);
            }
        }

//        void schedule(Task task, long time) {
//            if (taskToTimeMap.containsKey(time)) {
//                    taskToTimeMap.get(time).add(task);
//            } else {
//                List<Task> tasks = new ArrayList<>();
//                tasks.add(task);
//                taskToTimeMap.put(time, tasks);
//            }
//        }
//
//        public void tick() {//this is called every second
//            List<Task> tasks = taskToTimeMap.get(elapsedTime);
//            if (tasks != null) {
//                for (Task task : tasks) {
//                    //task.doExec();
//                }
//                taskToTimeMap.remove(elapsedTime);
//            }
//            elapsedTime++;
//        }
    }

    public class Task {

    }


    public class TaskTime {

        private Task task;
        private LocalDateTime taskExecutionTime;

        public TaskTime(Task task, LocalDateTime taskExecutionTime) {
            this.task = task;
            this.taskExecutionTime = taskExecutionTime;
        }

        public Task getTask() {
            return task;
        }

        public LocalDateTime getTaskExecutionTime() {
            return taskExecutionTime;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TaskTime taskTime = (TaskTime) o;
            return Objects.equals(task, taskTime.task) &&
                    Objects.equals(taskExecutionTime, taskTime.taskExecutionTime);
        }

        @Override
        public int hashCode() {
            return Objects.hash(task, taskExecutionTime);
        }

        @Override
        public String toString() {
            return "TaskTime{" +
                    "Task=" + task +
                    ", taskExecutionTime=" + taskExecutionTime +
                    '}';
        }
    }

}
