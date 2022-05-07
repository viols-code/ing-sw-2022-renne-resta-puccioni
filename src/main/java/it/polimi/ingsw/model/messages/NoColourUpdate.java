package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;

public class NoColourUpdate extends CardUpdate {
    @Serial
    private static final long serialVersionUID = 9111746659820901831L;
    private final Colour colour;

    public NoColourUpdate(Colour colour) {
        this.colour = colour;
    }

    @Override
    public void process(View view) {

    }
}
