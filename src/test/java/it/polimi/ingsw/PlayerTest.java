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
        int nPink,nYellow,nBlue,nGreen,nRed;
        nPink=4;
        nBlue=3;
        nGreen=2;
        nYellow=1;
        nRed=0;
        for(int i=0;i<nPink;i++)
            player1.addStudentToDiningRoom(Colour.PINK);
        for(int i=0;i<nBlue;i++)
            player1.addStudentToDiningRoom(Colour.BLUE);
        for(int i=0;i<nGreen;i++)
            player1.addStudentToDiningRoom(Colour.GREEN);
        for(int i=0;i<nYellow;i++)
            player1.addStudentToDiningRoom(Colour.YELLOW);
        for(int i=0;i<nRed;i++)
            player1.addStudentToDiningRoom(Colour.RED);

        assertEquals(nPink, player1.getDiningRoom(Colour.PINK));
        assertEquals(nBlue, player1.getDiningRoom(Colour.BLUE));
        assertEquals(nGreen, player1.getDiningRoom(Colour.GREEN));
        assertEquals(nYellow, player1.getDiningRoom(Colour.YELLOW));
        assertEquals(nRed, player1.getDiningRoom(Colour.RED));
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