package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.table.island.BasicGroupIsland;

public class BasicGame extends Game {
    /**
     * Constructor: creates a game
     */
    public BasicGame() {
        super();
    }

    /**
     * Add the GroupIsland to the Game
     */
    public void setUp() {
        super.setUp();
        for (int i = 0; i < 12; i++) {
            getTable().addGroupIsland(new BasicGroupIsland());
        }
    }
}
