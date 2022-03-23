package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.table.island.GroupIsland;
import it.polimi.ingsw.model.table.island.SingleIsland;
import it.polimi.ingsw.model.player.Player;

import java.util.*;
import java.util.stream.Collectors;

public abstract class CharacterCard {
    protected Game game;
    protected int initialCost;
    protected int actualCost;

    /**
     * Constructor: create a new CharacterCard
     *
     * @param game the Game
     */
    public CharacterCard(Game game) {
        this.game = game;
    }

    /**
     * Get the actualCost of the CharacterCard
     *
     * @return the actualCost of the CharacterCard
     */
    public int getCost() {
        return actualCost;
    }

    /**
     * Increments the actualCost by one
     */
    public void incrementCost() {
        actualCost += 1;
    }

    /**
     * Activates the effect of the CharacterCard
     */
    public void effect() {
    }

    /**
     * Calculates influence of the given Player in the given GroupIsland
     *
     * @param player      the Player
     * @param groupIsland the GroupIsland
     * @return influence of the given Player in the given GroupIsland
     */
    public int calculateInfluencePlayer(Player player, GroupIsland groupIsland) {
        int influence = 0;

        if (!groupIsland.isNoEntryTile()) {
            for (Colour colour : Colour.values()) {
                if (player.getSchoolBoard().hasProfessor(colour)) {
                    influence += groupIsland.getNumberStudent(colour);
                }
            }

            if (player.equals(groupIsland.getInfluence())) {
                influence += groupIsland.getNumberOfSingleIsland();
            }
        }

        return influence;
    }

    /**
     * Updates the influence of a group island if necessary
     *
     * @param groupIsland the groupIsland
     * @return
     */
    public void calculateInfluence(GroupIsland groupIsland){
        HashMap<Player,Integer> scores=new HashMap<>();
        List<Player> res=new ArrayList<>();
        for(int i=0;i<game.getNumberOfPlayer();i++){
            if(!game.getPlayerByIndex(i).equals(groupIsland.getInfluence()))
                scores.put(game.getPlayerByIndex(i),calculateInfluencePlayer(game.getPlayerByIndex(i),groupIsland));
        }

        Integer maxInfluence = scores.values().stream().reduce(0,(y1, y2)->{if(y1>y2)return y1;
                                                                                else return y2;});

        res = scores.entrySet()
                    .stream()
                    .filter(x -> x.getValue().equals(maxInfluence))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());


        if(scores.size()==1){
            changeInfluenceGroupIsland(res.get(0),groupIsland);
        }
    }

    /**
     * Checks if there is any change due to the change of the influence of the groupIsland selected
     * @param groupIsland
     */
    private void changeInfluenceGroupIsland(Player influencePlayer,GroupIsland groupIsland){
        if(groupIsland.getInfluence()==null){
            groupIsland.changeInfluence(influencePlayer);
            if(influencePlayer.getSchoolBoard().getTowers() - groupIsland.getNumberOfSingleIsland() <= 0){
                game.setWinner(influencePlayer);

            } else{
                influencePlayer.getSchoolBoard().removeTower(groupIsland.getNumberOfSingleIsland());
            }
        } else if(!(groupIsland.getInfluence().equals(influencePlayer))){
            groupIsland.getInfluence().getSchoolBoard().addTower(groupIsland.getNumberOfSingleIsland());
            groupIsland.changeInfluence(influencePlayer);
            if(influencePlayer.getSchoolBoard().getTowers() - groupIsland.getNumberOfSingleIsland() <= 0){
                game.setWinner(influencePlayer);

            } else{
                influencePlayer.getSchoolBoard().removeTower(groupIsland.getNumberOfSingleIsland());
            }
        }
    }


    public void checkProfessor(Colour colour) {
        for(int i = 0; i<game.getNumberOfPlayer(); i++){
            if (game.getCurrentPlayer().getSchoolBoard().getDiningRoom(colour) > game.getPlayerByIndex(i).getSchoolBoard().getDiningRoom(colour)) {
                game.getCurrentPlayer().getSchoolBoard().hasProfessor(colour);
            }
        }
    }

    public boolean checkMotherNatureMovement(int player, int movement) {
        if(game.getPlayerByIndex(player).getCurrentAssistantCard().getMotherNatureMovement() >= movement){
            return true;
        }

        return false;
    }

    /**
     * Set the colour
     *
     * @param colour the colour to be set
     * @throws IllegalAccessError if the CharacterCard doesn't have this method
     */
    public void setColour(Colour colour) throws IllegalAccessError {
        throw new IllegalAccessError("The card doesn't have this method");
    }

    /**
     * Set the island and the colour
     *
     * @param colour the colour chosen
     * @param island the island chosen
     */
    public void setColourAndIsland(Colour colour, SingleIsland island){

    }


    /**
     * Set the island to choose
     *
     * @param groupIsland
     */
    public void setGroupIsland(GroupIsland groupIsland){

    }

    /**
     * Set the colour
     *
     * @param colourDiningRoom the colour of the student in the diningRoom
     * @param colourEntrance   the colour of the student in the entrance
     * @throws IllegalAccessError if the CharacterCard doesn't have this method
     */
    public void setColourDiningRoomEntrance(Colour colourDiningRoom, Colour colourEntrance) throws IllegalAccessError {
        throw new IllegalAccessError("The card doesn't have this method");
    }

    /**
     * Set the colour
     *
     * @param colourCard     the colour of the student on the card
     * @param colourEntrance the colour of the student in the entrance
     * @throws IllegalAccessError if the CharacterCard doesn't have this method
     */
    public void setColourCardEntrance(Colour colourCard, Colour colourEntrance) throws IllegalAccessError {
        throw new IllegalAccessError("The card doesn't have this method");
    }

    /**
     * Unifies two GroupIsland
     *
     * @param groupIsland1 the first GroupIsland to be unified
     * @param groupIsland2 the second GroupIsland to be unified
     */
    private void unifyGroupIsland(GroupIsland groupIsland1, GroupIsland groupIsland2) {

        for (int i = 0; i < groupIsland2.getNumberOfSingleIsland(); i++) {
            groupIsland1.addSingleIsland(groupIsland2.getIslandByIndex(i));
        }
    }

    public void checkUnifyIsland(int groupIsland){

        if(game.getTable().getIslandAfter(groupIsland).getInfluence().equals(game.getTable().getGroupIslandByIndex(groupIsland).getInfluence())){
            unifyGroupIsland(game.getTable().getGroupIslandByIndex(groupIsland), game.getTable().getIslandAfter(groupIsland));
        }

        if(game.getTable().getIslandBefore(groupIsland).getInfluence().equals(game.getTable().getGroupIslandByIndex(groupIsland).getInfluence())){
            unifyGroupIsland(game.getTable().getGroupIslandByIndex(groupIsland), game.getTable().getIslandBefore(groupIsland));
        }

    }

}
