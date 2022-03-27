package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    private GameController gameController;
    private GameController gameControllerThree;

    @BeforeEach
    void setUp() {
        gameController = new GameController(false, 2);
        gameControllerThree = new GameController(false, 3);
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
    void settingTest(){
        assertEquals(8, gameController.getGame().getNumberOfTowersPerPlayer());
        assertEquals(7, gameController.getGame().getNumberStudentsEntrance());
        assertEquals(3, gameController.getGame().getStudentNumberMovement());

        assertEquals(2, gameController.getGame().getTable().getNumberOfCloudTile());
        assertEquals(12, gameController.getGame().getTable().getNumberOfGroupIsland());
        for(int i = 0; i < 12; i++){
            assertEquals(1, gameController.getGame().getTable().getGroupIslandByIndex(i).getNumberOfSingleIsland());
        }
        assertEquals(0, gameController.getGame().getTable().getMotherNaturePosition());

        int numBlue = 0;
        int numGreen = 0;
        int numPink = 0;
        int numRed = 0;
        int numYellow = 0;

        for(int i = 0; i < 12; i++){
            numBlue += gameController.getGame().getTable().getGroupIslandByIndex(i).getNumberStudent(Colour.BLUE);
            numGreen += gameController.getGame().getTable().getGroupIslandByIndex(i).getNumberStudent(Colour.GREEN);
            numPink += gameController.getGame().getTable().getGroupIslandByIndex(i).getNumberStudent(Colour.PINK);
            numRed += gameController.getGame().getTable().getGroupIslandByIndex(i).getNumberStudent(Colour.RED);
            numYellow += gameController.getGame().getTable().getGroupIslandByIndex(i).getNumberStudent(Colour.YELLOW);
        }

        assertEquals(2, numBlue);
        assertEquals(2, numGreen);
        assertEquals(2, numPink);
        assertEquals(2, numRed);
        assertEquals(2, numYellow);
    }

    @Test
    void playCharacterCard() {
        gameController.addPlayer("Viola", Wizard.TYPE_3);
        gameController.addPlayer("Laura", Wizard.TYPE_4);
        Game game = gameController.getGame();
        gameController.playCharacterCard(0, 0);
        assertEquals(game, gameController.getGame());
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

    // Tests that after 10 rounds the game ends
    @RepeatedTest(10)
    void testTwoPlayer() {
        // adding player to the game
        gameController.addPlayer("Laura", Wizard.TYPE_2);
        gameController.addPlayer("Viola", Wizard.TYPE_3);
        // First turn
        gameController.playAssistantCard(0, 0);
        gameController.playAssistantCard(1, 9);

        int i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(0, colour, 0, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(0, 1);
        gameController.chooseCloudTile(0, 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(1, colour, 0, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(1, 1);
        gameController.chooseCloudTile(1, 0);

        // Second turn
        gameController.playAssistantCard(0, 1);
        gameController.playAssistantCard(1, 8);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(0, colour, 0, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(0, 1);
        gameController.chooseCloudTile(0, 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(1, colour, 0, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(1, 1);
        gameController.chooseCloudTile(1, 0);

        //Third turn
        gameController.playAssistantCard(0, 2);
        gameController.playAssistantCard(1, 7);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(0, colour, 0, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(0, 1);
        gameController.chooseCloudTile(0, 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(1, colour, 0, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(1, 1);
        gameController.chooseCloudTile(1, 0);

        //Fourth turn
        gameController.playAssistantCard(0, 3);
        gameController.playAssistantCard(1, 6);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(0, colour, 0, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(0, 1);
        gameController.chooseCloudTile(0, 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(1, colour, 0, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(1, 1);
        gameController.chooseCloudTile(1, 0);

        //Fifth turn
        gameController.playAssistantCard(0, 4);
        gameController.playAssistantCard(1, 5);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(0, colour, 0, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(0, 1);
        gameController.chooseCloudTile(0, 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(1, colour, 0, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(1, 1);
        gameController.chooseCloudTile(1, 0);

        //Sixth turn
        gameController.playAssistantCard(0, 5);
        gameController.playAssistantCard(1, 4);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(1, colour, 0, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(1, 1);
        gameController.chooseCloudTile(1, 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(0, colour, 0, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(0, 1);
        gameController.chooseCloudTile(0, 0);

        //Seventh turn
        gameController.playAssistantCard(1, 3);
        gameController.playAssistantCard(0, 6);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(1, colour, 0, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(1, 1);
        gameController.chooseCloudTile(1, 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(0, colour, 0, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(0, 1);
        gameController.chooseCloudTile(0, 0);

        // Eighth turn
        gameController.playAssistantCard(1, 2);
        gameController.playAssistantCard(0, 7);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(1, colour, 0, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(1, 1);
        gameController.chooseCloudTile(1, 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(0, colour, 0, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(0, 1);
        gameController.chooseCloudTile(0, 0);

        // Ninth turn
        gameController.playAssistantCard(1, 1);
        gameController.playAssistantCard(0, 8);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(1, colour, 0, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(1, 1);
        gameController.chooseCloudTile(1, 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(0, colour, 0, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(0, 1);
        gameController.chooseCloudTile(0, 0);

        // Tenth turn
        gameController.playAssistantCard(1, 0);
        gameController.playAssistantCard(0, 9);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(1, colour, 0, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(1, 1);
        gameController.chooseCloudTile(1, 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(0, colour, 0, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(0, 1);
        gameController.chooseCloudTile(0, 0);

        assertEquals(GamePhase.END_GAME, gameController.getGame().getGamePhase());
        assertEquals(11, gameController.getGame().getRound());
        for (int j = 0; j < 10; j++) {
            assertFalse(gameController.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameController.getGame().getAssistantCard(j)));
            assertFalse(gameController.getGame().getPlayerByIndex(1).isAssistantCardPresent(gameController.getGame().getAssistantCard(j)));
        }
        assertEquals(12, gameController.getGame().getTable().getNumberOfGroupIsland());
        assertEquals("Laura", gameController.getGame().getWinner().getNickname());
    }

    // A simple test with three players
    @RepeatedTest(10)
    void threePlayerGame() {
        // start of the game
        assertEquals(GamePhase.SETTING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.WAITING, gameControllerThree.getGame().getTurnPhase());
        assertEquals(4, gameControllerThree.getGame().getStudentNumberMovement());
        assertEquals(9, gameControllerThree.getGame().getNumberStudentsEntrance());
        assertEquals(6, gameControllerThree.getGame().getNumberOfTowersPerPlayer());
        assertNull(gameControllerThree.getGame().getCurrentPlayer());
        gameControllerThree.addPlayer("Viola", Wizard.TYPE_1);
        assertEquals(1, gameControllerThree.getGame().getNumberOfPlayer());
        assertEquals("Viola", gameControllerThree.getGame().getPlayerByIndex(0).getNickname());
        assertEquals(Wizard.TYPE_1, gameControllerThree.getGame().getPlayerByIndex(0).getWizard());
        gameControllerThree.addPlayer("Laura", Wizard.TYPE_1);
        assertEquals(1, gameControllerThree.getGame().getNumberOfPlayer());
        gameControllerThree.addPlayer("Laura", Wizard.TYPE_3);
        assertEquals("Laura", gameControllerThree.getGame().getPlayerByIndex(1).getNickname());
        assertEquals(Wizard.TYPE_3, gameControllerThree.getGame().getPlayerByIndex(1).getWizard());
        assertEquals(2, gameControllerThree.getGame().getNumberOfPlayer());
        gameControllerThree.addPlayer("Sara", Wizard.TYPE_1);
        assertEquals(GamePhase.SETTING, gameControllerThree.getGame().getGamePhase());
        assertEquals(2, gameControllerThree.getGame().getNumberOfPlayer());
        gameControllerThree.addPlayer("Sara", Wizard.TYPE_4);
        assertEquals(3, gameControllerThree.getGame().getNumberOfPlayer());
        assertEquals("Sara", gameControllerThree.getGame().getPlayerByIndex(2).getNickname());
        assertEquals(Wizard.TYPE_4, gameControllerThree.getGame().getPlayerByIndex(2).getWizard());
        gameControllerThree.addPlayer("Elisa", Wizard.TYPE_2);
        assertEquals(3, gameControllerThree.getGame().getNumberOfPlayer());
        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.WAITING, gameControllerThree.getGame().getTurnPhase());
        assertEquals(gameControllerThree.getGame().getCurrentPlayer(), gameControllerThree.getGame().getPlayerByIndex(0));
        for (int i = 0; i < gameControllerThree.getGame().getNumberOfPlayer(); i++) {
            assertEquals(gameControllerThree.getGame().getNumberStudentsEntrance(), gameControllerThree.getGame().getPlayerByIndex(i).getSchoolBoard().getNumberStudentsEntrance());
            assertEquals(gameControllerThree.getGame().getNumberOfTowersPerPlayer(), gameControllerThree.getGame().getPlayerByIndex(i).getSchoolBoard().getTowers());
        }

        // playing AssistantCard first turn
        gameControllerThree.playAssistantCard(1, 0);
        assertNull(gameControllerThree.getGame().getPlayerByIndex(1).getCurrentAssistantCard());
        gameControllerThree.playAssistantCard(0, 4);
        assertEquals(gameControllerThree.getGame().getAssistantCard(4), gameControllerThree.getGame().getPlayerByIndex(0).getCurrentAssistantCard());
        assertFalse(gameControllerThree.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameControllerThree.getGame().getAssistantCard(4)));
        assertEquals(gameControllerThree.getGame().getCurrentPlayer(), gameControllerThree.getGame().getPlayerByIndex(1));
        gameControllerThree.playAssistantCard(1, 7);
        gameControllerThree.playAssistantCard(2, 2);
        assertEquals(gameControllerThree.getGame().getCurrentPlayer(), gameControllerThree.getGame().getPlayerByIndex(2));
        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerThree.getGame().getTurnPhase());

        // first turn
        int i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(2).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland(2, colour, 1, 0);
                    i++;
                }
            }
        }

        assertEquals(5, gameControllerThree.getGame().getPlayerByIndex(2).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(9, gameControllerThree.getGame().getPlayerByIndex(1).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(9, gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());

        int num = 0;
        for (Colour colour : Colour.values()) {
            num += gameControllerThree.getGame().getTable().getGroupIslandByIndex(1).getNumberStudent(colour);
        }
        assertEquals(5, num);
        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameControllerThree.getGame().getTurnPhase());

        gameControllerThree.moveMotherNature(2, 1);
        assertEquals(1, gameControllerThree.getGame().getTable().getMotherNaturePosition());
        assertNull(gameControllerThree.getGame().getTable().getGroupIslandByIndex(1).getInfluence());

        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerThree.getGame().getTurnPhase());

        gameControllerThree.chooseCloudTile(2, 2);
        assertEquals(9, gameControllerThree.getGame().getPlayerByIndex(2).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerThree.getGame().getTurnPhase());
        assertEquals(2, gameControllerThree.getGame().getTable().getNumberOfCloudTile());
        assertEquals(gameControllerThree.getGame().getCurrentPlayer(), gameControllerThree.getGame().getPlayerByIndex(0));


        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToDiningRoom(0, colour);
                    i++;
                }
            }
        }

        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameControllerThree.getGame().getTurnPhase());
        assertEquals(5, gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(9, gameControllerThree.getGame().getPlayerByIndex(1).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(9, gameControllerThree.getGame().getPlayerByIndex(2).getSchoolBoard().getNumberStudentsEntrance());

        gameControllerThree.moveMotherNature(0, 4);
        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameControllerThree.getGame().getTurnPhase());
        gameControllerThree.moveMotherNature(0, 3);
        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerThree.getGame().getTurnPhase());

        gameControllerThree.chooseCloudTile(0, 2);
        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerThree.getGame().getTurnPhase());

        gameControllerThree.chooseCloudTile(0, 1);
        assertEquals(1, gameControllerThree.getGame().getTable().getNumberOfCloudTile());
        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerThree.getGame().getTurnPhase());
        assertEquals(gameControllerThree.getGame().getCurrentPlayer(), gameControllerThree.getGame().getPlayerByIndex(1));


        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToDiningRoom(1, colour);
                    i++;
                }
            }
        }

        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameControllerThree.getGame().getTurnPhase());
        assertEquals(5, gameControllerThree.getGame().getPlayerByIndex(1).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(9, gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(9, gameControllerThree.getGame().getPlayerByIndex(2).getSchoolBoard().getNumberStudentsEntrance());

        gameControllerThree.moveMotherNature(1, 4);
        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerThree.getGame().getTurnPhase());
        gameControllerThree.chooseCloudTile(1, 0);

        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.WAITING, gameControllerThree.getGame().getTurnPhase());
        assertEquals(3, gameControllerThree.getGame().getTable().getNumberOfCloudTile());

        // second turn
        gameControllerThree.playAssistantCard(2, 7);
        gameControllerThree.playAssistantCard(0, 2);
        gameControllerThree.playAssistantCard(1, 4);

        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerThree.getGame().getTurnPhase());

        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland(0, colour, 7, 0);
                    i++;
                }
            }
        }
        gameControllerThree.moveMotherNature(0, 2);
        gameControllerThree.chooseCloudTile(0, 0);

        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland(1, colour, 10, 0);
                    i++;
                }
            }
        }
        gameControllerThree.moveMotherNature(1, 3);
        gameControllerThree.chooseCloudTile(1, 0);

        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(2).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland(2, colour, 7, 0);
                    i++;
                }
            }
        }
        gameControllerThree.moveMotherNature(2, 4);
        gameControllerThree.chooseCloudTile(2, 0);

        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.WAITING, gameControllerThree.getGame().getTurnPhase());

        // third turn
        gameControllerThree.playAssistantCard(0, 5);
        gameControllerThree.playAssistantCard(1, 1);
        gameControllerThree.playAssistantCard(2, 8);

        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerThree.getGame().getTurnPhase());

        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland(1, colour, 10, 0);
                    i++;
                }
            }
        }
        gameControllerThree.moveMotherNature(1, 1);
        gameControllerThree.chooseCloudTile(1, 0);


        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland(0, colour, 7, 0);
                    i++;
                }
            }
        }
        gameControllerThree.moveMotherNature(0, 3);
        gameControllerThree.chooseCloudTile(0, 0);

        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(2).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland(2, colour, 7, 0);
                    i++;
                }
            }
        }
        gameControllerThree.moveMotherNature(2, 4);
        gameControllerThree.chooseCloudTile(2, 0);

        if (gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getTowers() == 0) {
            assertEquals(GamePhase.END_GAME, gameControllerThree.getGame().getGamePhase());
            assertEquals("Viola", gameControllerThree.getGame().getWinner().getNickname());
        } else {
            assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerThree.getGame().getGamePhase());
            assertEquals(TurnPhase.WAITING, gameControllerThree.getGame().getTurnPhase());
        }

        // fourth turn
        gameControllerThree.playAssistantCard(1, 5);
        gameControllerThree.playAssistantCard(2, 1);
        gameControllerThree.playAssistantCard(0, 8);


        if (gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getTowers() == 0) {
            assertEquals(GamePhase.END_GAME, gameControllerThree.getGame().getGamePhase());
            assertEquals("Viola", gameControllerThree.getGame().getWinner().getNickname());
        } else {
            assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
            assertEquals(TurnPhase.MOVE_STUDENT, gameControllerThree.getGame().getTurnPhase());
        }

        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(2).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland(2, colour, 3, 0);
                    i++;
                }
            }
        }
        gameControllerThree.moveMotherNature(2, 1);
        gameControllerThree.chooseCloudTile(2, 0);

        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland(1, colour, 4, 0);
                    i++;
                }
            }
        }
        gameControllerThree.moveMotherNature(1, 1);
        gameControllerThree.chooseCloudTile(1, 0);


        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland(0, colour, 5, 0);
                    i++;
                }
            }
        }
        gameControllerThree.moveMotherNature(0, 1);
        gameControllerThree.chooseCloudTile(0, 0);

        if (gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getTowers() == 0) {
            assertEquals(GamePhase.END_GAME, gameControllerThree.getGame().getGamePhase());
            assertEquals("Viola", gameControllerThree.getGame().getWinner().getNickname());
        } else {
            assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerThree.getGame().getGamePhase());
            assertEquals(TurnPhase.WAITING, gameControllerThree.getGame().getTurnPhase());
        }

        // fifth turn
        gameControllerThree.playAssistantCard(2, 6);
        gameControllerThree.playAssistantCard(0, 3);
        gameControllerThree.playAssistantCard(1, 9);

        if (gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getTowers() == 0) {
            assertEquals(GamePhase.END_GAME, gameControllerThree.getGame().getGamePhase());
            assertEquals("Viola", gameControllerThree.getGame().getWinner().getNickname());
        } else {
            assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
            assertEquals(TurnPhase.MOVE_STUDENT, gameControllerThree.getGame().getTurnPhase());
        }

        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland(0, colour, 1, 0);
                    i++;
                }
            }
        }
        gameControllerThree.moveMotherNature(0, 1);
        gameControllerThree.chooseCloudTile(0, 0);

        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(2).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland(2, colour, 2, 0);
                    i++;
                }
            }
        }
        gameControllerThree.moveMotherNature(2, 1);
        gameControllerThree.chooseCloudTile(2, 0);

        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland(1, colour, 3, 0);
                    i++;
                }
            }
        }
        gameControllerThree.moveMotherNature(1, 1);
        gameControllerThree.chooseCloudTile(1, 0);

        if (gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getTowers() == 0) {
            assertEquals(GamePhase.END_GAME, gameControllerThree.getGame().getGamePhase());
            assertEquals("Viola", gameControllerThree.getGame().getWinner().getNickname());
        } else {
            assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerThree.getGame().getGamePhase());
            assertEquals(TurnPhase.WAITING, gameControllerThree.getGame().getTurnPhase());
        }
    }

    @RepeatedTest(10)
    void gameTest2Players() {
        assertEquals(GamePhase.SETTING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.WAITING, gameController.getGame().getTurnPhase());

        gameController.addPlayer("Laura", Wizard.TYPE_2);
        gameController.addPlayer("Viola", Wizard.TYPE_2);

        assertEquals(GamePhase.SETTING, gameController.getGame().getGamePhase());

        assertEquals(1, gameController.getGame().getNumberOfPlayer());

        gameController.addPlayer("Viola", Wizard.TYPE_1);
        assertEquals(2, gameController.getGame().getNumberOfPlayer());

        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameController.getGame().getGamePhase());

        assertEquals(TurnPhase.WAITING, gameController.getGame().getTurnPhase());
        assertEquals(gameController.getGame().getPlayerByIndex(0), gameController.getGame().getCurrentPlayer());

        gameController.playAssistantCard(1, 0);

        assertNull(gameController.getGame().getPlayerByIndex(0).getCurrentAssistantCard());

        //First turn

        gameController.playAssistantCard(0, 4);
        assertEquals(gameController.getGame().getAssistantCard(4), gameController.getGame().getPlayerByIndex(0).getCurrentAssistantCard());
        assertFalse(gameController.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameController.getGame().getAssistantCard(4)));
        assertEquals(gameController.getGame().getCurrentPlayer(), gameController.getGame().getPlayerByIndex(1));

        for (int i = 0; i < gameController.getGame().getNumberOfPlayer(); i++) {
            assertEquals(gameController.getGame().getNumberStudentsEntrance(), gameController.getGame().getPlayerByIndex(i).getSchoolBoard().getNumberStudentsEntrance());
            assertEquals(gameController.getGame().getNumberOfTowersPerPlayer(), gameController.getGame().getPlayerByIndex(i).getSchoolBoard().getTowers());
        }
        gameController.playAssistantCard(1, 8);

        assertEquals(gameController.getGame().getCurrentPlayer(), gameController.getGame().getPlayerByIndex(0));

        int i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(0, colour, 2, 0);
                    i++;
                }
            }
        }

        assertEquals(4, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(7, gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getNumberStudentsEntrance());

        int num = 0;
        for (Colour colour : Colour.values()) {
            num += gameController.getGame().getTable().getGroupIslandByIndex(2).getNumberStudent(colour);
        }

        assertEquals(4, num);
        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameController.getGame().getTurnPhase());

        gameController.moveMotherNature(0, 2);

        assertEquals(2, gameController.getGame().getTable().getMotherNaturePosition());
        assertNull(gameController.getGame().getTable().getGroupIslandByIndex(2).getInfluence());

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());

        gameController.chooseCloudTile(0, 1);
        assertEquals(7, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameController.getGame().getTurnPhase());
        assertEquals(1, gameController.getGame().getTable().getNumberOfCloudTile());
        assertEquals(gameController.getGame().getCurrentPlayer(), gameController.getGame().getPlayerByIndex(1));

        int j = 0;
        while (j < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToDiningRoom(1, colour);
                    j++;
                }
            }
        }

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameController.getGame().getTurnPhase());

        assertEquals(4, gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(7, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());

        gameController.moveMotherNature(1, 3);

        assertEquals(5, gameController.getGame().getTable().getMotherNaturePosition());

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());

        gameController.chooseCloudTile(1, 1);
        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());

        gameController.chooseCloudTile(1, 0);
        assertEquals(7, gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(2, gameController.getGame().getTable().getNumberOfCloudTile());
        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.WAITING, gameController.getGame().getTurnPhase());

        assertEquals(gameController.getGame().getCurrentPlayer(), gameController.getGame().getPlayerByIndex(0));


        //Second turn

        gameController.playAssistantCard(0, 9);
        gameController.playAssistantCard(1, 6);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(1, colour, 9, 0);
                    i++;
                }
            }
        }

        gameController.moveMotherNature(1, 1);

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());

        gameController.chooseCloudTile(1, 1);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(0, colour, 4, 0);
                    i++;
                }
            }
        }

        gameController.moveMotherNature(0, 2);

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());


        gameController.chooseCloudTile(0, 0);

        //Third turn

        gameController.playAssistantCard(1, 3);
        gameController.playAssistantCard(0, 5);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(1, colour, 0, 0);
                    i++;
                }
            }
        }

        gameController.moveMotherNature(1, 2);

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());

        gameController.chooseCloudTile(1, 1);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(0, colour, 2, 0);
                    i++;
                }
            }
        }

        gameController.moveMotherNature(0, 1);

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());


        gameController.chooseCloudTile(0, 0);

        //Fourth turn
        gameController.playAssistantCard(1, 7);
        gameController.playAssistantCard(0, 1);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(0, colour, 2, 0);
                    i++;
                }
            }
        }

        gameController.moveMotherNature(0, 1);

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());

        gameController.chooseCloudTile(0, 1);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(1, colour, 3, 0);
                    i++;
                }
            }
        }

        gameController.moveMotherNature(1, 1);

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());


        gameController.chooseCloudTile(1, 0);

        //Fifth turn
        gameController.playAssistantCard(0, 8);
        gameController.playAssistantCard(1, 2);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(1, colour, 4, 0);
                    i++;
                }
            }
        }

        gameController.moveMotherNature(1, 2);

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());

        gameController.chooseCloudTile(1, 1);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(0, colour, 6, 0);
                    i++;
                }
            }
        }

        gameController.moveMotherNature(0, 1);

        if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getTowers() == 0) {
            assertEquals(GamePhase.END_GAME, gameController.getGame().getGamePhase());
            assertEquals("Viola", gameController.getGame().getWinner().getNickname());
        } else {
            assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        }


        gameController.chooseCloudTile(0, 0);

        //Sixth turn
        gameController.playAssistantCard(1, 5);
        gameController.playAssistantCard(0, 3);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(0, colour, 4, 0);
                    i++;
                }
            }
        }

        gameController.moveMotherNature(0, 1);

        if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getTowers() == 0) {
            assertEquals(GamePhase.END_GAME, gameController.getGame().getGamePhase());
            assertEquals("Viola", gameController.getGame().getWinner().getNickname());
        } else {
            assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        }

        gameController.chooseCloudTile(0, 1);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(1, colour, 6, 0);
                    i++;
                }
            }
        }

        gameController.moveMotherNature(1, 1);

        if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getTowers() == 0) {
            assertEquals(GamePhase.END_GAME, gameController.getGame().getGamePhase());
            assertEquals("Viola", gameController.getGame().getWinner().getNickname());
        } else {
            assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        }
    }
}