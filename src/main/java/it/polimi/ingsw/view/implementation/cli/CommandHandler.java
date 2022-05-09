package it.polimi.ingsw.view.implementation.cli;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.implementation.cli.utils.ViewString;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CommandHandler {
    private final CLI cli;

    /**
     * Creates a new CommandHandler for the given CLI.
     * @param cli the cli to be associated to this CommandHandler
     */
    public CommandHandler(CLI cli) {
        this.cli = cli;
    }

    public String extractCommand(String str){
        return str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1);
    }


    /**
     * The main method. It parses the input, checking if it corresponds to a possible move, and in this case it calls it.
     * @param command user input in the CLI
     * @throws IllegalArgumentException if the input does not match with any possible player action
     */
    public void handle(String command) throws IllegalArgumentException {
        if (command == null)
            throw new IllegalArgumentException("Command can't be null");
        if (command.trim().equals(""))
            throw new IllegalArgumentException("Command can't be empty");

        String[] split = command.split(" ");
        String cmd = split[0];
        String[] args = new String[0];
        if (cmd.equals("view")) {
            split[1] = extractCommand(split[1]);
            for (int i = 2; i < split.length; i++) {
                split[1] += extractCommand(split[i]);
            }
            cmd = cmd.concat(split[1]);
            args = null;
        } else if(cmd.equals("play")){
            split[1] = extractCommand(split[1]);
            for (int i = 2; i < 3; i++) {
                split[1] += extractCommand(split[i]);
            }
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
            throw new IllegalArgumentException("Command does not exists.");
        }
    }

    /**
     * Calls the method to make the player see the character cards.
     */
    public void viewCharacterCards() {
        if(cli.getGameMode()){
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
        cli.getRenderer().printLocalPlayerCurrentAssistantCard();
    }

    /**
     * Calls the method to make the player see their assistant cards.
     */
    public void viewCurrentCharacterCard() {
        if(cli.getGameMode()){
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
    public void viewProfessors(){
        cli.getRenderer().printTableProfessors();
    }

    /**
     * Calls the method to make the player see the local player coins.
     */
    public void viewCoins() {
        if(cli.getGameMode()){
            cli.getRenderer().printLocalPlayerCoins();
        } else {
            System.out.println(ViewString.GAME_MODE);
        }
    }

    /**
     * Calls the method to make the player see coins on the table.
     */
    public void viewBank() {
        if(cli.getGameMode()){
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
     * @param args the decomposed user command
     */
    public void spy(String[] args) {
        if (args.length < 2) {
            System.out.println(ViewString.INCORRECT_FORMAT + ViewString.SPY);
        }
        String playerName = args[0];
        String object = args[1];
        for(int i = 2; i < args.length; i++){
            object += extractCommand(args[i]);
        }

        switch (object) {
            case "schoolBoard" -> cli.getRenderer().printOthersSchoolBoard(playerName);
            case "currentAssistantCard" -> cli.getRenderer().printOthersCurrentAssistantCard(playerName);
            case "coins" -> {
                if(cli.getGameMode()){
                    cli.getRenderer().printOthersCoins(playerName);
                } else {
                    System.out.println(ViewString.GAME_MODE);
                }
            }
        }
    }


    /**
     * Checks if the arguments are correct and then calls for the action sender to send a "move" action event.
     * @param args the decomposed user command
     */
    public void move(String[] args) {
        if (args.length < 2) {
            System.out.println(ViewString.INCORRECT_FORMAT);
        }

        String command = "move";
        String[] arguments = null;

        switch (args[0]) {
            case "mother" -> {
                if (args.length != 3) {
                    System.out.println(ViewString.INCORRECT_FORMAT + ViewString.MOVE_MOTHER_NATURE_STEPS);
                }
                command += extractCommand(args[0]);
                command += extractCommand(args[1]);
                arguments = Arrays.copyOfRange(args, 2, args.length);
            }
            case "student" -> {
                if (args.length < 4) {
                    System.out.println(ViewString.INCORRECT_FORMAT + ViewString.MOVE_MOTHER_NATURE_STEPS);
                }

                for (int i = 0; i < 4; i++) {
                    command += extractCommand(args[i]);
                }

                arguments = Arrays.copyOfRange(args, 4, args.length);
            }
        }

        try {
            Method cmdHandler;
            if (arguments != null) {
                cmdHandler = getClass().getMethod(command, arguments.getClass());
                cmdHandler.invoke(this, (Object) arguments);
            }
        } catch (NoSuchMethodException | SecurityException |
                IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new IllegalArgumentException("Command does not exists.");
        }
    }

    /**
     * Checks if the arguments are correct and then calls for the action sender to send a "move mother nature" action event.
     * @param args the decomposed user command
     */
    public void moveMotherNature(String[] args){
        try {
            int steps = Integer.parseInt(args[0]);
            cli.getActionSender().moveMotherNature(cli.getPlayerName(), steps);
        } catch (NumberFormatException e) {
            System.out.println(ViewString.INCORRECT_FORMAT + ViewString.MOVE_MOTHER_NATURE_STEPS);
        }
    }

    /**
     * Checks if the arguments are correct and then calls for the action sender to send a "move student to single island" action event.
     * @param args the decomposed user command
     */
    public void moveStudentToSingleIsland(String[] args){
        if (args.length != 3) {
            System.out.println(ViewString.INCORRECT_FORMAT + ViewString.MOVE_STUDENT_TO_ISLAND);
        }

        try {
            Colour colour = Colour.valueOf(args[0]);
            int groupIsland = Integer.parseInt(args[1]);
            int singleIsland = Integer.parseInt(args[2]);
            cli.getActionSender().moveStudentToIsland(cli.getPlayerName(), colour, groupIsland, singleIsland);
        } catch (NumberFormatException e) {
            System.out.println(ViewString.INCORRECT_FORMAT + ViewString.MOVE_MOTHER_NATURE_STEPS);
        }

    }

    /**
     * Checks if the arguments are correct and then calls for the action sender to send a "move student to dining room" action event.
     * @param args the decomposed user command
     */
    public void moveStudentToDiningRoom(String[] args){
        if (args.length != 1) {
            System.out.println(ViewString.INCORRECT_FORMAT + ViewString.MOVE_STUDENT_TO_DINING_ROOM);
        }

        Colour colour = Colour.valueOf(args[0]);
        cli.getActionSender().moveStudentToDiningRoom(cli.getPlayerName(), colour);

    }

    /**
     * Checks if the arguments are correct and then calls for the action sender to send a "draw" action event.
     * @param args the decomposed user command
     */
    public void playAssistantCard(String[] args) {
        if (args.length != 1) {
            System.out.println(ViewString.INCORRECT_FORMAT + ViewString.PLAY_ASSISTANT_CARD);
        }

        try {
            int card = Integer.parseInt(args[3]);
            cli.getActionSender().playAssistantCard(cli.getPlayerName(), card);
        } catch (NumberFormatException e) {
            System.out.println(ViewString.INCORRECT_FORMAT + ViewString.PLAY_ASSISTANT_CARD);
        }
    }

    /**
     * Checks if the arguments are correct and then calls for the action sender to send a "draw" action event.
     * @param args the decomposed user command
     */
    public void playCharacterCard(String[] args) {
        if(cli.getGameMode()){
            if (args.length != 1) {
                System.out.println(ViewString.INCORRECT_FORMAT + ViewString.PLAY_CHARACTER_CARD);
                return;
            }

            try {
                int card = Integer.parseInt(args[3]);
                cli.getActionSender().playCharacterCard(cli.getPlayerName(), card);
            } catch (NumberFormatException e) {
                System.out.println(ViewString.INCORRECT_FORMAT + ViewString.PLAY_CHARACTER_CARD);
            }
        } else {
            System.out.println(ViewString.GAME_MODE);
        }
    }

    /**
     * Checks if the arguments are correct and then calls for the action sender to send a "choose cloud tile" action event.
     * @param args the decomposed user command
     */
    public void choose(String[] args){
        if (args.length != 1) {
            System.out.println(ViewString.INCORRECT_FORMAT + ViewString.CHOOSE_CLOUD_TILE);
            return;
        }

        try {
            int cloudTile = Integer.parseInt(args[3]);
            cli.getActionSender().chooseCloudTile(cli.getPlayerName(), cloudTile);
        } catch (NumberFormatException e) {
            System.out.println(ViewString.INCORRECT_FORMAT + ViewString.PLAY_CHARACTER_CARD);
        }
    }

    /**
     * Checks if the arguments are correct and then calls for the action sender to send a "select" action event.
     * @param args the decomposed user command
     */
    public void select(String[] args){
        if (args.length < 1) {
            System.out.println(ViewString.INCORRECT_FORMAT);
            return;
        }

        switch (args[0]) {
            case "student" -> {
                if (args.length != 2) {
                    System.out.println(ViewString.INCORRECT_FORMAT + ViewString.SELECT_STUDENT_COLOUR);
                    return;
                }
                Colour colour = Colour.valueOf(args[1]);
                cli.getActionSender().setColour(cli.getPlayerName(), colour);

            }

            case "group" -> {
                if (args.length != 3) {
                    System.out.println(ViewString.INCORRECT_FORMAT + ViewString.SELECT_GROUP_ISLAND);
                    return;
                }
                if (args[1] != "island") {
                    System.out.println(ViewString.INCORRECT_FORMAT + ViewString.SELECT_GROUP_ISLAND);
                    return;
                }

                try {
                    int groupIsland = Integer.parseInt(args[2]);
                    cli.getActionSender().setGroupIsland(cli.getPlayerName(), groupIsland);
                } catch (NumberFormatException e) {
                    System.out.println(ViewString.INCORRECT_FORMAT + ViewString.PLAY_CHARACTER_CARD);
                }

            }
        }

    }

    /**
     * Checks if the arguments are correct and then calls for the action sender to send a "set colour and island" action event.
     * @param args the decomposed user command
     */
    public void put(String[] args){
        if (args.length != 4) {
            System.out.println(ViewString.INCORRECT_FORMAT + ViewString.STUDENT_TO_ISLAND);
        }
        try {
            Colour colour = Colour.valueOf(args[0]);
            if(args[1] != "on"){
                System.out.println(ViewString.INCORRECT_FORMAT + ViewString.STUDENT_TO_ISLAND);
                return;
            }
            int groupIsland = Integer.parseInt(args[2]);
            int singleIsland = Integer.parseInt(args[3]);
            cli.getActionSender().setColourAndIsland(cli.getPlayerName(), colour, groupIsland, singleIsland);
        } catch (NumberFormatException e) {
            System.out.println(ViewString.INCORRECT_FORMAT + ViewString.STUDENT_TO_ISLAND);
        }
    }

    /**
     * Checks if the arguments are correct and then calls for the action sender to send "exchange" action event.
     * @param args the decomposed user command
     */
    public void exchange(String[] args){
        if (args.length < 1) {
            System.out.println(ViewString.INCORRECT_FORMAT);
            return;
        }

        switch (args[0]) {
            case "dining" -> {
                if (args.length != 5) {
                    System.out.println(ViewString.INCORRECT_FORMAT + ViewString.EXCHANGE_DINING_ROOM_ENTRANCE);
                    return;
                }

                if (args[1] != "room") {
                    System.out.println(ViewString.INCORRECT_FORMAT + ViewString.EXCHANGE_DINING_ROOM_ENTRANCE);
                    return;
                }

                if (args[3] != "entrance") {
                    System.out.println(ViewString.INCORRECT_FORMAT + ViewString.EXCHANGE_DINING_ROOM_ENTRANCE);
                    return;
                }

                Colour colourDiningRoom = Colour.valueOf(args[2]);
                Colour colourEntrance = Colour.valueOf(args[4]);
                cli.getActionSender().setColourDiningRoomEntrance(cli.getPlayerName(), colourDiningRoom, colourEntrance);

            }

            case "entrance" -> {
                if (args.length != 4) {
                    System.out.println(ViewString.INCORRECT_FORMAT + ViewString.STUDENT_TO_ENTRANCE);
                    return;
                }
                if (args[2] != "card") {
                    System.out.println(ViewString.INCORRECT_FORMAT + ViewString.STUDENT_TO_ENTRANCE);
                    return;
                }

                Colour entranceColour = Colour.valueOf(args[1]);
                Colour cardColour = Colour.valueOf(args[3]);
                cli.getActionSender().setColourCardEntrance(cli.getPlayerName(), cardColour, entranceColour);
            }
        }


    }

    /**
     * Calls the method to make the player see all the possible commands.
     */
    public void help(String[] args) {
        cli.getRenderer().help();
    }

}
