package it.polimi.ingsw.view.beans;

import it.polimi.ingsw.model.Colour;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class that contains a local copy of the player's school board
 */
public class MockSchoolBoard {
    private final ObservableMap<Colour, Integer> entrance;
    private final ObservableMap<Colour, Integer> diningRoom;
    private final ObservableMap<Colour, Boolean> professorTable;
    private final IntegerProperty towers;
    private final List<StudentCoordinates> selectedStudents;

    public MockSchoolBoard() {
        entrance = FXCollections.observableHashMap();
        diningRoom = FXCollections.observableHashMap();
        professorTable = FXCollections.observableHashMap();
        towers = new SimpleIntegerProperty();
        for (Colour colour : Colour.values()) {
            entrance.put(colour, 0);
            diningRoom.put(colour, 0);
            professorTable.put(colour, false);
        }
        selectedStudents = new ArrayList<>();
    }

    /**
     * Gets the entrance
     *
     * @return the entrance as an HashMap
     */
    public HashMap<Colour, Integer> getEntrance() {
        return new HashMap<>(entrance);
    }

    /**
     * Updates the entrance
     *
     * @param entrance the entrance updated
     */
    public void setEntrance(HashMap<Colour, Integer> entrance) {
        this.entrance.entrySet().forEach(entry -> entry.setValue(entrance.get(entry.getKey())));
    }

    /**
     * Gets the dining room
     *
     * @return the dining room
     */
    public HashMap<Colour, Integer> getDiningRoom() {
        return new HashMap<>(diningRoom);
    }

    /**
     * Updates the dining room
     *
     * @param diningRoom the dining room updated
     */
    public void setDiningRoom(HashMap<Colour, Integer> diningRoom) {
        this.diningRoom.entrySet().forEach(entry -> entry.setValue(diningRoom.get(entry.getKey())));
    }

    /**
     * Gets the professor table
     *
     * @return the professor table
     */
    public HashMap<Colour, Boolean> getProfessorTable() {
        return new HashMap<>(professorTable);
    }

    /**
     * Updates the professor table
     *
     * @param professorTable the professor table updated
     */
    public void setProfessorTable(HashMap<Colour, Boolean> professorTable) {
        this.professorTable.entrySet().forEach(entry -> entry.setValue(professorTable.get(entry.getKey())));
    }

    /**
     * Gets the number of towers on this school board
     *
     * @return the number of towers on this school board
     */
    public int getTowers() {
        return towers.getValue();
    }

    /**
     * Sets the number of towers on this school board
     *
     * @param towers the number of towers
     */
    public void setTowers(int towers) {
        this.towers.setValue(towers);
    }

    /**
     * Gets the entrance as an ObservableMap
     *
     * @return an ObservableMap representing the entrance
     */
    public ObservableMap<Colour, Integer> getEntranceProperty() {
        return entrance;
    }

    /**
     * Gets the dining room as an ObservableMap
     *
     * @return an ObservableMap representing the dining room
     */
    public ObservableMap<Colour, Integer> getDiningRoomProperty() {
        return diningRoom;
    }

    /**
     * Gets the professor table as an ObservableMap
     *
     * @return an ObservableMap representing the professor table
     */
    public ObservableMap<Colour, Boolean> getProfessorTableProperty() {
        return professorTable;
    }

    /**
     * Gets the number of towers present on this schoolBoard
     *
     * @return the number of towers present on this schoolBoard as an IntegerProperty
     */
    public IntegerProperty getTowersProperty() {
        return towers;
    }
}