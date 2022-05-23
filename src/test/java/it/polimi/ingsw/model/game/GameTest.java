package it.polimi.ingsw.model.game;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.player.BasicPlayer;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.server.Lobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController(false, 3, new Lobby());
        gameController.setUp();
    }

    @Test
    void addPlayer() {
        assertEquals(gameController.getGame().getActiveCharacterCard(), gameController.getGame().getBasicState());
        Player player1 = new BasicPlayer("Laura", Wizard.TYPE_1, TowerColour.WHITE);
        gameController.getGame().addPlayer(player1);
        assertEquals(1, gameController.getGame().getNumberOfPlayer());
        assertEquals("Laura", gameController.getGame().getPlayerByIndex(0).getNickname());
        assertEquals(Wizard.TYPE_1, gameController.getGame().getPlayerByIndex(0).getWizard());

        Player player2 = new BasicPlayer("Sara", Wizard.TYPE_3, TowerColour.BLACK);
        gameController.getGame().addPlayer(player2);
        assertEquals(2, gameController.getGame().getNumberOfPlayer());
        assertEquals("Sara", gameController.getGame().getPlayerByIndex(1).getNickname());
        assertEquals(Wizard.TYPE_3, gameController.getGame().getPlayerByIndex(1).getWizard());

        Player player3 = new BasicPlayer("Viola", Wizard.TYPE_2, TowerColour.GREY);
        gameController.getGame().addPlayer(player3);
        assertEquals(3, gameController.getGame().getNumberOfPlayer());
        assertEquals("Viola", gameController.getGame().getPlayerByIndex(2).getNickname());
        assertEquals(Wizard.TYPE_2, gameController.getGame().getPlayerByIndex(2).getWizard());

        assertThrows(IllegalArgumentException.class, () -> gameController.getGame().getIndexOfPlayer(new BasicPlayer("Gio", Wizard.TYPE_2, TowerColour.WHITE)));
    }

    @Test
    void removePlayer() {
        assertEquals(gameController.getGame().getActiveCharacterCard(), gameController.getGame().getBasicState());
        Player player1 = new BasicPlayer("Laura", Wizard.TYPE_1, TowerColour.WHITE);
        gameController.getGame().addPlayer(player1);
        Player player2 = new BasicPlayer("Sara", Wizard.TYPE_3, TowerColour.BLACK);
        gameController.getGame().addPlayer(player2);
        gameController.getGame().removePlayer(player1);
        assertEquals(gameController.getGame().getActiveCharacterCard(), gameController.getGame().getBasicState());
        assertEquals(1, gameController.getGame().getNumberOfPlayer());
        assertEquals(player2, gameController.getGame().getPlayerByIndex(0));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> gameController.getGame().removePlayer(player1));

        String expectedMessage = "This player is not in the game";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void nextPlayerClockwise() {
        Player player1 = new BasicPlayer("Viola", Wizard.TYPE_2, TowerColour.WHITE);
        Player player2 = new BasicPlayer("Laura", Wizard.TYPE_3, TowerColour.BLACK);
        Player player3 = new BasicPlayer("Sara", Wizard.TYPE_3, TowerColour.GREY);
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

        Player player1 = new BasicPlayer("Viola", Wizard.TYPE_2, TowerColour.WHITE);
        Player player2 = new BasicPlayer("Laura", Wizard.TYPE_3, TowerColour.BLACK);
        Player player3 = new BasicPlayer("Sara", Wizard.TYPE_4, TowerColour.GREY);

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
    void isCurrentPlayer() {
        Player player1 = new BasicPlayer("Laura", Wizard.TYPE_1, TowerColour.WHITE);
        Player player2 = new BasicPlayer("Sara", Wizard.TYPE_2, TowerColour.BLACK);
        gameController.getGame().setCurrentPlayer(player1);
        assertTrue(gameController.getGame().isCurrentPlayer(player1));
        assertFalse(gameController.getGame().isCurrentPlayer(player2));

    }

    @Test
    void getFirstPlayerTurn() {
        Player player1 = new BasicPlayer("Laura", Wizard.TYPE_1, TowerColour.WHITE);
        gameController.getGame().addPlayer(player1);
        gameController.getGame().setFirstPlayerTurn(player1);
        assertEquals(player1, gameController.getGame().getFirstPlayerTurn());
    }

    @Test
    void incrementRound() {
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
    void getCharacterCardByIndex() {
        IllegalAccessError exception = assertThrows(IllegalAccessError.class, () -> gameController.getGame().getCharacterCardByIndex(0));

        String expectedMessage = "This is for the Expert Mode";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void getNumberOfCharacterCard() {
        IllegalAccessError exception = assertThrows(IllegalAccessError.class, () -> gameController.getGame().getNumberOfCharacterCard());

        String expectedMessage = "This is for the Expert Mode";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void addCharacterCard() {
        CharacterCard card1 = new NoColour(gameController.getGame());
        IllegalAccessError exception = assertThrows(IllegalAccessError.class, () -> gameController.getGame().addCharacterCard(card1));

        String expectedMessage = "This is for the Expert Mode";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void setActiveCharacterCard() {
        CharacterCard card1 = new NoColour(gameController.getGame());
        IllegalAccessError exception = assertThrows(IllegalAccessError.class, () -> gameController.getGame().setActiveCharacterCard(card1));

        String expectedMessage = "This is for the Expert Mode";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getNumberOfTowersPerPlayer() {
        gameController.getGame().setNumberOfTowersPerPlayer(6);
        assertEquals(6, gameController.getGame().getNumberOfTowersPerPlayer());
    }

    @Test
    void getNumberStudentsEntrance() {
        gameController.getGame().setNumberStudentsEntrance(9);
        assertEquals(9, gameController.getGame().getNumberStudentsEntrance());
    }

    @Test
    void getGamePhase() {
        gameController.getGame().setGamePhase(GamePhase.SETTING);
        assertEquals(GamePhase.SETTING, gameController.getGame().getGamePhase());

        gameController.getGame().setGamePhase(GamePhase.PLAYING);
        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());

    }

    @Test
    void getTurnPhase() {
        gameController.getGame().setTurnPhase(TurnPhase.MOVE_STUDENT);
        assertEquals(TurnPhase.MOVE_STUDENT, gameController.getGame().getTurnPhase());

        gameController.getGame().setTurnPhase(TurnPhase.CHOOSE_CLOUD_TILE);
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());

    }

    @Test
    void getWinner() {

        Player player1 = new BasicPlayer("Laura", Wizard.TYPE_2, TowerColour.WHITE);
        gameController.getGame().setWinner(player1);
        assertEquals(player1, gameController.getGame().getWinner());
    }

    @Test
    void getHasPlayedCharacterCard() {
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getHasPlayedCharacterCard());
    }

    @Test
    void setHasPlayedCharacterCard() {
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().setHasPlayedCharacterCard(true));
    }

    @Test
    void getCoins() {
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getCoins());
    }

    @Test
    void setCoins() {
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().setCoins(1));
    }


    @Test
    void hasProtectedIslandCard() {

        IllegalAccessError exception = assertThrows(IllegalAccessError.class, () -> gameController.getGame().hasProtectIslandCard());

        String expectedMessage = "This is for the Expert Mode";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getAssistantCard() {
        assertThrows(IllegalArgumentException.class, () -> gameController.getGame().getAssistantCard(10));
    }

    @Test
    void getCharacterCardIndex() {
        final CharacterCard card = new ProtectIsland(gameController.getGame());
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getCharacterCardIndex(card));

        final CharacterCard card1 = new NoColour(gameController.getGame());
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getCharacterCardIndex(card1));

        final CharacterCard card2 = new ExchangeEntranceDiningRoom(gameController.getGame());
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getCharacterCardIndex(card2));

        final CharacterCard card3 = new NoColour(gameController.getGame());
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getCharacterCardIndex(card3));

        final CharacterCard card4 = new IslandInfluence(gameController.getGame());
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getCharacterCardIndex(card4));

        final CharacterCard card5 = new MotherNatureMovement(gameController.getGame());
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getCharacterCardIndex(card5));

        final CharacterCard card6 = new NoTower(gameController.getGame());
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getCharacterCardIndex(card6));

        final CharacterCard card7 = new StudentToDiningRoom(gameController.getGame());
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getCharacterCardIndex(card7));

        final CharacterCard card8 = new StudentToEntrance(gameController.getGame());
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getCharacterCardIndex(card8));

        final CharacterCard card9 = new StudentToIsland(gameController.getGame());
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getCharacterCardIndex(card9));

        final CharacterCard card10 = new ThreeStudent(gameController.getGame());
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getCharacterCardIndex(card10));

        final CharacterCard card11 = new TwoPoints(gameController.getGame());
        assertThrows(IllegalAccessError.class, () -> gameController.getGame().getCharacterCardIndex(card11));
    }

}