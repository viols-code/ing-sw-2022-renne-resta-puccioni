package it.polimi.ingsw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player1;
    private Game game;

    @BeforeEach
    void setUp() {
        player1 = new Player("p1");
        game = new Game();
    }

    @Test
    void addAssistantCardList(){
        AssistantCard card= game.getAssistantCard(1);
        player1.addAssistantCardList(card);
        assertTrue(player1.getAssistantCardSet().contains(card));
    }

    @Test
    void playAssistantCard() {
        AssistantCard card= game.getAssistantCard(1);
        player1.addAssistantCardList(card);
        player1.playAssistantCard(card);
        assertFalse(player1.getAssistantCardSet().contains(card));
    }

    @Test
    void setCurrentAssistantCard() {
        AssistantCard card= game.getAssistantCard(1);
        player1.addAssistantCardList(card);
        player1.setCurrentAssistantCard(card);
        assertEquals(card,player1.getCurrentAssistantCard());
    }


    @Test
    void addCoins() {
        for (int i = 1; i <= 10; i++) {
            player1.addCoins(i);
            assertEquals(i + 1, player1.getCoins());
            player1.removeCoins(i);
        }
    }

    @Test
    void removeCoins() {
        player1.addCoins(10);
        for (int i = 1; i <= 10; i++) {
            player1.removeCoins(i);
            assertEquals(10 - i + 1, player1.getCoins());
            player1.addCoins(i);
        }

    }
}