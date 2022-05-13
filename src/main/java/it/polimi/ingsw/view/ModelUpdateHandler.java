package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;
import it.polimi.ingsw.view.beans.MockCard;
import it.polimi.ingsw.view.beans.MockGroupIsland;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

    public void updateActiveCharacterCard(CharacterCardEnumeration characterCard){
        if(characterCard == CharacterCardEnumeration.BASIC_STATE){
            getView().getModel().setCurrentCharacterCard(null);
        }
        else {
            getView().getModel().setCurrentCharacterCard(getView().getModel().getCharacterCardByType(characterCard));
        }
    }

    public void updateCardCoins(int characterCard, int coins){
        getView().getModel().getCharacterCardByIndex(characterCard).setCost(coins);
    }

    public void updateCharacterCardsAvailable(List<CharacterCardEnumeration> characterCards){
        characterCards.stream()
                .filter(characterCard -> getView().getModel().getCharacterCardByType(characterCard) == null)
                .forEach(characterCard -> getView().getModel().addCharacterCard(new MockCard(characterCard)));

        if(characterCards.contains(CharacterCardEnumeration.PROTECT_ISLAND)){
            for(MockGroupIsland groupIsland : getView().getModel().getTable().getGroupIslands()){
                groupIsland.setIsBasic(false);
            }
        }
    }

    public void updateCloudTile(int cloudTile, HashMap<Colour,Integer> students){
        getView().getModel().getTable().getCloudTileByIndex(cloudTile).setCloudTile(students);
        if(isCLoudTileEmpty(students)){
            getView().getModel().getTable().removeShownCloudTileByIndex(cloudTile);
        }
        else{
            getView().getModel().getTable().addShownCLoudTile();
            getView().getModel().getTable().setShownCloudTile(cloudTile,students);
        }
    }

    private boolean isCLoudTileEmpty(HashMap<Colour,Integer> students){
        List<Integer> res = students.entrySet()
                .stream()
                .map(stud -> stud.getValue())
                .filter(num -> num > 0)
                .collect(Collectors.toList());
        return res.size() == 0;
    }

    public void updatePlayerCoins(String player, int coins){
        getView().getModel().getPlayerByNickname(player).setCoins(coins);
    }

    public void updateTableCoins(int coins){
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

    public void updateProfessors(Colour colour){
        getView().getModel().getTable().removeProfessorFromTable(colour);
    }

    public void updateEntrance(String player, HashMap<Colour,Integer> entrance){
        getView().getModel().getPlayerByNickname(player).getSchoolBoard().setEntrance(entrance);
    }

    public void updateGamePhase(GamePhase gamePhase){
        getView().getModel().setGamePhase(gamePhase);
    }

    public void updateCurrentPlayer(String currentPlayer){
        getView().getModel().setCurrentPlayer(getView().getModel().getPlayerByNickname(currentPlayer));
    }

    public void updateInfluencePlayerOnGroupIsland(String player, int groupIsland){
        //to-do: sistemare la notify
        getView().getModel().getTable().getGroupIslandByIndex(groupIsland).setInfluentPlayer(player);
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
        //to-do: sistemare la notify
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

    public void updateSingleIsland(int groupIsland, int singleIsland, Colour student){
        //to-do: sistemare la notify in SingleIsland
        getView().getModel().getTable().getGroupIslandByIndex(groupIsland).getSingleIslandByIndex(singleIsland).setStudent(student);
    }

    public void updateTowers(String player, int towers){
        getView().getModel().getPlayerByNickname(player).getSchoolBoard().setTowers(towers);
    }

    public void updateTowerColour(String player, TowerColour towerColour){
        //to-do: message TowerColourUpdate
        getView().getModel().getPlayerByNickname(player).setTowerColour(towerColour);
    }

    public void updateTurnPhase(TurnPhase turnPhase){
        getView().getModel().setTurnPhase(turnPhase);
    }

    public void updateUnifyIsland(int groupIsland1, int groupIsland2){
        getView().getModel().getTable().unify(groupIsland1,groupIsland2);
    }

    public void updateWinner(String player){
        getView().getModel().setWinner(getView().getModel().getPlayerByNickname(player));
    }

    public void updateMotherNaturePositionUnify(int motherNaturePosition){
        getView().getModel().getTable().setMotherNaturePosition(motherNaturePosition);
    }

    /*
    character cards
     */
    public void updateStudentToIslandCard(HashMap<Colour,Integer> students){
        getView().getModel().getCharacterCardByType(CharacterCardEnumeration.STUDENT_TO_ISLAND).setStudents(students);
    }

    public void updateStudentToDiningRoomCard(HashMap<Colour,Integer> students){
        getView().getModel().getCharacterCardByType(CharacterCardEnumeration.STUDENT_TO_DINING_ROOM).setStudents(students);
    }

    public void updateStudentToDEntranceCard(HashMap<Colour,Integer> students){
        getView().getModel().getCharacterCardByType(CharacterCardEnumeration.STUDENT_TO_ENTRANCE).setStudents(students);
    }


}