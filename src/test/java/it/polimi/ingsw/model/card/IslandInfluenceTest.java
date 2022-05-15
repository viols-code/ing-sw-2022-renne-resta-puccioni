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

class IslandInfluenceTest {
    private IslandInfluence cardTest;
    private Game gameTest;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {
        gameTest = new ExpertGame();
        cardTest = new IslandInfluence(gameTest);

        gameTest.addCharacterCard(cardTest);
        for (int i = 0; i < 12; i++) {
            gameTest.getTable().addGroupIsland(new BasicGroupIsland());
        }

        settingBag();
        settingCard();

        player1 = new BasicPlayer("Viola", Wizard.TYPE_4, TowerColour.WHITE);
        player2 = new BasicPlayer("Laura", Wizard.TYPE_1, TowerColour.BLACK);

        gameTest.addPlayer(player1);
        gameTest.addPlayer(player2);

        player1.getSchoolBoard().addTower(8);
        player2.getSchoolBoard().addTower(8);

        player1.getSchoolBoard().addStudentToDiningRoom(Colour.BLUE);
        player1.getSchoolBoard().addProfessor(Colour.BLUE);
        player2.getSchoolBoard().addStudentToDiningRoom(Colour.RED);
        player2.getSchoolBoard().addProfessor(Colour.RED);

        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(0,0,Colour.BLUE);
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(0,0,Colour.BLUE);
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(0,0,Colour.BLUE);
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(0,0,Colour.RED);
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(0,0,Colour.RED);
    }

    @Test
    void setting() {
        assertEquals(3, cardTest.getCost());
        assertEquals(2, gameTest.getNumberOfPlayer());
    }

    @RepeatedTest(100)
    void setGroupIsland() {
        gameTest.getTable().setMotherNaturePosition(4);
        assertTrue(gameTest.getTable().getGroupIslandByIndex(4).getMotherNature());
        assertEquals(4, gameTest.getTable().getMotherNaturePosition());
        cardTest.setGroupIsland(0);
        assertEquals(player1, gameTest.getTable().getGroupIslandByIndex(0).getInfluence());
        assertEquals(7, player1.getSchoolBoard().getTowers());
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(0,0,Colour.RED);
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(0,0,Colour.RED);
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(0,0,Colour.RED);
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(0,0,Colour.RED);
        cardTest.setGroupIsland(0);
        assertTrue(gameTest.getTable().getGroupIslandByIndex(4).getMotherNature());
        assertEquals(4, gameTest.getTable().getMotherNaturePosition());
        assertEquals(player2, gameTest.getTable().getGroupIslandByIndex(0).getInfluence());
        assertEquals(8, player1.getSchoolBoard().getTowers());
        assertEquals(7, player2.getSchoolBoard().getTowers());

        gameTest.getTable().getGroupIslandByIndex(1).getIslandByIndex(0).addStudent(1,0,Colour.BLUE);
        gameTest.getTable().getGroupIslandByIndex(1).getIslandByIndex(0).addStudent(1,0,Colour.RED);
        gameTest.getTable().getGroupIslandByIndex(1).getIslandByIndex(0).addStudent(1,0,Colour.RED);
        gameTest.getTable().getGroupIslandByIndex(1).getIslandByIndex(0).addStudent(1,0,Colour.RED);
        assertEquals(12, gameTest.getTable().getNumberOfGroupIsland());
        cardTest.setGroupIsland(1);
        assertEquals(11, gameTest.getTable().getNumberOfGroupIsland());
        assertEquals(player2, gameTest.getTable().getGroupIslandByIndex(0).getInfluence());
        assertTrue(gameTest.getTable().getGroupIslandByIndex(3).getMotherNature());
        assertEquals(3, gameTest.getTable().getMotherNaturePosition());
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

    @RepeatedTest(100)
    void setMotherNaturePosition() {
        gameTest.getTable().setMotherNaturePosition(4);
        assertTrue(gameTest.getTable().getGroupIslandByIndex(4).getMotherNature());
        assertEquals(4, gameTest.getTable().getMotherNaturePosition());
        cardTest.setGroupIsland(0);
        assertEquals(player1, gameTest.getTable().getGroupIslandByIndex(0).getInfluence());
        assertEquals(7, player1.getSchoolBoard().getTowers());

        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(0,0,Colour.RED);
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(0,0,Colour.RED);
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(0,0,Colour.RED);
        gameTest.getTable().getGroupIslandByIndex(0).getIslandByIndex(0).addStudent(0,0,Colour.RED);
        cardTest.setGroupIsland(0);
        assertTrue(gameTest.getTable().getGroupIslandByIndex(4).getMotherNature());
        assertEquals(4, gameTest.getTable().getMotherNaturePosition());
        assertEquals(player2, gameTest.getTable().getGroupIslandByIndex(0).getInfluence());
        assertEquals(8, player1.getSchoolBoard().getTowers());
        assertEquals(7, player2.getSchoolBoard().getTowers());

        gameTest.getTable().getGroupIslandByIndex(1).getIslandByIndex(0).addStudent(1,0,Colour.BLUE);
        gameTest.getTable().getGroupIslandByIndex(1).getIslandByIndex(0).addStudent(1,0,Colour.RED);
        gameTest.getTable().getGroupIslandByIndex(1).getIslandByIndex(0).addStudent(1,0,Colour.RED);
        gameTest.getTable().getGroupIslandByIndex(1).getIslandByIndex(0).addStudent(1,0,Colour.RED);
        assertEquals(12, gameTest.getTable().getNumberOfGroupIsland());
        cardTest.setGroupIsland(1);
        assertEquals(11, gameTest.getTable().getNumberOfGroupIsland());
        assertEquals(player2, gameTest.getTable().getGroupIslandByIndex(0).getInfluence());
        assertTrue(gameTest.getTable().getGroupIslandByIndex(3).getMotherNature());
        assertEquals(3, gameTest.getTable().getMotherNaturePosition());


        gameTest.getTable().setMotherNaturePosition(10);
        assertTrue(gameTest.getTable().getGroupIslandByIndex(10).getMotherNature());
        assertEquals(10, gameTest.getTable().getMotherNaturePosition());
        gameTest.getTable().getGroupIslandByIndex(10).getIslandByIndex(0).addStudent(1,0,Colour.BLUE);
        gameTest.getTable().getGroupIslandByIndex(10).getIslandByIndex(0).addStudent(1,0,Colour.RED);
        gameTest.getTable().getGroupIslandByIndex(10).getIslandByIndex(0).addStudent(1,0,Colour.RED);
        gameTest.getTable().getGroupIslandByIndex(10).getIslandByIndex(0).addStudent(1,0,Colour.RED);
        assertEquals(11, gameTest.getTable().getNumberOfGroupIsland());
        cardTest.setGroupIsland(10);
        assertEquals(10, gameTest.getTable().getNumberOfGroupIsland());
        assertEquals(3, gameTest.getTable().getGroupIslandByIndex(0).getNumberOfSingleIsland());
        assertTrue(gameTest.getTable().getGroupIslandByIndex(0).getMotherNature());
        assertEquals(0, gameTest.getTable().getMotherNaturePosition());
    }

    @RepeatedTest(1000)
    void islandInfluent(){
        GameController gameController = new GameController(true, 2);
        gameController.setUp();
        gameController.addPlayer("Viola", Wizard.TYPE_1);
        gameController.addPlayer("Laura", Wizard.TYPE_2);
        gameController.setUpCharactersAndIslands();

        boolean flag = false;
        int i;
        for(i = 0; i < 3; i++){
            if(gameController.getGame().getCharacterCardByIndex(i).getCharacterCardType() == CharacterCardEnumeration.ISLAND_INFLUENCE){
                flag = true;
                break;
            }
        }

        if(flag){

            // FIRST TURN
            gameController.playAssistantCard("Viola", 0);
            gameController.playAssistantCard("Laura", 1);
            assertTrue(gameController.getGame().getTable().getGroupIslandByIndex(0).getMotherNature());
            assertEquals(TurnPhase.MOVE_STUDENT, gameController.getGame().getTurnPhase());

            // VIOLA
            for(Colour colour : Colour.values()){
                if(gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) >= 3){
                    gameController.moveStudentToDiningRoom("Viola", colour);
                    gameController.moveStudentToDiningRoom("Viola", colour);
                    gameController.moveStudentToDiningRoom("Viola", colour);
                }
            }

            for(Colour colour : Colour.values()){
                if(gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) >= 2){
                    gameController.moveStudentToDiningRoom("Viola", colour);
                    gameController.moveStudentToDiningRoom("Viola", colour);
                }
            }

            for(Colour colour : Colour.values()){
                if(gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) >= 1){
                    gameController.moveStudentToDiningRoom("Viola", colour);
                }
            }

            assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameController.getGame().getTurnPhase());
            gameController.moveMotherNature("Viola", 1);
            assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());
            gameController.chooseCloudTile("Viola", 0);
            assertTrue(gameController.getGame().getTable().getGroupIslandByIndex(1).getMotherNature());

            // LAURA
            assertEquals(TurnPhase.MOVE_STUDENT, gameController.getGame().getTurnPhase());

            for(Colour colour : Colour.values()){
                if(gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) >= 3){
                    gameController.moveStudentToDiningRoom("Laura", colour);
                    gameController.moveStudentToDiningRoom("Laura", colour);
                    gameController.moveStudentToDiningRoom("Laura", colour);
                }
            }

            for(Colour colour : Colour.values()){
                if(gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) >= 2){
                    gameController.moveStudentToDiningRoom("Laura", colour);
                    gameController.moveStudentToDiningRoom("Laura", colour);
                }
            }

            for(Colour colour : Colour.values()){
                if(gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) >= 1){
                    gameController.moveStudentToDiningRoom("Laura", colour);
                }
            }

            assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameController.getGame().getTurnPhase());
            gameController.moveMotherNature("Laura", 1);
            assertEquals(TurnPhase.CHOOSE_CLOUD_TILE, gameController.getGame().getTurnPhase());
            gameController.chooseCloudTile("Laura", 0);

            // SECOND TURN
            gameController.playAssistantCard("Viola", 1);
            gameController.playAssistantCard("Laura", 2);

            // VIOLA
            for(Colour colour : Colour.values()){
                if(gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) >= 3){
                    gameController.moveStudentToDiningRoom("Viola", colour);
                    gameController.moveStudentToDiningRoom("Viola", colour);
                    gameController.moveStudentToDiningRoom("Viola", colour);
                }
            }

            for(Colour colour : Colour.values()){
                if(gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) >= 2){
                    gameController.moveStudentToDiningRoom("Viola", colour);
                    gameController.moveStudentToDiningRoom("Viola", colour);
                }
            }

            for(Colour colour : Colour.values()){
                if(gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) >= 1){
                    gameController.moveStudentToDiningRoom("Viola", colour);
                }
            }

            gameController.moveMotherNature("Viola", 1);
            gameController.chooseCloudTile("Viola", 0);

            // LAURA
            for(Colour colour : Colour.values()){
                if(gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) >= 3){
                    gameController.moveStudentToDiningRoom("Laura", colour);
                    gameController.moveStudentToDiningRoom("Laura", colour);
                    gameController.moveStudentToDiningRoom("Laura", colour);
                }
            }

            for(Colour colour : Colour.values()){
                if(gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) >= 2){
                    gameController.moveStudentToDiningRoom("Laura", colour);
                    gameController.moveStudentToDiningRoom("Laura", colour);
                }
            }

            for(Colour colour : Colour.values()){
                if(gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) >= 1){
                    gameController.moveStudentToDiningRoom("Laura", colour);
                }
            }

            gameController.moveMotherNature("Laura", 1);
            gameController.chooseCloudTile("Laura", 0);

            // THIRD TURN
            gameController.playAssistantCard("Viola", 2);
            gameController.playAssistantCard("Laura", 3);

            for(Colour colour : Colour.values()){
                if(gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) >= 3){
                    gameController.moveStudentToDiningRoom("Viola", colour);
                    gameController.moveStudentToDiningRoom("Viola", colour);
                    gameController.moveStudentToDiningRoom("Viola", colour);
                }
            }

            for(Colour colour : Colour.values()){
                if(gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) >= 2){
                    gameController.moveStudentToDiningRoom("Viola", colour);
                    gameController.moveStudentToDiningRoom("Viola", colour);
                }
            }

            for(Colour colour : Colour.values()){
                if(gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) >= 1){
                    gameController.moveStudentToDiningRoom("Viola", colour);
                }
            }


            if(gameController.getGame().getCurrentPlayer().getCoins() >= 3){
                gameController.playCharacterCard("Viola", i);

                String name2 = "";
                boolean flag2 = true;
                if(gameController.getGame().getTable().getGroupIslandByIndex(3).getInfluence() == null){
                    flag2 = false;
                } else {
                    name2 = gameController.getGame().getTable().getGroupIslandByIndex(3).getInfluence().getNickname();
                }

                String name3 = "";
                boolean flag3 = true;
                if(gameController.getGame().getTable().getGroupIslandByIndex(5).getInfluence() == null){
                    flag3 = false;
                } else {
                    name3 = gameController.getGame().getTable().getGroupIslandByIndex(5).getInfluence().getNickname();
                }

                int numberOfIsland = gameController.getGame().getTable().getNumberOfGroupIsland();
                gameController.setGroupIsland("Viola", 4);

                if(!flag3 && !flag2){
                    assertEquals(numberOfIsland, gameController.getGame().getTable().getNumberOfGroupIsland());
                }

                if(name2.equals(name3)){
                    if(gameController.getGame().getTable().getNumberOfGroupIsland() < numberOfIsland){
                        assertEquals(numberOfIsland - 2, gameController.getGame().getTable().getNumberOfGroupIsland());
                    }
                } else{
                    if(gameController.getGame().getTable().getNumberOfGroupIsland() < numberOfIsland){
                        assertEquals(numberOfIsland - 1, gameController.getGame().getTable().getNumberOfGroupIsland());
                    }
                }

            }

            gameController.moveMotherNature("Viola", 1);
            gameController.chooseCloudTile("Viola", 0);
        }
    }
}