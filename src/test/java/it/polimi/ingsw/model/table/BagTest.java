package it.polimi.ingsw.model.table;

import it.polimi.ingsw.model.table.Bag;
import it.polimi.ingsw.model.game.BasicGame;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BagTest {
    Game gameTest;

    @BeforeEach
    void setUp(){
        gameTest = new BasicGame();
    }

    @Test
    void addStudentBag() {
        for (Colour colour : Colour.values()) {
            assertEquals(24, gameTest.getTable().getBag().getBagStudent(colour));
        }

        gameTest.getTable().getBag().addStudentBag(Colour.BLUE);
        assertEquals(25, gameTest.getTable().getBag().getBagStudent(Colour.BLUE));
    }

    @Test
    void bagDrawStudent() {
        int numPinkRemained = gameTest.getTable().getBag().getBagStudent(Colour.PINK);
        int numBlueRemained = gameTest.getTable().getBag().getBagStudent(Colour.BLUE);
        int numGreenRemained = gameTest.getTable().getBag().getBagStudent(Colour.GREEN);
        int numYellowRemained = gameTest.getTable().getBag().getBagStudent(Colour.YELLOW);
        int numRedRemained = gameTest.getTable().getBag().getBagStudent(Colour.RED);

        assertEquals(24, numPinkRemained);
        assertEquals(24, numBlueRemained);
        assertEquals(24, numGreenRemained);
        assertEquals(24, numYellowRemained);
        assertEquals(24, numRedRemained);

        for(int i = 0; i < 24; i++){
            Colour colour = gameTest.getTable().getBag().bagDrawStudent();
            if (colour.equals(Colour.PINK)) {
                numPinkRemained--;
                assertEquals(numPinkRemained, gameTest.getTable().getBag().getBagStudent(Colour.PINK));
                assertEquals(numGreenRemained, gameTest.getTable().getBag().getBagStudent(Colour.GREEN));
                assertEquals(numBlueRemained, gameTest.getTable().getBag().getBagStudent(Colour.BLUE));
                assertEquals(numRedRemained, gameTest.getTable().getBag().getBagStudent(Colour.RED));
                assertEquals(numYellowRemained, gameTest.getTable().getBag().getBagStudent(Colour.YELLOW));
            } else if (colour.equals(Colour.GREEN)) {
                numGreenRemained--;
                assertEquals(numPinkRemained, gameTest.getTable().getBag().getBagStudent(Colour.PINK));
                assertEquals(numGreenRemained, gameTest.getTable().getBag().getBagStudent(Colour.GREEN));
                assertEquals(numBlueRemained, gameTest.getTable().getBag().getBagStudent(Colour.BLUE));
                assertEquals(numRedRemained, gameTest.getTable().getBag().getBagStudent(Colour.RED));
                assertEquals(numYellowRemained, gameTest.getTable().getBag().getBagStudent(Colour.YELLOW));
            } else if (colour.equals(Colour.BLUE)) {
                numBlueRemained--;
                assertEquals(numPinkRemained, gameTest.getTable().getBag().getBagStudent(Colour.PINK));
                assertEquals(numGreenRemained, gameTest.getTable().getBag().getBagStudent(Colour.GREEN));
                assertEquals(numBlueRemained, gameTest.getTable().getBag().getBagStudent(Colour.BLUE));
                assertEquals(numRedRemained, gameTest.getTable().getBag().getBagStudent(Colour.RED));
                assertEquals(numYellowRemained, gameTest.getTable().getBag().getBagStudent(Colour.YELLOW));
            } else if (colour.equals(Colour.RED)) {
                numRedRemained--;
                assertEquals(numPinkRemained, gameTest.getTable().getBag().getBagStudent(Colour.PINK));
                assertEquals(numGreenRemained, gameTest.getTable().getBag().getBagStudent(Colour.GREEN));
                assertEquals(numBlueRemained, gameTest.getTable().getBag().getBagStudent(Colour.BLUE));
                assertEquals(numRedRemained, gameTest.getTable().getBag().getBagStudent(Colour.RED));
                assertEquals(numYellowRemained, gameTest.getTable().getBag().getBagStudent(Colour.YELLOW));
            } else {
                numYellowRemained--;
                assertEquals(numPinkRemained, gameTest.getTable().getBag().getBagStudent(Colour.PINK));
                assertEquals(numGreenRemained, gameTest.getTable().getBag().getBagStudent(Colour.GREEN));
                assertEquals(numBlueRemained, gameTest.getTable().getBag().getBagStudent(Colour.BLUE));
                assertEquals(numRedRemained, gameTest.getTable().getBag().getBagStudent(Colour.RED));
                assertEquals(numYellowRemained, gameTest.getTable().getBag().getBagStudent(Colour.YELLOW));
            }
        }
        }
}