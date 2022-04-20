package UpSolverStorage;

public class File extends Content {
    byte[] content;

    public File(byte[] content, String name) {
        this.content = content;
        this.name = name;
    }

    public void replaceContent(byte[] bytes) {
        this.content = bytes;
    }
}

