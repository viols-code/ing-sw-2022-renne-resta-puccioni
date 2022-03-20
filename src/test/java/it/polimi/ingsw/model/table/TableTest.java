package it.polimi.ingsw.model.table;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.BasicGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.table.island.BasicGroupIsland;
import it.polimi.ingsw.model.table.island.GroupIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    private Game gameTest;

    @BeforeEach
    void setUp() {
        gameTest = new BasicGame();
    }

    @Test
    void addGroupIsland() {
        GroupIsland groupIsland = new BasicGroupIsland();
        assertEquals(12, gameTest.getTable().getNumberOfGroupIsland());
        gameTest.getTable().removeGroupIsland(gameTest.getTable().getGroupIslandByIndex(0));
        assertEquals(11, gameTest.getTable().getNumberOfGroupIsland());
        gameTest.getTable().addGroupIsland(groupIsland);
        assertEquals(12, gameTest.getTable().getNumberOfGroupIsland());
        assertEquals(groupIsland, gameTest.getTable().getGroupIslandByIndex(11));
    }

    @Test
    void removeGroupIsland() {
        GroupIsland groupIsland;
        assertEquals(12, gameTest.getTable().getNumberOfGroupIsland());
        groupIsland = gameTest.getTable().getGroupIslandByIndex(0);
        gameTest.getTable().removeGroupIsland(gameTest.getTable().getGroupIslandByIndex(0));
        assertEquals(11, gameTest.getTable().getNumberOfGroupIsland());
        for (int i = 0; i < gameTest.getTable().getNumberOfGroupIsland(); i++) {
            assertNotEquals(groupIsland, gameTest.getTable().getGroupIslandByIndex(i));
        }
    }

    @Test
    void setMotherNaturePosition() {
        assertEquals(0, gameTest.getTable().getMotherNaturePosition());
        assertTrue(gameTest.getTable().getGroupIslandByIndex(0).getMotherNature());
        gameTest.getTable().setMotherNaturePosition(1);
        assertEquals(1, gameTest.getTable().getMotherNaturePosition());
        assertFalse(gameTest.getTable().getGroupIslandByIndex(0).getMotherNature());
        assertTrue(gameTest.getTable().getGroupIslandByIndex(1).getMotherNature());
    }

    @Test
    void addCLoudTile() {
        assertEquals(0, gameTest.getTable().getNumberOfCloudTile());

        HashMap<Colour, Integer> students = new HashMap<>();
        for (Colour colour : Colour.values()) {
            students.put(colour, 0);
        }

        int pink = 0;
        for (int i = 0; i < gameTest.getStudentNumberMovement(); i++) {
            Colour colour;
            colour = gameTest.getTable().getBag().bagDrawStudent();
            if (colour == Colour.PINK) {
                pink++;
            }
            students.replace(colour, students.get(colour), students.get(colour) + 1);
        }

        CloudTile cloudTile = new CloudTile(students);
        gameTest.getTable().addCLoudTile(cloudTile);
        assertEquals(1, gameTest.getTable().getNumberOfCloudTile());
        assertEquals(pink, gameTest.getTable().getCloudTilesByIndex(0).getTileStudents(Colour.PINK));
    }

    @Test
    void removeCLoudTile() {
        assertEquals(0, gameTest.getTable().getNumberOfCloudTile());

        HashMap<Colour, Integer> students = new HashMap<>();
        for (Colour colour : Colour.values()) {
            students.put(colour, 0);
        }

        for (int i = 0; i < gameTest.getStudentNumberMovement(); i++) {
            Colour colour;
            colour = gameTest.getTable().getBag().bagDrawStudent();
            students.replace(colour, students.get(colour), students.get(colour) + 1);
        }

        CloudTile cloudTile = new CloudTile(students);
        gameTest.getTable().addCLoudTile(cloudTile);
        assertEquals(1, gameTest.getTable().getNumberOfCloudTile());


        gameTest.getTable().removeCLoudTile(gameTest.getTable().getCloudTilesByIndex(0));
        assertEquals(0, gameTest.getTable().getNumberOfCloudTile());
    }
}