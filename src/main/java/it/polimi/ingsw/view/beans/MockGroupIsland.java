package it.polimi.ingsw.view.beans;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Class which represents the local copy of the group island in the game
 */
public class MockGroupIsland {
    /**
     * A list containing the single islands of the group island
     */
    private final ObservableList<MockSingleIsland> islands;

    /**
     * A boolean that states if the group island is basic or advanced
     */
    private boolean isBasic;

    /**
     * An integer that indicates the number of no entry tiles on this group island
     */
    private IntegerProperty noEntryTile;

    /**
     * A boolean that states if mother nature is on this island
     */
    private BooleanProperty motherNature;

    /**
     * The Player influence
     */
    private StringProperty influentPlayer;

    /**
     * Constructs the group island
     *
     * @param isBasic true if the group island is basic, false if the group island is advanced
     */
    public MockGroupIsland(boolean isBasic) {
        this.isBasic = isBasic;
        islands = FXCollections.observableArrayList();
        islands.add(new MockSingleIsland());
        noEntryTile = new SimpleIntegerProperty(0);
        motherNature = new SimpleBooleanProperty(false);
        influentPlayer = new SimpleStringProperty();
    }

    /**
     * Gets the island contained in this group island
     *
     * @return the ArrayList of single islands of this group island
     */
    public List<MockSingleIsland> getIslands() {
        return islands;
    }

    /**
     * Gets the single island at the given position
     *
     * @param index the position of the single island in the list
     * @return the single island
     */
    public MockSingleIsland getSingleIslandByIndex(int index) {
        return islands.get(index);
    }

    /**
     * Adds a single island to this group island
     *
     * @param singleIsland the single island
     */
    public void addMockSingleIsland(MockSingleIsland singleIsland) {
        islands.add(singleIsland);
    }

    /**
     * Gets the value of the attribute is basic
     *
     * @return true if the group island is basic, false if the group island is advanced
     */
    public boolean getIsBasic() {
        return isBasic;
    }


    /**
     * Sets the value of the attribute is basic
     */
    public void setIsBasic(boolean isBasic) {
        this.isBasic = isBasic;
    }

    /**
     * Gets the number of no entry tile on this group island
     *
     * @return the number of no entry tile
     */
    public int getNoEntryTile() {
        return noEntryTile.getValue();
    }

    /**
     * Sets the number of no entry tile
     *
     * @param noEntryTile the number of no entry tile
     */
    public void setNoEntryTile(int noEntryTile) {
        this.noEntryTile.setValue(noEntryTile);
    }

    /**
     * States if mother nature is on that island
     *
     * @return true if mother nature is on this island, false if mother nature is not on that island
     */
    public boolean isMotherNature() {
        return motherNature.getValue();
    }

    /**
     * Sets the attribute motherNature
     *
     * @param motherNature a boolean that is true if mother nature is on that island, false if mother nature is not on that island
     */
    public void setMotherNature(boolean motherNature) {
        this.motherNature.setValue(motherNature);
    }

    /**
     * Gets the influent player on that group island
     *
     * @return the nickname of the influent player on that group island
     */
    public String getInfluentPlayer() {
        return influentPlayer.getValue();
    }

    /**
     * Sets the influent player on that group island
     *
     * @param influentPlayer the nickname of the influent player on that group island
     */
    public void setInfluentPlayer(String influentPlayer) {
        this.influentPlayer.setValue(influentPlayer);
    }

    public BooleanProperty getIsMotherNatureProperty(){ return motherNature;}
}