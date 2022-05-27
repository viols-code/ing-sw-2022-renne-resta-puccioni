package it.polimi.ingsw.view.beans;

import it.polimi.ingsw.model.Colour;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.HashMap;

/**
 * Class that contains a local copy of the character cards
 */
public class MockCard {
    /**
     * The type of the character card
     */
    private final CharacterCardEnumeration type;

    /**
     * The character card cost
     */
    private IntegerProperty cost;

    /**
     * Number of student on the card
     */
    private final int numberOfStudentsOnTheCard;

    /**
     * The number of students that can be chosen
     */
    private final int numberOfStudentToChoose;

    /**
     * The number of no entry tile
     */
    private IntegerProperty numberOfNoEntryTile;

    /**
     * The students on the card
     */
    private ObservableMap<Colour, Integer> students;

    /**
     * Constructs the character card
     *
     * @param type the type of the character card
     */
    public MockCard(CharacterCardEnumeration type) {
        this.type = type;
        switch (type) {
            case STUDENT_TO_ISLAND, STUDENT_TO_DINING_ROOM -> {
                numberOfStudentsOnTheCard = 4;
                numberOfStudentToChoose = 1;
            }
            case STUDENT_TO_ENTRANCE -> {
                numberOfStudentsOnTheCard = 6;
                numberOfStudentToChoose = 3;
            }
            default -> {
                numberOfStudentsOnTheCard = 0;
                numberOfStudentToChoose = 0;
            }
        }
        if (type.equals(CharacterCardEnumeration.PROTECT_ISLAND)) {
            numberOfNoEntryTile = new SimpleIntegerProperty(4);
        } else {
            numberOfNoEntryTile = new SimpleIntegerProperty(0);
        }

        students = FXCollections.observableHashMap();
        students.put(Colour.GREEN,0);
        students.put(Colour.RED,0);
        students.put(Colour.YELLOW,0);
        students.put(Colour.PINK,0);
        students.put(Colour.BLUE,0);

        cost = new SimpleIntegerProperty();
        switch (type) {
            case STUDENT_TO_ISLAND, MOTHER_NATURE_MOVEMENT, STUDENT_TO_ENTRANCE, EXCHANGE_ENTRANCE_DINING_ROOM -> cost.setValue(1);
            case TAKE_PROFESSOR, PROTECT_ISLAND, TWO_POINTS, STUDENT_TO_DINING_ROOM -> cost.set(2);
            case ISLAND_INFLUENCE, NO_TOWER, NO_COLOUR, THREE_STUDENT -> cost.setValue(3);
        }
    }

    /**
     * Gets the character card type
     *
     * @return the character card type
     */
    public CharacterCardEnumeration getType() {
        return type;
    }

    /**
     * Gets the character card cost
     *
     * @return the cost
     */
    public int getCost() {
        return cost.getValue();
    }

    /**
     * Updates the cost of the character card
     *
     * @param cost the updated cost
     */
    public void setCost(int cost) {
        this.cost.setValue(cost);
    }

    /**
     * Gets the number of students on the card
     *
     * @return the number of students to put on the card
     */
    public int getNumberOfStudentsOnTheCard() {
        return numberOfStudentsOnTheCard;
    }

    /**
     * Gets the number of student that can be chosen from the card
     *
     * @return the number of student that can be chosen from the card
     */
    public int getNumberOfStudentToChoose() {
        return numberOfStudentToChoose;
    }

    /**
     * Gets the number of no entry tiles on the card
     *
     * @return number of no entry tiles
     */
    public int getNumberOfNoEntryTile() {
        return numberOfNoEntryTile.getValue();
    }

    /**
     * Updates the number of no entry tiles
     *
     * @param numberOfNoEntryTile the updated number of no entry tiles
     */
    public void setNumberOfNoEntryTile(int numberOfNoEntryTile) {
        this.numberOfNoEntryTile.setValue(numberOfNoEntryTile);
    }

    /**
     * Adds a student of the selected colour
     *
     * @param colour the selected student
     */
    public void addStudent(Colour colour) {
        if (type == CharacterCardEnumeration.STUDENT_TO_DINING_ROOM || type == CharacterCardEnumeration.STUDENT_TO_ENTRANCE || type == CharacterCardEnumeration.STUDENT_TO_ISLAND)
            students.replace(colour, students.get(colour), students.get(colour) + 1);
    }

    /**
     * Removes a student of the selected colour
     *
     * @param colour the student to remove
     */
    public void removeStudent(Colour colour) {
        if (type == CharacterCardEnumeration.STUDENT_TO_DINING_ROOM || type == CharacterCardEnumeration.STUDENT_TO_ENTRANCE || type == CharacterCardEnumeration.STUDENT_TO_ISLAND)
            students.replace(colour, students.get(colour), students.get(colour) - 1);
    }

    /**
     * Gets the students on the card
     *
     * @return the students on the card
     */
    public HashMap<Colour, Integer> getStudents() {
        return new HashMap<>(students);
    }

    /**
     * Sets the students on the card
     *
     * @param students the students on the card
     */
    public void setStudents(HashMap<Colour, Integer> students) {
        if (type == CharacterCardEnumeration.STUDENT_TO_DINING_ROOM || type == CharacterCardEnumeration.STUDENT_TO_ENTRANCE || type == CharacterCardEnumeration.STUDENT_TO_ISLAND)
            this.students.entrySet().forEach(entry -> entry.setValue(students.get(entry.getKey())));
    }
}