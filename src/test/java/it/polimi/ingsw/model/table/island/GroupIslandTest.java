package it.polimi.ingsw.model.table.island;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.player.BasicPlayer;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupIslandTest {
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController(false, 2);
    }

    @Test
    void  checkSetting(){
        int numberBlue = 0;
        int numberRed = 0;
        int numberGreen = 0;
        int numberPink = 0;
        int numberYellow = 0;
        int num;

        for(int i = 0; i < gameController.getGame().getTable().getNumberOfGroupIsland(); i++){
            num = gameController.getGame().getTable().getGroupIslandByIndex(i).getIslandByIndex(0).getStudents(Colour.BLUE);
            numberBlue += num;
            num = gameController.getGame().getTable().getGroupIslandByIndex(i).getIslandByIndex(0).getStudents(Colour.RED);
            numberRed += num;
            num = gameController.getGame().getTable().getGroupIslandByIndex(i).getIslandByIndex(0).getStudents(Colour.GREEN);
            numberGreen+= num;
            num = gameController.getGame().getTable().getGroupIslandByIndex(i).getIslandByIndex(0).getStudents(Colour.PINK);
            numberPink+= num;
            num = gameController.getGame().getTable().getGroupIslandByIndex(i).getIslandByIndex(0).getStudents(Colour.YELLOW);
            numberYellow+= num;

        }

        assertEquals(2, numberBlue);
        assertEquals(2, numberRed);
        assertEquals(2, numberGreen);
        assertEquals(2, numberPink);
        assertEquals(2, numberYellow);
    }

    @Test
    void addSingleIsland() {
        assertEquals(12, gameController.getGame().getTable().getNumberOfGroupIsland());
        int number1 = gameController.getGame().getTable().getGroupIslandByIndex(0).getNumberStudent(Colour.BLUE);
        int number2 = gameController.getGame().getTable().getGroupIslandByIndex(1).getNumberStudent(Colour.BLUE);

        for (int i = 0; i < gameController.getGame().getTable().getNumberOfGroupIsland(); i++) {
            assertEquals(1, gameController.getGame().getTable().getGroupIslandByIndex(i).getNumberOfSingleIsland());
        }

        gameController.getGame().getTable().getGroupIslandByIndex(0).addSingleIsland(gameController.getGame().getTable().getGroupIslandByIndex(1).getIslandByIndex(0));
        gameController.getGame().getTable().removeGroupIsland(gameController.getGame().getTable().getGroupIslandByIndex(1));

        assertEquals(2, gameController.getGame().getTable().getGroupIslandByIndex(0).getNumberOfSingleIsland());
        assertEquals(11, gameController.getGame().getTable().getNumberOfGroupIsland());
        assertEquals(number1 + number2, gameController.getGame().getTable().getGroupIslandByIndex(0).getNumberStudent(Colour.BLUE));
    }

    @Test
    void removeMotherNature() {
        gameController.getGame().getTable().setMotherNaturePosition(0);
        assertTrue(gameController.getGame().getTable().getGroupIslandByIndex(0).getMotherNature());
        gameController.getGame().getTable().getGroupIslandByIndex(0).removeMotherNature();
        gameController.getGame().getTable().getGroupIslandByIndex(1).placeMotherNature();
        assertFalse(gameController.getGame().getTable().getGroupIslandByIndex(0).getMotherNature());
        assertTrue(gameController.getGame().getTable().getGroupIslandByIndex(1).getMotherNature());
    }

    @Test
    void placeMotherNature() {
        gameController.getGame().getTable().setMotherNaturePosition(0);
        assertTrue(gameController.getGame().getTable().getGroupIslandByIndex(0).getMotherNature());
        gameController.getGame().getTable().getGroupIslandByIndex(0).removeMotherNature();
        gameController.getGame().getTable().getGroupIslandByIndex(2).placeMotherNature();
        assertFalse(gameController.getGame().getTable().getGroupIslandByIndex(0).getMotherNature());
        assertTrue(gameController.getGame().getTable().getGroupIslandByIndex(2).getMotherNature());
    }

    @Test
    void changeInfluence() {
        Player playerTest = new BasicPlayer("Viola", Wizard.TYPE_4);
        assertNull(gameController.getGame().getTable().getGroupIslandByIndex(0).getInfluence());
        gameController.getGame().getTable().getGroupIslandByIndex(0).changeInfluence(playerTest);
        assertEquals(playerTest, gameController.getGame().getTable().getGroupIslandByIndex(0).getInfluence());
    }

    @Test
    void isNoEntryTile() {
        for (int i = 0; i < gameController.getGame().getTable().getNumberOfGroupIsland(); i++) {
            assertFalse(gameController.getGame().getTable().getGroupIslandByIndex(i).isNoEntryTile());
        }
    }

    @Test
    void getNumberOfNoEntryTile() {
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getTable().getGroupIslandByIndex(0).getNumberOfNoEntryTile());
    }

    @Test
    void addNoEntryTile() {
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getTable().getGroupIslandByIndex(1).addNoEntryTile());
    }

    @Test
    void removeNoEntryTile() {
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getTable().getGroupIslandByIndex(2).removeNoEntryTile());
    }
}