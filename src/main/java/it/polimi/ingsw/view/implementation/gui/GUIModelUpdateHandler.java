package it.polimi.ingsw.view.implementation.gui;

import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.view.ModelUpdateHandler;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;

public class GUIModelUpdateHandler extends ModelUpdateHandler {
    public GUIModelUpdateHandler(GUI gui) {
        super(gui);
    }

    @Override
    public void updateGamePhase(GamePhase gamePhase) {
        super.updateGamePhase(gamePhase);
        if (gamePhase == GamePhase.PLAY_ASSISTANT_CARD && getView().getModel().getRound() == 1) {
            GUI.instance().showPlayerBoard();
        }
    }

    public void updateCurrentAssistantCard(String playerName, int assistantCard) {
        super.updateCurrentAssistantCard(playerName, assistantCard);
    }

    /**
     * Updates the turn phase
     *
     * @param turnPhase the new turn phase
     */
    @Override
    public void updateTurnPhase(TurnPhase turnPhase) {
        super.updateTurnPhase(turnPhase);
        if (GUI.instance().isGuidedMode()) {
            if (turnPhase != TurnPhase.WAITING && turnPhase != TurnPhase.ENDGAME) {
                if (GUI.instance().getModel().getCurrentPlayer().getNickname().equals(GUI.instance().getPlayerName())) {
                    GUI.instance().getRenderer().showGameMessage(getYourTurnMessage());
                } else {
                    GUI.instance().getRenderer().showGameMessage(getOtherTurnMessage());
                }
            }
        }
    }

    private String getYourTurnMessage() {
        String message = "";
        switch (GUI.instance().getModel().getTurnPhase()) {
            case PLAY_ASSISTANT_CARD -> message = "It's your turn: select the assistant card you want to play." +
                    "\n CLick on \"Deck\" to open your deck and click on the assistant card you want to play";
            case MOVE_STUDENT -> message = "It's your turn: you have to move " +
                    (GUI.instance().getModel().getPlayers().size() + 1) +
                    " students from your entrance to your dining room or to the islands.\n" +
                    "Click on the student you want to move and the click on the dining room if you want to move it on the dining room " +
                    "or go to the islands with the button \"Islands\" to select the island where put your student";
            case MOVE_MOTHER_NATURE -> message = "You have to move mother nature: go to the islands with the button \"Islands\" and click " +
                    "on the island where you want to put mother nature";
            case CHOOSE_CLOUD_TILE -> message = "You have to choose a cloud tile: " +
                    "go to the cloud tiles with the button \"Cloud Tiles\" and click on the one you want to choose";
        }
        return message;
    }

    private String getOtherTurnMessage() {
        String message = "";
        switch (GUI.instance().getModel().getTurnPhase()) {
            case PLAY_ASSISTANT_CARD -> message = "It's " + GUI.instance().getModel().getCurrentPlayer().getNickname() + "'s turn to choose an assistant card: go to their school board " +
                    "to see the assistant card chosen with the button " + GUI.instance().getModel().getCurrentPlayer().getNickname() + "'s School Board";
            case MOVE_STUDENT -> message = GUI.instance().getModel().getCurrentPlayer().getNickname() +
                    " is moving students from the entrance: to see the updates go to the school board with the button " + GUI.instance().getModel().getCurrentPlayer().getNickname() + "'s School Board" +
                    "or go to the islands with the button \"Islands\"";
            case MOVE_MOTHER_NATURE -> message = GUI.instance().getModel().getCurrentPlayer().getNickname() +
                    " is moving mother nature: go to the islands with the button \"Islands\" to see the updates ";
            case CHOOSE_CLOUD_TILE -> message = GUI.instance().getModel().getCurrentPlayer().getNickname() +
                    " is choosing a cloud tile: " +
                    "go to the cloud tiles with the button \"Cloud Tiles\" to see the updates";
        }
        return message;
    }

    /**
     * Updates the active character card in MockModel
     *
     * @param characterCard the new current character card
     */
    @Override
    public void updateActiveCharacterCard(CharacterCardEnumeration characterCard) {
        super.updateActiveCharacterCard(characterCard);
        if (GUI.instance().isGuidedMode()) {
            if (characterCard == CharacterCardEnumeration.BASIC_STATE) {
                GUI.instance().getRenderer().showGameMessage("The character card played is not active anymore");
            } else if (GUI.instance().getModel().getCurrentPlayer().getNickname().equals(GUI.instance().getPlayerName())) {
                GUI.instance().getRenderer().showGameMessage("You played the character card " + characterCard + "\n" + getCharacterCardMessage(characterCard));
            } else {
                GUI.instance().getRenderer().showGameMessage(GUI.instance().getModel().getCurrentPlayer().getNickname() + " played the character card " + characterCard);
            }
        }
    }

    private String getCharacterCardMessage(CharacterCardEnumeration characterCard) {
        String message = "\n";
        switch (characterCard) {
            case EXCHANGE_ENTRANCE_DINING_ROOM -> message += "You can swap one student in your entrance with one in your dining room" +
                    " to do that, select a student in your entrance and then select one student in your dining room.\n" +
                    "You can do that two times.";
            case ISLAND_INFLUENCE -> message += "Click on the card to select the island where you want to calculate influence";
            case NO_COLOUR -> message += "Click on the card to choose the color to exclude from the influence computation";
            case PROTECT_ISLAND -> message += "Click on the card to select the island where you want to put a no entry tile";
            case STUDENT_TO_DINING_ROOM -> message += "Click on one student below the card to put it in your dining room";
            case STUDENT_TO_ENTRANCE -> message += "You can swap one student in your entrance with one on this card" +
                    " to do that, select a student in your entrance and then select one student below this card.\n" +
                    "You can do that three times.";
            case STUDENT_TO_ISLAND -> message += "You can move one of the students below this card to an island.\n" +
                    "To do that, click on a student below this card and then click on the card to open the islands and select the one where " +
                    "you want to put the student";
            case THREE_STUDENT -> message += "You can choose the student colour clicking on that card.";
        }
        return message;
    }


}

