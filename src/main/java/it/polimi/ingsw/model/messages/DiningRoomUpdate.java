package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.HashMap;

/**
 * Update: the students in a player's dining room have changed
 */
public class DiningRoomUpdate extends PlayerUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 5500455658898702341L;
    /**
     * The nickname of the player
     */
    private final String playerName;
    /**
     * The HashMap with the students in the dining room
     */
    private final HashMap<Colour, Integer> students;

    /**
     * Constructor
     *
     * @param playerName the nickname of the player
     * @param students the students in the dining room
     */
    public DiningRoomUpdate(String playerName, HashMap<Colour, Integer> students) {
        this.playerName = playerName;
        this.students = students;
    }

    /**
     * Update the view with the dining room of the player
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateDiningRoom(playerName, students);
    }
}
