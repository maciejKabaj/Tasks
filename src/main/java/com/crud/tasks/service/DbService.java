package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.exception.TaskNotFoundException;
import com.crud.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DbService {

    private final TaskRepository repository;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task getTaskById(long id) {
        return repository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " not found"));
    }

    public Task saveTask(final Task task) {
        return repository.save(task);
    }

    public Optional<Task> getTask(final Long id) {
        return repository.findById(id);
    }

    public void deleteTask(final Long taskId) {
        repository.deleteById(taskId);
    }
}