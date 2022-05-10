package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchoolBoardTest {
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController(false, 2);
        gameController.setUp();
        gameController.getGame().addPlayer(new BasicPlayer("sara", Wizard.TYPE_1, TowerColour.WHITE));
        gameController.getGame().addPlayer(new BasicPlayer("laura", Wizard.TYPE_2, TowerColour.BLACK));
    }


    @Test
    void addStudentToDiningRoom() {
        for (Colour colour : Colour.values())
            assertEquals(0, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getDiningRoom(colour));

        for (int i = 1; i <= 10; i++) {
            for (Colour colour : Colour.values()) {
                gameController.getGame().getPlayerByIndex(0).getSchoolBoard().addStudentToDiningRoom(colour);
                assertEquals(i, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getDiningRoom(colour));
            }
        }

    }

    @Test
    void removeStudentFromDiningRoom() {
        for (int i = 1; i <= 10; i++) {
            for (Colour colour : Colour.values()) {
                gameController.getGame().getPlayerByIndex(0).getSchoolBoard().addStudentToDiningRoom(colour);
            }
        }
        for (int i = 1; i <= 10; i++) {
            for (Colour colour : Colour.values()) {
                gameController.getGame().getPlayerByIndex(0).getSchoolBoard().removeStudentFromDiningRoom(colour);
                assertEquals(10 - i, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getDiningRoom(colour));
            }
        }
    }

    @Test
    public void removeAllStudentFromDiningRoom() {
        for (Colour colour : Colour.values()) {
            assertEquals(0, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getDiningRoom(colour));
            gameController.getGame().getPlayerByIndex(0).getSchoolBoard().addStudentToDiningRoom(colour);
        }

        for (Colour colour : Colour.values()) {
            assertEquals(1, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getDiningRoom(colour));
        }

        gameController.getGame().getPlayerByIndex(0).getSchoolBoard().removeAllStudentFromDiningRoom(Colour.BLUE);

        assertEquals(0, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getDiningRoom(Colour.BLUE));
    }


    @Test
    void addStudentToEntrance() {
        int size = 0;
        for (Colour colour : Colour.values()) {
            size += gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour);
        }

        assertEquals(7, size);

        for (Colour colour : Colour.values()) {
            int num;
            if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                num = gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour);
                gameController.getGame().getPlayerByIndex(0).getSchoolBoard().removeStudentFromEntrance(colour);
                assertEquals(num - 1, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour));
                gameController.getGame().getPlayerByIndex(0).getSchoolBoard().addStudentToEntrance(colour);
                assertEquals(num, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour));
            }
        }
    }

    @Test
    void removeStudentFromEntrance() {
        int size = 0;
        for (Colour colour : Colour.values()) {
            size += gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour);
        }

        assertEquals(7, size);

        for (Colour colour : Colour.values()) {
            int num;
            if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                num = gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour);
                gameController.getGame().getPlayerByIndex(0).getSchoolBoard().removeStudentFromEntrance(colour);
                assertEquals(num - 1, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour));
                gameController.getGame().getPlayerByIndex(0).getSchoolBoard().addStudentToEntrance(colour);
                assertEquals(num, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour));
            }
        }
    }

    @Test
    void addProfessor() {
        for (Colour colour : Colour.values()) {
            gameController.getGame().getPlayerByIndex(0).getSchoolBoard().addProfessor(colour);
            assertTrue(gameController.getGame().getPlayerByIndex(0).getSchoolBoard().hasProfessor(colour));
        }
    }

    @Test
    void removeProfessor() {
        for (Colour colour : Colour.values()) {
            gameController.getGame().getPlayerByIndex(0).getSchoolBoard().addProfessor(colour);
        }
        for (Colour colour : Colour.values()) {
            gameController.getGame().getPlayerByIndex(0).getSchoolBoard().removeProfessor(colour);
            assertFalse(gameController.getGame().getPlayerByIndex(0).getSchoolBoard().hasProfessor(colour));
        }
    }

    @Test
    void hasProfessor() {
        for (Colour colour : Colour.values()) {
            assertFalse(gameController.getGame().getPlayerByIndex(0).getSchoolBoard().hasProfessor(colour));
            gameController.getGame().getPlayerByIndex(0).getSchoolBoard().addProfessor(colour);
            assertTrue(gameController.getGame().getPlayerByIndex(0).getSchoolBoard().hasProfessor(colour));
        }
    }

    @Test
    void addTower() {
        assertEquals(8, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getTowers());
        gameController.getGame().getPlayerByIndex(0).getSchoolBoard().removeTower(1);
        assertEquals(7, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getTowers());
        gameController.getGame().getPlayerByIndex(0).getSchoolBoard().removeTower(2);
        assertEquals(5, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getTowers());
        gameController.getGame().getPlayerByIndex(0).getSchoolBoard().addTower(1);
        assertEquals(6, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getTowers());
    }

    @Test
    void removeTower() {
        assertEquals(8, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getTowers());
        gameController.getGame().getPlayerByIndex(0).getSchoolBoard().removeTower(1);
        assertEquals(7, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getTowers());
        gameController.getGame().getPlayerByIndex(0).getSchoolBoard().removeTower(2);
        assertEquals(5, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getTowers());
        gameController.getGame().getPlayerByIndex(0).getSchoolBoard().addTower(1);
        assertEquals(6, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getTowers());
    }

    @Test
    void getNumberStudentEntrance() {
        assertEquals(7, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(7, gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getNumberStudentsEntrance());
    }

    @Test
    void getNumberOfProfessors() {
        gameController.getGame().getPlayerByIndex(0).getSchoolBoard().addProfessor(Colour.PINK);
        int professorCount = 0;
        for (Colour colour : Colour.values()) {
            if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().hasProfessor(colour)) {
                professorCount += 1;
            }
        }
        assertEquals(professorCount, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberOfProfessors());
    }
}