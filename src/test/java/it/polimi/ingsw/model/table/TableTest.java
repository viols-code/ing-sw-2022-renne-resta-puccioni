package it.polimi.ingsw.model.table;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.table.island.BasicGroupIsland;
import it.polimi.ingsw.model.table.island.GroupIsland;
import it.polimi.ingsw.server.Lobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController(false, 2, new Lobby());
        gameController.setUp();
        gameController.addPlayer("Viola", Wizard.TYPE_4);
        gameController.addPlayer("Laura", Wizard.TYPE_3);
        gameController.setUpCharactersAndIslands();
    }

    @Test
    void addGroupIsland() {
        GroupIsland groupIsland = new BasicGroupIsland();
        assertEquals(12, gameController.getGame().getTable().getNumberOfGroupIsland());
        gameController.getGame().getTable().removeGroupIsland(gameController.getGame().getTable().getGroupIslandByIndex(0));
        assertEquals(11, gameController.getGame().getTable().getNumberOfGroupIsland());
        gameController.getGame().getTable().addGroupIsland(groupIsland);
        assertEquals(12, gameController.getGame().getTable().getNumberOfGroupIsland());
        assertEquals(groupIsland, gameController.getGame().getTable().getGroupIslandByIndex(11));
    }

    @Test
    void removeGroupIsland() {
        GroupIsland groupIsland;
        assertEquals(12, gameController.getGame().getTable().getNumberOfGroupIsland());
        groupIsland = gameController.getGame().getTable().getGroupIslandByIndex(0);
        gameController.getGame().getTable().removeGroupIsland(gameController.getGame().getTable().getGroupIslandByIndex(0));
        assertEquals(11, gameController.getGame().getTable().getNumberOfGroupIsland());
        for (int i = 0; i < gameController.getGame().getTable().getNumberOfGroupIsland(); i++) {
            assertNotEquals(groupIsland, gameController.getGame().getTable().getGroupIslandByIndex(i));
        }
    }

    @Test
    void getNextGroupIslandClockWise() {
        int num = gameController.getGame().getTable().getNumberOfGroupIsland();
        assertEquals(0, gameController.getGame().getTable().getMotherNaturePosition());
        for (int i = 0; i < num; i++) {
            assertEquals((i + 1) % num, gameController.getGame().getTable().getNextGroupIslandClockWise());
            gameController.getGame().getTable().setMotherNaturePosition(gameController.getGame().getTable().getNextGroupIslandClockWise());
        }
    }

    @Test
    void getIslandBefore() {
        int num = gameController.getGame().getTable().getNumberOfGroupIsland();
        for (int i = 0; i < num; i++) {
            assertEquals(gameController.getGame().getTable().getGroupIslandByIndex(i),
                    gameController.getGame().getTable().getIslandBefore((i + 1) % num));
        }
    }

    @Test
    void getIslandAfter() {
        int num = gameController.getGame().getTable().getNumberOfGroupIsland();
        for (int i = 0; i < num; i++) {
            assertEquals(gameController.getGame().getTable().getGroupIslandByIndex(i),
                    gameController.getGame().getTable().getIslandAfter((i - 1) % num));
        }
    }

    @Test
    void setMotherNaturePosition() {
        assertEquals(0, gameController.getGame().getTable().getMotherNaturePosition());
        assertTrue(gameController.getGame().getTable().getGroupIslandByIndex(0).getMotherNature());
        gameController.getGame().getTable().setMotherNaturePosition(1);
        assertEquals(1, gameController.getGame().getTable().getMotherNaturePosition());
        assertFalse(gameController.getGame().getTable().getGroupIslandByIndex(0).getMotherNature());
        assertTrue(gameController.getGame().getTable().getGroupIslandByIndex(1).getMotherNature());
    }

    @Test
    void addCLoudTile() {
        assertEquals(2, gameController.getGame().getTable().getNumberOfCloudTile());

        HashMap<Colour, Integer> students = new HashMap<>();
        for (Colour colour : Colour.values()) {
            students.put(colour, 0);
        }

        int pink = 0;
        for (int i = 0; i < gameController.getGame().getStudentNumberMovement(); i++) {
            Colour colour;
            colour = gameController.getGame().getTable().getBag().bagDrawStudent();
            if (colour == Colour.PINK) {
                pink++;
            }
            students.replace(colour, students.get(colour), students.get(colour) + 1);
        }

        CloudTile cloudTile = new CloudTile(students);
        gameController.getGame().getTable().addCLoudTile(cloudTile);
        assertEquals(3, gameController.getGame().getTable().getNumberOfCloudTile());
        assertEquals(pink, gameController.getGame().getTable().getCloudTilesByIndex(2).getTileStudents(Colour.PINK));
    }

    @Test
    void removeCLoudTile() {
        assertEquals(2, gameController.getGame().getTable().getNumberOfCloudTile());

        HashMap<Colour, Integer> students = new HashMap<>();
        for (Colour colour : Colour.values()) {
            students.put(colour, 0);
        }

        for (int i = 0; i < gameController.getGame().getStudentNumberMovement(); i++) {
            Colour colour;
            colour = gameController.getGame().getTable().getBag().bagDrawStudent();
            students.replace(colour, students.get(colour), students.get(colour) + 1);
        }

        CloudTile cloudTile = new CloudTile(students);
        gameController.getGame().getTable().addCLoudTile(cloudTile);
        assertEquals(3, gameController.getGame().getTable().getNumberOfCloudTile());


        gameController.getGame().getTable().removeCLoudTile(gameController.getGame().getTable().getCloudTilesByIndex(0));
        assertEquals(2, gameController.getGame().getTable().getNumberOfCloudTile());
    }
}