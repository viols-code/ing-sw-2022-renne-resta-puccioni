package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BasicGameThreePlayerTest {
    private GameController gameController;

    @BeforeEach
    void setUp(){
        gameController = new GameController(false, 3);
    }

    @Test
    void threePlayerGame(){
        // start of the game
        assertEquals(GamePhase.SETTING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.WAITING, gameController.getGame().getTurnPhase());
        assertEquals(4, gameController.getGame().getStudentNumberMovement());
        assertEquals(9, gameController.getGame().getNumberStudentsEntrance());
        assertEquals(6, gameController.getGame().getNumberOfTowersPerPlayer());
        assertNull(gameController.getGame().getCurrentPlayer());
        gameController.addPlayer("Viola", Wizard.TYPE_1);
        assertEquals(1, gameController.getGame().getNumberOfPlayer());
        assertEquals("Viola", gameController.getGame().getPlayerByIndex(0).getNickname());
        assertEquals(Wizard.TYPE_1, gameController.getGame().getPlayerByIndex(0).getWizard());
        gameController.addPlayer("Laura", Wizard.TYPE_1);
        assertEquals(1, gameController.getGame().getNumberOfPlayer());
        gameController.addPlayer("Laura", Wizard.TYPE_3);
        assertEquals("Laura", gameController.getGame().getPlayerByIndex(1).getNickname());
        assertEquals(Wizard.TYPE_3, gameController.getGame().getPlayerByIndex(1).getWizard());
        assertEquals(2, gameController.getGame().getNumberOfPlayer());
        gameController.addPlayer("Sara", Wizard.TYPE_1);
        assertEquals(GamePhase.SETTING, gameController.getGame().getGamePhase());
        assertEquals(2, gameController.getGame().getNumberOfPlayer());
        gameController.addPlayer("Sara", Wizard.TYPE_4);
        assertEquals(3, gameController.getGame().getNumberOfPlayer());
        assertEquals("Sara", gameController.getGame().getPlayerByIndex(2).getNickname());
        assertEquals(Wizard.TYPE_4, gameController.getGame().getPlayerByIndex(2).getWizard());
        gameController.addPlayer("Elisa", Wizard.TYPE_2);
        assertEquals(3, gameController.getGame().getNumberOfPlayer());
        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.WAITING, gameController.getGame().getTurnPhase());
        assertEquals(gameController.getGame().getCurrentPlayer(), gameController.getGame().getPlayerByIndex(0));
        for(int i = 0; i < gameController.getGame().getNumberOfPlayer(); i++) {
            assertEquals(gameController.getGame().getNumberStudentsEntrance(), gameController.getGame().getPlayerByIndex(i).getSchoolBoard().getNumberStudentsEntrance());
            assertEquals(gameController.getGame().getNumberOfTowersPerPlayer(), gameController.getGame().getPlayerByIndex(i).getSchoolBoard().getTowers());
        }

        // playing AssistantCard first turn
        gameController.playAssistantCard(1, 0);
        assertNull(gameController.getGame().getPlayerByIndex(1).getCurrentAssistantCard());
        gameController.playAssistantCard(0, 4);
        assertEquals(gameController.getGame().getAssistantCard(4), gameController.getGame().getPlayerByIndex(0).getCurrentAssistantCard());
        assertFalse(gameController.getGame().getPlayerByIndex(0).isAssistantCardPresent(gameController.getGame().getAssistantCard(4)));
        assertEquals(gameController.getGame().getCurrentPlayer(), gameController.getGame().getPlayerByIndex(1));
        gameController.playAssistantCard(1, 7);
        gameController.playAssistantCard(2, 2);
        assertEquals(gameController.getGame().getCurrentPlayer(), gameController.getGame().getPlayerByIndex(2));
        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameController.getGame().getTurnPhase());

        // first turn
        int i = 0;
        while(i < 4){
            for(Colour colour: Colour.values()) {
                if(gameController.getGame().getPlayerByIndex(2).getSchoolBoard().getEntrance(colour) > 0){
                    gameController.moveStudentToIsland(2, colour, 1, 0);
                    i++;
                }
            }
        }

        assertEquals(5, gameController.getGame().getPlayerByIndex(2).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(9, gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(9, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());

        int num = 0;
        for(Colour colour: Colour.values()){
            num += gameController.getGame().getTable().getGroupIslandByIndex(1).getNumberStudent(colour);
        }
        assertEquals(5, num);
        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameController.getGame().getTurnPhase());

        gameController.moveMotherNature(2, 1);
        assertEquals(1, gameController.getGame().getTable().getMotherNaturePosition());
        assertNull(gameController.getGame().getTable().getGroupIslandByIndex(1).getInfluence());

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());

        gameController.chooseCloudTile(2, 2);
        assertEquals(9, gameController.getGame().getPlayerByIndex(2).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameController.getGame().getTurnPhase());
        assertEquals(2, gameController.getGame().getTable().getNumberOfCloudTile());
        assertEquals(gameController.getGame().getCurrentPlayer(), gameController.getGame().getPlayerByIndex(0));


        i = 0;
        while(i < 4){
            for(Colour colour: Colour.values()){
                if(gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0){
                    gameController.moveStudentToDiningRoom(0, colour);
                    i++;
                }
            }
        }

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameController.getGame().getTurnPhase());
        assertEquals(5, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(9, gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(9, gameController.getGame().getPlayerByIndex(2).getSchoolBoard().getNumberStudentsEntrance());

        gameController.moveMotherNature(0, 4);
        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameController.getGame().getTurnPhase());
        gameController.moveMotherNature(0, 3);
        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());

        gameController.chooseCloudTile(0, 2);
        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());

        gameController.chooseCloudTile(0, 1);
        assertEquals(1, gameController.getGame().getTable().getNumberOfCloudTile());
        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameController.getGame().getTurnPhase());
        assertEquals(gameController.getGame().getCurrentPlayer(), gameController.getGame().getPlayerByIndex(1));


        i = 0;
        while(i < 4){
            for(Colour colour: Colour.values()){
                if(gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0){
                    gameController.moveStudentToDiningRoom(1, colour);
                    i++;
                }
            }
        }

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameController.getGame().getTurnPhase());
        assertEquals(5, gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(9, gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getNumberStudentsEntrance());
        assertEquals(9, gameController.getGame().getPlayerByIndex(2).getSchoolBoard().getNumberStudentsEntrance());

        gameController.moveMotherNature(1, 4);
        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());
        gameController.chooseCloudTile(1, 0);

        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.WAITING, gameController.getGame().getTurnPhase());
        assertEquals(3, gameController.getGame().getTable().getNumberOfCloudTile());

        // second turn
        gameController.playAssistantCard(2, 7);
        gameController.playAssistantCard(0, 2);
        gameController.playAssistantCard(1, 4);

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameController.getGame().getTurnPhase());

        i = 0;
        while(i < 4){
            for(Colour colour: Colour.values()) {
                if(gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0){
                    gameController.moveStudentToIsland(0, colour, 7, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(0, 2);
        gameController.chooseCloudTile(0, 0);

        i = 0;
        while(i < 4){
            for(Colour colour: Colour.values()) {
                if(gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0){
                    gameController.moveStudentToIsland(1, colour, 10, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(1, 3);
        gameController.chooseCloudTile(1, 0);

        i = 0;
        while(i < 4){
            for(Colour colour: Colour.values()) {
                if(gameController.getGame().getPlayerByIndex(2).getSchoolBoard().getEntrance(colour) > 0){
                    gameController.moveStudentToIsland(2, colour, 7, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(2, 4);
        gameController.chooseCloudTile(2, 0);

        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.WAITING, gameController.getGame().getTurnPhase());

        // third turn
        gameController.playAssistantCard(0, 5);
        gameController.playAssistantCard(1, 1);
        gameController.playAssistantCard(2, 8);

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameController.getGame().getTurnPhase());

        i = 0;
        while(i < 4){
            for(Colour colour: Colour.values()) {
                if(gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0){
                    gameController.moveStudentToIsland(1, colour, 10, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(1, 1);
        gameController.chooseCloudTile(1, 0);


        i = 0;
        while(i < 4){
            for(Colour colour: Colour.values()) {
                if(gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0){
                    gameController.moveStudentToIsland(0, colour, 7, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(0, 3);
        gameController.chooseCloudTile(0, 0);

        i = 0;
        while(i < 4){
            for(Colour colour: Colour.values()) {
                if(gameController.getGame().getPlayerByIndex(2).getSchoolBoard().getEntrance(colour) > 0){
                    gameController.moveStudentToIsland(2, colour, 7, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(2, 4);
        gameController.chooseCloudTile(2, 0);

        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.WAITING, gameController.getGame().getTurnPhase());

        // fourth turn
        gameController.playAssistantCard(1, 5);
        gameController.playAssistantCard(2, 1);
        gameController.playAssistantCard(0, 8);


        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameController.getGame().getTurnPhase());

        i = 0;
        while(i < 4){
            for(Colour colour: Colour.values()) {
                if(gameController.getGame().getPlayerByIndex(2).getSchoolBoard().getEntrance(colour) > 0){
                    gameController.moveStudentToIsland(2, colour, 3, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(2, 1);
        gameController.chooseCloudTile(2, 0);

        i = 0;
        while(i < 4){
            for(Colour colour: Colour.values()) {
                if(gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0){
                    gameController.moveStudentToIsland(1, colour, 4, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(1, 1);
        gameController.chooseCloudTile(1, 0);


        i = 0;
        while(i < 4){
            for(Colour colour: Colour.values()) {
                if(gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0){
                    gameController.moveStudentToIsland(0, colour, 5, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(0, 1);
        gameController.chooseCloudTile(0, 0);

        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.WAITING, gameController.getGame().getTurnPhase());

        // fifth turn
        gameController.playAssistantCard(2, 6);
        gameController.playAssistantCard(0, 3);
        gameController.playAssistantCard(1, 10);

        assertEquals(GamePhase.PLAYING, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.MOVE_STUDENT, gameController.getGame().getTurnPhase());

        i = 0;
        while(i < 4){
            for(Colour colour: Colour.values()) {
                if(gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour) > 0){
                    gameController.moveStudentToIsland(0, colour, 1, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(0, 1);
        gameController.chooseCloudTile(0, 0);

        i = 0;
        while(i < 4){
            for(Colour colour: Colour.values()) {
                if(gameController.getGame().getPlayerByIndex(2).getSchoolBoard().getEntrance(colour) > 0){
                    gameController.moveStudentToIsland(2, colour, 2, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(2, 1);
        gameController.chooseCloudTile(2, 0);

        i = 0;
        while(i < 4){
            for(Colour colour: Colour.values()) {
                if(gameController.getGame().getPlayerByIndex(1).getSchoolBoard().getEntrance(colour) > 0){
                    gameController.moveStudentToIsland(1, colour, 3, 0);
                    i++;
                }
            }
        }
        gameController.moveMotherNature(1, 1);
        gameController.chooseCloudTile(1, 0);

        assertEquals(GamePhase.PLAY_ASSISTANT_CARD, gameController.getGame().getGamePhase());
        assertEquals(TurnPhase.WAITING, gameController.getGame().getTurnPhase());

    }
}
