package it.polimi.ingsw.view.implementation.cli;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.view.ModelUpdateHandler;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;
import it.polimi.ingsw.view.implementation.cli.utils.ViewString;

import java.util.HashMap;
import java.util.Locale;

public class CLIModelUpdateHandler extends ModelUpdateHandler {
    /**
     * Constructs a new ModelUpdateHandler.
     *
     * @param view the view responsible for this model update handler
     */
    protected CLIModelUpdateHandler(View view) {
        super(view);
    }

    /**
     * Updates the current player
     *
     * @param currentPlayer the nickname of the current player
     */
    @Override
    public void updateCurrentPlayer(String currentPlayer) {
        super.updateCurrentPlayer(currentPlayer);
        if (getView().getModel().getLocalPlayer().getNickname().equalsIgnoreCase(currentPlayer)) {
            getView().getRenderer().showGameMessage(ViewString.OWN_TURN);
        } else {
            getView().getRenderer().showGameMessage(ViewString.OTHER_TURN.formatted(currentPlayer));
        }
    }

    /**
     * Updates the turn phase
     *
     * @param turnPhase the new turn phase
     */
    @Override
    public void updateTurnPhase(TurnPhase turnPhase) {
        super.updateTurnPhase(turnPhase);
        if (turnPhase == TurnPhase.WAITING) {
            return;
        }
        if (getView().getModel().getLocalPlayer().getNickname().equalsIgnoreCase(getView().getModel().getCurrentPlayer().getNickname())) {
            switch (getView().getModel().getTurnPhase()) {
                case PLAY_ASSISTANT_CARD -> {
                    getView().getRenderer().showGameMessage(ViewString.SELECT_ASSISTANT_CARD);
                    getView().getRenderer().printAvailableAssistantCards();
                }
                case MOVE_STUDENT -> {
                    getView().getRenderer().showGameMessage(ViewString.MOVE_STUDENT_FROM_ENTRANCE);
                    getView().getRenderer().printLocalPlayerSchoolBoardHorizontal();
                    getView().getRenderer().printIslands();
                }
                case MOVE_MOTHER_NATURE -> {
                    getView().getRenderer().showGameMessage(ViewString.MOVE_MOTHER_NATURE);
                    getView().getRenderer().printAll();
                }
                case CHOOSE_CLOUD_TILE -> {
                    getView().getRenderer().printIslands();
                    System.out.println();
                    getView().getRenderer().showGameMessage(ViewString.SELECT_CLOUD_TILE);
                    getView().getRenderer().printCloudTiles();
                    getView().getRenderer().printLocalPlayerSchoolBoardHorizontal();
                }
            }
        }
    }

    /**
     * Updates the winner of the game
     *
     * @param player the nickname of the winner
     */
    @Override
    public void updateWinner(String player) {
        super.updateWinner(player);
        if (getView().getModel().getLocalPlayer().getNickname().equalsIgnoreCase(player)) {
            getView().getRenderer().showGameMessage(ViewString.YOU_WINNER);
        } else {
            getView().getRenderer().showGameMessage(ViewString.OTHER_WINNER.formatted(getView().getModel().getWinner().getNickname()));
        }
    }

    /**
     * Updates the influence player on the group island
     *
     * @param player      the nickname of the new influence player
     * @param groupIsland the index of the group island where influence changed
     */
    @Override
    public void updateInfluencePlayerOnGroupIsland(String player, int groupIsland) {
        if (getView().getModel().getTable().getGroupIslandByIndex(groupIsland).getInfluentPlayer() != null) {
            if (getView().getModel().getLocalPlayer().getNickname().equalsIgnoreCase(getView().getModel().getTable().getGroupIslandByIndex(groupIsland).getInfluentPlayer())) {
                getView().getRenderer().showGameMessage(ViewString.YOU_LOST_INFLUENCE.formatted(groupIsland));
            } else {
                getView().getRenderer().showGameMessage(ViewString.OTHER_LOST_INFLUENCE.formatted(getView().getModel().getTable().getGroupIslandByIndex(groupIsland).getInfluentPlayer(), groupIsland));
            }
        }
        super.updateInfluencePlayerOnGroupIsland(player, groupIsland);
        if (getView().getModel().getLocalPlayer().getNickname().equalsIgnoreCase(player)) {
            getView().getRenderer().showGameMessage(ViewString.YOU_INFLUENCE_PLAYER.formatted(groupIsland));
        } else {
            getView().getRenderer().showGameMessage(ViewString.OTHER_INFLUENCE_PLAYER.formatted(player, groupIsland));
        }
    }

    /**
     * Updates the ArrayList islands in MockTable according to the merge of the group island selected
     *
     * @param groupIsland1 the index of the first group island
     * @param groupIsland2 the index of the second group island
     */
    @Override
    public void updateUnifyIsland(int groupIsland1, int groupIsland2) {
        super.updateUnifyIsland(groupIsland1, groupIsland2);
        getView().getRenderer().showGameMessage(ViewString.UNIFY_ISLANDS.formatted(groupIsland1, groupIsland2));
    }

    /**
     * Updates the current assistant card of the selected player
     *
     * @param player        the nickname of the player
     * @param assistantCard the index of the assistant card played in this turn
     */
    @Override
    public void updateCurrentAssistantCard(String player, int assistantCard) {
        super.updateCurrentAssistantCard(player, assistantCard);
        if (getView().getModel().getLocalPlayer().getNickname().equalsIgnoreCase(player)) {
            getView().getRenderer().showGameMessage(ViewString.YOU_SELECTED_ASSISTANT_CARD.formatted(assistantCard - 1));
            getView().getRenderer().printLocalPlayerCurrentAssistantCard();
        } else {
            getView().getRenderer().showGameMessage(ViewString.OTHER_SELECTED_ASSISTANT_CARD.formatted(player, assistantCard - 1));
        }
    }

    /**
     * Updates the position of mother nature
     *
     * @param motherNaturePosition the integer representing the new position of mother nature
     */
    @Override
    public void updateMotherNaturePosition(int motherNaturePosition) {
        super.updateMotherNaturePosition(motherNaturePosition);
        if (getView().getModel().getGamePhase() != GamePhase.SETTING) {
            if (getView().getModel().getLocalPlayer().getNickname().equalsIgnoreCase(getView().getModel().getCurrentPlayer().getNickname())) {
                getView().getRenderer().showGameMessage(ViewString.YOU_SELECTED_MOTHER_NATURE_MOVEMENT.formatted(motherNaturePosition));
            } else {
                getView().getRenderer().showGameMessage(ViewString.OTHER_SELECTED_MOTHER_NATURE_MOVEMENT.formatted(getView().getModel().getCurrentPlayer().getNickname(), motherNaturePosition));
            }
        }
    }

    /**
     * Updates the round
     *
     * @param round the number of the current round
     */
    @Override
    public void updateRound(int round) {
        super.updateRound(round);
        if (round < 11) {
            getView().getRenderer().showGameMessage(ViewString.ROUND.formatted(round));
        }
    }

    /**
     * Updates the professor table on the school board of the player selected
     *
     * @param player         the nickname of the player
     * @param professorTable the hash map representing the updated professor table
     */
    @Override
    public void updateProfessorTable(String player, HashMap<Colour, Boolean> professorTable) {
        for (Colour colour : Colour.values()) {
            if (!professorTable.get(colour).equals(getView().getModel().getPlayerByNickname(player).getSchoolBoard().getProfessorTable().get(colour))) {
                if (getView().getModel().getLocalPlayer().getNickname().equalsIgnoreCase(player)) {
                    if (professorTable.get(colour)) {
                        getView().getRenderer().showGameMessage(ViewString.YOU_GOT_PROFESSORS.formatted(colour.name().toLowerCase(Locale.ROOT)));
                    } else {
                        getView().getRenderer().showGameMessage(ViewString.YOU_LOST_PROFESSORS.formatted(colour.name().toLowerCase(Locale.ROOT)));
                    }
                } else {
                    if (professorTable.get(colour)) {
                        getView().getRenderer().showGameMessage(ViewString.OTHER_GOT_PROFESSORS.formatted(player, colour.name().toLowerCase(Locale.ROOT)));
                    } else {
                        getView().getRenderer().showGameMessage(ViewString.OTHER_LOST_PROFESSORS.formatted(player, colour.name().toLowerCase(Locale.ROOT)));
                    }
                }
            }
        }

        //getView().getModel().getPlayerByNickname(player).getSchoolBoard().setProfessorTable(professorTable);
        super.updateProfessorTable(player, professorTable);
    }

    /**
     * Updates the position of mother nature after group islands merge
     *
     * @param motherNaturePosition the updated position of mother nature
     */
    @Override
    public void updateMotherNaturePositionUnify(int motherNaturePosition) {
        super.updateMotherNaturePositionUnify(motherNaturePosition);
        getView().getRenderer().showGameMessage(ViewString.MOTHER_NATURE_POSITION_UNIFY.formatted(motherNaturePosition));
    }

    /**
     * Updates the active character card in MockModel
     *
     * @param characterCard the new current character card
     */
    @Override
    public void updateActiveCharacterCard(CharacterCardEnumeration characterCard) {
        super.updateActiveCharacterCard(characterCard);
        if (characterCard == CharacterCardEnumeration.BASIC_STATE) {
            getView().getRenderer().showGameMessage(ViewString.BASIC_STATE);
        } else {
            if (getView().getModel().getLocalPlayer().getNickname().equalsIgnoreCase(getView().getModel().getCurrentPlayer().getNickname())) {
                getView().getRenderer().showGameMessage(ViewString.YOU_SET_ACTIVE_CHARACTER_CARD.formatted(characterCard.name().toLowerCase(Locale.ROOT)));
                getView().getRenderer().printActiveCharacterCard();

                switch (characterCard) {
                    case EXCHANGE_ENTRANCE_DINING_ROOM -> getView().getRenderer().showGameMessage(ViewString.ADVICE_EXCHANGE_DINING_ROOM_ENTRANCE.formatted(characterCard.name().toLowerCase(Locale.ROOT)));
                    case STUDENT_TO_ENTRANCE -> getView().getRenderer().showGameMessage(ViewString.ADVICE_STUDENT_TO_ENTRANCE.formatted(characterCard.name().toLowerCase(Locale.ROOT)));
                    case STUDENT_TO_DINING_ROOM, THREE_STUDENT, NO_COLOUR -> getView().getRenderer().showGameMessage(ViewString.ADVICE_SELECT_STUDENT_COLOUR.formatted(characterCard.name().toLowerCase(Locale.ROOT)));
                    case PROTECT_ISLAND, ISLAND_INFLUENCE -> getView().getRenderer().showGameMessage(ViewString.ADVICE_SELECT_GROUP_ISLAND.formatted(characterCard.name().toLowerCase(Locale.ROOT)));
                    case STUDENT_TO_ISLAND -> getView().getRenderer().showGameMessage(ViewString.ADVICE_STUDENT_TO_ISLAND.formatted(characterCard.name().toLowerCase(Locale.ROOT)));
                }
            } else {
                getView().getRenderer().showGameMessage(ViewString.OTHER_SET_ACTIVE_CHARACTER_CARD.formatted(getView().getModel().getCurrentPlayer().getNickname(), characterCard.name().toLowerCase(Locale.ROOT)));
            }
        }
    }

    /**
     * Updates the number of no entry tile on a group island
     *
     * @param groupIsland the index of the group island
     * @param num         the updated number of no entry tiles
     */
    @Override
    public void updateNoEntryTileOnGroupIsland(int groupIsland, int num) {
        if (getView().getModel().getTable().getGroupIslandByIndex(groupIsland).getNoEntryTile() < num) {
            if (!getView().getModel().getLocalPlayer().getNickname().equalsIgnoreCase(getView().getModel().getCurrentPlayer().getNickname())) {
                getView().getRenderer().showGameMessage(ViewString.OTHER_PROTECT.formatted(getView().getModel().getCurrentPlayer().getNickname(), groupIsland));
            } else {
                getView().getRenderer().showGameMessage(ViewString.YOU_PROTECT.formatted(groupIsland));

            }
        } else {
            getView().getRenderer().showGameMessage(ViewString.NO_INFLUENCE.formatted(groupIsland));
        }
        super.updateNoEntryTileOnGroupIsland(groupIsland, num);
    }

    /**
     * Shows a message on the CLI to let the players know that students are finished, so they are playing the last round
     */
    @Override
    public void updateEmptyBag() {
        getView().getRenderer().showGameMessage("The students are finished. This is the last round");
    }
}
