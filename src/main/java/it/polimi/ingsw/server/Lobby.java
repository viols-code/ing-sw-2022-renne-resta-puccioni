package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.messages.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.messages.*;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;

import java.util.*;

/**
 * Models a lobby, holding the list of connected clients, the first connected client and the number of players needed to
 * start the game in the current lobby
 */
public class Lobby extends Observable<IServerPacket> {
    private final UUID uuid;
    private SocketClientConnection firstConnection;
    private final List<SocketClientConnection> connections;
    private int playersToStart;
    private Boolean gameMode;
    private boolean isGameModeSet;
    private int indexOfFirstConnection = 0;
    private final List<String> nicknames = new ArrayList<>();
    private final HashMap<String, Wizard> players = new HashMap<>();
    private GameController controller;

    /**
     * Constructs a new Lobby with a random UUID, an empty connection list and the players needed to start set to -1
     */
    public Lobby() {
        this.uuid = UUID.randomUUID();
        this.firstConnection = null;
        this.connections = new ArrayList<>();
        this.playersToStart = -1;
        this.gameMode = null;
        this.isGameModeSet = false;
    }

    /**
     * Gets the UUID of this Lobby
     *
     * @return the uuid of the lobby
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Gets the list of clients connected to this lobby
     *
     * @return the list of client connections
     */
    List<SocketClientConnection> getConnections() {
        return connections;
    }


    /**
     * Adds the given connection to this lobby
     *
     * @param connection the connection to be added
     * @throws IllegalStateException if 3 clients are already connected to this lobby
     */
    public void addConnection(SocketClientConnection connection) throws IllegalStateException {
        if (connections.size() >= 3)
            throw new IllegalStateException();

        connections.add(connection);

        if (firstConnection == null) {
            firstConnection = connection;
            indexOfFirstConnection = connections.indexOf(firstConnection);
        }

        List<Wizard> otherWizard = new ArrayList<>();
        connections.forEach(con -> {
            if (con.getWizard() != null)
                otherWizard.add(con.getWizard());
        });

        notify(new AddToLobbyMessage(connection, firstConnection == connection, otherWizard));
    }

    /**
     * Sets the player name for the given connection. If there is another player with this name already connected sends
     * an error message to the client
     *
     * @param connection the connection that will have its player name set
     * @param playerName the player name to be set, if it's null or empty sends an error message to the client
     */
    public void setPlayerName(SocketClientConnection connection, String playerName) {
        try {
            checkPlayerName(playerName);
        } catch (IllegalArgumentException e) {
            notify(new ErrorMessage(connection, e.getMessage()));
        }

        connection.setPlayerName(playerName);
        if (nicknames.contains(playerName)) {
            notify(new CorrectReconnectionMessage(connection, players));
            sendGameInformation(connection);
            addToGame(connection);
            notify(new PlayerReconnectedMessage(connection.getPlayerName()));
        } else {
            List<String> otherNames = new ArrayList<>();
            connections.forEach(con -> {
                if (con.getPlayerName() != null)
                    otherNames.add(con.getPlayerName());
            });

            notify(new CorrectNicknameMessage(connection, playerName, otherNames));
        }

        nicknames.remove(playerName);

    }

    /**
     * Check if the nickname is correct
     *
     * @param playerName the player name to be set, if it's null or empty sends an error message to the client
     * @throws IllegalArgumentException if the name is already taken
     */
    public void checkPlayerName(String playerName) throws IllegalArgumentException {
        if (playerName == null || playerName.trim().equalsIgnoreCase("")) {
            throw new IllegalArgumentException("Your username can't be empty");
        } else if (playerName.trim().length() != playerName.length()) {
            throw new IllegalArgumentException("The nickname must be without empty spaces");
        } else if (playerName.split(" ").length > 1) {
            throw new IllegalArgumentException("The nickname must be without empty spaces");
        }


        for (SocketClientConnection clientConnection : connections) {
            if (playerName.equalsIgnoreCase(clientConnection.getPlayerName())) {
                throw new IllegalArgumentException("This username is already taken");
            }
        }
    }


    /**
     * Sets the wizard for the given connection. If there is another player with this wizard already connected sends
     * an error message to the client
     *
     * @param connection the connection that will have its player name set
     * @param wizard     the wizard to be set, if it's null or empty sends an error message to the client
     */
    public void setPlayerWizard(SocketClientConnection connection, Wizard wizard) {
        if (wizard == null) {
            notify(new ErrorMessage(connection, "Choose a number between 1 and 4"));
            return;
        }

        for (SocketClientConnection clientConnection : connections) {
            if (wizard.equals(clientConnection.getWizard())) {
                notify(new ErrorMessage(connection, "This wizard is already taken"));
                return;
            }
        }

        connection.setWizard(wizard);
        List<Wizard> otherWizard = new ArrayList<>();
        connections.forEach(con -> {
            if (con.getWizard() != null)
                otherWizard.add(con.getWizard());
        });

        if (connection.getPlayerName() != null) {
            Integer players;
            if (playersToStart == -1) {
                players = null;
            } else {
                players = playersToStart;
            }
            notify(new PlayerConnectMessage(connection.getPlayerName(), wizard, connections.indexOf(connection) + 1, players, otherWizard));
            notify(new CorrectWizardMessage(connection, wizard));
        }

        players.put(connection.getPlayerName(), connection.getWizard());
    }

    /**
     * Sets the players needed to start the game, if the given connection is not the first connection to the lobby
     * sends an error message to the client. If the number given is less than the number of clients already connected
     * sends an error message to the client
     *
     * @param connection     the connection that wants to set the number of players needed to start
     * @param playersToStart the number of players needed to start the game, should be between 2 and 3, otherwise an
     *                       error message will be sent to the client
     */
    public void setPlayersToStart(SocketClientConnection connection, int playersToStart) {
        if (connections.indexOf(connection) != indexOfFirstConnection) {
            notify(new ErrorMessage(connection, "Only the first player that connected to the lobby can set the number of players needed to start the game"));
            return;
        }
        if (playersToStart < 2 || playersToStart > 3) {
            notify(new ErrorMessage(connection, "This is not a number between 2 and 3"));
            return;
        }
        if (playersToStart < connections.size()) {
            notify(new ErrorMessage(connection, connections.size() + " players are already connected"));
            return;
        }

        this.playersToStart = playersToStart;
        notify(new PlayersToStartSetMessage(connection, playersToStart));
    }

    /**
     * Sets the gameMode (expert or basic), if the given connection is not the first connection to the lobby
     * sends an error message to the client. If the game mode chosen is not one of the available ones sends an error to the client
     *
     * @param connection the connection
     * @param gameMode   the game mode chosen
     */
    public void setGameMode(SocketClientConnection connection, Boolean gameMode) {
        if (connections.indexOf(connection) != indexOfFirstConnection) {
            notify(new ErrorMessage(connection, "Only the first player that connected to the lobby can set game mode"));
            return;
        }
        if (gameMode == null) {
            notify(new ErrorMessage(connection, "The game mode that you have chosen is invalid!"));
            return;
        }

        this.gameMode = gameMode;
        setGameMode(connection);
    }

    /**
     * Sets to true if the master player has already chosen the desired gameMode
     */
    public void setGameMode(SocketClientConnection connection) {
        isGameModeSet = true;
        notify(new GameModeSetMessage(connection));
        notify(new GameModeMessage(gameMode));
    }

    /**
     * Gets the gameMode chosen
     *
     * @return true if the gameMode is Expert
     */
    public boolean getGameMode() {
        return gameMode;
    }

    /**
     * Gets the number of players for the game
     *
     * @return the number of players participating in the game
     */
    public int getPlayersToStart() {
        return playersToStart;
    }

    /**
     * Notifies all connected clients that the game is starting
     */
    public synchronized void startGame() {
        HashMap<String, Wizard> players = new HashMap<>();
        connections.forEach(connection -> players.put(connection.getPlayerName(), connection.getWizard()));
        notify(new AllPlayersConnectedMessage(players, gameMode, playersToStart));
        notify(new GameStartMessage(gameMode));
        notify(new GameCanStartMessage());
    }

    /**
     * Remove the given crashed connection from the Lobby, disconnecting and notifying all others clients
     *
     * @param crashedConnection the connection that crashed
     */
    public synchronized void disconnectAll(SocketClientConnection crashedConnection) {
        getConnections().remove(crashedConnection);
        notify(new PlayerCrashMessage(crashedConnection.getPlayerName()));

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                connections.stream().filter(Objects::nonNull).forEach((conn) -> {
                    conn.closeConnection();
                    System.out.println("Connection of player " + conn.getPlayerName() + " closed");
                });
                connections.clear();
            }
        }, 5000);
    }

    /**
     * Checks if the number of players needed to start the game for this Lobby is set
     *
     * @return true if the playersToStart set are 2 or 3, false otherwise
     */
    public boolean isPlayersToStartSet() {
        return playersToStart == 2 || playersToStart == 3;
    }

    /**
     * Checks if the Lobby can start a game
     *
     * @return true if all connected clients have their name set and the number of connected clients is equal to
     * playersToStart, false otherwise
     */
    public synchronized boolean canStart() {
        for (SocketClientConnection conn : connections)
            if (conn.getPlayerName() == null || conn.getWizard() == null)
                return false;
        if (!isGameModeSet) return false;
        return playersToStart == connections.size();
    }

    /**
     * Remove the given connection from the Lobby, notifying all other clients.
     *
     * @param connection the connection to be removed from the lobby
     */
    public void disconnect(SocketClientConnection connection) {
        if (connection == firstConnection) {
            firstConnection = null;
            playersToStart = -1;
        }

        notify(new PlayerLeaveMessage(connection.getPlayerName()));
        nicknames.add(connection.getPlayerName());
        connections.remove(connection);
        connection.getRemoteView().getGameController().removePlayer(connection.getPlayerName());
    }

    /**
     * Add the client to the already started game
     */
    public void addToGame(SocketClientConnection connection) {
        Thread t = new Thread(new ReconnectionInstance(this, controller, connection));
        t.start();
    }

    /**
     * Terminate the Lobby, disconnecting all clients
     */
    public synchronized void terminate() {
        connections.stream().filter(Objects::nonNull).forEach(SocketClientConnection::closeConnection);
        connections.clear();
    }

    /**
     * Get the list of nicknames
     *
     * @return the list of nicknames
     */
    public List<String> getNicknames() {
        return nicknames;
    }

    /**
     * Sets the game controller of the Lobby
     *
     * @param gameController the game controller of the Lobby
     */
    public void setController(GameController gameController){
        this.controller = gameController;
    }

    /**
     * Notifies the correct reconnection of a player
     *
     * @param connection the connection of the player
     */
    public void notifyCorrectReconnection(SocketClientConnection connection) {
        notify(new CorrectReconnectionMessage(connection, players));
    }


    /**
     * Sends the information of the game to the reconnected client
     *
     * @param connection the connection of the player
     */
    public void sendGameInformation(SocketClientConnection connection){

        notify(new ModelInfoReconnectedUpdate(connection,controller.getGame().getTurnPhase(),controller.getGame().getGamePhase()));

        for(int i = 0; i < controller.getGame().getNumberOfPlayer(); i++){
            Player player1 = controller.getGame().getPlayerByIndex(i);
            int coins = -1;

            if(controller.isGameExpert()){
                coins = player1.getCoins();
            }
            HashMap<Colour, Integer> entrance = new HashMap<>();
            for(Colour colour : Colour.values()){
                entrance.put(colour, player1.getSchoolBoard().getEntrance(colour));
            }

            HashMap<Colour, Integer> diningRoom = new HashMap<>();
            for(Colour colour : Colour.values()){
                diningRoom.put(colour, player1.getSchoolBoard().getDiningRoom(colour));
            }

            HashMap<Colour, Boolean> professors = new HashMap<>();
            for(Colour colour : Colour.values()){
                professors.put(colour, player1.getSchoolBoard().hasProfessor(colour));
            }

            int currentAssistantCard = player1.getCurrentAssistantCard().getValue();

            notify(new SchoolBoardUpdate(connection, player1.getNickname(), entrance, diningRoom,
                    player1.getSchoolBoard().getTowers(), player1.getTowerColour(), professors, coins, currentAssistantCard));
        }

        notify(new CurrentPlayerReconnectUpdate(connection, controller.getGame().getCurrentPlayer().getNickname()));

        List<Integer> assistantCardsUsed = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            if(!controller.getGame().getPlayerByNickname(connection.getPlayerName()).isAssistantCardPresent(controller.getGame().getAssistantCard(i))){
                assistantCardsUsed.add(i);
            }
        }

        notify(new assistantCardsUpdate(connection, assistantCardsUsed));

        List<String> influentPlayers = new ArrayList<>();
        List<Integer> noEntryTiles = new ArrayList<>();
        List<Integer> singleIslands = new ArrayList<>();
        HashMap<Integer, HashMap<Colour, Integer>> students = new HashMap<>();
        HashMap<Integer, HashMap<Colour, Integer>> studentsOnCloudTiles = new HashMap<>();

        int count = 0;

        CharacterCardEnumeration characterCard = controller.getGame().getActiveCharacterCard().getCharacterCardType();

        HashMap<Colour, Boolean> professors = new HashMap<>();

        for(Colour colour : Colour.values()){
            professors.put(colour, controller.getGame().getTable().isProfessorOnTable(colour));
        }

        for(int i = 0; i < controller.getGame().getTable().getNumberOfGroupIsland(); i++){
            if(controller.getGame().getTable().getGroupIslandByIndex(i).getInfluence() != null){
                influentPlayers.add(controller.getGame().getTable().getGroupIslandByIndex(i).getInfluence().getNickname());
            }else{
                influentPlayers.add("");
            }

            if(controller.isGameExpert() && controller.getGame().hasProtectIslandCard()){
                noEntryTiles.add(controller.getGame().getTable().getGroupIslandByIndex(i).getNumberOfNoEntryTile());
            }else{
                noEntryTiles.add(0);
            }

            singleIslands.add(controller.getGame().getTable().getGroupIslandByIndex(i).getNumberOfSingleIsland());
            for(int j = 0; j < controller.getGame().getTable().getGroupIslandByIndex(i).getNumberOfSingleIsland(); j++){

                HashMap<Colour, Integer> studentsOnSingleIsland = new HashMap<>();
                for(Colour colour : Colour.values()){
                    studentsOnSingleIsland.put(colour, controller.getGame().getTable().getGroupIslandByIndex(i).getIslandByIndex(j).getStudents(colour));
                }

                students.put(count, studentsOnSingleIsland);
                count++;
            }
        }

        int motherNaturePosition = controller.getGame().getTable().getMotherNaturePosition();

        for(int i = 0; i < controller.getGame().getTable().getNumberOfCloudTile(); i++){
            HashMap<Colour, Integer> cloudStudents = new HashMap<>();

            for(Colour colour : Colour.values()){
                cloudStudents.put(colour, controller.getGame().getTable().getCloudTilesByIndex(i).getTileStudents(colour));
            }

            studentsOnCloudTiles.put(i, cloudStudents);
        }

        boolean hasProtectedCard = false;
        if(controller.isGameExpert()){
            hasProtectedCard = controller.getGame().hasProtectIslandCard();
        }

        notify(new TableReconnectUpdate(connection, controller.getGame().getTable().getNumberOfGroupIsland(), hasProtectedCard, influentPlayers,
                noEntryTiles, singleIslands, students, motherNaturePosition, studentsOnCloudTiles, characterCard, professors));


        if(gameMode){
            List<CharacterCardEnumeration> cards = new ArrayList<>();
            HashMap<CharacterCardEnumeration, Integer> cost = new HashMap<>();
            HashMap<Colour, Integer> student0 = new HashMap<>();
            HashMap<Colour, Integer> student1= new HashMap<>();
            HashMap<Colour, Integer> student2 = new HashMap<>();
            int noEntryTile = 0;

            for(int i = 0; i < 3; i++){
                cards.add(controller.getGame().getCharacterCardByIndex(i).getCharacterCardType());
                cost.put(controller.getGame().getCharacterCardByIndex(i).getCharacterCardType(), controller.getGame().getCharacterCardByIndex(i).getCost());

                for(Colour colour : Colour.values()){
                    if(i == 0){
                        try{
                            student0.put(colour, controller.getGame().getCharacterCardByIndex(i).getStudent(colour));
                            noEntryTile = controller.getGame().getCharacterCardByIndex(i).getNumberOfNoEntryTiles();
                        } catch(IllegalAccessError e){

                        }
                    } else if(i == 1){
                        try{
                            student1.put(colour, controller.getGame().getCharacterCardByIndex(i).getStudent(colour));
                            noEntryTile = controller.getGame().getCharacterCardByIndex(i).getNumberOfNoEntryTiles();
                        } catch(IllegalAccessError e){

                        }
                    } else{
                        try{
                            student2.put(colour, controller.getGame().getCharacterCardByIndex(i).getStudent(colour));
                            noEntryTile = controller.getGame().getCharacterCardByIndex(i).getNumberOfNoEntryTiles();
                        } catch(IllegalAccessError e){

                        }
                    }
                }
            }

            notify(new CharacterCardUpdate(connection, cards, cost, student0, student1, student2, controller.getGame().getCoins(), noEntryTile));
        }


    }
}