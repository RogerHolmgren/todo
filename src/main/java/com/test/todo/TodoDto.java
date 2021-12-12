package com.test.todo;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class TodoDto {

    private Long id;
    private String title;
    private Boolean completed;
    private String url;

    // Lägg on url direkt och förenkla i Controllern
    TodoDto(Todo todo, String url) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.completed = todo.getCompleted();
        this.url = url;
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public String getUrl() {
        return url;
    }
}
