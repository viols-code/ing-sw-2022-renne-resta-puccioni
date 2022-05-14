package it.polimi.ingsw.model.card;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.model.player.BasicPlayer;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.table.island.BasicGroupIsland;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TakeProfessorTest {
    private TakeProfessor cardTest;
    private Game gameTest;
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameTest = new ExpertGame();
        cardTest = new TakeProfessor(gameTest);

        gameTest.addCharacterCard(cardTest);
        for (int i = 0; i < 12; i++) {
            gameTest.getTable().addGroupIsland(new BasicGroupIsland());
        }

        settingBag();
        settingCard();

        Player player1 = new BasicPlayer("Viola", Wizard.TYPE_4, TowerColour.WHITE);
        Player player2 = new BasicPlayer("Laura", Wizard.TYPE_1, TowerColour.BLACK);

        gameTest.addPlayer(player1);
        gameTest.addPlayer(player2);
    }

    @Test
    void setting() {
        assertEquals(2, cardTest.getCost());
        assertEquals(2, gameTest.getNumberOfPlayer());
    }

    @RepeatedTest(100)
    void professor() {
        gameController = new GameController(true, 2);
        gameController.setUp();
        gameController.addPlayer("Viola", Wizard.TYPE_1);
        gameController.addPlayer("Laura", Wizard.TYPE_2);
        gameController.setUpCharactersAndIslands();

        gameController.playAssistantCard("Viola", 0);
        gameController.playAssistantCard("Laura", 1);

        boolean flag = false;
        int i;
        for (i = 0; i < 3; i++) {
            if (gameController.getGame().getCharacterCardByIndex(i).getCharacterCardType() == CharacterCardEnumeration.TAKE_PROFESSOR) {
                flag = true;
                break;
            }
        }

        if(flag){
            for(Colour colour: Colour.values()){
                if(gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) >= 3){
                    gameController.moveStudentToDiningRoom("Viola", colour);
                    gameController.moveStudentToDiningRoom("Viola", colour);
                    gameController.moveStudentToDiningRoom("Viola", colour);
                }
            }

            if(gameController.getGame().getCurrentPlayer().getCoins() == 2){
                gameController.playCharacterCard("Viola", i);

                for(Colour colour: Colour.values()){
                    if(gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(colour) > 0){
                        assertTrue(gameController.getGame().getCurrentPlayer().getSchoolBoard().hasProfessor(colour));
                        assertEquals(3, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(colour));
                    } else {
                        assertFalse(gameController.getGame().getCurrentPlayer().getSchoolBoard().hasProfessor(colour));
                    }
                }

            }
        }

    }

    @RepeatedTest(10000)
    void takeProfessor(){
        gameController = new GameController(true, 2);
        gameController.setUp();
        gameController.addPlayer("Viola", Wizard.TYPE_1);
        gameController.addPlayer("Laura", Wizard.TYPE_2);
        gameController.setUpCharactersAndIslands();

        gameController.playAssistantCard("Viola", 0);
        gameController.playAssistantCard("Laura", 1);

        boolean flag = false;
        int i;
        for (i = 0; i < 3; i++) {
            if (gameController.getGame().getCharacterCardByIndex(i).getCharacterCardType() == CharacterCardEnumeration.TAKE_PROFESSOR) {
                flag = true;
                break;
            }
        }

        if(flag){

            for(Colour colour: Colour.values()){
                if(gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) >= 3){
                    gameController.moveStudentToDiningRoom("Viola", colour);
                    gameController.moveStudentToDiningRoom("Viola", colour);
                    gameController.moveStudentToDiningRoom("Viola", colour);
                    assertTrue(gameController.getGame().getCurrentPlayer().getSchoolBoard().hasProfessor(colour));
                    assertEquals(3, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(colour));

                    gameController.moveMotherNature("Viola", 1);
                    gameController.chooseCloudTile("Viola", 0);

                    if(gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) >= 3){
                        gameController.moveStudentToDiningRoom("Laura", colour);
                        gameController.moveStudentToDiningRoom("Laura", colour);
                        gameController.moveStudentToDiningRoom("Laura", colour);
                        assertFalse(gameController.getGame().getCurrentPlayer().getSchoolBoard().hasProfessor(colour));
                        assertEquals(3, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(colour));

                        gameController.playCharacterCard("Laura", i);
                        assertTrue(gameController.getGame().getCurrentPlayer().getSchoolBoard().hasProfessor(colour));
                        assertEquals(3, gameController.getGame().getCurrentPlayer().getSchoolBoard().getDiningRoom(colour));

                    }

                    break;
                }
            }



        }

    }

    @Test
    void incrementCost() {
        int cost = cardTest.getCost();
        assertEquals(cost, cardTest.getCost());
        cardTest.incrementCost();
        assertEquals(cost + 1, cardTest.getCost());
    }

    @Test
    void setColour() {
        for (Colour colour : Colour.values()) {
            assertThrows(IllegalAccessError.class, () -> cardTest.setColour(colour));
        }
    }

    @Test
    void setColourAndIsland() {
        for (Colour colour : Colour.values()) {
            assertThrows(IllegalAccessError.class, () -> cardTest.setColourAndIsland(colour, gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0)));
        }
    }

    @Test
    public void setColourDiningRoomEntrance() {
        for (Colour colour : Colour.values()) {
            assertThrows(IllegalAccessError.class, () -> cardTest.setColourDiningRoomEntrance(colour, colour));
        }
    }

    @Test
    public void setColourCardEntrance() {
        for (Colour colour : Colour.values()) {
            assertThrows(IllegalAccessError.class, () -> cardTest.setColourCardEntrance(colour, colour));
        }
    }

    @Test
    void setGroupIsland() {
        for (int i = 0; i < gameTest.getTable().getNumberOfGroupIsland(); i++) {
            int finalI = i;
            assertThrows(IllegalAccessError.class, () -> cardTest.setGroupIsland(finalI));
        }
    }

    private void settingBag() {
        for (Colour colour : Colour.values()) {
            for (int i = 0; i < 2; i++) {
                gameTest.getTable().getBag().addStudentBag(colour);
            }
        }

        for (int i = 1; i < 12; i++) {
            if (i == 5) i++;
            gameTest.getTable().getGroupIslandByIndex(i).getIslandByIndex(0).addStudent(i,0,gameTest.getTable().getBag().bagDrawStudent());
        }

        for (Colour colour : Colour.values()) {
            for (int i = 0; i < 24; i++) {
                gameTest.getTable().getBag().addStudentBag(colour);
            }
        }
    }

    private void settingCard() {
        for (int i = 0; i < gameTest.getNumberOfCharacterCard(); i++) {
            gameTest.getCharacterCardByIndex(i).setting();
        }
    }
}