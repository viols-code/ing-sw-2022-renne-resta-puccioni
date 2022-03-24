package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.table.island.BasicGroupIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentToIslandTest {
    private StudentToIsland cardTest;
    private Game gameTest;

    @BeforeEach
    void setUp(){
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

        for(Colour colour: Colour.values()){
            num += cardTest.getStudent(colour);
        }

        assertEquals(4, num);
    }

    @Test
    void setColourAndIsland() {
        Colour colourTest;
        colourTest = Colour.BLUE;

        int num = 0;

        for(Colour colour: Colour.values()){
            num += cardTest.getStudent(colour);
        }
        assertEquals(4, num);

        for(Colour colour: Colour.values()){
            if(cardTest.getStudent(colour) > 0){
                colourTest = colour;
                break;
            }
        }

        int students = 0;
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).getStudents(colourTest);
        cardTest.setColourAndIsland(colourTest, gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0));
        assertEquals(students + 1, gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).getStudents(colourTest));

        num = 0;

        for(Colour colour: Colour.values()){
            num += cardTest.getStudent(colour);
        }
        assertEquals(4, num);

        for(Colour colour: Colour.values()){
            if(cardTest.getStudent(colour) == 0){
                colourTest = colour;
                break;
            }
        }

        Colour finalColourTest = colourTest;
        assertThrows(IllegalArgumentException.class, () -> cardTest.setColourAndIsland(finalColourTest, gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0)));
    }


    private void settingBag() {
        for (Colour colour : Colour.values()) {
            for (int i = 0; i < 2; i++) {
                gameTest.getTable().getBag().addStudentBag(colour);
            }
        }

        for (int i = 1; i < 12; i++) {
            if (i == 5) i++;
            gameTest.getTable().getGroupIslandByIndex(i).getIslandByIndex(0).addStudent(gameTest.getTable().getBag().bagDrawStudent());
        }

        for (Colour colour : Colour.values()) {
            for (int i = 0; i < 24; i++) {
                gameTest.getTable().getBag().addStudentBag(colour);
            }
        }
    }

    private void settingCard() {
        for (int i = 0; i < gameTest.getNumberOfCharacterCard(); i++) {
            gameTest.getCharacterCardsByIndex(i).setting();
        }
    }
}