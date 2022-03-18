package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;

public class ExchangeEntranceDiningRoom extends CharacterCard{
    private Colour colourDiningRoom;
    private Colour colourEntrance;
    private int times;

    public ExchangeEntranceDiningRoom(Game game){
        super(game);
        initialCost = 1;
        actualCost = initialCost;
    }

    public void effect(){
        game.getCurrentPlayer().getSchoolBoard().addStudentToDiningRoom(colourDiningRoom);
        game.getCurrentPlayer().getSchoolBoard().removeStudentFromDiningRoom(colourEntrance);
        game.getCurrentPlayer().getSchoolBoard().addStudentToEntrance(colourEntrance);
        game.getCurrentPlayer().getSchoolBoard().removeStudentFromEntrance(colourDiningRoom);

        if(times == 2){
            resetTimes();
            game.setActiveCharacterCard(game.getBasicState());
        } else {
            increaseTimes();
        }
    }

    public void setColourDiningRoom(Colour colour){
        colourDiningRoom = colour;
    }

    public void setColourEntrance(Colour colour){
        colourEntrance = colour;
    }

    private void resetTimes(){
        times = 0;
    }

    private void increaseTimes(){
        times += 1;
    }
}
