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
        gameController.getGame().addPlayer(new BasicPlayer("sara",Wizard.TYPE_1));
        gameController.getGame().addPlayer(new BasicPlayer("laura",Wizard.TYPE_2));
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

        gameController.getGame().getPlayerByIndex(0).getSchoolBoard().removeAllStudentFromDiningRoom();

        for (Colour colour : Colour.values()) {
            assertEquals(0, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getDiningRoom(colour));
        }
    }


    @Test
    void addStudentToEntrance() {
        for (Colour colour : Colour.values())
            assertEquals(0, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour));

        for (Colour colour : Colour.values()) {
            for (int i = 1; i <= 7; i++) {
                gameController.getGame().getPlayerByIndex(0).getSchoolBoard().addStudentToEntrance(colour);
                assertEquals(i, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour));
            }
        }
    }

    @Test
    void removeStudentFromEntrance() {
        for (Colour colour : Colour.values())
            assertEquals(0, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour));

        for (Colour colour : Colour.values()) {
            for (int i = 1; i <= 7; i++) {
                gameController.getGame().getPlayerByIndex(0).getSchoolBoard().addStudentToEntrance(colour);
            }
            for (int i = 1; i <= 7; i++) {
                gameController.getGame().getPlayerByIndex(0).getSchoolBoard().removeStudentFromEntrance(colour);
                assertEquals(7 - i, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour));
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
        for (int i = 1; i <= 5; i++) {
            gameController.getGame().getPlayerByIndex(0).getSchoolBoard().addTower(i);
            assertEquals(i, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getTowers());
            gameController.getGame().getPlayerByIndex(0).getSchoolBoard().removeTower(i);
        }

    }

    @Test
    void removeTower() {
        gameController.getGame().getPlayerByIndex(0).getSchoolBoard().addTower(8);
        for (int i = 1; i <= 7; i++) {
            gameController.getGame().getPlayerByIndex(0).getSchoolBoard().removeTower(i);
            assertEquals(8 - i, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getTowers());
            gameController.getGame().getPlayerByIndex(0).getSchoolBoard().addTower(i);
        }
    }

    @Test
    void getNumberStudentEntrance(){
        gameController.getGame().getPlayerByIndex(0).getSchoolBoard().addStudentToEntrance(Colour.RED);
        gameController.getGame().getPlayerByIndex(0).getSchoolBoard().addStudentToEntrance(Colour.YELLOW);
        gameController.getGame().getPlayerByIndex(0).getSchoolBoard().addStudentToEntrance(Colour.RED);
        gameController.getGame().getPlayerByIndex(0).getSchoolBoard().addStudentToEntrance(Colour.PINK);
        assertEquals(4,gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());

    }

    @Test
    void getNumberOfProfessors() {
        int contaProfessori=0;
        for(Colour colour:Colour.values()){
            if(gameController.getGame().getPlayerByIndex(0).getSchoolBoard().hasProfessor(colour)){
                contaProfessori+=1;
            }
        }
        assertEquals(contaProfessori,gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberOfProfessors());
    }
}