package it.polimi.ingsw.model.card;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;


public class ProtectIslandTest {
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController(true, 2);
        gameController.setUp();
        gameController.addPlayer("Viola", Wizard.TYPE_1);
        gameController.addPlayer("Laura", Wizard.TYPE_2);
        gameController.setUpCharactersAndIslands();
    }

    @RepeatedTest(10000)
    void protectIslandTest(){
        gameController.playAssistantCard("Viola", 0);
        gameController.playAssistantCard("Laura", 1);

        boolean flag = false;
        int i;
        for (i = 0; i < 3; i++) {
            if (gameController.getGame().getCharacterCardByIndex(i).getCharacterCardType() == CharacterCardEnumeration.PROTECT_ISLAND) {
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
                gameController.setGroupIsland("Viola", 1);
                assertEquals(1, gameController.getGame().getTable().getGroupIslandByIndex(1).getNumberOfNoEntryTile());
                assertEquals(3, gameController.getGame().getCharacterCardByIndex(i).getNumberOfNoEntryTiles());

                assertEquals(TurnPhase.MOVE_MOTHER_NATURE, gameController.getGame().getTurnPhase());
                gameController.moveMotherNature("Viola", 1);
                assertEquals(0, gameController.getGame().getTable().getGroupIslandByIndex(1).getNumberOfNoEntryTile());
                assertEquals(4, gameController.getGame().getCharacterCardByIndex(i).getNumberOfNoEntryTiles());
                assertNull(gameController.getGame().getTable().getGroupIslandByIndex(1).getInfluence());
            } else{
                gameController.playCharacterCard("Viola", i);
                gameController.setGroupIsland("Viola", 1);
                assertEquals(0, gameController.getGame().getTable().getGroupIslandByIndex(1).getNumberOfNoEntryTile());
                assertEquals(4, gameController.getGame().getCharacterCardByIndex(i).getNumberOfNoEntryTiles());
            }
        }
    }

}
