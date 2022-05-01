package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.List;

public class PlayersUpdate extends GameUpdate{
    @Serial
    private static final long serialVersionUID = 2891435710961382023L;
    private final List<Player> players;

    public PlayersUpdate(List<Player> players){
        this.players = players;
    }


    @Override
    public void process(View view) {

    }
}
