package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.HashMap;

/**
 * Update: the students on the StudentToIsland card
 */
public class StudentToIslandUpdate extends CardUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 8311475659746901831L;
    /**
     * The students on the StudentToIsland card
     */
    private final HashMap<Colour, Integer> students;

    /**
     * Constructor
     *
     * @param students The students on the StudentToIsland card
     */
    public StudentToIslandUpdate(HashMap<Colour, Integer> students) {
        this.students = students;
    }

    /**
     * Update the view with the students on the StudentToIsland card
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateStudentToIslandCard(students);
    }
}
