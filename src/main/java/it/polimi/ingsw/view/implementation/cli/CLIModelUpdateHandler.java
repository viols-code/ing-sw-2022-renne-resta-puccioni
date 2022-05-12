package it.polimi.ingsw.view.implementation.cli;

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
        getView().getRenderer().showGameMessage(ViewString.WINNER.formatted(getView().getModel().getWinner().getNickname()));
    }

    @Override
    public void updateInfluencePlayerOnGroupIsland(String player, int groupIsland){
        super.updateInfluencePlayerOnGroupIsland(player, groupIsland);
        getView().getRenderer().showGameMessage(ViewString.INFLUENCE_PLAYER.formatted(player, groupIsland));
    }

    @Override
    public void updateUnifyIsland(int groupIsland1, int groupIsland2){
        super.updateUnifyIsland(groupIsland1, groupIsland2);
        getView().getRenderer().showGameMessage(ViewString.UNIFY_ISLANDS.formatted(groupIsland1, groupIsland2));
    }


}
