package it.polimi.ingsw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Game game;
    private Player player1;

    @BeforeEach
    void setUp() {
        Game game = new Game();
        Player player1 = new Player("p1",game);
        game.addPlayer(player1);
    }
    /*@Test
    void addPlayer() {
        assertEquals(0, game.getPlayers().size());
        game.addPlayer(player);
        assertTrue(game.getPlayers().contains(player));
        assertEquals(1, game.getPlayers().size());
    }*/

    @Test
    void getNickname() {

    }

    @Test
    void getDiningRoom() {
    }

    @Test
    void addStudentToDiningRoom() {
    }

    @Test
    void removeStudentFromDiningRoom() {
    }

    @Test
    void getEntrance() {
    }

    @Test
    void addStudentToEntrance() {
    }

    @Test
    void testAddStudentToEntrance() {
    }

    @Test
    void removeStudentFromEntrance() {
    }

    @Test
    void addProfessor() {
    }

    @Test
    void removeProfessor() {
    }

    @Test
    void hasProfessor() {
    }

    @Test
    void getTowers() {
    }

    @Test
    void addTower() {
    }

    @Test
    void removeTower() {
    }

    @Test
    void addAssistantCardList() {
    }

    @Test
    void playAssistantCard() {
    }

    @Test
    void getCurrentAssistantCard() {
    }

    @Test
    void setCurrentAssistantCard() {
    }

    @Test
    void getCoins() {
    }

    @Test
    void addCoins() {
    }

    @Test
    void removeCoins() {
    }
}