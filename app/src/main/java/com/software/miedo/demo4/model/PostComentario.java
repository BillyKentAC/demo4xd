package com.software.miedo.demo4.model;

import java.io.Serializable;

public class PostComentario implements Serializable {

    private String content;

    public PostComentario(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
