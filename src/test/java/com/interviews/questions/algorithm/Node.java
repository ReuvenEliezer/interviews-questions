package com.interviews.questions.algorithm;

import java.util.HashSet;
import java.util.Set;

public class Node<T> {
    private T value;
    private Set<Node<T>> neighbors;

    public Node(T value) {
        this.value = value;
        this.neighbors = new HashSet<>();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Set<Node<T>> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(Set<Node<T>> neighbors) {
        this.neighbors = neighbors;
    }

    public void connect(Node<T> node) {
        if (this == node) throw new IllegalArgumentException("Can't connect node to itself");
        this.neighbors.add(node);
        node.neighbors.add(this);
    }
}