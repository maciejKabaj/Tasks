package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.exception.TaskNotFoundException;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
public class DbServiceTest {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository repository;

    @Test
    void shouldFetchAllTasks() {
        //Given
        List<Task> taskList = List.of(new Task(1L, "Test title", "Test content"));
        when(repository.findAll()).thenReturn(taskList);
        //When
        List<Task> result = dbService.getAllTasks();
        //Then
        assertEquals(taskList.size(), result.size());
        assertEquals(taskList.get(0).getId(), result.get(0).getId());
        assertEquals(taskList.get(0).getTitle(), result.get(0).getTitle());
        assertEquals(taskList.get(0).getContent(), result.get(0).getContent());
    }

    @Test
    void shouldFetchTaskById() {
        //Given
        List<Task> taskList = List.of(new Task(1L, "Test title", "Test content"));
        when(repository.findById(1L)).thenReturn(Optional.of(taskList.get(0)));
        //When
        Task result = dbService.getTaskById(1L);
        //Then
        assertEquals(taskList.get(0).getId(), result.getId());
        assertEquals(taskList.get(0).getTitle(), result.getTitle());
        assertEquals(taskList.get(0).getContent(), result.getContent());
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        //Given
        List<Task> taskList = List.of(new Task(1L, "Test title", "Test content"));
        when(repository.findById(2L)).thenReturn(Optional.empty());
        //When & Then
        assertThrows(TaskNotFoundException.class, () -> dbService.getTaskById(2L));
    }

    @Test
    void shouldSaveTask() {
        //Given
        Task task = new Task(1L, "Test title", "Test content");
        when(repository.save(task)).thenReturn(task);
        //When
        Task result = dbService.saveTask(task);
        //Then
        assertEquals(task.getId(), result.getId());
        assertEquals(task.getTitle(), result.getTitle());
        assertEquals(task.getContent(), result.getContent());
    }

    @Test
    void shouldGetTask() {
        //Given
        Task task = new Task(1L, "Test title", "Test content");
        when(repository.findById(1L)).thenReturn(Optional.of(task));
        //When
        Optional<Task> result = dbService.getTask(1L);
        //Then
        assertTrue(result.isPresent());
        assertEquals(task.getId(), result.get().getId());
        assertEquals(task.getTitle(), result.get().getTitle());
        assertEquals(task.getContent(), result.get().getContent());
    }

    @Test
    void shouldDeleteTask() {
        //Given
        Long taskId = 1L;
        //When
        dbService.deleteTask(taskId);
        //Then
        verify(repository, times(1)).deleteById(taskId);
    }
}
