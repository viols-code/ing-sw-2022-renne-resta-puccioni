package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.island.SingleIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SingleIslandTest {
    private SingleIsland testIsland;

    @BeforeEach
    void setUp() {
        testIsland = new SingleIsland();
    }

    @Test
    void addStudent() {
        for (Colour colour : Colour.values()) {
            assertEquals(0, testIsland.getStudents(colour));
        }

        for (int i = 1; i < 1000; i++) {
            for (Colour colour : Colour.values()) {
                testIsland.addStudent(colour);
                assertEquals(i, testIsland.getStudents(colour));
            }
        }
    }
}