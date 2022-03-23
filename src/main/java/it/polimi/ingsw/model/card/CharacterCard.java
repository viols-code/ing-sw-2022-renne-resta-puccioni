package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.island.GroupIsland;
import it.polimi.ingsw.model.table.island.SingleIsland;

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

    public Player calculateInfluence(GroupIsland groupIsland) {

       /* HashMap<Player, Integer> scores = new HashMap<>();

        for(int i = 0; i < numberOfPlayer; i++){
            scores.put(game.getPlayerByIndex(i), game.getActiveCharacterCard().calculateInfluence(game.getPlayerByIndex(i), groupIsland));
        }

        //Player player = scores.entrySet().stream(); ritorna il giocatore con la massima influenza*/
        return game.getCurrentPlayer();
    }

    /**
     * change the influence on a group island
     *
     * @param num
     */
    public void changeInfluenceGroupIsland(int num) {

    }


    public void checkProfessor(Colour colour) {
        for (int i = 0; i < game.getNumberOfPlayer(); i++) {
            if (game.getCurrentPlayer().getSchoolBoard().getDiningRoom(colour) > game.getPlayerByIndex(i).getSchoolBoard().getDiningRoom(colour)) {
                game.getCurrentPlayer().getSchoolBoard().hasProfessor(colour);
            }
        }
    }

    public boolean checkMotherNatureMovement(int player, int movement) {
        if (game.getPlayerByIndex(player).getCurrentAssistantCard().getMotherNatureMovement() >= movement) {
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
    public void setColourAndIsland(Colour colour, SingleIsland island) {

    }


    /**
     * Set the island to choose
     *
     * @param groupIsland
     */
    public void setGroupIsland(GroupIsland groupIsland) {

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

    public void checkUnifyIsland(int groupIsland) {

        if (game.getTable().getIslandAfter(groupIsland).getInfluence().equals(game.getTable().getGroupIslandByIndex(groupIsland).getInfluence())) {
            unifyGroupIsland(game.getTable().getGroupIslandByIndex(groupIsland), game.getTable().getIslandAfter(groupIsland));
        }

        if (game.getTable().getIslandBefore(groupIsland).getInfluence().equals(game.getTable().getGroupIslandByIndex(groupIsland).getInfluence())) {
            unifyGroupIsland(game.getTable().getGroupIslandByIndex(groupIsland), game.getTable().getIslandBefore(groupIsland));
        }

    }

}
