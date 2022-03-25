package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.ExpertPlayer;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.table.island.BasicGroupIsland;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StudentToEntranceTest {

    private StudentToEntrance cardTest;
    private Game gameTest;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {
        gameTest = new ExpertGame();
        cardTest = new StudentToEntrance(gameTest);

        gameTest.addCharacterCard(cardTest);
        for (int i = 0; i < 12; i++) {
            gameTest.getTable().addGroupIsland(new BasicGroupIsland());
        }

        settingBag();
        settingCard();

        player1 = new ExpertPlayer("Viola", Wizard.TYPE_3);
        player2 = new ExpertPlayer("Laura", Wizard.TYPE_1);

        gameTest.addPlayer(player1);
        gameTest.addPlayer(player2);

        player1.getSchoolBoard().addTower(8);
        player2.getSchoolBoard().addTower(8);

    }

    @Test
    void setting() {

        assertEquals(1, cardTest.getCost());

        int numStudentsOnCard = 0;

        for (Colour colour : Colour.values()) {
            numStudentsOnCard += cardTest.getStudent(colour);
        }

        assertEquals(6, numStudentsOnCard);
    }

    @Test
    void incrementCost(){
        int cost = cardTest.getCost();
        assertEquals(cost, cardTest.getCost());
        cardTest.incrementCost();
        assertEquals(cost + 1, cardTest.getCost());
    }

    @Test
    void setColour(){
        for(Colour colour: Colour.values()){
            assertThrows(IllegalAccessError.class, () -> cardTest.setColour(colour));
        }
    }

    @Test
    void setColourAndIsland() {
        for(Colour colour: Colour.values()){
            assertThrows(IllegalAccessError.class, () -> cardTest.setColourAndIsland(colour, gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0)));
        }
    }

    @Test
    public void setColourDiningRoomEntrance() {
        for(Colour colour: Colour.values()){
            assertThrows(IllegalAccessError.class, () -> cardTest.setColourDiningRoomEntrance(colour, colour));
        }
    }

    @Test
    public void setColourCardEntrance(){

        Colour colourCard;
        colourCard = Colour.RED;
        Colour colourEntrance;
        colourEntrance = Colour.BLUE;

        gameTest.setCurrentPlayer(player1);

        for (Colour colour : Colour.values()) {
            if (cardTest.getStudent(colourCard) > 0) {
                colourCard = colour;
                break;
            }
        }

        for (Colour colour : Colour.values()) {
            if (gameTest.getPlayerByIndex(0).getSchoolBoard().getEntrance(colourEntrance) > 0) {
                colourEntrance = colour;
                break;
            }
        }

        int studentsInEntrance;
        studentsInEntrance = gameTest.getPlayerByIndex(0).getSchoolBoard().getEntrance(colourCard);

        int studentsOnCard;
        studentsOnCard = cardTest.getStudent(colourEntrance);

        cardTest.setColourCardEntrance(colourCard, colourEntrance);

        assertEquals(studentsInEntrance + 1, gameTest.getPlayerByIndex(0).getSchoolBoard().getEntrance(colourCard));
        assertEquals(studentsOnCard + 1, cardTest.getStudent(colourEntrance));

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
