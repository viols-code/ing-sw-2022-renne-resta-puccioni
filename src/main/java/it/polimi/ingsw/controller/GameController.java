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

        for(Colour colour: Colour.values()){
            for(int i=0;i<2;i++){
                game.getTable().getBag().addStudentBag(colour);
            }
        }

        for(int i=1;i<12;i++){
            if(i==5)i++;
            game.getTable().getGroupIslandByIndex(i).getIslandByIndex(0).addStudent(game.getTable().getBag().bagDrawStudent());
        }

        for(Colour colour: Colour.values()){
            for(int i=0;i<24;i++){
                game.getTable().getBag().addStudentBag(colour);
            }
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

    private boolean canPlayAssistantCard(int player, int assistantCard){
        for(int i=0;i<player;i++){
            if(game.getPlayerByIndex(i).getCurrentAssistantCard().equals(game.getAssistantCard(assistantCard))) return false;
        }
        return true;
    }

    public void moveStudentToIsland(int player, Colour colour, int groupIsland, int singleIsland){
    /*
    CONTROLLO CHE NON NE ABBIA MESSI PI§ DI 4
     */
        if(game.getGamePhase() == GamePhase.PLAYING){
            if(game.getTurnPhase() == TurnPhase.MOVE_STUDENT){
                if(game.isCurrentPlayer(game.getPlayerByIndex(player))){
                    if(game.getPlayerByIndex(player).getSchoolBoard().getEntrance(colour) > 0){
                        game.getTable().getGroupIslandByIndex(groupIsland).getIslandByIndex(singleIsland).addStudent(colour);
                        game.getPlayerByIndex(player).getSchoolBoard().removeStudentFromEntrance(colour);
                    }
                }
            }
        }
    }

    public void moveStudentToDiningRoom(int player, Colour colour){
            if(game.getGamePhase() == GamePhase.PLAYING && game.getTurnPhase() == TurnPhase.MOVE_STUDENT){
                if(game.isCurrentPlayer(game.getPlayerByIndex(player))){
                    if(game.getPlayerByIndex(player).getSchoolBoard().getEntrance(colour) > 0){

                    }
                }
            }
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
        game.setTurnPhase(TurnPhase.MOVE_STUDENT);
    }

    private void nobodyPlayed(){
        for(int i = 0; i < numberOfPlayer; i++){
            game.getPlayerByIndex(i).setHasAlreadyPlayed(false);
        }
    }


    public void addPlayer(String nickname, Wizard wizard) {
        if (isGameExpert) {
            game.addPlayer(new ExpertPlayer(nickname, wizard));
        } else {
            game.addPlayer(new BasicPlayer(nickname, wizard));
        }
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
