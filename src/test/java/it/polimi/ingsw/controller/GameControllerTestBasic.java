package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GameControllerTestBasic {
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController(false, 2);
    }

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
        for (int j = 0; j < 10; j++) {
            assertFalse(gameController.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameController.getGame().getAssistantCard(j)));
            assertFalse(gameController.getGame().getPlayerByIndex(1).isAssistantCardPresent(gameController.getGame().getAssistantCard(j)));
        }
        assertEquals(12, gameController.getGame().getTable().getNumberOfGroupIsland());
        assertEquals("Laura", gameController.getGame().getWinner().getNickname());
    }
}
