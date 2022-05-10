package it.polimi.ingsw.model.table.island;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdvancedGroupIslandTest {
    private GameController gameController;
    private AdvancedGroupIsland groupIsland;


    @BeforeEach
    void setUp() {
        gameController = new GameController(true, 2);
        gameController.setUp();
    }

    @RepeatedTest(12)
    void checkSetting() {
        if (gameController.getGame().hasProtectIslandCard()) {
            assertEquals(12, gameController.getGame().getTable().getNumberOfGroupIsland());

            for (int i = 0; i < gameController.getGame().getTable().getNumberOfGroupIsland(); i++) {
                assertEquals(0, gameController.getGame().getTable().getGroupIslandByIndex(i).getNumberOfNoEntryTile());
            }
        }
    }


    @RepeatedTest(12)
    void addNoEntryTile() {
        if (gameController.getGame().hasProtectIslandCard()) {
            for (int i = 0; i < gameController.getGame().getTable().getNumberOfGroupIsland(); i++) {
                assertEquals(0, gameController.getGame().getTable().getGroupIslandByIndex(i).getNumberOfNoEntryTile());
            }
            gameController.getGame().getTable().getGroupIslandByIndex(0).addNoEntryTile();
            assertEquals(1, gameController.getGame().getTable().getGroupIslandByIndex(0).getNumberOfNoEntryTile());
        }
    }

    @Test
    void checkAddNoEntryTile() {
        groupIsland = new AdvancedGroupIsland();
        assertEquals(0, groupIsland.getNumberOfNoEntryTile());
        groupIsland.addNoEntryTile();
        assertEquals(1, groupIsland.getNumberOfNoEntryTile());
    }

    @RepeatedTest(12)
    void removeNoEntryTile() {
        if (gameController.getGame().hasProtectIslandCard()) {
            for (int i = 0; i < gameController.getGame().getTable().getNumberOfGroupIsland(); i++) {
                assertEquals(0, gameController.getGame().getTable().getGroupIslandByIndex(i).getNumberOfNoEntryTile());
            }
            gameController.getGame().getTable().getGroupIslandByIndex(0).addNoEntryTile();
            assertEquals(1, gameController.getGame().getTable().getGroupIslandByIndex(0).getNumberOfNoEntryTile());
            gameController.getGame().getTable().getGroupIslandByIndex(0).removeNoEntryTile();
            assertEquals(0, gameController.getGame().getTable().getGroupIslandByIndex(0).getNumberOfNoEntryTile());
        }
    }

    @Test
    void checkRemoveNoEntryTile() {
        groupIsland = new AdvancedGroupIsland();
        assertEquals(0, groupIsland.getNumberOfNoEntryTile());
        groupIsland.addNoEntryTile();
        assertEquals(1, groupIsland.getNumberOfNoEntryTile());
        groupIsland.removeNoEntryTile();
        assertEquals(0, groupIsland.getNumberOfNoEntryTile());
    }

    @RepeatedTest(20)
    void checkUnify() {
        if (gameController.getGame().hasProtectIslandCard()) {
            gameController.addPlayer("Viola", Wizard.TYPE_3);
            gameController.addPlayer("Laura", Wizard.TYPE_2);
            gameController.getGame().getTable().getGroupIslandByIndex(3).addNoEntryTile();
            gameController.getGame().getTable().getGroupIslandByIndex(3).changeInfluence(gameController.getGame().getPlayerByIndex(0));
            gameController.getGame().getTable().getGroupIslandByIndex(2).getIslandByIndex(0).addStudent(2,0,Colour.PINK);
            gameController.getGame().getPlayerByIndex(0).getSchoolBoard().addProfessor(Colour.PINK);
            assertEquals(1, gameController.getGame().getTable().getGroupIslandByIndex(3).getNumberOfNoEntryTile());
            assertTrue(gameController.getGame().getTable().getGroupIslandByIndex(3).isNoEntryTile());
            gameController.getGame().setGamePhase(GamePhase.PLAYING);
            gameController.getGame().setTurnPhase(TurnPhase.MOVE_MOTHER_NATURE);
            gameController.getGame().getPlayerByIndex(0).addCoins(2);
            gameController.getGame().setCurrentPlayer(gameController.getGame().getPlayerByIndex(0));
            assertEquals(0, gameController.getGame().getTable().getMotherNaturePosition());
            gameController.getGame().getPlayerByIndex(0).setCurrentAssistantCard(gameController.getGame().getAssistantCard(4));
            gameController.moveMotherNature("Viola", 2);
            assertEquals(1, gameController.getGame().getTable().getGroupIslandByIndex(2).getNumberOfNoEntryTile());
            assertTrue(gameController.getGame().getTable().getGroupIslandByIndex(2).isNoEntryTile());

        }
    }
}