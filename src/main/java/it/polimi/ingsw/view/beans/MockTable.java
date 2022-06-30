package it.polimi.ingsw.view.beans;

import it.polimi.ingsw.model.Colour;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.HashMap;
import java.util.List;

/**
 * Class which represents the local copy of the game table
 */
public class MockTable {
    /**
     * A list containing the cloud tiles
     */
    private final ObservableList<MockCloudTile> cloudTiles;

    /**
     * A list containing the shown cloud tiles
     */
    private final ObservableList<MockCloudTile> shownCloudTiles;

    /**
     * A list containing the group islands
     */
    private final ObservableList<MockGroupIsland> groupIslands;

    /**
     * A boolean that indicates if the bag is empty
     */
    private boolean isBagEmpty;

    /**
     * Indicates the position of mother nature (index of the group island)
     */
    private final IntegerProperty motherNaturePosition;

    /**
     * The professors available on the table
     */
    private final ObservableMap<Colour, Boolean> professorsAvailable;

    /**
     * The index of the groupIsland on which the influence has changed
     */
    private final IntegerProperty islandInfluenceChanged;

    /**
     * Constructs the table
     */
    public MockTable() {
        cloudTiles = FXCollections.observableArrayList();
        shownCloudTiles = FXCollections.observableArrayList();
        groupIslands = FXCollections.observableArrayList();
        isBagEmpty = false;
        motherNaturePosition = new SimpleIntegerProperty(0);
        islandInfluenceChanged = new SimpleIntegerProperty();
        professorsAvailable = FXCollections.observableHashMap();
        professorsAvailable.put(Colour.GREEN, true);
        professorsAvailable.put(Colour.RED, true);
        professorsAvailable.put(Colour.YELLOW, true);
        professorsAvailable.put(Colour.PINK, true);
        professorsAvailable.put(Colour.BLUE, true);

        for (int i = 0; i < 12; i++) {
            groupIslands.add(new MockGroupIsland(true));
        }
    }

    /**
     * Gets the professors available on the table
     *
     * @return professorAvailable the HashMap of professors available on the table
     */
    public synchronized HashMap<Colour, Boolean> getProfessorsAvailable() {
        return new HashMap<>(professorsAvailable);
    }

    /**
     * Removes a professor from the ones available (sets that colour to false)
     *
     * @param colour the colour of the professor to remove
     */
    public synchronized void removeProfessorFromTable(Colour colour) {
        professorsAvailable.replace(colour, professorsAvailable.get(colour), false);
    }

    /**
     * Adds a cloud tile to the list
     */
    public synchronized void addCloudTile() {
        cloudTiles.add(new MockCloudTile());
    }

    /**
     * Removes a cloud tile from the list
     *
     * @param cloudTile the index of the cloud tile in the list
     */
    public synchronized void removeShownCloudTileByIndex(int cloudTile) {
        shownCloudTiles.remove(cloudTile);
    }

    /**
     * Adds a cloud tile to the list shownCLoudTiles
     */
    public synchronized void addShownCLoudTile() {
        shownCloudTiles.add(new MockCloudTile());
    }

    /**
     * Sets the students on a cloud tile to show
     *
     * @param cloudTile the index of the cloud tile
     * @param students  the students on the cloud tile
     */
    public synchronized void setShownCloudTile(int cloudTile, HashMap<Colour, Integer> students) {
        shownCloudTiles.get(cloudTile).setCloudTile(students);
    }

    /**
     * Gets the cloud tiles shown
     *
     * @return the list of the cloud tiles shown
     */
    public synchronized List<MockCloudTile> getShownCloudTiles() {
        return shownCloudTiles;
    }

    /**
     * Gets the cloud tiles shown as an ObservableList
     *
     * @return the list of the cloud tiles shown as an ObservableList
     */
    public ObservableList<MockCloudTile> getShownCloudTilesProperty() {
        return shownCloudTiles;
    }


    /**
     * Gets the cloud tile with a given index
     *
     * @param cloudTile the index of the cloud tile selected
     * @return the cloud tile
     */
    public synchronized MockCloudTile getCloudTileByIndex(int cloudTile) {
        return cloudTiles.get(cloudTile);
    }

    /**
     * Gets the list of CloudTiles
     *
     * @return the list of cloudTiles
     */
    public synchronized List<MockCloudTile> getCloudTile() {
        return cloudTiles;
    }

    /**
     * Adds a group island to the list
     *
     * @param groupIsland the group island
     */
    public synchronized void addGroupIsland(MockGroupIsland groupIsland) {
        groupIslands.add(groupIsland);
    }

    /**
     * Gets the list of GroupIslands
     *
     * @return the list of groupIslands
     */
    public synchronized List<MockGroupIsland> getGroupIslands() {
        return groupIslands;
    }

    /**
     * Gets the group island with the given index
     *
     * @param index the given index
     * @return the group island
     */
    public synchronized MockGroupIsland getGroupIslandByIndex(int index) {
        return groupIslands.get(index);
    }

    /**
     * Gets the number of group islands on the table
     *
     * @return the number of group islands
     */
    public synchronized int getNumberOfGroupIslands(){
        return groupIslands.size();
    }

    /**
     * Gets the index of the group island given
     *
     * @param groupIsland the group island chosen
     * @return the index of the group island in the list
     */
    public synchronized int getIndexOfGroupIsland(MockGroupIsland groupIsland){
        return groupIslands.indexOf(groupIsland);
    }

    /**
     * Unifies two group islands
     *
     * @param groupIsland1 the first group island
     * @param groupIsland2 the second group island (it will be removed after unification)
     */
    public synchronized void unify(int groupIsland1, int groupIsland2) {
        for (MockSingleIsland singleIsland : groupIslands.get(groupIsland2).getIslands()) {
            groupIslands.get(groupIsland1).addMockSingleIsland(singleIsland);
        }
        groupIslands.remove(groupIsland2);
    }

    /**
     * Gets the current position of mother nature
     *
     * @return an integer that indicates the current position of mother nature
     */
    public synchronized int getMotherNaturePosition() {
        return motherNaturePosition.getValue();
    }

    /**
     * Sets the position of mother nature
     *
     * @param motherNaturePosition the new position
     */
    public synchronized void setMotherNaturePosition(int motherNaturePosition) {
        if (this.motherNaturePosition.getValue() < getGroupIslands().size()) {
            getGroupIslandByIndex(this.motherNaturePosition.getValue()).setMotherNature(false);
        }
        this.motherNaturePosition.setValue(motherNaturePosition);
        getGroupIslandByIndex(motherNaturePosition).setMotherNature(true);
    }

    /**
     * Gets the list of group islands
     *
     * @return an ObservableList containing the groupIslands
     */
    public synchronized ObservableList<MockGroupIsland> getGroupIslandsProperty() {
        return groupIslands;
    }

    /**
     * Gets the current position of mother nature as an IntegerProperty
     *
     * @return an IntegerProperty that indicates the current position of mother nature
     */
    public IntegerProperty getMotherNaturePositionProperty() {
        return motherNaturePosition;
    }

    /**
     * Sets the index of the island on which the influence has changed
     *
     * @param islandInfluenceChanged the index of the island on which the influence has changed
     */
    public void setIslandInfluenceChanged(int islandInfluenceChanged) {
        this.islandInfluenceChanged.set(islandInfluenceChanged);
    }

    /**
     * Sets the isBagEmpty boolean to true
     */
    public synchronized void setBagEmpty() {
        this.isBagEmpty = true;
    }
}