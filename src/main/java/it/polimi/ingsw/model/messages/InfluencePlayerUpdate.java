package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * Update: the influence on a GroupIsland has changed
 */
public class InfluencePlayerUpdate extends GroupIslandUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 8611475659820921831L;
    /**
     * The nickname of the player
     */
    private final String player;
    /**
     * The position of the group island in the List
     */
    private final int groupIsland;

    /**
     * Constructor
     *
     * @param player the nickname of influence player
     * @param groupIsland the group island
     */
    public InfluencePlayerUpdate(String player, int groupIsland) {
        this.player = player;
        this.groupIsland = groupIsland;
    }

    /**
     * Update the view with the new influence on the group island
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateInfluencePlayerOnGroupIsland(player, groupIsland);
    }
}
