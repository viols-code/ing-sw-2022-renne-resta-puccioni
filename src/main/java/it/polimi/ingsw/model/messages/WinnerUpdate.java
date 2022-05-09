package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

public class WinnerUpdate extends GameUpdate {
    @Serial
    private static final long serialVersionUID = 2122435023892342341L;
    private final String winner;

    public WinnerUpdate(String winner) {
        this.winner = winner;
    }


    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateWinner(winner);
    }
}
