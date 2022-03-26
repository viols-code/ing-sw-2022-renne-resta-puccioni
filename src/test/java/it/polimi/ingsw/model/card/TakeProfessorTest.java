package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.BasicPlayer;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.table.island.BasicGroupIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TakeProfessorTest {
    private TakeProfessor cardTest;
    private Game gameTest;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {
        gameTest = new ExpertGame();
        cardTest = new TakeProfessor(gameTest);

        gameTest.addCharacterCard(cardTest);
        for (int i = 0; i < 12; i++) {
            gameTest.getTable().addGroupIsland(new BasicGroupIsland());
        }

        settingBag();
        settingCard();

        player1 = new BasicPlayer("Viola", Wizard.TYPE_4);
        player2 = new BasicPlayer("Laura", Wizard.TYPE_1);

        gameTest.addPlayer(player1);
        gameTest.addPlayer(player2);
    }

    @Test
    void setting() {
        assertEquals(2, cardTest.getCost());
        assertEquals(2, gameTest.getNumberOfPlayer());
    }

    @Test
    void professor() {
        player1.getSchoolBoard().addStudentToDiningRoom(Colour.BLUE);
        player2.getSchoolBoard().addStudentToDiningRoom(Colour.RED);

        assertFalse(player1.getSchoolBoard().hasProfessor(Colour.BLUE));
        assertFalse(player1.getSchoolBoard().hasProfessor(Colour.GREEN));
        assertFalse(player1.getSchoolBoard().hasProfessor(Colour.YELLOW));
        assertFalse(player1.getSchoolBoard().hasProfessor(Colour.PINK));
        assertFalse(player1.getSchoolBoard().hasProfessor(Colour.RED));

        assertFalse(player2.getSchoolBoard().hasProfessor(Colour.RED));
        assertFalse(player2.getSchoolBoard().hasProfessor(Colour.GREEN));
        assertFalse(player2.getSchoolBoard().hasProfessor(Colour.YELLOW));
        assertFalse(player2.getSchoolBoard().hasProfessor(Colour.PINK));
        assertFalse(player2.getSchoolBoard().hasProfessor(Colour.BLUE));

        gameTest.setCurrentPlayer(player1);
        cardTest.professor();

        assertTrue(player1.getSchoolBoard().hasProfessor(Colour.BLUE));
        assertTrue(player1.getSchoolBoard().hasProfessor(Colour.GREEN));
        assertTrue(player1.getSchoolBoard().hasProfessor(Colour.YELLOW));
        assertTrue(player1.getSchoolBoard().hasProfessor(Colour.PINK));
        assertFalse(player1.getSchoolBoard().hasProfessor(Colour.RED));

        assertFalse(player2.getSchoolBoard().hasProfessor(Colour.RED));
        assertFalse(player2.getSchoolBoard().hasProfessor(Colour.GREEN));
        assertFalse(player2.getSchoolBoard().hasProfessor(Colour.YELLOW));
        assertFalse(player2.getSchoolBoard().hasProfessor(Colour.PINK));
        assertFalse(player2.getSchoolBoard().hasProfessor(Colour.BLUE));

        gameTest.setCurrentPlayer(player2);
        cardTest.professor();

        assertTrue(player2.getSchoolBoard().hasProfessor(Colour.RED));
        assertTrue(player2.getSchoolBoard().hasProfessor(Colour.GREEN));
        assertTrue(player2.getSchoolBoard().hasProfessor(Colour.YELLOW));
        assertTrue(player2.getSchoolBoard().hasProfessor(Colour.PINK));
        assertFalse(player2.getSchoolBoard().hasProfessor(Colour.BLUE));

        assertTrue(player1.getSchoolBoard().hasProfessor(Colour.BLUE));
        assertFalse(player1.getSchoolBoard().hasProfessor(Colour.GREEN));
        assertFalse(player1.getSchoolBoard().hasProfessor(Colour.YELLOW));
        assertFalse(player1.getSchoolBoard().hasProfessor(Colour.PINK));
        assertFalse(player1.getSchoolBoard().hasProfessor(Colour.RED));
    }

    @Test
    void incrementCost() {
        int cost = cardTest.getCost();
        assertEquals(cost, cardTest.getCost());
        cardTest.incrementCost();
        assertEquals(cost + 1, cardTest.getCost());
    }

    @Test
    void setColour() {
        for (Colour colour : Colour.values()) {
            assertThrows(IllegalAccessError.class, () -> cardTest.setColour(colour));
        }
    }

    @Test
    void setColourAndIsland() {
        for (Colour colour : Colour.values()) {
            assertThrows(IllegalAccessError.class, () -> cardTest.setColourAndIsland(colour, gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0)));
        }
    }

    @Test
    public void setColourDiningRoomEntrance() {
        for (Colour colour : Colour.values()) {
            assertThrows(IllegalAccessError.class, () -> cardTest.setColourDiningRoomEntrance(colour, colour));
        }
    }

    @Test
    public void setColourCardEntrance() {
        for (Colour colour : Colour.values()) {
            assertThrows(IllegalAccessError.class, () -> cardTest.setColourCardEntrance(colour, colour));
        }
    }

    @Test
    void setGroupIsland() {
        for (int i = 0; i < gameTest.getTable().getNumberOfGroupIsland(); i++) {
            int finalI = i;
            assertThrows(IllegalAccessError.class, () -> cardTest.setGroupIsland(finalI));
        }
    }

    private void settingBag() {
        for (Colour colour : Colour.values()) {
            for (int i = 0; i < 2; i++) {
                gameTest.getTable().getBag().addStudentBag(colour);
            }
        }

        for (int i = 1; i < 12; i++) {
            if (i == 5) i++;
            gameTest.getTable().getGroupIslandByIndex(i).getIslandByIndex(0).addStudent(gameTest.getTable().getBag().bagDrawStudent());
        }

        for (Colour colour : Colour.values()) {
            for (int i = 0; i < 24; i++) {
                gameTest.getTable().getBag().addStudentBag(colour);
            }
        }
    }

    private void settingCard() {
        for (int i = 0; i < gameTest.getNumberOfCharacterCard(); i++) {
            gameTest.getCharacterCardsByIndex(i).setting();
        }
    }
}