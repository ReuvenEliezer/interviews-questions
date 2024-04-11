package com.interviews.questions.algorithm;

import java.util.HashSet;
import java.util.Set;

public class Tree<T extends Comparable<T>> implements Comparable<Tree<T>> {
    private T value;
    private Tree<T> parent;
    private Set<Tree<T>> children;

    public Tree(T value) {
        this.value = value;
        this.children = new HashSet<>();
    }

    public Tree<T> getParent() {
        return parent;
    }

    public void setParent(Tree<T> parent) {
        this.parent = parent;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Set<Tree<T>> getChildren() {
        return children;
    }

    public Tree<T> addChild(T value) {
        Tree<T> newChild = new Tree<>(value);
        newChild.setParent(this);
        this.children.add(newChild);
        return newChild;
    }


    @Override
    public int compareTo(Tree<T> o) {
        return this.getValue().compareTo(o.getValue());
    }
}