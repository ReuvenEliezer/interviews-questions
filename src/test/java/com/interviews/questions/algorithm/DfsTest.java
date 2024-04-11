package com.interviews.questions.algorithm;

import org.junit.Test;

import java.util.*;

public class DfsTest {
    @Test
    public void findShortestPathBetween2PpointsInADistanceWeightedMap() {
        /**
         * https://stackoverflow.com/questions/17480022/java-find-shortest-path-between-2-points-in-a-distance-weighted-map
         */
        // mark all the vertices
        Vertex A = new Vertex("A");
        Vertex B = new Vertex("B");
        Vertex D = new Vertex("D");
        Vertex F = new Vertex("F");
        Vertex K = new Vertex("K");
        Vertex J = new Vertex("J");
        Vertex M = new Vertex("M");
        Vertex O = new Vertex("O");
        Vertex P = new Vertex("P");
        Vertex R = new Vertex("R");
        Vertex Z = new Vertex("Z");

        // set the edges and weight
        A.adjacencies = new Edge[]{new Edge(M, 8)};
        B.adjacencies = new Edge[]{new Edge(D, 11)};
        D.adjacencies = new Edge[]{new Edge(B, 11)};
        F.adjacencies = new Edge[]{new Edge(K, 23)};
        K.adjacencies = new Edge[]{new Edge(O, 40)};
        J.adjacencies = new Edge[]{new Edge(K, 25)};
        M.adjacencies = new Edge[]{new Edge(R, 8)};
        O.adjacencies = new Edge[]{new Edge(K, 40)};
        P.adjacencies = new Edge[]{new Edge(Z, 18)};
        R.adjacencies = new Edge[]{new Edge(P, 15)};
        Z.adjacencies = new Edge[]{new Edge(P, 18)};


        computePaths(A); // run Dijkstra
        System.out.println("Distance to " + Z + ": " + Z.minDistance);
        List<Vertex> path = getShortestPathTo(Z);
        System.out.println("Path: " + path);
    }

    class Vertex implements Comparable<Vertex> {
        public final String name;
        public Edge[] adjacencies;
        public double minDistance = Double.POSITIVE_INFINITY;

        public Vertex previous;

        public Vertex(String argName) {
            name = argName;
        }

        public String toString() {
            return name;
        }

        public int compareTo(Vertex other) {
            return Double.compare(minDistance, other.minDistance);
        }

    }
    class Edge {
        public final Vertex target;

        public final double weight;
        public Edge(Vertex argTarget, double argWeight) {
            target = argTarget;
            weight = argWeight;
        }

    }

    public void computePaths(Vertex source) {
        source.minDistance = 0.;
        Queue<Vertex> vertexQueue = new ArrayDeque<>();
        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies) {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);

                    v.minDistance = distanceThroughU;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
    }

    public List<Vertex> getShortestPathTo(Vertex target) {
        List<Vertex> path = new ArrayList<>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);

        Collections.reverse(path);
        return path;
    }
}



