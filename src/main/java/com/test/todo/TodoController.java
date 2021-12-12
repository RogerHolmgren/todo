package com.test.todo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
class TodoController {

    String baseUrl = "/todos"; // TODO do better

    private final TodoRepository repository;

    TodoController(TodoRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/todos")
    List<TodoDto> all() {
        List<Todo> mylist = repository.findAll();

        return mylist.stream()
                .map(TodoDtoGenerator::get)
                .collect(Collectors.toList());
    }

    @PostMapping("/todos")
    TodoDto newTodo(@RequestBody Todo newTodo) {
        if (newTodo.getCompleted() == null) {
            newTodo.setCompleted(false);
        }

        return TodoDtoGenerator.get(repository.save(newTodo));
    }

    @GetMapping("/todos/{id}")
    TodoDto one(@PathVariable Long id) {

        return TodoDtoGenerator.get(
                repository.findById(id)
                        .orElseThrow(() -> new TodoNotFoundException(id)));
    }

    @PutMapping("/todos/{id}")
    TodoDto replaceTodo(@RequestBody Todo newTodo, @PathVariable Long id) {

        return repository.findById(id)
                .map(todo -> {
                    todo.setTitle(newTodo.getTitle());
                    todo.setCompleted(newTodo.getCompleted());
                    return TodoDtoGenerator.get(repository.save(todo));
                })
                .orElseGet(() -> {
                    newTodo.setId(id);
                    return TodoDtoGenerator.get(repository.save(newTodo));
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