package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * Update: two group islands have been unified
 */
public class UnifyIslandsUpdate extends GroupIslandUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -5511475659820901831L;
    /**
     * The first group island
     */
    private final int firstGroupIsland;
    /**
     * The second group island
     */
    private final int secondGroupIsland;

    /**
     * Constructor
     *
     * @param firstGroupIsland the first group island
     * @param secondGroupIsland the second group island
     */
    public UnifyIslandsUpdate(int firstGroupIsland, int secondGroupIsland) {
        this.firstGroupIsland = firstGroupIsland;
        this.secondGroupIsland = secondGroupIsland;
    }

    /**
     * Update the view with the unification of two group islands
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateUnifyIsland(firstGroupIsland, secondGroupIsland);
    }
}
