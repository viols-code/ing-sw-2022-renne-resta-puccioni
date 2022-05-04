package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.HashMap;

public class StudentToIslandUpdate extends CardUpdate{
    @Serial
    private static final long serialVersionUID = 8311475659746901831L;
    private final HashMap<Colour,Integer> students;

    public StudentToIslandUpdate(HashMap<Colour,Integer> students){
        this.students = students;
    }

    @Override
    public void process(View view){

    }
}
