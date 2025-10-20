package com.crud.tasks.trello.controller;

import com.crud.tasks.controller.TrelloController;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@WebMvcTest(TrelloController.class)
public class TrelloControllerTest {

    @MockitoBean
    private TrelloFacade trelloFacade;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldFetchTrelloBoards() throws Exception {
        //Given
        List<TrelloBoardDto> trelloBoardDtos = List.of(
                new TrelloBoardDto("1", "Trello board", List.of(new TrelloListDto("1", "Trello list", true)))
        );
        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoardDtos);
        //When & Then
        mockMvc.perform(get("/v1/trello/boards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].name", is("Trello board")));
        verify(trelloFacade, times(1)).fetchTrelloBoards();
    }

    @Test
    void shouldCreateTrelloCard() throws Exception {
        //Given
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "Created Trello card", "testURL", new Badges());
        TrelloCardDto trelloCardDto = new TrelloCardDto("Card name", "Card description", "top", "1");
        when(trelloFacade.createCard(any(TrelloCardDto.class))).thenReturn(createdTrelloCardDto);
        //When & Then
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(trelloCardDto);
        mockMvc.perform(post("/v1/trello/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.name", is("Created Trello card")))
                .andExpect(jsonPath("$.shortUrl", is("testURL")))
                .andExpect(jsonPath("$.badges").exists());
    }
}
