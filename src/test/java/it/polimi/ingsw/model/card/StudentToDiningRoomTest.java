package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.ExpertPlayer;
import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentToDiningRoomTest {

    private Game gameTest;
    private StudentToDiningRoom cardTest;

    @BeforeEach
    void setUp() {
        gameTest = new ExpertGame();
        cardTest = new StudentToDiningRoom(gameTest);
        gameTest.addCharacterCard(cardTest);
        gameTest.addPlayer(new ExpertPlayer("sara", Wizard.TYPE_2, TowerColour.WHITE));
        gameTest.addPlayer(new ExpertPlayer("viola", Wizard.TYPE_3, TowerColour.BLACK));
        gameTest.setCurrentPlayer(gameTest.getPlayerByIndex(0));
        settingBag();
        cardTest.setting();
        for (Colour colour : Colour.values()) {
            gameTest.getPlayerByIndex(0).getSchoolBoard().addStudentToDiningRoom(colour);
            gameTest.getPlayerByIndex(1).getSchoolBoard().addStudentToDiningRoom(colour);
            gameTest.getPlayerByIndex(1).getSchoolBoard().addProfessor(colour);
        }
    }

    @Test
    void effect() {
    }

    @RepeatedTest(100)
    void setColour() {
        int coins = 1;
        for (Colour colour : Colour.values()) {
            if (cardTest.getStudents(colour) >= 3) {
                cardTest.setColour(colour);
                assertEquals(2, gameTest.getPlayerByIndex(0).getSchoolBoard().getDiningRoom(colour));
                assertEquals(coins, gameTest.getPlayerByIndex(0).getCoins());
                assertTrue(gameTest.getPlayerByIndex(0).getSchoolBoard().hasProfessor(colour));
                assertFalse(gameTest.getPlayerByIndex(1).getSchoolBoard().hasProfessor(colour));
                cardTest.setColour(colour);
                coins++;
                assertEquals(3, gameTest.getPlayerByIndex(0).getSchoolBoard().getDiningRoom(colour));
                assertEquals(coins, gameTest.getPlayerByIndex(0).getCoins());
                cardTest.setColour(colour);
                assertEquals(4, gameTest.getPlayerByIndex(0).getSchoolBoard().getDiningRoom(colour));
                assertEquals(coins, gameTest.getPlayerByIndex(0).getCoins());
            }
        }





        for (Colour colour : Colour.values()) {
            if (cardTest.getStudents(colour) == 0) {
                assertThrows(IllegalArgumentException.class, () -> cardTest.setColour(colour));
            }
        }

    }

    private void settingBag() {

        for (Colour colour : Colour.values()) {
            for (int i = 0; i < 24; i++) {
                gameTest.getTable().getBag().addStudentBag(colour);
            }
        }
    }
}