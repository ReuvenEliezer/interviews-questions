package com.interviews.questions.upsolverstorage;

import java.util.List;

public interface Storage {
    void write(String fullPath, Content content);

    Content read(String fullPath);

    void delete(String fullPath);

    List<Content> list(String fullPath);

    List<Content> listRecursively(String fullPath);
}
