package it.polimi.ingsw.view.beans;

import it.polimi.ingsw.model.Colour;

import java.util.HashMap;

/**
 * Class which represents a local copy of the cloud tiles
 */
public class MockCloudTile {

    /**
     * A hash map with the students on the cloud tile
     */
    private HashMap<Colour,Integer> tileStudents;


    /**
     * Constructor: creates a new MockCloudTile
     */
    public MockCloudTile(HashMap<Colour, Integer> cloudTile) {
        tileStudents = cloudTile;
    }

    /**
     * Gets the cloud tile
     *
     * @return the students on the cloud tile
     */
    public HashMap<Colour,Integer> getMockCloudTile(){
        return tileStudents;
    }

    /**
     * Sets the students on the cloud tile
     *
     * @param tileStudents hashmap with the students
     */
    public void setCloudTile(HashMap<Colour, Integer> tileStudents){
        this.tileStudents = tileStudents;
    }
}
