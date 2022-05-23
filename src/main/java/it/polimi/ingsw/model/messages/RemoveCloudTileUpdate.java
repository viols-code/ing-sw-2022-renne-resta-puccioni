package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.HashMap;

/**
 * Update: a cloud tile has been chosen
 */
public class RemoveCloudTileUpdate extends TableUpdate {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 7611979959820901831L;
    /**
     * The cloud tile chosen
     */
    private final int cloudTile;
    /**
     * The students on the cloud tile
     */
    private final HashMap<Colour, Integer> students;

    /**
     * Constructor
     *
     * @param cloudTile the cloud tile chosen
     * @param students  the students on the cloud tile
     */
    public RemoveCloudTileUpdate(int cloudTile, HashMap<Colour, Integer> students) {
        this.cloudTile = cloudTile;
        this.students = students;
    }

    /**
     * Update the view with the cloud tile chosen
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateCloudTileRemoved(cloudTile, students);
    }


}
