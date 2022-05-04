package it.polimi.ingsw.model.table;

import it.polimi.ingsw.IProcessablePacket;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.messages.CloudTileUpdate;
import it.polimi.ingsw.observer.Observable;

import java.util.HashMap;

/**
 * CloudTile
 *
 * @version 1.0
 */

public class CloudTile extends Observable<IProcessablePacket> {

    /**
     * A HashMap containing the students on the CloudTile
     */
    private final HashMap<Colour, Integer> tileStudents;

    /**
     * Constructor: creates a new CloudTile
     */
    public CloudTile(HashMap<Colour, Integer> cloudTile) {
        tileStudents = cloudTile;
        notify(new CloudTileUpdate(tileStudents));
    }

    /**
     * Get the students of the given colour that are on the CloudTile
     *
     * @param colour colour of the number of student we want to get
     * @return the number of students of the given colour on the CloudTile
     */
    public int getTileStudents(Colour colour) {
        return tileStudents.get(colour);
    }

}
