package com.klab.gui.model;

import java.util.Date;

public class Command {
    private String content;
    private Date createdAt;

    private Command() {
    }

    public Command(String content) {
        this.content = content;
        this.createdAt = new Date();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
