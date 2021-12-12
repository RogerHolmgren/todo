package com.test.todo;

public class TodoDtoGenerator {

    private static final String BASE_URL = "http://localhost:8080/todos";

    private TodoDtoGenerator() {}

    public static TodoDto get(Todo todo) {
        String url = BASE_URL + "/" + todo.getId();
        return new TodoDto(todo, url);
    }
}
