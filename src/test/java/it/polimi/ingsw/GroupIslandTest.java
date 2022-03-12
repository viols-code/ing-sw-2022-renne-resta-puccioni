package it.polimi.ingsw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupIslandTest {
    GroupIsland groupIslandTest;
    Player player1, player2;

    @BeforeEach
    void setUp() {
        groupIslandTest = new GroupIsland();
    }

    @Test
    void unifyIsland() {
    }

   /* @Test
    void changeInfluence() {
        player1 = new Player("Viola", game);
        player2 = new Player("Laura", game);

        for(int i = 0; i < game.getIslands().size(); i++){
            assertNull(game.getIslands().get(i).getInfluence());
        }

        game.getIslands().get(0).changeInfluence(player1);
        game.getIslands().get(1).changeInfluence(player2);

        Assertions.assertAll(
                () -> assertEquals(player1, game.getIslands().get(0).getInfluence()),
                () -> assertEquals(player2, game.getIslands().get(1).getInfluence())
        );

    }*/

    @Test
    void removeMotherNature() {
        assertFalse(groupIslandTest.getMotherNature());
        groupIslandTest.placeMotherNature();
        assertTrue(groupIslandTest.getMotherNature());
        groupIslandTest.removeMotherNature();
        assertFalse(groupIslandTest.getMotherNature());
    }

    @Test
    void placeMotherNature() {
        assertFalse(groupIslandTest.getMotherNature());
        groupIslandTest.placeMotherNature();
        assertTrue(groupIslandTest.getMotherNature());
    }

    @Test
    void calculateInfluence() {
    }

    @Test
    void calculateInfluenceWithoutColour() {
    }
}