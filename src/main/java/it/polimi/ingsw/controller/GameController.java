package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.model.table.island.AdvancedGroupIsland;
import it.polimi.ingsw.model.table.island.BasicGroupIsland;
import it.polimi.ingsw.model.table.island.GroupIsland;
import it.polimi.ingsw.model.player.BasicPlayer;
import it.polimi.ingsw.model.player.ExpertPlayer;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.table.CloudTile;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class GameController {
    /**
     * The Game
     */
    private final Game game;

    /**
     * Indicates if the game is in the expert mode
     */
    private final boolean isGameExpert;

    /**
     * The numberOfPlayer
     */
    private final int numberOfPlayer;

    /**
     * Constructor: creates a GameController
     *
     * @param isGameExpert indicates if the game is in the expert mode
     */
    public GameController(boolean isGameExpert, int numberOfPlayer) {
        this.isGameExpert = isGameExpert;
        this.numberOfPlayer = numberOfPlayer;

        if (isGameExpert) {
            game = new ExpertGame();

            List<Class<? extends CharacterCard>> cardTypes = new ArrayList<>(
                    Arrays.asList(StudentToIsland.class, TakeProfessor.class, IslandInfluence.class,
                            MotherNatureMovement.class, ProtectIsland.class, NoTower.class, StudentToEntrance.class,
                            TwoPoints.class, NoColour.class, ExchangeEntranceDiningRoom.class, StudentToDiningRoom.class,
                            ThreeStudent.class));

            Collections.shuffle(cardTypes);

            for (int i = 0; i < 3; i++) {
                try {
                    CharacterCard cardInstance = cardTypes.get(i).getConstructor(Game.class).newInstance(game);
                    game.addCharacterCard(cardInstance);
                } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

            boolean hasProtectIsland = game.hasProtectIslandCard();

            if (hasProtectIsland) {
                for (int i = 0; i < 12; i++) {
                    game.getTable().addGroupIsland(new AdvancedGroupIsland());
                }
            } else {
                for (int i = 0; i < 12; i++) {
                    game.getTable().addGroupIsland(new BasicGroupIsland());
                }
            }


        } else {
            game = new BasicGame();
        }

        game.getTable().setMotherNaturePosition(0);
        if(numberOfPlayer == 3){
            game.setNumberOfTowersPerPlayer(6);
            game.setNumberStudentsEntrance(9);
            game.setStudentNumberMovement(4);
        } else {
            game.setNumberOfTowersPerPlayer(8);
            game.setNumberStudentsEntrance(7);
            game.setStudentNumberMovement(3);
        }

        for(int i = 0; i < numberOfPlayer; i++){
            createCloudTile();
        }
    }

    public Game getGame() {
        return game;
    }

    public void playCharacterCard(int player, int characterCard) {
        if (isGameExpert) {
            if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
                if (!game.getHasPlayedCharacterCard()) {
                    game.setActiveCharacterCard(game.getCharacterCardsByIndex(characterCard));
                    game.setHasPlayedCharacterCard(false);
                }
            }
        }
    }

    public void playAssistantCard(int player, int assistantCard) {
        /*
        AGGIUNGERE IL CONTROLLO CHE NESSUNO ABBIA GIà GIOCATO QUEL VALORE DI CARTA
         */
        if(game.getGamePhase() == GamePhase.PLAY_ASSISTANT_CARD){
            if (game.isCurrentPlayer(game.getPlayerByIndex(player))){
                if(game.getPlayerByIndex(player).isAssistantCardPresent(game.getAssistantCard(assistantCard))){
                    game.getPlayerByIndex(player).setCurrentAssistantCard(game.getAssistantCard(assistantCard));
                    game.getPlayerByIndex(player).removeAssistantCard(game.getAssistantCard(assistantCard));
                    game.getPlayerByIndex(player).setHasAlreadyPlayed(true);

                    if(!endPhase()){
                        game.setCurrentPlayer(game.nextPlayerClockwise());
                    } else {
                        endPlayAssistantCard();
                    }
                }
            }
        }
    }

    public void moveStudentToIsland(int player, Colour colour, int groupIsland, int singleIsland){

        if(game.getGamePhase() == GamePhase.PLAYING && game.getTurnPhase() == TurnPhase.MOVE_STUDENT){
            if(game.isCurrentPlayer(game.getPlayerByIndex(player))){
                if(game.getPlayerByIndex(player).getSchoolBoard().getEntrance(colour) > 0){
                    if(checkStudentsMovement(player)) {
                        game.getTable().getGroupIslandByIndex(groupIsland).getIslandByIndex(singleIsland).addStudent(colour);
                        game.getPlayerByIndex(player).getSchoolBoard().removeStudentFromEntrance(colour);
                    }
                }
            }
        }

        if(checkEndMovementPhase(player)){
            game.setTurnPhase(TurnPhase.MOVE_MOTHER_NATURE);
        }
    }

    private boolean checkStudentsMovement(int player){
        if(game.getPlayerByIndex(player).getSchoolBoard().getNumberStudentsEntrance() >= game.getNumberStudentsEntrance() - 4){
            return true;
        }

        return false;
    }

    private boolean checkEndMovementPhase(int player){
        if(game.getPlayerByIndex(player).getSchoolBoard().getNumberStudentsEntrance() == 4){
            return true;
        }

        return false;
    }

    private boolean endPhase(){
        boolean endPhase = true;

        for(int i = 0; i < numberOfPlayer; i++){
            if(!game.getPlayerByIndex(i).getHasAlreadyPlayed()){
                endPhase = false;
            }
        }

        return endPhase;
    }

    private void endPlayAssistantCard(){
        game.setGamePhase(GamePhase.PLAYING);
        nobodyPlayed();
        game.setCurrentPlayer(game.nextPlayerTurn());
        game.setFirstPlayerTurn(game.nextPlayerTurn());
        game.setTurnPhase(TurnPhase.MOVE_STUDENT);
    }

    private void nobodyPlayed(){
        for(int i = 0; i < numberOfPlayer; i++){
            game.getPlayerByIndex(i).setHasAlreadyPlayed(false);
        }
    }

    /**
     * Adds a new player with the nickname and wizard chosen
     *
     * @param nickname
     * @param wizard
     */
    //Se il numero di giocatori è pari all'attributo in gameController si fa partire il gioco
    public void addPlayer(String nickname, Wizard wizard) {
        if (isGameExpert) {
            if(checkUniqueNickname(nickname)) {
                if(checkUniqueWizard(wizard)) { //altrimenti mandiamo un messaggio di cambiare nickname/wizard
                    game.addPlayer(new ExpertPlayer(nickname, wizard));
                }
            }
        } else {
            if(checkUniqueNickname(nickname)){
                if(checkUniqueWizard(wizard)){
                    game.addPlayer(new BasicPlayer(nickname, wizard));
                }
            }
        }

        if(numberOfPlayer==game.getNumberOfPlayer()){
            game.setCurrentPlayer(game.getPlayerByIndex(0));
            game.setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        }
    }

    /**
     * Checks if the nickname has already been taken
     *
     * @param nickname
     * @return a boolean which says if the nickname has already been taken
     */
    private boolean checkUniqueNickname(String nickname){

        for(int i = 0; i < numberOfPlayer; i++){
            if(game.getPlayerByIndex(i)!=game.getCurrentPlayer()){
                if(game.getPlayerByIndex(i).getNickname().equals(game.getCurrentPlayer().getNickname())){
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks if the nickname has already been taken
     *
     * @param wizard
     * @return a boolean which says if the wizard has already been taken
     */
    private boolean checkUniqueWizard(Wizard wizard){

        for(int i = 0; i < numberOfPlayer; i++){
            if(game.getPlayerByIndex(i)!=game.getCurrentPlayer()){
                if(game.getPlayerByIndex(i).getWizard()==game.getCurrentPlayer().getWizard()){
                    return false;
                }
            }
        }

        return true;
    }


    /**
     * Unifies two GroupIsland
     *
     * @param groupIsland1 the first GroupIsland to be unified
     * @param groupIsland2 the second GroupIsland to be unified
     */
    private void unifyGroupIsland(GroupIsland groupIsland1, GroupIsland groupIsland2) {

        for (int i = 0; i < groupIsland2.getNumberOfSingleIsland(); i++) {
            groupIsland1.addSingleIsland(groupIsland2.getIslandByIndex(i));
        }
    }

    private void createCloudTile(){
        HashMap<Colour, Integer> students = new HashMap<>();
        for(Colour colour : Colour.values()){
            students.put(colour, 0);
        }

        for(int i = 0; i < game.getStudentNumberMovement(); i++){
            Colour colour1 = game.getTable().getBag().bagDrawStudent();
            students.replace(colour1, students.get(colour1), students.get(colour1) + 1);
        }

        game.getTable().addCLoudTile(new CloudTile(students));
    }

}
