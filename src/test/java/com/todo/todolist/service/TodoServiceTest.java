package com.todo.todolist.service;


import com.todo.todolist.model.Todo;
import com.todo.todolist.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TodoServiceTest {

    @Mock
    private TodoRepository repository;

    @InjectMocks
    private TodoService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAll_ShouldReturnAllTodos() {
        // Arrange
        Todo todo1 = new Todo();
        todo1.setDescription("Test Todo 1");
        todo1.setCompleted(false);
        Todo todo2 = new Todo();
        todo2.setDescription("Test Todo 2");
        todo2.setCompleted(true);
        when(repository.findAll()).thenReturn(Arrays.asList(todo1, todo2));

        // Act
        List<Todo> result = service.findAll();

        // Assert
        assertThat(result).hasSize(2);
    }

    @Test
    void findById_ShouldReturnTodo() {
        // Arrange
        Long id = 1L;
        Optional<Todo> todo = Optional.of(new Todo());
        todo.get().setId(id);
        when(repository.findById(id)).thenReturn(todo);

        // Act
        Optional<Todo> result = service.findById(id);

        // Assert
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(id);
    }

    @Test
    void save_ShouldSaveAndReturnTodo() {
        // Arrange
        Todo todo = new Todo();
        todo.setDescription("New Todo");
        when(repository.save(any(Todo.class))).thenReturn(todo);

        // Act
        Todo result = service.save(todo);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getDescription()).isEqualTo("New Todo");
    }

    @Test
    void deleteById_ShouldInvokeRepository() {
        // Arrange
        Long id = 1L;

        // Act
        service.deleteById(id);

        // Assert
        verify(repository, times(1)).deleteById(id);
    }
}
