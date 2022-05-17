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
            for (int i = 0; i < split.length; i++) {
                Content content1;
                if (i < split.length - 1 || content instanceof Directory) {
                    content1 = new Directory(split[i]);
                } else {
                    content1 = content;
                }
                UpSolverNode upSolverNode;
                if (i == 0 && i < split.length - 1) {
                    upSolverNode = new UpSolverNode(content1, null);
                    prefixPathToStorageMap.put(split[0], upSolverNode);
                } else {
                    upSolverNode = createNewNode(content1, upSolverParentNode, split[i]);
                }
                upSolverParentNode = upSolverNode;
            }
        } else {
            //TODO search
            for (int i = 1; i < split.length; i++) {
                Map<String, UpSolverNode> children = upSolverParentNode.getChildren();
                String path = split[i];
                UpSolverNode upSolverNode = children.get(path);
                Content content1;
                if (upSolverNode == null) {
                    //TODO create it
                    if (i < split.length - 1 || content instanceof Directory) {
                        content1 = new Directory(split[i]);
                    } else {
                        content1 = content;
                    }
                    upSolverNode = createNewNode(content1, upSolverParentNode, split[i]);
                } else {
                    //TODO override
                    if (i == split.length - 1) {
                        if (content.name.equals(path) && content instanceof File) {
                            //TODO override
                            upSolverNode.setContent(content);
                        } else {
                            //TODO create new
                            if (content instanceof Directory) {
                                content1 = new Directory(path);
                            } else {
                                content1 = content;
                            }
                            upSolverNode = createNewNode(content1, upSolverParentNode, path);
                        }
                    }
                }
                upSolverParentNode = upSolverNode;
            }
        }
    }

    private UpSolverNode createNewNode(Content content, UpSolverNode parentNode, String path) {
        UpSolverNode upSolverRoot = new UpSolverNode(content, parentNode);
        parentNode.getChildren().put(path, upSolverRoot);
        return upSolverRoot;
    }

    private String[] splitPath(String fullPath) {
        return StringUtils.split(fullPath, "\\");
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

    private UpSolverNode findRelevantPath(String fullPath) {
        String[] split = splitPath(fullPath);
        UpSolverNode upSolverNode = prefixPathToStorageMap.get(split[0]);
        if (upSolverNode == null) {
            throw new NoSuchElementException();
        }

        for (int i = 1; i < split.length && upSolverNode.getChildren() != null; i++) {
            String s = split[i];
            upSolverNode = upSolverNode.getChildren().get(s);
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
        if (StringUtils.isEmpty(fullPath))
            throw new IllegalArgumentException();
    }
}
