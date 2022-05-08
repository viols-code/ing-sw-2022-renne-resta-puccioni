package it.polimi.ingsw.view;

import it.polimi.ingsw.client.messages.PlayerWizardMessage;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.implementation.cli.utils.ViewString;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.messages.PlayerNameMessage;
import it.polimi.ingsw.view.beans.MockModel;
import it.polimi.ingsw.view.beans.MockPlayer;

import java.util.List;
import java.util.Map;

/**
 * Class responsible for orchestrating all the functionalities of the game interface.
 */
public abstract class View {
    private final Client client;
    private ModelUpdateHandler modelUpdateHandler;
    private Renderer renderer;
    private ActionSender actionSender;
    private MockModel model;
    private GameState gameState;

    private String playerName;
    private Wizard wizard;
    private boolean gameMode;
    private boolean lobbyMaster;

    /**
     * Constructs a new View.
     *
     * @param client the client responsible for this view
     */
    public View(Client client) {
        this.client = client;
        this.model = new MockModel();
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

    public ModelUpdateHandler getModelUpdateHandler() {
        return modelUpdateHandler;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public ActionSender getActionSender() {
        return actionSender;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Wizard getPlayerWizard() {
        return wizard;
    }

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

    public GameState getGameState() {
        return gameState;
    }

    public MockModel getModel() {
        return model;
    }

    public void setModelUpdateHandler(ModelUpdateHandler modelUpdateHandler) {
        this.modelUpdateHandler = modelUpdateHandler;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public void setActionSender(ActionSender actionSender) {
        this.actionSender = actionSender;
    }

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
    public void setPlayerName(Wizard wizard) {
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
     */
    public void addToLobby(boolean isFirstConnection) {
        setGameState(GameState.CHOOSING_NAME);
        if (isFirstConnection)
            lobbyMaster = true;
    }

    /**
     * Handles the connection of another player to the lobby.
     *
     * @param playerName            the name of the player that connected
     * @param currentPlayers        the amount of players connected to the lobby
     * @param playersToStart        the number of players required to start the game
     * @param otherConnectedPlayers the list of the other connected player names
     */
    public void handlePlayerConnect(String playerName, Wizard wizard, boolean gameMode, int currentPlayers, int playersToStart, List<String> otherConnectedPlayers) {
        if (playerName.equals(getPlayerName()) || wizard.equals(getPlayerWizard())) {
            MockPlayer localPlayer = getModel().addPlayer(getPlayerName(), getPlayerWizard(), gameMode, true);
            getModel().setLocalPlayer(localPlayer);
            if (isLobbyMaster()) {
                setGameState(GameState.CHOOSING_PLAYERS);
            } else
                setGameState(GameState.WAITING_PLAYERS);

            if (!otherConnectedPlayers.isEmpty()) {
                otherConnectedPlayers.forEach(player -> getModel().addPlayer(playerName, wizard, gameMode, false));
            }
        } else {
            MockPlayer localPlayer= getModel().addPlayer(playerName, wizard, gameMode, false);
        }
    }

    public void handleSetPlayersToStart(int playersToStart) {
        setGameState(GameState.CHOOSING_GAME_MODE);
    }

    public void handleSetGameMode() {
        setGameState(GameState.WAITING_PLAYERS);
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
        getRenderer().showLobbyMessage(ViewString.GAME_STARTING);
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
     *
     * @param scores     a map associating usernames with their scores
     * @param winnerName the username of the winner
     */
    public abstract void handleEndGame(Map<String, Integer> scores, String winnerName);

    /**
     * Resets the View to the pre game state.
     */
    protected void reset() {
        this.gameState = GameState.CONNECTING;
        this.model = new MockModel();

        getClient().reset();
    }
}
