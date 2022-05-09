package it.polimi.ingsw.view.implementation.cli;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.player.SchoolBoard;
import it.polimi.ingsw.view.beans.*;
import it.polimi.ingsw.view.implementation.cli.utils.AnsiColour;

import it.polimi.ingsw.view.Renderer;
import it.polimi.ingsw.view.View;

import java.util.HashMap;

public class CLIRenderer extends Renderer {

    /**
     * Creates a new CLIRenderer for the given View.
     * @param view the cli to be associated with this CLIRenderer
     */
    protected CLIRenderer(View view) {
        super(view);
    }

    public View getView() {
        return view;
    }

    public void showGameMessage(String message){
        System.out.println(AnsiColour.BLUE + "[" + AnsiColour.italicize("TO:You") + AnsiColour.BLUE + "] " + message + AnsiColour.RESET);
    }

    public void showLobbyMessage(String message){
        System.out.println(AnsiColour.GREEN + "[" + AnsiColour.italicize("TO:Lobby") + AnsiColour.GREEN + "] " + message + AnsiColour.RESET);

    }

    public void showErrorMessage(String message){
        System.out.println(AnsiColour.RED + "[" + AnsiColour.italicize("ERROR") + AnsiColour.RED + "] " + message + AnsiColour.RESET);

    }

    public void printLocalPlayerSchoolBoard(){
        String schoolBoard = "";
        renderSchoolBoard(view.getModel().getLocalPlayer().getSchoolBoard(), schoolBoard);
    }

    public void printLocalPlayerCoins(){
        String numberCoins = "";
        renderCoins(view.getModel().getLocalPlayer().getCoins(), numberCoins);
    }

    public void printLocalPlayerCurrentAssistantCard(){
        String assistantCard = "";
        renderAssistantCard(view.getModel().getLocalPlayer().getCurrentAssistantCard().getValue(),
                view.getModel().getLocalPlayer().getCurrentAssistantCard().getMotherNatureMovement(), assistantCard);
    }

    public void printAvailableAssistantCards(){
        String assistantCard = "";
        for (AssistantCard card:  view.getModel().getLocalPlayer().getCards()) {
            renderAssistantCard(card.getValue(), card.getMotherNatureMovement(), assistantCard);
        }
    }

    public void printIslands(){
        String island = "";
        int i = 0;

        for (MockGroupIsland groupIsland: view.getModel().getTable().getGroupIslands()) {
            island = island.concat("GroupIsland " + i);

            for(MockSingleIsland singleIsland: groupIsland.getIslands()){
                for(Colour colour : Colour.values()){
                    island = island.concat("\n\t" +
                            AnsiColour.getStudentColour(colour) + colour.name() +": " + singleIsland.getStudents(colour) + AnsiColour.RESET);
                }
            }

            i++;
            island = island.concat("\n");
        }

        System.out.println(island);
    }

    public void printCloudTiles(){
        String cloudTile = "";
        int i = 0;

        for (MockCloudTile cloud : view.getModel().getTable().g) {
            island = island.concat("GroupIsland " + i);

            for(MockSingleIsland singleIsland: groupIsland.getIslands()){
                for(Colour colour : Colour.values()){
                    island = island.concat("\n\t" +
                            AnsiColour.getStudentColour(colour) + colour.name() +": " + singleIsland.getStudents(colour) + AnsiColour.RESET);
                }
            }

            i++;
            island = island.concat("\n");
        }

        System.out.println(island);

    }

    public void printActiveCharacterCard(){

    }

    public void printCharacterCards(){

    }

    public void printTableCoins(){

    }

    public void printTableProfessors(){

    }

    public void printOthersCoins(String playerName){
        String numberCoins = playerName + "\n";
        renderCoins(view.getModel().getPlayerByNickname(playerName).getCoins(), numberCoins);
    }

    public void printOthersSchoolBoard(String playerName){
        String schoolBoard = playerName + "\n";
        renderSchoolBoard(view.getModel().getPlayerByNickname(playerName).getSchoolBoard(), schoolBoard);
    }

    public void printOthersCurrentAssistantCard(String playerName){
        String assistantCard = playerName + "\n";
        renderAssistantCard(view.getModel().getPlayerByNickname(playerName).getCurrentAssistantCard().getValue(),
                view.getModel().getPlayerByNickname(playerName).getCurrentAssistantCard().getMotherNatureMovement(), assistantCard);
    }

    public void printResult(){

    }

    public void help(){

    }

    private void renderSchoolBoard(MockSchoolBoard mockSchoolBoard,  String schoolBoard){
        HashMap<Colour, Integer> entrance = mockSchoolBoard.getEntrance();
        HashMap<Colour, Integer> diningRoom = mockSchoolBoard.getDiningRoom();
        HashMap<Colour, Boolean> professor = mockSchoolBoard.getProfessorTable();

        schoolBoard = schoolBoard.concat(AnsiColour.bold("Entrance: "));

        for(Colour colour : Colour.values()){
            schoolBoard = schoolBoard.concat("\n\t" +
                    AnsiColour.getStudentColour(colour) + colour.name() +": " + entrance.get(colour) + AnsiColour.RESET);
        }

        schoolBoard = schoolBoard.concat("\n" + AnsiColour.bold("Dining Room: "));

        for(Colour colour : Colour.values()){
            schoolBoard = schoolBoard.concat("\n\t" +
                    AnsiColour.getStudentColour(colour) + colour.name() +": " + diningRoom.get(colour) + AnsiColour.RESET);
        }

        schoolBoard = schoolBoard.concat("\n"+  AnsiColour.bold("Professors: "));

        for(Colour colour : Colour.values()){
            schoolBoard = schoolBoard.concat("\n\t" +
                    AnsiColour.getStudentColour(colour) + colour.name() +": " + professor.get(colour) + AnsiColour.RESET);
        }

        schoolBoard = schoolBoard.concat("\n" + AnsiColour.bold("Towers: ") + view.getModel().getLocalPlayer().getSchoolBoard().getTowers());


        System.out.println(schoolBoard);

    }

    private void renderCoins(int coins,  String numberCoins){
        numberCoins = numberCoins.concat(AnsiColour.GOLD + "Coins: " + coins);
        System.out.println(numberCoins);
    }

    private void renderAssistantCard(int value, int steps,  String assistantCard){
        assistantCard = assistantCard.concat("Value: " + value + "\n" + "Steps: " + steps);
        System.out.println(assistantCard);
    }
}
