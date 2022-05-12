package it.polimi.ingsw.view.implementation.cli;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.beans.*;
import it.polimi.ingsw.view.implementation.cli.utils.AnsiColour;

import it.polimi.ingsw.view.Renderer;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.implementation.cli.utils.ViewString;

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
            island = island.concat("Group island " + i);
            if(groupIsland.isMotherNature()){
                island = island.concat(AnsiColour.GOLD + "\nMother Nature is here!" + AnsiColour.RESET);
            }
            if(groupIsland.getInfluentPlayer() != null){
                island = island.concat("\n" + "The influent player is: " + groupIsland.getInfluentPlayer());
            }
            int j = 0;
            for(MockSingleIsland singleIsland: groupIsland.getIslands()){
                island = island.concat("\n" + "Single island " + j);
                for(Colour colour : Colour.values()){
                    island = island.concat("\n\t" +
                            AnsiColour.getStudentColour(colour) + colour.name() +": " + singleIsland.getStudents(colour) + AnsiColour.RESET);
                }
                j++;
            }

            i++;
            island = island.concat("\n");
        }

        System.out.println(island);
    }

    public void printCloudTiles(){
        String cloudTile = "";
        int i = 0;

        for (MockCloudTile cloud : view.getModel().getTable().getShownCloudTiles()) {
            cloudTile = cloudTile.concat("Cloud tile: " + i);
            HashMap<Colour, Integer> students = cloud.getMockCloudTile();

            for(Colour colour : Colour.values()){
                cloudTile = cloudTile.concat("\n\t" +
                        AnsiColour.getStudentColour(colour) + colour.name() +": " + students.get(colour) + AnsiColour.RESET);
            }

            cloudTile = cloudTile.concat("\n");
            i++;
        }

        System.out.println(cloudTile);
    }

    public void printActiveCharacterCard(){
        String character = "The active character card is: ";
        renderCharacter(view.getModel().getCurrentCharacterCard(), character);
    }

    public void printCharacterCards(){
        String character = "The character cards available are: ";
        for(int i = 0; i < 3; i++){
            character = character.concat("\nCharacter Card " + i);
            renderCharacter(view.getModel().getCharacterCardByIndex(i), character);
            character = "";

        }
    }

    public void renderCharacter(MockCard card, String character){
        character = character.concat("\n" + card.getType().name());
        character = character.concat("\n\tCost: " + card.getCost());

        HashMap<Colour, Integer> students = card.getStudents();
        if(card.getNumberOfStudentsOnTheCard() > 0){
            for(Colour colour : Colour.values()){
                character = character.concat("\n\t" +
                        AnsiColour.getStudentColour(colour) + colour.name() +": " + students.get(colour) + AnsiColour.RESET);
            }
        }

        if(card.getType() == CharacterCardEnumeration.PROTECT_ISLAND){
            character = character.concat("\n\tThe number of entry tiles available is: " + card.getNumberOfNoEntryTile());
        }

        System.out.println(character);
    }

    public void printTableCoins(){
        String coins = "";
        coins = coins.concat("Available coins: " + view.getModel().getCoins());
        System.out.println(coins);
    }

    public void printTableProfessors(){
        String professors = "";
        professors = professors.concat("Available professors: ");
        HashMap<Colour, Boolean> prof =  view.getModel().getTable().getProfessorsAvailable();


        for(Colour colour : Colour.values()){
            professors = professors.concat("\n\t" +
                    AnsiColour.getStudentColour(colour) + colour.name() +": "  + prof.get(colour) + AnsiColour.RESET);
        }
        System.out.println(professors);
    }

    public void printOthersCoins(String playerName){
        String numberCoins = view.getModel().getPlayerByNickname(playerName).getNickname() + "\n";
        renderCoins(view.getModel().getPlayerByNickname(playerName).getCoins(), numberCoins);
    }

    public void printOthersSchoolBoard(String playerName){
        String schoolBoard = view.getModel().getPlayerByNickname(playerName).getNickname() + "\n";
        renderSchoolBoard(view.getModel().getPlayerByNickname(playerName).getSchoolBoard(), schoolBoard);
    }

    public void printOthersCurrentAssistantCard(String playerName){
        String assistantCard = view.getModel().getPlayerByNickname(playerName).getNickname() + "\n";
        renderAssistantCard(view.getModel().getPlayerByNickname(playerName).getCurrentAssistantCard().getValue(),
                view.getModel().getPlayerByNickname(playerName).getCurrentAssistantCard().getMotherNatureMovement(), assistantCard);
    }

    public void printResult(){
        System.out.println("The winner is: " + view.getModel().getWinner().getNickname());
    }


    /**
     * Prints all the possible commands that the player can type in the CLI.
     */
    @Override
    public void help() {
        int index = 1;
        for (String command : ViewString.getCommands()) {
            System.out.println(index + ") " + AnsiColour.GREEN + command + AnsiColour.RESET);
            index++;
        }
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

        schoolBoard = schoolBoard.concat("\n" + AnsiColour.bold("Towers: ") + mockSchoolBoard.getTowers());


        System.out.println(schoolBoard);

    }

    private void renderCoins(int coins,  String numberCoins){
        numberCoins = numberCoins.concat(AnsiColour.GOLD + "Coins: " + coins + AnsiColour.RESET);
        System.out.println(numberCoins);
    }

    private void renderAssistantCard(int value, int steps,  String assistantCard){
        assistantCard = assistantCard.concat("Assistant Card number: " + (value - 1) + "\n\tValue: " + value + "\n\t" + "Steps: " + steps);
        System.out.println(assistantCard);
    }
}
