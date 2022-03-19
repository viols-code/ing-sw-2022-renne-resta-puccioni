package it.polimi.ingsw.model.island;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.BasicGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.BasicPlayer;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupIslandTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new BasicGame();
    }

    @Test
    void addSingleIsland() {
        assertEquals(12, game.getTable().getNumberOfGroupIsland());
        int number1 = game.getTable().getGroupIslandByIndex(0).getNumberStudent(Colour.BLUE);
        int number2 = game.getTable().getGroupIslandByIndex(1).getNumberStudent(Colour.BLUE);

        for(int i = 0; i < game.getTable().getNumberOfGroupIsland(); i++){
            assertEquals(1, game.getTable().getGroupIslandByIndex(i).getNumberOfSingleIsland());
        }

        game.getTable().getGroupIslandByIndex(0).addSingleIsland(game.getTable().getGroupIslandByIndex(1).getIslands(0));
        game.getTable().removeGroupIsland(game.getTable().getGroupIslandByIndex(1));

        assertEquals(2, game.getTable().getGroupIslandByIndex(0).getNumberOfSingleIsland());
        assertEquals(11, game.getTable().getNumberOfGroupIsland());
        assertEquals(number1 + number2,game.getTable().getGroupIslandByIndex(0).getNumberStudent(Colour.BLUE));
    }

    @Test
    void removeMotherNature() {
        assertTrue(game.getTable().getGroupIslandByIndex(0).getMotherNature());
        game.getTable().getGroupIslandByIndex(0).removeMotherNature();
        game.getTable().getGroupIslandByIndex(1).placeMotherNature();
        assertFalse(game.getTable().getGroupIslandByIndex(0).getMotherNature());
        assertTrue(game.getTable().getGroupIslandByIndex(1).getMotherNature());
    }

    @Test
    void placeMotherNature() {
        assertTrue(game.getTable().getGroupIslandByIndex(0).getMotherNature());
        game.getTable().getGroupIslandByIndex(0).removeMotherNature();
        game.getTable().getGroupIslandByIndex(2).placeMotherNature();
        assertFalse(game.getTable().getGroupIslandByIndex(0).getMotherNature());
        assertTrue(game.getTable().getGroupIslandByIndex(2).getMotherNature());
    }

    @Test
    void changeInfluence() {
        Player playerTest = new BasicPlayer("Viola", Wizard.TYPE_4);
        assertNull(game.getTable().getGroupIslandByIndex(0).getInfluence());
        game.getTable().getGroupIslandByIndex(0).changeInfluence(playerTest);
        assertEquals(playerTest, game.getTable().getGroupIslandByIndex(0).getInfluence());
    }

    @Test
    void isNoEntryTile() {
        for(int i = 0; i < game.getTable().getNumberOfGroupIsland(); i++){
            assertFalse(game.getTable().getGroupIslandByIndex(i).isNoEntryTile());
        }
    }

    @Test
    void getNumberOfNoEntryTile() {
        assertThrows(IllegalAccessError.class, () -> game.getTable().getGroupIslandByIndex(0).getNumberOfNoEntryTile());
    }

    @Test
    void addNoEntryTile() {
        assertThrows(IllegalAccessError.class, () -> game.getTable().getGroupIslandByIndex(1).addNoEntryTile());
    }

    @Test
    void removeNoEntryTile() {
        assertThrows(IllegalAccessError.class, () -> game.getTable().getGroupIslandByIndex(2).removeNoEntryTile());
    }
}