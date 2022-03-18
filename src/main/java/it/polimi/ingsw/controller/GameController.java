package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.game.BasicGame;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.island.AdvancedGroupIsland;
import it.polimi.ingsw.model.island.BasicGroupIsland;
import it.polimi.ingsw.model.island.GroupIsland;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController {
    /**
     * The Game
     */
    private final Game game;

    /**
     * Indicates if the game is in the expert mode
     */
    private final boolean isGameExpert;

    /**
     * Constructor: creates a GameController
     *
     * @param isGameExpert indicates if the game is in the expert mode
     */
    public GameController(boolean isGameExpert) {
        this.isGameExpert = isGameExpert;

        if (isGameExpert) {
            game = new ExpertGame();

            List<Integer> random = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                random.add(i);
            }
            Collections.shuffle(random);

            boolean hasProtectIsland = false;
            for (int i = 0; i < 3; i++) {
                switch (random.get(i)) {
                    case 0:
                        game.addCharacterCard(new StudentToIsland(game));
                    case 1:
                        game.addCharacterCard(new TakeProfessor(game));
                    case 2:
                        game.addCharacterCard(new IslandInfluence(game));
                    case 3:
                        game.addCharacterCard(new MotherNatureMovement(game));
                    case 4:
                        game.addCharacterCard(new ProtectIsland(game));
                        hasProtectIsland = true;
                    case 5:
                        game.addCharacterCard(new NoTower(game));
                    case 6:
                        game.addCharacterCard(new StudentToEntrance(game));
                    case 7:
                        game.addCharacterCard(new TwoPoints(game));
                    case 8:
                        game.addCharacterCard(new NoColour(game));
                    case 9:
                        game.addCharacterCard(new ExchangeEntranceDiningRoom(game));
                    case 10:
                        game.addCharacterCard(new StudentToDiningRoom(game));
                    case 11:
                        game.addCharacterCard(new ThreeStudent(game));
                }
            }

            if (hasProtectIsland) {
                for (int i = 0; i < 12; i++) {
                    game.getTable().addGroupIsland(new AdvancedGroupIsland());
                }
            } else {
                for (int i = 0; i < 12; i++) {
                    game.getTable().addGroupIsland(new BasicGroupIsland());
                }
            }


        } else {
            game = new BasicGame();
        }
    }

    /**
     * Unifies two GroupIsland
     *
     * @param groupIsland1 the first GroupIsland to be unified
     * @param groupIsland2 the second GroupIsland to be unified
     */
    private void unifyGroupIsland(GroupIsland groupIsland1, GroupIsland groupIsland2) {

        for (int i = 0; i < groupIsland2.getNumberOfSingleIsland(); i++) {
            groupIsland1.addSingleIsland(groupIsland2.getIslands(i));
        }
    }

}
