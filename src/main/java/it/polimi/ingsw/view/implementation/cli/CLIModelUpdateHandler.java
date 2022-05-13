package it.polimi.ingsw.view.implementation.cli;

import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.view.ModelUpdateHandler;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.implementation.cli.utils.ViewString;

public class CLIModelUpdateHandler extends ModelUpdateHandler {
    /**
     * Constructs a new ModelUpdateHandler.
     *
     * @param view the view responsible for this model update handler
     */
    protected CLIModelUpdateHandler(View view) {
        super(view);
    }

    @Override
    public void updateCurrentPlayer(String currentPlayer){
        super.updateCurrentPlayer(currentPlayer);
        if(getView().getModel().getLocalPlayer().getNickname().equalsIgnoreCase(currentPlayer)){
            getView().getRenderer().showGameMessage(ViewString.OWN_TURN);
        } else {
            getView().getRenderer().showGameMessage(ViewString.OTHER_TURN.formatted(currentPlayer));
        }
    }

    public void updateTurnPhase(TurnPhase turnPhase){
        super.updateTurnPhase(turnPhase);
        if(turnPhase == TurnPhase.WAITING){
            return;
        }
        if(getView().getModel().getLocalPlayer().getNickname().equalsIgnoreCase(getView().getModel().getCurrentPlayer().getNickname())){
            switch (getView().getModel().getTurnPhase()){
                case PLAY_ASSISTANT_CARD -> getView().getRenderer().showGameMessage(ViewString.SELECT_ASSISTANT_CARD);
                case MOVE_STUDENT -> getView().getRenderer().showGameMessage(ViewString.MOVE_STUDENT_FROM_ENTRANCE);
                case MOVE_MOTHER_NATURE -> getView().getRenderer().showGameMessage(ViewString.MOVE_MOTHER_NATURE);
                case CHOOSE_CLOUD_TILE -> getView().getRenderer().showGameMessage(ViewString.SELECT_CLOUD_TILE);
            }
        }
    }

    @Override
    public void updateWinner(String player){
        super.updateWinner(player);
        if(getView().getModel().getLocalPlayer().getNickname().equalsIgnoreCase(player)){
            getView().getRenderer().showGameMessage(ViewString.YOU_WINNER);
        } else {
            getView().getRenderer().showGameMessage(ViewString.OTHER_WINNER.formatted(getView().getModel().getWinner().getNickname()));
        }
    }

    @Override
    public void updateInfluencePlayerOnGroupIsland(String player, int groupIsland){
        super.updateInfluencePlayerOnGroupIsland(player, groupIsland);
        if(getView().getModel().getLocalPlayer().getNickname().equalsIgnoreCase(player)){
            getView().getRenderer().showGameMessage(ViewString.YOU_INFLUENCE_PLAYER.formatted(groupIsland));
        } else {
            getView().getRenderer().showGameMessage(ViewString.OTHER_INFLUENCE_PLAYER.formatted(player, groupIsland));
        }
    }

    @Override
    public void updateUnifyIsland(int groupIsland1, int groupIsland2){
        super.updateUnifyIsland(groupIsland1, groupIsland2);
        getView().getRenderer().showGameMessage(ViewString.UNIFY_ISLANDS.formatted(groupIsland1, groupIsland2));
    }

    @Override
    public void updateCurrentAssistantCard(String player, int assistantCard){
        super.updateCurrentAssistantCard(player, assistantCard);
        if(getView().getModel().getLocalPlayer().getNickname().equalsIgnoreCase(player)){
            getView().getRenderer().showGameMessage(ViewString.YOU_SELECTED_ASSISTANT_CARD.formatted(assistantCard - 1));
        } else {
            getView().getRenderer().showGameMessage(ViewString.OTHER_SELECTED_ASSISTANT_CARD.formatted(player, assistantCard - 1));
        }
    }

    @Override
    public void updateMotherNaturePosition(int motherNaturePosition){
        super.updateMotherNaturePosition(motherNaturePosition);
        if(getView().getModel().getGamePhase() != GamePhase.SETTING){
            if(getView().getModel().getLocalPlayer().getNickname().equalsIgnoreCase(getView().getModel().getCurrentPlayer().getNickname())){
                getView().getRenderer().showGameMessage(ViewString.YOU_SELECTED_MOTHER_NATURE_MOVEMENT.formatted(motherNaturePosition));
            } else {
                getView().getRenderer().showGameMessage(ViewString.OTHER_SELECTED_MOTHER_NATURE_MOVEMENT.formatted(getView().getModel().getCurrentPlayer().getNickname(), motherNaturePosition));
            }
        }
    }

    @Override
    public void updateRound(int round){
        super.updateRound(round);
        if(round < 11){
            getView().getRenderer().showGameMessage(ViewString.ROUND.formatted(round));
        }
    }


}
