package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.HashMap;

public class CloudTileUpdate extends TableUpdate{
    @Serial
    private static final long serialVersionUID = 7611479959820901831L;
    private HashMap<Colour, Integer> students;

    public CloudTileUpdate(HashMap<Colour, Integer> students){
        this.students = students;
    }

    @Override
    public void process(View view){

    }
}
