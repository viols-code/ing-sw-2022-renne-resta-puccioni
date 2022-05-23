package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.HashMap;

/**
 * Update: the students on the StudentToDiningRoom card
 */
public class StudentToDiningRoomUpdate extends CardUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -7699975659820901831L;
    /**
     * The HashMap with the students on the card
     */
    private final HashMap<Colour, Integer> students;

    /**
     * Constructor
     *
     * @param students the HashMap with the students on the card
     */
    public StudentToDiningRoomUpdate(HashMap<Colour, Integer> students) {
        this.students = students;
    }

    /**
     * Update the view with the students on the StudentToDiningRoom card
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateStudentToDiningRoomCard(students);
    }
}
