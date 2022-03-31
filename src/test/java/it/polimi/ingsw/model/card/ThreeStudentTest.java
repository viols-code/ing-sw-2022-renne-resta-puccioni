package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.ExpertPlayer;
import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThreeStudentTest {

    private Game gameTest;
    private ThreeStudent cardTest;

    @BeforeEach
    void setUp() {
        gameTest = new ExpertGame();
        cardTest = new ThreeStudent(gameTest);
        gameTest.addCharacterCard(cardTest);
        gameTest.addPlayer(new ExpertPlayer("sara", Wizard.TYPE_2, TowerColour.WHITE));
        gameTest.addPlayer(new ExpertPlayer("viola", Wizard.TYPE_3, TowerColour.BLACK));
        gameTest.setCurrentPlayer(gameTest.getPlayerByIndex(0));
        for (Colour colour : Colour.values()) {
            gameTest.getPlayerByIndex(0).getSchoolBoard().addStudentToDiningRoom(colour);
            gameTest.getPlayerByIndex(1).getSchoolBoard().addStudentToDiningRoom(colour);
            gameTest.getPlayerByIndex(1).getSchoolBoard().addStudentToDiningRoom(colour);
            gameTest.getPlayerByIndex(1).getSchoolBoard().addStudentToDiningRoom(colour);
            gameTest.getPlayerByIndex(1).getSchoolBoard().addProfessor(colour);
        }
    }

    @Test
    void effect() {
    }

    @Test
    void setColour() {
        cardTest.setColour(Colour.RED);
        for (Colour colour : Colour.values()) {
            if (colour.equals(Colour.RED)) {
                assertEquals(0, gameTest.getPlayerByIndex(0).getSchoolBoard().getDiningRoom(colour));
                assertEquals(0, gameTest.getPlayerByIndex(1).getSchoolBoard().getDiningRoom(colour));
            } else {
                assertEquals(1, gameTest.getPlayerByIndex(0).getSchoolBoard().getDiningRoom(colour));
                assertEquals(3, gameTest.getPlayerByIndex(1).getSchoolBoard().getDiningRoom(colour));
            }
        }
    }
}