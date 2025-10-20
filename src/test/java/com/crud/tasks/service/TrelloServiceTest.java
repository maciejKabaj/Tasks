package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.argThat;

@ExtendWith(MockitoExtension.class)
class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Mock
    private AdminConfig adminConfig;

    @Test
    void shouldFetchTrelloBoards() {
        // Given
        List<TrelloBoardDto> trelloBoards = List.of(
                new TrelloBoardDto("1", "Test Board", List.of())
        );
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoards);
        // When
        List<TrelloBoardDto> result = trelloService.fetchTrelloBoards();
        // Then
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
        assertEquals("Test Board", result.get(0).getName());
        verify(trelloClient, times(1)).getTrelloBoards();
    }

    @Test
    void shouldSendEmailWhenCardIsCreated() {
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Card Name", "Desc", "top", "1");
        CreatedTrelloCardDto createdCardDto = new CreatedTrelloCardDto("1", "Card Name", "http://test.com", null);
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdCardDto);
        when(adminConfig.getAdminMail()).thenReturn("admin@tasks.com");
        // When
        CreatedTrelloCardDto result = trelloService.createTrelloCard(trelloCardDto);
        // Then
        assertEquals(createdCardDto, result);
        verify(emailService, times(1)).send(argThat(mail ->
                mail.getMailTo().equals("admin@tasks.com") &&
                        mail.getSubject().equals("Tasks: New Trello card") &&
                        mail.getMessage().contains("Card Name")));
    }

    @Test
    void shouldNotSendEmailWhenCardIsNull() {
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Card Name", "Desc", "top", "1");
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(null);
        // When
        CreatedTrelloCardDto result = trelloService.createTrelloCard(trelloCardDto);
        // Then
        assertNull(result);
        verify(emailService, never()).send(any());
    }
}
