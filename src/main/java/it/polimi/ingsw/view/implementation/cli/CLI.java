package it.polimi.ingsw.view.implementation.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.implementation.cli.utils.ASCIIArt;
import it.polimi.ingsw.view.implementation.cli.utils.AnsiColour;
import it.polimi.ingsw.view.implementation.cli.utils.ViewString;

import java.util.List;
import java.util.Scanner;

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
        this.setModelUpdateHandler(new CLIModelUpdateHandler(this));
        this.setRenderer(new CLIRenderer(this));
        this.setActionSender(new CLIActionSender(this));
        this.commandHandler = new CommandHandler(this);
    }

    @Override
    public void addToLobby(boolean isFirstConnection) {
        super.addToLobby(isFirstConnection);
        getRenderer().showLobbyMessage(ViewString.CHOOSE_NAME);
    }

    @Override
    public void handleCorrectNickname(String nickname, List<String> takenNicknames){
        super.handleCorrectNickname(nickname, takenNicknames);
        getRenderer().showLobbyMessage(ViewString.CHOOSE_WIZARD);
    }

    @Override
    public void handleCorrectWizard(Wizard wizard, List<Wizard> takenWizard){
        super.handleCorrectWizard(wizard, takenWizard);
        switch (wizard) {
            case TYPE_1 -> System.out.println(AnsiColour.GOLD + ASCIIArt.WIZARD_ONE + AnsiColour.RESET);
            case TYPE_2 -> System.out.println(AnsiColour.GOLD + ASCIIArt.WIZARD_TWO + AnsiColour.RESET);
            case TYPE_3 -> System.out.println(AnsiColour.GOLD + ASCIIArt.WIZARD_THREE + AnsiColour.RESET);
            case TYPE_4 -> System.out.println(AnsiColour.GOLD + ASCIIArt.WIZARD_FOUR + AnsiColour.RESET);
        }
        if(isLobbyMaster()){
            getRenderer().showLobbyMessage(ViewString.CHOOSE_GAME_MODE);
        } else {
            getRenderer().showLobbyMessage(ViewString.WAITING_PLAYERS);
        }
    }

    @Override
    public void handlePlayerConnect(String playerName, Wizard wizard, int currentPlayers, Integer playersToStart){
        if(playersToStart == null){
            getRenderer().showLobbyMessage(ViewString.PLAYER_CONNECTED_WITH_COUNT.formatted(playerName, currentPlayers));
        } else {
            getRenderer().showLobbyMessage(ViewString.PLAYER_CONNECTED_WITH_COUNT_ON_TOTAL.formatted(playerName, currentPlayers, playersToStart));
        }
    }


    @Override
    public void handleGameStart(boolean gameMode) {
        super.handleGameStart(gameMode);
        getRenderer().showLobbyMessage(ViewString.GAME_STARTING);
    }

    @Override
    public void handleGameMode(boolean gameMode){
        if(!isLobbyMaster()){
            if(gameMode){
                getRenderer().showLobbyMessage(ViewString.GAME_MODE_MESSAGE_EXPERT);
            } else{
                getRenderer().showLobbyMessage(ViewString.GAME_MODE_MESSAGE_BASIC);
            }
        }
    }

    @Override
    public void handleGameCanStartMessage(){
        super.handleGameCanStartMessage();
    }

    @Override
    public void handleSetPlayersToStart(int playersToStart) {
        super.handleSetPlayersToStart(playersToStart);
        getRenderer().showLobbyMessage(ViewString.PLAYERS_TO_START_SET);
    }

    @Override
    public void handleSetGameMode() {
        super.handleSetGameMode();
        getRenderer().showLobbyMessage(ViewString.CHOOSE_PLAYERS_TO_START);
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
        System.out.println(AnsiColour.BLUE + ASCIIArt.ERIANTYS + AnsiColour.RESET);

        getRenderer().showLobbyMessage("Enter the server ip and port (leave blank for localhost):");
        //addToLobby(false);

        String command;
        while (getClient().isActive()) {
            command = scanner.nextLine();

            if (!getClient().isActive())
                break;

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
                            break;
                        }
                        setWizard(Wizard.valueOf(wizardNumber));
                }

                case CHOOSING_GAME_MODE -> {
                    if (command.equalsIgnoreCase("expert")) {
                        getActionSender().setGameMode(true);
                        getRenderer().showLobbyMessage("Playing using the expert game rules!");
                    }
                    else if(command.equalsIgnoreCase("basic")){
                        getActionSender().setGameMode(false);
                        getRenderer().showLobbyMessage("Playing using the basic game rules!");
                    }
                    else{
                        getRenderer().showErrorMessage("Write 'expert' or 'basic'");
                    }
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

                case WAITING_PLAYERS -> getRenderer().showLobbyMessage(ViewString.WAITING_PLAYERS);

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
