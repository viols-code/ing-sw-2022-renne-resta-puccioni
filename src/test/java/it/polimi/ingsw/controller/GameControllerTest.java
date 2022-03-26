package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController(false, 2);
    }

    @Test
    void setting() {
        assertEquals(8, gameController.getGame().getNumberOfTowersPerPlayer());
        assertEquals(7, gameController.getGame().getNumberStudentsEntrance());
        assertEquals(3, gameController.getGame().getStudentNumberMovement());
        assertEquals(12, gameController.getGame().getTable().getNumberOfGroupIsland());
        for (int i = 0; i < 12; i++) {
            int num = 0;
            for (Colour colour : Colour.values()) {
                num += gameController.getGame().getTable().getGroupIslandByIndex(i).getIslandByIndex(0).getStudents(colour);
            }
            if (i == 0 || i == 6) {
                assertEquals(0, num);
            } else {
                assertEquals(1, num);
            }
        }

        int size = 0;
        for (Colour colour : Colour.values()) {
            size += gameController.getGame().getTable().getBag().getBagStudent(colour);
        }
        assertEquals(114, size);

        assertEquals(2, gameController.getGame().getTable().getNumberOfCloudTile());

        for (int i = 0; i < gameController.getGame().getTable().getNumberOfCloudTile(); i++) {
            int num = 0;
            for (Colour colour : Colour.values()) {
                num += gameController.getGame().getTable().getCloudTilesByIndex(i).getTileStudents(colour);
            }
            assertEquals(3, num);
        }
    }

    @Test
    void playAssistantCard() {
        gameController.addPlayer("Viola", Wizard.TYPE_1);
        gameController.addPlayer("Laura", Wizard.TYPE_2);
        gameController.moveStudentToIsland(0, Colour.PINK, 0, 0);
        assertEquals(0, gameController.getGame().getTable().getGroupIslandByIndex(0).getIslandByIndex(0).getStudents(Colour.PINK));
        gameController.playAssistantCard(1, 0);
        assertNull(gameController.getGame().getPlayerByIndex(1).getCurrentAssistantCard());
        gameController.playAssistantCard(0, 0);
        assertEquals(gameController.getGame().getAssistantCard(0), gameController.getGame().getPlayerByIndex(0).getCurrentAssistantCard());
        assertFalse(gameController.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameController.getGame().getAssistantCard(0)));
    }

    @Test
    void moveStudentToIsland() {
        gameController.addPlayer("Viola", Wizard.TYPE_1);
        assertNull(gameController.getGame().getPlayerByIndex(0).getCurrentAssistantCard());
        gameController.moveStudentToIsland(0, Colour.PINK, 0, 0);
        assertEquals(0, gameController.getGame().getTable().getGroupIslandByIndex(0).getIslandByIndex(0).getStudents(Colour.PINK));
        gameController.addPlayer("Laura", Wizard.TYPE_2);
        assertNull(gameController.getGame().getPlayerByIndex(1).getCurrentAssistantCard());
        gameController.moveStudentToIsland(0, Colour.PINK, 0, 0);
        assertEquals(0, gameController.getGame().getTable().getGroupIslandByIndex(0).getIslandByIndex(0).getStudents(Colour.PINK));
        gameController.playAssistantCard(0, 0);
        assertEquals(gameController.getGame().getAssistantCard(0), gameController.getGame().getPlayerByIndex(0).getCurrentAssistantCard());
        assertFalse(gameController.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameController.getGame().getAssistantCard(0)));
        gameController.playAssistantCard(1, 0);
        assertNull(gameController.getGame().getPlayerByIndex(1).getCurrentAssistantCard());
        gameController.playAssistantCard(1, 1);
        assertEquals(gameController.getGame().getAssistantCard(1), gameController.getGame().getPlayerByIndex(1).getCurrentAssistantCard());
        assertFalse(gameController.getGame().getPlayerByIndex(1).isAssistantCardPresent(gameController.getGame().getAssistantCard(1)));
        int num = gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(Colour.PINK);
        gameController.moveStudentToIsland(0, Colour.PINK, 0, 0);
        if (num > 0) {
            assertEquals(1, gameController.getGame().getTable().getGroupIslandByIndex(0).getIslandByIndex(0).getStudents(Colour.PINK));
            assertEquals(6, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        } else {
            assertEquals(0, gameController.getGame().getTable().getGroupIslandByIndex(0).getIslandByIndex(0).getStudents(Colour.PINK));
            assertEquals(7, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        }
    }

    @Test
    void moveStudentToDiningRoom() {
        gameController.addPlayer("Viola", Wizard.TYPE_1);
        gameController.addPlayer("Laura", Wizard.TYPE_2);
        gameController.playAssistantCard(0, 0);
        gameController.playAssistantCard(1, 1);
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToDiningRoom(0, colour);
                }
            }
        }
        assertEquals(4, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
    }

    @Test
    void moveMotherNature() {
        gameController.addPlayer("Viola", Wizard.TYPE_1);
        gameController.addPlayer("Laura", Wizard.TYPE_2);
        gameController.playAssistantCard(0, 0);
        gameController.playAssistantCard(1, 1);
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToDiningRoom(0, colour);
                }
            }
        }
        gameController.moveMotherNature(0, 1);
        assertEquals(1, gameController.getGame().getTable().getMotherNaturePosition());
    }

    @Test
    void addPlayer() {
        gameController.addPlayer("Viola", Wizard.TYPE_1);
        assertEquals(1, gameController.getGame().getNumberOfPlayer());

        int size = 0;
        for (Colour colour : Colour.values()) {
            size += gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour);
        }
        assertEquals(7, size);
        assertEquals(7, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());

        size = 0;
        for (Colour colour : Colour.values()) {
            size += gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getDiningRoom(colour);
        }
        assertEquals(0, size);

        for (int i = 0; i < 10; i++) {
            assertTrue(gameController.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameController.getGame().getAssistantCard(i)));
        }

        gameController.addPlayer("Laura", Wizard.TYPE_1);
        assertEquals(1, gameController.getGame().getNumberOfPlayer());
        gameController.addPlayer("Viola", Wizard.TYPE_2);
        assertEquals(1, gameController.getGame().getNumberOfPlayer());
    }

    @Test
    void chooseCloudTile() {
        gameController.addPlayer("Viola", Wizard.TYPE_1);
        gameController.addPlayer("Laura", Wizard.TYPE_2);
        gameController.playAssistantCard(0, 0);
        gameController.playAssistantCard(1, 1);
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToDiningRoom(0, colour);
                }
            }
        }
        gameController.moveMotherNature(0, 1);
        assertEquals(1, gameController.getGame().getTable().getMotherNaturePosition());
        gameController.chooseCloudTile(0, 0);
        assertEquals(7, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
    }

    @Test
    void setColour() {
        gameController.addPlayer("Viola", Wizard.TYPE_1);
        gameController.addPlayer("Laura", Wizard.TYPE_2);
        gameController.playAssistantCard(0, 0);
        gameController.playAssistantCard(1, 1);
        assertThrows(IllegalAccessError.class, () -> gameController.setColour(Colour.PINK));
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToDiningRoom(0, colour);
                }
            }
        }
        assertThrows(IllegalAccessError.class, () -> gameController.setColour(Colour.GREEN));
        gameController.moveMotherNature(0, 1);
        assertEquals(1, gameController.getGame().getTable().getMotherNaturePosition());
        gameController.chooseCloudTile(0, 0);
        assertEquals(7, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        assertThrows(IllegalAccessError.class, () -> gameController.setColour(Colour.BLUE));
    }

    @Test
    void setColourAndIsland() {
        gameController.addPlayer("Viola", Wizard.TYPE_1);
        gameController.addPlayer("Laura", Wizard.TYPE_2);
        gameController.playAssistantCard(0, 0);
        gameController.playAssistantCard(1, 1);
        assertThrows(IllegalAccessError.class, () -> gameController.setColourAndIsland(Colour.GREEN, 1, 0));
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToDiningRoom(0, colour);
                }
            }
        }
        assertThrows(IllegalAccessError.class, () -> gameController.setColourAndIsland(Colour.YELLOW, 1, 0));
        gameController.moveMotherNature(0, 1);
        assertEquals(1, gameController.getGame().getTable().getMotherNaturePosition());
        gameController.chooseCloudTile(0, 0);
        assertEquals(7, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        assertThrows(IllegalAccessError.class, () -> gameController.setColourAndIsland(Colour.BLUE, 0, 0));
    }

    @Test
    void setGroupIsland() {
        gameController.addPlayer("Viola", Wizard.TYPE_1);
        gameController.addPlayer("Laura", Wizard.TYPE_2);
        gameController.playAssistantCard(0, 0);
        gameController.playAssistantCard(1, 1);
        assertThrows(IllegalAccessError.class, () -> gameController.setGroupIsland(0));
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToDiningRoom(0, colour);
                }
            }
        }
        gameController.moveMotherNature(0, 1);
        assertEquals(1, gameController.getGame().getTable().getMotherNaturePosition());
        gameController.chooseCloudTile(0, 0);
        assertEquals(7, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        assertThrows(IllegalAccessError.class, () -> gameController.setGroupIsland(0));
    }

    @Test
    void setColourDiningRoomEntrance() {
        gameController.addPlayer("Viola", Wizard.TYPE_1);
        gameController.addPlayer("Laura", Wizard.TYPE_2);
        gameController.playAssistantCard(0, 0);
        gameController.playAssistantCard(1, 1);
        assertThrows(IllegalAccessError.class, () -> gameController.setColourDiningRoomEntrance(Colour.PINK, Colour.RED));
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToDiningRoom(0, colour);
                }
            }
        }
        assertThrows(IllegalAccessError.class, () -> gameController.setColourDiningRoomEntrance(Colour.BLUE, Colour.YELLOW));
        gameController.moveMotherNature(0, 1);
        assertEquals(1, gameController.getGame().getTable().getMotherNaturePosition());
        gameController.chooseCloudTile(0, 0);
        assertEquals(7, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        assertThrows(IllegalAccessError.class, () -> gameController.setColourDiningRoomEntrance(Colour.BLUE, Colour.RED));
    }

    @Test
    void setColourCardEntrance() {
        gameController.addPlayer("Viola", Wizard.TYPE_1);
        gameController.addPlayer("Laura", Wizard.TYPE_2);
        gameController.playAssistantCard(0, 0);
        gameController.playAssistantCard(1, 1);
        assertThrows(IllegalAccessError.class, () -> gameController.setColourCardEntrance(Colour.BLUE, Colour.GREEN));
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToDiningRoom(0, colour);
                }
            }
        }
        gameController.moveMotherNature(0, 1);
        assertEquals(1, gameController.getGame().getTable().getMotherNaturePosition());
        gameController.chooseCloudTile(0, 0);
        assertEquals(7, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        assertThrows(IllegalAccessError.class, () -> gameController.setColourCardEntrance(Colour.BLUE, Colour.RED));
    }
}