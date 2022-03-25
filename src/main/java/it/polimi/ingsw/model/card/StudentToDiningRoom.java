package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;

import java.util.HashMap;

public class StudentToDiningRoom extends CharacterCard {
    private final HashMap<Colour, Integer> students;
    private Colour colour;

    /**
     * Constructor
     *
     * @param game the Game
     */
    public StudentToDiningRoom(Game game) {
        super(game);
        actualCost = 2;
        students = new HashMap<>();
    }

    public void setting() {
        for (Colour colour1 : Colour.values()) {
            students.put(colour1, 0);
        }

        for (int i = 0; i < 4; i++) {
            Colour colour1 = game.getTable().getBag().bagDrawStudent();
            students.replace(colour1, students.get(colour1), students.get(colour1) + 1);
        }
    }

    /**
     * Activates the effect of the CharacterCard
     */
    @Override
    public void effect() {
        game.getCurrentPlayer().getSchoolBoard().addStudentToDiningRoom(colour);
        students.replace(colour, students.get(colour), students.get(colour) - 1);
        if (game.getTable().getBag().isBagEmpty()) {
            Colour colour1 = game.getTable().getBag().bagDrawStudent();
            students.replace(colour1, students.get(colour1), students.get(colour1) + 1);
        }
        game.setActiveCharacterCard(game.getBasicState());
    }

    /**
     * Set the colour of the chosen student
     *
     * @param colour the colour to be set
     */
    @Override
    public void setColour(Colour colour) {
        this.colour = colour;
        this.effect();
    }

}
