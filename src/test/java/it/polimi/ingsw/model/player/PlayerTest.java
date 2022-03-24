package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.AssistantCard;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController(false, 2);
        gameController.getGame().addPlayer(new BasicPlayer("sara",Wizard.TYPE_1));
        gameController.getGame().addPlayer(new BasicPlayer("laura",Wizard.TYPE_2));
    }


    @Test
    void addAssistantCard() {
        AssistantCard card;
        for(int i=0;i<10;i++){
            card = gameController.getGame().getAssistantCard(i);
            gameController.getGame().getPlayerByIndex(0).addAssistantCard(card);
            assertTrue(gameController.getGame().getPlayerByIndex(0).isAssistantCardPresent(card));
        }


    }

    @Before
    public void setUpRemoveAssistantCard(){
        AssistantCard card;
        for(int i=0;i<10;i++){
            card = gameController.getGame().getAssistantCard(i);
            gameController.getGame().getPlayerByIndex(0).addAssistantCard(card);
        }
    }

    @Test
    void removeAssistantCard() {
        AssistantCard card;
        for(int i=0;i<10;i++){
            card = gameController.getGame().getAssistantCard(i);
            gameController.getGame().getPlayerByIndex(0).removeAssistantCard(card);
            assertFalse(gameController.getGame().getPlayerByIndex(0).isAssistantCardPresent(card));
        }
    }
    @Before
    public void setUpSetCurrentAssistantCard(){
        AssistantCard card;
        for(int i=0;i<10;i++){
            card = gameController.getGame().getAssistantCard(i);
            gameController.getGame().getPlayerByIndex(0).addAssistantCard(card);
        }
    }

    @Test
    void setCurrentAssistantCard() {
        AssistantCard card;
        for(int i=0;i<10;i++){
            card = gameController.getGame().getAssistantCard(i);
            gameController.getGame().getPlayerByIndex(0).setCurrentAssistantCard(card);
            assertEquals(card, gameController.getGame().getPlayerByIndex(0).getCurrentAssistantCard());
            assertFalse(gameController.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameController.getGame().getAssistantCard(i)));
        }
    }

    @Test
    void setHasAlreadyPlayed() {
        gameController.getGame().getPlayerByIndex(0).setHasAlreadyPlayed(true);
        assertTrue(gameController.getGame().getPlayerByIndex(0).getHasAlreadyPlayed());
    }

    @Test
    void getCoin() {
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getPlayerByIndex(0).getCoins());
    }

    @Test
    void addCoins() {
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getPlayerByIndex(0).addCoins(10));
    }

    @Test
    void removeCoins() {
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getPlayerByIndex(0).removeCoins(10));
    }
}
