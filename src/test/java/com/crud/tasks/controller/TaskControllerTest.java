package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @MockitoBean
    private DbService service;

    @MockitoBean
    private TaskMapper taskMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldFetchEmptyTaskList() throws Exception {
        //Given
        when(service.getAllTasks()).thenReturn(List.of());
        when(taskMapper.mapToTaskDtoList(List.of())).thenReturn(List.of());
        //When & Then
        mockMvc.perform(get("/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldFetchTaskList() throws Exception {
        //Given
        when(service.getAllTasks()).thenReturn(
                List.of(new Task(1L, "Test title", "Test content")));
        when(taskMapper.mapToTaskDtoList(anyList())).thenReturn(
                List.of(new TaskDto(1L, "Test title", "Test content"))
        );
        //When & Then
        mockMvc.perform(get("/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test title")))
                .andExpect(jsonPath("$[0].content", is("Test content")));
    }

    @Test
    void shouldFetchSingleTask() throws Exception {
        //Given
        when(service.getTaskById(1L))
                .thenReturn(new Task(1L, "Test title", "Test content"));
        when(taskMapper.mapToTaskDto(any(Task.class)))
                .thenReturn(new TaskDto(1L, "Test title", "Test content"));
        //When & Then
        mockMvc.perform(get("/v1/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test title")))
                .andExpect(jsonPath("$.content", is("Test content")));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        //When & Then
        mockMvc.perform(delete("/v1/tasks/1"))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteTask(1L);
    }

    @Test
    void shouldUpdateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Updated title", "Updated content");
        when(taskMapper.mapToTask(any(TaskDto.class)))
                .thenReturn(new Task(1L, "Updated title", "Updated content"));
        when(service.saveTask(any(Task.class)))
                .thenReturn(new Task(1L, "Updated title", "Updated content"));
        when(taskMapper.mapToTaskDto(any(Task.class)))
                .thenReturn( new TaskDto(1L, "Updated title", "Updated content"));
        //When & Then
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(taskDto);
        mockMvc.perform(put("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Updated title")))
                .andExpect(jsonPath("$.content", is("Updated content")));
    }

    @Test
    void shouldCreateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "New title", "New content");
        when(taskMapper.mapToTask(any(TaskDto.class)))
                .thenReturn(new Task(1L, "New title", "New content"));
        //When & Then
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(taskDto);
        mockMvc.perform(post("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk());
        verify(service, times(1)).saveTask(any(Task.class));
        verify(taskMapper, times(1)).mapToTask(any(TaskDto.class));
    }
}

