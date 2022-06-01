package it.polimi.ingsw.view.implementation.cli;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.Renderer;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.beans.*;
import it.polimi.ingsw.view.implementation.cli.utils.ASCIIArt;
import it.polimi.ingsw.view.implementation.cli.utils.AnsiColour;
import it.polimi.ingsw.view.implementation.cli.utils.ViewString;

import java.util.*;

public class CLIRenderer extends Renderer {

    /**
     * Creates a new CLIRenderer for the given View.
     *
     * @param view the cli to be associated with this CLIRenderer
     */
    protected CLIRenderer(View view) {
        super(view);
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
    public void showGameMessage(String message) {
        System.out.println(AnsiColour.BLUE + "[" + AnsiColour.italicize("TO:You") + AnsiColour.BLUE + "] " + message + AnsiColour.RESET);
    }

    /**
     * Shows a lobby message
     *
     * @param message the message to show
     */
    public void showLobbyMessage(String message) {
        System.out.println(AnsiColour.GREEN + "[" + AnsiColour.italicize("TO:Lobby") + AnsiColour.GREEN + "] " + message + AnsiColour.RESET);

    }

    /**
     * Shows an error message
     *
     * @param message the message to show
     */
    public void showErrorMessage(String message) {
        System.out.println(AnsiColour.RED + "[" + AnsiColour.italicize("ERROR") + AnsiColour.RED + "] " + message + AnsiColour.RESET);

    }

    /**
     * Prints the school board of the local player
     */
    public void printLocalPlayerSchoolBoard() {
        String schoolBoard = "";
        renderSchoolBoard(view.getModel().getLocalPlayer().getSchoolBoard(), schoolBoard);
    }

    /**
     * Prints the coins of the local player
     */
    public void printLocalPlayerCoins() {
        String numberCoins = "";
        renderCoins(view.getModel().getLocalPlayer().getCoins(), numberCoins);
    }

    /**
     * Prints the current assistant card of the local layer
     */
    public void printLocalPlayerCurrentAssistantCard() {
        String assistantCard = "";
        renderAssistantCard(view.getModel().getLocalPlayer().getCurrentAssistantCard().getValue(),
                view.getModel().getLocalPlayer().getCurrentAssistantCard().getMotherNatureMovement(), assistantCard);
    }

    /**
     * Prints the available assistant cards
     */
    public void printAvailableAssistantCards() {
        String assistantCard = "";
        view.getModel().getLocalPlayer().getCards()
                .forEach((key, value) -> renderAssistantCard(value.getValue(), value.getMotherNatureMovement(), assistantCard));
    }

    /**
     * Prints the islands
     */
    public void printIslands() {
        System.out.println("\u001b[2J");

        int count = 0;
        Formatter formatter = new Formatter();
        List<String> groupIslandText1 = new ArrayList<>();
        List<String> groupIslandText2 = new ArrayList<>();
        List<String> influenceText1 = new ArrayList<>();
        List<String> influenceText2 = new ArrayList<>();
        List<String> motherNatureText1 = new ArrayList<>();
        List<String> motherNatureText2 = new ArrayList<>();
        List<String> colour11 = new ArrayList<>();
        List<String> colour21 = new ArrayList<>();
        List<String> colour12 = new ArrayList<>();
        List<String> colour22 = new ArrayList<>();

        int i = 0;
        for (MockGroupIsland groupIsland : view.getModel().getTable().getGroupIslands()) {
            String influence = " ";
            if(groupIsland.getInfluentPlayer() != null){
                switch (getView().getModel().getPlayerByNickname(groupIsland.getInfluentPlayer()).getTowerColour()){
                    case WHITE -> influence = "\u001b[47;1m◯\u001b[0m";
                    case BLACK -> influence = "\u001b[30m◯\u001b[0m";
                    case GREY -> influence = "\u001b[30; 1m◯\u001b[0m";
                }
            }
            if(count < 6){
                influenceText1.add(influence);
            } else{
                influenceText2.add(influence);
            }

            String entryTile = " ";
            if(!groupIsland.getIsBasic()){
                if(groupIsland.getNoEntryTile() > 0){
                   entryTile = "%d".formatted(groupIsland.getNoEntryTile());
                }
            }
            if(count < 6){
                influenceText1.add(entryTile);
            } else{
                influenceText2.add(entryTile);
            }


            String motherNature = " ";
            if(groupIsland.isMotherNature()){
                motherNature = "\u001b[31m◯\u001b[0m";
            }
            if(count < 6){
                motherNatureText1.add(motherNature);
            } else{
                motherNatureText2.add(motherNature);
            }

            int j = 0;
            for (MockSingleIsland singleIsland : groupIsland.getIslands()) {
                if(count < 6){
                    groupIslandText1.add(i + ", " + j);
                } else{
                    groupIslandText2.add(i + ", " + j);
                }

                if(j > 0){
                    influence = " ";
                    if(groupIsland.getInfluentPlayer() != null){
                        switch (getView().getModel().getPlayerByNickname(groupIsland.getInfluentPlayer()).getTowerColour()){
                            case WHITE -> influence = "\u001b[47;1m◯\u001b[0m";
                            case BLACK -> influence = "\u001b[30m◯\u001b[0m";
                            case GREY -> influence = "\u001b[30; 1m◯\u001b[0m";
                        }
                    }
                    if(count < 6){
                        influenceText1.add(influence);
                    } else{
                        influenceText2.add(influence);
                    }

                    entryTile = " ";
                    if(count < 6){
                        influenceText1.add(entryTile);
                    } else{
                        influenceText2.add(entryTile);
                    }

                    motherNature = " ";
                    if(count < 6){
                        motherNatureText1.add(motherNature);
                    } else{
                        motherNatureText2.add(motherNature);
                    }
                }

                if(count < 6){
                    Colour(colour11, colour21, singleIsland);
                } else{
                    Colour(colour12, colour22, singleIsland);
                }

                j++;
                count ++;
            }

            i++;

            if(count == 12){
                List<String> total1 = createTotal(groupIslandText1, influenceText1, motherNatureText1, colour11, colour21);
                List<String> total2 =createTotal(groupIslandText2, influenceText2, motherNatureText2, colour12, colour22);
                List<String> total = new ArrayList<>();
                total.addAll(total1);
                total.addAll(total2);
                System.out.println(formatter.format(ASCIIArt.ISLAND, total.toArray()));

            }
        }

        System.out.print("\033[H\033[2J");
        System.out.flush();

    }

    private List<String> createTotal(List<String> groupIslandText1, List<String> influenceText1, List<String> motherNatureText1, List<String> colour11, List<String> colour21) {
        List<String> total1 = new ArrayList<>();
        total1.addAll(groupIslandText1);
        total1.addAll(influenceText1);
        total1.addAll(motherNatureText1);
        total1.addAll(colour11);
        total1.addAll(colour21);
        return total1;
    }

    private void Colour(List<String> colour11, List<String> colour21, MockSingleIsland singleIsland) {
        colour11.add("\u001b[41;1m %d \u001b[0m".formatted(singleIsland.getStudents(Colour.RED)));
        colour11.add("\u001b[42;1m %d \u001b[0m".formatted(singleIsland.getStudents(Colour.GREEN)));
        colour11.add("\u001b[43;1m %d \u001b[0m".formatted(singleIsland.getStudents(Colour.YELLOW)));
        colour21.add("\u001b[44;1m %d \u001b[0m".formatted(singleIsland.getStudents(Colour.BLUE)));
        colour21.add("\u001b[45;1m %d \u001b[0m".formatted(singleIsland.getStudents(Colour.PINK)));
    }

    /**
     * Prints the available cloud tiles
     */
    public void printCloudTiles() {
        String cloudTile = "";
        int i = 0;

        for (MockCloudTile cloud : view.getModel().getTable().getShownCloudTiles()) {
            cloudTile = cloudTile.concat("Cloud tile: " + i);
            HashMap<Colour, Integer> students = cloud.getMockCloudTile();

            for (Colour colour : Colour.values()) {
                cloudTile = cloudTile.concat("\n\t" +
                        AnsiColour.getStudentColour(colour) + colour.name() + ": " + students.get(colour) + AnsiColour.RESET);
            }

            cloudTile = cloudTile.concat("\n");
            i++;
        }

        System.out.println(cloudTile);
    }

    /**
     * Prints the active character card
     */
    public void printActiveCharacterCard() {
        String character = "The active character card is: ";
        if (view.getModel().getCurrentCharacterCard() != null) {
            renderCharacter(view.getModel().getCurrentCharacterCard(), character);
        } else {
            showGameMessage(ViewString.NO_ACTIVE_CHARACTER_CARD);
        }
    }

    /**
     * Prints the character cards
     */
    public void printCharacterCards() {
        String character = "The character cards available are: ";
        for (int i = 0; i < 3; i++) {
            character = character.concat("\nCharacter Card " + i);
            renderCharacter(view.getModel().getCharacterCardByIndex(i), character);
            character = "";

        }
    }

    /**
     * Prints the selected character card
     *
     * @param card      the card to print
     * @param character the name of the character card
     */
    public void renderCharacter(MockCard card, String character) {
        character = character.concat("\n" + card.getType().name());
        character = character.concat("\n\tCost: " + card.getCost());

        HashMap<Colour, Integer> students = card.getStudents();
        if (card.getNumberOfStudentsOnTheCard() > 0) {
            for (Colour colour : Colour.values()) {
                character = character.concat("\n\t" +
                        AnsiColour.getStudentColour(colour) + colour.name() + ": " + students.get(colour) + AnsiColour.RESET);
            }
        }

        if (card.getType() == CharacterCardEnumeration.PROTECT_ISLAND) {
            character = character.concat("\n\tThe number of entry tiles available is: " + card.getNumberOfNoEntryTile());
        }

        System.out.println(character);
    }

    /**
     * Prints the available table coins
     */
    public void printTableCoins() {
        String coins = "";
        coins = coins.concat("Available coins: " + view.getModel().getCoins());
        System.out.println(coins);
    }

    /**
     * Prints the professors available on the table
     */
    public void printTableProfessors() {
        String professors = "";
        professors = professors.concat("Available professors: ");
        HashMap<Colour, Boolean> prof = view.getModel().getTable().getProfessorsAvailable();


        for (Colour colour : Colour.values()) {
            professors = professors.concat("\n\t" +
                    AnsiColour.getStudentColour(colour) + colour.name() + ": " + prof.get(colour) + AnsiColour.RESET);
        }
        System.out.println(professors);
    }

    /**
     * Prints the coins of another player
     *
     * @param playerName the nickname of the player
     * @throws IllegalArgumentException if the nickname typed is not present in this game
     */
    public void printOthersCoins(String playerName) throws IllegalArgumentException {
        String numberCoins = view.getModel().getPlayerByNickname(playerName).getNickname() + "\n";
        renderCoins(view.getModel().getPlayerByNickname(playerName).getCoins(), numberCoins);
    }

    /**
     * Prints the school board of another player
     *
     * @param playerName the nickname of the player
     * @throws IllegalArgumentException if the nickname typed is not present in this game
     */
    public void printOthersSchoolBoard(String playerName) throws IllegalArgumentException {
        String schoolBoard = view.getModel().getPlayerByNickname(playerName).getNickname() + "\n";
        renderSchoolBoard(view.getModel().getPlayerByNickname(playerName).getSchoolBoard(), schoolBoard);
    }

    /**
     * Prints the current assistant card of another player
     *
     * @param playerName the nickname of the player
     * @throws IllegalArgumentException if the nickname typed is not present in this game
     */
    public void printOthersCurrentAssistantCard(String playerName) throws IllegalArgumentException {
        String assistantCard = view.getModel().getPlayerByNickname(playerName).getNickname() + "\n";
        renderAssistantCard(view.getModel().getPlayerByNickname(playerName).getCurrentAssistantCard().getValue(),
                view.getModel().getPlayerByNickname(playerName).getCurrentAssistantCard().getMotherNatureMovement(), assistantCard);
    }

    /**
     * Prints the results of the game
     */
    public void printResult() {
        if (view.getModel().getWinner() == null) {
            showGameMessage("The game is not ended yet, so there is no winner");
        } else {
            showGameMessage("The winner is: " + view.getModel().getWinner().getNickname());
        }
    }


    /**
     * Prints all the possible commands that the player can type in the CLI.
     */
    @Override
    public void help() {
        int index = 1;
        if (getView().getGameMode()) {
            for (String command : ViewString.getCommandsExpert()) {
                System.out.println(index + ") " + AnsiColour.GREEN + command + AnsiColour.RESET);
                index++;
            }
        } else {
            for (String command : ViewString.getCommandsBasic()) {
                System.out.println(index + ") " + AnsiColour.GREEN + command + AnsiColour.RESET);
                index++;
            }
        }
    }

    /**
     * Renders the school board given
     *
     * @param mockSchoolBoard the school board to print
     * @param schoolBoard     the string to print
     */
    private void renderSchoolBoard(MockSchoolBoard mockSchoolBoard, String schoolBoard) {
        HashMap<Colour, Integer> entrance = mockSchoolBoard.getEntrance();
        HashMap<Colour, Integer> diningRoom = mockSchoolBoard.getDiningRoom();
        HashMap<Colour, Boolean> professor = mockSchoolBoard.getProfessorTable();

        schoolBoard = schoolBoard.concat(AnsiColour.bold("Entrance: "));

        for (Colour colour : Colour.values()) {
            schoolBoard = schoolBoard.concat("\n\t" +
                    AnsiColour.getStudentColour(colour) + colour.name() + ": " + entrance.get(colour) + AnsiColour.RESET);
        }

        schoolBoard = schoolBoard.concat("\n" + AnsiColour.bold("Dining Room: "));

        for (Colour colour : Colour.values()) {
            schoolBoard = schoolBoard.concat("\n\t" +
                    AnsiColour.getStudentColour(colour) + colour.name() + ": " + diningRoom.get(colour) + AnsiColour.RESET);
        }

        schoolBoard = schoolBoard.concat("\n" + AnsiColour.bold("Professors: "));

        for (Colour colour : Colour.values()) {
            schoolBoard = schoolBoard.concat("\n\t" +
                    AnsiColour.getStudentColour(colour) + colour.name() + ": " + professor.get(colour) + AnsiColour.RESET);
        }

        schoolBoard = schoolBoard.concat("\n" + AnsiColour.bold("Towers: ") + mockSchoolBoard.getTowers());


        System.out.println(schoolBoard);

    }

    /**
     * Prints the coins of a player
     *
     * @param coins       the number of coins
     * @param numberCoins the string to print
     */
    private void renderCoins(int coins, String numberCoins) {
        numberCoins = numberCoins.concat(AnsiColour.GOLD + "Coins: " + coins + AnsiColour.RESET);
        System.out.println(numberCoins);
    }

    /**
     * Prints the assistant card
     *
     * @param value         the assistant card value
     * @param steps         the steps that mother nature can do
     * @param assistantCard the string to print
     */
    private void renderAssistantCard(int value, int steps, String assistantCard) {
        assistantCard = assistantCard.concat("Assistant Card number: " + (value - 1) + "\n\tValue: " + value + "\n\t" + "Steps: " + steps);
        System.out.println(assistantCard);
    }
}
