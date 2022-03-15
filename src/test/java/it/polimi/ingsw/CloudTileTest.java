package it.polimi.ingsw;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloudTileTest {
    private Game game;
    private CloudTile cloudTile;

    @BeforeEach
    void setUp() {
        game = new Game();
        cloudTile = new CloudTile(game.bagDrawCloudTile());
    }

    @Test
    void getTileStudents() {
        int count = 0;
        for(Colour colour:Colour.values()){
            count += cloudTile.getTileStudents(colour);
        }

        assertEquals(count, game.getStudentNumberMovement());
    }
}