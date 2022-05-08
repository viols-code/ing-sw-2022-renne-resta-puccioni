package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.messages.*;

/**
 * Class responsible for sending message to the server according to the player actions
 */
public abstract class ActionSender {
    private final View view;

    /**
     * Constructs a new CLIActionSender.
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

    /**
     * Sends a message with the chosen cloud tile
     *
     * @param localPlayer the nickname of the local player
     * @param cloudTile the index of the cloud tile chosen
     * @throws IllegalArgumentException if the local player is not the current player or
     * the index chosen for the cloud tile is not valid
     */
    public void chooseCloudTile(String localPlayer, int cloudTile) throws IllegalArgumentException{
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

    /**
     * Sends a message with the movement for mother nature
     *
     * @param localPlayer the nickname of the local player
     * @param steps the steps mother nature has to do
     * @throws IllegalArgumentException if the local player is not the current player or
     * the number of steps chosen for mother nature is not valid
     */
    public void moveMotherNature(String localPlayer, int steps) throws IllegalArgumentException{
        if(!getView().getModel().getCurrentPlayer().getNickname().equals(localPlayer)){
            throw new IllegalArgumentException("it's not your turn");
        }
        /*else if(steps <= 0 || steps > getView().getModel().getPlayerByNickname(localPlayer).getCurrentAssistantCard().getMotherNatureMovement()){
            throw new IllegalArgumentException("mother nature steps out of range");
        }*/
        else{
            getView().getClient().send(new MoveMotherNature(localPlayer,steps));
        }
    }

    /**
     * Sends a message with the student that will be moved from the entrance to the dining room
     *
     * @param localPlayer the nickname of the local player
     * @param student the colour of the student chosen
     * @throws IllegalArgumentException if the local player isn't the current player, the player doesn't have the
     * selected student in their entrance or the dining room table of the selected student is full
     */
    public void moveStudentToDiningRoom(String localPlayer, Colour student)throws IllegalArgumentException{
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

    /**
     * Sends a message with the student that will be moved from the entrance to the selected island
     *
     * @param localPlayer the nickname of the local player
     * @param student the colour of the student chosen
     * @param groupIsland the index of the group island
     * @param singleIsland the index of the single island
     * @throws IllegalArgumentException if the local player isn't the current player,
     * the local player doesn't have the selected student in their entrance,
     * the group island index is out of range
     * the single island index is out of range
     */
    public void moveStudentToIsland(String localPlayer, Colour student, int groupIsland, int singleIsland) throws IllegalArgumentException{
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

    /**
     * Sends a message with the chosen assistant card
     *
     * @param localPlayer the nickname of the local player
     * @param assistantCard the value of assistant card
     * @throws IllegalArgumentException if the local player isn't the current player
     * or the value of the assistant card is out of range
     */
    public void playAssistantCard(String localPlayer, int assistantCard)throws IllegalArgumentException{
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

    /**
     * Sends a message with the selected character card
     *
     * @param localPlayer the nickname of the local player
     * @param characterCard the index of the character card
     * @throws IllegalArgumentException if the local player isn't the current player,
     * the game mode isn't expert,
     * the character card index is out of range,
     * the player hasn't enough money to pay the card
     */
    public void playCharacterCard(String localPlayer, int characterCard)throws IllegalArgumentException{
        if(!getView().getModel().isGameExpert()){
            throw new IllegalArgumentException("the game mode is not expert: you can't play character card");
        }
        else if(!getView().getModel().getCurrentPlayer().getNickname().equals(localPlayer)){
            throw new IllegalArgumentException("it's not your turn");
        }
        else if(characterCard < 0 || characterCard > 2){
            throw new IllegalArgumentException("character card index out of range");
        }
        else if(getView().getModel().getPlayerByNickname(localPlayer).getCoins() < getView().getModel().getCharacterCardByIndex(characterCard).getCost()){
            throw new IllegalArgumentException("you can't pay this card");
        }
        else{
            getView().getClient().send(new PlayCharacterCard(localPlayer,characterCard));
        }
    }

    /**
     * Sends a message with the selected colour for the character card
     *
     * @param localPlayer the nickname of the local player
     * @param colour the colour chosen
     * @throws IllegalArgumentException the game mode isn't expert or the local player isn't the current player
     */
    public void setColour(String localPlayer, Colour colour)throws IllegalArgumentException{
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

    /**
     * Sends a message with the selected student and the selected
     * @param localPlayer the nickname of the local player
     * @param colour the colour of the student selected
     * @param groupIsland the index of the group island
     * @param singleIsland the index of the single island
     * @throws IllegalArgumentException if the game mode isn't expert
     * the local player isn't the current player,
     * the local player doesn't have the selected student in their entrance,
     * the group island index is out of range
     * the single island index is out of range
     */
    public void setColourAndIsland(String localPlayer, Colour colour, int groupIsland, int singleIsland)throws IllegalArgumentException{
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

    /**
     * Sends a message with the selected students to switch
     *
     * @param localPlayer the nickname of the local player
     * @param colourCard the colour of the student on card selected
     * @param colourEntrance the colour of the student in the entrance selected
     * @throws IllegalArgumentException if the game mode isn't expert,
     * the player isn't the current player
     * the selected student for the card isn't present
     * the selected student for the entrance isn't present
     */
    public void setColourCardEntrance(String localPlayer, Colour colourCard, Colour colourEntrance)throws IllegalArgumentException{
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

    /**
     * Sends a message with the selected students to switch
     *
     * @param localPlayer the nickname of the local player
     * @param colourDiningRoom the colour of the student in the dining room selected
     * @param colourEntrance the colour of the student in the entrance selected
     * @throws IllegalArgumentException if the game mode isn't expert,
     * the player isn't the current player
     * the selected student for the dining room isn't present
     * the selected student for the entrance isn't present
     */
    public void setColourDiningRoomEntrance(String localPlayer, Colour colourDiningRoom, Colour colourEntrance)throws IllegalArgumentException{
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

    /**
     * Sends a message with the selected group island -> for character card IslandInfluence
     *
     * @param localPlayer the nickname of the local player
     * @param groupIsland the index of the group island
     * @throws IllegalArgumentException if the game mode isn't expert,
     * the local player isn't the current player
     * the group island index is out of range
     */
    public void setGroupIsland(String localPlayer, int groupIsland)throws IllegalArgumentException{
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
