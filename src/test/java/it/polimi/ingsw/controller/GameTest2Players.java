package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.model.player.BasicPlayer;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class GameTest2Players {

    private GameController gameController;

    @BeforeEach
    void setUp(){
        gameController = new GameController(false, 2);
    }

    @Test
    void gameTest2Players(){

        assertEquals(GamePhase.SETTING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.WAITING, gameController.getGame().getTurnPhase());

        gameController.addPlayer("Laura", Wizard.TYPE_2);
        gameController.addPlayer("Viola", Wizard.TYPE_2);

        assertEquals(GamePhase.SETTING, gameController.getGame().getGamePhase());

        assertEquals(1, gameController.getGame().getNumberOfPlayer());

        gameController.addPlayer("Viola", Wizard.TYPE_1);
        assertEquals(2, gameController.getGame().getNumberOfPlayer());

        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameController.getGame().getGamePhase());

        assertEquals(TurnPhase.WAITING, gameController.getGame().getTurnPhase());
        assertEquals(gameController.getGame().getPlayerByIndex(0), gameController.getGame().getCurrentPlayer());

        gameController.playAssistantCard(1, 0);

        assertNull(gameController.getGame().getPlayerByIndex(0).getCurrentAssistantCard());

        gameController.playAssistantCard(0, 4);
        assertEquals(gameController.getGame().getAssistantCard(4), gameController.getGame().getPlayerByIndex(0).getCurrentAssistantCard());
        assertFalse(gameController.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameController.getGame().getAssistantCard(4)));
        assertEquals(gameController.getGame().getCurrentPlayer(), gameController.getGame().getPlayerByIndex(1));

        for(int i =0; i < gameController.getGame().getNumberOfPlayer(); i++){
            assertEquals(gameController.getGame().getNumberStudentsEntrance(), gameController.getGame().getPlayerByIndex(i).getSchoolBoard().getNumberStudentsEntrance());
            assertEquals(gameController.getGame().getNumberOfTowersPerPlayer(), gameController.getGame().getPlayerByIndex(i).getSchoolBoard().getTowers());
        }
        gameController.playAssistantCard(1, 8);

        assertEquals(gameController.getGame().getCurrentPlayer(), gameController.getGame().getPlayerByIndex(0));

        int i = 0;
        while(i<3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(0, colour, 2,0);
                    i++;
                }
            }
        }

        assertEquals(4, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(4, gameController.getGame().getTable().getGroupIslandByIndex(2).getIslandByIndex(0).getStudents());


    }

}
