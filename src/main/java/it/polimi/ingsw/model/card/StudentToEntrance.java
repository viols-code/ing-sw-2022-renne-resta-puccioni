package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.messages.StudentToEntranceUpdate;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;

import java.util.HashMap;

public class StudentToEntrance extends CharacterCard {
    /**
     * The HashMap containing the students that are on the card
     */
    private final HashMap<Colour, Integer> students;
    /**
     * The chosen colour on the card to be switched
     */
    private Colour colourCard;
    /**
     * The chosen colour in the card to be switched
     */
    private Colour colourEntrance;
    /**
     * The times the player has already used this card
     */
    private int times;

    /**
     * Constructor
     *
     * @param game the Game
     */
    public StudentToEntrance(Game game) {
        super(game);
        actualCost = 1;
        times = 0;
        students = new HashMap<>();
        type = CharacterCardEnumeration.STUDENT_TO_ENTRANCE;
    }

    /**
     * Sets the students on the card
     */
    public void setting() {
        for (Colour colour1 : Colour.values()) {
            students.put(colour1, 0);
        }

        for (int i = 0; i < 6; i++) {
            try {
                Colour colour1 = game.getTable().getBag().bagDrawStudent();
                students.replace(colour1, students.get(colour1), students.get(colour1) + 1);
            } catch (IllegalAccessError ex) {
                ex.printStackTrace();
            }
        }
        notify(new StudentToEntranceUpdate(students));
    }

    /**
     * Activates the effect of the card
     */
    protected void effect() throws IllegalArgumentException {
        game.getCurrentPlayer().getSchoolBoard().removeStudentFromEntrance(colourEntrance);
        game.getCurrentPlayer().getSchoolBoard().addStudentToEntrance(colourCard);
        students.replace(colourCard, students.get(colourCard), students.get(colourCard) - 1);
        students.replace(colourEntrance, students.get(colourEntrance), students.get(colourEntrance) + 1);
        if (times == 2) {
            resetTimes();
            setColourCardEntrance(null, null);
            game.setActiveCharacterCard(game.getBasicState());
        } else {
            increaseTimes();
        }
        notify(new StudentToEntranceUpdate(students));
    }

    /**
     * Set the colour
     *
     * @param colourCard     the colour of the student in the diningRoom
     * @param colourEntrance the colour of the student in the entrance
     */
    @Override
    public void setColourCardEntrance(Colour colourCard, Colour colourEntrance) throws IllegalAccessError {
        this.colourCard = colourCard;
        this.colourEntrance = colourEntrance;
        if (colourCard != null && colourEntrance != null) {
            if (students.get(colourCard) > 0) {
                this.effect();
            } else {
                throw new IllegalArgumentException("There is no student of such colour on the card");
            }
        }
    }

    /**
     * Reset the times
     */
    private void resetTimes() {
        times = 0;
    }

    /**
     * Increase the times
     */
    private void increaseTimes() {
        times += 1;
    }

    /**
     * Get number of student of the given colour
     *
     * @param colour colour
     * @return number of student of the given colour
     */
    public int getStudent(Colour colour) {
        return students.get(colour);
    }
}
