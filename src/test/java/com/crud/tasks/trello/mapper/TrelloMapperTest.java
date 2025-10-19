package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TrelloMapperTest {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    void shouldMapTrelloBoardDtoListToTrelloBoardList() {
        //Given
        List<TrelloBoardDto> trelloBoardDtos = List.of(
                new TrelloBoardDto("1", "Test Board 1", List.of(new TrelloListDto("1", "Test List 1", true))),
                new TrelloBoardDto("2", "Test Board 2", List.of(new TrelloListDto("2", "Test List 2", false)))
        );
        //When
        List<TrelloBoard> mappedBoardList = trelloMapper.mapToBoards(trelloBoardDtos);
        //Then
        assertEquals(2, mappedBoardList.size());
        assertEquals("1", mappedBoardList.get(0).getId());
        assertEquals("2", mappedBoardList.get(1).getId());
        assertEquals("Test Board 1", mappedBoardList.get(0).getName());
        assertEquals("Test Board 2", mappedBoardList.get(1).getName());
        assertEquals(1, mappedBoardList.get(0).getLists().size());
        assertEquals("1", mappedBoardList.get(0).getLists().get(0).getId());
        assertEquals("2", mappedBoardList.get(1).getLists().get(0).getId());
        assertEquals("Test List 1", mappedBoardList.get(0).getLists().get(0).getName());
        assertEquals("Test List 2", mappedBoardList.get(1).getLists().get(0).getName());
        assertTrue(mappedBoardList.get(0).getLists().get(0).isClosed());
        assertFalse(mappedBoardList.get(1).getLists().get(0).isClosed());
    }

    @Test
    void shouldMapTrelloBoardListToTrelloBoardDtoList() {
        //Given
        List<TrelloBoard> trelloBoards = List.of(
                new TrelloBoard("1", "Test Board 1", List.of(new TrelloList("1", "Test List 1", true))),
                new TrelloBoard("2", "Test Board 2", List.of(new TrelloList("2", "Test List 2", false)))
        );
        //When
        List<TrelloBoardDto> mappedBoardDtoList = trelloMapper.mapToBoardsDto(trelloBoards);
        //Then
        assertEquals(2, mappedBoardDtoList.size());
        assertEquals("1", mappedBoardDtoList.get(0).getId());
        assertEquals("2", mappedBoardDtoList.get(1).getId());
        assertEquals("Test Board 1", mappedBoardDtoList.get(0).getName());
        assertEquals("Test Board 2", mappedBoardDtoList.get(1).getName());
        assertEquals(1, mappedBoardDtoList.get(0).getLists().size());
        assertEquals("1", mappedBoardDtoList.get(0).getLists().get(0).getId());
        assertEquals("2", mappedBoardDtoList.get(1).getLists().get(0).getId());
        assertEquals("Test List 1", mappedBoardDtoList.get(0).getLists().get(0).getName());
        assertEquals("Test List 2", mappedBoardDtoList.get(1).getLists().get(0).getName());
        assertTrue(mappedBoardDtoList.get(0).getLists().get(0).isClosed());
        assertFalse(mappedBoardDtoList.get(1).getLists().get(0).isClosed());
    }

    @Test
    void shouldMapTrelloListDtoListToTrelloList() {
        //Given
        List<TrelloListDto> trelloListDtos = List.of(
                new TrelloListDto("1", "Test List 1", false),
                new TrelloListDto("2", "Test List 2", true)
        );
        //When
        List<TrelloList> mappedList = trelloMapper.mapToList(trelloListDtos);
        //Then
        assertEquals(2, mappedList.size());
        assertEquals("1", mappedList.get(0).getId());
        assertEquals("2", mappedList.get(1).getId());
        assertEquals("Test List 1", mappedList.get(0).getName());
        assertEquals("Test List 2", mappedList.get(1).getName());
        assertFalse(mappedList.get(0).isClosed());
        assertTrue(mappedList.get(1).isClosed());
    }

    @Test
    void shouldMapTrelloListToTrelloListDtoList() {
        //Given
        List<TrelloList> trelloLists = List.of(
                new TrelloList("1", "Test List 1", false),
                new TrelloList("2", "Test List 2", true)
        );
        //When
        List<TrelloListDto> mappedList = trelloMapper.mapToListDto(trelloLists);
        //Then
        assertEquals(2, mappedList.size());
        assertEquals("1", mappedList.get(0).getId());
        assertEquals("2", mappedList.get(1).getId());
        assertEquals("Test List 1", mappedList.get(0).getName());
        assertEquals("Test List 2", mappedList.get(1).getName());
        assertFalse(mappedList.get(0).isClosed());
        assertTrue(mappedList.get(1).isClosed());
    }

    @Test
    void shouldMapTrelloCardToTrelloCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("Card Name", "Card Description", "Card Pos", "Card List");
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertEquals(trelloCard.getName(), trelloCardDto.getName());
        assertEquals(trelloCard.getDescription(), trelloCardDto.getDescription());
        assertEquals(trelloCard.getPos(), trelloCardDto.getPos());
        assertEquals(trelloCard.getListId(), trelloCardDto.getListId());
    }

    @Test
    void shouldMapTrelloCardDtoToTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Card name", "Card description", "pos", "listId");
        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertEquals(trelloCardDto.getName(), trelloCard.getName());
        assertEquals(trelloCardDto.getDescription(), trelloCard.getDescription());
        assertEquals(trelloCardDto.getPos(), trelloCard.getPos());
        assertEquals(trelloCardDto.getListId(), trelloCard.getListId());
    }
}
