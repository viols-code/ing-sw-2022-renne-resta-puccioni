package it.polimi.ingsw.view.implementation.cli;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.ActionSender;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.messages.*;

/**
 * Implementation of ActionSender for the CLI
 */
public class CLIActionSender extends ActionSender{

    /**
     * Constructs a new ActionSender.
     *
     * @param view the view responsible for this action sender
     */
    public CLIActionSender(View view) {
        super(view);
    }

    /**
     * Sends a message with the chosen cloud tile
     *
     * @param localPlayer the nickname of the local player
     * @param cloudTile the index of the cloud tile chosen
     * @throws IllegalArgumentException if the local player is not the current player or
     * the index chosen for the cloud tile is not valid
     */
    @Override
    public void chooseCloudTile(String localPlayer, int cloudTile) throws IllegalArgumentException{
        try{
            super.chooseCloudTile(localPlayer, cloudTile);
        }catch(IllegalAccessError e){
            getView().getRenderer().showErrorMessage(e.getMessage());
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
    @Override
    public void moveMotherNature(String localPlayer, int steps) throws IllegalArgumentException{
        try{
            super.moveMotherNature(localPlayer, steps);
        }catch(IllegalAccessError e){
            getView().getRenderer().showErrorMessage(e.getMessage());
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
    @Override
    public void moveStudentToDiningRoom(String localPlayer, Colour student)throws IllegalArgumentException{
        try{
            super.moveStudentToDiningRoom(localPlayer, student);
        }catch(IllegalAccessError e){
            getView().getRenderer().showErrorMessage(e.getMessage());
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
    @Override
    public void moveStudentToIsland(String localPlayer, Colour student, int groupIsland, int singleIsland) throws IllegalArgumentException{
        try{
            super.moveStudentToIsland(localPlayer, student, groupIsland, singleIsland);
        }catch(IllegalAccessError e){
            getView().getRenderer().showErrorMessage(e.getMessage());
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
    @Override
    public void playAssistantCard(String localPlayer, int assistantCard)throws IllegalArgumentException{
        try{
            super.playAssistantCard(localPlayer, assistantCard);
        }catch(IllegalAccessError e){
            getView().getRenderer().showErrorMessage(e.getMessage());
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
    @Override
    public void playCharacterCard(String localPlayer, int characterCard)throws IllegalArgumentException{
        try{
            super.playCharacterCard(localPlayer, characterCard);
        }catch(IllegalAccessError e){
            getView().getRenderer().showErrorMessage(e.getMessage());
        }
    }

    /**
     * Sends a message with the selected colour for the character card
     *
     * @param localPlayer the nickname of the local player
     * @param colour the colour chosen
     * @throws IllegalArgumentException the game mode isn't expert or the local player isn't the current player
     */
    @Override
    public void setColour(String localPlayer, Colour colour)throws IllegalArgumentException{
        try{
            super.setColour(localPlayer, colour);
        }catch(IllegalAccessError e){
            getView().getRenderer().showErrorMessage(e.getMessage());
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
    @Override
    public void setColourAndIsland(String localPlayer, Colour colour, int groupIsland, int singleIsland)throws IllegalArgumentException{
        try{
            super.setColourAndIsland(localPlayer, colour, groupIsland, singleIsland);
        }catch(IllegalAccessError e){
            getView().getRenderer().showErrorMessage(e.getMessage());
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
    @Override
    public void setColourCardEntrance(String localPlayer, Colour colourCard, Colour colourEntrance)throws IllegalArgumentException{
        try{
            super.setColourCardEntrance(localPlayer, colourCard, colourEntrance);
        }catch(IllegalAccessError e){
            getView().getRenderer().showErrorMessage(e.getMessage());
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
    @Override
    public void setColourDiningRoomEntrance(String localPlayer, Colour colourDiningRoom, Colour colourEntrance)throws IllegalArgumentException{
        try{
            super.setColourDiningRoomEntrance(localPlayer, colourDiningRoom, colourEntrance);
        }catch(IllegalAccessError e){
            getView().getRenderer().showErrorMessage(e.getMessage());
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
    @Override
    public void setGroupIsland(String localPlayer, int groupIsland)throws IllegalArgumentException{
        try{
            super.setGroupIsland(localPlayer, groupIsland);
        }catch(IllegalAccessError e){
            getView().getRenderer().showErrorMessage(e.getMessage());
        }
    }

}
