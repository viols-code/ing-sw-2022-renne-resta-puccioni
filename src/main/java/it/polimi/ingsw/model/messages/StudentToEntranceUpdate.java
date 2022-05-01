package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;

public class StudentToEntranceUpdate extends CardUpdate{
    @Serial
    private static final long serialVersionUID = 928675659820901831L;

    private Colour colourEntrance;
    private Colour colourCard;

    public StudentToEntranceUpdate(Colour colourEntrance, Colour colourCard){
        this.colourCard = colourCard;
        this.colourEntrance = colourEntrance;
    }

    @Override
    public void process(View view){

    }
}
