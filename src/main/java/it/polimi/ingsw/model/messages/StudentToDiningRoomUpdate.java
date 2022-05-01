package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;

public class StudentToDiningRoomUpdate extends CardUpdate{
    @Serial
    private static final long serialVersionUID = -7699975659820901831L;

    private Colour colour;

    public StudentToDiningRoomUpdate(Colour colour){
        this.colour = colour;
    }

    @Override
    public void process(View view){

    }
}
