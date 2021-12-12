package com.test.todo;

public class TodoNotFoundException extends RuntimeException {

    TodoNotFoundException(Long id) {
        super("Could not find todo with id: " + id);
    }
}
