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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MotherNatureMovementTest {
    private MotherNatureMovement cardTest;
    private Game gameTest;
    private Player player1;

    @BeforeEach
    void setUp() {
        gameTest = new ExpertGame();
        cardTest = new MotherNatureMovement(gameTest);
        player1 = new ExpertPlayer("Viola", Wizard.TYPE_4);

        gameTest.addPlayer(player1);
        player1.setCurrentAssistantCard(gameTest.getAssistantCard(0));

        gameTest.addCharacterCard(cardTest);
        for (int i = 0; i < 12; i++) {
            gameTest.getTable().addGroupIsland(new BasicGroupIsland());
        }

        settingBag();
        settingCard();
    }

    @Test
    void checkMotherNatureMovement() {
        assertEquals(1, gameTest.getAssistantCard(0).getMotherNatureMovement());
        assertEquals(gameTest.getAssistantCard(0), player1.getCurrentAssistantCard());
        assertTrue(cardTest.checkMotherNatureMovement(0, 3));
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
        for(Colour colour: Colour.values()){
            assertThrows(IllegalAccessError.class, () -> cardTest.setColourCardEntrance(colour, colour));
        }
    }

    @Test
    void setGroupIsland() {
        for(int i = 0; i < gameTest.getTable().getNumberOfGroupIsland(); i++){
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