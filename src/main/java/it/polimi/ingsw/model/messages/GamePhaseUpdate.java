package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.view.View;

import java.io.Serial;

public class GamePhaseUpdate extends GameUpdate {
    @Serial
    private static final long serialVersionUID = 7187510051232342083L;
    private final GamePhase gamePhase;

    public GamePhaseUpdate(GamePhase gamePhase) {
        this.gamePhase = gamePhase;
    }


    @Override
    public void process(View view) {

    }
}
