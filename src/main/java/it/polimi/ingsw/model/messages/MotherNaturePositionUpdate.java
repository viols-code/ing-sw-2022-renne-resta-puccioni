package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

public class MotherNaturePositionUpdate extends TableUpdate {

    @Serial
    private static final long serialVersionUID = 7611410559820901831L;
    private final int movement;

    public MotherNaturePositionUpdate(int movement) {
        this.movement = movement;
    }

    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateMotherNaturePosition(movement);
    }

}
