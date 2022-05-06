package it.polimi.ingsw.model.game;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.card.CharacterCard;
import it.polimi.ingsw.model.card.NoColour;
import it.polimi.ingsw.model.card.ProtectIsland;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
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
        if (gameController.getGame().getCharacterCardByIndex(0) instanceof ProtectIsland ||
                gameController.getGame().getCharacterCardByIndex(1) instanceof ProtectIsland ||
                gameController.getGame().getCharacterCardByIndex(2) instanceof ProtectIsland) {
            assertTrue(gameController.getGame().hasProtectIslandCard());
        } else {
            assertFalse(gameController.getGame().hasProtectIslandCard());
        }
    }

    @Test
    void getCoins() {
        assertEquals(20, gameController.getGame().getCoins());
        gameController.addPlayer("Viola", Wizard.TYPE_1);
        assertEquals(19, gameController.getGame().getCoins());
        gameController.addPlayer("Laura", Wizard.TYPE_2);
        assertEquals(18, gameController.getGame().getCoins());
        gameController.addPlayer("Sara", Wizard.TYPE_3);
        assertEquals(17, gameController.getGame().getCoins());
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

    @RepeatedTest(10)
    void setting() {
        gameController.addPlayer("Viola", Wizard.TYPE_1);
        gameController.addPlayer("Laura", Wizard.TYPE_2);
        gameController.addPlayer("Sara", Wizard.TYPE_3);
        gameController.playAssistantCard("Viola", 0);
        gameController.playAssistantCard("Laura", 1);
        gameController.playAssistantCard("Sara", 2);
        if (gameController.getGame().getCharacterCardByIndex(0).getCost() == 1) {
            gameController.playCharacterCard("Viola", 0);
            assertTrue(gameController.getGame().getHasPlayedCharacterCard());
            assertEquals(0, gameController.getGame().getPlayerByIndex(0).getCoins());
            assertEquals(2, gameController.getGame().getActiveCharacterCard().getCost());
        } else if (gameController.getGame().getCharacterCardByIndex(1).getCost() == 1) {
            gameController.playCharacterCard("Viola", 1);
            assertTrue(gameController.getGame().getHasPlayedCharacterCard());
            assertEquals(0, gameController.getGame().getPlayerByIndex(0).getCoins());
            assertEquals(2, gameController.getGame().getActiveCharacterCard().getCost());
        } else if (gameController.getGame().getCharacterCardByIndex(2).getCost() == 1) {
            gameController.playCharacterCard("Viola", 2);
            assertTrue(gameController.getGame().getHasPlayedCharacterCard());
            assertEquals(0, gameController.getGame().getPlayerByIndex(0).getCoins());
            assertEquals(2, gameController.getGame().getActiveCharacterCard().getCost());
        }
    }
}
