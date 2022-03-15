package it.polimi.ingsw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupIslandTest {
    GroupIsland groupIslandTest;

    @BeforeEach
    void setUp() {
        groupIslandTest = new GroupIsland();
    }

    @Test
    void unifyIsland() {
        GroupIsland groupIslandTest1 = new GroupIsland();
        int size = groupIslandTest.getNumberOfSingleIsland();

        for(int i = 0; i < groupIslandTest1.getNumberOfSingleIsland(); i++) {
            for (Colour colour : Colour.values()) {
                groupIslandTest1.getIslands(i).addStudent(colour);
                assertEquals(1, groupIslandTest1.getIslands(i).getStudents(colour));
            }
        }

        for(int i = 0; i < groupIslandTest.getNumberOfSingleIsland(); i++) {
            for (Colour colour : Colour.values()) {
                groupIslandTest.getIslands(i).addStudent(colour);
                assertEquals(1, groupIslandTest.getIslands(i).getStudents(colour));
            }
        }

        groupIslandTest.unifyIsland(groupIslandTest1);

        for(int i = 0; i < groupIslandTest.getNumberOfSingleIsland(); i++) {
            for (Colour colour : Colour.values()) {
                assertEquals(1, groupIslandTest.getIslands(i).getStudents(colour));
            }
        }

        for (Colour colour : Colour.values()) {
            int count = 0;
            for(int i = 0; i < groupIslandTest.getNumberOfSingleIsland(); i++) {
                count += groupIslandTest.getIslands(i).getStudents(colour);
            }
            assertEquals(2, count);
        }


        assertEquals(size + groupIslandTest1.getNumberOfSingleIsland(), groupIslandTest.getNumberOfSingleIsland());
    }


    @Test
    void removeMotherNature() {
        assertFalse(groupIslandTest.getMotherNature());
        groupIslandTest.placeMotherNature();
        assertTrue(groupIslandTest.getMotherNature());
        groupIslandTest.removeMotherNature();
        assertFalse(groupIslandTest.getMotherNature());
    }

    @Test
    void placeMotherNature() {
        assertFalse(groupIslandTest.getMotherNature());
        groupIslandTest.placeMotherNature();
        assertTrue(groupIslandTest.getMotherNature());
    }


    @Test
    void changeInfluence() {
        Player player1 = new Player("Viola");
        Player player2 = new Player("Laura");

        assertNull(groupIslandTest.getInfluence());
        groupIslandTest.changeInfluence(player1);
        assertEquals(player1, groupIslandTest.getInfluence());
        groupIslandTest.changeInfluence(player2);
        assertEquals(player2, groupIslandTest.getInfluence());
    }

    @Test
    void calculateInfluence() {
        Player player1 = new Player("Viola");
        Player player2 = new Player("Laura");
        GroupIsland groupIslandTest1 = new GroupIsland();

        player1.getSchoolBoard().addProfessor(Colour.BLUE);
        player1.getSchoolBoard().addProfessor(Colour.PINK);
        player1.getSchoolBoard().addProfessor(Colour.RED);
        player2.getSchoolBoard().addProfessor(Colour.YELLOW);
        player2.getSchoolBoard().addProfessor(Colour.GREEN);

        groupIslandTest.getIslands(0).addStudent(Colour.BLUE);
        groupIslandTest.getIslands(0).addStudent(Colour.PINK);

        assertEquals(2, groupIslandTest.calculateInfluence(player1));
        assertEquals(0, groupIslandTest.calculateInfluence(player2));

        groupIslandTest.changeInfluence(player1);

        assertEquals(3, groupIslandTest.calculateInfluence(player1));
        assertEquals(0, groupIslandTest.calculateInfluence(player2));

        groupIslandTest.getIslands(0).addStudent(Colour.YELLOW);
        groupIslandTest.getIslands(0).addStudent(Colour.YELLOW);
        groupIslandTest.getIslands(0).addStudent(Colour.GREEN);
        groupIslandTest.getIslands(0).addStudent(Colour.GREEN);

        assertEquals(3, groupIslandTest.calculateInfluence(player1));
        assertEquals(4, groupIslandTest.calculateInfluence(player2));

        groupIslandTest.changeInfluence(player2);

        assertEquals(2, groupIslandTest.calculateInfluence(player1));
        assertEquals(5, groupIslandTest.calculateInfluence(player2));

        groupIslandTest.getIslands(0).addStudent(Colour.RED);

        assertEquals(3, groupIslandTest.calculateInfluence(player1));
        assertEquals(5, groupIslandTest.calculateInfluence(player2));

        groupIslandTest1.getIslands(0).addStudent(Colour.GREEN);
        groupIslandTest1.getIslands(0).addStudent(Colour.PINK);
        groupIslandTest1.getIslands(0).addStudent(Colour.PINK);

        assertEquals(2, groupIslandTest1.calculateInfluence(player1));
        assertEquals(1, groupIslandTest1.calculateInfluence(player2));

        groupIslandTest1.changeInfluence(player1);

        assertEquals(3, groupIslandTest1.calculateInfluence(player1));
        assertEquals(1, groupIslandTest1.calculateInfluence(player2));

        groupIslandTest1.getIslands(0).addStudent(Colour.YELLOW);
        groupIslandTest1.getIslands(0).addStudent(Colour.YELLOW);
        groupIslandTest1.getIslands(0).addStudent(Colour.YELLOW);

        assertEquals(3, groupIslandTest1.calculateInfluence(player1));
        assertEquals(4, groupIslandTest1.calculateInfluence(player2));

        groupIslandTest1.changeInfluence(player2);

        assertEquals(2, groupIslandTest1.calculateInfluence(player1));
        assertEquals(5, groupIslandTest1.calculateInfluence(player2));

        groupIslandTest.unifyIsland(groupIslandTest1);

        assertEquals(5, groupIslandTest.calculateInfluence(player1));
        assertEquals(10, groupIslandTest.calculateInfluence(player2));
    }

    @Test
    void calculateInfluenceWithoutTowers() {
        Player player1 = new Player("Viola");
        Player player2 = new Player("Laura");
        GroupIsland groupIslandTest1 = new GroupIsland();

        player1.getSchoolBoard().addProfessor(Colour.BLUE);
        player1.getSchoolBoard().addProfessor(Colour.PINK);
        player1.getSchoolBoard().addProfessor(Colour.RED);
        player2.getSchoolBoard().addProfessor(Colour.YELLOW);
        player2.getSchoolBoard().addProfessor(Colour.GREEN);

        groupIslandTest.getIslands(0).addStudent(Colour.BLUE);
        groupIslandTest.getIslands(0).addStudent(Colour.PINK);

        assertNull(groupIslandTest.getInfluence());
        assertEquals(2, groupIslandTest.calculateInfluenceWithoutTowers(player1));
        assertEquals(0, groupIslandTest.calculateInfluenceWithoutTowers(player2));

        groupIslandTest.changeInfluence(player1);
        assertEquals(player1, groupIslandTest.getInfluence());
        assertEquals(2, groupIslandTest.calculateInfluenceWithoutTowers(player1));
        assertEquals(0, groupIslandTest.calculateInfluenceWithoutTowers(player2));

        groupIslandTest.getIslands(0).addStudent(Colour.GREEN);
        groupIslandTest.getIslands(0).addStudent(Colour.GREEN);
        groupIslandTest.getIslands(0).addStudent(Colour.YELLOW);

        assertEquals(2, groupIslandTest.calculateInfluenceWithoutTowers(player1));
        assertEquals(3, groupIslandTest.calculateInfluenceWithoutTowers(player2));

        groupIslandTest.changeInfluence(player2);
        assertEquals(player2, groupIslandTest.getInfluence());
        assertEquals(2, groupIslandTest.calculateInfluenceWithoutTowers(player1));
        assertEquals(3, groupIslandTest.calculateInfluenceWithoutTowers(player2));

        groupIslandTest.getIslands(0).addStudent(Colour.RED);
        groupIslandTest.getIslands(0).addStudent(Colour.RED);

        assertEquals(4, groupIslandTest.calculateInfluenceWithoutTowers(player1));
        assertEquals(3, groupIslandTest.calculateInfluenceWithoutTowers(player2));
        groupIslandTest.changeInfluence(player1);
        assertEquals(player1, groupIslandTest.getInfluence());
        assertEquals(4, groupIslandTest.calculateInfluenceWithoutTowers(player1));
        assertEquals(3, groupIslandTest.calculateInfluenceWithoutTowers(player2));

        groupIslandTest1.getIslands(0).addStudent(Colour.GREEN);
        groupIslandTest1.getIslands(0).addStudent(Colour.PINK);
        groupIslandTest1.getIslands(0).addStudent(Colour.PINK);

        assertEquals(2, groupIslandTest1.calculateInfluenceWithoutTowers(player1));
        assertEquals(1, groupIslandTest1.calculateInfluenceWithoutTowers(player2));

        groupIslandTest1.changeInfluence(player1);
        assertEquals(player1, groupIslandTest1.getInfluence());
        assertEquals(2, groupIslandTest1.calculateInfluenceWithoutTowers(player1));
        assertEquals(1, groupIslandTest1.calculateInfluenceWithoutTowers(player2));


        groupIslandTest.unifyIsland(groupIslandTest1);
        assertEquals(6, groupIslandTest.calculateInfluenceWithoutTowers(player1));
        assertEquals(4, groupIslandTest.calculateInfluenceWithoutTowers(player2));
    }

    @Test
    void calculateInfluenceWithoutColour() {
        Player player1 = new Player("Viola");
        Player player2 = new Player("Laura");
        GroupIsland groupIslandTest1 = new GroupIsland();

        player1.getSchoolBoard().addProfessor(Colour.BLUE);
        player1.getSchoolBoard().addProfessor(Colour.PINK);
        player1.getSchoolBoard().addProfessor(Colour.RED);
        player2.getSchoolBoard().addProfessor(Colour.YELLOW);
        player2.getSchoolBoard().addProfessor(Colour.GREEN);

        groupIslandTest.getIslands(0).addStudent(Colour.BLUE);
        groupIslandTest.getIslands(0).addStudent(Colour.PINK);

        assertEquals(1, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.BLUE));
        assertEquals(1, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.PINK));
        assertEquals(2, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.RED));
        assertEquals(2, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.YELLOW));
        assertEquals(2, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.GREEN));

        assertEquals(0, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.BLUE));
        assertEquals(0, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.PINK));
        assertEquals(0, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.RED));
        assertEquals(0, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.YELLOW));
        assertEquals(0, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.GREEN));

        groupIslandTest.changeInfluence(player1);
        assertEquals(player1, groupIslandTest.getInfluence());

        assertEquals(2, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.BLUE));
        assertEquals(2, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.PINK));
        assertEquals(3, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.RED));
        assertEquals(3, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.YELLOW));
        assertEquals(3, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.GREEN));

        assertEquals(0, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.BLUE));
        assertEquals(0, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.PINK));
        assertEquals(0, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.RED));
        assertEquals(0, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.YELLOW));
        assertEquals(0, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.GREEN));

        groupIslandTest.getIslands(0).addStudent(Colour.RED);
        groupIslandTest.getIslands(0).addStudent(Colour.YELLOW);

        assertEquals(3, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.BLUE));
        assertEquals(3, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.PINK));
        assertEquals(3, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.RED));
        assertEquals(4, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.YELLOW));
        assertEquals(4, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.GREEN));

        assertEquals(1, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.BLUE));
        assertEquals(1, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.PINK));
        assertEquals(1, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.RED));
        assertEquals(0, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.YELLOW));
        assertEquals(1, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.GREEN));


        groupIslandTest1.getIslands(0).addStudent(Colour.GREEN);
        groupIslandTest1.getIslands(0).addStudent(Colour.PINK);

        assertEquals(1, groupIslandTest1.calculateInfluenceWithoutColour(player1, Colour.BLUE));
        assertEquals(0, groupIslandTest1.calculateInfluenceWithoutColour(player1, Colour.PINK));
        assertEquals(1, groupIslandTest1.calculateInfluenceWithoutColour(player1, Colour.RED));
        assertEquals(1, groupIslandTest1.calculateInfluenceWithoutColour(player1, Colour.YELLOW));
        assertEquals(1, groupIslandTest1.calculateInfluenceWithoutColour(player1, Colour.GREEN));

        assertEquals(1, groupIslandTest1.calculateInfluenceWithoutColour(player2, Colour.BLUE));
        assertEquals(1, groupIslandTest1.calculateInfluenceWithoutColour(player2, Colour.PINK));
        assertEquals(1, groupIslandTest1.calculateInfluenceWithoutColour(player2, Colour.RED));
        assertEquals(1, groupIslandTest1.calculateInfluenceWithoutColour(player2, Colour.YELLOW));
        assertEquals(0, groupIslandTest1.calculateInfluenceWithoutColour(player2, Colour.GREEN));

        groupIslandTest1.changeInfluence(player1);
        assertEquals(player1, groupIslandTest1.getInfluence());

        assertEquals(2, groupIslandTest1.calculateInfluenceWithoutColour(player1, Colour.BLUE));
        assertEquals(1, groupIslandTest1.calculateInfluenceWithoutColour(player1, Colour.PINK));
        assertEquals(2, groupIslandTest1.calculateInfluenceWithoutColour(player1, Colour.RED));
        assertEquals(2, groupIslandTest1.calculateInfluenceWithoutColour(player1, Colour.YELLOW));
        assertEquals(2, groupIslandTest1.calculateInfluenceWithoutColour(player1, Colour.GREEN));

        assertEquals(1, groupIslandTest1.calculateInfluenceWithoutColour(player2, Colour.BLUE));
        assertEquals(1, groupIslandTest1.calculateInfluenceWithoutColour(player2, Colour.PINK));
        assertEquals(1, groupIslandTest1.calculateInfluenceWithoutColour(player2, Colour.RED));
        assertEquals(1, groupIslandTest1.calculateInfluenceWithoutColour(player2, Colour.YELLOW));
        assertEquals(0, groupIslandTest1.calculateInfluenceWithoutColour(player2, Colour.GREEN));


        groupIslandTest.unifyIsland(groupIslandTest1);
        assertEquals(5, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.BLUE));
        assertEquals(4, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.PINK));
        assertEquals(5, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.RED));
        assertEquals(6, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.YELLOW));
        assertEquals(6, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.GREEN));

        assertEquals(2, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.BLUE));
        assertEquals(2, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.PINK));
        assertEquals(2, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.RED));
        assertEquals(1, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.YELLOW));
        assertEquals(1, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.GREEN));

    }
}

