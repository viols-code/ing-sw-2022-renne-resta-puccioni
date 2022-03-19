package it.polimi.ingsw.model;

import it.polimi.ingsw.model.game.BasicGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.model.player.BasicPlayer;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game gameTest;
    Player player1;
    Player player2;
    Player player3;

    @BeforeEach
    void setUp() {
        gameTest = new BasicGame();
        player1 = new BasicPlayer("Viola", Wizard.TYPE_1);
        gameTest.addPlayer(player1);
    }

    @Test
    void addPlayer() {
        assertEquals(1, gameTest.getNumberOfPlayer());
        assertEquals("Viola", gameTest.getPlayerByIndex(0).getNickname());
        assertEquals(Wizard.TYPE_1, gameTest.getPlayerByIndex(0).getWizard());
    }

    @Test
    void removePlayer() {
        gameTest.removePlayer(player1);
        assertEquals(0, gameTest.getNumberOfPlayer());
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