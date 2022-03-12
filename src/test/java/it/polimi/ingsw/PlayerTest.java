package it.polimi.ingsw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Game game;
    private Player player1;

    @BeforeEach
    void setUp() {
        game = new Game();
        player1 = new Player("p1",game);
        game.addPlayer(player1);
    }

    @Test
    void addStudentToDiningRoom() {
        player1.addStudentToDiningRoom(Colour.PINK);
        assertEquals(1, player1.getDiningRoom(Colour.PINK));
    }

    @Test
    void removeStudentFromDiningRoom() {
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