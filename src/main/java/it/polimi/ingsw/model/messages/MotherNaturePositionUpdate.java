package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * Update the position of mother nature
 */
public class MotherNaturePositionUpdate extends TableUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 7611410559820901831L;
    /**
     * The position of mother nature
     */
    private final int movement;

    /**
     * Constructor
     *
     * @param movement the position of mother nature
     */
    public MotherNaturePositionUpdate(int movement) {
        this.movement = movement;
    }

    /**
     * Update the view with the position of mother nature
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateMotherNaturePosition(movement);
    }

}
