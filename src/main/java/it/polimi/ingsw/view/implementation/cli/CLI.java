package it.polimi.ingsw.view.implementation.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.implementation.cli.utils.ASCIIArt;
import it.polimi.ingsw.view.implementation.cli.utils.AnsiColour;
import it.polimi.ingsw.view.implementation.cli.utils.ViewString;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Command line interface main class
 */
public class CLI extends View {
    private final CommandHandler commandHandler;

    /**
     * Creates a new CLI for the given client
     *
     * @param client the client that requests a command line interface
     */
    public CLI(Client client) {
        super(client);
        this.setModelUpdateHandler(new CLIModelUpdateHandler(this));
        this.setRenderer(new CLIRenderer(this));
        this.setActionSender(new CLIActionSender(this));
        this.commandHandler = new CommandHandler(this);
    }

    /**
     * Handles the successful connection to a server lobby.
     *
     * @param isFirstConnection true if this client is the first to connect to the lobby, false otherwise
     * @param takenWizard       the list of wizard already taken
     */
    @Override
    public void addToLobby(boolean isFirstConnection, List<Wizard> takenWizard) {
        super.addToLobby(isFirstConnection, takenWizard);
        getRenderer().showLobbyMessage(ViewString.CHOOSE_NAME);
    }

    /**
     * Handles the successful nickname setting
     *
     * @param nickname       the nickname chosen by the local player
     * @param takenNicknames the nicknames chosen by the players connected to the lobby
     */
    @Override
    public void handleCorrectNickname(String nickname, List<String> takenNicknames) {
        super.handleCorrectNickname(nickname, takenNicknames);
        getRenderer().showLobbyMessage(ViewString.CHOOSE_WIZARD);
    }

    /**
     * Handles the successful wizard setting
     *
     * @param wizard the wizard chosen by the local player
     */
    @Override
    public void handleCorrectWizard(Wizard wizard) {
        super.handleCorrectWizard(wizard);
        switch (wizard) {
            case TYPE_1 -> System.out.println(AnsiColour.GOLD + ASCIIArt.WIZARD_ONE + AnsiColour.RESET);
            case TYPE_2 -> System.out.println(AnsiColour.GOLD + ASCIIArt.WIZARD_TWO + AnsiColour.RESET);
            case TYPE_3 -> System.out.println(AnsiColour.GOLD + ASCIIArt.WIZARD_THREE + AnsiColour.RESET);
            case TYPE_4 -> System.out.println(AnsiColour.GOLD + ASCIIArt.WIZARD_FOUR + AnsiColour.RESET);
        }
        if (isLobbyMaster()) {
            getRenderer().showLobbyMessage(ViewString.CHOOSE_GAME_MODE);
        } else {
            getRenderer().showLobbyMessage(ViewString.WAITING_PLAYERS);
        }
    }

    /**
     * Handles the connection of another player to the lobby.
     *
     * @param playerName     the name of the player that connected
     * @param wizard         the wizard that the player has chosen
     * @param currentPlayers the amount of players connected to the lobby
     * @param playersToStart the number of players required to start the game
     * @param otherWizard    the list of wizard already taken
     */
    @Override
    public void handlePlayerConnect(String playerName, Wizard wizard, int currentPlayers, Integer playersToStart, List<Wizard> otherWizard) {
        super.handlePlayerConnect(playerName, wizard, currentPlayers, playersToStart, otherWizard);
        if (playersToStart == null) {
            getRenderer().showLobbyMessage(ViewString.PLAYER_CONNECTED_WITH_COUNT.formatted(playerName, currentPlayers));
        } else {
            getRenderer().showLobbyMessage(ViewString.PLAYER_CONNECTED_WITH_COUNT_ON_TOTAL.formatted(playerName, currentPlayers, playersToStart));
        }
    }

    /**
     * Handles the game start.
     *
     * @param gameMode the game config used for this game
     */
    @Override
    public void handleGameStart(boolean gameMode) {
        super.handleGameStart(gameMode);
        getRenderer().showLobbyMessage(ViewString.GAME_STARTING);
    }

    /**
     * Handles the successful setting of the game mode (basic or expert)
     *
     * @param gameMode a boolean which is true if the game mode set is expert, false if it's basic
     */
    @Override
    public void handleGameMode(boolean gameMode) {
        if (!isLobbyMaster()) {
            if (gameMode) {
                getRenderer().showLobbyMessage(ViewString.GAME_MODE_MESSAGE_EXPERT);
            } else {
                getRenderer().showLobbyMessage(ViewString.GAME_MODE_MESSAGE_BASIC);
            }
        }
    }

    /**
     * Handles the game can start message
     */
    @Override
    public void handleGameCanStartMessage() {
        super.handleGameCanStartMessage();
    }

    /**
     * Handles the successful setting of the number of players
     *
     * @param playersToStart the number of players set to start the game
     */
    @Override
    public void handleSetPlayersToStart(int playersToStart) {
        super.handleSetPlayersToStart(playersToStart);
        getRenderer().showLobbyMessage(ViewString.PLAYERS_TO_START_SET);
    }

    /**
     * Handles the successful game mode setting
     */
    @Override
    public void handleSetGameMode() {
        super.handleSetGameMode();
        getRenderer().showLobbyMessage(ViewString.CHOOSE_PLAYERS_TO_START);
    }

    /**
     * Handles the disconnection of one of the other players from the lobby.
     *
     * @param playerName the name of the player that disconnected
     */
    @Override
    public void handlePlayerDisconnect(String playerName) {
        getRenderer().showLobbyMessage(playerName == null ? ViewString.PLAYER_DISCONNECT :
                ViewString.PLAYER_DISCONNECT_WITH_NAME.formatted(playerName));
    }

    /**
     * Handles the disconnection of one of the other players, terminating the game.
     *
     * @param playerName the name of the player that disconnected
     */
    @Override
    public void handlePlayerCrash(String playerName) {
        getRenderer().showLobbyMessage(playerName == null ? ViewString.PLAYER_CRASH :
                ViewString.PLAYER_CRASH_WITH_NAME.formatted(playerName));
    }

    /**
     * Handles the ending of the game.
     */
    @Override
    public void handleEndGame() {
        getRenderer().printResult();
    }

    /**
     * Handles the interaction with the user
     */
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        // Print the logo
        System.out.println(AnsiColour.BLUE + ASCIIArt.ERIANTYS + AnsiColour.RESET);

        getRenderer().showLobbyMessage("Enter the server ip and port (leave blank for localhost):");

        String command;
        while (getClient().isActive()) {
            try {
                command = scanner.nextLine();
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("Error: no line found");
                break;
            }


            if (!getClient().isActive())
                break;

            switch (getGameState()) {
                // Handle the connection with the Server
                case CONNECTING -> {
                    if (command.isBlank()) {
                        if (!getClient().connect())
                            getRenderer().showErrorMessage("Unknown host or port, please try again!");
                        break;
                    }

                    String[] ipAndPort = command.split(":");

                    if (ipAndPort.length != 2) {
                        getRenderer().showErrorMessage("Invalid format, please type \"serverip:port\"!");
                        break;
                    }

                    String ip = ipAndPort[0];
                    int port;
                    try {
                        port = Integer.parseInt(ipAndPort[1]);
                    } catch (NumberFormatException e) {
                        getRenderer().showErrorMessage("The port should be a number!");
                        break;
                    }

                    getClient().setIp(ip);
                    getClient().setPort(port);

                    if (!getClient().connect())
                        getRenderer().showErrorMessage("Unknown host or port, please try again!");
                }

                // Let the user choose their name
                case CHOOSING_NAME -> {
                    if (command.length() != command.trim().length()) {
                        getRenderer().showErrorMessage("The nickname must be without empty spaces");
                    } else if (command.split(" ").length > 1) {
                        getRenderer().showErrorMessage("The nickname must be without empty spaces");
                    } else {
                        setPlayerName(command);
                    }
                }

                // Let the user choose their wizard
                case CHOOSING_WIZARD -> {
                    int wizardNumber;
                    try {
                        wizardNumber = Integer.parseInt(command);
                    } catch (NumberFormatException e) {
                        getRenderer().showErrorMessage("Choose a number between 1 and 4");
                        break;
                    }
                    setWizard(Wizard.valueOf(wizardNumber));
                }

                // Let the user select the game mode
                case CHOOSING_GAME_MODE -> {
                    if (command.equalsIgnoreCase("expert")) {
                        getActionSender().setGameMode(true);
                        getRenderer().showLobbyMessage("Playing using the expert game rules!");
                    } else if (command.equalsIgnoreCase("basic")) {
                        getActionSender().setGameMode(false);
                        getRenderer().showLobbyMessage("Playing using the basic game rules!");
                    } else {
                        getRenderer().showErrorMessage("Write 'expert' or 'basic'");
                    }
                }

                // Let the user select the number of players in the game
                case CHOOSING_PLAYERS -> {
                    int playersToStart;
                    try {
                        playersToStart = Integer.parseInt(command);
                    } catch (NumberFormatException e) {
                        getRenderer().showErrorMessage(ViewString.NOT_A_NUMBER);
                        break;
                    }

                    if (playersToStart < 2 || playersToStart > 3) {
                        getRenderer().showErrorMessage(ViewString.NOT_IN_RANGE);
                        break;
                    }

                    getActionSender().setPlayersToStart(playersToStart);
                }

                // Waiting for other players
                case WAITING_PLAYERS -> getRenderer().showLobbyMessage(ViewString.WAITING_PLAYERS);

                // Playing the game
                case PLAYING -> {
                    try {
                        commandHandler.handle(command);
                    } catch (IllegalArgumentException e) {
                        getRenderer().showErrorMessage(e.getMessage());
                    }
                }
            }
        }
        scanner.close();
    }
}
