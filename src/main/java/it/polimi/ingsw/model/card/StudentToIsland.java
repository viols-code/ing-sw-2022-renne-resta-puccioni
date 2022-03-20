package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.island.SingleIsland;

import java.util.HashMap;

public class StudentToIsland extends CharacterCard {

    /**
     * The hashMap containing the students that are on the card
     */
    private final HashMap<Colour, Integer> studentsOnCard;

    /**
     * The colour chosen by the player
     */
    Colour colour;

    /**
     * The singleIsland chosen by the player
     */
    SingleIsland islandChosen;

    /**
     * Costructor
     *
     * @param game
     */
    public StudentToIsland(Game game) {
        super(game);

        initialCost = 1;
        actualCost = initialCost;

        studentsOnCard = new HashMap<>();

        for(Colour colour : Colour.values()){
            studentsOnCard.put(colour, 0);
        }

        for(int i = 0; i < 4; i++){
            Colour colour = game.getTable().getBag().bagDrawStudent();
            studentsOnCard.replace(colour, studentsOnCard.get(colour), studentsOnCard.get(colour) + 1);
        }

    }

    /**
     * Set the colour of the chosen student and the island chosen
     *
     * @param colour
     * @param singleIsland
     */
    @Override
    public void setColourAndIsland(Colour colour, SingleIsland singleIsland){
        if(studentsOnCard.get(colour) == 0){
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
    public void effect(){
        islandChosen.addStudent(colour);
        studentsOnCard.replace(colour, studentsOnCard.get(colour), studentsOnCard.get(colour) - 1);
        Colour newColour = game.getTable().getBag().bagDrawStudent();
        studentsOnCard.replace(newColour, studentsOnCard.get(newColour), studentsOnCard.get(newColour) + 1);
        game.setActiveCharacterCard(game.getBasicState());
    }

}
