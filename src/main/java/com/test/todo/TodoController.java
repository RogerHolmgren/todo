package com.test.todo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
class TodoController {
    private final TodoRepository repository;

    TodoController(TodoRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/todos")
    List<TodoDto> getTodos() {
        return repository.findAll().stream()
                .map(TodoDtoGenerator::get)
                .collect(Collectors.toList());
    }

    @GetMapping("/todos/{id}")
    TodoDto getTodo(@PathVariable Long id) {
        return TodoDtoGenerator.get(
                repository.findById(id)
                        .orElseThrow(() -> new TodoNotFoundException(id)));
    }

    @PostMapping("/todos")
    TodoDto postTodo(@RequestBody Todo newTodo) {
        if (newTodo.getCompleted() == null) {
            newTodo.setCompleted(false);
        }

        return TodoDtoGenerator.get(repository.save(newTodo));
    }

    @PatchMapping("/todos/{id}")
    TodoDto patchTodo(@RequestBody Todo newTodo, @PathVariable Long id) {
        return repository.findById(id)
                .map(todo -> {
                    if (newTodo.getTitle() != null) {
                        todo.setTitle(newTodo.getTitle());
                    }
                    if (newTodo.getCompleted() != null) {
                        todo.setCompleted(newTodo.getCompleted());
                    }
                    if (newTodo.getOrder() != null) {
                        todo.setOrder(newTodo.getOrder());
                    }
                    return TodoDtoGenerator.get(repository.save(todo));
                })
                .orElseThrow(() -> new TodoNotFoundException(id));
    }

    @DeleteMapping("/todos/{id}")
    void deleteTodo(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @DeleteMapping("/todos")
    void deleteTodos() {
        repository.deleteAll();
    }
}