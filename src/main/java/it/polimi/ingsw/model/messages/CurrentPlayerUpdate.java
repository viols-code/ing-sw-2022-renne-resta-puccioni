package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

public class CurrentPlayerUpdate extends GameUpdate {
    @Serial
    private static final long serialVersionUID = 1037513789242382023L;
    private final String currentPlayer;

    public CurrentPlayerUpdate(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateCurrentPlayer(currentPlayer);
    }
}
