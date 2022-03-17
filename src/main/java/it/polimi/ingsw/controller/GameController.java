package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.game.BasicGame;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.island.GroupIsland;

import java.util.HashMap;

public class GameController {
    private final Game game;
    private final boolean isGameExpert;
    private State state;
    private final HashMap<CharacterCard, State> stateMatch;
    private final HashMap<State, CharacterCard> cardMatch;

    public GameController(boolean isGameExpert){
        this.isGameExpert = isGameExpert;

        if(isGameExpert){
            game = new ExpertGame();
        } else {
            game = new BasicGame();
        }

        stateMatch = new HashMap<>();
        cardMatch = new HashMap<>();
    }

    public Game getGame() {
        return game;
    }

    public CharacterCard getCard(){
        return cardMatch.get(state);
    }

    private void unifyGroupIsland(GroupIsland groupIsland1, GroupIsland groupIsland2){

        for(int i = 0; i < groupIsland2.getNumberOfSingleIsland(); i++){
            groupIsland1.addSingleIsland(groupIsland2.getIslands(i));
        }
    }

}
