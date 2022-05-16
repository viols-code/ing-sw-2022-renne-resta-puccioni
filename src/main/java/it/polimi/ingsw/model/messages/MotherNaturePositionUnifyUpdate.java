package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.view.View;

import java.io.Serial;

public class MotherNaturePositionUnifyUpdate extends TableUpdate {
    @Serial
    private static final long serialVersionUID = 8711410559820901381L;
    private final int movement;

    public MotherNaturePositionUnifyUpdate(int movement) {
        this.movement = movement;
    }

    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateMotherNaturePositionUnify(movement);
    }
}
