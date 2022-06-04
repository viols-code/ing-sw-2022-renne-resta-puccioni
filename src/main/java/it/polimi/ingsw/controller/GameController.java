package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.card.*;
import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.model.player.*;
import it.polimi.ingsw.model.table.CloudTile;
import it.polimi.ingsw.model.table.island.AdvancedGroupIsland;
import it.polimi.ingsw.model.table.island.BasicGroupIsland;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.Lobby;
import it.polimi.ingsw.view.messages.PlayerEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * GameController
 */
public class GameController implements Observer<PlayerEvent> {
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
     * The lobby connected to this GameController
     */
    private final Lobby lobby;


    /**
     * Constructor: creates a GameController
     *
     * @param isGameExpert   indicates if the game is in the expert mode
     * @param numberOfPlayer indicates the numberOfPlayer
     */
    public GameController(boolean isGameExpert, int numberOfPlayer, Lobby lobby) {
        this.isGameExpert = isGameExpert;
        this.numberOfPlayer = numberOfPlayer;
        this.lobby = lobby;
        if (isGameExpert) {
            this.game = new ExpertGame();
        } else {
            this.game = new BasicGame();
        }
    }

    /*
    SETTING PHASE
    */

    /**
     * Sets up the game
     * If the game is in expert mode, draws the three CharacterCards
     * If the game is in expert mode and has the ProtectIsland card, creates the AdvanceGroupIsland, otherwise creates the BasicGroupIsland
     */
    public void setUp() {
        game.setUp();

        if (isGameExpert) {
            // Creates a List with all the CharacterCards
            List<Class<? extends CharacterCard>> cardTypes = new ArrayList<>(
                    Arrays.asList(StudentToIsland.class, TakeProfessor.class, IslandInfluence.class,
                            MotherNatureMovement.class, ProtectIsland.class, NoTower.class, StudentToEntrance.class,
                            TwoPoints.class, NoColour.class, ExchangeEntranceDiningRoom.class, StudentToDiningRoom.class,
                            ThreeStudent.class));

            // Shuffle the list
            Collections.shuffle(cardTypes);

            // Choose only the first three CharacterCards
            for (int i = 0; i < 3; i++) {
                try {
                    CharacterCard cardInstance = cardTypes.get(i).getConstructor(Game.class).newInstance(game);
                    game.addCharacterCard(cardInstance);
                } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }


            try {
                // If the game is in expert mode and the ProtectIsland card has been chosen, creates the AdvancedGroupIsland
                if (game.hasProtectIslandCard()) {
                    for (int i = 0; i < 12; i++) {
                        game.getTable().addGroupIsland(new AdvancedGroupIsland());
                    }
                    // If the game is in expert mode without the ProtectIsland card, creates the BasicGroupIsland
                } else {
                    for (int i = 0; i < 12; i++) {
                        game.getTable().addGroupIsland(new BasicGroupIsland());
                    }
                }
            } catch (IllegalAccessError ex) {
                ex.printStackTrace();
            }

        }
        // Sets some integers for the game
        settingInteger();
    }

    /**
     * Set up the Table and the Players
     * Set up the Bag, the CharacterCards and the CloudTiles
     * Set up the Players' entrance, towers and assistantCards
     */
    public void setUpTableAndPlayers() {
        // Add students in the Bag
        settingBag();
        if (isGameExpert) {
            // Setting of the cards with students on them
            settingCard();
        }
        // Setting the cloud tiles
        settingCloudTile();

        // For all the players in the game
        for (int i = 0; i < numberOfPlayer; i++) {
            Player player = game.getPlayerByIndex(i);
            // Sets the correct number of towers
            player.getSchoolBoard().addTower(game.getNumberOfTowersPerPlayer());
            // Sets the students in the entrance
            for (int j = 0; j < game.getNumberStudentsEntrance(); j++) {
                try {
                    player.getSchoolBoard().addStudentToEntrance(game.getTable().getBag().bagDrawStudent());
                } catch (IllegalAccessError ex) {
                    ex.printStackTrace();
                }
            }

            // Add all the assistant cards
            for (int j = 0; j < 10; j++) {
                player.addAssistantCard(game.getAssistantCard(j));
            }
        }

        game.setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
        game.setTurnPhase(TurnPhase.PLAY_ASSISTANT_CARD);
    }

    /**
     * Setting the bag and the students on the SingleIsland
     */
    private void settingBag() {
        // Adds two students of each colour in the Bag
        for (Colour colour : Colour.values()) {
            for (int i = 0; i < 2; i++) {
                game.getTable().getBag().addStudentBag(colour);
            }
        }

        // Starting from the GroupIsland 1, add one student to each SingleIsland
        for (int i = 1; i < 12; i++) {
            // The SingleIsland 6 must be left empty
            if (i == 6) i++;
            try {
                game.getTable().getGroupIslandByIndex(i).getIslandByIndex(0).addStudent(i, 0, game.getTable().getBag().bagDrawStudent());
            } catch (IllegalAccessError ex) {
                ex.printStackTrace();
            }
        }

        // Adds 24 students of each colour in the Bag
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
        // Sets all the CharacterCards
        for (int i = 0; i < 3; i++) {
            try {
                game.getCharacterCardByIndex(i).setting();
            } catch (IllegalAccessError ex) {
                ex.printStackTrace();
            }

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
     * @param nickname      the nickname of the player
     * @param characterCard the index of the CharacterCard
     */
    public synchronized void playCharacterCard(String nickname, int characterCard) {
        int player;
        try {
            player = game.getIndexOfPlayer(game.getPlayerByNickname(nickname));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return;
        }

        if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
            if (isGameExpert && player >= 0 && player < numberOfPlayer && characterCard >= 0 && characterCard < 3) {
                if (game.getGamePhase() == GamePhase.PLAYING) {
                    try {
                        if (!game.getHasPlayedCharacterCard() &&
                                game.getCurrentPlayer().getCoins() >= game.getCharacterCardByIndex(characterCard).getCost()) {
                            // Set the active CharacterCards
                            game.setActiveCharacterCard(game.getCharacterCardByIndex(characterCard));
                            // Set that the current Player has played a CharacterCard
                            game.setHasPlayedCharacterCard(true);
                            // Remove the coins from the current Player
                            game.getCurrentPlayer().removeCoins(game.getCharacterCardByIndex(characterCard).getCost());
                            // Add coins to the table
                            game.setCoins(game.getCoins() + game.getCharacterCardByIndex(characterCard).getCost() - 1);
                            // Increment the costs of the CharacterCard
                            game.getCharacterCardByIndex(characterCard).incrementCost();
                            // Call the method professor, which is valid only if the CharacterCard played is the TakeProfessor Card
                            game.getCharacterCardByIndex(characterCard).professor();
                        } else {
                            game.notifyInvalidAction(nickname, "You cannot play this card");
                        }
                    } catch (IllegalAccessError ex) {
                        ex.printStackTrace();
                    }
                } else {
                    game.notifyInvalidAction(nickname, "It is not the right phase of the game");
                }
            } else {
                game.notifyInvalidAction(nickname, "For the Character Card choose a number between 0 and 2");
            }
        } else {
            game.notifyInvalidAction(nickname, "It is not your turn");
        }
    }

    /*
    ASSISTANT CARD
     */

    /**
     * Playing AssistantCard
     *
     * @param nickname      the nickname of the player
     * @param assistantCard the index of the AssistantCard
     */
    public synchronized void playAssistantCard(String nickname, int assistantCard) {
        int player;
        try {
            player = game.getIndexOfPlayer(game.getPlayerByNickname(nickname));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return;
        }

        if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
            if (player >= 0 && player < numberOfPlayer && assistantCard >= 0 && assistantCard < 10) {
                if (game.getGamePhase() == GamePhase.PLAY_ASSISTANT_CARD) {
                    try {
                        if (game.getPlayerByIndex(player).isAssistantCardPresent(game.getAssistantCard(assistantCard))) {
                            if (canPlayAssistantCard(assistantCard)) {
                                // Sets the current AssistantCard
                                game.getPlayerByIndex(player).setCurrentAssistantCard(game.getAssistantCard(assistantCard));
                                // Remove the AssistantCard chosen from the Set of the current Player
                                game.getPlayerByIndex(player).removeAssistantCard(game.getAssistantCard(assistantCard));
                                // Set hasAlreadyPlayer to true for the current Player
                                game.getPlayerByIndex(player).setHasAlreadyPlayed(true);

                                // If not all the Players have already played an AssistantCard
                                if (!endPhasePlay()) {
                                    // Set the current Player to the nextPlayerClockwise
                                    game.setCurrentPlayer(game.nextPlayerClockwise());
                                    // Set the TurnPhase to PLAY_ASSISTANT_CARD
                                    game.setTurnPhase(TurnPhase.PLAY_ASSISTANT_CARD);
                                    // If all the Players have played an AssistantCard
                                } else {
                                    // End the PLAY_ASSISTANT_CARD phase
                                    endPlayAssistantCard();
                                }
                            } else {
                                game.notifyInvalidAction(nickname, "For this turn you cannot play this assistant card, choose another one");
                            }
                        } else {
                            game.notifyInvalidAction(nickname, "You have already played this assistant card");
                        }
                    } catch (IllegalArgumentException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    game.notifyInvalidAction(nickname, "It is not the right phase to play an assistant card");
                }
            } else {
                game.notifyInvalidAction(nickname, "For the Assistant Card choose a number between 0 and 9");
            }
        } else {
            game.notifyInvalidAction(nickname, "It is not your turn");
        }
    }

    /**
     * Check if the player can play an AssistantCard
     *
     * @param assistantCard the index of the AssistantCard
     * @return true if the Player can play the AssistantCard, false otherwise
     */
    private boolean canPlayAssistantCard(int assistantCard) {
        // First check if the AssistantCard played is different from all the AssistantCard already played in that round
        boolean check = true;
        for (int i = 0; i < game.getNumberOfPlayer(); i++) {
            if (game.getPlayerByIndex(i).getHasAlreadyPlayed() && !game.getCurrentPlayer().equals(game.getPlayerByIndex(i))) {
                try {
                    if (game.getPlayerByIndex(i).getCurrentAssistantCard().equals(game.getAssistantCard(assistantCard))) {
                        check = false;
                    }
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                }

            }
        }
        // If the AssistantCard played is different from all the AssistantCard already played in that round, returns true
        if (check) {
            return true;
        }


        // If the AssistantCard played is equal to another AssistantCard played in that turn
        // Checks if the current Player has another AssistantCard in their set that is different from the AssistantCard
        // already played, if not so, then the Player is allowed to play this AssistantCard
        for (int j = 0; j < 10; j++) {
            if (game.getCurrentPlayer().isAssistantCardPresent(game.getAssistantCard(j))) {
                check = true;
                for (int i = 0; i < game.getNumberOfPlayer(); i++) {
                    if (!game.getPlayerByIndex(i).equals(game.getCurrentPlayer())) {
                        if (game.getPlayerByIndex(i).getHasAlreadyPlayed()) {
                            if (game.getPlayerByIndex(i).getCurrentAssistantCard().equals(game.getAssistantCard(j))) {
                                check = false;
                            }
                        }
                    }
                }

                if (check) {
                    return false;
                }
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
     * @param nickname     the nickname of the player
     * @param colour       the colour of the student to be moved
     * @param groupIsland  the index of the GroupIsland
     * @param singleIsland the index of the SingleIsland
     */
    public synchronized void moveStudentToIsland(String nickname, Colour colour, int groupIsland, int singleIsland) {
        int player;
        try {
            player = game.getIndexOfPlayer(game.getPlayerByNickname(nickname));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return;
        }

        if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
            if (player >= 0 && player < numberOfPlayer && groupIsland >= 0 && groupIsland < game.getTable().getNumberOfGroupIsland() && singleIsland >= 0 && singleIsland < game.getTable().getGroupIslandByIndex(groupIsland).getNumberOfSingleIsland()) {
                if (game.getGamePhase() == GamePhase.PLAYING && game.getTurnPhase() == TurnPhase.MOVE_STUDENT) {
                    if (game.getPlayerByIndex(player).getSchoolBoard().getEntrance(colour) > 0) {
                        if (checkStudentsMovement(player)) {
                            // Add the student of the given colour to the SingleIsland
                            game.getTable().getGroupIslandByIndex(groupIsland).getIslandByIndex(singleIsland).addStudent(groupIsland, singleIsland, colour);
                            // Remove the student of the given colour from the SingleIsland
                            game.getPlayerByIndex(player).getSchoolBoard().removeStudentFromEntrance(colour);
                            // If the Player has already moved all their students from the entrance, then change the TurnPhase to the MOVE_MOTHER_NATURE phase
                            if (checkEndMovementPhase(player)) {
                                game.setTurnPhase(TurnPhase.MOVE_MOTHER_NATURE);
                            }
                        } else {
                            game.notifyInvalidAction(nickname, "You have already moved all your students");
                        }
                    } else {
                        game.notifyInvalidAction(nickname, "You have not enough student of the given colour");
                    }
                } else {
                    game.notifyInvalidAction(nickname, "This is not the right phase of the game");
                }
            } else {
                game.notifyInvalidAction(nickname, "You have chosen an island out of range");
            }
        } else {
            game.notifyInvalidAction(nickname, "This is not your turn");
        }
    }

    /**
     * Move student from entrance to a diningRoom
     *
     * @param nickname the nickname of the player
     * @param colour   the colour of the student to be moved
     */
    public synchronized void moveStudentToDiningRoom(String nickname, Colour colour) {
        int player;
        try {
            player = game.getIndexOfPlayer(game.getPlayerByNickname(nickname));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return;
        }

        if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
            if (game.getGamePhase() == GamePhase.PLAYING && game.getTurnPhase() == TurnPhase.MOVE_STUDENT) {
                if (player >= 0 && player < numberOfPlayer) {
                    if (game.getPlayerByIndex(player).getSchoolBoard().getEntrance(colour) > 0) {
                        if (checkStudentsMovement(player) && game.getPlayerByIndex(player).getSchoolBoard().getDiningRoom(colour) < 10) {
                            try {
                                // Remove student of the given colour from the entrance
                                game.getPlayerByIndex(player).getSchoolBoard().removeStudentFromEntrance(colour);
                                // Add the student of the given colour in the dining room
                                game.getPlayerByIndex(player).getSchoolBoard().addStudentToDiningRoom(colour);

                                // If the game is expert, check if the Player earned a coin
                                if (isGameExpert && ((game.getPlayerByIndex(player).getSchoolBoard().getDiningRoom(colour)) % 3) == 0 && game.getCoins() > 0) {
                                    game.getPlayerByIndex(player).addCoins(1);
                                    game.setCoins(game.getCoins() - 1);
                                }
                            } catch (IllegalAccessError ex) {
                                ex.printStackTrace();
                            }
                            // If the Player doesn't already have the professor of the given colour, check if they obtained it
                            if (!game.getCurrentPlayer().getSchoolBoard().hasProfessor(colour)) {
                                game.getActiveCharacterCard().checkProfessor(colour);
                                if (game.getCurrentPlayer().getSchoolBoard().hasProfessor(colour)) {
                                    // Remove the professor from the Table
                                    game.getTable().setNoProfessorOnTable(colour);
                                }
                            }
                            // If the Player has already moved all their students from the entrance, then change the TurnPhase to the MOVE_MOTHER_NATURE phase
                            if (checkEndMovementPhase(player)) {
                                game.setTurnPhase(TurnPhase.MOVE_MOTHER_NATURE);
                            }
                        } else {
                            game.notifyInvalidAction(nickname, "You have moved too many students");
                        }
                    } else {
                        game.notifyInvalidAction(nickname, "You have not enough student of the given colour");
                    }
                }
            } else {
                game.notifyInvalidAction(nickname, "It is not the right phase of the game");
            }
        } else {
            game.notifyInvalidAction(nickname, "You are not the current player, wait your turn");
        }
    }

    /**
     * Check for the movement of students
     *
     * @param player the index of the Player
     * @return true if the player can move another student, false otherwise
     */
    private boolean checkStudentsMovement(int player) {
        return game.getPlayerByIndex(player).getSchoolBoard().getNumberStudentsEntrance() >= game.getNumberStudentsEntrance() - game.getStudentNumberMovement();
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
     * @param nickname the nickname of the Player
     * @param movement the number of moves
     */
    public synchronized void moveMotherNature(String nickname, int movement) {
        int player;
        try {
            player = game.getIndexOfPlayer(game.getPlayerByNickname(nickname));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return;
        }

        if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
            if (game.getGamePhase() == GamePhase.PLAYING && game.getTurnPhase() == TurnPhase.MOVE_MOTHER_NATURE) {
                if (movement > 0) {
                    if (game.getActiveCharacterCard().checkMotherNatureMovement(player, movement)) {
                        // Set mother nature position
                        game.getTable().setMotherNaturePosition((game.getTable().getMotherNaturePosition() + movement) % game.getTable().getNumberOfGroupIsland());
                        // Calculate the influence using the active CharacterCard
                        game.getActiveCharacterCard().calculateInfluence(game.getTable().getMotherNaturePosition());
                        // If the Winner has been set, end the Game
                        if (game.getWinner() != null) endGame();
                            // If the number of the GroupIsland is less than three, end the game
                        else if (game.getTable().getNumberOfGroupIsland() <= 3) {
                            calculateWinner();
                            endGame();
                        } else {
                            // If there are no more students in the Bag, end the Turn
                            if (game.getTable().getBag().getNoStudent()) {
                                endTurn();
                            }
                            // Set the TurnPhase to the CHOOSE_CLOUD_TILE_PHASE
                            else {
                                game.setTurnPhase(TurnPhase.CHOOSE_CLOUD_TILE);
                            }
                        }
                    } else {
                        game.notifyInvalidAction(nickname, "The number of movement is too high");
                    }
                } else {
                    game.notifyInvalidAction(nickname, "The number of movement must be positive");
                }
            } else {
                game.notifyInvalidAction(nickname, "It is not the right game phase");
            }
        } else {
            game.notifyInvalidAction(nickname, "It is not your turn");
        }
    }

    /**
     * Sets the end of the Game
     */
    private void endGame() {
        game.setGamePhase(GamePhase.END_GAME);
        game.setTurnPhase(TurnPhase.ENDGAME);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                lobby.terminate();
            }
        }, 100_000L); //Wait for 100 seconds before closing all connections to give time to all clients to terminate properly
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
        nobodyPlayed();
        game.setFirstPlayerLastTurn(game.getFirstPlayerTurn());
        game.setFirstPlayerTurn(game.nextPlayerTurn());
        game.setCurrentPlayer(game.nextPlayerTurn());
        game.setGamePhase(GamePhase.PLAYING);
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
    public synchronized void addPlayer(String nickname, Wizard wizard) {
        if (game.getNumberOfPlayer() < numberOfPlayer && game.getGamePhase() == GamePhase.SETTING) {
            if (checkUniqueNickname(nickname)) {
                if (checkUniqueWizard(wizard)) {
                    if (isGameExpert) {
                        // If the Game is in expert mode, add an ExpertPlayer
                        game.addPlayer(new ExpertPlayer(nickname, wizard, TowerColour.valueOf(game.getNumberOfPlayer())));
                        try {
                            game.setCoins(game.getCoins() - 1);
                        } catch (IllegalAccessError ex) {
                            ex.printStackTrace();
                        }

                    } else {
                        // If the Game is in basic mode, add a BasicPlayer
                        game.addPlayer(new BasicPlayer(nickname, wizard, TowerColour.valueOf(game.getNumberOfPlayer())));
                    }
                } else {
                    game.notifyInvalidAction(nickname, "Your wizard is not unique");
                }
            } else {
                game.notifyInvalidAction(nickname, "Your nickname is not unique");
            }

            // If the numberOfPlayer is equals to the Players in the Game, start the Game
            if (numberOfPlayer == game.getNumberOfPlayer()) {
                game.setCurrentPlayer(game.getPlayerByIndex(0));
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
        // For all colours, put 0 students in the CloudTile
        HashMap<Colour, Integer> students = new HashMap<>();
        for (Colour colour : Colour.values()) {
            students.put(colour, 0);
        }

        // Add the students to the CloudTile from the Bag
        for (int i = 0; i < game.getStudentNumberMovement(); i++) {
            try {
                Colour colour1 = game.getTable().getBag().bagDrawStudent();
                students.replace(colour1, students.get(colour1), students.get(colour1) + 1);
            } catch (IllegalAccessError ex) {
                ex.printStackTrace();
            }
        }

        game.getTable().addCLoudTile(new CloudTile(students));
    }

    /**
     * Choose a cloudTile
     *
     * @param nickname  nickname of the player
     * @param cloudTile index of the cloudTile
     */
    public synchronized void chooseCloudTile(String nickname, int cloudTile) {
        int player;
        try {
            player = game.getIndexOfPlayer(game.getPlayerByNickname(nickname));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return;
        }

        if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
            if (game.getGamePhase() == GamePhase.PLAYING && game.getTurnPhase() == TurnPhase.CHOOSE_CLOUD_TILE) {
                if (cloudTile >= 0 && cloudTile < game.getTable().getNumberOfCloudTile()) {
                    // For all the colours in the CloudTile, add the student of the given colour
                    for (Colour colour : Colour.values()) {
                        for (int i = 0; i < game.getTable().getCloudTilesByIndex(cloudTile).getTileStudents(colour); i++) {
                            game.getCurrentPlayer().getSchoolBoard().addStudentToEntrance(colour);
                        }
                    }
                    game.getTable().removeCLoudTile(game.getTable().getCloudTilesByIndex(cloudTile));
                    // End the Turn
                    endTurn();
                } else {
                    game.notifyInvalidAction(nickname, "The number of cloud tile given is out of range");
                }
            } else {
                game.notifyInvalidAction(nickname, "It is not the right game phase");
            }
        } else {
            game.notifyInvalidAction(nickname, "It is not your turn");
        }
    }

    /**
     * End of the turn of the current Player
     */
    private void endTurn() {
        game.getCurrentPlayer().setHasAlreadyPlayed(true);

        if (isGameExpert) {
            try {
                // Set hasPlayedCharacterCard to false
                game.setHasPlayedCharacterCard(false);
                // Set the state to the basic one
                if (!game.getActiveCharacterCard().equals(game.getBasicState())) {
                    try {
                        game.getActiveCharacterCard().setColour(null);
                        game.getActiveCharacterCard().setColourDiningRoomEntrance(null, null);
                        game.getActiveCharacterCard().setColourAndIsland(null, null);
                        game.getActiveCharacterCard().setColourCardEntrance(null, null);
                        game.getActiveCharacterCard().setGroupIsland(-1);
                    } catch (IllegalArgumentException e) {
                        // Ignored
                    }
                    game.setActiveCharacterCard(game.getBasicState());
                }
            } catch (IllegalAccessError ex) {
                ex.printStackTrace();
            }
        }

        if (!(endPhasePlay())) {
            // If the PLAYING phase is not ended, sets the TurnPhase and the current Player
            game.setCurrentPlayer(game.nextPlayerTurn());
            game.setTurnPhase(TurnPhase.MOVE_STUDENT);
        } else {
            if (!game.getTable().getBag().getNoStudent()) {
                // If the PLAYING phase is ended, sets the CloudTile, increments the Round and sets the next current Player
                settingCloudTile();
                game.incrementRound();
                if (game.getRound() >= 11) {
                    calculateWinner();
                    endGame();
                } else {
                    game.setCurrentPlayer(game.getFirstPlayerTurn());
                    game.setGamePhase(GamePhase.PLAY_ASSISTANT_CARD);
                    game.setTurnPhase(TurnPhase.PLAY_ASSISTANT_CARD);
                    for (int i = 0; i < game.getNumberOfPlayer(); i++) {
                        game.getPlayerByIndex(i).setNullAssistantCard();
                    }
                }
                nobodyPlayed();

            } else {
                calculateWinner();
                endGame();
            }

        }
    }

    /**
     * Sets the colour of the student
     *
     * @param nickname the nickname of the player
     * @param colour   the colour
     */
    public synchronized void setColour(String nickname, Colour colour) {
        int player;
        try {
            player = game.getIndexOfPlayer(game.getPlayerByNickname(nickname));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return;
        }

        if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
            try {
                game.getActiveCharacterCard().setColour(colour);
            } catch (IllegalAccessError | IllegalArgumentException ex) {
                game.notifyInvalidAction(nickname, ex.getMessage());
            }
        } else {
            game.notifyInvalidAction(nickname, "You are not the current player, wait your turn");
        }
    }

    /**
     * Sets the parameters needed for the character card StudentToIsland
     *
     * @param nickname     the nickname of the player
     * @param colour       colour of the student on the card
     * @param groupIsland  the group island chosen
     * @param singleIsland the single island chosen
     */
    public synchronized void setColourAndIsland(String nickname, Colour colour, int groupIsland, int singleIsland) {
        int player;
        try {
            player = game.getIndexOfPlayer(game.getPlayerByNickname(nickname));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return;
        }

        if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
            try {
                if (groupIsland >= 0 && groupIsland < game.getTable().getNumberOfGroupIsland() && singleIsland >= 0 && singleIsland < game.getTable().getGroupIslandByIndex(groupIsland).getNumberOfSingleIsland())
                    game.getActiveCharacterCard().setColourAndIsland(colour, game.getTable().getGroupIslandByIndex(groupIsland).getIslandByIndex(singleIsland));
            } catch (IllegalAccessError | IllegalArgumentException ex) {
                game.notifyInvalidAction(nickname, ex.getMessage());
            }
        } else {
            game.notifyInvalidAction(nickname, "You are not the current player, wait your turn");
        }

    }

    /**
     * Sets the group island in the character card IslandInfluence
     *
     * @param nickname    the nickname of the player
     * @param groupIsland the group island
     */
    public synchronized void setGroupIsland(String nickname, int groupIsland) {
        int player;
        try {
            player = game.getIndexOfPlayer(game.getPlayerByNickname(nickname));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return;
        }

        if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
            try {
                if (groupIsland >= 0 && groupIsland < game.getTable().getNumberOfGroupIsland())
                    game.getActiveCharacterCard().setGroupIsland(groupIsland);
            } catch (IllegalAccessError | IllegalArgumentException ex) {
                game.notifyInvalidAction(nickname, ex.getMessage());
            }
        } else {
            game.notifyInvalidAction(nickname, "You are not the current player, wait your turn");
        }
    }

    /**
     * Sets the parameters for the character card ExchangeDiningRoomEntrance
     *
     * @param nickname         the nickname of the player
     * @param colourDiningRoom the student of the dining room
     * @param colourEntrance   the student of the entrance
     */
    public synchronized void setColourDiningRoomEntrance(String nickname, Colour colourDiningRoom, Colour colourEntrance) {
        int player;
        try {
            player = game.getIndexOfPlayer(game.getPlayerByNickname(nickname));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return;
        }

        if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
            try {
                game.getActiveCharacterCard().setColourDiningRoomEntrance(colourDiningRoom, colourEntrance);
            } catch (IllegalAccessError | IllegalArgumentException ex) {
                game.notifyInvalidAction(nickname, ex.getMessage());
            }
        } else {
            game.notifyInvalidAction(nickname, "You are not the current player, wait your turn");
        }
    }

    /**
     * Sets the parameter for the character card StudentToEntrance
     *
     * @param nickname       the nickname of the player
     * @param colourCard     the student of the card
     * @param colourEntrance the student of the entrance
     */
    public synchronized void setColourCardEntrance(String nickname, Colour colourCard, Colour colourEntrance) {
        int player;
        try {
            player = game.getIndexOfPlayer(game.getPlayerByNickname(nickname));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return;
        }

        if (game.isCurrentPlayer(game.getPlayerByIndex(player))) {
            try {
                game.getActiveCharacterCard().setColourCardEntrance(colourCard, colourEntrance);
            } catch (IllegalAccessError | IllegalArgumentException ex) {
                game.notifyInvalidAction(nickname, ex.getMessage());
            }
        } else {
            game.notifyInvalidAction(nickname, "You are not the current player, wait your turn");
        }
    }


    /**
     * Calculate the winner of the Game
     */
    private void calculateWinner() {
        int min = 8;
        List<Player> possibleWinner = new ArrayList<>();

        // Gets the Players with the minimum number of towers
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

        // If only one Player is left, they are the winner
        if (possibleWinner.size() == 1) {
            game.setWinner(possibleWinner.get(0));
            return;
        }
        // Get the Players with the max number of Professors
        else {
            for (Player player : possibleWinner) {
                if (player.getSchoolBoard().getNumberOfProfessors() > max) {
                    max = player.getSchoolBoard().getNumberOfProfessors();
                }
            }
        }

        // If more than one Player is left, then the winner is the first Player in the possibleWinner list that connected
        for (Player player : possibleWinner) {
            if (player.getSchoolBoard().getNumberOfProfessors() == max) {
                game.setWinner(player);
                return;
            }
        }
    }

    /**
     * Update an event
     *
     * @param event the event
     */
    @Override
    public synchronized void update(PlayerEvent event) {
        try {
            event.process(this);
        } catch (IllegalStateException ignored) {
            //Ignored exception for player action that is not during his turn
        }

    }
}
