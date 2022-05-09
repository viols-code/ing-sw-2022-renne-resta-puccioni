package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

public class RoundUpdate extends GameUpdate {
    @Serial
    private static final long serialVersionUID = -1089569123871352198L;
    private final int round;

    public RoundUpdate(int round) {
        this.round = round;
    }


    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateRound(round);
    }
}
