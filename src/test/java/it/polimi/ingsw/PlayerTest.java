package it.polimi.ingsw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Game game;
    private Player player1;

    @BeforeEach
    void setUp() {
        game = new Game();
        player1 = new Player("p1", game);
        game.addPlayer(player1);
    }

    @Test
    void addStudentToDiningRoom() {
        for (Colour colour : Colour.values())
            assertEquals(0, player1.getDiningRoom(colour));

        for (int i = 1; i <= 10; i++) {
            for (Colour colour : Colour.values()) {
                player1.addStudentToDiningRoom(colour);
                assertEquals(i, player1.getDiningRoom(colour));
            }
        }

    }

    @Test
    void removeStudentFromDiningRoom() {
        for (int i = 1; i <= 10; i++) {
            for (Colour colour : Colour.values()) {
                player1.addStudentToDiningRoom(colour);
            }
        }
        for (int i = 1; i <= 10; i++) {
            for (Colour colour : Colour.values()) {
                player1.removeStudentFromDiningRoom(colour);
                assertEquals(10 - i, player1.getDiningRoom(colour));
            }
        }
    }

    @Test
    void addStudentToEntrance() {
        game.setNumberStudentsEntrance(7);
        for (Colour colour : Colour.values())
            assertEquals(0, player1.getEntrance(colour));

        for (Colour colour : Colour.values()) {
            for (int i = 1; i <= 7; i++) {
                player1.addStudentToEntrance(colour);
                assertEquals(i, player1.getEntrance(colour));
            }
            for (int i = 1; i <= 7; i++) {
                player1.removeStudentFromEntrance(colour);
            }
        }

        game.setNumberStudentsEntrance(9);
        for (Colour colour : Colour.values())
            assertEquals(0, player1.getEntrance(colour));

        for (Colour colour : Colour.values()) {
            for (int i = 1; i <= 9; i++) {
                player1.addStudentToEntrance(colour);
                assertEquals(i, player1.getEntrance(colour));
            }
            for (int i = 1; i <= 9; i++) {
                player1.removeStudentFromEntrance(colour);
            }
        }
    }


    @Test
    void testAddStudentToEntrance() {
        HashMap<Colour,Integer> cloudTileStudents=game.bagDrawCloudTile();
        CloudTile cloudTile=new CloudTile(cloudTileStudents);
        player1.addStudentToEntrance(cloudTile);
        for(Colour colour: Colour.values()){
            assertEquals(cloudTileStudents.get(colour),player1.getEntrance(colour));
        }
    }

    @Test
    void removeStudentFromEntrance() {
        game.setNumberStudentsEntrance(7);
        for (Colour colour : Colour.values())
            assertEquals(0, player1.getEntrance(colour));

        for (Colour colour : Colour.values()) {
            for (int i = 1; i <= 7; i++) {
                player1.addStudentToEntrance(colour);
            }
            for (int i = 1; i <= 7; i++) {
                player1.removeStudentFromEntrance(colour);
                assertEquals(7 - i, player1.getEntrance(colour));
            }
        }

        game.setNumberStudentsEntrance(9);
        for (Colour colour : Colour.values())
            assertEquals(0, player1.getEntrance(colour));

        for (Colour colour : Colour.values()) {
            for (int i = 1; i <= 9; i++) {
                player1.addStudentToEntrance(colour);
            }
            for (int i = 1; i <= 9; i++) {
                player1.removeStudentFromEntrance(colour);
                assertEquals(9 - i, player1.getEntrance(colour));
            }
        }
    }

    @Test
    void addProfessor() {
        for (Colour colour : Colour.values()) {
            player1.addProfessor(colour);
            assertTrue(player1.hasProfessor(colour));
        }
    }

    @Test
    void removeProfessor() {
        for (Colour colour : Colour.values()) {
            player1.addProfessor(colour);
        }
        for (Colour colour : Colour.values()) {
            player1.removeProfessor(colour);
            assertFalse(player1.hasProfessor(colour));
        }
    }

    @Test
    void hasProfessor() {
        for (Colour colour : Colour.values()) {
            assertFalse(player1.hasProfessor(colour));
            player1.addProfessor(colour);
            assertTrue(player1.hasProfessor(colour));
        }
    }

    @Test
    void addTower() {
        game.setNumberOfTowersPerPlayer(8);
        for (int i = 1; i <= 7; i++) {
            player1.addTower(i);
            assertEquals(i, player1.getTowers());
            player1.removeTower(i);
        }

        game.setNumberOfTowersPerPlayer(6);
        for (int i = 1; i <= 5; i++) {
            player1.addTower(i);
            assertEquals(i, player1.getTowers());
            player1.removeTower(i);
        }

    }

    @Test
    void removeTower() {
        game.setNumberOfTowersPerPlayer(8);
        player1.addTower(8);
        for (int i = 1; i <= 7; i++) {
            player1.removeTower(i);
            assertEquals(8 - i, player1.getTowers());
            player1.addTower(i);
        }

        player1.removeTower(8);

        game.setNumberOfTowersPerPlayer(6);
        player1.addTower(6);
        for (int i = 1; i <= 5; i++) {
            player1.removeTower(i);
            assertEquals(6 - i, player1.getTowers());
            player1.addTower(i);
        }
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
    void addCoins() {
        for (int i = 1; i <= 10; i++) {
            player1.addCoins(i);
            assertEquals(i + 1, player1.getCoins());
            player1.removeCoins(i);
        }
    }

    @Test
    void removeCoins() {
        player1.addCoins(10);
        for (int i = 1; i <= 10; i++) {
            player1.removeCoins(i);
            assertEquals(10 - i + 1, player1.getCoins());
            player1.addCoins(i);
        }

    }
}