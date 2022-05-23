package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.HashMap;

/**
 * Update: the students in a player's entrance have changed
 */
public class EntranceUpdate extends PlayerUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 4422435658892342341L;
    /**
     * The nickname of the player
     */
    private final String playerName;
    /**
     * The HashMap with the students in the entrance
     */
    private final HashMap<Colour, Integer> students;

    /**
     * Constructor
     *
     * @param playerName the nickname of the player
     * @param students   the HashMap with the students in the entrance
     */
    public EntranceUpdate(String playerName, HashMap<Colour, Integer> students) {
        this.playerName = playerName;
        this.students = students;
    }

    /**
     * Update the view with the entrance of the player
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateEntrance(playerName, students);
    }
}
