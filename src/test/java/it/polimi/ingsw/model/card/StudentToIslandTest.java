package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.table.island.BasicGroupIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StudentToIslandTest {
    private StudentToIsland cardTest;
    private Game gameTest;

    @BeforeEach
    void setUp() {
        gameTest = new ExpertGame();
        cardTest = new StudentToIsland(gameTest);

        gameTest.addCharacterCard(cardTest);
        for (int i = 0; i < 12; i++) {
            gameTest.getTable().addGroupIsland(new BasicGroupIsland());
        }

        settingBag();
        settingCard();
    }

    @Test
    void setting() {
        assertEquals(1, cardTest.getCost());

        int num = 0;

        for (Colour colour : Colour.values()) {
            num += cardTest.getStudent(colour);
        }

        assertEquals(4, num);
    }

    @Test
    void setColourAndIsland() {
        Colour colourTest;
        colourTest = Colour.BLUE;

        int num = 0;

        for (Colour colour : Colour.values()) {
            num += cardTest.getStudent(colour);
        }
        assertEquals(4, num);

        for (Colour colour : Colour.values()) {
            if (cardTest.getStudent(colour) > 0) {
                colourTest = colour;
                break;
            }
        }

        int students;
        students = gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).getStudents(colourTest);
        cardTest.setColourAndIsland(colourTest, gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0));
        assertEquals(students + 1, gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).getStudents(colourTest));

        num = 0;

        for (Colour colour : Colour.values()) {
            num += cardTest.getStudent(colour);
        }
        assertEquals(4, num);

        for (Colour colour : Colour.values()) {
            if (cardTest.getStudent(colour) == 0) {
                colourTest = colour;
                break;
            }
        }

        Colour finalColourTest = colourTest;
        assertThrows(IllegalArgumentException.class, () -> cardTest.setColourAndIsland(finalColourTest, gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0)));
    }

    @Test
    void incrementCost() {
        int cost = cardTest.getCost();
        assertEquals(cost, cardTest.getCost());
        cardTest.incrementCost();
        assertEquals(cost + 1, cardTest.getCost());
    }

    @Test
    void setColour() {
        for (Colour colour : Colour.values()) {
            assertThrows(IllegalAccessError.class, () -> cardTest.setColour(colour));
        }
    }

    @Test
    public void setColourDiningRoomEntrance() {
        for (Colour colour : Colour.values()) {
            assertThrows(IllegalAccessError.class, () -> cardTest.setColourDiningRoomEntrance(colour, colour));
        }
    }

    @Test
    public void setColourCardEntrance() {
        for (Colour colour : Colour.values()) {
            assertThrows(IllegalAccessError.class, () -> cardTest.setColourCardEntrance(colour, colour));
        }
    }

    @Test
    void setGroupIsland() {
        for (int i = 0; i < gameTest.getTable().getNumberOfGroupIsland(); i++) {
            int finalI = i;
            assertThrows(IllegalAccessError.class, () -> cardTest.setGroupIsland(finalI));
        }
    }


    private void settingBag() {
        for (Colour colour : Colour.values()) {
            for (int i = 0; i < 2; i++) {
                gameTest.getTable().getBag().addStudentBag(colour);
            }
        }

        for (int i = 1; i < 12; i++) {
            if (i == 5) i++;
            gameTest.getTable().getGroupIslandByIndex(i).getIslandByIndex(0).addStudent(i, 0, gameTest.getTable().getBag().bagDrawStudent());
        }

        for (Colour colour : Colour.values()) {
            for (int i = 0; i < 24; i++) {
                gameTest.getTable().getBag().addStudentBag(colour);
            }
        }
    }

    private void settingCard() {
        for (int i = 0; i < gameTest.getNumberOfCharacterCard(); i++) {
            gameTest.getCharacterCardByIndex(i).setting();
        }
    }
}