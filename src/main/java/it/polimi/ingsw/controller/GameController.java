package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.game.BasicGame;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.island.GroupIsland;

public class GameController {
    private final Game game;
    private final boolean isGameExpert;

    public GameController(boolean isGameExpert) {
        this.isGameExpert = isGameExpert;

        if (isGameExpert) {
            game = new ExpertGame();
        } else {
            game = new BasicGame();
        }
    }

    public Game getGame() {
        return game;
    }

    private void unifyGroupIsland(GroupIsland groupIsland1, GroupIsland groupIsland2) {

        for (int i = 0; i < groupIsland2.getNumberOfSingleIsland(); i++) {
            groupIsland1.addSingleIsland(groupIsland2.getIslands(i));
        }
    }

}
