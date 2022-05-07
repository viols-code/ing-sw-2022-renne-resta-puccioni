package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

public class InfluencePlayerUpdate extends GroupIslandUpdate {
    @Serial
    private static final long serialVersionUID = 8611475659820921831L;
    private String player;

    public InfluencePlayerUpdate(String player) {
        this.player = player;
    }

    @Override
    public void process(View view) {

    }
}
