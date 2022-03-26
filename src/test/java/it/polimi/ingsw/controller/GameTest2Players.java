package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.model.player.BasicPlayer;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
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

        //PRIMO TURNO

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
        assertEquals(7, gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getNumberStudentsEntrance());

        int num = 0;
        for(Colour colour: Colour.values()){
            num += gameController.getGame().getTable().getGroupIslandByIndex(2).getNumberStudent(colour);
        }

        assertEquals(4, num);
        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameController.getGame().getTurnPhase());

        gameController.moveMotherNature(0,2);

        assertEquals(2, gameController.getGame().getTable().getMotherNaturePosition());
        assertNull(gameController.getGame().getTable().getGroupIslandByIndex(2).getInfluence());

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());

        gameController.chooseCloudTile(0, 1);
        assertEquals(7, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameController.getGame().getTurnPhase());
        assertEquals(1, gameController.getGame().getTable().getNumberOfCloudTile());
        assertEquals(gameController.getGame().getCurrentPlayer(), gameController.getGame().getPlayerByIndex(1));

        int j = 0;
        while (j < 3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToDiningRoom(1, colour);
                    j++;
                }
            }
        }

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameController.getGame().getTurnPhase());

        assertEquals(4, gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(7, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());

        gameController.moveMotherNature(1,3);

        assertEquals(5, gameController.getGame().getTable().getMotherNaturePosition());

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());

        /*gameController.chooseCloudTile(1, 1);
        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());*/

        gameController.chooseCloudTile(1, 0);
        assertEquals(7, gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(2, gameController.getGame().getTable().getNumberOfCloudTile());
        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.WAITING, gameController.getGame().getTurnPhase());

        assertEquals(gameController.getGame().getCurrentPlayer(), gameController.getGame().getPlayerByIndex(0));


        //SECONDO TURNO

        gameController.playAssistantCard(0,9);
        gameController.playAssistantCard(1,6);

        i = 0;
        while(i<3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(1, colour, 9,0);
                    i++;
                }
            }
        }

        gameController.moveMotherNature(1,1);

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());

        gameController.chooseCloudTile(1, 1);

        i = 0;
        while(i<3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(0, colour, 4,0);
                    i++;
                }
            }
        }

        gameController.moveMotherNature(0,2);

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());


        gameController.chooseCloudTile(0, 0);

        //TERZO TURNO

        gameController.playAssistantCard(1,3);
        gameController.playAssistantCard(0,5);

        i = 0;
        while(i<3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(1, colour, 0,0);
                    i++;
                }
            }
        }

        gameController.moveMotherNature(1,2);

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());

        gameController.chooseCloudTile(1, 1);

        i = 0;
        while(i<3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(0, colour, 2,0);
                    i++;
                }
            }
        }

        gameController.moveMotherNature(0,1);

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());


        gameController.chooseCloudTile(0, 0);

        //QUARTO TURNO

        gameController.playAssistantCard(1,7);
        gameController.playAssistantCard(0,1);

        i = 0;
        while(i<3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(0, colour, 2,0);
                    i++;
                }
            }
        }

        gameController.moveMotherNature(0,1);

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());

        gameController.chooseCloudTile(0, 1);

        i = 0;
        while(i<3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(1, colour, 3,0);
                    i++;
                }
            }
        }

        gameController.moveMotherNature(1,1);

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());


        gameController.chooseCloudTile(1, 0);

        //QUINTO TURNO

        gameController.playAssistantCard(0,8);
        gameController.playAssistantCard(1,2);

        i = 0;
        while(i<3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(1, colour, 4,0);
                    i++;
                }
            }
        }

        gameController.moveMotherNature(1,2);

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());

        gameController.chooseCloudTile(1, 1);

        i = 0;
        while(i<3) {
            for (Colour colour : Colour.values()) {
                if (gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0) {
                    gameController.moveStudentToIsland(0, colour, 6,0);
                    i++;
                }
            }
        }

        gameController.moveMotherNature(0,1);

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());


        gameController.chooseCloudTile(0, 0);


    }

}
