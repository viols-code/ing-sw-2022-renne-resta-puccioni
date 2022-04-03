package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    private GameController gameControllerTwo;
    private GameController gameControllerThree;

    @BeforeEach
    void setUp() {
        gameControllerTwo = new GameController(false, 2);
        gameControllerThree = new GameController(false, 3);
    }

    @Test
    void setting() {
        assertEquals(8, gameControllerTwo.getGame().getNumberOfTowersPerPlayer());
        assertEquals(7, gameControllerTwo.getGame().getNumberStudentsEntrance());
        assertEquals(3, gameControllerTwo.getGame().getStudentNumberMovement());
        assertEquals(12, gameControllerTwo.getGame().getTable().getNumberOfGroupIsland());
        for (int i = 0; i < 12; i++) {
            int num = 0;
            for (Colour colour : Colour.values()) {
                num += gameControllerTwo.getGame().getTable().getGroupIslandByIndex(i).getIslandByIndex(0).getStudents(colour);
            }
            if (i == 0 || i == 6) {
                assertEquals(0, num);
            } else {
                assertEquals(1, num);
            }
        }

        int size = 0;
        for (Colour colour : Colour.values()) {
            size += gameControllerTwo.getGame().getTable().getBag().getBagStudent(colour);
        }
        assertEquals(114, size);

        assertEquals(2, gameControllerTwo.getGame().getTable().getNumberOfCloudTile());

        for (int i = 0; i < gameControllerTwo.getGame().getTable().getNumberOfCloudTile(); i++) {
            int num = 0;
            for (Colour colour : Colour.values()) {
                num += gameControllerTwo.getGame().getTable().getCloudTilesByIndex(i).getTileStudents(colour);
            }
            assertEquals(3, num);
        }
    }

    @Test
    void settingTest() {
        assertEquals(8, gameControllerTwo.getGame().getNumberOfTowersPerPlayer());
        assertEquals(7, gameControllerTwo.getGame().getNumberStudentsEntrance());
        assertEquals(3, gameControllerTwo.getGame().getStudentNumberMovement());

        assertEquals(2, gameControllerTwo.getGame().getTable().getNumberOfCloudTile());
        assertEquals(12, gameControllerTwo.getGame().getTable().getNumberOfGroupIsland());
        for (int i = 0; i < 12; i++) {
            assertEquals(1, gameControllerTwo.getGame().getTable().getGroupIslandByIndex(i).getNumberOfSingleIsland());
        }
        assertEquals(0, gameControllerTwo.getGame().getTable().getMotherNaturePosition());

        int numBlue = 0;
        int numGreen = 0;
        int numPink = 0;
        int numRed = 0;
        int numYellow = 0;

        for (int i = 0; i < 12; i++) {
            numBlue += gameControllerTwo.getGame().getTable().getGroupIslandByIndex(i).getNumberStudent(Colour.BLUE);
            numGreen += gameControllerTwo.getGame().getTable().getGroupIslandByIndex(i).getNumberStudent(Colour.GREEN);
            numPink += gameControllerTwo.getGame().getTable().getGroupIslandByIndex(i).getNumberStudent(Colour.PINK);
            numRed += gameControllerTwo.getGame().getTable().getGroupIslandByIndex(i).getNumberStudent(Colour.RED);
            numYellow += gameControllerTwo.getGame().getTable().getGroupIslandByIndex(i).getNumberStudent(Colour.YELLOW);
        }

        assertEquals(2, numBlue);
        assertEquals(2, numGreen);
        assertEquals(2, numPink);
        assertEquals(2, numRed);
        assertEquals(2, numYellow);
    }

    @Test
    void playCharacterCard() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_3);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_4);
        Game game = gameControllerTwo.getGame();
        gameControllerTwo.playCharacterCard(0, 0);
        assertEquals(game, gameControllerTwo.getGame());
    }

    @Test
    void playAssistantCard() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        gameControllerTwo.moveStudentToIsland(0, Colour.PINK, 0, 0);
        assertEquals(0, gameControllerTwo.getGame().getTable().getGroupIslandByIndex(0).getIslandByIndex(0).getStudents(Colour.PINK));
        gameControllerTwo.playAssistantCard(1, 0);
        assertNull(gameControllerTwo.getGame().getPlayerByIndex(1).getCurrentAssistantCard());
        gameControllerTwo.playAssistantCard(0, 0);
        assertEquals(gameControllerTwo.getGame().getAssistantCard(0), gameControllerTwo.getGame().getPlayerByIndex(0).getCurrentAssistantCard());
        assertFalse(gameControllerTwo.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameControllerTwo.getGame().getAssistantCard(0)));
    }

    @Test
    void playAssistantCardCornerCase() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);

        for(int i=0; i<9; i++){
            gameControllerTwo.playAssistantCard(0,i);
            assertEquals(gameControllerTwo.getGame().getAssistantCard(i),gameControllerTwo.getGame().getPlayerByIndex(0).getCurrentAssistantCard());
            gameControllerTwo.playAssistantCard(1,(i + 1)%9);
            assertEquals(gameControllerTwo.getGame().getAssistantCard((i+1)%9),gameControllerTwo.getGame().getPlayerByIndex(1).getCurrentAssistantCard());
            gameControllerTwo.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        }
        gameControllerTwo.playAssistantCard(1,9);
        gameControllerTwo.playAssistantCard(0,9);
        assertEquals(gameControllerTwo.getGame().getAssistantCard(9),gameControllerTwo.getGame().getPlayerByIndex(1).getCurrentAssistantCard());
        assertEquals(gameControllerTwo.getGame().getAssistantCard(9),gameControllerTwo.getGame().getPlayerByIndex(0).getCurrentAssistantCard());
    }

    @Test
    void moveStudentToIsland() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        assertNull(gameControllerTwo.getGame().getPlayerByIndex(0).getCurrentAssistantCard());
        gameControllerTwo.moveStudentToIsland(0, Colour.PINK, 0, 0);
        assertEquals(0, gameControllerTwo.getGame().getTable().getGroupIslandByIndex(0).getIslandByIndex(0).getStudents(Colour.PINK));
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        assertNull(gameControllerTwo.getGame().getPlayerByIndex(1).getCurrentAssistantCard());
        gameControllerTwo.moveStudentToIsland(0, Colour.PINK, 0, 0);
        assertEquals(0, gameControllerTwo.getGame().getTable().getGroupIslandByIndex(0).getIslandByIndex(0).getStudents(Colour.PINK));
        gameControllerTwo.playAssistantCard(0, 0);
        assertEquals(gameControllerTwo.getGame().getAssistantCard(0), gameControllerTwo.getGame().getPlayerByIndex(0).getCurrentAssistantCard());
        assertFalse(gameControllerTwo.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameControllerTwo.getGame().getAssistantCard(0)));
        gameControllerTwo.playAssistantCard(1, 0);
        assertNull(gameControllerTwo.getGame().getPlayerByIndex(1).getCurrentAssistantCard());
        gameControllerTwo.playAssistantCard(1, 1);
        assertEquals(gameControllerTwo.getGame().getAssistantCard(1), gameControllerTwo.getGame().getPlayerByIndex(1).getCurrentAssistantCard());
        assertFalse(gameControllerTwo.getGame().getPlayerByIndex(1).isAssistantCardPresent(gameControllerTwo.getGame().getAssistantCard(1)));
        int num = gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(Colour.PINK);
        gameControllerTwo.moveStudentToIsland(0, Colour.PINK, 0, 0);
        if (num > 0) {
            assertEquals(1, gameControllerTwo.getGame().getTable().getGroupIslandByIndex(0).getIslandByIndex(0).getStudents(Colour.PINK));
            assertEquals(6, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        } else {
            assertEquals(0, gameControllerTwo.getGame().getTable().getGroupIslandByIndex(0).getIslandByIndex(0).getStudents(Colour.PINK));
            assertEquals(7, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        }
    }

    @Test
    void moveStudentToDiningRoom() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        gameControllerTwo.playAssistantCard(0, 0);
        gameControllerTwo.playAssistantCard(1, 1);
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToDiningRoom(0, colour);
                }
            }
        }
        assertEquals(4, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
    }

    @Test
    void moveMotherNature() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        gameControllerTwo.playAssistantCard(0, 0);
        gameControllerTwo.playAssistantCard(1, 1);
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToDiningRoom(0, colour);
                }
            }
        }
        gameControllerTwo.moveMotherNature(0, 1);
        assertEquals(1, gameControllerTwo.getGame().getTable().getMotherNaturePosition());
    }

    @Test
    void addPlayer() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        assertEquals(1, gameControllerTwo.getGame().getNumberOfPlayer());
        assertEquals(TowerColour.WHITE, gameControllerTwo.getGame().getPlayerByIndex(0).getTowerColour());

        int size = 0;
        for (Colour colour : Colour.values()) {
            size += gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour);
        }
        assertEquals(7, size);
        assertEquals(7, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());

        size = 0;
        for (Colour colour : Colour.values()) {
            size += gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getDiningRoom(colour);
        }
        assertEquals(0, size);

        for (int i = 0; i < 10; i++) {
            assertTrue(gameControllerTwo.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameControllerTwo.getGame().getAssistantCard(i)));
        }

        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_1);
        assertEquals(1, gameControllerTwo.getGame().getNumberOfPlayer());
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_2);
        assertEquals(1, gameControllerTwo.getGame().getNumberOfPlayer());
    }

    @Test
    void chooseCloudTile() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        assertEquals(TowerColour.WHITE, gameControllerTwo.getGame().getPlayerByIndex(0).getTowerColour());
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        assertEquals(TowerColour.BLACK, gameControllerTwo.getGame().getPlayerByIndex(1).getTowerColour());
        gameControllerTwo.playAssistantCard(0, 0);
        gameControllerTwo.playAssistantCard(1, 1);
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToDiningRoom(0, colour);
                }
            }
        }
        gameControllerTwo.moveMotherNature(0, 1);
        assertEquals(1, gameControllerTwo.getGame().getTable().getMotherNaturePosition());
        gameControllerTwo.chooseCloudTile(0, 0);
        assertEquals(7, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
    }

    @Test
    void setColour() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        gameControllerTwo.playAssistantCard(0, 0);
        gameControllerTwo.playAssistantCard(1, 1);
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToDiningRoom(0, colour);
                }
            }
        }
        gameControllerTwo.moveMotherNature(0, 1);
        assertEquals(1, gameControllerTwo.getGame().getTable().getMotherNaturePosition());
        gameControllerTwo.chooseCloudTile(0, 0);
        assertEquals(7, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        gameControllerTwo.setColour(Colour.PINK);
        assertEquals(gameControllerTwo.getGame().getActiveCharacterCard(), gameControllerTwo.getGame().getBasicState());
    }

    @Test
    void setColourAndIsland() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        gameControllerTwo.playAssistantCard(0, 0);
        gameControllerTwo.playAssistantCard(1, 1);
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToDiningRoom(0, colour);
                }
            }
        }
        gameControllerTwo.moveMotherNature(0, 1);
        assertEquals(1, gameControllerTwo.getGame().getTable().getMotherNaturePosition());
        gameControllerTwo.chooseCloudTile(0, 0);
        assertEquals(7, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        gameControllerTwo.setColourAndIsland(Colour.PINK, 0, 0);
        assertEquals(gameControllerTwo.getGame().getActiveCharacterCard(), gameControllerTwo.getGame().getBasicState());
    }

    @Test
    void setGroupIsland() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        gameControllerTwo.playAssistantCard(0, 0);
        gameControllerTwo.playAssistantCard(1, 1);
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToDiningRoom(0, colour);
                }
            }
        }
        gameControllerTwo.moveMotherNature(0, 1);
        assertEquals(1, gameControllerTwo.getGame().getTable().getMotherNaturePosition());
        gameControllerTwo.chooseCloudTile(0, 0);
        assertEquals(7, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(gameControllerTwo.getGame().getActiveCharacterCard(), gameControllerTwo.getGame().getBasicState());
        gameControllerTwo.setGroupIsland(0);
        assertEquals(gameControllerTwo.getGame().getActiveCharacterCard(), gameControllerTwo.getGame().getBasicState());
    }

    @Test
    void setColourDiningRoomEntrance() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        gameControllerTwo.playAssistantCard(0, 0);
        gameControllerTwo.playAssistantCard(1, 1);
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToDiningRoom(0, colour);
                }
            }
        }
        gameControllerTwo.moveMotherNature(0, 1);
        assertEquals(1, gameControllerTwo.getGame().getTable().getMotherNaturePosition());
        gameControllerTwo.chooseCloudTile(0, 0);
        assertEquals(7, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(gameControllerTwo.getGame().getActiveCharacterCard(), gameControllerTwo.getGame().getBasicState());
        gameControllerTwo.setColourDiningRoomEntrance(Colour.RED, Colour.BLUE);
        assertEquals(gameControllerTwo.getGame().getActiveCharacterCard(), gameControllerTwo.getGame().getBasicState());
    }

    @Test
    void setColourCardEntrance() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        gameControllerTwo.playAssistantCard(0, 0);
        gameControllerTwo.playAssistantCard(1, 1);
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToDiningRoom(0, colour);
                }
            }
        }
        gameControllerTwo.moveMotherNature(0, 1);
        assertEquals(1, gameControllerTwo.getGame().getTable().getMotherNaturePosition());
        gameControllerTwo.chooseCloudTile(0, 0);
        assertEquals(7, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(gameControllerTwo.getGame().getActiveCharacterCard(), gameControllerTwo.getGame().getBasicState());
        gameControllerTwo.setColourCardEntrance(Colour.RED, Colour.BLUE);
        assertEquals(gameControllerTwo.getGame().getActiveCharacterCard(), gameControllerTwo.getGame().getBasicState());
    }

    // Tests that after 10 rounds the game ends
    @RepeatedTest(10)
    void testGameEndAfter10Rounds() {
        // adding player to the game
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_3);
        // First turn
        gameControllerTwo.playAssistantCard(0, 0);
        gameControllerTwo.playAssistantCard(1, 9);

        int i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(0, colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature(0, 1);
        gameControllerTwo.chooseCloudTile(0, 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(1, colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature(1, 1);
        gameControllerTwo.chooseCloudTile(1, 0);

        // Second turn
        gameControllerTwo.playAssistantCard(0, 1);
        gameControllerTwo.playAssistantCard(1, 8);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(0, colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature(0, 1);
        gameControllerTwo.chooseCloudTile(0, 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(1, colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature(1, 1);
        gameControllerTwo.chooseCloudTile(1, 0);

        //Third turn
        gameControllerTwo.playAssistantCard(0, 2);
        gameControllerTwo.playAssistantCard(1, 7);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(0, colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature(0, 1);
        gameControllerTwo.chooseCloudTile(0, 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(1, colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature(1, 1);
        gameControllerTwo.chooseCloudTile(1, 0);

        //Fourth turn
        gameControllerTwo.playAssistantCard(0, 3);
        gameControllerTwo.playAssistantCard(1, 6);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(0, colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature(0, 1);
        gameControllerTwo.chooseCloudTile(0, 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(1, colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature(1, 1);
        gameControllerTwo.chooseCloudTile(1, 0);

        //Fifth turn
        gameControllerTwo.playAssistantCard(0, 4);
        gameControllerTwo.playAssistantCard(1, 5);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(0, colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature(0, 1);
        gameControllerTwo.chooseCloudTile(0, 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(1, colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature(1, 1);
        gameControllerTwo.chooseCloudTile(1, 0);

        //Sixth turn
        gameControllerTwo.playAssistantCard(0, 5);
        gameControllerTwo.playAssistantCard(1, 4);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(1, colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature(1, 1);
        gameControllerTwo.chooseCloudTile(1, 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(0, colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature(0, 1);
        gameControllerTwo.chooseCloudTile(0, 0);

        //Seventh turn
        gameControllerTwo.playAssistantCard(1, 3);
        gameControllerTwo.playAssistantCard(0, 6);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(1, colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature(1, 1);
        gameControllerTwo.chooseCloudTile(1, 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(0, colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature(0, 1);
        gameControllerTwo.chooseCloudTile(0, 0);

        // Eighth turn
        gameControllerTwo.playAssistantCard(1, 2);
        gameControllerTwo.playAssistantCard(0, 7);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(1, colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature(1, 1);
        gameControllerTwo.chooseCloudTile(1, 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(0, colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature(0, 1);
        gameControllerTwo.chooseCloudTile(0, 0);

        // Ninth turn
        gameControllerTwo.playAssistantCard(1, 1);
        gameControllerTwo.playAssistantCard(0, 8);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(1, colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature(1, 1);
        gameControllerTwo.chooseCloudTile(1, 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(0, colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature(0, 1);
        gameControllerTwo.chooseCloudTile(0, 0);

        // Tenth turn
        gameControllerTwo.playAssistantCard(1, 0);
        gameControllerTwo.playAssistantCard(0, 9);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(1, colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature(1, 1);
        gameControllerTwo.chooseCloudTile(1, 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(0, colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature(0, 1);
        gameControllerTwo.chooseCloudTile(0, 0);

        assertEquals(GamePhase.END_GAME, gameControllerTwo.getGame().getGamePhase());
        assertEquals(11, gameControllerTwo.getGame().getRound());
        for (int j = 0; j < 10; j++) {
            assertFalse(gameControllerTwo.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameControllerTwo.getGame().getAssistantCard(j)));
            assertFalse(gameControllerTwo.getGame().getPlayerByIndex(1).isAssistantCardPresent(gameControllerTwo.getGame().getAssistantCard(j)));
        }
        assertEquals(12, gameControllerTwo.getGame().getTable().getNumberOfGroupIsland());
        assertEquals("Laura", gameControllerTwo.getGame().getWinner().getNickname());
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
        assertEquals(TowerColour.WHITE, gameControllerThree.getGame().getPlayerByIndex(0).getTowerColour());
        assertEquals(1, gameControllerThree.getGame().getNumberOfPlayer());
        assertEquals("Viola", gameControllerThree.getGame().getPlayerByIndex(0).getNickname());
        assertEquals(Wizard.TYPE_1, gameControllerThree.getGame().getPlayerByIndex(0).getWizard());
        gameControllerThree.addPlayer("Laura", Wizard.TYPE_1);
        assertEquals(1, gameControllerThree.getGame().getNumberOfPlayer());
        gameControllerThree.addPlayer("Laura", Wizard.TYPE_3);
        assertEquals(TowerColour.BLACK, gameControllerThree.getGame().getPlayerByIndex(1).getTowerColour());
        assertEquals("Laura", gameControllerThree.getGame().getPlayerByIndex(1).getNickname());
        assertEquals(Wizard.TYPE_3, gameControllerThree.getGame().getPlayerByIndex(1).getWizard());
        assertEquals(2, gameControllerThree.getGame().getNumberOfPlayer());
        gameControllerThree.addPlayer("Sara", Wizard.TYPE_1);
        assertEquals(GamePhase.SETTING, gameControllerThree.getGame().getGamePhase());
        assertEquals(2, gameControllerThree.getGame().getNumberOfPlayer());
        gameControllerThree.addPlayer("Sara", Wizard.TYPE_4);
        assertEquals(TowerColour.GREY, gameControllerThree.getGame().getPlayerByIndex(2).getTowerColour());
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
        assertEquals(GamePhase.SETTING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.WAITING, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_2);

        assertEquals(GamePhase.SETTING, gameControllerTwo.getGame().getGamePhase());

        assertEquals(1, gameControllerTwo.getGame().getNumberOfPlayer());

        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        assertEquals(2, gameControllerTwo.getGame().getNumberOfPlayer());

        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getGamePhase());

        assertEquals(TurnPhase.WAITING, gameControllerTwo.getGame().getTurnPhase());
        assertEquals(gameControllerTwo.getGame().getPlayerByIndex(0), gameControllerTwo.getGame().getCurrentPlayer());

        gameControllerTwo.playAssistantCard(1, 0);

        assertNull(gameControllerTwo.getGame().getPlayerByIndex(0).getCurrentAssistantCard());

        //First turn

        gameControllerTwo.playAssistantCard(0, 4);
        assertEquals(gameControllerTwo.getGame().getAssistantCard(4), gameControllerTwo.getGame().getPlayerByIndex(0).getCurrentAssistantCard());
        assertFalse(gameControllerTwo.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameControllerTwo.getGame().getAssistantCard(4)));
        assertEquals(gameControllerTwo.getGame().getCurrentPlayer(), gameControllerTwo.getGame().getPlayerByIndex(1));

        for (int i = 0; i < gameControllerTwo.getGame().getNumberOfPlayer(); i++) {
            assertEquals(gameControllerTwo.getGame().getNumberStudentsEntrance(), gameControllerTwo.getGame().getPlayerByIndex(i).getSchoolBoard().getNumberStudentsEntrance());
            assertEquals(gameControllerTwo.getGame().getNumberOfTowersPerPlayer(), gameControllerTwo.getGame().getPlayerByIndex(i).getSchoolBoard().getTowers());
        }
        gameControllerTwo.playAssistantCard(1, 8);

        assertEquals(gameControllerTwo.getGame().getCurrentPlayer(), gameControllerTwo.getGame().getPlayerByIndex(0));

        int i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(0, colour, 2, 0);
                    i++;
                }
            }
        }

        assertEquals(4, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(7, gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getNumberStudentsEntrance());

        int num = 0;
        for (Colour colour : Colour.values()) {
            num += gameControllerTwo.getGame().getTable().getGroupIslandByIndex(2).getNumberStudent(colour);
        }

        assertEquals(4, num);
        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.moveMotherNature(0, 2);

        assertEquals(2, gameControllerTwo.getGame().getTable().getMotherNaturePosition());
        assertNull(gameControllerTwo.getGame().getTable().getGroupIslandByIndex(2).getInfluence());

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.chooseCloudTile(0, 1);
        assertEquals(7, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerTwo.getGame().getTurnPhase());
        assertEquals(1, gameControllerTwo.getGame().getTable().getNumberOfCloudTile());
        assertEquals(gameControllerTwo.getGame().getCurrentPlayer(), gameControllerTwo.getGame().getPlayerByIndex(1));

        int j = 0;
        while (j < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToDiningRoom(1, colour);
                    j++;
                }
            }
        }

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameControllerTwo.getGame().getTurnPhase());

        assertEquals(4, gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(7, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());

        gameControllerTwo.moveMotherNature(1, 3);

        assertEquals(5, gameControllerTwo.getGame().getTable().getMotherNaturePosition());

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.chooseCloudTile(1, 1);
        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.chooseCloudTile(1, 0);
        assertEquals(7, gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(2, gameControllerTwo.getGame().getTable().getNumberOfCloudTile());
        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.WAITING, gameControllerTwo.getGame().getTurnPhase());

        assertEquals(gameControllerTwo.getGame().getCurrentPlayer(), gameControllerTwo.getGame().getPlayerByIndex(0));


        //Second turn

        gameControllerTwo.playAssistantCard(0, 9);
        gameControllerTwo.playAssistantCard(1, 6);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(1, colour, 9, 0);
                    i++;
                }
            }
        }

        gameControllerTwo.moveMotherNature(1, 1);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.chooseCloudTile(1, 1);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(0, colour, 4, 0);
                    i++;
                }
            }
        }

        gameControllerTwo.moveMotherNature(0, 2);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());


        gameControllerTwo.chooseCloudTile(0, 0);

        //Third turn

        gameControllerTwo.playAssistantCard(1, 3);
        gameControllerTwo.playAssistantCard(0, 5);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(1, colour, 0, 0);
                    i++;
                }
            }
        }

        gameControllerTwo.moveMotherNature(1, 2);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.chooseCloudTile(1, 1);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(0, colour, 2, 0);
                    i++;
                }
            }
        }

        gameControllerTwo.moveMotherNature(0, 1);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());


        gameControllerTwo.chooseCloudTile(0, 0);

        //Fourth turn
        gameControllerTwo.playAssistantCard(1, 7);
        gameControllerTwo.playAssistantCard(0, 1);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(0, colour, 2, 0);
                    i++;
                }
            }
        }

        gameControllerTwo.moveMotherNature(0, 1);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.chooseCloudTile(0, 1);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(1, colour, 3, 0);
                    i++;
                }
            }
        }

        gameControllerTwo.moveMotherNature(1, 1);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());


        gameControllerTwo.chooseCloudTile(1, 0);

        //Fifth turn
        gameControllerTwo.playAssistantCard(0, 8);
        gameControllerTwo.playAssistantCard(1, 2);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(1, colour, 4, 0);
                    i++;
                }
            }
        }

        gameControllerTwo.moveMotherNature(1, 2);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.chooseCloudTile(1, 1);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(0, colour, 6, 0);
                    i++;
                }
            }
        }

        gameControllerTwo.moveMotherNature(0, 1);

        if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getTowers() == 0) {
            assertEquals(GamePhase.END_GAME, gameControllerTwo.getGame().getGamePhase());
            assertEquals("Viola", gameControllerTwo.getGame().getWinner().getNickname());
        } else {
            assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        }


        gameControllerTwo.chooseCloudTile(0, 0);

        //Sixth turn
        gameControllerTwo.playAssistantCard(1, 5);
        gameControllerTwo.playAssistantCard(0, 3);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(0, colour, 4, 0);
                    i++;
                }
            }
        }

        gameControllerTwo.moveMotherNature(0, 1);

        if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getTowers() == 0) {
            assertEquals(GamePhase.END_GAME, gameControllerTwo.getGame().getGamePhase());
            assertEquals("Viola", gameControllerTwo.getGame().getWinner().getNickname());
        } else {
            assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        }

        gameControllerTwo.chooseCloudTile(0, 1);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland(1, colour, 6, 0);
                    i++;
                }
            }
        }

        gameControllerTwo.moveMotherNature(1, 1);

        if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getTowers() == 0) {
            assertEquals(GamePhase.END_GAME, gameControllerTwo.getGame().getGamePhase());
            assertEquals("Viola", gameControllerTwo.getGame().getWinner().getNickname());
        } else {
            assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        }
    }

    @Test
    void professorAreCorrect() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_3);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_1);

        gameControllerTwo.playAssistantCard(0, 0);
        gameControllerTwo.playAssistantCard(1, 1);

        Colour colour1 = Colour.RED;
        for (Colour colour : Colour.values()) {
            if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                colour1 = colour;
                break;
            }
        }

        gameControllerTwo.moveStudentToDiningRoom(0, colour1);
        assertTrue(gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().hasProfessor(colour1));

        for (Colour colour : Colour.values()) {
            if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                colour1 = colour;
                break;
            }
        }

        gameControllerTwo.moveStudentToDiningRoom(0, colour1);
        assertTrue(gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().hasProfessor(colour1));

    }

    @Test
    void unify() {
        GameController gameController;
        gameController = new GameController(true, 2);
        gameController.addPlayer("Viola", Wizard.TYPE_1);
        gameController.addPlayer("Laura", Wizard.TYPE_3);

        // First Round
        gameController.playAssistantCard(0, 0);
        gameController.playAssistantCard(1, 1);

        int j = 0;
        while (j <= 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToDiningRoom(0, colour);
                    j++;
                }
            }
        }

        gameController.moveMotherNature(0, 1);
        gameController.chooseCloudTile(0, 0);

        j = 0;
        while (j <= 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(1, colour, 2, 0);
                    j++;
                }
            }
        }

        gameController.moveMotherNature(1, 1);
        gameController.chooseCloudTile(1, 0);


        // Second Round
        gameController.playAssistantCard(0, 1);
        gameController.playAssistantCard(1, 2);

        j = 0;
        while (j <= 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToDiningRoom(0, colour);
                    j++;
                }
            }
        }

        gameController.moveMotherNature(0, 1);
        gameController.chooseCloudTile(0, 0);

        j = 0;
        while (j <= 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(1, colour, 2, 0);
                    j++;
                }
            }
        }

        gameController.moveMotherNature(1, 2);
        gameController.chooseCloudTile(1, 0);

        // Third turn
        gameController.playAssistantCard(0, 2);
        gameController.playAssistantCard(1, 3);

        j = 0;
        while (j <= 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToDiningRoom(0, colour);
                    j++;
                }
            }
        }

        gameController.moveMotherNature(0, 1);
        gameController.chooseCloudTile(0, 0);

        j = 0;
        while (j <= 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(1, colour, 2, 0);
                    j++;
                }
            }
        }

        gameController.moveMotherNature(1, 2);
        gameController.chooseCloudTile(1, 0);

    }

    @Test
    void checkAssistantCard() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_3);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_4);

        gameControllerTwo.playAssistantCard(0, 0);
        gameControllerTwo.playAssistantCard(1, 0);
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerTwo.playAssistantCard(1, 1);
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerTwo.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerTwo.getGame().setTurnPhase(TurnPhase.WAITING);

        gameControllerTwo.playAssistantCard(0, 1);
        gameControllerTwo.playAssistantCard(1, 2);
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerTwo.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerTwo.getGame().setTurnPhase(TurnPhase.WAITING);

        gameControllerTwo.playAssistantCard(0, 2);
        gameControllerTwo.playAssistantCard(1, 0);
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerTwo.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerTwo.getGame().setTurnPhase(TurnPhase.WAITING);

        gameControllerTwo.playAssistantCard(1, 4);
        gameControllerTwo.playAssistantCard(0, 3);
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerTwo.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerTwo.getGame().setTurnPhase(TurnPhase.WAITING);

        gameControllerTwo.playAssistantCard(0, 4);
        gameControllerTwo.playAssistantCard(1, 5);
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerTwo.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerTwo.getGame().setTurnPhase(TurnPhase.WAITING);

        gameControllerTwo.playAssistantCard(0, 5);
        gameControllerTwo.playAssistantCard(1, 3);
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerTwo.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerTwo.getGame().setTurnPhase(TurnPhase.WAITING);

        gameControllerTwo.playAssistantCard(1, 7);
        gameControllerTwo.playAssistantCard(0, 6);
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerTwo.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerTwo.getGame().setTurnPhase(TurnPhase.WAITING);

        gameControllerTwo.playAssistantCard(0, 7);
        gameControllerTwo.playAssistantCard(1, 8);
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerTwo.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerTwo.getGame().setTurnPhase(TurnPhase.WAITING);

        gameControllerTwo.playAssistantCard(0, 8);
        gameControllerTwo.playAssistantCard(1, 6);
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerTwo.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerTwo.getGame().setTurnPhase(TurnPhase.WAITING);

        gameControllerTwo.playAssistantCard(1, 9);
        gameControllerTwo.playAssistantCard(0, 9);
        assertEquals(gameControllerTwo.getGame().getPlayerByIndex(1), gameControllerTwo.getGame().getCurrentPlayer());
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAYING);
        for (int i = 0; i < 10; i++) {
            assertFalse(gameControllerTwo.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameControllerTwo.getGame().getAssistantCard(i)));
            assertFalse(gameControllerTwo.getGame().getPlayerByIndex(1).isAssistantCardPresent(gameControllerTwo.getGame().getAssistantCard(i)));
        }
    }
}