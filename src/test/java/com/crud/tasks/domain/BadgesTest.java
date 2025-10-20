package com.crud.tasks.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BadgesTest {

    @Test
    void shouldCreateAndReadFieldsFromBadges() {
        // Given
        Trello trello = new Trello();
        trello.setBoard(5);
        trello.setCard(10);
        AttachmentsByType attachmentsByType = new AttachmentsByType();
        attachmentsByType.setTrello(trello);
        Badges badges = new Badges();
        badges.setVotes(3);
        badges.setAttachmentsByType(attachmentsByType);
        // When & Then
        assertEquals(3, badges.getVotes());
        assertEquals(5, badges.getAttachmentsByType().getTrello().getBoard());
        assertEquals(10, badges.getAttachmentsByType().getTrello().getCard());
    }
}
