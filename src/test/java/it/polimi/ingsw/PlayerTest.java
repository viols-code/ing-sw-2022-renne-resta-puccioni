package it.polimi.ingsw;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.game.BasicGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.model.player.BasicPlayer;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player1;
    private Game game;

    @BeforeEach
    void setUp() {
        player1 = new BasicPlayer("Sara", Wizard.TYPE_2);
        game = new BasicGame();
    }

    @Test
    void isAssistantCardPresent(){

    }

    @Test
    void addAssistantCard(){
        AssistantCard card= game.getAssistantCard(1);
        player1.addAssistantCard(card);
        assertTrue(player1.isAssistantCardPresent(card));
    }

    @Test
    void removeAssistantCard() {
        AssistantCard card= game.getAssistantCard(1);
        player1.addAssistantCard(card);
        player1.removeAssistantCard(card);
        assertFalse(player1.isAssistantCardPresent(card));
    }

    @Test
    void setCurrentAssistantCard() {
        AssistantCard card= game.getAssistantCard(1);
        player1.addAssistantCard(card);
        player1.setCurrentAssistantCard(card);
        assertEquals(card,player1.getCurrentAssistantCard());
    }


    @Test
    void addCoins() {
        for (int i = 1; i <= 10; i++) {
            player1.addCoins(i);
            assertEquals(i + 1, player1.getCoins());
            player1.removeCoins(i);
        }
    }

    @Test
    void removeCoins() {
        player1.addCoins(10);
        for (int i = 1; i <= 10; i++) {
            player1.removeCoins(i);
            assertEquals(10 - i + 1, player1.getCoins());
            player1.addCoins(i);
        }

    }
}