package it.polimi.ingsw.view;


import it.polimi.ingsw.view.beans.MockPlayer;

import java.util.Map;

/**
 * Class responsible of game components rendering, mainly used for the CLI.
 */
public abstract class Renderer {
    private final View view;

    protected Renderer(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void showGameMessage(String message) {
    }

    public void showLobbyMessage(String message) {
    }

    public abstract void showErrorMessage(String message);

    public void printLocalPlayerSchoolBoard() {
    }

    public void printLocalPlayerCoins(){
    }

    public void printLocalPlayerCurrentAssistantCard(){
    }

    public void printAvailableAssistantCards(){
    }

    public void printIslands() {
    }

    public void printCloudTiles() {
    }

    public void printActiveCharacterCard() {
    }

    public void printCharacterCards() {
    }

    public void printTableCoins() {
    }

    public void printTableProfessors(){
    }

    public void printOthersCoins(String playerName){
    }

    public void printOthersSchoolBoard(String playerName) {
    }

    public void printOthersCurrentAssistantCard(String playerName) {
    }


    public void printResult(){
    }

    public void help() {
    }
}
