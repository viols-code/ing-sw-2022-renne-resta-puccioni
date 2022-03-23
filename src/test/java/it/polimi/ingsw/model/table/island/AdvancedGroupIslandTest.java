package it.polimi.ingsw.model.table.island;

import it.polimi.ingsw.controller.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdvancedGroupIslandTest {
    private GameController gameController;


    @BeforeEach
    void setUp() {
        do {
            gameController = new GameController(true, 3);
        } while (!gameController.getGame().hasProtectIslandCard());
    }

    @Test
    void numberOfGroupIsland() {
        int i = 0;
        assertEquals(12, gameController.getGame().getTable().getNumberOfGroupIsland());
    }

    @Test
    void getNumberOfNoEntryTile() {
        assertEquals(0, gameController.getGame().getTable().getGroupIslandByIndex(0).getNumberOfNoEntryTile());
    }

    @Test
    void addNoEntryTile() {
        for (int i = 0; i < gameController.getGame().getTable().getNumberOfGroupIsland(); i++) {
            assertEquals(0, gameController.getGame().getTable().getGroupIslandByIndex(i).getNumberOfNoEntryTile());
        }
        gameController.getGame().getTable().getGroupIslandByIndex(0).addNoEntryTile();
        assertEquals(1, gameController.getGame().getTable().getGroupIslandByIndex(0).getNumberOfNoEntryTile());
    }

    @Test
    void removeNoEntryTile() {
        for (int i = 0; i < gameController.getGame().getTable().getNumberOfGroupIsland(); i++) {
            assertEquals(0, gameController.getGame().getTable().getGroupIslandByIndex(i).getNumberOfNoEntryTile());
        }
        gameController.getGame().getTable().getGroupIslandByIndex(0).addNoEntryTile();
        assertEquals(1, gameController.getGame().getTable().getGroupIslandByIndex(0).getNumberOfNoEntryTile());
        gameController.getGame().getTable().getGroupIslandByIndex(0).removeNoEntryTile();
        assertEquals(0, gameController.getGame().getTable().getGroupIslandByIndex(0).getNumberOfNoEntryTile());
    }
}