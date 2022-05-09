package it.polimi.ingsw.view;


import it.polimi.ingsw.view.beans.MockPlayer;

import java.util.Map;

/**
 * Class responsible for game components rendering, mainly used for the CLI.
 */
public abstract class Renderer {
    protected final View view;

    protected Renderer(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public abstract void showGameMessage(String message);

    public abstract void showLobbyMessage(String message);

    public abstract void showErrorMessage(String message);

    public abstract void printLocalPlayerSchoolBoard();

    public abstract void printLocalPlayerCoins();

    public abstract void printLocalPlayerCurrentAssistantCard();

    public abstract void printAvailableAssistantCards();

    public abstract void printIslands();

    public abstract void printCloudTiles();

    public abstract void printActiveCharacterCard();

    public abstract void printCharacterCards();

    public abstract void printTableCoins();

    public abstract void printTableProfessors();

    public abstract void printOthersCoins(String playerName);

    public abstract void printOthersSchoolBoard(String playerName);

    public abstract void printOthersCurrentAssistantCard(String playerName);

    public abstract void printResult();

    public abstract void help();
}
