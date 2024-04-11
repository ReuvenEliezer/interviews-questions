package com.interviews.questions.upsolverstorage;

import lombok.Getter;

@Getter
public class File extends Content {
    private byte[] content;

    public File(byte[] content, String name) {
        this.content = content;
        this.prop = new com.interviews.questions.upsolverstorage.Prop(name);
    }

}

