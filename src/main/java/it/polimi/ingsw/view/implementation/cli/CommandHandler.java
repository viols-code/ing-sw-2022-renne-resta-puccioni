package it.polimi.ingsw.view.implementation.cli;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;
import it.polimi.ingsw.view.implementation.cli.utils.ViewString;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Locale;

public class CommandHandler {
    private final CLI cli;

    /**
     * Creates a new CommandHandler for the given CLI.
     *
     * @param cli the cli to be associated to this CommandHandler
     */
    public CommandHandler(CLI cli) {
        this.cli = cli;
    }

    public String extractCommand(String str) {
        return str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1);
    }


    /**
     * The main method. It parses the input, checking if it corresponds to a possible move, and in this case it calls it.
     *
     * @param command user input in the CLI
     * @throws IllegalArgumentException if the input does not match with any possible player action
     */
    public void handle(String command) throws IllegalArgumentException {
        if (command == null)
            throw new IllegalArgumentException("Command can't be null");
        if (command.trim().equals(""))
            throw new IllegalArgumentException("Command can't be empty");

        command = command.trim();
        String[] split = command.split(" ");
        String cmd = split[0];
        String[] args = new String[0];
        if (cmd.equals("view")) {
            if (split.length == 1) {
                System.out.println(ViewString.INCORRECT_COMMAND);
                return;
            }

            split[1] = extractCommand(split[1]);
            for (int i = 2; i < split.length; i++) {
                split[1] += extractCommand(split[i]);
            }
            cmd = cmd.concat(split[1]);
            args = null;
        } else if (cmd.equals("play")) {
            if (split.length < 3) {
                System.out.println(ViewString.INCORRECT_FORMAT + ViewString.PLAY);
                return;
            }

            split[1] = extractCommand(split[1]);
            split[1] += extractCommand(split[2]);
            cmd = cmd.concat(split[1]);
            args = Arrays.copyOfRange(split, 3, split.length);
        } else if (split.length > 1)
            args = Arrays.copyOfRange(split, 1, split.length);

        try {
            Method cmdHandler;
            if (args != null) {
                cmdHandler = getClass().getMethod(cmd, args.getClass());
                cmdHandler.invoke(this, (Object) args);
            } else {
                cmdHandler = getClass().getMethod(cmd);
                cmdHandler.invoke(this);
            }
        } catch (NoSuchMethodException | SecurityException |
                IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Calls the method to make the player see the character cards.
     */
    public void viewCharacterCards() {
        if (cli.getGameMode()) {
            cli.getRenderer().printCharacterCards();
        } else {
            System.out.println(ViewString.GAME_MODE);
        }
    }

    /**
     * Calls the method to make the player see their assistant cards.
     */
    public void viewAssistantCards() {
        cli.getRenderer().printAvailableAssistantCards();
    }

    /**
     * Calls the method to make the player see their assistant cards.
     */
    public void viewCurrentAssistantCard() {

        if (cli.getModel().getLocalPlayer().getCurrentAssistantCard() != null) {
            cli.getRenderer().printLocalPlayerCurrentAssistantCard();
        } else {
            cli.getRenderer().showGameMessage(ViewString.YOUR_CARD_NOT_PLAYED);
        }
    }

    /**
     * Calls the method to make the player see their assistant cards.
     */
    public void viewActiveCharacterCard() {
        if (cli.getGameMode()) {
            cli.getRenderer().printActiveCharacterCard();
        } else {
            System.out.println(ViewString.GAME_MODE);
        }
    }

    /**
     * Calls the method to make the player see the islands.
     */
    public void viewIslands() {
        cli.getRenderer().printIslands();
    }

    /**
     * Calls the method to make the player see their schoolBoard.
     */
    public void viewSchoolBoard() {
        cli.getRenderer().printLocalPlayerSchoolBoard();
    }

    /**
     * Calls the method to make the player see the cloud tiles.
     */
    public void viewCloudTile() {
        cli.getRenderer().printCloudTiles();
    }

    /**
     * Calls the method to make the player see the professors on the table.
     */
    public void viewProfessors() {
        cli.getRenderer().printTableProfessors();
    }

    /**
     * Calls the method to make the player see the local player coins.
     */
    public void viewCoins() {
        if (cli.getGameMode()) {
            cli.getRenderer().printLocalPlayerCoins();
        } else {
            System.out.println(ViewString.GAME_MODE);
        }
    }

    /**
     * Calls the method to make the player see coins on the table.
     */
    public void viewBank() {
        if (cli.getGameMode()) {
            cli.getRenderer().printTableCoins();
        } else {
            System.out.println(ViewString.GAME_MODE);
        }
    }

    /**
     * Calls the method to make the player see the winner.
     */
    public void viewResult() {
        cli.getRenderer().printResult();
    }

    /**
     * Checks if the arguments are correct and then calls for the methods to spy other players' boards.
     *
     * @param args the decomposed user command
     */
    public void spy(String[] args) {
        if (args.length < 2) {
            cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.SPY);
            return;
        }
        String playerName = args[0];
        playerName = playerName.toLowerCase(Locale.ROOT);
        String object = args[1];
        for (int i = 2; i < args.length; i++) {
            object = object.concat(extractCommand(args[i]));
        }

        switch (object) {
            case "schoolBoard" -> cli.getRenderer().printOthersSchoolBoard(playerName);
            case "currentAssistantCard" -> {
                if (cli.getModel().getPlayerByNickname(playerName).getCurrentAssistantCard() != null) {
                    cli.getRenderer().printOthersCurrentAssistantCard(playerName);
                } else {
                    cli.getRenderer().showGameMessage(ViewString.CARD_NOT_PLAYED.formatted(playerName));
                }
            }
            case "coins" -> {
                if (cli.getGameMode()) {
                    cli.getRenderer().printOthersCoins(playerName);
                } else {
                    System.out.println(ViewString.GAME_MODE);
                }
            }
            default -> cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.SPY);
        }
    }


    /**
     * Checks if the arguments are correct and then calls for the action sender to send a "move" action event.
     *
     * @param args the decomposed user command
     */
    public void move(String[] args) {
        if (args.length < 1) {
            cli.getRenderer().showErrorMessage(ViewString.INCORRECT_COMMAND);
            return;
        }

        String command = "move";
        String[] arguments = null;

        switch (args[0]) {
            case "mother" -> {
                if (args.length != 3) {
                    cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.MOVE_MOTHER_NATURE_STEPS);
                    return;
                }
                command = command.concat(extractCommand(args[0]));
                command = command.concat(extractCommand(args[1]));
                arguments = Arrays.copyOfRange(args, 2, args.length);
            }
            case "student" -> {
                if (args.length < 4) {
                    cli.getRenderer().showErrorMessage(ViewString.INCORRECT_COMMAND);
                    return;
                }

                for (int i = 0; i < 4; i++) {
                    command = command.concat(extractCommand(args[i]));
                }

                arguments = Arrays.copyOfRange(args, 4, args.length);
            }
        }

        try {
            Method cmdHandler;
            if (arguments != null) {
                cmdHandler = getClass().getMethod(command, arguments.getClass());
                cmdHandler.invoke(this, (Object) arguments);
            } else {
                cli.getRenderer().showErrorMessage(ViewString.INCORRECT_COMMAND);
            }
        } catch (NoSuchMethodException | SecurityException |
                IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new IllegalArgumentException("Command does not exists.");
        }
    }

    /**
     * Checks if the arguments are correct and then calls for the action sender to send a "move mother nature" action event.
     *
     * @param args the decomposed user command
     */
    public void moveMotherNature(String[] args) {
        if (args.length != 1) {
            cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.MOVE_MOTHER_NATURE_STEPS);
            return;
        }

        try {
            int steps = Integer.parseInt(args[0]);
            cli.getActionSender().moveMotherNature(cli.getPlayerName(), steps);
        } catch (NumberFormatException e) {
            cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.MOVE_MOTHER_NATURE_STEPS);
        }
    }

    /**
     * Checks if the arguments are correct and then calls for the action sender to send a "move student to single island" action event.
     *
     * @param args the decomposed user command
     */
    public void moveStudentToSingleIsland(String[] args) {
        if (args.length != 3) {
            cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.MOVE_STUDENT_TO_ISLAND);
            return;
        }

        try {
            Colour colour = Colour.valueOf(args[0].toUpperCase(Locale.ROOT));
            int groupIsland = Integer.parseInt(args[1]);
            int singleIsland = Integer.parseInt(args[2]);
            cli.getActionSender().moveStudentToIsland(cli.getPlayerName(), colour, groupIsland, singleIsland);
        } catch (NumberFormatException e) {
            cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.MOVE_STUDENT_TO_ISLAND);
        }

    }

    /**
     * Checks if the arguments are correct and then calls for the action sender to send a "move student to dining room" action event.
     *
     * @param args the decomposed user command
     */
    public void moveStudentToDiningRoom(String[] args) {
        if (args.length != 1) {
            cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.MOVE_STUDENT_TO_DINING_ROOM);
            return;
        }

        Colour colour = Colour.valueOf(args[0].toUpperCase());
        cli.getActionSender().moveStudentToDiningRoom(cli.getPlayerName(), colour);

    }

    /**
     * Checks if the arguments are correct and then calls for the action sender to send a "draw" action event.
     *
     * @param args the decomposed user command
     */
    public void playAssistantCard(String[] args) {
        if (args.length != 1) {
            cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.PLAY_ASSISTANT_CARD);
            return;
        }

        try {
            int card = Integer.parseInt(args[0]);
            cli.getActionSender().playAssistantCard(cli.getPlayerName(), card);
        } catch (NumberFormatException e) {
            cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.PLAY_ASSISTANT_CARD);
        }
    }

    /**
     * Checks if the arguments are correct and then calls for the action sender to send a "draw" action event.
     *
     * @param args the decomposed user command
     */
    public void playCharacterCard(String[] args) {
        if (args.length != 1) {
            cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.PLAY_CHARACTER_CARD);
            return;
        }

        try {
            int card = Integer.parseInt(args[0]);
            if (cli.getGameMode()) {
                cli.getActionSender().playCharacterCard(cli.getPlayerName(), card);
            } else {
                cli.getRenderer().showErrorMessage(ViewString.GAME_MODE);
            }
        } catch (NumberFormatException e) {
            cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.PLAY_CHARACTER_CARD);
        }
    }

    /**
     * Checks if the arguments are correct and then calls for the action sender to send a "choose cloud tile" action event.
     *
     * @param args the decomposed user command
     */
    public void choose(String[] args) {
        if (args.length != 1) {
            cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.CHOOSE_CLOUD_TILE);
            return;
        }

        try {
            int cloudTile = Integer.parseInt(args[0]);
            cli.getActionSender().chooseCloudTile(cli.getPlayerName(), cloudTile);
        } catch (NumberFormatException e) {
            cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.CHOOSE_CLOUD_TILE);
        }
    }

    /**
     * Checks if the arguments are correct and then calls for the action sender to send a "select" action event.
     *
     * @param args the decomposed user command
     */

    public void select(String[] args) {
        if (args.length < 1) {
            cli.getRenderer().showErrorMessage(ViewString.INCORRECT_COMMAND);
            return;
        }

        switch (args[0]) {
            case "colour" -> {
                if (args.length != 2) {
                    cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.SELECT_STUDENT_COLOUR);
                    return;
                }

                if(cli.getGameMode()){
                    if(cli.getModel().getCurrentCharacterCard() != null && (cli.getModel().getCurrentCharacterCard().getType() == CharacterCardEnumeration.NO_COLOUR ||
                            cli.getModel().getCurrentCharacterCard().getType() == CharacterCardEnumeration.THREE_STUDENT)){
                        Colour colour = Colour.valueOf(args[1].toUpperCase(Locale.ROOT));
                        cli.getActionSender().setColour(cli.getPlayerName(), colour);
                    } else{
                        if(cli.getModel().getCurrentCharacterCard() == null || cli.getModel().getCurrentCharacterCard().getType() == CharacterCardEnumeration.BASIC_STATE){
                            cli.getRenderer().showErrorMessage(ViewString.NO_ACTIVE_CHARACTER_CARD);
                        } else{
                            cli.getRenderer().showErrorMessage(ViewString.NO_METHOD);
                        }
                    }
                } else {
                    cli.getRenderer().showErrorMessage(ViewString.GAME_MODE);
                }

            }

            case "group" -> {
                if (args.length != 3) {
                    cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.SELECT_GROUP_ISLAND);
                    return;
                }
                if (!args[1].equals("island")) {
                    cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.SELECT_GROUP_ISLAND);
                    return;
                }


                if(cli.getGameMode()){
                    if(cli.getModel().getCurrentCharacterCard() != null && (cli.getModel().getCurrentCharacterCard().getType() == CharacterCardEnumeration.ISLAND_INFLUENCE ||
                            cli.getModel().getCurrentCharacterCard().getType() == CharacterCardEnumeration.PROTECT_ISLAND)){
                        try {
                            int groupIsland = Integer.parseInt(args[2]);
                            cli.getActionSender().setGroupIsland(cli.getPlayerName(), groupIsland);
                        } catch (NumberFormatException e) {
                            cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.PLAY_CHARACTER_CARD);
                        }
                    } else{
                        if(cli.getModel().getCurrentCharacterCard() == null || cli.getModel().getCurrentCharacterCard().getType() == CharacterCardEnumeration.BASIC_STATE){
                            cli.getRenderer().showErrorMessage(ViewString.NO_ACTIVE_CHARACTER_CARD);
                        } else{
                            cli.getRenderer().showErrorMessage(ViewString.NO_METHOD);
                        }
                    }
                } else {
                    cli.getRenderer().showErrorMessage(ViewString.GAME_MODE);
                }

            }

            default -> cli.getRenderer().showErrorMessage(ViewString.INCORRECT_COMMAND);
        }

    }

    /**
     * Checks if the arguments are correct and then calls for the action sender to send a "set colour and island" action event.
     *
     * @param args the decomposed user command
     */
    public void put(String[] args) {
        if (args.length != 4) {
            cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.STUDENT_TO_ISLAND);
            return;
        }
        try {
            Colour colour = Colour.valueOf(args[0].toUpperCase(Locale.ROOT));
            if (!args[1].equals("on")) {
                cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.STUDENT_TO_ISLAND);
                return;
            }

            if(cli.getGameMode()){
                if(cli.getModel().getCurrentCharacterCard() != null && cli.getModel().getCurrentCharacterCard().getType() == CharacterCardEnumeration.STUDENT_TO_ISLAND){
                    int groupIsland = Integer.parseInt(args[2]);
                    int singleIsland = Integer.parseInt(args[3]);
                    cli.getActionSender().setColourAndIsland(cli.getPlayerName(), colour, groupIsland, singleIsland);
                } else{
                    if(cli.getModel().getCurrentCharacterCard() == null || cli.getModel().getCurrentCharacterCard().getType() == CharacterCardEnumeration.BASIC_STATE){
                        cli.getRenderer().showErrorMessage(ViewString.NO_ACTIVE_CHARACTER_CARD);
                    } else{
                        cli.getRenderer().showErrorMessage(ViewString.NO_METHOD);
                    }
                }
            } else {
                cli.getRenderer().showErrorMessage(ViewString.GAME_MODE);
            }


        } catch (NumberFormatException e) {
            cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.STUDENT_TO_ISLAND);
        }
    }

    /**
     * Checks if the arguments are correct and then calls for the action sender to send "exchange" action event.
     *
     * @param args the decomposed user command
     */
    public void exchange(String[] args) {
        if (args.length < 1) {
            cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT);
            return;
        }

        switch (args[0]) {
            case "dining" -> {
                if (args.length != 5) {
                    cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.EXCHANGE_DINING_ROOM_ENTRANCE);
                    return;
                }

                if (!args[1].equals("room")) {
                    cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.EXCHANGE_DINING_ROOM_ENTRANCE);
                    return;
                }

                if (!args[3].equals("entrance")) {
                    cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.EXCHANGE_DINING_ROOM_ENTRANCE);
                    return;
                }

                if(cli.getGameMode()){
                    if(cli.getModel().getCurrentCharacterCard() != null && cli.getModel().getCurrentCharacterCard().getType() == CharacterCardEnumeration.EXCHANGE_ENTRANCE_DINING_ROOM){
                        Colour colourDiningRoom = Colour.valueOf(args[2].toUpperCase(Locale.ROOT));
                        Colour colourEntrance = Colour.valueOf(args[4].toUpperCase(Locale.ROOT));
                        cli.getActionSender().setColourDiningRoomEntrance(cli.getPlayerName(), colourDiningRoom, colourEntrance);
                    } else{
                        if(cli.getModel().getCurrentCharacterCard() == null || cli.getModel().getCurrentCharacterCard().getType() == CharacterCardEnumeration.BASIC_STATE){
                            cli.getRenderer().showErrorMessage(ViewString.NO_ACTIVE_CHARACTER_CARD);
                        } else{
                            cli.getRenderer().showErrorMessage(ViewString.NO_METHOD);
                        }
                    }
                } else {
                    cli.getRenderer().showErrorMessage(ViewString.GAME_MODE);
                }

            }

            case "entrance" -> {
                if (args.length != 4) {
                    cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.STUDENT_TO_ENTRANCE);
                    return;
                }
                if (!args[2].equals("card")) {
                    cli.getRenderer().showErrorMessage(ViewString.INCORRECT_FORMAT + ViewString.STUDENT_TO_ENTRANCE);
                    return;
                }

                if(cli.getGameMode()){
                    if(cli.getModel().getCurrentCharacterCard() != null && cli.getModel().getCurrentCharacterCard().getType() == CharacterCardEnumeration.STUDENT_TO_ENTRANCE){
                        Colour entranceColour = Colour.valueOf(args[1].toUpperCase(Locale.ROOT));
                        Colour cardColour = Colour.valueOf(args[3].toUpperCase(Locale.ROOT));
                        cli.getActionSender().setColourCardEntrance(cli.getPlayerName(), cardColour, entranceColour);
                    } else{
                        if(cli.getModel().getCurrentCharacterCard() == null || cli.getModel().getCurrentCharacterCard().getType() == CharacterCardEnumeration.BASIC_STATE){
                            cli.getRenderer().showErrorMessage(ViewString.NO_ACTIVE_CHARACTER_CARD);
                        } else{
                            cli.getRenderer().showErrorMessage(ViewString.NO_METHOD);
                        }
                    }
                } else {
                    cli.getRenderer().showErrorMessage(ViewString.GAME_MODE);
                }
            }

            default -> cli.getRenderer().showErrorMessage(ViewString.INCORRECT_COMMAND);
        }


    }

    /**
     * Calls the method to make the player see all the possible commands.
     */
    public void help(String[] args) {
        cli.getRenderer().help();
    }

}
