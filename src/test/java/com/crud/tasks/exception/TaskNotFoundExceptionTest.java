package com.crud.tasks.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskNotFoundExceptionTest {

    @Test
    void shouldReturnCorrectMessage() {
        //Given
        TaskNotFoundException taskNotFoundException = new TaskNotFoundException("Task Not Found");
        //Then
        assertEquals("Task Not Found", taskNotFoundException.getMessage());
    }
}
