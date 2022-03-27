package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.ExpertPlayer;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.table.island.BasicGroupIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeEntranceDiningRoomTest {
    private Game gameTest;
    private ExchangeEntranceDiningRoom cardTest;

    @BeforeEach
    void setUp() {
        gameTest = new ExpertGame();
        cardTest = new ExchangeEntranceDiningRoom(gameTest);
        gameTest.addCharacterCard(cardTest);
        gameTest.addPlayer(new ExpertPlayer("sara", Wizard.TYPE_2));
        gameTest.addPlayer(new ExpertPlayer("viola", Wizard.TYPE_3));
        for (int i = 0; i < 12; i++) {
            gameTest.getTable().addGroupIsland(new BasicGroupIsland());
        }
        gameTest.setCurrentPlayer(gameTest.getPlayerByIndex(0));

        gameTest.getPlayerByIndex(0).getSchoolBoard().addStudentToDiningRoom(Colour.YELLOW);
        gameTest.getPlayerByIndex(0).getSchoolBoard().addStudentToDiningRoom(Colour.YELLOW);
        gameTest.getPlayerByIndex(0).getSchoolBoard().addStudentToDiningRoom(Colour.YELLOW);
        gameTest.getPlayerByIndex(1).getSchoolBoard().addStudentToDiningRoom(Colour.GREEN);
        gameTest.getPlayerByIndex(0).getSchoolBoard().addStudentToEntrance(Colour.GREEN);
        gameTest.getPlayerByIndex(0).getSchoolBoard().addStudentToEntrance(Colour.GREEN);

        gameTest.getPlayerByIndex(0).getSchoolBoard().addProfessor(Colour.YELLOW);
        gameTest.getPlayerByIndex(1).getSchoolBoard().addProfessor(Colour.GREEN);
    }

    @Test
    void effect() {
    }

    @Test
    void setColourDiningRoomEntrance() {
        cardTest.setColourDiningRoomEntrance(Colour.YELLOW, Colour.GREEN);
        assertEquals(Colour.YELLOW, cardTest.getColourDiningRoom());
        assertEquals(Colour.GREEN, cardTest.getColourEntrance());
        assertTrue(gameTest.getPlayerByIndex(0).getSchoolBoard().hasProfessor(Colour.YELLOW));
        assertTrue(gameTest.getPlayerByIndex(1).getSchoolBoard().hasProfessor(Colour.GREEN));
        assertFalse(gameTest.getPlayerByIndex(0).getSchoolBoard().hasProfessor(Colour.GREEN));
        cardTest.setColourDiningRoomEntrance(Colour.YELLOW, Colour.GREEN);
        assertEquals(Colour.YELLOW, cardTest.getColourDiningRoom());
        assertEquals(Colour.GREEN, cardTest.getColourEntrance());
        assertTrue(gameTest.getPlayerByIndex(0).getSchoolBoard().hasProfessor(Colour.YELLOW));
        assertTrue(gameTest.getPlayerByIndex(0).getSchoolBoard().hasProfessor(Colour.GREEN));
        assertFalse(gameTest.getPlayerByIndex(1).getSchoolBoard().hasProfessor(Colour.GREEN));
    }
}