package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.MailCreatorService;
import com.crud.tasks.service.SimpleEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyEmailScheduler {

    private final SimpleEmailService emailService;
    private final TaskRepository taskRepository;
    private final AdminConfig adminConfig;
    private final MailCreatorService mailCreatorService;

    @Scheduled(cron = "0 5 10 * * *")
    public void sendDailyHtmlEmail() {
        long size = taskRepository.count();

        String message = "Here is your daily task summary!";

        emailService.send(
                new Mail(
                        adminConfig.getAdminMail(),
                        "Daily Tasks Summary",
                        mailCreatorService.buildDailyTasksEmail(message, size),
                        null
                )
        );
    }
}
