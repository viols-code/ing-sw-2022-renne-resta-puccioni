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
    private final Game game;
    private final boolean isGameExpert;

    public GameController(boolean isGameExpert) {
        this.isGameExpert = isGameExpert;

        if (isGameExpert) {
            game = new ExpertGame();

            List<Integer> random = new ArrayList<>();
            for(int i = 0; i < 12; i++){
                random.add(i);
            }
            Collections.shuffle(random);

            boolean hasProtectIsland = false;
            for(int i = 0; i < 3; i++){
                switch (random.get(i)){
                    case 0:
                        game.addCharacterCard(new StudentToIsland());
                    case 1:
                        game.addCharacterCard(new TakeProfessor());
                    case 2:
                        game.addCharacterCard(new IslandInfluence());
                    case 3:
                        game.addCharacterCard(new MotherNatureMovement());
                    case 4:
                        game.addCharacterCard(new ProtectIsland());
                        hasProtectIsland = true;
                    case 5:
                        game.addCharacterCard(new NoTower(game));
                    case 6:
                        game.addCharacterCard(new StudentToEntrance());
                    case 7:
                        game.addCharacterCard(new TwoPoints());
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

            if(hasProtectIsland){
                for (int i = 0; i < 12; i++) {
                    game.getTable().addGroupIsland(new AdvancedGroupIsland());
                }
            } else{
                for (int i = 0; i < 12; i++) {
                    game.getTable().addGroupIsland(new BasicGroupIsland());
                }
            }


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
