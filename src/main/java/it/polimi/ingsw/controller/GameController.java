package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.game.BasicGame;
import it.polimi.ingsw.model.game.ExpertGame;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GamePhase;
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
        if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
            if (game.getGamePhase() == GamePhase.PLAY_ASSISTANT_CARD) {
                game.getPlayerByIndex(player).setCurrentAssistantCard(game.getAssistantCard(assistantCard));
                game.getPlayerByIndex(player).removeAssistantCard(game.getAssistantCard(assistantCard));
            }
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
            groupIsland1.addSingleIsland(groupIsland2.getIslands(i));
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
