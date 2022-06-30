package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.GamePhase;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;
import it.polimi.ingsw.view.beans.MockCard;
import it.polimi.ingsw.view.beans.MockGroupIsland;
import it.polimi.ingsw.view.beans.MockSingleIsland;

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

    /**
     * Updates the active character card in MockModel
     *
     * @param characterCard the new current character card
     */
    public void updateActiveCharacterCard(CharacterCardEnumeration characterCard) {
        if (characterCard == CharacterCardEnumeration.BASIC_STATE) {
            getView().getModel().setCurrentCharacterCard(getView().getModel().getBasicState());
        } else {
            getView().getModel().setCurrentCharacterCard(getView().getModel().getCharacterCardByType(characterCard));
        }
    }

    /**
     * Updates the cost of the selected character card
     *
     * @param characterCard the index of the character card selected
     * @param coins         the new cost of the character card
     */
    public void updateCardCoins(int characterCard, int coins) {
        getView().getModel().getCharacterCardByIndex(characterCard).setCost(coins);
    }

    /**
     * Updates the list of the character cards available
     *
     * @param characterCards the list of the character cards drawn for this game
     */
    public void updateCharacterCardsAvailable(List<CharacterCardEnumeration> characterCards) {
        characterCards.stream()
                .filter(characterCard -> getView().getModel().getCharacterCardByType(characterCard) == null)
                .forEach(characterCard -> getView().getModel().addCharacterCard(new MockCard(characterCard)));

        if (characterCards.contains(CharacterCardEnumeration.PROTECT_ISLAND)) {
            for (MockGroupIsland groupIsland : getView().getModel().getTable().getGroupIslands()) {
                groupIsland.setIsBasic(false);
            }
        }
    }

    /**
     * Updates a specific cloud tile when it is filled
     *
     * @param cloudTile the index of the cloud tile selected
     * @param students  the hash map containing the students on the cloud tile
     */
    public void updateCloudTileAdded(int cloudTile, HashMap<Colour, Integer> students) {
        getView().getModel().getTable().getCloudTileByIndex(cloudTile).setCloudTile(students);
        getView().getModel().getTable().addShownCLoudTile();
        getView().getModel().getTable().setShownCloudTile(cloudTile, students);

    }

    /**
     * Updates a specific cloud tile when it is chosen
     *
     * @param cloudTile the index of the cloud tile selected
     * @param students  the hash map containing the students on the cloud tile
     */
    public void updateCloudTileRemoved(int cloudTile, HashMap<Colour, Integer> students) {
        getView().getModel().getTable().getCloudTileByIndex(cloudTile).setCloudTile(students);
        getView().getModel().getTable().removeShownCloudTileByIndex(cloudTile);
    }

    /**
     * Updates the coins available for the selected player
     *
     * @param player the nickname of the player
     * @param coins  the coins of that player
     */
    public void updatePlayerCoins(String player, int coins) {
        getView().getModel().getPlayerByNickname(player).setCoins(coins);
    }

    /**
     * Updates the coins available on the table
     *
     * @param coins the new value of coins available on the table
     */
    public void updateTableCoins(int coins) {
        getView().getModel().setCoins(coins);
    }

    /**
     * Updates the current assistant card of the selected player
     *
     * @param player        the nickname of the player
     * @param assistantCard the index of the assistant card played in this turn
     */
    public void updateCurrentAssistantCard(String player, int assistantCard) {
        getView().getModel().getPlayerByNickname(player).setCurrentAssistantCard(assistantCard);
        getView().getModel().getPlayerByNickname(player).setAssistantCardValue(true);
    }

    /**
     * Updates the dining room on the school board of the selected player
     *
     * @param player     the nickname of the player
     * @param diningRoom the hash map containing the updated dining room
     */
    public void updateDiningRoom(String player, HashMap<Colour, Integer> diningRoom) {
        getView().getModel().getPlayerByNickname(player).getSchoolBoard().setDiningRoom(diningRoom);
    }

    public void updateEmptyBag() {
        getView().getModel().getTable().setBagEmpty();
    }

    /**
     * Removes a professor from the table when a player controls it
     *
     * @param colour the colour of the professor
     */
    public void updateProfessors(Colour colour) {
        getView().getModel().getTable().removeProfessorFromTable(colour);
    }

    /**
     * Updates the entrance on the school board of the selected player
     *
     * @param player   the nickname of the player
     * @param entrance the hash map containing the updated entrance
     */
    public void updateEntrance(String player, HashMap<Colour, Integer> entrance) {
        getView().getModel().getPlayerByNickname(player).getSchoolBoard().setEntrance(entrance);
    }

    /**
     * Updates the game phase
     *
     * @param gamePhase the new game phase
     */
    public void updateGamePhase(GamePhase gamePhase) {
        getView().getModel().setGamePhase(gamePhase);
    }

    /**
     * Updates the current player
     *
     * @param currentPlayer the nickname of the current player
     */
    public void updateCurrentPlayer(String currentPlayer) {
        getView().getModel().setCurrentPlayer(getView().getModel().getPlayerByNickname(currentPlayer));
    }

    /**
     * Updates the influence player on the group island
     *
     * @param player      the nickname of the new influence player
     * @param groupIsland the index of the group island where influence changed
     */
    public void updateInfluencePlayerOnGroupIsland(String player, int groupIsland) {
        getView().getModel().getTable().getGroupIslandByIndex(groupIsland).setInfluentPlayer(player);
        getView().getModel().getTable().setIslandInfluenceChanged(groupIsland);
    }


    /**
     * Updates the position of mother nature
     *
     * @param motherNaturePosition the integer representing the new position of mother nature
     */
    public void updateMotherNaturePosition(int motherNaturePosition) {
        getView().getModel().getTable().setMotherNaturePosition(motherNaturePosition);
    }

    /**
     * Updates the number of no entry tiles on the character card ProtectIsland
     *
     * @param num the updated number of no entry tiles on the card
     */
    public void updateNoEntryTilesOnCard(int num) {
        getView().getModel().getCharacterCardByType(CharacterCardEnumeration.PROTECT_ISLAND).setNumberOfNoEntryTile(num);
    }

    /**
     * Updates the number of no entry tile on a group island
     *
     * @param groupIsland the index of the group island
     * @param num         the updated number of no entry tiles
     */
    public void updateNoEntryTileOnGroupIsland(int groupIsland, int num) {
        getView().getModel().getTable().getGroupIslandByIndex(groupIsland).setNoEntryTile(num);
    }

    /**
     * Updates the professor table on the school board of the player selected
     *
     * @param player         the nickname of the player
     * @param professorTable the hash map representing the updated professor table
     */
    public void updateProfessorTable(String player, HashMap<Colour, Boolean> professorTable) {
        getView().getModel().getPlayerByNickname(player).getSchoolBoard().setProfessorTable(professorTable);
    }

    /**
     * Updates the round
     *
     * @param round the number of the current round
     */
    public void updateRound(int round) {
        getView().getModel().setRound(round);
    }

    /**
     * Updates the student on a single island
     *
     * @param groupIsland  the index of the group island selected
     * @param singleIsland the index of the single island
     * @param student      the hash map containing the updated students on the single island
     */
    public void updateSingleIsland(int groupIsland, int singleIsland, Colour student) {
        getView().getModel().getTable().getGroupIslandByIndex(groupIsland).getSingleIslandByIndex(singleIsland).setStudent(student);
    }

    /**
     * Updates the number of the towers on the school board of the selected player
     *
     * @param player the nickname of the player selected
     * @param towers the updated number of towers
     */
    public void updateTowers(String player, int towers) {
        getView().getModel().getPlayerByNickname(player).getSchoolBoard().setTowers(towers);
    }

    /**
     * Updates the tower colour of the selected player ate the beginning of the game
     *
     * @param player      the nickname of the selected player
     * @param towerColour the tower colour assigned to that player
     */
    public void updateTowerColour(String player, TowerColour towerColour) {
        getView().getModel().getPlayerByNickname(player).setTowerColour(towerColour);
    }

    /**
     * Updates the turn phase
     *
     * @param turnPhase the new turn phase
     */
    public void updateTurnPhase(TurnPhase turnPhase) {
        getView().getModel().setTurnPhase(turnPhase);
    }

    /**
     * Updates the ArrayList islands in MockTable according to the merge of the group island selected
     *
     * @param groupIsland1 the index of the first group island
     * @param groupIsland2 the index of the second group island
     */
    public void updateUnifyIsland(int groupIsland1, int groupIsland2) {
        for (MockGroupIsland groupIsland : view.getModel().getTable().getGroupIslands()) {
            groupIsland.clearInfluentPlayerProperty();
            if (view.getModel().isCharacterCardPresent(CharacterCardEnumeration.PROTECT_ISLAND)) {
                groupIsland.clearNoEntryTileProperty();
            }
        }
        getView().getModel().getTable().unify(groupIsland1, groupIsland2);
    }

    /**
     * Updates the winner of the game
     *
     * @param player the nickname of the winner
     */
    public void updateWinner(String player) {
        getView().getModel().setWinner(getView().getModel().getPlayerByNickname(player));
    }

    /**
     * Updates the position of mother nature after group islands merge
     *
     * @param motherNaturePosition the updated position of mother nature
     */
    public void updateMotherNaturePositionUnify(int motherNaturePosition) {
        getView().getModel().getTable().setMotherNaturePosition(motherNaturePosition);
    }

    /**
     * Updates the students on the character card StudentToIsland
     *
     * @param students the hash map representing the updated students on the card
     */
    public void updateStudentToIslandCard(HashMap<Colour, Integer> students) {
        getView().getModel().getCharacterCardByType(CharacterCardEnumeration.STUDENT_TO_ISLAND).setStudents(students);
    }

    /**
     * Updates the students on the character card StudentToDiningRoom
     *
     * @param students the hash map representing the updated students on the card
     */
    public void updateStudentToDiningRoomCard(HashMap<Colour, Integer> students) {
        getView().getModel().getCharacterCardByType(CharacterCardEnumeration.STUDENT_TO_DINING_ROOM).setStudents(students);
    }

    /**
     * Updates the students on the character card StudentToEntrance
     *
     * @param students the hash map representing the updated students on the card
     */
    public void updateStudentToEntranceCard(HashMap<Colour, Integer> students) {
        getView().getModel().getCharacterCardByType(CharacterCardEnumeration.STUDENT_TO_ENTRANCE).setStudents(students);
    }

    /**
     * Updates the current assistant card to null
     *
     * @param player the nickname of the player
     */
    public void updateCurrentAssistantCardNull(String player) {
        getView().getModel().getPlayerByNickname(player).setAssistantCardValue(false);
    }


    /**
     * Updates the students on a card
     *
     * @param student0 students on card 0
     * @param student1 students on card 1
     * @param student2 students on card 2
     */
    public void updateCardStudents(HashMap<Colour, Integer> student0, HashMap<Colour, Integer> student1, HashMap<Colour, Integer> student2) {
        if(getView().getModel().getReconnected()){
            if(!student0.isEmpty()){
                getView().getModel().getCharacterCardByIndex(0).setStudents(student0);
            }
            if(!student1.isEmpty()){
                getView().getModel().getCharacterCardByIndex(1).setStudents(student1);
            }
            if(!student2.isEmpty()){
                getView().getModel().getCharacterCardByIndex(2).setStudents(student2);
            }
        }
    }

    /**
     * Update the number of islands
     */
    public void updateIslands(int groupIslands, boolean expert){
        if(getView().getModel().getReconnected()){
            getView().getModel().getTable().setGroupIslands(groupIslands, expert);
        }
    }

    /**
     * Update the influent players
     *
     * @param influentPlayers a list containing the influent players for every groupIsland
     */
    public void updateInfluentPlayers(List<String> influentPlayers){
        if(getView().getModel().getReconnected()){
            for(int i = 0; i < getView().getModel().getTable().getNumberOfGroupIslands(); i++){
                if(influentPlayers.get(i).equals("")){
                    getView().getModel().getTable().getGroupIslandByIndex(i).setInfluentPlayer(null);
                }else{
                    getView().getModel().getTable().getGroupIslandByIndex(i).setInfluentPlayer(influentPlayers.get(i));
                }
            }
        }
    }

    /**
     * Update the noEntryTiles
     *
     * @param noEntryTiles a list containing the noEntryTiles for every groupIsland
     */
    public void updateNoEntryTiles(List<Integer> noEntryTiles){
        if(getView().getModel().getReconnected()){
            for(int i = 0; i < getView().getModel().getTable().getNumberOfGroupIslands(); i++){
                getView().getModel().getTable().getGroupIslandByIndex(i).setNoEntryTile(noEntryTiles.get(i));
            }
        }
    }

    /**
     * Update the singleIslands
     *
     * @param singleIslands a list containing the singleIslands for every groupIsland
     */
    public void updateSingleIslands(List<Integer> singleIslands){
        if(getView().getModel().getReconnected()){
            for(int i = 0; i < getView().getModel().getTable().getNumberOfGroupIslands(); i++){
                for(int j = 1; j < singleIslands.get(i); j++){
                    getView().getModel().getTable().getGroupIslandByIndex(i).addMockSingleIsland(new MockSingleIsland());
                }
            }
        }
    }

    /**
     * Update the students on the singleIslands
     *
     * @param students a hashMap containing the students for every singleIslands
     */
    public void updateStudents(HashMap<Integer, HashMap<Colour, Integer>> students){
        if(getView().getModel().getReconnected()){
            int count = 0;

            for(int i = 0; i < getView().getModel().getTable().getNumberOfGroupIslands(); i++){
                for(int j = 0; j < getView().getModel().getTable().getGroupIslandByIndex(i).getNumberOfSingleIslands(); j++){
                    for(Colour colour: Colour.values()){
                        for(int k = 0; k < students.get(count).get(colour); k++){
                            getView().getModel().getTable().getGroupIslandByIndex(i).getSingleIslandByIndex(j).setStudent(colour);
                        }
                    }

                    count++;
                }
            }
        }
    }

    /**
     * Update the students on the cloudTiles
     *
     * @param studentsOnCloudTiles a hashMap containing the students for every cloudTile
     */
    public void updateStudentsOnShownCloudTiles(HashMap<Integer, HashMap<Colour, Integer>> studentsOnCloudTiles){

        for(int i = 0; i < studentsOnCloudTiles.size(); i++){
            getView().getModel().getTable().addShownCLoudTile();
            getView().getModel().getTable().getShownCloudTiles().get(i).setCloudTile(studentsOnCloudTiles.get(i));
        }
    }

    /**
     * Updates the active character card in MockModel when a player reconnects
     *
     * @param characterCard the new current character card
     */
    public void updateReconnectedActiveCharacterCard(CharacterCardEnumeration characterCard) {
        if (characterCard == CharacterCardEnumeration.BASIC_STATE) {
            getView().getModel().setCurrentCharacterCard(getView().getModel().getBasicState());
        } else {
            getView().getModel().setCurrentCharacterCard(getView().getModel().getCharacterCardByType(characterCard));
        }
    }

    public void updateProfessorsReconnection(HashMap<Colour, Boolean> professors){

        for(Colour colour : Colour.values()){
            if(!professors.get(colour)){
                getView().getModel().getTable().removeProfessorFromTable(colour);
            }
        }
    }
}