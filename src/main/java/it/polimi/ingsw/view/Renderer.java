package it.polimi.ingsw.view;


/**
 * Class responsible for game components rendering, mainly used for the CLI.
 */
public abstract class Renderer {
    protected final View view;

    /**
     * Constructs the renderer
     *
     * @param view the view related to this renderer
     */
    protected Renderer(View view) {
        this.view = view;
    }

    /**
     * Gets the view related to this renderer
     *
     * @return the view related to that renderer
     */
    public View getView() {
        return view;
    }

    /**
     * Shows a game message
     *
     * @param message the message to show
     */
    public abstract void showGameMessage(String message);

    /**
     * Shows a lobby message
     *
     * @param message the message to show
     */
    public abstract void showLobbyMessage(String message);

    /**
     * Shows an error message
     *
     * @param message the message to show
     */
    public abstract void showErrorMessage(String message);

    /**
     * Prints the school board of the local player
     */
    public abstract void printLocalPlayerSchoolBoard();

    /**
     * Prints the coins of the local player
     */
    public abstract void printLocalPlayerCoins();

    /**
     * Prints the current assistant card of the local layer
     */
    public abstract void printLocalPlayerCurrentAssistantCard();

    /**
     * Prints the available assistant cards
     */
    public abstract void printAvailableAssistantCards();

    /**
     * Prints the islands
     */
    public abstract void printIslands();

    /**
     * Prints the available cloud tiles
     */
    public abstract void printCloudTiles();

    /**
     * Prints the active character card
     */
    public abstract void printActiveCharacterCard();

    /**
     * Prints the character cards
     */
    public abstract void printCharacterCards();

    /**
     * Prints the available table coins
     */
    public abstract void printTableCoins();

    /**
     * Prints the professors available on the table
     */
    public abstract void printTableProfessors();

    /**
     * Prints the coins of another player
     *
     * @param playerName the nickname of the player
     */
    public abstract void printOthersCoins(String playerName);

    /**
     * Prints the school board of another player
     *
     * @param playerName the nickname of the player
     */
    public abstract void printOthersSchoolBoard(String playerName);

    /**
     * Prints the current assistant card of another player
     *
     * @param playerName the nickname of the player
     */
    public abstract void printOthersCurrentAssistantCard(String playerName);

    /**
     * Prints the results of the game
     */
    public abstract void printResult();

    /**
     * Prints the available commands (useful for the CLI)
     */
    public abstract void help();

    public void printSituation() {
    }
}
