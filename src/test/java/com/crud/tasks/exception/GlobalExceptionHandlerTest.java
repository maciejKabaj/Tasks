package com.crud.tasks.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GlobalExceptionHandlerTest {

    @Test
    void shouldHandleTaskNotFoundExceptionAndReturnErrorResponse() {
        //Given
        TaskNotFoundException taskNotFoundException = new TaskNotFoundException("Task Not Found");
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        //When
        ResponseEntity<Map<String, String>> handledTaskNotFoundException =
                globalExceptionHandler.handleTaskNotFoundException(taskNotFoundException);
        //Then
        assertEquals(HttpStatus.NOT_FOUND, handledTaskNotFoundException.getStatusCode());
        Map<String, String> body = handledTaskNotFoundException.getBody();
        assertTrue(body.containsKey("error"));
        assertEquals(taskNotFoundException.getMessage(), body.get("error"));
    }
}
