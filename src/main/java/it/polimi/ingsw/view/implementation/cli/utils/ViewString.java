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
    public static final String CHOOSE_GAME_MODE = "Choose the game mode writing 'basic' or 'expert'";
    public static final String PLAYER_DISCONNECT = "Someone disconnected";
    public static final String PLAYER_DISCONNECT_WITH_NAME = "Player %s disconnected";
    public static final String PLAYER_CONNECTED_WITH_COUNT = "Player %s connected with count %d. The number of players is not set yet";
    public static final String PLAYER_CONNECTED_WITH_COUNT_ON_TOTAL = "Player %s connected with count (%d/%d)";
    public static final String WAITING_PLAYERS = "Waiting for other players to join";
    public static final String GAME_STARTING = "The game is starting";

    /*
    Error messages
     */
    public static final String GAME_MODE = "The game is in the basic mode";
    public static final String NOT_A_NUMBER = "Please input a number";
    public static final String NOT_IN_RANGE = "This is not a number between 2 and 3";
    public static final String PLAYER_CRASH = "Someone crashed, terminating the game";
    public static final String PLAYER_CRASH_WITH_NAME = "Player %s crashed, terminating the game";


    /*
    Game messages
     */
    public static final String GAME_MODE_MESSAGE_EXPERT = "The game mode has been set to expert";
    public static final String GAME_MODE_MESSAGE_BASIC = "The game mode has been set to basic";
    public static final String OWN_TURN = "It's your turn";
    public static final String OTHER_TURN = "It's %s's turn";
    public static final String SELECT_ASSISTANT_CARD = "Select the assistant card you want to play:";
    public static final String YOU_SELECTED_ASSISTANT_CARD = "You played the assistant card number %d";
    public static final String OTHER_SELECTED_ASSISTANT_CARD = "%s played the assistant card number %d";
    public static final String MOVE_STUDENT_FROM_ENTRANCE = "Move the students from your entrance:";
    public static final String YOU_GOT_PROFESSORS = "You have now the professor of colour %s";
    public static final String YOU_LOST_PROFESSORS = "You have lost the professor of colour %s";
    public static final String OTHER_GOT_PROFESSORS = "%s has now the professor of colour %s";
    public static final String OTHER_LOST_PROFESSORS = "%s has lost the professor of colour %s";
    public static final String MOVE_MOTHER_NATURE = "Select the number of steps of mother nature:";
    public static final String YOU_SELECTED_MOTHER_NATURE_MOVEMENT = "You have moved mother nature to the group island %d";
    public static final String OTHER_SELECTED_MOTHER_NATURE_MOVEMENT = "%s has moved mother nature to the group island %d";
    public static final String SELECT_CLOUD_TILE = "Select the cloud tile:";
    public static final String ROUND = "The round %d is starting";
    public static final String UNIFY_ISLANDS = "Group island %d and group island %d have been unified";
    public static final String YOU_INFLUENCE_PLAYER = "You have now the influence on the group island %d";
    public static final String OTHER_INFLUENCE_PLAYER = "%s has now the influence on the group island %d";
    public static final String YOU_LOST_INFLUENCE = "You have lost the influence on the group island %d";
    public static final String OTHER_LOST_INFLUENCE = "%s has lost the influence on the group island %d";
    public static final String MOTHER_NATURE_POSITION_UNIFY = "Due to the unification of group islands mother nature is now at position %d";
    public static final String YOU_WINNER = "The game has ended. You have won!";
    public static final String OTHER_WINNER = "The game has ended. The winner is %s";
    public static final String CARD_NOT_PLAYED = "%s hasn't played the assistant card yet";
    public static final String YOUR_CARD_NOT_PLAYED = "You haven't played the assistant card yet";

    // mettiamo anche i mex delle character card?
    public static final String NO_ACTIVE_CHARACTER_CARD = "There is no active character card";

    /*
    Input errors
     */
    public static final String INCORRECT_FORMAT = "Incorrect format: please input ";
    public static final String INCORRECT_COMMAND = "Incorrect format: please type 'help' to see all possible commands";
    public static final String PLAY = ViewString.PLAY_ASSISTANT_CARD + " or " + ViewString.PLAY_CHARACTER_CARD;



    /*
    Command format
     */
    public static final String VIEW_CHARACTER_CARDS = "\"view character cards\"";
    public static final String VIEW_ASSISTANT_CARDS = "\"view assistant cards\"";
    public static final String VIEW_CURRENT_ASSISTANT_CARDS = "\"view current assistant card\"";
    public static final String VIEW_ACTIVE_CHARACTER_CARDS = "\"view active character card\"";
    public static final String VIEW_ISLANDS = "\"view islands\"";
    public static final String VIEW_PROFESSORS = "\"view professors\"";
    public static final String VIEW_SCHOOL_BOARD = "\"view school board\"";
    public static final String VIEW_CLOUD_TILE = "\"view cloud tile\"";
    public static final String VIEW_COINS = "\"view coins\"";
    public static final String VIEW_BANK = "\"view bank\"";
    public static final String VIEW_RESULT = "\"view result\"";

    public static final String SPY = "\"spy <player name> <school board|current assistant card|coins>\"";
    public static final String PLAY_ASSISTANT_CARD = "\"play assistant card <assistantCard num>\"";
    public static final String PLAY_CHARACTER_CARD = "\"play character card <characterCard num>\"";
    public static final String MOVE_STUDENT_TO_ISLAND = "\"move student to single island <student colour> <group island num> <single island num>\"";
    public static final String MOVE_STUDENT_TO_DINING_ROOM = "\"move student to dining room <student colour>\"";
    public static final String MOVE_MOTHER_NATURE_STEPS = "\"move mother nature <mother nature step num> \"";
    public static final String CHOOSE_CLOUD_TILE = "\"choose <cloud tile num> \"";
    public static final String EXCHANGE_DINING_ROOM_ENTRANCE = "\"exchange dining room <student colour> entrance <student colour> \"";
    public static final String SELECT_GROUP_ISLAND = "\"select group island <group island num>\"";
    public static final String SELECT_STUDENT_COLOUR = "\"select colour <student colour>\"";
    public static final String STUDENT_TO_ENTRANCE = "\"exchange entrance <student colour> card <student colour>\"";
    public static final String STUDENT_TO_ISLAND = "\"put <student colour> on <group island num> <single island num>\"";

    public static final String HELP = "\"help\"";

    public static List<String> getCommands() {
        List<String> commands = new ArrayList<>();
        addMultipleToList(commands, VIEW_CHARACTER_CARDS, VIEW_ASSISTANT_CARDS, VIEW_ACTIVE_CHARACTER_CARDS, VIEW_CURRENT_ASSISTANT_CARDS, VIEW_ISLANDS, VIEW_SCHOOL_BOARD,
                VIEW_CLOUD_TILE, VIEW_COINS, VIEW_PROFESSORS, VIEW_BANK, VIEW_RESULT, SPY, PLAY_ASSISTANT_CARD, PLAY_CHARACTER_CARD, MOVE_STUDENT_TO_ISLAND,
                MOVE_STUDENT_TO_DINING_ROOM, MOVE_MOTHER_NATURE_STEPS, CHOOSE_CLOUD_TILE, EXCHANGE_DINING_ROOM_ENTRANCE, SELECT_GROUP_ISLAND, SELECT_STUDENT_COLOUR,
                STUDENT_TO_ENTRANCE, STUDENT_TO_ISLAND, HELP);

        return commands;
    }

    private static void addMultipleToList(List<String> commands, String... args) {
        commands.addAll(Arrays.asList(args));
    }

}
