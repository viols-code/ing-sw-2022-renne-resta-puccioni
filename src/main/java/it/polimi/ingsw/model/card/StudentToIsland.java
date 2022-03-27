package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.table.island.SingleIsland;

import java.util.HashMap;

public class StudentToIsland extends CharacterCard {

    /**
     * The hashMap containing the students that are on the card
     */
    private final HashMap<Colour, Integer> studentsOnCard;

    /**
     * The colour chosen by the player
     */
    private Colour colour;

    /**
     * The singleIsland chosen by the player
     */
    private SingleIsland islandChosen;

    /**
     * Constructor: StudentToIsland
     *
     * @param game the game
     */
    public StudentToIsland(Game game) {
        super(game);
        actualCost = 1;
        studentsOnCard = new HashMap<>();
        for (Colour colour : Colour.values()) {
            studentsOnCard.put(colour, 0);
        }

    }

    public void setting() {
        for (int i = 0; i < 4; i++) {
            Colour colour = game.getTable().getBag().bagDrawStudent();
            studentsOnCard.replace(colour, studentsOnCard.get(colour), studentsOnCard.get(colour) + 1);
        }
    }

    /**
     * Set the colour of the chosen student and the island chosen
     *
     * @param colour       the colour of the student chosen
     * @param singleIsland the island chosen
     * @throws IllegalArgumentException if there is no such colour on the card
     */
    @Override
    public void setColourAndIsland(Colour colour, SingleIsland singleIsland) throws IllegalArgumentException {
        if (studentsOnCard.get(colour) == 0) {
            throw new IllegalArgumentException("There is no such colour on the card");
        }

        this.islandChosen = singleIsland;
        this.colour = colour;
        this.effect();
    }

    /**
     * Activate the effect of the characterCard
     */
    @Override
    protected void effect() {
        islandChosen.addStudent(colour);
        studentsOnCard.replace(colour, studentsOnCard.get(colour), studentsOnCard.get(colour) - 1);
        if (!game.getTable().getBag().isBagEmpty()) {
            Colour newColour = game.getTable().getBag().bagDrawStudent();
            studentsOnCard.replace(newColour, studentsOnCard.get(newColour), studentsOnCard.get(newColour) + 1);
        }
        game.setActiveCharacterCard(game.getBasicState());
    }

    /*
    FOR TESTING
     */

    /**
     * Get number of student of the given colour
     *
     * @param colour colour
     * @return number of student of the given colour
     */
    protected int getStudent(Colour colour) {
        return studentsOnCard.get(colour);
    }

}
