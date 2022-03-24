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
    void getNickname(){
        assertEquals("sara",gameController.getGame().getPlayerByIndex(0).getNickname());
        assertEquals("laura",gameController.getGame().getPlayerByIndex(1).getNickname());
    }

    @Test
    void getWizard(){
        assertEquals(Wizard.TYPE_1,gameController.getGame().getPlayerByIndex(0).getWizard());
        assertEquals(Wizard.TYPE_2,gameController.getGame().getPlayerByIndex(1).getWizard());
    }

    @Test
    void isAssistantCardPresent(){
        gameController.getGame().getPlayerByIndex(0).addAssistantCard(gameController.getGame().getAssistantCard(1));
        assertTrue(gameController.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameController.getGame().getAssistantCard(1)));
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
    public void setUpGetCurrentAssistantCard(){
        AssistantCard card;
        for(int i=0;i<10;i++){
            card = gameController.getGame().getAssistantCard(i);
            gameController.getGame().getPlayerByIndex(0).addAssistantCard(card);
        }
    }

    @Test
    void getCurrentAssistantCard(){
        AssistantCard card;
        for(int i=0;i<10;i++){
            card = gameController.getGame().getAssistantCard(i);
            gameController.getGame().getPlayerByIndex(0).setCurrentAssistantCard(card);
            assertEquals(card,gameController.getGame().getPlayerByIndex(0).getCurrentAssistantCard());
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
    void getSchoolBoard(){
        assertEquals(gameController.getGame().getPlayerByIndex(0).schoolBoard,gameController.getGame().getPlayerByIndex(0).getSchoolBoard());
        assertEquals(gameController.getGame().getPlayerByIndex(0).schoolBoard,gameController.getGame().getPlayerByIndex(0).getSchoolBoard());
    }

    @Test
    void getHasAlreadyPlayed(){
        gameController.getGame().getPlayerByIndex(0).setHasAlreadyPlayed(true);
        assertTrue(gameController.getGame().getPlayerByIndex(0).getHasAlreadyPlayed());
        assertFalse(gameController.getGame().getPlayerByIndex(1).getHasAlreadyPlayed());
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

    @Test
    void equals(){
        assertFalse(gameController.getGame().getPlayerByIndex(0).equals(gameController.getGame().getPlayerByIndex(1)));
    }

    @Test
    void hashCodeTest(){
        assertNotEquals(gameController.getGame().getPlayerByIndex(0).hashCode(),gameController.getGame().getPlayerByIndex(1).hashCode());
    }
}
