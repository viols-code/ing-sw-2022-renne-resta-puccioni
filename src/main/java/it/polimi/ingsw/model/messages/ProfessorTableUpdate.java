package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.HashMap;

/**
 * Update: a player's professor table has changed
 */
public class ProfessorTableUpdate extends PlayerUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 3311215655398702341L;
    /**
     * The nickname of the player
     */
    private final String playerName;
    /**
     * The HashMap of player's professor table
     */
    private final HashMap<Colour, Boolean> professors;

    /**
     * Constructor
     *
     * @param playerName the player's nickname
     * @param professors the player's professor table
     */
    public ProfessorTableUpdate(String playerName, HashMap<Colour, Boolean> professors) {
        this.playerName = playerName;
        this.professors = professors;
    }

    /**
     * Update the view with the player's professor table
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateProfessorTable(playerName, professors);
    }
}
