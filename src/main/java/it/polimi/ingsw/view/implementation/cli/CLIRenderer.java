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
        renderSchoolBoard(view.getModel().getLocalPlayer());
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
            influence(count, influenceText1, influenceText2, groupIsland, influence);

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
                    influence(count, influenceText1, influenceText2, groupIsland, influence);

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

    private void influence(int count, List<String> influenceText1, List<String> influenceText2, MockGroupIsland groupIsland, String influence) {
        if(groupIsland.getInfluentPlayer() != null){
            switch (getView().getModel().getPlayerByNickname(groupIsland.getInfluentPlayer()).getTowerColour()){
                case WHITE -> influence = "\u001b[37;1m◯\u001b[0m";
                case BLACK -> influence = "\u001b[30m◯\u001b[0m";
                case GREY -> influence = "\u001b[30; 1m◯\u001b[0m";
            }
        }
        if(count < 6){
            influenceText1.add(influence);
        } else{
            influenceText2.add(influence);
        }
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
        renderSchoolBoard(view.getModel().getPlayerByNickname(playerName));
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
     * @param player the player
     */
    private void renderSchoolBoard(MockPlayer player) {
        HashMap<Colour, Integer> entrance = player.getSchoolBoard().getEntrance();
        HashMap<Colour, Integer> diningRoom = player.getSchoolBoard().getDiningRoom();
        HashMap<Colour, Boolean> professor = player.getSchoolBoard().getProfessorTable();

        List<String> entranceText1 = new ArrayList<>();
        List<String> entranceText2 = new ArrayList<>();
        int count = 0;
        for(Colour colour: Colour.values()){
            for(int i = 0; i < entrance.get(colour); i++){
                if(count < 5){
                    selectingColour(entranceText1, colour);
                } else{
                    selectingColour(entranceText2, colour);
                }
                count++;
            }
        }

        if(entranceText1.size() < 5){
            while(entranceText1.size() < 5){
                entranceText1.add(" ");
            }
        }

        if(entranceText2.size() < 5){
            while(entranceText2.size() < 5){
                entranceText2.add(" ");
            }
        }

        List<String> diningRoomText0 = new ArrayList<>();
        List<String> diningRoomText1 = new ArrayList<>();
        List<String> diningRoomText2 = new ArrayList<>();
        List<String> diningRoomText3 = new ArrayList<>();
        List<String> diningRoomText4 = new ArrayList<>();
        List<String> diningRoomText5 = new ArrayList<>();
        List<String> diningRoomText6 = new ArrayList<>();
        List<String> diningRoomText7 = new ArrayList<>();
        List<String> diningRoomText8 = new ArrayList<>();
        List<String> diningRoomText9 = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            for(Colour colour : Colour.values()){
                switch (i){
                    case 0 -> {
                        if(diningRoom.get(colour) > i){
                            selectingColour(diningRoomText0, colour);
                        } else {
                            diningRoomText0.add(" ");
                        }
                    }
                    case 1 -> {
                        if(diningRoom.get(colour) > i){
                            selectingColour(diningRoomText1, colour);
                        } else {
                            diningRoomText1.add(" ");
                        }
                    }
                    case 2 -> {
                        if(diningRoom.get(colour) > i){
                            selectingColour(diningRoomText2, colour);
                        } else {
                            diningRoomText2.add(" ");
                        }
                    }
                    case 3 -> {
                        if(diningRoom.get(colour) > i){
                            selectingColour(diningRoomText3, colour);
                        } else {
                            diningRoomText3.add(" ");
                        }
                    }
                    case 4 -> {
                        if(diningRoom.get(colour) > i){
                            selectingColour(diningRoomText4, colour);
                        } else {
                            diningRoomText4.add(" ");
                        }
                    }
                    case 5 -> {
                        if(diningRoom.get(colour) > i){
                            selectingColour(diningRoomText5, colour);
                        } else {
                            diningRoomText5.add(" ");
                        }
                    }
                    case 6 -> {
                        if(diningRoom.get(colour) > i){
                            selectingColour(diningRoomText6, colour);
                        } else {
                            diningRoomText6.add(" ");
                        }
                    }
                    case 7 -> {
                        if(diningRoom.get(colour) > i){
                            selectingColour(diningRoomText7, colour);
                        } else {
                            diningRoomText7.add(" ");
                        }
                    }

                    case 8 -> {
                        if(diningRoom.get(colour) > i){
                            selectingColour(diningRoomText8, colour);
                        } else {
                            diningRoomText8.add(" ");
                        }
                    }

                    case 9 -> {
                        if(diningRoom.get(colour) > i){
                            selectingColour(diningRoomText9, colour);
                        } else {
                            diningRoomText9.add(" ");
                        }
                    }
                }

            }


        }

        List<String> professorText = new ArrayList<>();

        for(Colour colour : Colour.values()){
                    if(professor.get(colour)){
                        selectingColour(professorText, colour);
                    } else {
                        professorText.add(" ");
                    }
        }

        List<String> tower1 = new ArrayList<>();
        List<String> tower2 = new ArrayList<>();
        String influence = "";
        count = 0;
        for(int i = 0; i < player.getSchoolBoard().getTowers(); i++){
            if(count < 4){
                switch (player.getTowerColour()){
                    case WHITE -> influence = "\u001b[37;1m●\u001b[0m";
                    case BLACK -> influence = "\u001b[30m●\u001b[0m";
                    case GREY -> influence = "\u001b[30; 1m●\u001b[0m";
                }
                tower1.add(influence);
            } else{
                tower2.add(influence);
            }
            count++;
        }

        while(count < 8){
            if(count < 4){
                while(count < 4){
                    tower1.add(" ");
                    count ++;
                }
            } else {
                tower2.add(" ");
                count++;
            }
        }

        Formatter formatter = new Formatter();

        List<String> total = new ArrayList<>();
        total.addAll(tower2);
        total.addAll(tower1);
        total.addAll(professorText);
        total.addAll(diningRoomText9);
        total.addAll(diningRoomText8);
        total.addAll(diningRoomText7);
        total.addAll(diningRoomText6);
        total.addAll(diningRoomText5);
        total.addAll(diningRoomText4);
        total.addAll(diningRoomText3);
        total.addAll(diningRoomText2);
        total.addAll(diningRoomText1);
        total.addAll(diningRoomText0);
        total.addAll(entranceText1);
        total.addAll(entranceText2);


        System.out.println(formatter.format(ASCIIArt.SCHOOL_BOARD, total.toArray()));

    }

    private void selectingColour(List<String> entranceText1, Colour colour) {
        switch (colour){
            case RED -> entranceText1.add("\u001b[31;1m●\u001b[0m");
            case GREEN -> entranceText1.add("\u001b[32;1m●\u001b[0m");
            case YELLOW -> entranceText1.add("\u001b[33;1m●\u001b[0m");
            case BLUE -> entranceText1.add("\u001b[34;1m●\u001b[0m");
            case PINK -> entranceText1.add("\u001b[35;1m●\u001b[0m");
        }
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
