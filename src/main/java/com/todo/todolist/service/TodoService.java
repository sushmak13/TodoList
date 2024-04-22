package com.todo.todolist.service;

import com.todo.todolist.model.Todo;
import com.todo.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    @Autowired
    private TodoRepository repository;

    public List<Todo> findAll() {
        return repository.findAll();
    }

    public Optional<Todo> findById(Long id) {
        return repository.findById(id);
    }

    public Todo save(Todo todo) {
        return repository.save(todo);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

