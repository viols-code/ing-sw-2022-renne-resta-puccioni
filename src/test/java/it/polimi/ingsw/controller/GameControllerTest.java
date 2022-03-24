package it.polimi.ingsw.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class GameControllerTest {
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController(true, 2);
    }

    @Test
    void playCharacterCard() {
        int num = 0;
    }

    @Test
    void playAssistantCard() {
    }

    @Test
    void addPlayer() {
    }
}