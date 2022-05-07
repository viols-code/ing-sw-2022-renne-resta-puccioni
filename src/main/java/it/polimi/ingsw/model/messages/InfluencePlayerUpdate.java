package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

public class InfluencePlayerUpdate extends GroupIslandUpdate {
    @Serial
    private static final long serialVersionUID = 8611475659820921831L;
    private final String player;
    private final int groupIsland;
    public InfluencePlayerUpdate(String player, int grouIsland) {
        this.player = player;
        this.groupIsland = grouIsland;
    }

    @Override
    public void process(View view) {

    }
}
