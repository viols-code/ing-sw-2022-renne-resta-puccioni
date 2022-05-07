package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.List;

public class PlayersUpdate extends GameUpdate {
    @Serial
    private static final long serialVersionUID = 2891435710961382023L;
    private final String nickName;
    private final Wizard wizard;

    public PlayersUpdate(String nickName, Wizard wizard) {
        this.nickName = nickName;
        this.wizard = wizard;
    }


    @Override
    public void process(View view) {

    }
}
