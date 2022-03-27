package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.model.player.BasicPlayer;
import it.polimi.ingsw.model.player.ExpertPlayer;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.model.table.CloudTile;
import it.polimi.ingsw.model.table.island.AdvancedGroupIsland;
import it.polimi.ingsw.model.table.island.BasicGroupIsland;

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
     * @param numberOfPlayer indicates the numberOfPlayer
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
        settingBag();
        settingInteger();
        settingCloudTile();

        if (isGameExpert) {
            settingCard();
        }

    }

    /*
    SETTING PHASE
    */

    /**
     * Setting the bag and the students on the SingleIsland
     */
    private void settingBag() {
        for (Colour colour : Colour.values()) {
            for (int i = 0; i < 2; i++) {
                game.getTable().getBag().addStudentBag(colour);
            }
        }

        for (int i = 1; i < 12; i++) {
            if (i == 6) i++;
            game.getTable().getGroupIslandByIndex(i).getIslandByIndex(0).addStudent(game.getTable().getBag().bagDrawStudent());
        }

        for (Colour colour : Colour.values()) {
            for (int i = 0; i < 24; i++) {
                game.getTable().getBag().addStudentBag(colour);
            }
        }
    }

    /**
     * Setting the cloudTiles
     */
    private void settingCloudTile() {
        for (int i = 0; i < numberOfPlayer; i++) {
            createCloudTile();
        }
    }

    /**
     * Setting the integer for the control of the game
     */
    private void settingInteger() {
        game.getTable().setMotherNaturePosition(0);
        if (numberOfPlayer == 3) {
            game.setNumberOfTowersPerPlayer(6);
            game.setNumberStudentsEntrance(9);
            game.setStudentNumberMovement(4);
        } else {
            game.setNumberOfTowersPerPlayer(8);
            game.setNumberStudentsEntrance(7);
            game.setStudentNumberMovement(3);
        }
    }

    /**
     * Setting the CharacterCard
     */
    private void settingCard() {
        for (int i = 0; i < 3; i++) {
            game.getCharacterCardByIndex(i).setting();
        }
    }

    /*
    GAME
     */

    /**
     * Get the instance of the Game
     *
     * @return the Game
     */
    public Game getGame() {
        return game;
    }


    /*
    CharacterCard Playing
     */

    /**
     * Playing CharacterCard
     *
     * @param player the index of the player
     * @param characterCard the index of the CharacterCard
     */
    public void playCharacterCard(int player, int characterCard) {
        if (isGameExpert && player >= 0 && player < numberOfPlayer && characterCard >= 0 && characterCard < 3) {
            if (game.getGamePhase() == GamePhase.PLAYING) {
                if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
                    if (!game.getHasPlayedCharacterCard() &&
                            game.getCurrentPlayer().getCoins() >= game.getCharacterCardByIndex(characterCard).getCost()) {
                        game.setActiveCharacterCard(game.getCharacterCardByIndex(characterCard));
                        game.setHasPlayedCharacterCard(true);
                        game.getCurrentPlayer().removeCoins(game.getCharacterCardByIndex(characterCard).getCost());
                        game.getCharacterCardByIndex(characterCard).incrementCost();
                        game.setCoins(game.getCoins() + game.getCharacterCardByIndex(characterCard).getCost() - 1);
                        game.getCharacterCardByIndex(characterCard).professor();
                    }
                }
            }
        }
    }

    /*
    ASSISTANT CARD
     */

    /**
     * Playing AssistantCard
     *
     * @param player the index of the player
     * @param assistantCard the index of the AssistantCard
     */
    public void playAssistantCard(int player, int assistantCard) {
        if (player >= 0 && player < numberOfPlayer && assistantCard >= 0 && assistantCard < 10) {
            if (game.getGamePhase() == GamePhase.PLAY_ASSISTANT_CARD) {
                if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
                    if (game.getPlayerByIndex(player).isAssistantCardPresent(game.getAssistantCard(assistantCard))) {
                        if (canPlayAssistantCard(player, assistantCard)) {
                            game.getPlayerByIndex(player).setCurrentAssistantCard(game.getAssistantCard(assistantCard));
                            game.getPlayerByIndex(player).removeAssistantCard(game.getAssistantCard(assistantCard));
                            game.getPlayerByIndex(player).setHasAlreadyPlayed(true);

                            if (!endPhasePlay()) {
                                game.setCurrentPlayer(game.nextPlayerClockwise());
                            } else {
                                endPlayAssistantCard();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Check if the player can play an AssistantCard
     *
     * @param player the index of the player
     * @param assistantCard the index of the AssistantCard
     * @return true if the Player can play the AssistantCard, false otherwise
     */
    private boolean canPlayAssistantCard(int player, int assistantCard) {
        for (int i = 0; i < player; i++) {
            if (game.getPlayerByIndex(i).getHasAlreadyPlayed()) {
                if (game.getPlayerByIndex(i).getCurrentAssistantCard().equals(game.getAssistantCard(assistantCard)))
                    return false;
            }
        }
        return true;
    }

    /*
    MOVE STUDENT PHASE
     */

    /**
     * Move student from entrance to a SingleIsland
     *
     * @param player the index of the player
     * @param colour the colour of the student to be moved
     * @param groupIsland the index of the GroupIsland
     * @param singleIsland the index of the SingleIsland
     */
    public void moveStudentToIsland(int player, Colour colour, int groupIsland, int singleIsland) {
        if (player >= 0 && player < numberOfPlayer && groupIsland >= 0 && groupIsland < game.getTable().getNumberOfGroupIsland() && singleIsland >= 0 && singleIsland < game.getTable().getGroupIslandByIndex(groupIsland).getNumberOfSingleIsland()) {
            if (game.getGamePhase() == GamePhase.PLAYING && game.getTurnPhase() == TurnPhase.MOVE_STUDENT) {
                if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
                    if (game.getPlayerByIndex(player).getSchoolBoard().getEntrance(colour) > 0) {
                        if (checkStudentsMovement(player)) {
                            game.getTable().getGroupIslandByIndex(groupIsland).getIslandByIndex(singleIsland).addStudent(colour);
                            game.getPlayerByIndex(player).getSchoolBoard().removeStudentFromEntrance(colour);
                            if (checkEndMovementPhase(player)) {
                                game.setTurnPhase(TurnPhase.MOVE_MOTHER_NATURE);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Move student from entrance to a diningRoom
     *
     * @param player the index of the player
     * @param colour the colour of the student to be moved
     */
    public void moveStudentToDiningRoom(int player, Colour colour) {
        if (player >= 0 && player < numberOfPlayer) {
            if (game.getGamePhase() == GamePhase.PLAYING && game.getTurnPhase() == TurnPhase.MOVE_STUDENT) {
                if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
                    if (game.getPlayerByIndex(player).getSchoolBoard().getEntrance(colour) > 0) {
                        if (checkStudentsMovement(player) && game.getPlayerByIndex(player).getSchoolBoard().getDiningRoom(colour) < 10) {
                            game.getPlayerByIndex(player).getSchoolBoard().removeStudentFromEntrance(colour);
                            try {
                                game.getPlayerByIndex(player).getSchoolBoard().addStudentToDiningRoom(colour);
                            }catch(IllegalArgumentException ex){
                                ex.printStackTrace();
                            }
                            if (isGameExpert && ((game.getPlayerByIndex(player).getSchoolBoard().getDiningRoom(colour) + 1) % 3) == 0 && game.getCoins() > 0) {
                                game.getPlayerByIndex(player).addCoins(1);
                                game.setCoins(game.getCoins() - 1);
                            }
                            if (!game.getCurrentPlayer().getSchoolBoard().hasProfessor(colour)) {
                                game.getActiveCharacterCard().checkProfessor(colour);
                            }
                            if (checkEndMovementPhase(player)) {
                                game.setTurnPhase(TurnPhase.MOVE_MOTHER_NATURE);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Check for the movement of students
     *
     * @param player the index of the Player
     * @return true if the player can move another student, false otherwise
     */
    private boolean checkStudentsMovement(int player) {
        return game.getPlayerByIndex(player).getSchoolBoard().getNumberStudentsEntrance() >= game.getNumberStudentsEntrance() - 4;
    }

    /**
     * Check if the movement phase is ended
     *
     * @param player the index of the Player
     * @return true if the movement phase is ended, false otherwise
     */
    private boolean checkEndMovementPhase(int player) {
        return game.getPlayerByIndex(player).getSchoolBoard().getNumberStudentsEntrance() == game.getNumberStudentsEntrance() - game.getStudentNumberMovement();
    }

    /*
    MOVING MOTHER NATURE
     */


    /**
     * Move mother nature
     *
     * @param player the index of the Player
     * @param movement the number of moves
     */
    public void moveMotherNature(int player, int movement) {
        if (movement > 0) {
            if (game.getGamePhase() == GamePhase.PLAYING && game.getTurnPhase() == TurnPhase.MOVE_MOTHER_NATURE) {
                if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
                    if (game.getActiveCharacterCard().checkMotherNatureMovement(player, movement)) {
                        game.getTable().setMotherNaturePosition((game.getTable().getMotherNaturePosition() + movement) % game.getTable().getNumberOfGroupIsland());
                        game.getActiveCharacterCard().calculateInfluence(game.getTable().getMotherNaturePosition());
                        if (game.getWinner() != null) endGame();
                        else if (game.getTable().getNumberOfGroupIsland() <= 3) calculateWinner();
                        game.setTurnPhase(TurnPhase.CHOOSE_CLOUD_TILE);
                    }
                }

            }
        }
    }

    /**
     * Sets the end of the Game
     */
    private void endGame() {
        game.setGamePhase(GamePhase.END_GAME);
        game.setTurnPhase(TurnPhase.WAITING);
    }

    /**
     * Checks if all the players have played
     *
     * @return true if all the players have played, false otherwise
     */
    private boolean endPhasePlay() {
        boolean endPhase = true;

        for (int i = 0; i < numberOfPlayer; i++) {
            if (!game.getPlayerByIndex(i).getHasAlreadyPlayed()) {
                endPhase = false;
            }
        }

        return endPhase;
    }

    /**
     * Sets the end of the playAssistantCardPhase
     */
    private void endPlayAssistantCard() {
        game.setGamePhase(GamePhase.PLAYING);
        nobodyPlayed();
        game.setCurrentPlayer(game.nextPlayerTurn());
        game.setFirstPlayerTurn(game.nextPlayerTurn());
        game.setTurnPhase(TurnPhase.MOVE_STUDENT);
    }

    /**
     * Set hasAlreadyPlayed false for all players
     */
    private void nobodyPlayed() {
        for (int i = 0; i < numberOfPlayer; i++) {
            game.getPlayerByIndex(i).setHasAlreadyPlayed(false);
        }
    }

    /**
     * Adds a new player with the nickname and wizard chosen
     *
     * @param nickname the nickname chosen by the player
     * @param wizard   the wizard chosen by the player
     */
    public void addPlayer(String nickname, Wizard wizard) {
        if (game.getNumberOfPlayer() < numberOfPlayer && game.getGamePhase() == GamePhase.SETTING) {
            if(checkUniqueNickname(nickname)){
                if(checkUniqueWizard(wizard)){
                    if(isGameExpert){
                        game.addPlayer(new ExpertPlayer(nickname, wizard));
                        game.setCoins(game.getCoins() - 1);
                    } else{
                        game.addPlayer(new BasicPlayer(nickname, wizard));
                    }
                }
            }

            if (numberOfPlayer == game.getNumberOfPlayer()) {
                game.setCurrentPlayer(game.getPlayerByIndex(0));
                game.setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
            }
        }
    }

    /**
     * Checks if the nickname has already been taken
     *
     * @param nickname the nickname of the player
     * @return a boolean which says if the nickname has already been taken
     */
    private boolean checkUniqueNickname(String nickname) {
        for (int i = 0; i < game.getNumberOfPlayer(); i++) {
            if (game.getPlayerByIndex(i) != game.getCurrentPlayer()) {
                if (game.getPlayerByIndex(i).getNickname().equals(nickname)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks if the nickname has already been taken
     *
     * @param wizard the wizard of the player
     * @return a boolean which says if the wizard has already been taken
     */
    private boolean checkUniqueWizard(Wizard wizard) {

        for (int i = 0; i < game.getNumberOfPlayer(); i++) {
            if (game.getPlayerByIndex(i) != game.getCurrentPlayer()) {
                if (game.getPlayerByIndex(i).getWizard() == wizard) {
                    return false;
                }
            }
        }

        return true;
    }


    /**
     * Create the cloudTile
     */
    private void createCloudTile() {
        HashMap<Colour, Integer> students = new HashMap<>();
        for (Colour colour : Colour.values()) {
            students.put(colour, 0);
        }

        for (int i = 0; i < game.getStudentNumberMovement(); i++) {
            if (!game.getTable().getBag().isBagEmpty()) {
                Colour colour1 = game.getTable().getBag().bagDrawStudent();
                students.replace(colour1, students.get(colour1), students.get(colour1) + 1);
            }
        }

        game.getTable().addCLoudTile(new CloudTile(students));
    }

    /**
     * Choose a cloudTile
     *
     * @param player index of the player
     * @param cloudTile index of the cloudTile
     */
    public void chooseCloudTile(int player, int cloudTile) {
        game.setHasPlayedCharacterCard(false);
        game.setActiveCharacterCard(game.getBasicState());
        if (cloudTile >= 0 && cloudTile < game.getTable().getNumberOfCloudTile()) {
            if (game.getGamePhase() == GamePhase.PLAYING && game.getTurnPhase() == TurnPhase.CHOOSE_CLOUD_TILE) {
                if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
                    for (Colour colour : Colour.values()) {
                        for (int i = 0; i < game.getTable().getCloudTilesByIndex(cloudTile).getTileStudents(colour); i++) {
                            game.getCurrentPlayer().getSchoolBoard().addStudentToEntrance(colour);
                        }
                    }
                    game.getTable().removeCLoudTile(game.getTable().getCloudTilesByIndex(cloudTile));
                    game.getCurrentPlayer().setHasAlreadyPlayed(true);

                    if (!(endPhasePlay())) {
                        game.setCurrentPlayer(game.nextPlayerTurn());
                        game.setTurnPhase(TurnPhase.MOVE_STUDENT);
                    } else {
                        settingCloudTile();
                        game.setCurrentPlayer(game.getFirstPlayerTurn());
                        game.setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
                        game.setTurnPhase(TurnPhase.WAITING);
                        game.incrementRound();
                        nobodyPlayed();
                        if (game.getRound() >= 11) {
                            calculateWinner();
                            endGame();
                        }

                    }
                }
            }
        }
    }


    public void setColour(Colour colour) {
        game.getActiveCharacterCard().setColour(colour);
    }

    public void setColourAndIsland(Colour colour, int groupIsland, int singleIsland) {
        if (groupIsland >= 0 && groupIsland < game.getTable().getNumberOfGroupIsland() && singleIsland >= 0 && singleIsland < game.getTable().getGroupIslandByIndex(groupIsland).getNumberOfSingleIsland())
            game.getActiveCharacterCard().setColourAndIsland(colour, game.getTable().getGroupIslandByIndex(groupIsland).getIslandByIndex(singleIsland));
    }

    public void setGroupIsland(int groupIsland) {
        if (groupIsland >= 0 && groupIsland < game.getTable().getNumberOfGroupIsland())
            game.getActiveCharacterCard().setGroupIsland(groupIsland);
    }

    public void setColourDiningRoomEntrance(Colour colourDiningRoom, Colour colourEntrance) {
        game.getActiveCharacterCard().setColourDiningRoomEntrance(colourDiningRoom, colourEntrance);
    }

    public void setColourCardEntrance(Colour colourCard, Colour colourEntrance) {
        game.getActiveCharacterCard().setColourCardEntrance(colourCard, colourEntrance);
    }


    /**
     * Calculate the winner of the Game
     */
    private void calculateWinner() {
        int min = 8;
        List<Player> possibleWinner = new ArrayList<>();

        for (int i = 0; i < numberOfPlayer; i++) {
            if (game.getPlayerByIndex(i).getSchoolBoard().getTowers() < min) {
                min = game.getPlayerByIndex(i).getSchoolBoard().getTowers();
            }
        }

        for (int i = 0; i < numberOfPlayer; i++) {
            if (game.getPlayerByIndex(i).getSchoolBoard().getTowers() == min) {
                possibleWinner.add(game.getPlayerByIndex(i));
            }
        }

        int max = 0;

        if (possibleWinner.size() == 1) {
            game.setWinner(possibleWinner.get(0));
            return;
        } else {
            for (Player player : possibleWinner) {
                if (player.getSchoolBoard().getNumberOfProfessors() > max) {
                    max = player.getSchoolBoard().getNumberOfProfessors();
                }
            }
        }

        for (Player player : possibleWinner) {
            if (player.getSchoolBoard().getNumberOfProfessors() == max) {
                game.setWinner(player);
                return;
            }
        }
    }
}
