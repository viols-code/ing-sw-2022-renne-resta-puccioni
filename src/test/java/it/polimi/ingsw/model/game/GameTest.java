package it.polimi.ingsw.model.game;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.card.CharacterCard;
import it.polimi.ingsw.model.card.NoColour;
import it.polimi.ingsw.model.player.BasicPlayer;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController(false, 3);
    }

    @Test
    void addPlayer() {
        Player player1 = new BasicPlayer("Laura", Wizard.TYPE_1);
        gameController.getGame().addPlayer(player1);
        assertEquals(1, gameController.getGame().getNumberOfPlayer());
        assertEquals("Laura", gameController.getGame().getPlayerByIndex(0).getNickname());
        assertEquals(Wizard.TYPE_1, gameController.getGame().getPlayerByIndex(0).getWizard());

        Player player2 = new BasicPlayer("Sara", Wizard.TYPE_3);
        gameController.getGame().addPlayer(player2);
        assertEquals(2, gameController.getGame().getNumberOfPlayer());
        assertEquals("Sara", gameController.getGame().getPlayerByIndex(1).getNickname());
        assertEquals(Wizard.TYPE_3, gameController.getGame().getPlayerByIndex(1).getWizard());

        Player player3 = new BasicPlayer("Viola", Wizard.TYPE_2);
        gameController.getGame().addPlayer(player3);
        assertEquals(3, gameController.getGame().getNumberOfPlayer());
        assertEquals("Viola", gameController.getGame().getPlayerByIndex(2).getNickname());
        assertEquals(Wizard.TYPE_2, gameController.getGame().getPlayerByIndex(2).getWizard());
    }

    @Test
    void removePlayer() {

        Player player1 = new BasicPlayer("Laura", Wizard.TYPE_1);
        gameController.getGame().addPlayer(player1);
        Player player2 = new BasicPlayer("Sara", Wizard.TYPE_3);
        gameController.getGame().addPlayer(player2);
        gameController.getGame().removePlayer(player1);

        assertEquals(1, gameController.getGame().getNumberOfPlayer());
        assertEquals(player2, gameController.getGame().getPlayerByIndex(0));

    }

    @Test
    void nextPlayerClockwise() {
        Player player1 = new BasicPlayer("Viola", Wizard.TYPE_2);
        Player player2 = new BasicPlayer("Laura", Wizard.TYPE_3);
        Player player3 = new BasicPlayer("Sara", Wizard.TYPE_3);
        gameController.getGame().addPlayer(player1);
        gameController.getGame().addPlayer(player2);
        gameController.getGame().addPlayer(player3);
        gameController.getGame().setCurrentPlayer(player1);
        assertEquals(player2, gameController.getGame().nextPlayerClockwise());
        gameController.getGame().setCurrentPlayer(player3);
        assertEquals(player1, gameController.getGame().nextPlayerClockwise());

    }

    @Test
    void nextPlayerTurn() {

        Player player1 = new BasicPlayer("Viola", Wizard.TYPE_2);
        Player player2 = new BasicPlayer("Laura", Wizard.TYPE_3);
        Player player3 = new BasicPlayer("Sara", Wizard.TYPE_4);

        gameController.getGame().addPlayer(player1);
        gameController.getGame().addPlayer(player2);
        gameController.getGame().addPlayer(player3);

        AssistantCard card1 = new AssistantCard(6, 3);
        AssistantCard card2 = new AssistantCard(10, 5);
        AssistantCard card3 = new AssistantCard(7, 4);

        gameController.getGame().getPlayerByIndex(0).setCurrentAssistantCard(card1);
        gameController.getGame().getPlayerByIndex(1).setCurrentAssistantCard(card2);
        gameController.getGame().getPlayerByIndex(2).setCurrentAssistantCard(card3);

        gameController.getGame().getPlayerByIndex(0).setHasAlreadyPlayed(true);

        assertEquals(player3, gameController.getGame().nextPlayerTurn());

        gameController.getGame().getPlayerByIndex(2).setHasAlreadyPlayed(true);

        assertEquals(player2, gameController.getGame().nextPlayerTurn());

    }

    @Test
    void isCurrentPlayer(){

        Player player1 = new BasicPlayer("Laura", Wizard.TYPE_1);
        Player player2 = new BasicPlayer("Sara", Wizard.TYPE_2);
        gameController.getGame().setCurrentPlayer(player1);
        assertTrue(gameController.getGame().isCurrentPlayer(player1));
        assertFalse(gameController.getGame().isCurrentPlayer(player2));

    }

    @Test
    void incrementRound(){
        gameController.getGame().incrementRound();
        assertEquals(2, gameController.getGame().getRound());
        gameController.getGame().incrementRound();
        gameController.getGame().incrementRound();
        gameController.getGame().incrementRound();
        gameController.getGame().incrementRound();
        gameController.getGame().incrementRound();
        gameController.getGame().incrementRound();
        assertEquals(8, gameController.getGame().getRound());
        gameController.getGame().incrementRound();
        gameController.getGame().incrementRound();
        assertEquals(10, gameController.getGame().getRound());
    }

    @Test
    void addCharacterCard(){

        CharacterCard card1 = new NoColour(gameController.getGame());
        IllegalAccessError exception = assertThrows(IllegalAccessError.class, () -> gameController.getGame().addCharacterCard(card1));

        String expectedMessage = "This is for the Expert Mode";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void hasProtectedIslandCard(){

        IllegalAccessError exception = assertThrows(IllegalAccessError.class, () -> gameController.getGame().hasProtectIslandCard());

        String expectedMessage = "This is for the Expert Mode";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}