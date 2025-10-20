package com.crud.tasks.scheduler;

import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.config.AdminConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.argThat;

@ExtendWith(MockitoExtension.class)
class EmailSchedulerTest {

    @InjectMocks
    private EmailScheduler emailScheduler;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AdminConfig adminConfig;

    @Test
    void shouldSendEmailWithSingleTaskMessage() {
        // Given
        when(taskRepository.count()).thenReturn(1L);
        when(adminConfig.getAdminMail()).thenReturn("admin@tasks.com");
        // When
        emailScheduler.sendInformationEmail();
        // Then
        verify(simpleEmailService, times(1)).send(argThat(mail ->
                mail.getMailTo().equals("admin@tasks.com") &&
                        mail.getSubject().equals("Tasks: Once a day email") &&
                        mail.getMessage().contains("1 task")));
    }

    @Test
    void shouldSendEmailWithMultipleTasksMessage() {
        // Given
        when(taskRepository.count()).thenReturn(5L);
        when(adminConfig.getAdminMail()).thenReturn("admin@tasks.com");
        // When
        emailScheduler.sendInformationEmail();
        // Then
        verify(simpleEmailService, times(1)).send(argThat(mail ->
                mail.getMailTo().equals("admin@tasks.com") &&
                        mail.getSubject().equals("Tasks: Once a day email") &&
                        mail.getMessage().contains("5 tasks")));
    }
}
