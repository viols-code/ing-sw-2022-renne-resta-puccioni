package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.ExpertPlayer;
import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    void setColour() {
        List<Colour> colourList = new ArrayList<>();
        for (Colour colour : Colour.values()) {
            if (cardTest.getStudents(colour) > 0) {
                colourList.add(colour);
            }
        }
        cardTest.setColour(colourList.get(0));
        assertEquals(2, gameTest.getPlayerByIndex(0).getSchoolBoard().getDiningRoom(colourList.get(0)));
        assertTrue(gameTest.getPlayerByIndex(0).getSchoolBoard().hasProfessor(colourList.get(0)));
        assertFalse(gameTest.getPlayerByIndex(1).getSchoolBoard().hasProfessor(colourList.get(0)));
    }

    private void settingBag() {

        for (Colour colour : Colour.values()) {
            for (int i = 0; i < 24; i++) {
                gameTest.getTable().getBag().addStudentBag(colour);
            }
        }
    }
}