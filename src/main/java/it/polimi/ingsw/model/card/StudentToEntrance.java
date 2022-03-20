package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;

import java.util.HashMap;

public class StudentToEntrance extends CharacterCard {
    private final HashMap<Colour, Integer> students;
    private Colour colourCard;
    private Colour colourEntrance;
    private int times;

    /**
     * Constructor
     *
     * @param game the Game
     */
    public StudentToEntrance(Game game) {
        super(game);
        initialCost = 1;
        actualCost = initialCost;
        times = 0;
        students = new HashMap<>();
        for (Colour colour1 : Colour.values()) {
            students.put(colour1, 0);
        }

        for (int i = 0; i < 6; i++) {
            Colour colour1 = game.getTable().getBag().bagDrawStudent();
            students.replace(colour1, students.get(colour1), students.get(colour1) + 1);
        }
    }

    /**
     * Activates the effect of the card
     */
    public void effect() {
        game.getCurrentPlayer().getSchoolBoard().removeStudentFromEntrance(colourEntrance);
        game.getCurrentPlayer().getSchoolBoard().addStudentToEntrance(colourCard);
        students.replace(colourCard, students.get(colourCard), students.get(colourCard) - 1);
        students.replace(colourEntrance, students.get(colourEntrance), students.get(colourEntrance) + 1);
        if (times == 3) {
            resetTimes();
            game.setActiveCharacterCard(game.getBasicState());
        } else {
            increaseTimes();
        }
    }

    /**
     * Set the colour
     *
     * @param colourCard     the colour of the student in the diningRoom
     * @param colourEntrance the colour of the student in the entrance
     */
    @Override
    public void setColourCardEntrance(Colour colourCard, Colour colourEntrance) {
        this.colourCard = colourCard;
        this.colourEntrance = colourEntrance;
        this.effect();
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
}
