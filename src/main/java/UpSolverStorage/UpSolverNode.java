package UpSolverStorage;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class UpSolverNode {
    private Content content;
    private UpSolverNode parent;

    private Map<String, UpSolverNode> children = new ConcurrentHashMap<>();

    public UpSolverNode(Content content, UpSolverNode parent) {
        this.content = content;
        this.parent = parent;
    }

}
