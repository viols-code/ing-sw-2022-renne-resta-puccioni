package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * Update: Mother Nature position has changed due to a unification
 */
public class MotherNaturePositionUnifyUpdate extends TableUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 8711410559820901381L;
    /**
     * The actual position of mother nature
     */
    private final int movement;

    /**
     * Constructor
     *
     * @param movement the position of mother nature
     */
    public MotherNaturePositionUnifyUpdate(int movement) {
        this.movement = movement;
    }

    /**
     * Update the view with the actual position of mother nature
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateMotherNaturePositionUnify(movement);
    }
}
