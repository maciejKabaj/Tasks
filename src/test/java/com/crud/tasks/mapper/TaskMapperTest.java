package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskMapperTest {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    void shouldMapToTask() {
        // Given
        TaskDto taskDto = new TaskDto(1L, "Title", "Content");
        // When
        Task task = taskMapper.mapToTask(taskDto);
        // Then
        assertEquals(taskDto.getId(), task.getId());
        assertEquals(taskDto.getTitle(), task.getTitle());
        assertEquals(taskDto.getContent(), task.getContent());
    }

    @Test
    void shouldMapToTaskDto() {
        // Given
        Task task = new Task(1L, "Title", "Content");
        // When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        // Then
        assertEquals(task.getId(), taskDto.getId());
        assertEquals(task.getTitle(), taskDto.getTitle());
        assertEquals(task.getContent(), taskDto.getContent());
    }

    @Test
    void shouldMapToTaskDtoList() {
        // Given
        List<Task> tasks = List.of(
                new Task(1L, "Title1", "Content1"),
                new Task(2L, "Title2", "Content2")
        );
        // When
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(tasks);
        // Then
        assertEquals(2, taskDtos.size());
        assertEquals("Title1", taskDtos.get(0).getTitle());
        assertEquals("Content2", taskDtos.get(1).getContent());
    }
}
