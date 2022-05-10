package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.HashMap;

public class ProfessorsUpdate extends TableUpdate {
    @Serial
    private static final long serialVersionUID = 3311215655398702341L;
    Colour colour;

    public ProfessorsUpdate(Colour colour) {
        this.colour = colour;
    }


    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateProfessors(colour);
    }
}
