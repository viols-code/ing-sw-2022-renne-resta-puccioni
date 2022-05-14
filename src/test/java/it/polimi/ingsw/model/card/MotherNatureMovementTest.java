package it.polimi.ingsw.model.card;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;

class MotherNatureMovementTest {
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController(true, 2);
        gameController.setUp();
        gameController.addPlayer("Viola", Wizard.TYPE_2);
        gameController.addPlayer("Laura", Wizard.TYPE_3);
        gameController.setUpCharactersAndIslands();
    }

    @RepeatedTest(100)
    void motherNatureMovement() {
        gameController.playAssistantCard("Viola", 0);
        gameController.playAssistantCard("Laura", 1);

        boolean flag = false;
        int i;
        for (i = 0; i < 3; i++) {
            if (gameController.getGame().getCharacterCardByIndex(i).getCharacterCardType() == CharacterCardEnumeration.MOTHER_NATURE_MOVEMENT) {
                flag = true;
                break;
            }
        }

        if (flag) {
            gameController.playCharacterCard("Viola", i);
            assertEquals(gameController.getGame().getCharacterCardByIndex(i), gameController.getGame().getActiveCharacterCard());

            int j = 0;
            while(j < 3){
                for(Colour colour: Colour.values()){
                    if(gameController.getGame().getCurrentPlayer().getSchoolBoard().getEntrance(colour) > 0){
                        gameController.moveStudentToDiningRoom("Viola", colour);
                        j++;
                    }
                }
            }
            gameController.moveMotherNature("Viola", 4);
            gameController.moveMotherNature("Viola", 5);
            gameController.moveMotherNature("Viola", 6);
            assertEquals(0, gameController.getGame().getTable().getMotherNaturePosition());

            gameController.moveMotherNature("Viola", 3);
            assertEquals(3, gameController.getGame().getTable().getMotherNaturePosition());
        }
    }

}