package it.polimi.ingsw.model.table.island;

import it.polimi.ingsw.controller.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdvancedGroupIslandTest {
    private GameController gameController;
    private AdvancedGroupIsland groupIsland;


    @BeforeEach
    void setUp() {
        gameController = new GameController(true, 2);
    }

    @Test
    void checkSetting() {
        if (gameController.getGame().hasProtectIslandCard()) {
            assertEquals(12, gameController.getGame().getTable().getNumberOfGroupIsland());

            for (int i = 0; i < gameController.getGame().getTable().getNumberOfGroupIsland(); i++) {
                assertEquals(0, gameController.getGame().getTable().getGroupIslandByIndex(i).getNumberOfNoEntryTile());
            }
        }
    }


    @Test
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

    @Test
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
    void checkReoveNoEntryTile() {
        groupIsland = new AdvancedGroupIsland();
        assertEquals(0, groupIsland.getNumberOfNoEntryTile());
        groupIsland.addNoEntryTile();
        assertEquals(1, groupIsland.getNumberOfNoEntryTile());
        groupIsland.removeNoEntryTile();
        assertEquals(0, groupIsland.getNumberOfNoEntryTile());
    }
}