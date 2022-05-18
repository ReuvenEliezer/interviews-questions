package UpSolverStorage;

import lombok.Getter;

@Getter
public class File extends Content {
    private byte[] content;

    public File(byte[] content, String name) {
        this.content = content;
        this.name = name;
    }

}

