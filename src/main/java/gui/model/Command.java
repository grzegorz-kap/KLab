package gui.model;

import java.time.LocalDateTime;

public class Command {
    private String content;
    private LocalDateTime createdAt;

    public Command(String content) {
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
