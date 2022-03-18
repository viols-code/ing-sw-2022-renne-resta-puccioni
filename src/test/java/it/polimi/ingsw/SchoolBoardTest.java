package it.polimi.ingsw;

import it.polimi.ingsw.model.game.BasicGame;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.SchoolBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchoolBoardTest {
    private SchoolBoard schoolBoard;
    private Game game;

    @BeforeEach
    void setUp() {
       schoolBoard = new SchoolBoard();
       game = new BasicGame();
    }

    @Test
    void addStudentToDiningRoom() {
        for (Colour colour : Colour.values())
            assertEquals(0, schoolBoard.getDiningRoom(colour));

        for (int i = 1; i <= 10; i++) {
            for (Colour colour : Colour.values()) {
                schoolBoard.addStudentToDiningRoom(colour);
                assertEquals(i, schoolBoard.getDiningRoom(colour));
            }
        }

    }

    @Test
    void removeStudentFromDiningRoom() {
        for (int i = 1; i <= 10; i++) {
            for (Colour colour : Colour.values()) {
                schoolBoard.addStudentToDiningRoom(colour);
            }
        }
        for (int i = 1; i <= 10; i++) {
            for (Colour colour : Colour.values()) {
                schoolBoard.removeStudentFromDiningRoom(colour);
                assertEquals(10 - i, schoolBoard.getDiningRoom(colour));
            }
        }
    }

    @Test
    void addStudentToEntrance() {
        for (Colour colour : Colour.values())
            assertEquals(0, schoolBoard.getEntrance(colour));

        for (Colour colour : Colour.values()) {
            for (int i = 1; i <= 7; i++) {
                schoolBoard.addStudentToEntrance(colour);
                assertEquals(i, schoolBoard.getEntrance(colour));
            }
        }
    }

    @Test
    void removeStudentFromEntrance() {
        for (Colour colour : Colour.values())
            assertEquals(0, schoolBoard.getEntrance(colour));

        for (Colour colour : Colour.values()) {
            for (int i = 1; i <= 7; i++) {
                schoolBoard.addStudentToEntrance(colour);
            }
            for (int i = 1; i <= 7; i++) {
                schoolBoard.removeStudentFromEntrance(colour);
                assertEquals(7 - i, schoolBoard.getEntrance(colour));
            }
        }
    }

    @Test
    void addProfessor() {
        for (Colour colour : Colour.values()) {
            schoolBoard.addProfessor(colour);
            assertTrue(schoolBoard.hasProfessor(colour));
        }
    }

    @Test
    void removeProfessor() {
        for (Colour colour : Colour.values()) {
            schoolBoard.addProfessor(colour);
        }
        for (Colour colour : Colour.values()) {
            schoolBoard.removeProfessor(colour);
            assertFalse(schoolBoard.hasProfessor(colour));
        }
    }

    @Test
    void hasProfessor() {
        for (Colour colour : Colour.values()) {
            assertFalse(schoolBoard.hasProfessor(colour));
            schoolBoard.addProfessor(colour);
            assertTrue(schoolBoard.hasProfessor(colour));
        }
    }

    @Test
    void addTower() {
        for (int i = 1; i <= 5; i++) {
            schoolBoard.addTower(i);
            assertEquals(i, schoolBoard.getTowers());
            schoolBoard.removeTower(i);
        }

    }

    @Test
    void removeTower() {
        schoolBoard.addTower(8);
        for (int i = 1; i <= 7; i++) {
            schoolBoard.removeTower(i);
            assertEquals(8 - i, schoolBoard.getTowers());
            schoolBoard.addTower(i);
        }
    }

}