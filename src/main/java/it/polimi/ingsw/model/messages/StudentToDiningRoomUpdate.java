package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.HashMap;

public class StudentToDiningRoomUpdate extends CardUpdate{
    @Serial
    private static final long serialVersionUID = -7699975659820901831L;

    private final HashMap<Colour,Integer> students;

    public StudentToDiningRoomUpdate(HashMap<Colour,Integer> students){
        this.students = students;
    }

    @Override
    public void process(View view){

    }
}
