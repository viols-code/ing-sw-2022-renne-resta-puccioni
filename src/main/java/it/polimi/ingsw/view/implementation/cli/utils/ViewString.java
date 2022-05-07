package it.polimi.ingsw.view.implementation.cli.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewString {
    /*
    Lobby messages
     */
    public static final String CHOOSE_NAME = "Enter your username:";
    public static final String CHOOSE_WIZARD = "Enter your wizard:";
    public static final String CHOOSE_PLAYERS_TO_START = "Choose the number of players required to start the game:";
    public static final String PLAYERS_TO_START_SET = "Successfully set the number of players, wait for other players";
    public static final String CHOOSE_GAME_MODE = "Choose the number of players required to start the game:";
    public static final String GAME_MODE_SET = "Successfully set the game mode, wait for other players";
    public static final String PLAYER_CONNECTED = "Player %s connected";
    public static final String PLAYER_DISCONNECT = "Someone disconnected";
    public static final String PLAYER_DISCONNECT_WITH_NAME = "Player %s disconnected";
    public static final String PLAYER_CONNECTED_WITH_COUNT = "Player %s connected (%d/%d)";
    public static final String WAITING_PLAYERS = "Waiting for other players to join";
    public static final String GAME_STARTING = "The game is starting";

    /*
    Error messages
     */
    public static final String NOT_YOUR_TURN = "It's not your turn";
    public static final String COMMAND_NOT_FOUND = "This command does not exists";
    public static final String NOT_A_NUMBER = "Please input a number";
    public static final String NOT_IN_RANGE = "This is not a number between 1 and 3";
    public static final String PLAYER_CRASH = "Someone crashed, terminating the game";
    public static final String PLAYER_CRASH_WITH_NAME = "Player %s crashed, terminating the game";

    /*
    Game messages
     */
    public static final String OWN_TURN = "It's your turn";
    public static final String OTHER_TURN = "It's %s turn";
    public static final String SELECT_ASSISTANT_CARD = "Select the Assistant Card you want to play:";
    public static final String MOVE_STUDENT_FROM_ENTRANCE = "Move the students from your entrance:";
    public static final String MOVE_MOTHER_NATURE = "Select the number of steps of mother nature:";
    public static final String SELECT_CLOUD_TILE = "Select the cloud tile:";
    public static final String INCORRECT_FORMAT = "Incorrect format: please input ";

    /*
    Command format
     */

    public static final String VIEW_CHARACTER_CARDS = "\"view character cards\"";
    public static final String VIEW_ASSISTANT_CARDS = "\"view assistant cards\"";
    public static final String VIEW_ISLAND = "\"view islands\"";
    public static final String VIEW_SCHOOL_BOARD = "\"view school board\"";
    public static final String VIEW_CLOUD_TILE = "\"view cloud tile\"";
    public static final String VIEW_RESULT = "\"view winner\"";

    public static final String SPY = "\"spy <player name> <schoolBoard|currentAssistantCard>\"";
    public static final String PLAY_ASSISTANT_CARD = "\"play <assistantCard num>\"";
    public static final String PLAY_CHARACTER_CARD = "\"play <characterCard num>\"";
    public static final String MOVE_STUDENT_TO_ISLAND = "\"move student <student colour> to <groupIsland num> <singleIsland num>\"";
    public static final String MOVE_STUDENT_TO_DINING_ROOM = "\"move student <student colour> to dining room\"";
    public static final String CHOOSE_MOTHER_NATURE_STEPS = "\"move mother nature by <movement num> \"";
    public static final String CHOOSE_CLOUD_TILE = "\"choose <cloudTile num> \"";

    public static final String HELP = "\"help\"";

    public static List<String> getCommands() {
        List<String> commands = new ArrayList<>();
        addMultipleToList(commands, VIEW_CHARACTER_CARDS, VIEW_ASSISTANT_CARDS, VIEW_ISLAND, VIEW_SCHOOL_BOARD,
                VIEW_CLOUD_TILE, VIEW_RESULT, SPY, PLAY_ASSISTANT_CARD, PLAY_CHARACTER_CARD, MOVE_STUDENT_TO_ISLAND,
                MOVE_STUDENT_TO_DINING_ROOM, CHOOSE_MOTHER_NATURE_STEPS, CHOOSE_CLOUD_TILE, HELP);

        return commands;
    }

    private static void addMultipleToList(List<String> commands, String... args) {
        commands.addAll(Arrays.asList(args));
    }

}
