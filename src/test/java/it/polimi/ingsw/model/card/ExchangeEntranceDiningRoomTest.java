package it.polimi.ingsw.model.card;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.ExpertPlayer;
import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.table.island.BasicGroupIsland;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ExchangeEntranceDiningRoomTest {
    private Game gameTest;
    private ExchangeEntranceDiningRoom cardTest;

    @BeforeEach
    void setUp() {
        gameTest = new ExpertGame();
        cardTest = new ExchangeEntranceDiningRoom(gameTest);
        gameTest.addCharacterCard(cardTest);
        gameTest.addPlayer(new ExpertPlayer("sara", Wizard.TYPE_2, TowerColour.WHITE));
        gameTest.addPlayer(new ExpertPlayer("viola", Wizard.TYPE_3, TowerColour.BLACK));
        for (int i = 0; i < 12; i++) {
            gameTest.getTable().addGroupIsland(new BasicGroupIsland());
        }
        gameTest.setCurrentPlayer(gameTest.getPlayerByIndex(0));

        gameTest.getPlayerByIndex(0).getSchoolBoard().addStudentToDiningRoom(Colour.YELLOW);
        gameTest.getPlayerByIndex(0).getSchoolBoard().addStudentToDiningRoom(Colour.YELLOW);
        gameTest.getPlayerByIndex(0).getSchoolBoard().addStudentToDiningRoom(Colour.YELLOW);
        gameTest.getPlayerByIndex(1).getSchoolBoard().addStudentToDiningRoom(Colour.GREEN);
        gameTest.getPlayerByIndex(0).getSchoolBoard().addStudentToEntrance(Colour.GREEN);
        gameTest.getPlayerByIndex(0).getSchoolBoard().addStudentToEntrance(Colour.GREEN);

        gameTest.getPlayerByIndex(0).getSchoolBoard().addProfessor(Colour.YELLOW);
        gameTest.getPlayerByIndex(1).getSchoolBoard().addProfessor(Colour.GREEN);
    }

    @Test
    void setColourDiningRoomEntrance() {
        cardTest.setColourDiningRoomEntrance(Colour.YELLOW, Colour.GREEN);
        assertEquals(Colour.YELLOW, cardTest.getColourDiningRoom());
        assertEquals(Colour.GREEN, cardTest.getColourEntrance());
        assertTrue(gameTest.getPlayerByIndex(0).getSchoolBoard().hasProfessor(Colour.YELLOW));
        assertTrue(gameTest.getPlayerByIndex(1).getSchoolBoard().hasProfessor(Colour.GREEN));
        assertFalse(gameTest.getPlayerByIndex(0).getSchoolBoard().hasProfessor(Colour.GREEN));
        cardTest.setColourDiningRoomEntrance(Colour.YELLOW, Colour.GREEN);
        assertEquals(Colour.YELLOW, cardTest.getColourDiningRoom());
        assertEquals(Colour.GREEN, cardTest.getColourEntrance());
        assertTrue(gameTest.getPlayerByIndex(0).getSchoolBoard().hasProfessor(Colour.YELLOW));
        assertTrue(gameTest.getPlayerByIndex(0).getSchoolBoard().hasProfessor(Colour.GREEN));
        assertFalse(gameTest.getPlayerByIndex(1).getSchoolBoard().hasProfessor(Colour.GREEN));
    }

    @RepeatedTest(2000)
    void exchangeEntranceDiningRoom(){
        GameController gameController;
        gameController = new GameController(true, 2);
        gameController.setUp();
        gameController.addPlayer("Viola", Wizard.TYPE_3);
        gameController.addPlayer("Laura", Wizard.TYPE_4);
        gameController.setUpCharactersAndIslands();

        boolean flag = false;
        int i;
        for(i = 0; i < 3; i++){
            if(gameController.getGame().getCharacterCardByIndex(i).getCharacterCardType() == CharacterCardEnumeration.EXCHANGE_ENTRANCE_DINING_ROOM){
                flag = true;
                break;
            }
        }

        if(flag){
            gameController.playAssistantCard("Viola", 0);
            gameController.playAssistantCard("Laura", 1);

            gameController.playCharacterCard("Viola", i);
            assertEquals(CharacterCardEnumeration.EXCHANGE_ENTRANCE_DINING_ROOM, gameController.getGame().getActiveCharacterCard().getCharacterCardType());
            assertEquals(0, gameController.getGame().getCurrentPlayer().getCoins());

            int numPink = 0;
            int numGreen = 0;
            int numYellow = 0;
            int numBlue = 0;
            int numRed = 0;

            for(Colour colour : Colour.values()){
                for(int j = 0; j < gameController.getGame().getPlayerByIndex(0).getSchoolBoard().getEntrance(colour); j++){
                    switch (colour) {
                        case YELLOW -> numYellow++;
                        case PINK -> numPink++;
                        case GREEN -> numGreen++;
                        case BLUE -> numBlue++;
                        case RED -> numRed++;
                    }
                }
            }

            if(numPink == 0){
                gameController.setColourDiningRoomEntrance("Viola", Colour.RED, Colour.PINK);
                assertEquals(numPink, gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(Colour.PINK));
                assertEquals(0, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(Colour.PINK));
            }

            if(numGreen == 0){
                gameController.setColourDiningRoomEntrance("Viola", Colour.RED, Colour.GREEN);
                assertEquals(numGreen, gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(Colour.GREEN));
                assertEquals(0, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(Colour.GREEN));
            }

            if(numBlue == 0){
                gameController.setColourDiningRoomEntrance("Viola", Colour.RED, Colour.BLUE);
                assertEquals(numBlue, gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(Colour.BLUE));
                assertEquals(0, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(Colour.BLUE));
            }

            if(numYellow == 0){
                gameController.setColourDiningRoomEntrance("Viola", Colour.RED, Colour.YELLOW);
                assertEquals(numYellow, gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(Colour.YELLOW));
                assertEquals(0, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(Colour.YELLOW));
            }

            if(numRed == 0){
                gameController.setColourDiningRoomEntrance("Viola", Colour.RED, Colour.RED);
                assertEquals(numRed, gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(Colour.RED));
                assertEquals(0, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(Colour.RED));
            }

            if(numPink > 0){
                gameController.moveStudentToDiningRoom("Viola", Colour.PINK);
                numPink --;
                assertEquals(numPink, gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(Colour.PINK));
                assertEquals(1, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(Colour.PINK));

                if(numBlue == 0){
                    gameController.setColourDiningRoomEntrance("Viola", Colour.PINK, Colour.BLUE);
                    assertEquals(numBlue, gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(Colour.BLUE));
                    assertEquals(0, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(Colour.BLUE));
                    assertEquals(1, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(Colour.PINK));
                }

                if(numGreen == 0){
                    gameController.setColourDiningRoomEntrance("Viola", Colour.PINK, Colour.GREEN);
                    assertEquals(numGreen, gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(Colour.GREEN));
                    assertEquals(0, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(Colour.GREEN));
                    assertEquals(1, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(Colour.PINK));
                }
            } else if(numYellow > 0){
                gameController.moveStudentToDiningRoom("Viola", Colour.YELLOW);
                numYellow --;
                assertEquals(numYellow, gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(Colour.YELLOW));
                assertEquals(1, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(Colour.YELLOW));

                if(numBlue == 0){
                    gameController.setColourDiningRoomEntrance("Viola", Colour.YELLOW, Colour.BLUE);
                    assertEquals(numBlue, gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(Colour.BLUE));
                    assertEquals(0, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(Colour.BLUE));
                    assertEquals(1, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(Colour.YELLOW));
                }

                if(numGreen == 0){
                    gameController.setColourDiningRoomEntrance("Viola", Colour.YELLOW, Colour.GREEN);
                    assertEquals(numGreen, gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(Colour.GREEN));
                    assertEquals(0, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(Colour.GREEN));
                    assertEquals(1, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(Colour.YELLOW));
                }

                if(numBlue > 0){
                    gameController.setColourDiningRoomEntrance("Viola", Colour.YELLOW, Colour.BLUE);
                    numBlue --;
                    numYellow ++;
                    assertEquals(numBlue, gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(Colour.BLUE));
                    assertEquals(numYellow, gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(Colour.YELLOW));
                    assertEquals(1, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(Colour.BLUE));
                    assertEquals(0, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(Colour.YELLOW));
                }
            } else if(numGreen >= 3 && numRed > 0){
                gameController.moveStudentToDiningRoom("Viola", Colour.GREEN);
                gameController.moveStudentToDiningRoom("Viola", Colour.GREEN);
                gameController.moveStudentToDiningRoom("Viola", Colour.RED);
                numGreen = numGreen - 2;
                numRed = numRed - 1;

                assertEquals(numGreen, gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(Colour.GREEN));
                assertEquals(numRed, gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(Colour.RED));
                assertEquals(2, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(Colour.GREEN));
                assertEquals(1, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(Colour.RED));

                gameController.setColourDiningRoomEntrance("Viola", Colour.RED, Colour.GREEN);
                assertEquals(3, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(Colour.GREEN));
                assertEquals(0, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(Colour.RED));
                assertEquals(1, gameController.getGame().getCurrentPlayer().getCoins());



            }



        }



    }
}