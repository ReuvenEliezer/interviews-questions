package UpSolverStorage;

import java.util.List;

public interface Storage {
    void write(String fullPath, byte[] content);

    byte[] read(String fullPath);

    List<String> list(String fullPath);

    List<String> listRecursively(String fullPath);
}
