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
        getView().getClient().send(new ChooseCloudTile(localPlayer,cloudTile));
    }

    public void moveMotherNature(String localPlayer, int steps){
        getView().getClient().send(new MoveMotherNature(localPlayer,steps));
    }

    public void moveStudentToDiningRoom(String localPlayer, Colour student){
        getView().getClient().send(new MoveStudentToDiningRoom(localPlayer,student));
    }

    public void moveStudentToIsland(String localPlayer, Colour student, int groupIsland, int singleIsland){
        getView().getClient().send(new MoveStudentToIsland(localPlayer,student,groupIsland,singleIsland));
    }

    public void playAssistantCard(String localPlayer, int assistantCard){
        getView().getClient().send(new PlayAssistantCard(localPlayer,assistantCard));
    }

    public void playCharacterCard(String localPlayer, int characterCard){
        getView().getClient().send(new PlayCharacterCard(localPlayer,characterCard));
    }

    public void setColour(String localPlayer, Colour colour){
        getView().getClient().send(new SetColour(localPlayer,colour));
    }

    public void setColourAndIsland(String localPlayer, Colour colour, int groupIsland, int singleIsland){
        getView().getClient().send(new SetColourAndIsland(localPlayer,colour,groupIsland,singleIsland));
    }

    public void setColourCardEntrance(String localPlayer, Colour colourCard, Colour colourEntrance){
        getView().getClient().send(new SetColourCardEntrance(localPlayer,colourCard,colourEntrance));
    }

    public void setColourDiningRoomEntrance(String localPlayer, Colour colourDiningRoom, Colour colourEntrance){
        getView().getClient().send(new SetColourDiningRoomEntrance(localPlayer,colourDiningRoom,colourEntrance));
    }

    public void setGroupIsland(String localPlayer, int groupIsland){
        getView().getClient().send(new SetGroupIsland(localPlayer,groupIsland));
    }


}
