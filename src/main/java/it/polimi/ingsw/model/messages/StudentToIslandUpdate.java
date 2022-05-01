package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;

public class StudentToIslandUpdate extends CardUpdate{
    @Serial
    private static final long serialVersionUID = 8311475659746901831L;
    private Colour colour;

    public StudentToIslandUpdate(Colour colour){
        this.colour = colour;
    }

    @Override
    public void process(View view){

    }
}
