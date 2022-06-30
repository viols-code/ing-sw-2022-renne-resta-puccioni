package it.polimi.ingsw.view;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.messages.PlayerNameMessage;
import it.polimi.ingsw.client.messages.PlayerWizardMessage;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.beans.MockModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.List;

/**
 * Class responsible for orchestrating all the functionalities of the game interface.
 */
public abstract class View {
    private final Client client;
    private ModelUpdateHandler modelUpdateHandler;
    private Renderer renderer;
    private ActionSender actionSender;
    private final MockModel model;
    private GameState gameState;

    private String playerName;
    protected Wizard wizard;
    private boolean gameMode;
    private int numPlayers;
    private boolean lobbyMaster;
    private final ObservableList<Wizard> takenWizards;
    private final ObservableList<String> otherNicks;

    /**
     * Constructs a new View.
     *
     * @param client the client responsible for this view
     */
    public View(Client client) {
        this.client = client;
        this.model = new MockModel();
        this.gameState = GameState.CONNECTING;
        this.wizard = null;
        this.takenWizards = FXCollections.observableArrayList();
        this.otherNicks = FXCollections.observableArrayList();
    }

    /**
     * Entry method to initialize the View.
     */
    public abstract void run();

    /**
     * Gets the client.
     *
     * @return the client associated with the view
     */
    public Client getClient() {
        return client;
    }

    /**
     * Gets the model update handler
     *
     * @return the model update handler associated with the view
     */
    public ModelUpdateHandler getModelUpdateHandler() {
        return modelUpdateHandler;
    }

    /**
     * Gets the renderer.
     *
     * @return the renderer associated with the view
     */
    public Renderer getRenderer() {
        return renderer;
    }

    /**
     * Gets the action sender.
     *
     * @return the action sender associated with the view
     */
    public ActionSender getActionSender() {
        return actionSender;
    }

    /**
     * Gets the nickname of the local player of the view.
     *
     * @return the player nickname
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Gets the wizard of the local player of the view.
     *
     * @return the player wizard
     */
    public Wizard getPlayerWizard() {
        return wizard;
    }

    /**
     * Gets the game mode selected for this game
     *
     * @return the game mode
     */
    public boolean getGameMode() {
        return gameMode;
    }

    /**
     * Checks if the local player is the first one that joined the lobby.
     *
     * @return true if the player is the first one that joined the lobby, false otherwise
     */
    public boolean isLobbyMaster() {
        return lobbyMaster;
    }

    /**
     * Gets the game state
     *
     * @return the game state
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Gets the mock model
     *
     * @return the mock model
     */
    public synchronized MockModel getModel() {
        return model;
    }

    /**
     * Sets the model update handler
     *
     * @param modelUpdateHandler the model update handler
     */
    public void setModelUpdateHandler(ModelUpdateHandler modelUpdateHandler) {
        this.modelUpdateHandler = modelUpdateHandler;
    }

    /**
     * Sets the renderer
     *
     * @param renderer the renderer
     */
    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    /**
     * Sets the action sender
     *
     * @param actionSender the action sender
     */
    public void setActionSender(ActionSender actionSender) {
        this.actionSender = actionSender;
    }

    /**
     * Sets the game state
     *
     * @param gameState the game state
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Sets the local player username.
     *
     * @param playerName the local player username
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        getClient().send(new PlayerNameMessage(playerName));
    }

    /**
     * Sets the local player wizard.
     *
     * @param wizard the local player wizard
     */
    public void setWizard(Wizard wizard) {
        this.wizard = wizard;
        getClient().send(new PlayerWizardMessage(wizard));
    }


    /*
     Handle ServerMessages
     */

    /**
     * Handles the successful connection to a server lobby.
     *
     * @param isFirstConnection true if this client is the first to connect to the lobby, false otherwise
     * @param takenWizard       the list of wizard already taken
     */
    public void addToLobby(boolean isFirstConnection, List<Wizard> takenWizard) {
        this.lobbyMaster = isFirstConnection;

        if (takenWizard != null) {
            this.takenWizards.clear();
            this.takenWizards.addAll(takenWizard);
        }
    }


    /**
     * Handles the successful connection to the waiting room.
     */
    public void correctConnection() {
        setGameState(GameState.CHOOSING_NAME);
    }

    /**
     * Handles the correct reconnection of a player
     *
     * @param players    a hash map that associates each nickname with the wizard chosen by the player
     */
    public void correctReconnection(HashMap<String, Wizard> players) {
        getModel().setReconnected(true);
        setGameState(GameState.PLAYING);
        numPlayers = players.size();
        players.forEach((key, value) -> getModel().addPlayer(key, value, gameMode, key.equalsIgnoreCase(this.playerName)));
        for (int i = 0; i < numPlayers; i++) {
            getModel().getTable().addCloudTile();
        }
    }

    /**
     * Notify that a player reconnected to the game
     *
     * @param playerName the nickname of the player reconnected
     */
    public void handlePlayerReconnect(String playerName){

    }

    /**
     * Handles the successful nickname setting
     *
     * @param nickname       the nickname chosen by the local player
     * @param takenNicknames the nicknames chosen by the players connected to the lobby
     */
    public void handleCorrectNickname(String nickname, List<String> takenNicknames) {
        setGameState(GameState.CHOOSING_WIZARD);

        if (takenNicknames != null) {
            for (String player : takenNicknames) {
                if (!player.equals(getPlayerName()) && !getModel().getNicknames().contains(player)) {
                    getModel().addPlayerNickname(player);
                }
            }
        }
    }

    /**
     * Handles the successful wizard setting
     *
     * @param wizard the wizard chosen by the local player
     */
    public void handleCorrectWizard(Wizard wizard) {
        if (isLobbyMaster()) {
            setGameState(GameState.CHOOSING_GAME_MODE);
        } else {
            setGameState(GameState.WAITING_PLAYERS);
        }
    }

    /**
     * Handles the successful setting ot the game mode (basic or expert)
     *
     * @param gameMode a boolean which is true if the game mode set is expert, false if it's basic
     */
    public void handleGameMode(boolean gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Handles the successful connection of all players and prepares the mock model for the game
     *
     * @param players    a hash map that associates each nickname with the wizard chosen by the player
     * @param gameMode   a boolean which is true if the game mode set is expert, false if it's basic
     * @param numPlayers the number of players in the game
     */
    public void handleAllPlayersConnected(HashMap<String, Wizard> players, boolean gameMode, int numPlayers) {
        this.gameMode = gameMode;
        this.numPlayers = numPlayers;
        players.forEach((key, value) -> getModel().addPlayer(key, value, gameMode, key.equalsIgnoreCase(this.playerName)));
        for (int i = 0; i < numPlayers; i++) {
            getModel().getTable().addCloudTile();
        }
    }

    /**
     * Handles the connection of another player to the lobby.
     *
     * @param playerName     the name of the player that connected
     * @param wizard         the name of the player that connected
     * @param currentPlayers the amount of players connected to the lobby
     * @param playersToStart the number of players required to start the game
     * @param takenWizard    the list of wizard already taken
     */
    public void handlePlayerConnect(String playerName, Wizard wizard, int currentPlayers, Integer playersToStart, List<Wizard> takenWizard) {
        this.takenWizards.clear();
        if (takenWizard != null) {
            this.takenWizards.addAll(takenWizard);
        }
    }

    /**
     * Handles the successful setting of the number of players
     *
     * @param playersToStart the number of players set to start the game
     */
    public void handleSetPlayersToStart(int playersToStart) {
        setGameState(GameState.WAITING_PLAYERS);
    }

    /**
     * Handles the successful game mode setting
     */
    public void handleSetGameMode() {
        setGameState(GameState.CHOOSING_PLAYERS);
    }

    /**
     * Handles the disconnection of one of the other players from the lobby.
     *
     * @param playerName the name of the player that disconnected
     */
    public abstract void handlePlayerDisconnect(String playerName);

    /**
     * Handles the game start.
     *
     * @param gameMode the game config used for this game
     */
    public void handleGameStart(boolean gameMode) {
        getModel().setGameMode(gameMode);

        setGameState(GameState.STARTING);
    }

    /**
     * Handles the game can start message
     */
    public void handleGameCanStartMessage() {
        setGameState(GameState.PLAYING);
    }

    /**
     * Handles the disconnection of one of the other players, terminating the game.
     *
     * @param playerName the name of the player that disconnected
     */
    public abstract void handlePlayerCrash(String playerName);

    /**
     * Handles an invalid action done by this client.
     *
     * @param errorMessage error message given by the server
     */
    public void handleInvalidAction(String errorMessage) {
        getRenderer().showErrorMessage(errorMessage);
    }

    /**
     * Handles the ending of the game.
     */
    public abstract void handleEndGame();

    /**
     * Get the ObservableList of taken wizard
     *
     * @return the ObservableList of taken wizard
     */
    public ObservableList<Wizard> getTakenWizardsProperty() {
        return takenWizards;
    }

    /**
     * Get the List of taken wizard
     *
     * @return the List of taken wizard
     */
    public List<Wizard> getTakenWizards() {
        return takenWizards;
    }

    /**
     * Get the number of players in the game
     *
     * @return the number of players in the game
     */
    public int getNumPlayers() {
        return numPlayers;
    }
}
