package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.game.BasicGame;
import it.polimi.ingsw.model.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController(false, 2);
        gameController.getGame().addPlayer(new BasicPlayer("sara",Wizard.TYPE_1));
        gameController.getGame().addPlayer(new BasicPlayer("laura",Wizard.TYPE_2));
    }


    @Test
    void addAssistantCard() {
        AssistantCard card = gameController.getGame().getAssistantCard(1);
        gameController.getGame().getPlayerByIndex(0).addAssistantCard(card);
        assertTrue(gameController.getGame().getPlayerByIndex(0).isAssistantCardPresent(card));

    }

    @Test
    void removeAssistantCard() {
        AssistantCard card = gameController.getGame().getAssistantCard(1);
        gameController.getGame().getPlayerByIndex(0).addAssistantCard(card);
        gameController.getGame().getPlayerByIndex(0).removeAssistantCard(card);
        assertFalse(gameController.getGame().getPlayerByIndex(0).isAssistantCardPresent(card));
    }

    @Test
    void setCurrentAssistantCard() {
        AssistantCard card = gameController.getGame().getAssistantCard(1);
        gameController.getGame().getPlayerByIndex(0).addAssistantCard(card);
        gameController.getGame().getPlayerByIndex(0).setCurrentAssistantCard(card);
        assertEquals(card, gameController.getGame().getPlayerByIndex(0).getCurrentAssistantCard());
    }

    @Test
    void setHasAlreadyPlayed() {

    }

    @Test
    void getCoin() {
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getPlayerByIndex(0).getCoins());
    }

    @Test
    void addCoins() {
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getPlayerByIndex(0).addCoins(10));
    }

    @Test
    void removeCoins() {
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getPlayerByIndex(0).removeCoins(10));
    }
}
