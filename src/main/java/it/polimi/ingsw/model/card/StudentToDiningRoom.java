package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;

import java.util.HashMap;

public class StudentToDiningRoom extends CharacterCard{
    private final HashMap<Colour,Integer> students;
    private Colour colour;

    public StudentToDiningRoom(Game game){
        super(game);
        initialCost = 2;
        actualCost = initialCost;
        students = new HashMap<>();

        for(Colour colour1:Colour.values()){
            students.put(colour1, 0);
        }

        for(int i = 0; i < 4; i++){
            Colour colour1 = game.getTable().getBag().bagDrawStudent();
            students.replace(colour1, students.get(colour1), students.get(colour1) + 1);
        }
    }

    public void effect(){
        game.getCurrentPlayer().getSchoolBoard().addStudentToDiningRoom(colour);
        students.replace(colour, students.get(colour), students.get(colour) - 1);
        game.setActiveCharacterCard(game.getBasicState());
    }

    public void setColour(Colour colour){
        this.colour = colour;
    }

}
