package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;

public class SingleIslandUpdate extends TableUpdate {
    @Serial
    private static final long serialVersionUID = 7611475669820901831L;
    private Colour colour;

    public SingleIslandUpdate(Colour colour) {
        this.colour = colour;
    }

    @Override
    public void process(View view) {

    }
}
