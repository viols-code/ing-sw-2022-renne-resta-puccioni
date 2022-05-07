package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.HashMap;

public class StudentToEntranceUpdate extends CardUpdate {
    @Serial
    private static final long serialVersionUID = 928675659820901831L;
    private final HashMap<Colour, Integer> students;

    public StudentToEntranceUpdate(HashMap<Colour, Integer> students) {
        this.students = students;
    }

    @Override
    public void process(View view) {

    }
}
