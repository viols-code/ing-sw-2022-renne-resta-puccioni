package it.polimi.ingsw;

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
        gameTest = new Game();
        player1 = new Player("player1", gameTest);
        gameTest.addPlayer(player1);

    }

    @Test
    void addPlayer() {
        assertEquals(1, gameTest.getNumberOfPlayer());
    }

    @Test
    void removePlayer() {
        gameTest.removePlayer(player1);
        assertEquals(0, gameTest.getNumberOfPlayer());
    }

    @Test
    void nextPlayerClockwise() {
        player2 = new Player("player2", gameTest);
        player3 = new Player("player3", gameTest);
        gameTest.addPlayer(player2);
        gameTest.addPlayer(player3);
        gameTest.setCurrentPlayer(player3);
        assertEquals(player1, gameTest.nextPlayerClockwise());

    }

    @Test
    void nextPlayerTurn() {

        /*
        TO DO
         */
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
    void addStudentBag() {
        assertEquals(24, gameTest.getBag(Colour.PINK));
        gameTest.removeStudentBag(Colour.PINK);
        assertEquals(23, gameTest.getBag(Colour.PINK));
        gameTest.removeStudentBag(Colour.PINK);
        assertEquals(22, gameTest.getBag(Colour.PINK));
        gameTest.addStudentBag(Colour.PINK);
        assertEquals(23, gameTest.getBag(Colour.PINK));
    }

    @Test
    void removeStudentBag() {
        assertEquals(24, gameTest.getBag(Colour.GREEN));
        gameTest.removeStudentBag(Colour.GREEN);
        assertEquals(23, gameTest.getBag(Colour.GREEN));
        gameTest.removeStudentBag(Colour.GREEN);
        assertEquals(22, gameTest.getBag(Colour.GREEN));
    }

    @Test
    void removeGroupIsland() {
        GroupIsland islandRemoved = gameTest.getIslands().get(2);
        gameTest.removeGroupIsland(gameTest.getIslands().get(2));
        for (int i = 0; i < gameTest.getIslands().size(); i++) {
            assertNotEquals(gameTest.getIslands().get(i), islandRemoved);
        }
    }

    @Test
    void unify() {
        GroupIsland island2 = gameTest.getIslands().get(1);

        gameTest.unify(gameTest.getIslands().get(0), gameTest.getIslands().get(1));
        assertEquals(11, gameTest.getIslands().size());

        for (int i = 0; i < gameTest.getIslands().size(); i++) {
            assertNotEquals(island2, gameTest.getIslands().get(i));
        }

        GroupIsland island11 = gameTest.getIslands().get(10);
        gameTest.unify(gameTest.getIslands().get(0), gameTest.getIslands().get(10));
        assertEquals(10, gameTest.getIslands().size());

        for (int i = 0; i < gameTest.getIslands().size(); i++) {
            assertNotEquals(island11, gameTest.getIslands().get(i));
        }

        GroupIsland island5 = gameTest.getIslands().get(4);
        gameTest.unify(gameTest.getIslands().get(3), gameTest.getIslands().get(4));
        assertEquals(9, gameTest.getIslands().size());

        for (int i = 0; i < gameTest.getIslands().size(); i++) {
            assertNotEquals(island5, gameTest.getIslands().get(i));
        }

    }

    @Test
    void incrementRound() {
        gameTest.incrementRound();
        assertEquals(1, gameTest.getRound());
    }

    @Test
    void addCLoudTile() {
        CloudTile cloud1 = new CloudTile(gameTest.bagDrawCloudTile());
        CloudTile cloud2 = new CloudTile(gameTest.bagDrawCloudTile());
        gameTest.addCLoudTile(cloud1);
        assertEquals(cloud1, gameTest.getCloudTiles().get(0));
        gameTest.addCLoudTile(cloud2);
        assertEquals(cloud2, gameTest.getCloudTiles().get(1));
    }

    @Test
    void removeCLoudTile() {
        CloudTile cloud1 = new CloudTile(gameTest.bagDrawCloudTile());
        gameTest.addCLoudTile(cloud1);
        gameTest.removeCLoudTile(cloud1);
        for (int i = 0; i < gameTest.getCloudTiles().size(); i++) {
            assertNotEquals(gameTest.getCloudTiles().get(i), cloud1);
        }
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
        gameTest.setGamePhase(GamePhase.CHOOSE_CLOUD_TILE);
        assertEquals(GamePhase.CHOOSE_CLOUD_TILE, gameTest.getGamePhase());
    }

    @Test
    void isCurrentPlayer() {
        gameTest.setCurrentPlayer(player1);
        assertTrue(gameTest.isCurrentPlayer(player1));
    }

    @Test
    void bagDrawStudent() {
        /*
        DA FARE
         */
    }
}