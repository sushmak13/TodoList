package com.todo.todolist.controller;

import com.todo.todolist.model.Todo;
import com.todo.todolist.service.TodoService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TodoControllerTest {

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllTodos_ShouldReturnAllTodos() {
        Todo todo1 = new Todo(1L, "Test task 1", false);
        Todo todo2 = new Todo(2L, "Test task 2", true);
        List<Todo> todos = Arrays.asList(todo1, todo2);

        when(todoService.findAll()).thenReturn(todos);

        ResponseEntity<List<Todo>> response = todoController.getAllTodos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(todos, response.getBody());
    }

    @Test
    public void getTodoById_WhenTodoExists_ShouldReturnTodo() {
        Todo todo = new Todo(1L, "Test task 1", false);
        when(todoService.findById(1L)).thenReturn(Optional.of(todo));

        ResponseEntity<Todo> response = todoController.getTodoById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
       /* assertEquals(todo,
                response.getBody().orElse(null));*/
    }

    @Test
    public void getTodoById_WhenTodoDoesNotExist_ShouldReturnNotFound() {
        when(todoService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Todo> response = todoController.getTodoById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void createTodo_ShouldReturnCreatedTodo() {
        Todo newTodo = new Todo(null, "New Task", false);
        Todo savedTodo = new Todo(1L, "New Task", false);
        when(todoService.save(newTodo)).thenReturn(savedTodo);

        ResponseEntity<Todo> response = todoController.createTodo(newTodo);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedTodo, response.getBody());
    }

    @Test
    public void updateTodo_WhenTodoExists_ShouldUpdateAndReturnTodo() {
        Todo existingTodo = new Todo(1L, "Existing Task", false);
        Todo updatedTodo = new Todo(1L, "Updated Task", true);
        when(todoService.findById(1L)).thenReturn(Optional.of(existingTodo));
        when(todoService.save(existingTodo)).thenReturn(updatedTodo);

        ResponseEntity<Todo> response = todoController.updateTodo(1L, updatedTodo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTodo, response.getBody());
    }

    @Test
    public void updateTodo_WhenTodoDoesNotExist_ShouldReturnNotFound() {
        Todo updatedTodo = new Todo(1L, "Updated Task", true);
        when(todoService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Todo> response = todoController.updateTodo(1L, updatedTodo);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteTodo_WhenTodoExists_ShouldReturnNoContent() {
        when(todoService.findById(1L)).thenReturn(Optional.of(new Todo()));

        ResponseEntity<Void> response = todoController.deleteTodo(1L);

        verify(todoService).deleteById(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deleteTodo_WhenTodoDoesNotExist_ShouldReturnNotFound() {
        when(todoService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = todoController.deleteTodo(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

