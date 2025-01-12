package com.interviews.questions;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    @Test
    public void test() {
        // Java code to demonstrate Graph representation
// using ArrayList in Java
        // Creating a graph with 5 vertices
        int V = 5;
        List<List<Integer>> adj = new ArrayList<>(V);

        for (int i = 0; i < V; i++)
            adj.add(new ArrayList<>());

        // Adding edges one by one
        addEdge(adj, 0, 1);
        addEdge(adj, 0, 4);
        addEdge(adj, 1, 2);
        addEdge(adj, 1, 3);
        addEdge(adj, 1, 4);
        addEdge(adj, 2, 3);
        addEdge(adj, 3, 4);

        printGraph(adj);
    }

    // A utility function to add an edge in an
    // undirected graph
    static void addEdge(List<List<Integer>> adj, int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u);
    }

    // A utility function to print the adjacency list
    // representation of graph
    static void printGraph(List<List<Integer>> adj) {
        for (int i = 0; i < adj.size(); i++) {
            System.out.println("\nAdjacency list of vertex" + i);
            System.out.print("head");

            for (int j = 0; j < adj.get(i).size(); j++) {
                System.out.print(adj.get(i).get(j) + "->");
            }
            System.out.println();
        }
    }

}

