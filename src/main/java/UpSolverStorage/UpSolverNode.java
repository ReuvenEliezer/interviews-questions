package UpSolverStorage;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class UpSolverNode {
    private String path;
    private Content content;
    private UpSolverNode parent;

    private Map<String, UpSolverNode> children = new ConcurrentHashMap<>();

    public UpSolverNode(Content content, UpSolverNode parent) {
        this.path = content.name;
        this.content = content;
        this.parent = parent;
    }

    public UpSolverNode(String path) {
        this.path = path;
    }
}
