package it.polimi.ingsw.model.table;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BagTest {
    GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController(false, 2);
    }

    @Test
    void checkSetting(){
        for (Colour colour : Colour.values()) {
            assertEquals(24, gameController.getGame().getTable().getBag().getBagStudent(colour));
        }
    }

    @Test
    void addStudentBag() {
        for (Colour colour : Colour.values()) {
            assertEquals(24, gameController.getGame().getTable().getBag().getBagStudent(colour));
        }

        gameController.getGame().getTable().getBag().addStudentBag(Colour.BLUE);
        assertEquals(25, gameController.getGame().getTable().getBag().getBagStudent(Colour.BLUE));
    }

    @Test
    void isBagEmpty(){
        while(! gameController.getGame().getTable().getBag().isBagEmpty()){
            gameController.getGame().getTable().getBag().bagDrawStudent();
        }
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getTable().getBag().bagDrawStudent());
    }

    @Test
    void bagDrawStudent() {
        int numPinkRemained = gameController.getGame().getTable().getBag().getBagStudent(Colour.PINK);
        int numBlueRemained = gameController.getGame().getTable().getBag().getBagStudent(Colour.BLUE);
        int numGreenRemained = gameController.getGame().getTable().getBag().getBagStudent(Colour.GREEN);
        int numYellowRemained = gameController.getGame().getTable().getBag().getBagStudent(Colour.YELLOW);
        int numRedRemained = gameController.getGame().getTable().getBag().getBagStudent(Colour.RED);

        assertEquals(24, numPinkRemained);
        assertEquals(24, numBlueRemained);
        assertEquals(24, numGreenRemained);
        assertEquals(24, numYellowRemained);
        assertEquals(24, numRedRemained);

        for (int i = 0; i < 24; i++) {
            Colour colour = gameController.getGame().getTable().getBag().bagDrawStudent();
            if (colour.equals(Colour.PINK)) {
                numPinkRemained--;
                assertEquals(numPinkRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.PINK));
                assertEquals(numGreenRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.GREEN));
                assertEquals(numBlueRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.BLUE));
                assertEquals(numRedRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.RED));
                assertEquals(numYellowRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.YELLOW));
            } else if (colour.equals(Colour.GREEN)) {
                numGreenRemained--;
                assertEquals(numPinkRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.PINK));
                assertEquals(numGreenRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.GREEN));
                assertEquals(numBlueRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.BLUE));
                assertEquals(numRedRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.RED));
                assertEquals(numYellowRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.YELLOW));
            } else if (colour.equals(Colour.BLUE)) {
                numBlueRemained--;
                assertEquals(numPinkRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.PINK));
                assertEquals(numGreenRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.GREEN));
                assertEquals(numBlueRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.BLUE));
                assertEquals(numRedRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.RED));
                assertEquals(numYellowRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.YELLOW));
            } else if (colour.equals(Colour.RED)) {
                numRedRemained--;
                assertEquals(numPinkRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.PINK));
                assertEquals(numGreenRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.GREEN));
                assertEquals(numBlueRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.BLUE));
                assertEquals(numRedRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.RED));
                assertEquals(numYellowRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.YELLOW));
            } else {
                numYellowRemained--;
                assertEquals(numPinkRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.PINK));
                assertEquals(numGreenRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.GREEN));
                assertEquals(numBlueRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.BLUE));
                assertEquals(numRedRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.RED));
                assertEquals(numYellowRemained, gameController.getGame().getTable().getBag().getBagStudent(Colour.YELLOW));
            }
        }
    }
}