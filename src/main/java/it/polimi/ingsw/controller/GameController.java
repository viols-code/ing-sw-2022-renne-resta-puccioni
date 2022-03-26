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

    public Game getGame() {
        return game;
    }

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

    private void settingCloudTile() {
        for (int i = 0; i < numberOfPlayer; i++) {
            createCloudTile();
        }
    }

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

    private void settingCard() {
        for (int i = 0; i < 3; i++) {
            game.getCharacterCardsByIndex(i).setting();
        }
    }

    public void playCharacterCard(int player, int characterCard) {
        if (isGameExpert) {
            if (game.getGamePhase() == GamePhase.PLAYING) {
                if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
                    if (!game.getHasPlayedCharacterCard() &&
                            game.getCurrentPlayer().getCoins() >= game.getCharacterCardsByIndex(characterCard).getCost()) {
                        game.setActiveCharacterCard(game.getCharacterCardsByIndex(characterCard));
                        game.setHasPlayedCharacterCard(true);
                        game.getCurrentPlayer().removeCoins(game.getCharacterCardsByIndex(characterCard).getCost());
                        game.getCharacterCardsByIndex(characterCard).incrementCost();
                        game.setCoins(game.getCoins() + game.getCharacterCardsByIndex(characterCard).getCost() - 1);
                        game.getCharacterCardsByIndex(characterCard).professor();
                    }
                }
            }
        }
    }

    public void playAssistantCard(int player, int assistantCard) {
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

    private boolean canPlayAssistantCard(int player, int assistantCard) {
        for (int i = 0; i < player; i++) {
            if (game.getPlayerByIndex(i).getHasAlreadyPlayed()) {
                if (game.getPlayerByIndex(i).getCurrentAssistantCard().equals(game.getAssistantCard(assistantCard)))
                    return false;
            }
        }
        return true;
    }

    public void moveStudentToIsland(int player, Colour colour, int groupIsland, int singleIsland) {

        if (game.getGamePhase() == GamePhase.PLAYING && game.getTurnPhase() == TurnPhase.MOVE_STUDENT) {
            if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
                if (game.getPlayerByIndex(player).getSchoolBoard().getEntrance(colour) > 0) {
                    if (checkStudentsMovement(player)) {
                        game.getTable().getGroupIslandByIndex(groupIsland).getIslandByIndex(singleIsland).addStudent(colour);
                        game.getPlayerByIndex(player).getSchoolBoard().removeStudentFromEntrance(colour);
                    }
                }
            }
        }

        if (checkEndMovementPhase(player)) {
            game.setTurnPhase(TurnPhase.MOVE_MOTHER_NATURE);
        }
    }

    public void moveStudentToDiningRoom(int player, Colour colour) {
        if (game.getGamePhase() == GamePhase.PLAYING && game.getTurnPhase() == TurnPhase.MOVE_STUDENT) {
            if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
                if (game.getPlayerByIndex(player).getSchoolBoard().getEntrance(colour) > 0) {
                    if (checkStudentsMovement(player) && game.getPlayerByIndex(player).getSchoolBoard().getDiningRoom(colour) < 10) {
                        game.getPlayerByIndex(player).getSchoolBoard().removeStudentFromEntrance(colour);
                        game.getPlayerByIndex(player).getSchoolBoard().addStudentToDiningRoom(colour);
                        if (isGameExpert && ((game.getPlayerByIndex(player).getSchoolBoard().getDiningRoom(colour) + 1) % 3) == 0) {
                            game.getPlayerByIndex(player).addCoins(1);
                            game.setCoins(game.getCoins() - 1);
                        }
                        if (!game.getCurrentPlayer().getSchoolBoard().hasProfessor(colour)) {
                            game.getActiveCharacterCard().checkProfessor(colour);
                        }
                    }
                }
            }
        }

        if (checkEndMovementPhase(player)) {
            game.setTurnPhase(TurnPhase.MOVE_MOTHER_NATURE);
        }
    }

    private boolean checkStudentsMovement(int player) {
        return game.getPlayerByIndex(player).getSchoolBoard().getNumberStudentsEntrance() >= game.getNumberStudentsEntrance() - 4;
    }

    private boolean checkEndMovementPhase(int player) {
        return game.getPlayerByIndex(player).getSchoolBoard().getNumberStudentsEntrance() == 4;
    }


    public void moveMotherNature(int player, int movement) {

        if (game.getGamePhase() == GamePhase.PLAYING && game.getTurnPhase() == TurnPhase.MOVE_MOTHER_NATURE) {
            if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
                if (game.getActiveCharacterCard().checkMotherNatureMovement(player, movement)) {
                    game.getActiveCharacterCard().calculateInfluence(game.getTable().getMotherNaturePosition() + movement);

                    if (game.getWinner() != null) endGame();
                    else if (game.getTable().getNumberOfGroupIsland() <= 3) calculateWinner();

                    game.setTurnPhase(TurnPhase.CHOOSE_CLOUD_TILE);
                }

            }
        }
    }

    private void endGame() {

    }


    private boolean endPhasePlay() {
        boolean endPhase = true;

        for (int i = 0; i < numberOfPlayer; i++) {
            if (!game.getPlayerByIndex(i).getHasAlreadyPlayed()) {
                endPhase = false;
            }
        }

        return endPhase;
    }

    private void endPlayAssistantCard() {
        game.setGamePhase(GamePhase.PLAYING);
        nobodyPlayed();
        game.setCurrentPlayer(game.nextPlayerTurn());
        game.setFirstPlayerTurn(game.nextPlayerTurn());
        game.setTurnPhase(TurnPhase.MOVE_STUDENT);
    }

    private void nobodyPlayed() {
        for (int i = 0; i < numberOfPlayer; i++) {
            game.getPlayerByIndex(i).setHasAlreadyPlayed(false);
        }
    }

    /**
     * Adds a new player with the nickname and wizard chosen
     *
     * @param nickname the nickname chosen by the player
     * @param wizard the wizard chosen by the player
     */
    public void addPlayer(String nickname, Wizard wizard) {
        if(game.getNumberOfPlayer() < numberOfPlayer && game.getGamePhase() == GamePhase.SETTING){
            if (isGameExpert) {
                if (checkUniqueNickname(nickname)) {
                    if (checkUniqueWizard(wizard)) { //altrimenti mandiamo un messaggio di cambiare nickname/wizard
                        game.addPlayer(new ExpertPlayer(nickname, wizard));
                    }
                }
            } else {
                if (checkUniqueNickname(nickname)) {
                    if (checkUniqueWizard(wizard)) {
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

    public void chooseCloudTile(int player, int cloudTile) {

        if (game.getGamePhase() == GamePhase.PLAYING && game.getTurnPhase() == TurnPhase.CHOOSE_CLOUD_TILE) {
            if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
                for (Colour colour : Colour.values()) {
                    for (int i = 0; i < game.getTable().getCloudTilesByIndex(cloudTile).getTileStudents(colour); i++) {
                        game.getCurrentPlayer().getSchoolBoard().addStudentToEntrance(colour);
                    }
                }

                game.getCurrentPlayer().setHasAlreadyPlayed(true);

                if (!(endPhasePlay())) {
                    game.setCurrentPlayer(game.nextPlayerTurn());
                    game.setTurnPhase(TurnPhase.MOVE_STUDENT);
                } else {
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

    public void setColour(Colour colour) {
        game.getActiveCharacterCard().setColour(colour);
    }

    public void setColourAndIsland(Colour colour, int groupIsland, int singleIsland) {
        game.getActiveCharacterCard().setColourAndIsland(colour, game.getTable().getGroupIslandByIndex(groupIsland).getIslandByIndex(singleIsland));
    }

    public void setGroupIsland(int groupIsland) {
        game.getActiveCharacterCard().setGroupIsland(groupIsland);
    }

    public void setColourDiningRoomEntrance(Colour colourDiningRoom, Colour colourEntrance) {
        game.getActiveCharacterCard().setColourDiningRoomEntrance(colourDiningRoom, colourEntrance);
    }

    public void setColourCardEntrance(Colour colourCard, Colour colourEntrance) {
        game.getActiveCharacterCard().setColourCardEntrance(colourCard, colourEntrance);
    }


    private void calculateWinner() {

        int max = 0;
        List<Player> possibleWinner = new ArrayList<>();

        for (int i = 0; i < numberOfPlayer; i++) {
            if (game.getPlayerByIndex(i).getSchoolBoard().getTowers() > max) {
                max = game.getPlayerByIndex(i).getSchoolBoard().getTowers();
            }
        }

        for (int i = 0; i < numberOfPlayer; i++) {
            if (game.getPlayerByIndex(i).getSchoolBoard().getTowers() == max) {
                possibleWinner.add(game.getPlayerByIndex(i));
            }
        }

        max = 0;

        if (possibleWinner.size() == 1) {
            game.setWinner(possibleWinner.get(0));
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
}
