package com.test.todo;

public class TodoDto {

    private final Long id;
    private final String title;
    private final Boolean completed;
    private final Integer order;
    private final String url;

    TodoDto(Todo todo, String url) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.completed = todo.getCompleted();
        this.order = todo.getOrder();
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

    public Integer getOrder() {
        return order;
    }

    public String getUrl() {
        return url;
    }
}
