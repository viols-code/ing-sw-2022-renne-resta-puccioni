package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.messages.*;

/**
 * Class responsible for sending user interactions to the server.
 */
public abstract class ActionSender {
    private final View view;

    /**
     * Constructs a new ActionSender.
     *
     * @param view the view responsible for this action sender
     */
    public ActionSender(View view) {
        this.view = view;
    }

    /**
     * Gets the View.
     *
     * @return the view
     */
    public View getView() {
        return view;
    }

    public void chooseCloudTile(String localPlayer, int cloudTile){
        if(!getView().getModel().getCurrentPlayer().getNickname().equals(localPlayer)){
            throw new IllegalArgumentException("it's not your turn");
        }
        else if(cloudTile < 0 || cloudTile >= getView().getModel().getPlayers().size()){
            throw new IllegalArgumentException("cloud tile index out of range");
        }
        else{
            getView().getClient().send(new ChooseCloudTile(localPlayer,cloudTile));
        }
    }

    public void moveMotherNature(String localPlayer, int steps){
        if(!getView().getModel().getCurrentPlayer().getNickname().equals(localPlayer)){
            throw new IllegalArgumentException("it's not your turn");
        }
        else if(steps <= 0 || steps > getView().getModel().getPlayerByNickname(localPlayer).getCurrentAssistantCard().getMotherNatureMovement()){
            throw new IllegalArgumentException("mother nature steps out of range");
        }
        else{
            getView().getClient().send(new MoveMotherNature(localPlayer,steps));
        }
    }

    public void moveStudentToDiningRoom(String localPlayer, Colour student){
        if(!getView().getModel().getCurrentPlayer().getNickname().equals(localPlayer)){
            throw new IllegalArgumentException("it's not your turn");
        }
        else if(getView().getModel().getPlayerByNickname(localPlayer).getSchoolBoard().getEntrance().get(student) <= 0){
            throw new IllegalArgumentException("you don't have this student in your entrance");
        }
        else if(getView().getModel().getPlayerByNickname(localPlayer).getSchoolBoard().getDiningRoom().get(student) == 10){
            throw new IllegalArgumentException("this dining room table is full");
        }
        else{
            getView().getClient().send(new MoveStudentToDiningRoom(localPlayer,student));
        }

    }

    public void moveStudentToIsland(String localPlayer, Colour student, int groupIsland, int singleIsland){
        if(!getView().getModel().getCurrentPlayer().getNickname().equals(localPlayer)){
            throw new IllegalArgumentException("it's not your turn");
        }
        else if(getView().getModel().getPlayerByNickname(localPlayer).getSchoolBoard().getEntrance().get(student) <= 0){
            throw new IllegalArgumentException("you don't have this student in your entrance");
        }
        else if(groupIsland < 0 || groupIsland >= getView().getModel().getTable().getGroupIslands().size()){
            throw new IllegalArgumentException("group island index out of range");
        }
        else if(singleIsland < 0 || singleIsland >= getView().getModel().getTable().getGroupIslandByIndex(groupIsland).getIslands().size()){
            throw new IllegalArgumentException("single island index out of range");
        }
        else{
            getView().getClient().send(new MoveStudentToIsland(localPlayer,student,groupIsland,singleIsland));
        }
    }

    public void playAssistantCard(String localPlayer, int assistantCard){
        if(!getView().getModel().getCurrentPlayer().getNickname().equals(localPlayer)){
            throw new IllegalArgumentException("it's not your turn");
        }
        else if(assistantCard < 1 || assistantCard > 10){
            throw new IllegalArgumentException("assistant card value out of range");
        }
        else{
            getView().getClient().send(new PlayAssistantCard(localPlayer,assistantCard));
        }
    }

    public void playCharacterCard(String localPlayer, int characterCard){
        if(!getView().getModel().isGameExpert()){
            throw new IllegalArgumentException("the game mode is not expert: you can't play character card");
        }
        else if(!getView().getModel().getCurrentPlayer().getNickname().equals(localPlayer)){
            throw new IllegalArgumentException("it's not your turn");
        }
        else if(characterCard < 0 || characterCard > 2){
            throw new IllegalArgumentException("character card index out of range");
        }
        else{
            getView().getClient().send(new PlayCharacterCard(localPlayer,characterCard));
        }
    }

    public void setColour(String localPlayer, Colour colour){
        if(!getView().getModel().isGameExpert()){
            throw new IllegalArgumentException("the game mode is not expert: you can't play character card");
        }
        else if(!getView().getModel().getCurrentPlayer().getNickname().equals(localPlayer)){
            throw new IllegalArgumentException("it's not your turn");
        }
        else{
            getView().getClient().send(new SetColour(localPlayer,colour));
        }
    }

    public void setColourAndIsland(String localPlayer, Colour colour, int groupIsland, int singleIsland){
        if(!getView().getModel().isGameExpert()){
            throw new IllegalArgumentException("the game mode is not expert: you can't play character card");
        }
        else if(!getView().getModel().getCurrentPlayer().getNickname().equals(localPlayer)){
            throw new IllegalArgumentException("it's not your turn");
        }
        else if(groupIsland < 0 || groupIsland >= getView().getModel().getTable().getGroupIslands().size()){
            throw new IllegalArgumentException("group island index out of range");
        }
        else if(singleIsland < 0 || singleIsland >= getView().getModel().getTable().getGroupIslandByIndex(groupIsland).getIslands().size()){
            throw new IllegalArgumentException("single island index out of range");
        }
        else{
            getView().getClient().send(new SetColourAndIsland(localPlayer,colour,groupIsland,singleIsland));
        }
    }

    public void setColourCardEntrance(String localPlayer, Colour colourCard, Colour colourEntrance){
        if(!getView().getModel().getCurrentPlayer().getNickname().equals(localPlayer)){
            throw new IllegalArgumentException("it's not your turn");
        }
        else if(getView().getModel().getCurrentCharacterCard().getStudents().get(colourCard) <= 0){
            throw new IllegalArgumentException("the card doesn't have this student");
        }
        else if(getView().getModel().getPlayerByNickname(localPlayer).getSchoolBoard().getEntrance().get(colourEntrance) <= 0){
            throw new IllegalArgumentException("you don't have this student in your entrance");
        }
        else{
            getView().getClient().send(new SetColourCardEntrance(localPlayer,colourCard,colourEntrance));
        }
    }

    public void setColourDiningRoomEntrance(String localPlayer, Colour colourDiningRoom, Colour colourEntrance){
        if(!getView().getModel().getCurrentPlayer().getNickname().equals(localPlayer)){
            throw new IllegalArgumentException("it's not your turn");
        }
        else if(getView().getModel().getPlayerByNickname(localPlayer).getSchoolBoard().getDiningRoom().get(colourDiningRoom) <=0){
            throw new IllegalArgumentException("you don't have this student in your dining room");
        }
        else if(getView().getModel().getPlayerByNickname(localPlayer).getSchoolBoard().getEntrance().get(colourEntrance) <= 0){
            throw new IllegalArgumentException("you don't have this student in your entrance");
        }
        else{
            getView().getClient().send(new SetColourDiningRoomEntrance(localPlayer,colourDiningRoom,colourEntrance));
        }
    }

    public void setGroupIsland(String localPlayer, int groupIsland){
        if(!getView().getModel().getCurrentPlayer().getNickname().equals(localPlayer)){
            throw new IllegalArgumentException("it's not your turn");
        }
        else if(groupIsland < 0 || groupIsland >= getView().getModel().getTable().getGroupIslands().size()){
            throw new IllegalArgumentException("group island index out of range");
        }
        else{
            getView().getClient().send(new SetGroupIsland(localPlayer,groupIsland));
        }
    }


}
