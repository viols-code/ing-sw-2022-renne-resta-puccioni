package it.polimi.ingsw.model.game;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.game.BasicGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.model.player.BasicPlayer;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameTest {
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController(false, 2);
    }

    @Test
    void addPlayer() {
        Player player1 = new BasicPlayer("Laura", Wizard.TYPE_1);
        gameController.getGame().addPlayer(player1);
        assertEquals(1, gameController.getGame().getNumberOfPlayer());
        assertEquals("Laura", gameController.getGame().getPlayerByIndex(0).getNickname());
        assertEquals(Wizard.TYPE_1, gameController.getGame().getPlayerByIndex(0).getWizard());

        Player player2 = new BasicPlayer("Sara", Wizard.TYPE_3);
        gameController.getGame().addPlayer(player2);
        assertEquals(2, gameController.getGame().getNumberOfPlayer());
        assertEquals("Sara", gameController.getGame().getPlayerByIndex(1).getNickname());
        assertEquals(Wizard.TYPE_3, gameController.getGame().getPlayerByIndex(1).getWizard());
    }

    @Test
    void removePlayer() {

        Player player1 = new BasicPlayer("Laura", Wizard.TYPE_1);
        gameController.getGame().addPlayer(player1);
        Player player2 = new BasicPlayer("Sara", Wizard.TYPE_3);
        gameController.getGame().addPlayer(player2);
        gameController.getGame().removePlayer(player1);

        assertEquals(1, gameController.getGame().getNumberOfPlayer());
        assertEquals(player2, gameController.getGame().getPlayerByIndex(0));

    }

    @Test
    void nextPlayerClockwise() {
        player2 = new BasicPlayer("Sara", Wizard.TYPE_2);
        player3 = new BasicPlayer("Laura", Wizard.TYPE_3);
        gameTest.addPlayer(player2);
        gameTest.addPlayer(player3);
        gameTest.setCurrentPlayer(player3);
        // assertEquals(player1, gameTest.nextPlayerClockwise());

    }

    @Test
    void nextPlayerTurn() {

        AssistantCard card1 = new AssistantCard(3, 4);
        AssistantCard card2 = new AssistantCard(4, 4);
        AssistantCard card3 = new AssistantCard(6, 4);

        player1.setCurrentAssistantCard(card1);
        gameTest.setCurrentPlayer(player1);

        player2 = new BasicPlayer("Sara", Wizard.TYPE_2);
        player3 = new BasicPlayer("Laura", Wizard.TYPE_3);
        gameTest.addPlayer(player2);
        gameTest.addPlayer(player3);

        player2.setCurrentAssistantCard(card3);
        player3.setCurrentAssistantCard(card2);

//        assertEquals(player3, gameTest.nextPlayerTurn());

    }

    @Test
    void setCurrentPlayer() {
        gameTest.setCurrentPlayer(player1);
        assertTrue(gameTest.isCurrentPlayer(player1));
    }

    @Test
    void setFirstPlayerTurn() {
        gameTest.setFirstPlayerTurn(player1);
        assertEquals(player1, gameTest.getFirstPlayerTurn());
    }

    @Test
    void removeGroupIsland() {
        // GroupIsland islandRemoved = gameTest.getTable().getGroupIslandByIndex(2);
        //gameTest.getTable().removeGroupIsland(gameTest.getTable().getGroupIslandByIndex(2));
        // for (int i = 0; i < gameTest.getTable().getNumberOfGroupIsland(); i++) {
        //    assertNotEquals(gameTest.getTable().getGroupIslandByIndex(i), islandRemoved);
        //  }
    }

    @Test
    void incrementRound() {
        gameTest.incrementRound();
        assertEquals(1, gameTest.getRound());
    }


    @Test
    void setStudentNumberMovement() {
        gameTest.setStudentNumberMovement(4);
        assertEquals(4, gameTest.getStudentNumberMovement());
        gameTest.setStudentNumberMovement(3);
        assertEquals(3, gameTest.getStudentNumberMovement());

    }

    @Test
    void setNumberOfTowersPerPlayer() {
        gameTest.setNumberOfTowersPerPlayer(6);
        assertEquals(6, gameTest.getNumberOfTowersPerPlayer());
        gameTest.setNumberOfTowersPerPlayer(8);
        assertEquals(8, gameTest.getNumberOfTowersPerPlayer());
    }

    @Test
    void setNumberStudentsEntrance() {
        gameTest.setNumberStudentsEntrance(7);
        assertEquals(7, gameTest.getNumberStudentsEntrance());
        gameTest.setNumberStudentsEntrance(9);
        assertEquals(9, gameTest.getNumberStudentsEntrance());
    }

    @Test
    void setGamePhase() {
        gameTest.setGamePhase(GamePhase.SETTING);
        assertEquals(GamePhase.SETTING, gameTest.getGamePhase());
        gameTest.setGamePhase(GamePhase.SET_CLOUD_TILE);
        assertEquals(GamePhase.SET_CLOUD_TILE, gameTest.getGamePhase());
    }

    @Test
    void isCurrentPlayer() {
        gameTest.setCurrentPlayer(player1);
        assertTrue(gameTest.isCurrentPlayer(player1));
    }

}