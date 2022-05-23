package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.HashMap;

/**
 * Update: a new Cloud Tile has been created
 */
public class NewCloudTileUpdate extends TableUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 7611479959820901831L;
    /**
     * The position of the cloud tile in the list
     */
    private final int cloudTile;
    /**
     * The HashMap with the students on the cloud tile
     */
    private final HashMap<Colour, Integer> students;

    /**
     * Constructor
     *
     * @param cloudTile the position of the cloud tile in the list
     * @param students  HashMap with the students on the cloud tile
     */
    public NewCloudTileUpdate(int cloudTile, HashMap<Colour, Integer> students) {
        this.cloudTile = cloudTile;
        this.students = students;
    }

    /**
     * Update the view with the new cloud tile
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateCloudTileAdded(cloudTile, students);
    }
}
