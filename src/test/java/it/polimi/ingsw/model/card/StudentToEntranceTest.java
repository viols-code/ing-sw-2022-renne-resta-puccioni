package it.polimi.ingsw.model.card;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.ExpertPlayer;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.table.island.BasicGroupIsland;
import it.polimi.ingsw.server.Lobby;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StudentToEntranceTest {

    private StudentToEntrance cardTest;
    private Game gameTest;

    @BeforeEach
    void setUp() {
        Player player2;
        gameTest = new ExpertGame();
        cardTest = new StudentToEntrance(gameTest);

        gameTest.addCharacterCard(cardTest);
        for (int i = 0; i < 12; i++) {
            gameTest.getTable().addGroupIsland(new BasicGroupIsland());
        }

        settingBag();
        settingCard();

        Player player1 = new ExpertPlayer("Viola", Wizard.TYPE_3, TowerColour.WHITE);
        player2 = new ExpertPlayer("Laura", Wizard.TYPE_1, TowerColour.BLACK);

        gameTest.addPlayer(player1);
        gameTest.addPlayer(player2);

        player1.getSchoolBoard().addTower(8);
        player2.getSchoolBoard().addTower(8);

    }

    @Test
    void setting() {
        assertEquals(1, cardTest.getCost());

        int numStudentsOnCard = 0;

        for (Colour colour : Colour.values()) {
            numStudentsOnCard += cardTest.getStudent(colour);
        }

        assertEquals(6, numStudentsOnCard);
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
    void setColourDiningRoomEntrance() {
        for (Colour colour : Colour.values()) {
            assertThrows(IllegalAccessError.class, () -> cardTest.setColourDiningRoomEntrance(colour, colour));
        }
    }

    @RepeatedTest(3)
    void exceptionNoColourOnCard() {
        for (Colour colour : Colour.values()) {
            if (cardTest.getStudent(colour) == 0) {
                assertThrows(IllegalArgumentException.class, () -> cardTest.setColourCardEntrance(colour, colour));
            }
        }
    }

    @RepeatedTest(1000)
    void setColourCardEntrance() {
        GameController gameController = new GameController(true, 2, new Lobby());
        gameController.setUp();
        gameController.addPlayer("Viola", Wizard.TYPE_2);
        gameController.addPlayer("Laura", Wizard.TYPE_1);
        gameController.setUpTableAndPlayers();

        boolean flag = false;
        int i;
        for (i = 0; i < 3; i++) {
            if (gameController.getGame().getCharacterCardByIndex(i).getCharacterCardType() == CharacterCardEnumeration.STUDENT_TO_ENTRANCE) {
                flag = true;
                break;
            }
        }

        if (flag) {
            gameController.playAssistantCard("Viola", 0);
            gameController.playAssistantCard("Laura", 1);
            gameController.playCharacterCard("Viola", i);
            assertEquals(0, gameController.getGame().getCurrentPlayer().getCoins());

            Colour colourCard;
            colourCard = Colour.RED;
            Colour colourEntrance;
            colourEntrance = Colour.BLUE;
            boolean flag1 = false;
            boolean flag2 = false;

            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getCharacterCardByIndex(i).getStudent(colour) > 0) {
                    colourCard = colour;
                    flag1 = true;
                    break;
                }
            }

            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    colourEntrance = colour;
                    flag2 = true;
                    break;
                }
            }

            if (flag1 && flag2) {
                int studentsInEntrance;
                int studentsOnCard;
                studentsOnCard = gameController.getGame().getCharacterCardByIndex(i).getStudent(colourEntrance);
                studentsInEntrance = gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colourCard);


                gameController.setColourCardEntrance("Viola", colourCard, colourEntrance);
                if (colourCard != colourEntrance) {
                    assertEquals(studentsInEntrance + 1, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colourCard));
                    assertEquals(studentsOnCard + 1, gameController.getGame().getCharacterCardByIndex(i).getStudent(colourEntrance));
                } else {
                    assertEquals(studentsInEntrance, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colourCard));
                    assertEquals(studentsOnCard, gameController.getGame().getCharacterCardByIndex(i).getStudent(colourEntrance));
                }
            }

            colourCard = Colour.RED;
            colourEntrance = Colour.BLUE;
            flag1 = false;
            flag2 = false;

            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getCharacterCardByIndex(i).getStudent(colour) > 0) {
                    colourCard = colour;
                    flag1 = true;
                    break;
                }
            }

            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    colourEntrance = colour;
                    flag2 = true;
                    break;
                }
            }

            if (flag1 && flag2) {
                int studentsInEntrance;
                int studentsOnCard;
                studentsOnCard = gameController.getGame().getCharacterCardByIndex(i).getStudent(colourEntrance);
                studentsInEntrance = gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colourCard);


                gameController.setColourCardEntrance("Viola", colourCard, colourEntrance);
                if (colourCard != colourEntrance) {
                    assertEquals(studentsInEntrance + 1, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colourCard));
                    assertEquals(studentsOnCard + 1, gameController.getGame().getCharacterCardByIndex(i).getStudent(colourEntrance));
                } else {
                    assertEquals(studentsInEntrance, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colourCard));
                    assertEquals(studentsOnCard, gameController.getGame().getCharacterCardByIndex(i).getStudent(colourEntrance));
                }
            }

            colourCard = Colour.RED;
            colourEntrance = Colour.BLUE;
            flag1 = false;
            flag2 = false;

            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getCharacterCardByIndex(i).getStudent(colour) > 0) {
                    colourCard = colour;
                    flag1 = true;
                    break;
                }
            }

            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0) {
                    colourEntrance = colour;
                    flag2 = true;
                    break;
                }
            }

            if (flag1 && flag2) {
                int studentsInEntrance;
                int studentsOnCard;
                studentsOnCard = gameController.getGame().getCharacterCardByIndex(i).getStudent(colourEntrance);
                studentsInEntrance = gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colourCard);


                gameController.setColourCardEntrance("Viola", colourCard, colourEntrance);
                if (colourCard != colourEntrance) {
                    assertEquals(studentsInEntrance + 1, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colourCard));
                    assertEquals(studentsOnCard + 1, gameController.getGame().getCharacterCardByIndex(i).getStudent(colourEntrance));
                } else {
                    assertEquals(studentsInEntrance, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colourCard));
                    assertEquals(studentsOnCard, gameController.getGame().getCharacterCardByIndex(i).getStudent(colourEntrance));
                }
            }

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
            gameTest.getTable().getGroupIslandByIndex(i).getIslandByIndex(0).addStudent(i, 0, gameTest.getTable().getBag().bagDrawStudent());
        }

        for (Colour colour : Colour.values()) {
            for (int i = 0; i < 24; i++) {
                gameTest.getTable().getBag().addStudentBag(colour);
            }
        }
    }

    private void settingCard() {
        for (int i = 0; i < gameTest.getNumberOfCharacterCard(); i++) {
            gameTest.getCharacterCardByIndex(i).setting();
        }
    }


}
