package it.polimi.ingsw.view.implementation.cli.utils;

import it.polimi.ingsw.model.Colour;

/**
 * CLass used to change the colour in the CLI
 */
public class AnsiColour {
    private static final String START_ITALICIZE = "\033[3m";
    private static final String START_BOLD = "\033[1m";

    public static final String RESET = "\u001B[0m";

    public static final String RED = "\u001B[91m";
    public static final String GREEN = "\u001B[92m";
    public static final String YELLOW = "\u001B[93m";
    public static final String BLUE = "\u001B[94m";
    public static final String PINK = "\u001B[95m";

    public static final String WHITE = "\u001B[37m";
    public static final String GREY = "\u001B[90m";
    public static final String BLACK = "\u001B[30m";

    public static final String GOLD = "\u001B[33m";

    public static String getStudentColour(Colour colour) {
        if (colour == null) return WHITE;
        return switch (colour) {
            case YELLOW -> YELLOW;
            case RED -> RED;
            case GREEN -> GREEN;
            case PINK -> PINK;
            case BLUE -> BLUE;
        };
    }

    public static String italicize(String message) {
        return START_ITALICIZE + message + RESET;
    }

    public static String bold(String message) {
        return START_BOLD + message + RESET;
    }
}
