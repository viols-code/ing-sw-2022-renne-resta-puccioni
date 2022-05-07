package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.messages.CardCoinsUpdate;
import it.polimi.ingsw.model.messages.InfluencePlayerUpdate;
import it.polimi.ingsw.model.messages.NoEntryTileUpdate;
import it.polimi.ingsw.model.messages.UnifyIslandsUpdate;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.table.island.GroupIsland;
import it.polimi.ingsw.model.table.island.SingleIsland;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.IServerPacket;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class CharacterCard extends Observable<IServerPacket> {
    /**
     * The Game
     */
    protected Game game;
    /**
     * Cost of the card
     */
    protected int actualCost;
    /**
     * Type of the character card
     */
    protected CharacterCardEnumeration type;

    /**
     * Constructor: create a new CharacterCard
     *
     * @param game the Game
     */
    public CharacterCard(Game game) {
        this.game = game;
    }
    /*
    TYPE
     */

    /**
     * Gets the type of the character card
     *
     * @return the type
     */
    public CharacterCardEnumeration getCharacterCardType() {
        return type;
    }

    /*
    SETTING
     */

    /**
     * Set the state of the card
     */
    public void setting() {
    }

    /*
    COINS
     */

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
        notify(new CardCoinsUpdate(game.getCharacterCardIndex(this), actualCost));
    }

    /*
    EFFECT
     */

    /**
     * Activates the effect of the CharacterCard
     */
    protected void effect() {
    }


    /*
    INFLUENCE
     */

    /**
     * Calculates influence of the given Player in the given GroupIsland
     *
     * @param player      the Player
     * @param groupIsland the GroupIsland
     * @return influence of the given Player in the given GroupIsland
     */
    public int calculateInfluencePlayer(Player player, GroupIsland groupIsland) {
        int influence = 0;

        for (Colour colour : Colour.values()) {
            if (player.getSchoolBoard().hasProfessor(colour)) {
                influence += groupIsland.getNumberStudent(colour);
            }
        }

        if (player.equals(groupIsland.getInfluence())) {
            influence += groupIsland.getNumberOfSingleIsland();
        }

        return influence;
    }

    /**
     * If the influence on the group island selected is changed calls the method changeInfluenceGroupIsland to update the state
     *
     * @param groupIsland the groupIsland
     */
    public void calculateInfluence(int groupIsland) {
        if (!game.getTable().getGroupIslandByIndex(groupIsland).isNoEntryTile()) {
            HashMap<Player, Integer> scores = new HashMap<>();
            List<Player> res;
            for (int i = 0; i < game.getNumberOfPlayer(); i++) {
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


            if (res.size() == 1) {
                changeInfluenceGroupIsland(res.get(0), groupIsland);
            }
        } else {
            game.getTable().getGroupIslandByIndex(groupIsland).removeNoEntryTile();
            notify(new NoEntryTileUpdate(groupIsland,game.getTable().getGroupIslandByIndex(groupIsland).getNumberOfNoEntryTile()));
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
            setNewInfluencePlayer(influencePlayer, groupIsland);
        } else if (!(game.getTable().getGroupIslandByIndex(groupIsland).getInfluence().equals(influencePlayer))) {
            game.getTable().getGroupIslandByIndex(groupIsland).getInfluence().getSchoolBoard().addTower(game.getTable().getGroupIslandByIndex(groupIsland).getNumberOfSingleIsland());
            setNewInfluencePlayer(influencePlayer, groupIsland);
        }
        checkUnifyIsland(groupIsland);
    }

    /**
     * Set a new influence on an island
     *
     * @param influencePlayer the Player that influences the island
     * @param groupIsland     GroupIsland
     */
    private void setNewInfluencePlayer(Player influencePlayer, int groupIsland) {
        game.getTable().getGroupIslandByIndex(groupIsland).changeInfluence(influencePlayer);
        notify(new InfluencePlayerUpdate(influencePlayer.getNickname(),groupIsland));
        if (influencePlayer.getSchoolBoard().getTowers() - game.getTable().getGroupIslandByIndex(groupIsland).getNumberOfSingleIsland() <= 0) {
            influencePlayer.getSchoolBoard().removeTower(influencePlayer.getSchoolBoard().getTowers());
            game.setWinner(influencePlayer);
        } else {
            influencePlayer.getSchoolBoard().removeTower(game.getTable().getGroupIslandByIndex(groupIsland).getNumberOfSingleIsland());
        }
    }


    /*
    PROFESSOR
     */

    /**
     * Checks if the current player can take the control of the professor selected
     *
     * @param colour the colour of the professor selected
     */
    public void checkProfessor(Colour colour) {
        boolean control = true;
        for (int i = 0; i < game.getNumberOfPlayer(); i++) {
            if (game.getPlayerByIndex(i).getSchoolBoard().hasProfessor(colour) && !game.getPlayerByIndex(i).equals(game.getCurrentPlayer())) {
                control = false;
                if (game.getCurrentPlayer().getSchoolBoard().getDiningRoom(colour) > game.getPlayerByIndex(i).getSchoolBoard().getDiningRoom(colour)) {
                    game.getCurrentPlayer().getSchoolBoard().addProfessor(colour);
                    game.getPlayerByIndex(i).getSchoolBoard().removeProfessor(colour);
                }
            }
        }

        if (control) {
            boolean check = true;
            for (int i = 0; i < game.getNumberOfPlayer(); i++) {
                if (game.getCurrentPlayer().getSchoolBoard().getDiningRoom(colour) <= game.getPlayerByIndex(i).getSchoolBoard().getDiningRoom(colour)
                        && !game.getPlayerByIndex(i).equals(game.getCurrentPlayer())) {
                    check = false;
                }
            }
            if (check) {
                game.getCurrentPlayer().getSchoolBoard().addProfessor(colour);
            }
        }
    }

    /**
     * Checks if the current player can take the control of the professors
     */
    public void professor() {
    }

    /*
    MOTHER NATURE
     */

    /**
     * Checks if mother nature can do the steps required from the player
     *
     * @param player   the player who wants to move mother nature
     * @param movement the steps required for mother nature
     * @return true if mother nature can do the steps required
     */
    public boolean checkMotherNatureMovement(int player, int movement) {
        return game.getPlayerByIndex(player).getCurrentAssistantCard().getMotherNatureMovement() >= movement;
    }

    /*
    STATE
     */

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
     * @throws IllegalAccessError if the CharacterCard doesn't have this method
     */
    public void setColourAndIsland(Colour colour, SingleIsland island) throws IllegalAccessError {
        throw new IllegalAccessError("The card doesn't have this method");
    }


    /**
     * Set the island to choose
     *
     * @param groupIsland the GroupIsland to be set
     * @throws IllegalAccessError if the CharacterCard doesn't have this method
     */
    public void setGroupIsland(int groupIsland) throws IllegalAccessError {
        throw new IllegalAccessError("The card doesn't have this method");
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

    /*
    UNIFY ISLANDS
     */

    /**
     * Unifies two GroupIsland
     *
     * @param groupIsland1 the first GroupIsland to be unified
     * @param groupIsland2 the second GroupIsland to be unified
     */
    protected void unifyGroupIsland(GroupIsland groupIsland1, GroupIsland groupIsland2) {
        for (int i = 0; i < groupIsland2.getNumberOfSingleIsland(); i++) {
            groupIsland1.addSingleIsland(groupIsland2.getIslandByIndex(i));
            if (groupIsland2.isNoEntryTile()) {
                for (int j = 0; j < groupIsland2.getNumberOfNoEntryTile(); j++) {
                    groupIsland1.addNoEntryTile();
                }
            }
        }
        game.getTable().removeGroupIsland(groupIsland2);
    }

    /**
     * Checks if the selected group island is unifiable with adjacent ones
     *
     * @param groupIsland the group island selected
     */
    protected void checkUnifyIsland(int groupIsland) {
        if (game.getTable().getGroupIslandByIndex(groupIsland).getInfluence().equals(game.getTable().getIslandAfter(groupIsland).getInfluence())) {
            if (groupIsland == game.getTable().getNumberOfGroupIsland() - 1) {
                game.getTable().setMotherNaturePosition(0);
                unifyGroupIsland(game.getTable().getIslandAfter(groupIsland), game.getTable().getGroupIslandByIndex(groupIsland));
                notify(new UnifyIslandsUpdate(0, groupIsland));
                groupIsland = 0;
            } else {
                unifyGroupIsland(game.getTable().getGroupIslandByIndex(groupIsland), game.getTable().getIslandAfter(groupIsland));
                notify(new UnifyIslandsUpdate(groupIsland, (groupIsland + 1)));
            }
        }

        if (game.getTable().getGroupIslandByIndex(groupIsland).getInfluence().equals(game.getTable().getIslandBefore(groupIsland).getInfluence())) {
            if (groupIsland > 0) {
                game.getTable().setMotherNaturePosition(groupIsland - 1);
                unifyGroupIsland(game.getTable().getIslandBefore(groupIsland), game.getTable().getGroupIslandByIndex(groupIsland));
                notify(new UnifyIslandsUpdate(groupIsland - 1, groupIsland));
            } else {
                unifyGroupIsland(game.getTable().getGroupIslandByIndex(groupIsland), game.getTable().getIslandBefore(groupIsland));
                notify(new UnifyIslandsUpdate(groupIsland, game.getTable().getNumberOfGroupIsland() - 1));
            }

        }
    }

}
