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
        cli.getRenderer().printCharacterCards();
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
    public void setViewCurrentCharacterCard() {
        cli.getRenderer().printActiveCharacterCard();
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
    public void ViewCloudTile() {
        cli.getRenderer().printCloudTiles();
    }

    /**
     * Calls the method to make the player see the professors on the table.
     */
    public void ViewProfessors(){
        cli.getRenderer().printTableProfessors();
    }

    /**
     * Calls the method to make the player see the local player coins.
     */
    public void ViewCoins() {
        cli.getRenderer().printLocalPlayerCoins();
    }

    /**
     * Calls the method to make the player see coins on the table.
     */
    public void ViewBank() {
        cli.getRenderer().printTableCoins();
    }

    /**
     * Calls the method to make the player see the winner.
     */
    public void ViewResult() {
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
            case "coins" -> cli.getRenderer().printOthersCoins(playerName);
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

        switch (args[0]) {
            case "mother" -> {
                try {
                    int steps;
                    steps = Integer.parseInt(args[3]);
                    cli.getActionSender().moveMotherNature(cli.getPlayerName(), steps);
                } catch (NumberFormatException e) {
                    System.out.println(ViewString.INCORRECT_FORMAT + ViewString.MOVE_MOTHER_NATURE_STEPS);
                    return;
                }
            }
            case "student" -> {
                Colour colour;
                colour = Colour.valueOf(args[1]);


            }
        }
    }

    /**
     * Checks if the arguments are correct and then calls for the action sender to send a "draw" action event.
     * @param args the decomposed user command
     */
    public void play(String[] args) {

    }




    /**
     * Calls the method to make the player see all the possible commands.
     */
    public void help(String[] args) {
        cli.getRenderer().help();
    }



}
