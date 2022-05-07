package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;
import it.polimi.ingsw.view.beans.MockCard;

import java.util.HashMap;
import java.util.List;

/**
 * Class responsible for handling the updates that are received from the server.
 */
public abstract class ModelUpdateHandler {
    private final View view;

    /**
     * Constructs a new ModelUpdateHandler.
     *
     * @param view the view responsible for this model update handler
     */
    protected ModelUpdateHandler(View view) {
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

    public void updateActiveCharacterCard(int characterCard){
        getView().getModel().setCurrentCharacterCard(getView().getModel().getCharacterCardByIndex(characterCard));
    }

    public void updateCardCoins(int characterCard, int coins){
        getView().getModel().getCharacterCardByIndex(characterCard).setCost(coins);
    }

    public void updateCharacterCardsAvailable(List<CharacterCardEnumeration> characterCards){
        characterCards.stream()
                .filter(characterCard -> getView().getModel().getCharacterCardByType(characterCard) == null)
                .forEach(characterCard -> getView().getModel().addCharacterCard(new MockCard(characterCard)));
    }

    public void updateCloudTile(int cloudTile, HashMap<Colour,Integer> students){
        getView().getModel().getTable().getCloudTileByIndex(cloudTile).setCloudTile(students);
    }

    public void updateCoins(int coins){
        getView().getModel().setCoins(coins);
    }

    public void updateCurrentAssistantCard(String player, int assistantCard){
        getView().getModel().getPlayerByNickname(player).setCurrentAssistantCard(assistantCard);
    }

    public void updateDiningRoom(String player, HashMap<Colour,Integer> diningRoom){
        getView().getModel().getPlayerByNickname(player).getSchoolBoard().setDiningRoom(diningRoom);
    }

    public void updateEmptyBag(){
        //to-do: think how to show it
    }

    public void updateEntrance(String player, HashMap<Colour,Integer> entrance){
        getView().getModel().getPlayerByNickname(player).getSchoolBoard().setEntrance(entrance);
    }

    public void updateGamePhase(GamePhase gamePhase){
        getView().getModel().setGamePhase(gamePhase);
    }

    public void updateIslandInfluence(String player, int groupIsland){
        getView().getModel().getTable().getGroupIslandByIndex(groupIsland);
    }

    public void updateIslandInfluence(int groupIsland){
        //to-do: think how to show it
    }

    public void updateMotherNaturePosition(int motherNaturePosition){
        getView().getModel().getTable().setMotherNaturePosition(motherNaturePosition);
    }

    public void noColourUpdate(Colour colour){
        //to-do think how to show it
    }

    public void updateNoEntryTilesOnCard(int num){
        getView().getModel().getCharacterCardByType(CharacterCardEnumeration.PROTECT_ISLAND).setNumberOfNoEntryTile(num);
    }

    public void updateNoEntryTileOnGroupIsland(int groupIsland, int num){
        getView().getModel().getTable().getGroupIslandByIndex(groupIsland).setNoEntryTile(num);
    }

    public void updatePlayers(String nickname, Wizard wizard){
        //to-do: capire se l'aggiunta dei player nel mock model avviene prima o dopo la creazione di game
    }

    public void updateProfessorTable(String player, HashMap<Colour,Boolean> professorTable){
        getView().getModel().getPlayerByNickname(player).getSchoolBoard().setProfessorTable(professorTable);
    }

    public void updateRound(int round){
        getView().getModel().setRound(round);
    }



}