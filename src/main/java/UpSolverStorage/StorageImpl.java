package UpSolverStorage;

import org.thymeleaf.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class StorageImpl implements Storage {

    private static Map<String, UpSolverNode> prefixPathToStorageMap = new ConcurrentHashMap<>();
//    private static Map<String, ConcurrentHashMap<String, Content>> storage = new ConcurrentHashMap<>();


    @Override
    public void write(String fullPath, Content content) {
        //TODO impl
        validatePath(fullPath);
        String[] split = StringUtils.split(fullPath, "\\");

//        UpSolverNode upSolverNode = prefixPathToStorageMap.computeIfAbsent(split[0], e -> new UpSolverNode(split[0]));
        UpSolverNode upSolverRootNode = prefixPathToStorageMap.get(split[0]);
        UpSolverNode prevNode = null;
        if (upSolverRootNode == null) {
            //create it with children
            for (int i = 0; i < split.length; i++) {
                if (i == 0 && i < split.length - 1) {
                    upSolverRootNode = new UpSolverNode(content, null);
                    prefixPathToStorageMap.put(split[0], upSolverRootNode);
                } else {
                    upSolverRootNode = new UpSolverNode(content, prevNode);
                    prevNode.children.put(split[i], upSolverRootNode);
                }
                prevNode = upSolverRootNode;
            }

        } else {
            //TODO search
            for (String s : split) {
            }
        }

    }

    @Override
    public Content read(String fullPath) {
        //TODO impl
        validatePath(fullPath);
        String[] split = StringUtils.split(fullPath, "\\");
        UpSolverNode upSolverNode = prefixPathToStorageMap.get(split[0]);
        if (upSolverNode == null) {
            throw new NoSuchElementException();
        }

        for (int i = 1; i < split.length && upSolverNode.children != null; i++) {
            String s = split[i];
            upSolverNode = upSolverNode.children.get(s);
        }
        if (upSolverNode != null) {
            Content content = upSolverNode.content;
            return content;
//            if (content instanceof File) {
//                File file = (File) content;
//                return file.content;
//            }
        }
        throw new NoSuchElementException();
    }

    private UpSolverNode upSolverNodeRecursive(String prefix, Map<String, UpSolverNode> map) {
        UpSolverNode upSolverNode = map.get(prefix);
        if (upSolverNode.children == null) {
            return upSolverNode;
        }
        return upSolverNodeRecursive(upSolverNode.path, upSolverNode.children);
    }

    private void validatePath(String fullPath) {
        if (StringUtils.isEmpty(fullPath))
            throw new IllegalArgumentException();
    }

    @Override
    public List<String> list(String fullPath) {
        //TODO impl
        validatePath(fullPath);
        return Collections.emptyList();
    }

    @Override
    public List<String> listRecursively(String fullPath) {
        //TODO impl
        validatePath(fullPath);
        return Collections.emptyList();
    }
}
