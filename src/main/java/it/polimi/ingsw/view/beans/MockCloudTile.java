package it.polimi.ingsw.view.beans;

import it.polimi.ingsw.model.Colour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.HashMap;

/**
 * Class which represents a local copy of the cloud tiles
 */
public class MockCloudTile {

    /**
     * A hash map with the students on the cloud tile
     */
    private ObservableMap<Colour, Integer> tileStudents;


    /**
     * Constructor: creates a new MockCloudTile
     */
    public MockCloudTile() {
        tileStudents = FXCollections.observableHashMap();
        for(Colour colour: Colour.values()){
            tileStudents.put(colour,0);
        }
    }

    /**
     * Gets the cloud tile
     *
     * @return the students on the cloud tile
     */
    public HashMap<Colour, Integer> getMockCloudTile() {
        return new HashMap<>(tileStudents);
    }

    /**
     * Sets the students on the cloud tile
     *
     * @param tileStudents hashmap with the students
     */
    public void setCloudTile(HashMap<Colour, Integer> tileStudents) {
        this.tileStudents.entrySet().forEach(entry -> entry.setValue(tileStudents.get(entry.getKey())));
    }
}