package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.server.Lobby;
import it.polimi.ingsw.view.messages.MoveMotherNature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    private GameController gameControllerTwo;
    private GameController gameControllerThree;

    @BeforeEach
    void setUp() {
        gameControllerTwo = new GameController(false, 2, new Lobby());
        gameControllerTwo.setUp();
        gameControllerThree = new GameController(false, 3, new Lobby());
        gameControllerThree.setUp();
    }

    @Test
    void setting() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_4);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_3);

        gameControllerTwo.setUpTableAndPlayers();

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
        assertEquals(100, size);

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
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_4);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_3);

        gameControllerTwo.setUpTableAndPlayers();

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
        gameControllerTwo.setUpTableAndPlayers();
        Game game = gameControllerTwo.getGame();
        gameControllerTwo.playCharacterCard("Viola", 0);
        assertEquals(game, gameControllerTwo.getGame());
    }

    @Test
    void playAssistantCard() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        gameControllerTwo.setUpTableAndPlayers();
        gameControllerTwo.moveStudentToIsland("Viola", Colour.PINK, 0, 0);
        assertEquals(0, gameControllerTwo.getGame().getTable().getGroupIslandByIndex(0).getIslandByIndex(0).getStudents(Colour.PINK));
        gameControllerTwo.playAssistantCard("Laura", 0);
        assertNull(gameControllerTwo.getGame().getPlayerByIndex(1).getCurrentAssistantCard());
        gameControllerTwo.playAssistantCard("Viola", 0);
        assertEquals(gameControllerTwo.getGame().getAssistantCard(0), gameControllerTwo.getGame().getPlayerByIndex(0).getCurrentAssistantCard());
        assertFalse(gameControllerTwo.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameControllerTwo.getGame().getAssistantCard(0)));
    }

    @Test
    void playAssistantCardCornerCase() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        gameControllerTwo.setUpTableAndPlayers();

        for (int i = 0; i < 9; i++) {
            gameControllerTwo.playAssistantCard("Viola", i);
            assertEquals(gameControllerTwo.getGame().getAssistantCard(i), gameControllerTwo.getGame().getPlayerByIndex(0).getCurrentAssistantCard());
            gameControllerTwo.playAssistantCard("Laura", (i + 1) % 9);
            assertEquals(gameControllerTwo.getGame().getAssistantCard((i + 1) % 9), gameControllerTwo.getGame().getPlayerByIndex(1).getCurrentAssistantCard());
            gameControllerTwo.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        }
        gameControllerTwo.playAssistantCard("Laura", 9);
        gameControllerTwo.playAssistantCard("Viola", 9);
        assertEquals(gameControllerTwo.getGame().getAssistantCard(9), gameControllerTwo.getGame().getPlayerByIndex(1).getCurrentAssistantCard());
        assertEquals(gameControllerTwo.getGame().getAssistantCard(9), gameControllerTwo.getGame().getPlayerByIndex(0).getCurrentAssistantCard());
    }

    @Test
    void moveStudentToIsland() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        assertNull(gameControllerTwo.getGame().getPlayerByIndex(0).getCurrentAssistantCard());
        gameControllerTwo.moveStudentToIsland("Viola", Colour.PINK, 0, 0);
        assertEquals(0, gameControllerTwo.getGame().getTable().getGroupIslandByIndex(0).getIslandByIndex(0).getStudents(Colour.PINK));
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        assertNull(gameControllerTwo.getGame().getPlayerByIndex(1).getCurrentAssistantCard());
        gameControllerTwo.setUpTableAndPlayers();
        gameControllerTwo.moveStudentToIsland("Viola", Colour.PINK, 0, 0);
        assertEquals(0, gameControllerTwo.getGame().getTable().getGroupIslandByIndex(0).getIslandByIndex(0).getStudents(Colour.PINK));
        gameControllerTwo.playAssistantCard("Viola", 0);
        assertEquals(gameControllerTwo.getGame().getAssistantCard(0), gameControllerTwo.getGame().getPlayerByIndex(0).getCurrentAssistantCard());
        assertFalse(gameControllerTwo.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameControllerTwo.getGame().getAssistantCard(0)));
        gameControllerTwo.playAssistantCard("Laura", 0);
        assertNull(gameControllerTwo.getGame().getPlayerByIndex(1).getCurrentAssistantCard());
        gameControllerTwo.playAssistantCard("Laura", 1);
        assertEquals(gameControllerTwo.getGame().getAssistantCard(1), gameControllerTwo.getGame().getPlayerByIndex(1).getCurrentAssistantCard());
        assertFalse(gameControllerTwo.getGame().getPlayerByIndex(1).isAssistantCardPresent(gameControllerTwo.getGame().getAssistantCard(1)));
        int num = gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(Colour.PINK);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.PINK, 0, 0);
        if (num > 0) {
            assertEquals(1, gameControllerTwo.getGame().getTable().getGroupIslandByIndex(0).getIslandByIndex(0).getStudents(Colour.PINK));
            assertEquals(6, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        } else {
            assertEquals(0, gameControllerTwo.getGame().getTable().getGroupIslandByIndex(0).getIslandByIndex(0).getStudents(Colour.PINK));
            assertEquals(7, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        }
    }

    @RepeatedTest(100)
    void moveStudentToDiningRoom() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        gameControllerTwo.setUpTableAndPlayers();
        gameControllerTwo.playAssistantCard("Viola", 0);
        gameControllerTwo.playAssistantCard("Laura", 1);
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToDiningRoom("Viola", colour);
                }
            }
        }
        assertEquals(4, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());

        for (Colour colour : Colour.values()) {
            if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getDiningRoom(colour) > 0) {
                assertTrue(gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().hasProfessor(colour));
                assertFalse(gameControllerTwo.getGame().getTable().isProfessorOnTable(colour));
            }
        }
    }

    @Test
    void moveMotherNature() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        gameControllerTwo.setUpTableAndPlayers();
        gameControllerTwo.playAssistantCard("Viola", 0);
        gameControllerTwo.playAssistantCard("Laura", 1);
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToDiningRoom("Viola", colour);
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        assertEquals(1, gameControllerTwo.getGame().getTable().getMotherNaturePosition());
    }

    @Test
    void addPlayer() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        assertEquals(1, gameControllerTwo.getGame().getNumberOfPlayer());
        assertEquals(TowerColour.WHITE, gameControllerTwo.getGame().getPlayerByIndex(0).getTowerColour());

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
        gameControllerTwo.setUpTableAndPlayers();
        gameControllerTwo.playAssistantCard("Viola", 0);
        gameControllerTwo.playAssistantCard("Laura", 1);
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToDiningRoom("Viola", colour);
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        assertEquals(1, gameControllerTwo.getGame().getTable().getMotherNaturePosition());
        gameControllerTwo.chooseCloudTile("Viola", 0);
        assertEquals(7, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
    }

    @Test
    void setColour() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        gameControllerTwo.setUpTableAndPlayers();
        gameControllerTwo.playAssistantCard("Viola", 0);
        gameControllerTwo.playAssistantCard("Laura", 1);
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToDiningRoom("Viola", colour);
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        assertEquals(1, gameControllerTwo.getGame().getTable().getMotherNaturePosition());
        gameControllerTwo.chooseCloudTile("Viola", 0);
        assertEquals(7, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        gameControllerTwo.setColour("Viola", Colour.PINK);
        assertEquals(gameControllerTwo.getGame().getActiveCharacterCard(), gameControllerTwo.getGame().getBasicState());
    }

    @Test
    void setColourAndIsland() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        gameControllerTwo.setUpTableAndPlayers();
        gameControllerTwo.playAssistantCard("Viola", 0);
        gameControllerTwo.playAssistantCard("Laura", 1);
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToDiningRoom("Viola", colour);
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        assertEquals(1, gameControllerTwo.getGame().getTable().getMotherNaturePosition());
        gameControllerTwo.chooseCloudTile("Viola", 0);
        assertEquals(7, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        gameControllerTwo.setColourAndIsland("Viola", Colour.PINK, 0, 0);
        assertEquals(gameControllerTwo.getGame().getActiveCharacterCard(), gameControllerTwo.getGame().getBasicState());
    }

    @Test
    void setGroupIsland() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        gameControllerTwo.setUpTableAndPlayers();
        gameControllerTwo.playAssistantCard("Viola", 0);
        gameControllerTwo.playAssistantCard("Laura", 1);
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToDiningRoom("Viola", colour);
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        assertEquals(1, gameControllerTwo.getGame().getTable().getMotherNaturePosition());
        gameControllerTwo.chooseCloudTile("Viola", 0);
        assertEquals(7, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(gameControllerTwo.getGame().getActiveCharacterCard(), gameControllerTwo.getGame().getBasicState());
        gameControllerTwo.setGroupIsland("Viola", 0);
        assertEquals(gameControllerTwo.getGame().getActiveCharacterCard(), gameControllerTwo.getGame().getBasicState());
    }

    @Test
    void setColourDiningRoomEntrance() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        gameControllerTwo.setUpTableAndPlayers();
        gameControllerTwo.playAssistantCard("Viola", 0);
        gameControllerTwo.playAssistantCard("Laura", 1);
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToDiningRoom("Viola", colour);
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        assertEquals(1, gameControllerTwo.getGame().getTable().getMotherNaturePosition());
        gameControllerTwo.chooseCloudTile("Viola", 0);
        assertEquals(7, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(gameControllerTwo.getGame().getActiveCharacterCard(), gameControllerTwo.getGame().getBasicState());
        gameControllerTwo.setColourDiningRoomEntrance("Viola", Colour.RED, Colour.BLUE);
        assertEquals(gameControllerTwo.getGame().getActiveCharacterCard(), gameControllerTwo.getGame().getBasicState());
    }

    @Test
    void setColourCardEntrance() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        gameControllerTwo.setUpTableAndPlayers();
        gameControllerTwo.playAssistantCard("Viola", 0);
        gameControllerTwo.playAssistantCard("Laura", 1);
        for (int i = 0; i < 3; i++) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToDiningRoom("Viola", colour);
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        assertEquals(1, gameControllerTwo.getGame().getTable().getMotherNaturePosition());
        gameControllerTwo.chooseCloudTile("Viola", 0);
        assertEquals(7, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(gameControllerTwo.getGame().getActiveCharacterCard(), gameControllerTwo.getGame().getBasicState());
        gameControllerTwo.setColourCardEntrance("Viola", Colour.RED, Colour.BLUE);
        assertEquals(gameControllerTwo.getGame().getActiveCharacterCard(), gameControllerTwo.getGame().getBasicState());
    }

    // Tests that after 10 rounds the game ends
    @RepeatedTest(100)
    void testGameEndAfter10Rounds() {
        // adding player to the game
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_3);
        gameControllerTwo.setUpTableAndPlayers();
        // First turn
        assertNull(gameControllerTwo.getGame().getPlayerByNickname("Viola").getCurrentAssistantCard());
        assertNull(gameControllerTwo.getGame().getPlayerByNickname("Laura").getCurrentAssistantCard());
        gameControllerTwo.playAssistantCard("Laura", 0);
        gameControllerTwo.playAssistantCard("Viola", 9);

        int i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        // Second turn
        assertNull(gameControllerTwo.getGame().getPlayerByNickname("Viola").getCurrentAssistantCard());
        assertNull(gameControllerTwo.getGame().getPlayerByNickname("Laura").getCurrentAssistantCard());
        gameControllerTwo.playAssistantCard("Laura", 1);
        gameControllerTwo.playAssistantCard("Viola", 8);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        //Third turn
        assertNull(gameControllerTwo.getGame().getPlayerByNickname("Viola").getCurrentAssistantCard());
        assertNull(gameControllerTwo.getGame().getPlayerByNickname("Laura").getCurrentAssistantCard());
        gameControllerTwo.playAssistantCard("Laura", 2);
        gameControllerTwo.playAssistantCard("Viola", 7);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        //Fourth turn
        assertNull(gameControllerTwo.getGame().getPlayerByNickname("Viola").getCurrentAssistantCard());
        assertNull(gameControllerTwo.getGame().getPlayerByNickname("Laura").getCurrentAssistantCard());
        gameControllerTwo.playAssistantCard("Laura", 3);
        gameControllerTwo.playAssistantCard("Viola", 6);
        gameControllerTwo.update(new MoveMotherNature("Viola", 3));

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        //Fifth turn
        assertNull(gameControllerTwo.getGame().getPlayerByNickname("Viola").getCurrentAssistantCard());
        assertNull(gameControllerTwo.getGame().getPlayerByNickname("Laura").getCurrentAssistantCard());
        gameControllerTwo.playAssistantCard("Laura", 4);
        gameControllerTwo.playAssistantCard("Viola", 5);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        //Sixth turn
        assertNull(gameControllerTwo.getGame().getPlayerByNickname("Viola").getCurrentAssistantCard());
        assertNull(gameControllerTwo.getGame().getPlayerByNickname("Laura").getCurrentAssistantCard());
        gameControllerTwo.playAssistantCard("Laura", 5);
        gameControllerTwo.playAssistantCard("Viola", 4);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        //Seventh turn
        assertNull(gameControllerTwo.getGame().getPlayerByNickname("Viola").getCurrentAssistantCard());
        assertNull(gameControllerTwo.getGame().getPlayerByNickname("Laura").getCurrentAssistantCard());
        gameControllerTwo.playAssistantCard("Viola", 3);
        gameControllerTwo.playAssistantCard("Laura", 6);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        // Eighth turn
        assertNull(gameControllerTwo.getGame().getPlayerByNickname("Viola").getCurrentAssistantCard());
        assertNull(gameControllerTwo.getGame().getPlayerByNickname("Laura").getCurrentAssistantCard());
        gameControllerTwo.playAssistantCard("Viola", 2);
        gameControllerTwo.playAssistantCard("Laura", 7);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        // Ninth turn
        assertNull(gameControllerTwo.getGame().getPlayerByNickname("Viola").getCurrentAssistantCard());
        assertNull(gameControllerTwo.getGame().getPlayerByNickname("Laura").getCurrentAssistantCard());
        gameControllerTwo.playAssistantCard("Viola", 1);
        gameControllerTwo.playAssistantCard("Laura", 8);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        // Tenth turn
        assertNull(gameControllerTwo.getGame().getPlayerByNickname("Viola").getCurrentAssistantCard());
        assertNull(gameControllerTwo.getGame().getPlayerByNickname("Laura").getCurrentAssistantCard());
        gameControllerTwo.playAssistantCard("Viola", 0);
        gameControllerTwo.playAssistantCard("Laura", 9);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

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
    @RepeatedTest(100)
    void threePlayerGame() {
        // start of the game
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
        gameControllerThree.setGroupIsland("Elisa", 0);
        gameControllerThree.setColourDiningRoomEntrance("Elisa", Colour.RED, Colour.GREEN);
        gameControllerThree.setColour("Elisa", Colour.RED);
        gameControllerThree.setColourCardEntrance("Elisa", Colour.RED, Colour.BLUE);
        gameControllerThree.setColourAndIsland("Elisa", Colour.RED, 0, 0);
        assertEquals(3, gameControllerThree.getGame().getNumberOfPlayer());
        gameControllerThree.setUpTableAndPlayers();
        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.PLAY_ASSISTANT_CARD, gameControllerThree.getGame().getTurnPhase());
        assertEquals(gameControllerThree.getGame().getCurrentPlayer(), gameControllerThree.getGame().getPlayerByIndex(0));
        for (int i = 0; i < gameControllerThree.getGame().getNumberOfPlayer(); i++) {
            assertEquals(gameControllerThree.getGame().getNumberStudentsEntrance(), gameControllerThree.getGame().getPlayerByIndex(i).getSchoolBoard().getNumberStudentsEntrance());
            assertEquals(gameControllerThree.getGame().getNumberOfTowersPerPlayer(), gameControllerThree.getGame().getPlayerByIndex(i).getSchoolBoard().getTowers());
        }

        // playing AssistantCard first turn
        assertNull(gameControllerThree.getGame().getPlayerByNickname("Viola").getCurrentAssistantCard());
        assertNull(gameControllerThree.getGame().getPlayerByNickname("Laura").getCurrentAssistantCard());
        gameControllerThree.playAssistantCard("Laura", 0);
        assertNull(gameControllerThree.getGame().getPlayerByIndex(1).getCurrentAssistantCard());
        gameControllerThree.playAssistantCard("Viola", 4);
        assertEquals(gameControllerThree.getGame().getAssistantCard(4), gameControllerThree.getGame().getPlayerByIndex(0).getCurrentAssistantCard());
        assertFalse(gameControllerThree.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameControllerThree.getGame().getAssistantCard(4)));
        assertEquals(gameControllerThree.getGame().getCurrentPlayer(), gameControllerThree.getGame().getPlayerByIndex(1));
        gameControllerThree.playAssistantCard("Laura", 7);
        gameControllerThree.playAssistantCard("Sara", 2);
        assertEquals(gameControllerThree.getGame().getCurrentPlayer(), gameControllerThree.getGame().getPlayerByIndex(2));
        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerThree.getGame().getTurnPhase());

        // first turn
        int i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(2).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Sara", colour, 1, 0);
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

        gameControllerThree.moveMotherNature("Sara", 1);
        assertEquals(1, gameControllerThree.getGame().getTable().getMotherNaturePosition());
        assertNull(gameControllerThree.getGame().getTable().getGroupIslandByIndex(1).getInfluence());

        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerThree.getGame().getTurnPhase());

        gameControllerThree.chooseCloudTile("Sara", 2);
        assertEquals(9, gameControllerThree.getGame().getPlayerByIndex(2).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerThree.getGame().getTurnPhase());
        assertEquals(2, gameControllerThree.getGame().getTable().getNumberOfCloudTile());
        assertEquals(gameControllerThree.getGame().getCurrentPlayer(), gameControllerThree.getGame().getPlayerByIndex(0));


        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToDiningRoom("Viola", colour);
                    i++;
                }
            }
        }

        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameControllerThree.getGame().getTurnPhase());
        assertEquals(5, gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(9, gameControllerThree.getGame().getPlayerByIndex(1).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(9, gameControllerThree.getGame().getPlayerByIndex(2).getSchoolBoard().getNumberStudentsEntrance());

        gameControllerThree.moveMotherNature("Viola", 4);
        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameControllerThree.getGame().getTurnPhase());
        gameControllerThree.moveMotherNature("Viola", 3);
        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerThree.getGame().getTurnPhase());

        gameControllerThree.chooseCloudTile("Viola", 2);
        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerThree.getGame().getTurnPhase());

        gameControllerThree.chooseCloudTile("Viola", 1);
        assertEquals(1, gameControllerThree.getGame().getTable().getNumberOfCloudTile());
        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerThree.getGame().getTurnPhase());
        assertEquals(gameControllerThree.getGame().getCurrentPlayer(), gameControllerThree.getGame().getPlayerByIndex(1));


        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }

        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameControllerThree.getGame().getTurnPhase());
        assertEquals(5, gameControllerThree.getGame().getPlayerByIndex(1).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(9, gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(9, gameControllerThree.getGame().getPlayerByIndex(2).getSchoolBoard().getNumberStudentsEntrance());

        gameControllerThree.moveMotherNature("Laura", 4);
        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerThree.getGame().getTurnPhase());
        gameControllerThree.chooseCloudTile("Laura", 0);

        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.PLAY_ASSISTANT_CARD, gameControllerThree.getGame().getTurnPhase());
        assertEquals(3, gameControllerThree.getGame().getTable().getNumberOfCloudTile());

        // second turn
        gameControllerThree.playAssistantCard("Sara", 7);
        gameControllerThree.playAssistantCard("Viola", 2);
        gameControllerThree.playAssistantCard("Laura", 4);

        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerThree.getGame().getTurnPhase());

        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Viola", colour, 7, 0);
                    i++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Viola", 2);
        gameControllerThree.chooseCloudTile("Viola", 0);

        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Laura", colour, 10, 0);
                    i++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Laura", 3);
        gameControllerThree.chooseCloudTile("Laura", 0);

        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(2).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Sara", colour, 7, 0);
                    i++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Sara", 4);
        gameControllerThree.chooseCloudTile("Sara", 0);

        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.PLAY_ASSISTANT_CARD, gameControllerThree.getGame().getTurnPhase());

        // third turn
        assertNull(gameControllerThree.getGame().getPlayerByNickname("Viola").getCurrentAssistantCard());
        assertNull(gameControllerThree.getGame().getPlayerByNickname("Laura").getCurrentAssistantCard());
        assertNull(gameControllerThree.getGame().getPlayerByNickname("Sara").getCurrentAssistantCard());
        gameControllerThree.playAssistantCard("Viola", 5);
        gameControllerThree.playAssistantCard("Laura", 1);
        gameControllerThree.playAssistantCard("Sara", 8);

        assertEquals(GamePhase.PLAYING, gameControllerThree.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerThree.getGame().getTurnPhase());

        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Laura", colour, 10, 0);
                    i++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Laura", 1);
        gameControllerThree.chooseCloudTile("Laura", 0);


        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Viola", colour, 7, 0);
                    i++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Viola", 3);
        gameControllerThree.chooseCloudTile("Viola", 0);

        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(2).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Sara", colour, 7, 0);
                    i++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Sara", 4);
        gameControllerThree.chooseCloudTile("Sara", 0);

        if (gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getTowers() == 0) {
            assertEquals(GamePhase.END_GAME, gameControllerThree.getGame().getGamePhase());
            assertEquals("Viola", gameControllerThree.getGame().getWinner().getNickname());
        } else {
            assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerThree.getGame().getGamePhase());
            assertEquals(TurnPhase.PLAY_ASSISTANT_CARD, gameControllerThree.getGame().getTurnPhase());
        }

        // fourth turn
        gameControllerThree.playAssistantCard("Laura", 5);
        gameControllerThree.playAssistantCard("Sara", 1);
        gameControllerThree.playAssistantCard("Viola", 8);

        if (gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getTowers() == 0) {
            assertEquals(GamePhase.END_GAME, gameControllerThree.getGame().getGamePhase());
            assertEquals("Viola", gameControllerThree.getGame().getWinner().getNickname());
        } else if (gameControllerThree.getGame().getTable().getBag().isBagEmpty()) {
            assertEquals(GamePhase.END_GAME, gameControllerThree.getGame().getGamePhase());
            assertEquals("Viola", gameControllerThree.getGame().getWinner().getNickname());
        } else if (gameControllerThree.getGame().getTable().getNumberOfGroupIsland() <= 3) {
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
                    gameControllerThree.moveStudentToIsland("Sara", colour, 3, 0);
                    i++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Sara", 1);
        gameControllerThree.chooseCloudTile("Sara", 0);

        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Laura", colour, 4, 0);
                    i++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Laura", 1);
        gameControllerThree.chooseCloudTile("Laura", 0);


        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Viola", colour, 5, 0);
                    i++;
                }
            }
        }

        gameControllerThree.moveMotherNature("Viola", 1);
        gameControllerThree.chooseCloudTile("Viola", 0);

        if (gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getTowers() == 0) {
            assertEquals(GamePhase.END_GAME, gameControllerThree.getGame().getGamePhase());
            assertEquals("Viola", gameControllerThree.getGame().getWinner().getNickname());
        } else if (gameControllerThree.getGame().getTable().getBag().isBagEmpty()) {
            assertEquals(GamePhase.END_GAME, gameControllerThree.getGame().getGamePhase());
            assertEquals("Viola", gameControllerThree.getGame().getWinner().getNickname());
        } else if (gameControllerThree.getGame().getTable().getNumberOfGroupIsland() <= 3) {
            assertEquals(GamePhase.END_GAME, gameControllerThree.getGame().getGamePhase());
            assertEquals("Viola", gameControllerThree.getGame().getWinner().getNickname());
        } else {
            assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerThree.getGame().getGamePhase());
            assertEquals(TurnPhase.PLAY_ASSISTANT_CARD, gameControllerThree.getGame().getTurnPhase());
        }

        // fifth turn
        gameControllerThree.playAssistantCard("Sara", 6);
        gameControllerThree.playAssistantCard("Viola", 3);
        gameControllerThree.playAssistantCard("Laura", 9);

        if (gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getTowers() == 0) {
            assertEquals(GamePhase.END_GAME, gameControllerThree.getGame().getGamePhase());
            assertEquals("Viola", gameControllerThree.getGame().getWinner().getNickname());
        } else if (gameControllerThree.getGame().getTable().getBag().isBagEmpty()) {
            assertEquals(GamePhase.END_GAME, gameControllerThree.getGame().getGamePhase());
            assertEquals("Viola", gameControllerThree.getGame().getWinner().getNickname());
        } else if (gameControllerThree.getGame().getTable().getNumberOfGroupIsland() <= 3) {
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
                    gameControllerThree.moveStudentToIsland("Viola", colour, 1, 0);
                    i++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Viola", 1);
        gameControllerThree.chooseCloudTile("Viola", 0);

        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(2).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Sara", colour, 2, 0);
                    i++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Sara", 1);
        gameControllerThree.chooseCloudTile("Sara", 0);

        i = 0;
        while (i < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Laura", colour, 3, 0);
                    i++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Laura", 1);
        gameControllerThree.chooseCloudTile("Laura", 0);

        if (gameControllerThree.getGame().getPlayerByIndex(0).getSchoolBoard().getTowers() == 0) {
            assertEquals(GamePhase.END_GAME, gameControllerThree.getGame().getGamePhase());
            assertEquals("Viola", gameControllerThree.getGame().getWinner().getNickname());
        } else if (gameControllerThree.getGame().getTable().getBag().isBagEmpty()) {
            assertEquals(GamePhase.END_GAME, gameControllerThree.getGame().getGamePhase());
            assertEquals("Viola", gameControllerThree.getGame().getWinner().getNickname());
        } else if (gameControllerThree.getGame().getTable().getNumberOfGroupIsland() <= 3) {
            assertEquals(GamePhase.END_GAME, gameControllerThree.getGame().getGamePhase());
            assertEquals("Viola", gameControllerThree.getGame().getWinner().getNickname());
        } else if (10 - gameControllerThree.getGame().getRound() == 0) {
            assertEquals(GamePhase.END_GAME, gameControllerThree.getGame().getGamePhase());
            assertEquals("Viola", gameControllerThree.getGame().getWinner().getNickname());
        } else {
            assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerThree.getGame().getGamePhase());
            assertEquals(TurnPhase.PLAY_ASSISTANT_CARD, gameControllerThree.getGame().getTurnPhase());
        }
    }

    @RepeatedTest(100)
    void gameTest2Players() {
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_2);

        assertEquals(1, gameControllerTwo.getGame().getNumberOfPlayer());

        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        gameControllerTwo.setUpTableAndPlayers();
        assertEquals(2, gameControllerTwo.getGame().getNumberOfPlayer());

        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getGamePhase());

        assertEquals(TurnPhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getTurnPhase());
        assertEquals(gameControllerTwo.getGame().getPlayerByIndex(0), gameControllerTwo.getGame().getCurrentPlayer());

        gameControllerTwo.playAssistantCard("Viola", 0);

        assertNull(gameControllerTwo.getGame().getPlayerByIndex(0).getCurrentAssistantCard());

        //First turn

        gameControllerTwo.playAssistantCard("Laura", 4);
        assertEquals(gameControllerTwo.getGame().getAssistantCard(4), gameControllerTwo.getGame().getPlayerByIndex(0).getCurrentAssistantCard());
        assertFalse(gameControllerTwo.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameControllerTwo.getGame().getAssistantCard(4)));
        assertEquals(gameControllerTwo.getGame().getCurrentPlayer(), gameControllerTwo.getGame().getPlayerByIndex(1));

        for (int i = 0; i < gameControllerTwo.getGame().getNumberOfPlayer(); i++) {
            assertEquals(gameControllerTwo.getGame().getNumberStudentsEntrance(), gameControllerTwo.getGame().getPlayerByIndex(i).getSchoolBoard().getNumberStudentsEntrance());
            assertEquals(gameControllerTwo.getGame().getNumberOfTowersPerPlayer(), gameControllerTwo.getGame().getPlayerByIndex(i).getSchoolBoard().getTowers());
        }
        gameControllerTwo.playAssistantCard("Viola", 8);

        assertEquals(gameControllerTwo.getGame().getCurrentPlayer(), gameControllerTwo.getGame().getPlayerByIndex(0));

        int i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 2, 0);
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

        gameControllerTwo.moveMotherNature("Laura", 2);

        assertEquals(2, gameControllerTwo.getGame().getTable().getMotherNaturePosition());
        assertNull(gameControllerTwo.getGame().getTable().getGroupIslandByIndex(2).getInfluence());

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.chooseCloudTile("Laura", 1);
        assertEquals(7, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerTwo.getGame().getTurnPhase());
        assertEquals(1, gameControllerTwo.getGame().getTable().getNumberOfCloudTile());
        assertEquals(gameControllerTwo.getGame().getCurrentPlayer(), gameControllerTwo.getGame().getPlayerByIndex(1));

        int j = 0;
        while (j < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToDiningRoom("Viola", colour);
                    j++;
                }
            }
        }

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameControllerTwo.getGame().getTurnPhase());

        assertEquals(4, gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(7, gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());

        gameControllerTwo.moveMotherNature("Viola", 3);

        assertEquals(5, gameControllerTwo.getGame().getTable().getMotherNaturePosition());

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.chooseCloudTile("Viola", 1);
        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.chooseCloudTile("Viola", 0);
        assertEquals(7, gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(2, gameControllerTwo.getGame().getTable().getNumberOfCloudTile());
        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getTurnPhase());

        assertEquals(gameControllerTwo.getGame().getCurrentPlayer(), gameControllerTwo.getGame().getPlayerByIndex(0));


        //Second turn

        gameControllerTwo.playAssistantCard("Laura", 9);
        gameControllerTwo.playAssistantCard("Viola", 6);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 9, 0);
                    i++;
                }
            }
        }

        gameControllerTwo.moveMotherNature("Viola", 1);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.chooseCloudTile("Viola", 1);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 4, 0);
                    i++;
                }
            }
        }

        gameControllerTwo.moveMotherNature("Laura", 2);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());


        gameControllerTwo.chooseCloudTile("Laura", 0);

        //Third turn

        gameControllerTwo.playAssistantCard("Viola", 3);
        gameControllerTwo.playAssistantCard("Laura", 5);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }

        gameControllerTwo.moveMotherNature("Viola", 2);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.chooseCloudTile("Viola", 1);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 2, 0);
                    i++;
                }
            }
        }

        gameControllerTwo.moveMotherNature("Laura", 1);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());


        gameControllerTwo.chooseCloudTile("Viola", 0);

        //Fourth turn
        gameControllerTwo.playAssistantCard("Viola", 7);
        gameControllerTwo.playAssistantCard("Laura", 1);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 2, 0);
                    i++;
                }
            }
        }

        gameControllerTwo.moveMotherNature("Laura", 1);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.chooseCloudTile("Laura", 1);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 3, 0);
                    i++;
                }
            }
        }

        gameControllerTwo.moveMotherNature("Viola", 1);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());


        gameControllerTwo.chooseCloudTile("Viola", 0);

        //Fifth turn
        gameControllerTwo.playAssistantCard("Laura", 8);
        gameControllerTwo.playAssistantCard("Viola", 2);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 4, 0);
                    i++;
                }
            }
        }

        gameControllerTwo.moveMotherNature("Viola", 2);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.chooseCloudTile("Viola", 1);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 6, 0);
                    i++;
                }
            }
        }

        gameControllerTwo.moveMotherNature("Laura", 1);

        if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getTowers() == 0) {
            assertEquals(GamePhase.END_GAME, gameControllerTwo.getGame().getGamePhase());
            assertEquals("Viola", gameControllerTwo.getGame().getWinner().getNickname());
        } else {
            assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        }


        gameControllerTwo.chooseCloudTile("Laura", 0);

        //Sixth turn
        gameControllerTwo.playAssistantCard("Viola", 5);
        gameControllerTwo.playAssistantCard("Laura", 3);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 4, 0);
                    i++;
                }
            }
        }

        gameControllerTwo.moveMotherNature("Laura", 1);

        if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getTowers() == 0) {
            assertEquals(GamePhase.END_GAME, gameControllerTwo.getGame().getGamePhase());
            assertEquals("Viola", gameControllerTwo.getGame().getWinner().getNickname());
        } else {
            assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        }

        gameControllerTwo.chooseCloudTile("Laura", 1);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 6, 0);
                    i++;
                }
            }
        }

        gameControllerTwo.moveMotherNature("Viola", 1);

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
        gameControllerTwo.setUpTableAndPlayers();

        gameControllerTwo.playAssistantCard("Viola", 0);
        gameControllerTwo.playAssistantCard("Laura", 1);

        Colour colour1 = Colour.RED;
        for (Colour colour : Colour.values()) {
            if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                colour1 = colour;
                break;
            }
        }

        gameControllerTwo.moveStudentToDiningRoom("Viola", colour1);
        assertTrue(gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().hasProfessor(colour1));

        for (Colour colour : Colour.values()) {
            if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                colour1 = colour;
                break;
            }
        }

        gameControllerTwo.moveStudentToDiningRoom("Viola", colour1);
        assertTrue(gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().hasProfessor(colour1));

    }

    @Test
    void unify() {
        GameController gameController;
        gameController = new GameController(true, 2, new Lobby());
        gameController.setUp();
        gameController.addPlayer("Viola", Wizard.TYPE_1);
        gameController.addPlayer("Laura", Wizard.TYPE_3);
        gameController.setUpTableAndPlayers();

        // First Round
        gameController.playAssistantCard("Viola", 0);
        gameController.playAssistantCard("Laura", 1);

        int j = 0;
        while (j <= 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToDiningRoom("Viola", colour);
                    j++;
                }
            }
        }

        gameController.moveMotherNature("Viola", 1);
        gameController.chooseCloudTile("Viola", 0);

        j = 0;
        while (j <= 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland("Laura", colour, 2, 0);
                    j++;
                }
            }
        }

        gameController.moveMotherNature("Laura", 1);
        gameController.chooseCloudTile("Laura", 0);


        // Second Round
        gameController.playAssistantCard("Viola", 1);
        gameController.playAssistantCard("Laura", 2);

        j = 0;
        while (j <= 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToDiningRoom("Viola", colour);
                    j++;
                }
            }
        }

        gameController.moveMotherNature("Viola", 1);
        gameController.chooseCloudTile("Viola", 0);

        j = 0;
        while (j <= 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland("Laura", colour, 2, 0);
                    j++;
                }
            }
        }

        gameController.moveMotherNature("Laura", 2);
        gameController.chooseCloudTile("Laura", 0);

        // Third turn
        gameController.playAssistantCard("Viola", 2);
        gameController.playAssistantCard("Laura", 3);

        j = 0;
        while (j <= 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToDiningRoom("Viola", colour);
                    j++;
                }
            }
        }

        gameController.moveMotherNature("Viola", 1);
        gameController.chooseCloudTile("Viola", 0);

        j = 0;
        while (j <= 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland("Laura", colour, 2, 0);
                    j++;
                }
            }
        }

        gameController.moveMotherNature("Laura", 2);
        gameController.chooseCloudTile("Laura", 0);

    }

    @Test
    void checkCanPlayAssistantCardTwoPlayer() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_3);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_4);
        gameControllerTwo.setUpTableAndPlayers();

        gameControllerTwo.playAssistantCard("Viola", 0);
        gameControllerTwo.playAssistantCard("Laura", 0);
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerTwo.playAssistantCard("Laura", 1);
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerTwo.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerTwo.getGame().setTurnPhase(TurnPhase.PLAY_ASSISTANT_CARD);

        gameControllerTwo.playAssistantCard("Viola", 1);
        gameControllerTwo.playAssistantCard("Laura", 2);
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerTwo.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerTwo.getGame().setTurnPhase(TurnPhase.PLAY_ASSISTANT_CARD);

        gameControllerTwo.playAssistantCard("Viola", 2);
        gameControllerTwo.playAssistantCard("Laura", 0);
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerTwo.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerTwo.getGame().setTurnPhase(TurnPhase.PLAY_ASSISTANT_CARD);

        gameControllerTwo.playAssistantCard("Laura", 4);
        gameControllerTwo.playAssistantCard("Viola", 3);
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerTwo.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerTwo.getGame().setTurnPhase(TurnPhase.PLAY_ASSISTANT_CARD);

        gameControllerTwo.playAssistantCard("Viola", 4);
        gameControllerTwo.playAssistantCard("Laura", 5);
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerTwo.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerTwo.getGame().setTurnPhase(TurnPhase.PLAY_ASSISTANT_CARD);

        gameControllerTwo.playAssistantCard("Viola", 5);
        gameControllerTwo.playAssistantCard("Laura", 3);
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerTwo.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerTwo.getGame().setTurnPhase(TurnPhase.PLAY_ASSISTANT_CARD);

        gameControllerTwo.playAssistantCard("Laura", 7);
        gameControllerTwo.playAssistantCard("Viola", 6);
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerTwo.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerTwo.getGame().setTurnPhase(TurnPhase.PLAY_ASSISTANT_CARD);

        gameControllerTwo.playAssistantCard("Viola", 7);
        gameControllerTwo.playAssistantCard("Laura", 7);
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerTwo.playAssistantCard("Laura", 8);
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerTwo.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerTwo.getGame().setTurnPhase(TurnPhase.PLAY_ASSISTANT_CARD);

        gameControllerTwo.playAssistantCard("Viola", 8);
        gameControllerTwo.playAssistantCard("Laura", 8);
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerTwo.playAssistantCard("Laura", 6);
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerTwo.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerTwo.getGame().setTurnPhase(TurnPhase.PLAY_ASSISTANT_CARD);

        gameControllerTwo.playAssistantCard("Laura", 9);
        gameControllerTwo.playAssistantCard("Viola", 9);
        assertEquals(gameControllerTwo.getGame().getPlayerByIndex(1), gameControllerTwo.getGame().getCurrentPlayer());
        assertEquals(gameControllerTwo.getGame().getGamePhase(), GamePhase.PLAYING);
        for (int i = 0; i < 10; i++) {
            assertFalse(gameControllerTwo.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameControllerTwo.getGame().getAssistantCard(i)));
            assertFalse(gameControllerTwo.getGame().getPlayerByIndex(1).isAssistantCardPresent(gameControllerTwo.getGame().getAssistantCard(i)));
        }
    }

    @Test
    void checkCanPlayAssistantCardThreePlayer() {
        gameControllerThree.addPlayer("Viola", Wizard.TYPE_3);
        gameControllerThree.addPlayer("Laura", Wizard.TYPE_4);
        gameControllerThree.addPlayer("Sara", Wizard.TYPE_2);
        gameControllerThree.setUpTableAndPlayers();

        gameControllerThree.playAssistantCard("Viola", 0);
        gameControllerThree.playAssistantCard("Laura", 1);
        gameControllerThree.playAssistantCard("Sara", 2);
        assertEquals(gameControllerThree.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerThree.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerThree.getGame().setTurnPhase(TurnPhase.PLAY_ASSISTANT_CARD);

        gameControllerThree.playAssistantCard("Viola", 2);
        gameControllerThree.playAssistantCard("Laura", 0);
        gameControllerThree.playAssistantCard("Sara", 1);
        assertEquals(gameControllerThree.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerThree.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerThree.getGame().setTurnPhase(TurnPhase.PLAY_ASSISTANT_CARD);

        gameControllerThree.playAssistantCard("Laura", 2);
        gameControllerThree.playAssistantCard("Sara", 0);
        gameControllerThree.playAssistantCard("Viola", 1);
        assertEquals(gameControllerThree.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerThree.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerThree.getGame().setTurnPhase(TurnPhase.PLAY_ASSISTANT_CARD);

        gameControllerThree.playAssistantCard("Sara", 5);
        gameControllerThree.playAssistantCard("Viola", 3);
        gameControllerThree.playAssistantCard("Laura", 4);
        assertEquals(gameControllerThree.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerThree.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerThree.getGame().setTurnPhase(TurnPhase.PLAY_ASSISTANT_CARD);

        gameControllerThree.playAssistantCard("Viola", 4);
        gameControllerThree.playAssistantCard("Laura", 5);
        gameControllerThree.playAssistantCard("Sara", 3);
        assertEquals(gameControllerThree.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerThree.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerThree.getGame().setTurnPhase(TurnPhase.PLAY_ASSISTANT_CARD);

        gameControllerThree.playAssistantCard("Sara", 4);
        gameControllerThree.playAssistantCard("Viola", 5);
        gameControllerThree.playAssistantCard("Laura", 3);
        assertEquals(gameControllerThree.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerThree.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerThree.getGame().setTurnPhase(TurnPhase.PLAY_ASSISTANT_CARD);

        gameControllerThree.playAssistantCard("Laura", 7);
        gameControllerThree.playAssistantCard("Sara", 8);
        gameControllerThree.playAssistantCard("Viola", 6);
        assertEquals(gameControllerThree.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerThree.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerThree.getGame().setTurnPhase(TurnPhase.PLAY_ASSISTANT_CARD);

        gameControllerThree.playAssistantCard("Viola", 7);
        gameControllerThree.playAssistantCard("Laura", 8);
        gameControllerThree.playAssistantCard("Sara", 6);
        assertEquals(gameControllerThree.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerThree.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerThree.getGame().setTurnPhase(TurnPhase.PLAY_ASSISTANT_CARD);

        gameControllerThree.playAssistantCard("Sara", 7);
        gameControllerThree.playAssistantCard("Viola", 8);
        gameControllerThree.playAssistantCard("Laura", 6);
        assertEquals(gameControllerThree.getGame().getGamePhase(), GamePhase.PLAYING);
        gameControllerThree.getGame().setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerThree.getGame().setTurnPhase(TurnPhase.PLAY_ASSISTANT_CARD);

        gameControllerThree.playAssistantCard("Laura", 9);
        gameControllerThree.playAssistantCard("Sara", 9);
        gameControllerThree.playAssistantCard("Viola", 9);
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(1), gameControllerThree.getGame().getCurrentPlayer());
        assertEquals(gameControllerThree.getGame().getGamePhase(), GamePhase.PLAYING);
        int j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Laura", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Laura", 0);
        gameControllerThree.moveMotherNature("Laura", 1);
        assertEquals(1, gameControllerThree.getGame().getTable().getMotherNaturePosition());
        gameControllerThree.chooseCloudTile("Laura", 0);
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(2), gameControllerThree.getGame().getCurrentPlayer());
        assertEquals(gameControllerThree.getGame().getGamePhase(), GamePhase.PLAYING);
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Sara", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Sara", 0);
        gameControllerThree.moveMotherNature("Sara", 1);
        assertEquals(2, gameControllerThree.getGame().getTable().getMotherNaturePosition());
        gameControllerThree.chooseCloudTile("Sara", 0);
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(0), gameControllerThree.getGame().getCurrentPlayer());
        assertEquals(gameControllerThree.getGame().getGamePhase(), GamePhase.PLAYING);

        for (int i = 0; i < 10; i++) {
            assertFalse(gameControllerThree.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameControllerThree.getGame().getAssistantCard(i)));
            assertFalse(gameControllerThree.getGame().getPlayerByIndex(1).isAssistantCardPresent(gameControllerThree.getGame().getAssistantCard(i)));
            assertFalse(gameControllerThree.getGame().getPlayerByIndex(2).isAssistantCardPresent(gameControllerThree.getGame().getAssistantCard(i)));
        }
    }

    @RepeatedTest(100)
    void checkIsBagEmpty() {
        gameControllerThree.addPlayer("Viola", Wizard.TYPE_3);
        gameControllerThree.addPlayer("Laura", Wizard.TYPE_4);
        gameControllerThree.addPlayer("Sara", Wizard.TYPE_2);
        gameControllerThree.setUpTableAndPlayers();

        int num = 0;
        for (Colour colour : Colour.values()) {
            num += gameControllerThree.getGame().getTable().getBag().getBagStudent(colour);
        }

        assertEquals(81, num);

        // FIRST TURN
        gameControllerThree.playAssistantCard("Viola", 0);
        gameControllerThree.playAssistantCard("Laura", 1);
        gameControllerThree.playAssistantCard("Sara", 2);

        // Player 0
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(0), gameControllerThree.getGame().getCurrentPlayer());
        int j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Viola", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Viola", 1);
        gameControllerThree.chooseCloudTile("Viola", 0);

        // Player 1
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(1), gameControllerThree.getGame().getCurrentPlayer());
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Laura", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Laura", 1);
        gameControllerThree.chooseCloudTile("Laura", 0);

        // Player 2
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(2), gameControllerThree.getGame().getCurrentPlayer());
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Sara", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Sara", 1);
        gameControllerThree.chooseCloudTile("Sara", 0);

        // SECOND TURN
        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerThree.getGame().getGamePhase());
        gameControllerThree.playAssistantCard("Viola", 2);
        gameControllerThree.playAssistantCard("Laura", 0);
        gameControllerThree.playAssistantCard("Sara", 1);

        // Player 1
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(1), gameControllerThree.getGame().getCurrentPlayer());
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Laura", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Laura", 1);
        gameControllerThree.chooseCloudTile("Laura", 0);

        // Player 2
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(2), gameControllerThree.getGame().getCurrentPlayer());
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Sara", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Sara", 1);
        gameControllerThree.chooseCloudTile("Sara", 0);

        // Player 0
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(0), gameControllerThree.getGame().getCurrentPlayer());
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Viola", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Viola", 1);
        gameControllerThree.chooseCloudTile("Viola", 0);

        // THIRD TURN
        gameControllerThree.playAssistantCard("Laura", 2);
        gameControllerThree.playAssistantCard("Sara", 0);
        gameControllerThree.playAssistantCard("Viola", 1);

        // Player 2
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(2), gameControllerThree.getGame().getCurrentPlayer());
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Sara", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Sara", 1);
        gameControllerThree.chooseCloudTile("Sara", 0);

        // Player 0
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(0), gameControllerThree.getGame().getCurrentPlayer());
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Viola", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Viola", 1);
        gameControllerThree.chooseCloudTile("Viola", 0);

        // Player 1
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(1), gameControllerThree.getGame().getCurrentPlayer());
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Laura", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Laura", 1);
        gameControllerThree.chooseCloudTile("Laura", 0);

        // FOURTH TURN
        gameControllerThree.playAssistantCard("Sara", 5);
        gameControllerThree.playAssistantCard("Viola", 3);
        gameControllerThree.playAssistantCard("Laura", 4);
        assertEquals(gameControllerThree.getGame().getGamePhase(), GamePhase.PLAYING);

        // Player 0
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(0), gameControllerThree.getGame().getCurrentPlayer());
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Viola", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Viola", 1);
        gameControllerThree.chooseCloudTile("Viola", 0);

        // Player 1
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(1), gameControllerThree.getGame().getCurrentPlayer());
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Laura", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Laura", 1);
        gameControllerThree.chooseCloudTile("Laura", 0);

        // Player 2
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(2), gameControllerThree.getGame().getCurrentPlayer());
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Sara", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Sara", 1);
        gameControllerThree.chooseCloudTile("Sara", 0);

        // FIFTH TURN
        gameControllerThree.playAssistantCard("Viola", 4);
        gameControllerThree.playAssistantCard("Laura", 5);
        gameControllerThree.playAssistantCard("Sara", 3);
        assertEquals(gameControllerThree.getGame().getGamePhase(), GamePhase.PLAYING);

        // Player 2
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(2), gameControllerThree.getGame().getCurrentPlayer());
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Sara", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Sara", 1);
        gameControllerThree.chooseCloudTile("Sara", 0);

        // Player 0
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(0), gameControllerThree.getGame().getCurrentPlayer());
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Viola", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Viola", 1);
        gameControllerThree.chooseCloudTile("Viola", 0);

        // Player 1
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(1), gameControllerThree.getGame().getCurrentPlayer());
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Laura", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Laura", 1);
        gameControllerThree.chooseCloudTile("Laura", 0);

        // SIXTH TURN
        gameControllerThree.playAssistantCard("Sara", 4);
        gameControllerThree.playAssistantCard("Viola", 5);
        gameControllerThree.playAssistantCard("Laura", 3);
        assertEquals(gameControllerThree.getGame().getGamePhase(), GamePhase.PLAYING);

        // Player 1
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(1), gameControllerThree.getGame().getCurrentPlayer());
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Laura", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Laura", 1);
        gameControllerThree.chooseCloudTile("Laura", 0);

        // Player 2
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(2), gameControllerThree.getGame().getCurrentPlayer());
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Sara", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Sara", 1);
        gameControllerThree.chooseCloudTile("Sara", 0);

        // Player 0
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(0), gameControllerThree.getGame().getCurrentPlayer());
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Viola", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Viola", 1);
        gameControllerThree.chooseCloudTile("Viola", 0);

        // SEVENTH TURN
        assertEquals(gameControllerThree.getGame().getGamePhase(), GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerThree.playAssistantCard("Laura", 7);
        gameControllerThree.playAssistantCard("Sara", 8);
        gameControllerThree.playAssistantCard("Viola", 6);
        assertEquals(gameControllerThree.getGame().getGamePhase(), GamePhase.PLAYING);

        // Player 0
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(0), gameControllerThree.getGame().getCurrentPlayer());
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Viola", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Viola", 1);
        gameControllerThree.chooseCloudTile("Viola", 0);

        // Player 1
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(1), gameControllerThree.getGame().getCurrentPlayer());
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Laura", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Laura", 1);
        gameControllerThree.chooseCloudTile("Laura", 0);

        // Player 2
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(2), gameControllerThree.getGame().getCurrentPlayer());
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Sara", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Sara", 1);
        gameControllerThree.chooseCloudTile("Sara", 0);

        // EIGHTH TURN
        assertEquals(gameControllerThree.getGame().getGamePhase(), GamePhase.PLAY_ASSISTANT_CARD);
        gameControllerThree.playAssistantCard("Viola", 7);
        gameControllerThree.playAssistantCard("Laura", 8);
        gameControllerThree.playAssistantCard("Sara", 6);
        assertEquals(gameControllerThree.getGame().getGamePhase(), GamePhase.PLAYING);
        assertEquals(gameControllerThree.getGame().getTurnPhase(), TurnPhase.MOVE_STUDENT);

        // Player 2
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(2), gameControllerThree.getGame().getCurrentPlayer());
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Sara", colour, 0, 0);
                    j++;
                }
            }
        }
        assertEquals(gameControllerThree.getGame().getGamePhase(), GamePhase.PLAYING);
        assertEquals(gameControllerThree.getGame().getTurnPhase(), TurnPhase.MOVE_MOTHER_NATURE);
        gameControllerThree.moveMotherNature("Sara", 1);
        assertEquals(gameControllerThree.getGame().getGamePhase(), GamePhase.PLAYING);
        assertTrue(gameControllerThree.getGame().getTable().getBag().isBagEmpty());

        // Player 0
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(0), gameControllerThree.getGame().getCurrentPlayer());
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Viola", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Viola", 1);

        // Player 1
        assertEquals(gameControllerThree.getGame().getPlayerByIndex(1), gameControllerThree.getGame().getCurrentPlayer());
        j = 0;
        while (j < 4) {
            for (Colour colour : Colour.values()) {
                if (gameControllerThree.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerThree.moveStudentToIsland("Laura", colour, 0, 0);
                    j++;
                }
            }
        }
        gameControllerThree.moveMotherNature("Laura", 1);

        assertEquals(gameControllerThree.getGame().getGamePhase(), GamePhase.END_GAME);
        assertEquals(gameControllerThree.getGame().getTurnPhase(), TurnPhase.ENDGAME);
        assertTrue(gameControllerThree.getGame().getTable().getBag().getNoStudent());

    }

    @RepeatedTest(20)
    void characterCardSetting() {
        GameController gameControllerExpert = new GameController(true, 2, new Lobby());
        gameControllerExpert.setUp();
        gameControllerExpert.addPlayer("Viola", Wizard.TYPE_1);
        assertEquals(TowerColour.WHITE, gameControllerExpert.getGame().getPlayerByNickname("Viola").getTowerColour());
        gameControllerExpert.addPlayer("Laura", Wizard.TYPE_2);
        assertEquals(TowerColour.BLACK, gameControllerExpert.getGame().getPlayerByNickname("Laura").getTowerColour());
        gameControllerExpert.addPlayer("Laura", Wizard.TYPE_3);
        assertEquals(TowerColour.BLACK, gameControllerExpert.getGame().getPlayerByNickname("Laura").getTowerColour());
        assertEquals(Wizard.TYPE_2, gameControllerExpert.getGame().getPlayerByNickname("Laura").getWizard());
        gameControllerExpert.setUpTableAndPlayers();


        gameControllerExpert.playAssistantCard("Viola", 0);
        gameControllerExpert.playAssistantCard("Laura", 1);
        assertEquals(GamePhase.PLAYING, gameControllerExpert.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerExpert.getGame().getTurnPhase());


        if (gameControllerExpert.getGame().getPlayerByNickname("Viola").getCoins() >= gameControllerExpert.getGame().getCharacterCardByIndex(0).getCost()) {
            gameControllerExpert.playCharacterCard("Viola", 0);
            assertEquals(gameControllerExpert.getGame().getActiveCharacterCard(), gameControllerExpert.getGame().getCharacterCardByIndex(0));
        }

    }

    @RepeatedTest(100)
    void exceptionSetting() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_2);
        assertEquals(1, gameControllerTwo.getGame().getNumberOfPlayer());
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_1);
        assertEquals(1, gameControllerTwo.getGame().getNumberOfPlayer());
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        assertEquals(1, gameControllerTwo.getGame().getNumberOfPlayer());
        gameControllerTwo.addPlayer("Sara", Wizard.TYPE_3);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_1);
        gameControllerTwo.setUpTableAndPlayers();

        // exception with the Character Card
        gameControllerTwo.moveStudentToDiningRoom("Sara", Colour.GREEN);
        gameControllerTwo.chooseCloudTile("Viola", 4);
        gameControllerTwo.playCharacterCard("Viola", 0);
        assertEquals(gameControllerTwo.getGame().getActiveCharacterCard(), gameControllerTwo.getGame().getBasicState());
        gameControllerTwo.playCharacterCard("Laura", 1);
        assertEquals(gameControllerTwo.getGame().getActiveCharacterCard(), gameControllerTwo.getGame().getBasicState());
        gameControllerTwo.playCharacterCard("Sara", 1);
        assertEquals(gameControllerTwo.getGame().getActiveCharacterCard(), gameControllerTwo.getGame().getBasicState());

        // exception with the AssistantCard
        gameControllerTwo.moveStudentToDiningRoom("Sara", Colour.GREEN);
        gameControllerTwo.chooseCloudTile("Viola", 4);
        gameControllerTwo.playAssistantCard("Viola", 11);
        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getGamePhase());
        gameControllerTwo.playAssistantCard("Viola", -1);
        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getGamePhase());
        gameControllerTwo.playAssistantCard("Sara", 0);
        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getGamePhase());
        gameControllerTwo.playAssistantCard("Laura", 1);
        gameControllerTwo.moveStudentToIsland("Laura", Colour.GREEN, 0, 0);
        gameControllerTwo.moveStudentToDiningRoom("Laura", Colour.GREEN);
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        //playing the AssistantCard
        gameControllerTwo.playAssistantCard("Viola", 0);
        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getGamePhase());
        assertEquals(gameControllerTwo.getGame().getAssistantCard(0), gameControllerTwo.getGame().getPlayerByNickname("Viola").getCurrentAssistantCard());
        gameControllerTwo.playAssistantCard("Viola", 1);
        assertEquals(gameControllerTwo.getGame().getAssistantCard(0), gameControllerTwo.getGame().getPlayerByNickname("Viola").getCurrentAssistantCard());
        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getGamePhase());
        gameControllerTwo.playAssistantCard("Sara", 1);
        assertEquals(gameControllerTwo.getGame().getAssistantCard(0), gameControllerTwo.getGame().getPlayerByNickname("Viola").getCurrentAssistantCard());
        assertEquals(gameControllerTwo.getGame().getAssistantCard(1), gameControllerTwo.getGame().getPlayerByNickname("Sara").getCurrentAssistantCard());
        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        gameControllerTwo.playAssistantCard("Viola", 0);
        assertEquals(gameControllerTwo.getGame().getAssistantCard(0), gameControllerTwo.getGame().getPlayerByNickname("Viola").getCurrentAssistantCard());
        assertEquals(gameControllerTwo.getGame().getAssistantCard(1), gameControllerTwo.getGame().getPlayerByNickname("Sara").getCurrentAssistantCard());
        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerTwo.getGame().getTurnPhase());

        // exception with movement of students
        gameControllerTwo.moveStudentToDiningRoom("Sara", Colour.GREEN);
        gameControllerTwo.moveStudentToDiningRoom("Sara", Colour.PINK);
        gameControllerTwo.moveStudentToDiningRoom("Sara", Colour.BLUE);
        gameControllerTwo.moveStudentToDiningRoom("Sara", Colour.YELLOW);
        gameControllerTwo.moveStudentToDiningRoom("Sara", Colour.RED);

        gameControllerTwo.playAssistantCard("Laura", 1);
        gameControllerTwo.moveStudentToIsland("Laura", Colour.GREEN, 0, 0);
        gameControllerTwo.moveStudentToDiningRoom("Laura", Colour.GREEN);
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.chooseCloudTile("Viola", 4);

        gameControllerTwo.moveStudentToDiningRoom("Viola", Colour.GREEN);
        gameControllerTwo.moveStudentToDiningRoom("Viola", Colour.GREEN);
        gameControllerTwo.moveStudentToDiningRoom("Viola", Colour.GREEN);
        gameControllerTwo.moveStudentToDiningRoom("Viola", Colour.GREEN);
        gameControllerTwo.moveStudentToDiningRoom("Viola", Colour.PINK);
        gameControllerTwo.moveStudentToDiningRoom("Viola", Colour.PINK);
        gameControllerTwo.moveStudentToDiningRoom("Viola", Colour.PINK);
        gameControllerTwo.moveStudentToDiningRoom("Viola", Colour.PINK);
        gameControllerTwo.moveStudentToDiningRoom("Viola", Colour.BLUE);
        gameControllerTwo.moveStudentToDiningRoom("Viola", Colour.BLUE);
        gameControllerTwo.moveStudentToDiningRoom("Viola", Colour.BLUE);
        gameControllerTwo.moveStudentToDiningRoom("Viola", Colour.RED);
        gameControllerTwo.moveStudentToDiningRoom("Viola", Colour.RED);
        gameControllerTwo.moveStudentToDiningRoom("Viola", Colour.RED);
        gameControllerTwo.moveStudentToDiningRoom("Viola", Colour.RED);
        gameControllerTwo.moveStudentToDiningRoom("Viola", Colour.YELLOW);
        gameControllerTwo.moveStudentToDiningRoom("Viola", Colour.YELLOW);
        gameControllerTwo.moveStudentToDiningRoom("Viola", Colour.YELLOW);
        gameControllerTwo.moveStudentToDiningRoom("Viola", Colour.YELLOW);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameControllerTwo.getGame().getTurnPhase());


        gameControllerTwo.chooseCloudTile("Viola", 4);
        gameControllerTwo.moveStudentToDiningRoom("Viola", Colour.YELLOW);
        gameControllerTwo.playAssistantCard("Viola", 0);
        gameControllerTwo.chooseCloudTile("Sara", 4);
        gameControllerTwo.moveStudentToDiningRoom("Sara", Colour.YELLOW);
        gameControllerTwo.playAssistantCard("Sara", 0);


        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.moveMotherNature("Sara", 1);
        gameControllerTwo.moveMotherNature("Viola", -1);
        gameControllerTwo.moveMotherNature("Viola", 29200);
        gameControllerTwo.moveMotherNature("Viola", 1);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());

        // exception with the cloud tile
        gameControllerTwo.chooseCloudTile("Sara", -1);
        gameControllerTwo.chooseCloudTile("Sara", 4);
        gameControllerTwo.chooseCloudTile("Viola", -1);
        gameControllerTwo.chooseCloudTile("Viola", 4);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.chooseCloudTile("Viola", 0);

        // Sara's turn

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.chooseCloudTile("Viola", 4);
        gameControllerTwo.moveStudentToDiningRoom("Viola", Colour.YELLOW);
        gameControllerTwo.playAssistantCard("Viola", 0);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.moveStudentToDiningRoom("Sara", Colour.GREEN);
        gameControllerTwo.moveStudentToDiningRoom("Sara", Colour.PINK);
        gameControllerTwo.moveStudentToDiningRoom("Sara", Colour.BLUE);
        gameControllerTwo.moveStudentToDiningRoom("Sara", Colour.YELLOW);
        gameControllerTwo.moveStudentToDiningRoom("Sara", Colour.RED);
        gameControllerTwo.moveStudentToDiningRoom("Sara", Colour.GREEN);
        gameControllerTwo.moveStudentToDiningRoom("Sara", Colour.PINK);
        gameControllerTwo.moveStudentToDiningRoom("Sara", Colour.BLUE);
        gameControllerTwo.moveStudentToDiningRoom("Sara", Colour.YELLOW);
        gameControllerTwo.moveStudentToDiningRoom("Sara", Colour.RED);
        gameControllerTwo.moveStudentToDiningRoom("Sara", Colour.GREEN);
        gameControllerTwo.moveStudentToDiningRoom("Sara", Colour.PINK);
        gameControllerTwo.moveStudentToDiningRoom("Sara", Colour.BLUE);
        gameControllerTwo.moveStudentToDiningRoom("Sara", Colour.YELLOW);
        gameControllerTwo.moveStudentToDiningRoom("Sara", Colour.RED);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.chooseCloudTile("Viola", 0);
        gameControllerTwo.chooseCloudTile("Sara", 0);
        gameControllerTwo.moveMotherNature("Viola", -1);
        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.moveMotherNature("Sara", 1);
        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.chooseCloudTile("Viola", 0);
        gameControllerTwo.chooseCloudTile("Sara", 4);
        gameControllerTwo.moveMotherNature("Viola", -1);
        gameControllerTwo.chooseCloudTile("Viola", 30);
        gameControllerTwo.chooseCloudTile("Sara", 30);
        gameControllerTwo.moveMotherNature("Viola", -1);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.chooseCloudTile("Sara", 0);

        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getTurnPhase());

        //exception with the Assistant Card
        gameControllerTwo.playAssistantCard("Viola", 0);
        gameControllerTwo.playAssistantCard("Sara", 0);
        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getTurnPhase());
        gameControllerTwo.playAssistantCard("Viola", 1);
        gameControllerTwo.playAssistantCard("Viola", 0);
        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getTurnPhase());
        gameControllerTwo.playAssistantCard("Sara", 2);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.chooseCloudTile("Viola", 0);
        gameControllerTwo.chooseCloudTile("Sara", 4);
        gameControllerTwo.moveMotherNature("Viola", -1);
        gameControllerTwo.chooseCloudTile("Viola", 30);
        gameControllerTwo.chooseCloudTile("Sara", 30);
        gameControllerTwo.moveMotherNature("Viola", -1);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.moveStudentToIsland("Sara", Colour.GREEN, 0, 0);
        gameControllerTwo.moveStudentToIsland("Sara", Colour.RED, 0, 0);
        gameControllerTwo.moveStudentToIsland("Sara", Colour.BLUE, 0, 0);
        gameControllerTwo.moveStudentToIsland("Sara", Colour.YELLOW, 0, 0);
        gameControllerTwo.moveStudentToIsland("Sara", Colour.PINK, 0, 0);
        gameControllerTwo.moveStudentToIsland("Sara", Colour.GREEN, -1, 0);
        gameControllerTwo.moveStudentToIsland("Sara", Colour.RED, 0, -30);
        gameControllerTwo.moveStudentToIsland("Sara", Colour.BLUE, 13, 0);
        gameControllerTwo.moveStudentToIsland("Sara", Colour.YELLOW, 0, 14);
        gameControllerTwo.moveStudentToIsland("Sara", Colour.PINK, -1, -30);
        gameControllerTwo.moveStudentToIsland("Sara", Colour.GREEN, 0, 0);
        gameControllerTwo.moveStudentToIsland("Sara", Colour.RED, 0, 0);
        gameControllerTwo.moveStudentToIsland("Sara", Colour.BLUE, 0, 0);
        gameControllerTwo.moveStudentToIsland("Sara", Colour.YELLOW, 0, 0);
        gameControllerTwo.moveStudentToIsland("Sara", Colour.PINK, 0, 0);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerTwo.getGame().getTurnPhase());


        gameControllerTwo.moveStudentToIsland("Viola", Colour.GREEN, -1, 0);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.RED, 0, -30);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.BLUE, 13, 0);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.YELLOW, 0, 14);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.PINK, -1, -30);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.moveStudentToIsland("Viola", Colour.GREEN, 0, 0);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.RED, 0, 0);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.BLUE, 0, 0);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.YELLOW, 0, 0);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.PINK, 0, 0);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.GREEN, 0, 0);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.RED, 0, 0);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.BLUE, 0, 0);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.YELLOW, 0, 0);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.PINK, 0, 0);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.GREEN, 0, 0);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.RED, 0, 0);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.BLUE, 0, 0);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.YELLOW, 0, 0);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.PINK, 0, 0);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameControllerTwo.getGame().getTurnPhase());


        gameControllerTwo.chooseCloudTile("Viola", 0);
        gameControllerTwo.chooseCloudTile("Sara", 4);
        gameControllerTwo.moveMotherNature("Viola", -1);
        gameControllerTwo.chooseCloudTile("Viola", 30);
        gameControllerTwo.chooseCloudTile("Sara", 30);
        gameControllerTwo.moveMotherNature("Viola", -1);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.moveMotherNature("Viola", 1);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.moveStudentToIsland("Viola", Colour.PINK, 0, 0);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.GREEN, -1, 0);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.RED, 0, -30);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.BLUE, 13, 0);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.YELLOW, 0, 14);
        gameControllerTwo.moveStudentToIsland("Viola", Colour.PINK, -1, -30);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.chooseCloudTile("Viola", 1);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerTwo.getGame().getTurnPhase());
    }


    @RepeatedTest(1000)
    void testCanPlayAssistantCard() {
        // adding player to the game
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_2);
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_3);
        gameControllerTwo.setUpTableAndPlayers();
        // First turn
        gameControllerTwo.playAssistantCard("Laura", 0);
        gameControllerTwo.playAssistantCard("Viola", 1);

        int i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        // Second turn
        gameControllerTwo.playAssistantCard("Laura", 1);
        gameControllerTwo.playAssistantCard("Viola", 2);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        //Third turn
        gameControllerTwo.playAssistantCard("Laura", 2);
        gameControllerTwo.playAssistantCard("Viola", 3);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        //Fourth turn
        gameControllerTwo.playAssistantCard("Laura", 3);
        gameControllerTwo.playAssistantCard("Viola", 4);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        //Fifth turn
        gameControllerTwo.playAssistantCard("Laura", 4);
        gameControllerTwo.playAssistantCard("Viola", 5);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        //Sixth turn
        gameControllerTwo.playAssistantCard("Laura", 5);
        gameControllerTwo.playAssistantCard("Viola", 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        //Seventh turn
        gameControllerTwo.playAssistantCard("Viola", 7);
        gameControllerTwo.playAssistantCard("Laura", 7);

        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.playAssistantCard("Laura", 8);
        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerTwo.getGame().getTurnPhase());

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);


        // Eighth turn
        gameControllerTwo.playAssistantCard("Viola", 8);
        gameControllerTwo.playAssistantCard("Laura", 7);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        // Ninth turn
        gameControllerTwo.playAssistantCard("Laura", 6);
        gameControllerTwo.playAssistantCard("Viola", 6);

        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getTurnPhase());

        gameControllerTwo.playAssistantCard("Viola", 9);
        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerTwo.getGame().getTurnPhase());

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        // Tenth turn
        gameControllerTwo.playAssistantCard("Laura", 9);
        gameControllerTwo.playAssistantCard("Viola", 6);

        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerTwo.getGame().getTurnPhase());

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        assertEquals(GamePhase.END_GAME, gameControllerTwo.getGame().getGamePhase());
        assertEquals(11, gameControllerTwo.getGame().getRound());
        for (int j = 0; j < 10; j++) {
            assertFalse(gameControllerTwo.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameControllerTwo.getGame().getAssistantCard(j)));
            assertFalse(gameControllerTwo.getGame().getPlayerByIndex(1).isAssistantCardPresent(gameControllerTwo.getGame().getAssistantCard(j)));
        }
        assertEquals(12, gameControllerTwo.getGame().getTable().getNumberOfGroupIsland());
        assertEquals("Laura", gameControllerTwo.getGame().getWinner().getNickname());
    }

    @Test
    void assistantCard() {
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_1);
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_4);
        gameControllerTwo.setUpTableAndPlayers();

        // First turn
        gameControllerTwo.playAssistantCard("Laura", 0);
        gameControllerTwo.playAssistantCard("Viola", 0);
        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getTurnPhase());
        gameControllerTwo.playAssistantCard("Viola", 1);
        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerTwo.getGame().getTurnPhase());

        // Player 0
        int i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        // Player 1
        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        // Second turn
        gameControllerTwo.playAssistantCard("Laura", 1);
        gameControllerTwo.playAssistantCard("Viola", 0);

        // Player 1
        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        // Player 0
        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        // Third turn
        gameControllerTwo.playAssistantCard("Viola", 2);
        gameControllerTwo.playAssistantCard("Laura", 2);
        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getTurnPhase());
        gameControllerTwo.playAssistantCard("Laura", 3);
        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerTwo.getGame().getTurnPhase());

        // Player 1
        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        // Player 0
        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        // Fourth Turn
        gameControllerTwo.playAssistantCard("Viola", 3);
        gameControllerTwo.playAssistantCard("Laura", 2);

        // Player 0
        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        // Player 1
        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        // Fifth turn
        gameControllerTwo.playAssistantCard("Laura", 4);
        gameControllerTwo.playAssistantCard("Laura", 4);
        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getTurnPhase());
        gameControllerTwo.playAssistantCard("Viola", 5);
        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerTwo.getGame().getTurnPhase());

        // Player 0
        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        // Player 1
        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        // Sixth turn
        gameControllerTwo.playAssistantCard("Laura", 5);
        gameControllerTwo.playAssistantCard("Viola", 4);

        // Player 1
        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        // Player 0
        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        // Seventh turn
        gameControllerTwo.playAssistantCard("Viola", 6);
        gameControllerTwo.playAssistantCard("Laura", 6);
        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getTurnPhase());
        gameControllerTwo.playAssistantCard("Laura", 7);
        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerTwo.getGame().getTurnPhase());

        // Player 1
        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        // Player 0
        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        // Eighth turn
        gameControllerTwo.playAssistantCard("Viola", 7);
        gameControllerTwo.playAssistantCard("Laura", 6);

        // Player 0
        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Laura", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Laura", 1);
        gameControllerTwo.chooseCloudTile("Laura", 0);

        // Player 1
        i = 0;
        while (i < 3) {
            for (Colour colour : Colour.values()) {
                if (gameControllerTwo.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameControllerTwo.moveStudentToIsland("Viola", colour, 0, 0);
                    i++;
                }
            }
        }
        gameControllerTwo.moveMotherNature("Viola", 1);
        gameControllerTwo.chooseCloudTile("Viola", 0);

        // Ninth turn
        gameControllerTwo.playAssistantCard("Laura", 8);
        gameControllerTwo.playAssistantCard("Viola", 8);
        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.PLAY_ASSISTANT_CARD, gameControllerTwo.getGame().getTurnPhase());
        gameControllerTwo.playAssistantCard("Viola", 9);
        assertEquals(GamePhase.PLAYING, gameControllerTwo.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameControllerTwo.getGame().getTurnPhase());
    }


    @Test
    public void endStudent() {
        gameControllerTwo.addPlayer("Viola", Wizard.TYPE_2);
        gameControllerTwo.addPlayer("Laura", Wizard.TYPE_1);
        assertEquals(2, Wizard.getWizardCode(gameControllerTwo.getGame().getPlayerByIndex(0).getWizard()));
        assertEquals(1, Wizard.getWizardCode(gameControllerTwo.getGame().getPlayerByIndex(1).getWizard()));
        gameControllerTwo.setUpTableAndPlayers();
        for (int i = 0; i < 100; i++) {
            gameControllerTwo.getGame().getTable().getBag().bagDrawStudent();
        }

        assertThrows(IllegalAccessError.class, () -> gameControllerTwo.getGame().getTable().getBag().bagDrawStudent());
        assertTrue(gameControllerTwo.getGame().getTable().getBag().getNoStudent());
    }

}