package com.test.todo;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
class TodoController {

    private final TodoRepository repository;

    TodoController(TodoRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/todos")
    List<Todo> all() {
        return repository.findAll();
    }

    @PostMapping("/todos")
    Todo newTodo(@RequestBody Todo newTodo) {
        if (newTodo.getCompleted() == null) {
            newTodo.setCompleted(false);
        }

        return repository.save(newTodo);
    }

    @GetMapping("/todos/{id}")
    Todo one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
    }

    @PutMapping("/todos/{id}")
    Todo replaceTodo(@RequestBody Todo newTodo, @PathVariable Long id) {

        return repository.findById(id)
                .map(todo -> {
                    todo.setTitle(newTodo.getTitle());
                    todo.setCompleted(newTodo.getCompleted());
                    return repository.save(todo);
                })
                .orElseGet(() -> {
                    newTodo.setId(id);
                    return repository.save(newTodo);
                });
    }

    @DeleteMapping("/todos/{id}")
    void deleteTodo(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @DeleteMapping("/todos")
    void deleteTodos() {
        repository.deleteAll();
    }

    static class TodoNotFoundException extends RuntimeException {
        TodoNotFoundException(Long id) {
            super("Could not find todo with id: " + id);
        }
    }
}