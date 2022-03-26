package it.polimi.ingsw.model.game;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.card.CharacterCard;
import it.polimi.ingsw.model.card.NoColour;
import it.polimi.ingsw.model.card.ProtectIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpertGameTest {

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
        } else {
            assertFalse(gameController.getGame().hasProtectIslandCard());
        }
    }

    @Test
    void getCoins() {
        gameController.getGame().setCoins(20);
        assertEquals(20, gameController.getGame().getCoins());
    }

    @Test
    void getHasPlayedCharacterCard() {
        gameController.getGame().setHasPlayedCharacterCard(true);
        assertTrue(gameController.getGame().getHasPlayedCharacterCard());
        gameController.getGame().setHasPlayedCharacterCard(false);
        assertFalse(gameController.getGame().getHasPlayedCharacterCard());
    }

    @Test
    void setActiveCharacterCard() {
        CharacterCard card1 = new NoColour(gameController.getGame());
        gameController.getGame().setActiveCharacterCard(card1);
        assertEquals(card1, gameController.getGame().getActiveCharacterCard());

    }
}
