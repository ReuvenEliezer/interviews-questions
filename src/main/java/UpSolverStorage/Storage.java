package UpSolverStorage;

import java.util.List;

public interface Storage {
    void write(String fullPath, Content content);

    Content read(String fullPath);

    List<String> list(String fullPath);

    List<String> listRecursively(String fullPath);
}
