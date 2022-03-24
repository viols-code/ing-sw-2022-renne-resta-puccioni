package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.game.BasicGame;
import it.polimi.ingsw.model.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player playerTest;
    private Game game;

    @BeforeEach
    void setUp() {
        playerTest = new BasicPlayer("Sara", Wizard.TYPE_2);
        game = new BasicGame();
    }


    @Test
    void addAssistantCard() {
        AssistantCard card = game.getAssistantCard(1);
        playerTest.addAssistantCard(card);
        assertTrue(playerTest.isAssistantCardPresent(card));
    }

    @Test
    void removeAssistantCard() {
        AssistantCard card = game.getAssistantCard(1);
        playerTest.addAssistantCard(card);
        playerTest.removeAssistantCard(card);
        assertFalse(playerTest.isAssistantCardPresent(card));
    }

    @Test
    void setCurrentAssistantCard() {
        AssistantCard card = game.getAssistantCard(1);
        playerTest.addAssistantCard(card);
        playerTest.setCurrentAssistantCard(card);
        assertEquals(card, playerTest.getCurrentAssistantCard());
    }
    @Test
    void setHasAlreadyPlayed(){

    }

    @Test
    void getCoin() {
        assertThrows(IllegalAccessError.class, () -> playerTest.getCoins());
    }

    @Test
    void addCoins() {
        assertThrows(IllegalAccessError.class, () -> playerTest.addCoins(10));
    }

    @Test
    void removeCoins() {
        assertThrows(IllegalAccessError.class, () -> playerTest.removeCoins(10));
    }
}
