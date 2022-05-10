package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.view.View;

import java.io.Serial;

public class TurnPhaseUpdate extends GameUpdate {
    @Serial
    private static final long serialVersionUID = 1437513789242382023L;
    private final TurnPhase turnPhase;

    public TurnPhaseUpdate(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
    }


    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateTurnPhase(turnPhase);
    }
}
