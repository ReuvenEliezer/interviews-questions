package UpSolverStorage;

import org.thymeleaf.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class StorageImpl implements Storage {

    private static Map<String, UpSolverNode> prefixPathToStorageMap;

    StorageImpl() {
        prefixPathToStorageMap = new ConcurrentHashMap<>();
    }

    @Override
    public void write(String fullPath, Content content) {
        validatePath(fullPath);
        String[] split = splitPath(fullPath);
        UpSolverNode upSolverParentNode = prefixPathToStorageMap.get(split[0]);
        if (upSolverParentNode == null) {
            //TODO create it with children
            upSolverParentNode = createRootNode(content, split[0]);
            for (int i = 1; i < split.length; i++) {
                String path = split[i];
                /**
                 * the next node that created will be a parent node for the next iteration
                 */
                upSolverParentNode = createNewNode(content, upSolverParentNode, path);
            }
        } else {
            //TODO search
            for (int i = 1; i < split.length; i++) {
                Map<String, UpSolverNode> children = upSolverParentNode.getChildren();
                String path = split[i];
                UpSolverNode upSolverNode = children.get(path);
                if (upSolverNode == null) {
                    //TODO create it
                    upSolverNode = createNewNode(content, upSolverParentNode, path);
                } else {
                    //TODO override
                    if (i == split.length - 1) {
                        if (content.name.equals(path) && content instanceof File) {
                            //TODO override
                            upSolverNode.setContent(content);
                        } else {
                            //TODO create new
                            upSolverNode = createNewNode(content, upSolverParentNode, path);
                        }
                    }
                }
                upSolverParentNode = upSolverNode;
            }
        }
    }

    @Override
    public Content read(String fullPath) {
        validatePath(fullPath);
        UpSolverNode upSolverNode = findRelevantPath(fullPath);
        if (upSolverNode != null) {
            return upSolverNode.getContent();
        }
        throw new NoSuchElementException();
    }

    @Override
    public List<Content> list(String fullPath) {
        validatePath(fullPath);
        UpSolverNode upSolverNode = findRelevantPath(fullPath);
        if (upSolverNode != null) {
            Map<String, UpSolverNode> children = upSolverNode.getChildren();
            return children.values().stream().map(UpSolverNode::getContent).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<Content> listRecursively(String fullPath) {
        validatePath(fullPath);
        UpSolverNode upSolverNode = findRelevantPath(fullPath);

        List<Content> contents = new ArrayList<>();
        if (upSolverNode != null) {
            addContent(upSolverNode.getChildren(), contents);
            return contents;
        }

        return contents;
    }

    private UpSolverNode createRootNode(Content content, String path) {
        UpSolverNode upSolverRoot = new UpSolverNode(content, null);
        prefixPathToStorageMap.put(path, upSolverRoot);
        return upSolverRoot;
    }

    private UpSolverNode createNewNode(Content content, UpSolverNode parentNode, String path) {
        UpSolverNode upSolverNode = new UpSolverNode(content, parentNode);
        parentNode.getChildren().put(path, upSolverNode);
        return upSolverNode;
    }

    private String[] splitPath(String fullPath) {
        return StringUtils.split(fullPath, "\\");
    }

    private UpSolverNode findRelevantPath(String fullPath) {
        String[] split = splitPath(fullPath);
        UpSolverNode upSolverNode = prefixPathToStorageMap.get(split[0]);
        if (upSolverNode == null) {
            throw new NoSuchElementException();
        }

        for (int i = 1; i < split.length && upSolverNode.getChildren() != null; i++) {
            String path = split[i];
            upSolverNode = upSolverNode.getChildren().get(path);
        }
        return upSolverNode;
    }

    private void addContent(Map<String, UpSolverNode> upSolverNodeMap, List<Content> result) {
        for (Map.Entry<String, UpSolverNode> entry : upSolverNodeMap.entrySet()) {
            UpSolverNode upSolverNode = entry.getValue();
            result.add(upSolverNode.getContent());
            addContent(upSolverNode.getChildren(), result);
        }
    }

    private void validatePath(String fullPath) {
        if (StringUtils.isEmpty(fullPath)) throw new IllegalArgumentException();
    }
}
