package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;

import java.util.HashMap;

public class StudentToIsland extends CharacterCard {

    private final HashMap<Colour, Integer> studentsOnCard;

    int initialCost;

    int actualCost;

    Colour colour;

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

    @Override
    public void setColour(Colour colour){
        if(studentsOnCard.get(colour) == 0){
            throw new IllegalArgumentException("There is no such colour on the card");
        }
        this.colour = colour;
        this.effect();
    }

    @Override
    public void effect(){
        //capire come far scegliere l'isola
    }

}
