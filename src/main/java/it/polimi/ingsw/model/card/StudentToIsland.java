package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.messages.StudentToIslandUpdate;
import it.polimi.ingsw.model.table.island.SingleIsland;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentToIsland extends CharacterCard {

    /**
     * The HashMap containing the students that are on the card
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
        type = CharacterCardEnumeration.STUDENT_TO_ISLAND;

    }

    /**
     * Sets the students on the card
     */
    @Override
    public void setting() {
        for (int i = 0; i < 4; i++) {
            try {
                Colour colour = game.getTable().getBag().bagDrawStudent();
                studentsOnCard.replace(colour, studentsOnCard.get(colour), studentsOnCard.get(colour) + 1);
            } catch (IllegalAccessError ex) {
                ex.printStackTrace();
            }
        }
        notify(new StudentToIslandUpdate(studentsOnCard));
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
        if (colour == null && singleIsland == null) {
            this.islandChosen = null;
            this.colour = null;
        } else {
            if (studentsOnCard.get(colour) == 0) {
                throw new IllegalArgumentException("There is no such colour on the card");
            }
            this.islandChosen = singleIsland;
            this.colour = colour;
            this.effect();
        }
    }

    /**
     * Activate the effect of the characterCard
     */
    @Override
    protected void effect() {
        List<Integer> indexes = getGroupIslandIndexAndSingleIslandIndexes();
        islandChosen.addStudent(indexes.get(0), indexes.get(1), colour);
        studentsOnCard.replace(colour, studentsOnCard.get(colour), studentsOnCard.get(colour) - 1);
        try {
            Colour newColour = game.getTable().getBag().bagDrawStudent();
            studentsOnCard.replace(newColour, studentsOnCard.get(newColour), studentsOnCard.get(newColour) + 1);
        } catch (IllegalAccessError ex) {
            ex.printStackTrace();
        }
        notify(new StudentToIslandUpdate(studentsOnCard));
        game.setActiveCharacterCard(game.getBasicState());
    }

    /**
     * Return the indexes of the group island and single island chosen
     *
     * @return the indexes of the group island and single island chosen
     */
    private List<Integer> getGroupIslandIndexAndSingleIslandIndexes() {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < game.getTable().getNumberOfGroupIsland(); i++) {
            for (int j = 0; j < game.getTable().getGroupIslandByIndex(i).getNumberOfSingleIsland(); j++) {
                if (islandChosen.equals(game.getTable().getGroupIslandByIndex(i).getIslandByIndex(j))) {
                    indexes.add(i);
                    indexes.add(j);
                }
            }
        }
        return indexes;
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
