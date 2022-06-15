package it.polimi.ingsw.view.implementation.gui;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.ActionSender;

public class GUIActionSender extends ActionSender {
    public GUIActionSender(GUI gui) {
        super(gui);
    }

    /**
     * Sends a message with the chosen assistant card
     *
     * @param localPlayer   the nickname of the local player
     * @param assistantCard the value of assistant card
     */
    @Override
    public void playAssistantCard(String localPlayer, int assistantCard) {
        try {
            super.playAssistantCard(localPlayer, assistantCard);
        } catch (IllegalArgumentException e) {
            getView().getRenderer().showErrorMessage(e.getMessage());
        }
    }

    /**
     * Sends a message with the chosen cloud tile
     *
     * @param localPlayer the nickname of the local player
     * @param cloudTile   the index of the cloud tile chosen
     */
    @Override
    public void chooseCloudTile(String localPlayer, int cloudTile) {
        try {
            super.chooseCloudTile(localPlayer, cloudTile);
        } catch (IllegalArgumentException e) {
            getView().getRenderer().showErrorMessage(e.getMessage());
        }
    }

    /**
     * Sends a message with the movement for mother nature
     *
     * @param localPlayer the nickname of the local player
     * @param steps       the steps mother nature has to do
     */
    @Override
    public void moveMotherNature(String localPlayer, int steps) {
        try {
            super.moveMotherNature(localPlayer, steps);
        } catch (IllegalArgumentException e) {
            getView().getRenderer().showErrorMessage(e.getMessage());
        }
    }

    /**
     * Sends a message with the student that will be moved from the entrance to the dining room
     *
     * @param localPlayer the nickname of the local player
     * @param student     the colour of the student chosen
     */
    @Override
    public void moveStudentToDiningRoom(String localPlayer, Colour student) {
        try {
            super.moveStudentToDiningRoom(localPlayer, student);
        } catch (IllegalArgumentException e) {
            getView().getRenderer().showErrorMessage(e.getMessage());
        }
    }

    /**
     * Sends a message with the student that will be moved from the entrance to the selected island
     *
     * @param localPlayer  the nickname of the local player
     * @param student      the colour of the student chosen
     * @param groupIsland  the index of the group island
     * @param singleIsland the index of the single island
     */
    @Override
    public void moveStudentToIsland(String localPlayer, Colour student, int groupIsland, int singleIsland) {
        try {
            super.moveStudentToIsland(localPlayer, student, groupIsland, singleIsland);
        } catch (IllegalArgumentException e) {
            getView().getRenderer().showErrorMessage(e.getMessage());
        }
    }

    /**
     * Sends a message with the selected character card
     *
     * @param localPlayer   the nickname of the local player
     * @param characterCard the index of the character card
     */
    @Override
    public void playCharacterCard(String localPlayer, int characterCard) {
        try {
            super.playCharacterCard(localPlayer, characterCard);
        } catch (IllegalArgumentException e) {
            getView().getRenderer().showErrorMessage(e.getMessage());
        }
    }

    /**
     * Sends a message with the selected colour for the character card
     *
     * @param localPlayer the nickname of the local player
     * @param colour      the colour chosen
     */
    @Override
    public void setColour(String localPlayer, Colour colour) {
        try {
            super.setColour(localPlayer, colour);
        } catch (IllegalArgumentException e) {
            getView().getRenderer().showErrorMessage(e.getMessage());
        }
    }

    /**
     * Sends a message with the selected student and the selected
     *
     * @param localPlayer  the nickname of the local player
     * @param colour       the colour of the student selected
     * @param groupIsland  the index of the group island
     * @param singleIsland the index of the single island
     */
    @Override
    public void setColourAndIsland(String localPlayer, Colour colour, int groupIsland, int singleIsland) {
        try {
            super.setColourAndIsland(localPlayer, colour, groupIsland, singleIsland);
        } catch (IllegalArgumentException e) {
            getView().getRenderer().showErrorMessage(e.getMessage());
        }
    }

    /**
     * Sends a message with the selected students to switch
     *
     * @param localPlayer    the nickname of the local player
     * @param colourCard     the colour of the student on card selected
     * @param colourEntrance the colour of the student in the entrance selected
     */
    @Override
    public void setColourCardEntrance(String localPlayer, Colour colourCard, Colour colourEntrance) {
        try {
            super.setColourCardEntrance(localPlayer, colourCard, colourEntrance);
        } catch (IllegalArgumentException e) {
            getView().getRenderer().showErrorMessage(e.getMessage());
        }
    }

    /**
     * Sends a message with the selected students to switch
     *
     * @param localPlayer      the nickname of the local player
     * @param colourDiningRoom the colour of the student in the dining room selected
     * @param colourEntrance   the colour of the student in the entrance selected
     */
    @Override
    public void setColourDiningRoomEntrance(String localPlayer, Colour colourDiningRoom, Colour colourEntrance) {
        try {
            super.setColourDiningRoomEntrance(localPlayer, colourDiningRoom, colourEntrance);
        } catch (IllegalArgumentException e) {
            getView().getRenderer().showErrorMessage(e.getMessage());
        }
    }

    /**
     * Sends a message with the selected group island -> for character card IslandInfluence
     *
     * @param localPlayer the nickname of the local player
     * @param groupIsland the index of the group island
     */
    @Override
    public void setGroupIsland(String localPlayer, int groupIsland) {
        try {
            super.setGroupIsland(localPlayer, groupIsland);
        } catch (IllegalArgumentException e) {
            getView().getRenderer().showErrorMessage(e.getMessage());
        }
    }

}

