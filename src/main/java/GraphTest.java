import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class GraphTest {

    /**
     * https://algorithms.tutorialhorizon.com/determine-the-order-of-tests-when-tests-have-dependencies-on-each-other/
     * https://www.geeksforgeeks.org/find-the-ordering-of-tasks-from-given-dependencies/
     * https://www.geeksforgeeks.org/topological-sorting/
     */

    @Test
    public void test() {
        //https://algorithms.tutorialhorizon.com/determine-the-order-of-tests-when-tests-have-dependencies-on-each-other/
        List<Character> testCase = new ArrayList<>();
        testCase.add('A');
        testCase.add('B');
        testCase.add('C');
        testCase.add('D');
        testCase.add('E');
        testCase.add('F');
        testCase.add('G');
        int vertices = testCase.size();
        Graph graph = new Graph(vertices);
        graph.addEgde('A', 'B', testCase);
        graph.addEgde('A', 'C', testCase);
        graph.addEgde('B', 'D', testCase);
        graph.addEgde('B', 'E', testCase);
        graph.addEgde('C', 'D', testCase);
        graph.addEgde('D', 'E', testCase);
        graph.addEgde('G', 'E', testCase);
        graph.addEgde('A', 'G', testCase);
        graph.topologicalSorting(testCase);
    }

    class Node {
        char source;
        char destination;

        Node(char source, char destination) {
            this.source = source;
            this.destination = destination;
        }
    }

    class Graph {
        int vertices;
        LinkedList<Node>[] adjList;

        Graph(int vertices) {
            this.vertices = vertices;
            adjList = new LinkedList[vertices];
            for (int i = 0; i < vertices; i++) {
                adjList[i] = new LinkedList<>();
            }
        }

        public void addEgde(char source, char destination, List<Character> testCases) {
            Node node = new Node(source, destination);
            adjList[testCases.indexOf(source)].addFirst(node);
        }

        public void topologicalSorting(List<Character> testCases) {
            boolean[] visited = new boolean[vertices];
            Stack<Character> stack = new Stack<>();
            //visit from each node if not already visited
            for (int i = 0; i < vertices; i++) {
                if (!visited[i]) {
                    topologicalSortUtil(testCases.get(i), visited, stack, testCases);
                }
            }
            System.out.println("Test Case Sequence: ");
            int size = stack.size();
            for (int i = 0; i < size; i++) {
                System.out.print(stack.pop() + " ");
            }
        }

        public void topologicalSortUtil(char sftwr, boolean[] visited, Stack<Character> stack, List<Character> testCases) {
            int index = testCases.indexOf(sftwr);
            visited[index] = true;
            for (int i = 0; i < adjList[testCases.indexOf(sftwr)].size(); i++) {
                Node node = adjList[index].get(i);
                if (!visited[testCases.indexOf(node.destination)])
                    topologicalSortUtil(node.destination, visited, stack, testCases);
            }
            stack.push(sftwr);
        }
    }


}
