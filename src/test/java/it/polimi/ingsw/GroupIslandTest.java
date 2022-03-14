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
        int size = groupIslandTest.getIslands().size();

        for (SingleIsland island : groupIslandTest1.getIslands()) {
            for(Colour colour: Colour.values()){
                island.addStudent(colour);
                assertEquals(1, island.getStudents(colour));
            }
        }

        for (SingleIsland island : groupIslandTest.getIslands()) {
            for(Colour colour: Colour.values()){
                island.addStudent(colour);
                assertEquals(1, island.getStudents(colour));
            }
        }

        groupIslandTest.unifyIsland(groupIslandTest1);

        for (SingleIsland island : groupIslandTest.getIslands()) {
            for(Colour colour: Colour.values()){
                assertEquals(1, island.getStudents(colour));
            }
        }

        for (Colour colour: Colour.values()){
            int count = 0;
            for(SingleIsland island: groupIslandTest.getIslands()){
                count += island.getStudents(colour);
            }
            assertEquals(2, count);
        }


        assertEquals(size + groupIslandTest1.getIslands().size(), groupIslandTest.getIslands().size());
    }

    @Test
    void changeInfluence() {
        Game game = new Game();
        Player player1 = new Player("Viola", game);
        Player player2= new Player("Laura", game);

        assertNull(groupIslandTest.getInfluence());
        groupIslandTest.changeInfluence(player1);
        assertEquals(player1, groupIslandTest.getInfluence());
        groupIslandTest.changeInfluence(player2);
        assertEquals(player2, groupIslandTest.getInfluence());
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
    void calculateInfluence() {
        Game game = new Game();
        Player player1 = new Player("Viola", game);
        Player player2 = new Player("Laura", game);
        GroupIsland groupIslandTest1 = new GroupIsland();

        player1.addProfessor(Colour.BLUE);
        player1.addProfessor(Colour.PINK);
        player1.addProfessor(Colour.RED);
        player2.addProfessor(Colour.YELLOW);
        player2.addProfessor(Colour.GREEN);

        groupIslandTest.getIslands().get(0).addStudent(Colour.BLUE);
        groupIslandTest.getIslands().get(0).addStudent(Colour.PINK);

        assertEquals(2, groupIslandTest.calculateInfluence(player1));
        assertEquals(0, groupIslandTest.calculateInfluence(player2));

        groupIslandTest.getIslands().get(0).addStudent(Colour.RED);
        groupIslandTest.getIslands().get(0).addStudent(Colour.YELLOW);

        assertEquals(3, groupIslandTest.calculateInfluence(player1));
        assertEquals(1, groupIslandTest.calculateInfluence(player2));

        groupIslandTest1.getIslands().get(0).addStudent(Colour.GREEN);
        groupIslandTest1.getIslands().get(0).addStudent(Colour.PINK);

        assertEquals(1, groupIslandTest1.calculateInfluence(player1));
        assertEquals(1, groupIslandTest1.calculateInfluence(player2));

        groupIslandTest.unifyIsland(groupIslandTest1);

        assertEquals(4, groupIslandTest.calculateInfluence(player1));
        assertEquals(2, groupIslandTest.calculateInfluence(player2));
    }

    @Test
    void calculateInfluenceWithoutColour() {
        Game game = new Game();
        Player player1 = new Player("Viola", game);
        Player player2 = new Player("Laura", game);
        GroupIsland groupIslandTest1 = new GroupIsland();

        player1.addProfessor(Colour.BLUE);
        player1.addProfessor(Colour.PINK);
        player1.addProfessor(Colour.RED);
        player2.addProfessor(Colour.YELLOW);
        player2.addProfessor(Colour.GREEN);

        groupIslandTest.getIslands().get(0).addStudent(Colour.BLUE);
        groupIslandTest.getIslands().get(0).addStudent(Colour.PINK);

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

        groupIslandTest.getIslands().get(0).addStudent(Colour.RED);
        groupIslandTest.getIslands().get(0).addStudent(Colour.YELLOW);

        assertEquals(2, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.BLUE));
        assertEquals(2, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.PINK));
        assertEquals(2, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.RED));
        assertEquals(3, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.YELLOW));
        assertEquals(3, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.GREEN));

        assertEquals(1, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.BLUE));
        assertEquals(1, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.PINK));
        assertEquals(1, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.RED));
        assertEquals(0, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.YELLOW));
        assertEquals(1, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.GREEN));


        groupIslandTest1.getIslands().get(0).addStudent(Colour.GREEN);
        groupIslandTest1.getIslands().get(0).addStudent(Colour.PINK);

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

        groupIslandTest.unifyIsland(groupIslandTest1);

        assertEquals(3, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.BLUE));
        assertEquals(2, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.PINK));
        assertEquals(3, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.RED));
        assertEquals(4, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.YELLOW));
        assertEquals(4, groupIslandTest.calculateInfluenceWithoutColour(player1, Colour.GREEN));

        assertEquals(2, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.BLUE));
        assertEquals(2, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.PINK));
        assertEquals(2, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.RED));
        assertEquals(1, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.YELLOW));
        assertEquals(1, groupIslandTest.calculateInfluenceWithoutColour(player2, Colour.GREEN));
    }
}

