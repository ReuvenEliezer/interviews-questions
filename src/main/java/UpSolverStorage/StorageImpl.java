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
        String[] split = StringUtils.split(fullPath, "\\");
        UpSolverNode upSolverRootNode = prefixPathToStorageMap.get(split[0]);
        if (upSolverRootNode == null) {
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
                    upSolverNode = new UpSolverNode(content1, upSolverRootNode);
                    upSolverRootNode.getChildren().put(split[i], upSolverNode);
                }
                upSolverRootNode = upSolverNode;
            }
        } else {
            //TODO search
            for (int i = 1; i < split.length; i++) {
                Map<String, UpSolverNode> children = upSolverRootNode.getChildren();
                String path = split[i];
                UpSolverNode upSolverRoot = children.get(path);
                Content content1;
                if (upSolverRoot == null) {
                    //TODO create it
                    if (i < split.length - 1 || content instanceof Directory) {
                        content1 = new Directory(split[i]);
                    } else {
                        content1 = content;
                    }
                    upSolverRoot = new UpSolverNode(content1, upSolverRootNode);
                    upSolverRoot.setContent(content1);
                    upSolverRootNode.getChildren().put(split[i], upSolverRoot);
                } else {
                    //TODO override
                    if (i == split.length - 1) {
                        if (content.name.equals(split[i]) && content instanceof File) {
                            //TODO override
                            upSolverRoot.setContent(content);
                        } else {
                            //TODO create new
                            if (content instanceof Directory) {
                                content1 = new Directory(split[i]);
                            } else {
                                content1 = content;
                            }
                            upSolverRoot = new UpSolverNode(content1, upSolverRootNode);
                            upSolverRoot.setContent(content1);
                            upSolverRootNode.getChildren().put(split[i], upSolverRoot);
                        }
                    }
                }
                upSolverRootNode = upSolverRoot;
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

        if (upSolverNode != null) {
            List<Content> contents = new ArrayList<>();
            addContent(upSolverNode.getChildren(), contents);
            return contents;
        }

        return Collections.emptyList();
    }

    private UpSolverNode findRelevantPath(String fullPath) {
        String[] split = StringUtils.split(fullPath, "\\");
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
