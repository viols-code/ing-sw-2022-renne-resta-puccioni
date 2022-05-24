package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.HashMap;

/**
 * Update: the students on the StudentToEntrance card
 */
public class StudentToEntranceUpdate extends CardUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 928675659820901831L;
    /**
     * The students on the StudentToEntrance card
     */
    private final HashMap<Colour, Integer> students;

    /**
     * Constructor
     *
     * @param students the students on the StudentToEntrance card
     */
    public StudentToEntranceUpdate(HashMap<Colour, Integer> students) {
        this.students = students;
    }

    /**
     * Update the view with the students on the StudentToEntrance card
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateStudentToEntranceCard(students);
    }
}
