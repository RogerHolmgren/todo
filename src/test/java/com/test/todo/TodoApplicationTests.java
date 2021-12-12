package com.test.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TodoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TodoRepository repository;

    @Test
    void whenCallingApiRoot_itShouldReturnOk() throws Exception {
        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk());
    }

    @Test
    void whenPostingANewTodo_itShouldBeReturnedWithAnId() throws Exception {
        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Todo())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Test
    void whenBooleanValuesIsNull_itShouldBeSetToFalseAsDefault() throws Exception {
        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Todo())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(false));
    }


    @Test
    void whenPatchingAValue_otherValuesShouldBeUnchanged() throws Exception {
        // Setup
        Todo originalTodo = getTodo();

        // Test
        Todo patchedTodo = new Todo();
        patchedTodo.setTitle("Updated Title");

        mockMvc.perform(patch("/todos/" + originalTodo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchedTodo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(patchedTodo.getTitle()))
                .andExpect(jsonPath("$.completed").value(originalTodo.getCompleted()))
                .andExpect(jsonPath("$.order").value(originalTodo.getOrder()));
    }

    private Todo getTodo() {
        Todo todo = new Todo();
        todo.setTitle("Original");
        todo.setCompleted(false);
        todo.setOrder(10);
        return repository.save(todo);
    }
}
