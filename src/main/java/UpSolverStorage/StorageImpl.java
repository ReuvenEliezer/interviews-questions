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
            upSolverParentNode = createRootNode(content, split[0], split.length > 1);
            for (int i = 1; i < split.length; i++) {
                String path = split[i];
                /**
                 * the next node that created will be a parent node for the next iteration
                 */
                upSolverParentNode = createNewNode(content, upSolverParentNode, path, i < split.length - 1);
            }
        } else {
            //TODO search
            for (int i = 1; i < split.length; i++) {
                Map<String, UpSolverNode> children = upSolverParentNode.getChildren();
                String path = split[i];
                UpSolverNode upSolverNode = children.get(path);
                if (upSolverNode == null) {
                    upSolverNode = createNewNode(content, upSolverParentNode, path, i < split.length - 1);
                } else if (i == split.length - 1 && content.name.equals(path) && content instanceof File) {
                    //TODO override
                    upSolverNode.setContent(content);
                }
                upSolverParentNode = upSolverNode;
            }
        }
    }

    @Override
    public Content read(String fullPath) {
        validatePath(fullPath);
        UpSolverNode upSolverNode = findRelevantPath(fullPath);
        if (upSolverNode == null) {
            throw new NoSuchElementException();
        }
        return upSolverNode.getContent();
    }

    @Override
    public void delete(String fullPath) {
        validatePath(fullPath);
        UpSolverNode upSolverNode = findRelevantPath(fullPath);
        if (upSolverNode == null) {
            throw new NoSuchElementException();
        }

        Map<String, UpSolverNode> children = upSolverNode.getParent().getChildren();
        children.remove(upSolverNode.getContent().name);
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
            addContentRecursive(upSolverNode.getChildren().values(), contents);
            return contents;
        }

        return contents;
    }

    private UpSolverNode createRootNode(Content content, String path, boolean isDirectory) {
        content = buildContentPath(content, path, isDirectory);
        UpSolverNode upSolverRoot = new UpSolverNode(content, null);
        prefixPathToStorageMap.put(path, upSolverRoot);
        return upSolverRoot;
    }

    private Content buildContentPath(Content content, String path, boolean isDirectory) {
        if (isDirectory || content instanceof Directory) {
            //create the path as Directory
            return new Directory(path);
        }
        return content;
    }

    private UpSolverNode createNewNode(Content content, UpSolverNode parentNode, String path, boolean isDirectory) {
        //content by value (not by reference)
        content = buildContentPath(content, path, isDirectory);
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

    private void addContentRecursive(Collection<UpSolverNode> nodes, List<Content> result) {
        for (UpSolverNode mode : nodes) {
            result.add(mode.getContent());
            addContentRecursive(mode.getChildren().values(), result);
        }
    }

    private void validatePath(String fullPath) {
        if (StringUtils.isEmpty(fullPath)) throw new IllegalArgumentException();
    }
}
