package it.polimi.ingsw.view.implementation.cli;

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
            split[1] = split[1].substring(0, 1).toUpperCase(Locale.ROOT) + split[1].substring(1);
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
        // cli.getRenderer().print();
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
     * Calls the method to make the player see the winner.
     */
    public void ViewWinner() {
        // cli.getRenderer().print();
    }




    public void help() {
    }



}
