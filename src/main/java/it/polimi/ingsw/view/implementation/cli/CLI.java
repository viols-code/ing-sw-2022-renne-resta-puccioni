package it.polimi.ingsw.view.implementation.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.implementation.cli.utils.AnsiColour;
import it.polimi.ingsw.view.implementation.cli.utils.ViewString;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

/**
 * Command line interface main class
 */
public class CLI extends View {
    private final CommandHandler commandHandler;

    /**
     * Creates a new CLI for the given client
     * @param client the client that requests a command line interface
     */
    public CLI(Client client) {
        super(client);
        //this.setModelUpdateHandler(new CLIModelUpdateHandler(this));
        this.setRenderer(new CLIRenderer(this));
        this.setActionSender(new CLIActionSender(this));
        this.commandHandler = new CommandHandler(this);
    }

    @Override
    public void addToLobby(boolean isFirstConnection) {
        super.addToLobby(isFirstConnection);
        getRenderer().showGameMessage(ViewString.CHOOSE_NAME);
    }

    @Override
    public void handlePlayerConnect(String playerName, Wizard wizard, boolean gameMode, int currentPlayers, int playersToStart, List<String> otherConnectedPlayers) {
        super.handlePlayerConnect(playerName, wizard, gameMode, currentPlayers, playersToStart, otherConnectedPlayers);
        boolean playersToStartSet = playersToStart != -1;
        getRenderer().showLobbyMessage(playersToStartSet ? ViewString.PLAYER_CONNECTED_WITH_COUNT.formatted(playerName, currentPlayers, playersToStart) :
                ViewString.PLAYER_CONNECTED.formatted(playerName));

        if (playerName.equals(getPlayerName()) && isLobbyMaster())
            getRenderer().showGameMessage(ViewString.CHOOSE_PLAYERS_TO_START);
    }

    @Override
    public void handleSetPlayersToStart(int playersToStart) {
        super.handleSetPlayersToStart(playersToStart);
        getRenderer().showGameMessage(ViewString.PLAYERS_TO_START_SET);
    }

    @Override
    public void handleSetGameMode() {
        super.handleSetGameMode();
        getRenderer().showGameMessage(ViewString.GAME_MODE_SET);
    }

    @Override
    public void handlePlayerDisconnect(String playerName) {
        getRenderer().showLobbyMessage(playerName == null ? ViewString.PLAYER_DISCONNECT :
                ViewString.PLAYER_DISCONNECT_WITH_NAME.formatted(playerName));
    }

    @Override
    public void handlePlayerCrash(String playerName) {
        getRenderer().showLobbyMessage(playerName == null ? ViewString.PLAYER_CRASH :
                ViewString.PLAYER_CRASH_WITH_NAME.formatted(playerName));
        getClient().terminate();
    }

    @Override
    public void handleEndGame() {
        getRenderer().printResult();
        getClient().terminate();
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        //Logo
        //System.out.println(AnsiColour.BLUE + ASCIIArt.MASTER + AnsiColour.RESET);

        getRenderer().showGameMessage("Enter the server ip and port (leave blank for localhost):");
        //addToLobby(false);

        String command;
        while (getClient().isActive()) {
            command = scanner.nextLine();

            if (!getClient().isActive())
                break;

            cmdSwitch:
            switch (getGameState()) {
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
                case CHOOSING_NAME -> setPlayerName(command);

                case CHOOSING_WIZARD -> {
                        int wizardNumber;
                        try {
                        wizardNumber = Integer.parseInt(command);
                        } catch (NumberFormatException e) {
                            getRenderer().showErrorMessage("Choose a number between 1 and 4");
                            return;
                        }
                        setWizard(Wizard.valueOf(wizardNumber));
                }

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

                case CHOOSING_GAME_MODE -> {
                    if (command.equalsIgnoreCase("expert")) {
                        getActionSender().setGameMode(true);
                        getRenderer().showGameMessage("Playing using the expert game rules!");
                        break;
                    }
                    else if(command.equalsIgnoreCase("basic")){
                        getActionSender().setGameMode(false);
                        getRenderer().showGameMessage("Playing using the basic game rules!");
                        break;
                    }
                    else{
                        getRenderer().showGameMessage("Write 'expert' or 'basic'");
                    }
                }

                case WAITING_PLAYERS -> getRenderer().showGameMessage(ViewString.WAITING_PLAYERS);

                case PLAYING -> {
                    try {
                        commandHandler.handle(command);
                    } catch (IllegalArgumentException e) {
                        getRenderer().showErrorMessage(ViewString.COMMAND_NOT_FOUND);
                    }
                }
            }
        }
        scanner.close();
    }
}
