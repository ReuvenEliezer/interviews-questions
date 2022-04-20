package UpSolverStorage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UpSolverNode {
    String path;
    Content content;
    UpSolverNode parent;

    Map<String, UpSolverNode> children = new ConcurrentHashMap<>();

    public UpSolverNode(Content content, UpSolverNode parent) {
        this.path = content.name;
        this.content = content;
        this.parent = parent;
    }

    public UpSolverNode(String path) {
        this.path = path;

    }
}
