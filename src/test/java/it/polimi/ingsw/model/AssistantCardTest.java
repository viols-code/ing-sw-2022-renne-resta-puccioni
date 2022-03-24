package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.player.BasicPlayer;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssistantCardTest {

    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController(false, 2);
        gameController.getGame().addPlayer(new BasicPlayer("sara", Wizard.TYPE_1));
        gameController.getGame().addPlayer(new BasicPlayer("laura",Wizard.TYPE_2));
    }

    @Test
    void getValue() {
        for(int i=1;i<=10;i++){
            assertEquals(i,gameController.getGame().getAssistantCard(i-1).getValue());
        }
    }

    @Test
    void getMotherNatureMovement() {
        for(int i=1;i<=10;i+=2){
            assertEquals(i,gameController.getGame().getAssistantCard(i-1).getValue());
            assertEquals(i+1,gameController.getGame().getAssistantCard(i).getValue());
        }
    }

    @Test
    void testEquals() {
        assertEquals(gameController.getGame().getAssistantCard(0),gameController.getGame().getAssistantCard(0));
        assertNotEquals(gameController.getGame().getAssistantCard(0),gameController.getGame().getAssistantCard(1));
    }

    @Test
    void testHashCode() {
        assertEquals(gameController.getGame().getAssistantCard(0).hashCode(),gameController.getGame().getAssistantCard(0).hashCode());
        assertNotEquals(gameController.getGame().getAssistantCard(0).hashCode(),gameController.getGame().getAssistantCard(1).hashCode());
    }
}