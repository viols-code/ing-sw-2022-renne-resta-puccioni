package it.polimi.ingsw.model.game;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.card.ProtectIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExpertGameTest {

    private GameController gameController;

    @BeforeEach
    void setup() {
        gameController = new GameController(true, 3);
    }

    @Test
    void addCharacterCard() {

        assertEquals(3, gameController.getGame().getNumberOfCharacterCard());

    }

    @Test
    void hasProtectedIslandCard() {

        if (gameController.getGame().getCharacterCardsByIndex(0) instanceof ProtectIsland ||
                gameController.getGame().getCharacterCardsByIndex(1) instanceof ProtectIsland ||
                gameController.getGame().getCharacterCardsByIndex(2) instanceof ProtectIsland) {
            assertTrue(gameController.getGame().hasProtectIslandCard());
        }
        else {
            assertFalse(gameController.getGame().hasProtectIslandCard());
        }
    }
}
