package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpertPlayerTest {
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController(true, 2);
        gameController.getGame().addPlayer(new ExpertPlayer("sara", Wizard.TYPE_1));
        gameController.getGame().addPlayer(new ExpertPlayer("laura", Wizard.TYPE_2));
    }

    @Test
    void addCoins() {
        for (int i = 1; i <= 10; i++) {
            gameController.getGame().getPlayerByIndex(0).addCoins(i);
            assertEquals(i + 1, gameController.getGame().getPlayerByIndex(0).getCoins());
            gameController.getGame().getPlayerByIndex(0).removeCoins(i);
        }
    }

    @Test
    void removeCoins() {
        gameController.getGame().getPlayerByIndex(0).addCoins(10);
        for (int i = 1; i <= 10; i++) {
            gameController.getGame().getPlayerByIndex(0).removeCoins(i);
            assertEquals(10 - i + 1, gameController.getGame().getPlayerByIndex(0).getCoins());
            gameController.getGame().getPlayerByIndex(0).addCoins(i);
        }
    }

    @Test
    void equals() {
        assertFalse(gameController.getGame().getPlayerByIndex(0).equals(gameController.getGame().getPlayerByIndex(1)));
    }

    @Test
    void hashCodeTest() {
        assertNotEquals(gameController.getGame().getPlayerByIndex(0).hashCode(), gameController.getGame().getPlayerByIndex(1).hashCode());
    }

}