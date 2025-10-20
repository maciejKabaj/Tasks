package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrelloValidatorTest {

    private TrelloValidator trelloValidator = new TrelloValidator();

    @Test
    void validateCard() {
        //Given
        TrelloCard trelloCardWithTest = new TrelloCard("Trello card test", "Trello card description", "top", "1");
        TrelloCard trelloCardWithoutTest = new TrelloCard("Trello card", "Trello card description", "left", "2");
        //When
        trelloValidator.validateCard(trelloCardWithTest);
        trelloValidator.validateCard(trelloCardWithoutTest);
        //Then
        assertDoesNotThrow(() -> trelloValidator.validateCard(trelloCardWithTest));
        assertDoesNotThrow(() -> trelloValidator.validateCard(trelloCardWithoutTest));
    }

    @Test
    void validateTrelloBoards() {
        //Given
        List<TrelloBoard> trelloBoards = List.of(
                new TrelloBoard("1", "test", List.of(new TrelloList("1", "Trello list 1", true))),
                new TrelloBoard("2", "Trello board", List.of(new TrelloList("2", "Trello list 2", false)))
        );
        //When
        List<TrelloBoard> filteredBoard = trelloValidator.validateTrelloBoards(trelloBoards);
        //Then
        assertEquals(1, filteredBoard.size());
        assertEquals("Trello board", filteredBoard.get(0).getName());
    }
}
