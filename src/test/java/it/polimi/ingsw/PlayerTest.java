package it.polimi.ingsw;

import org.junit.Before;
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
        for(Colour colour: Colour.values())
            assertEquals(0,player1.getDiningRoom(colour));

        for(int i=1;i<=10;i++){
            for(Colour colour: Colour.values()){
                player1.addStudentToDiningRoom(colour);
                assertEquals(i,player1.getDiningRoom(colour));
            }
        }

    }

    @Test
    void removeStudentFromDiningRoom() {
        for(int i=1;i<=10;i++){
            for(Colour colour: Colour.values()){
                player1.addStudentToDiningRoom(colour);
            }
        }
        for(int i=1;i<=10;i++){
            for(Colour colour: Colour.values()){
                player1.removeStudentFromDiningRoom(colour);
                assertEquals(10-i,player1.getDiningRoom(colour));
            }
        }
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