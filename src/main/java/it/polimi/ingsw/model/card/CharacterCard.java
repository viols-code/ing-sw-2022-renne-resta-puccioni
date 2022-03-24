package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.island.GroupIsland;
import it.polimi.ingsw.model.table.island.SingleIsland;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public void setting() {

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
     * If the influence on the group island selected is changed calls the method changeInfluenceGroupIsland to update the state
     *
     * @param groupIsland the groupIsland
     */
    public void calculateInfluence(int groupIsland) {
        HashMap<Player, Integer> scores = new HashMap<>();
        List<Player> res = new ArrayList<>();
        for (int i = 0; i < game.getNumberOfPlayer(); i++) {
            if (!game.getPlayerByIndex(i).equals(game.getTable().getGroupIslandByIndex(groupIsland).getInfluence()))
                scores.put(game.getPlayerByIndex(i), calculateInfluencePlayer(game.getPlayerByIndex(i), game.getTable().getGroupIslandByIndex(groupIsland)));
        }

        Integer maxInfluence = scores.values().stream().reduce(0, (y1, y2) -> {
            if (y1 > y2) return y1;
            else return y2;
        });

        res = scores.entrySet()
                .stream()
                .filter(x -> x.getValue().equals(maxInfluence))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());


        if (scores.size() == 1) {
            changeInfluenceGroupIsland(res.get(0), groupIsland);
        }
    }

    /**
     * Changes the influence on a group island
     * Unifies islands when necessary
     * If the new influence player has finished his towers sets him as the winner
     *
     * @param groupIsland     the groupIsland selected
     * @param influencePlayer the new influence player of the group island selected
     */
    public void changeInfluenceGroupIsland(Player influencePlayer, int groupIsland) {
        if (game.getTable().getGroupIslandByIndex(groupIsland).getInfluence() == null) {
            game.getTable().getGroupIslandByIndex(groupIsland).changeInfluence(influencePlayer);
            if (influencePlayer.getSchoolBoard().getTowers() - game.getTable().getGroupIslandByIndex(groupIsland).getNumberOfSingleIsland() <= 0) {
                game.setWinner(influencePlayer);

            } else {
                influencePlayer.getSchoolBoard().removeTower(game.getTable().getGroupIslandByIndex(groupIsland).getNumberOfSingleIsland());
            }
        } else if (!(game.getTable().getGroupIslandByIndex(groupIsland).getInfluence().equals(influencePlayer))) {
            game.getTable().getGroupIslandByIndex(groupIsland).getInfluence().getSchoolBoard().addTower(game.getTable().getGroupIslandByIndex(groupIsland).getNumberOfSingleIsland());
            game.getTable().getGroupIslandByIndex(groupIsland).changeInfluence(influencePlayer);
            if (influencePlayer.getSchoolBoard().getTowers() - game.getTable().getGroupIslandByIndex(groupIsland).getNumberOfSingleIsland() <= 0) {
                game.setWinner(influencePlayer);

            } else {
                influencePlayer.getSchoolBoard().removeTower(game.getTable().getGroupIslandByIndex(groupIsland).getNumberOfSingleIsland());
            }
        }
        checkUnifyIsland(groupIsland);
    }


    /**
     * Checks if the current player can take the control of the professor selected
     *
     * @param colour the colour of the professor selected
     */
    public void checkProfessor(Colour colour) {
        for (int i = 0; i < game.getNumberOfPlayer(); i++) {
            if (game.getCurrentPlayer().getSchoolBoard().getDiningRoom(colour) > game.getPlayerByIndex(i).getSchoolBoard().getDiningRoom(colour)) {
                game.getCurrentPlayer().getSchoolBoard().hasProfessor(colour);
            }
        }
    }

    /**
     * Checks if mother nature can do the steps required from the player
     *
     * @param player   the player who wants to move mother nature
     * @param movement the steps required for mother nature
     * @return true if mother nature can do the steps required
     */
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
    public void setGroupIsland(int groupIsland) {

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

    /**
     * Checks if the selected group island is unifiable with adjacent ones
     *
     * @param groupIsland the group island selected
     */
    public void checkUnifyIsland(int groupIsland) {

        if (game.getTable().getIslandAfter(groupIsland).getInfluence().equals(game.getTable().getGroupIslandByIndex(groupIsland).getInfluence())) {
            unifyGroupIsland(game.getTable().getGroupIslandByIndex(groupIsland), game.getTable().getIslandAfter(groupIsland));
        }

        if (game.getTable().getIslandBefore(groupIsland).getInfluence().equals(game.getTable().getGroupIslandByIndex(groupIsland).getInfluence())) {
            unifyGroupIsland(game.getTable().getGroupIslandByIndex(groupIsland), game.getTable().getIslandBefore(groupIsland));
        }

    }

}
