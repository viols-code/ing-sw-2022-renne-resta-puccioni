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
            assertEquals(i,gameController.getGame().getAssistantCard(i).getValue());
        }
    }

    @Test
    void getMotherNatureMovement() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }
}