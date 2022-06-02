package it.polimi.ingsw.view.implementation.cli;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.player.TowerColour;
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
        renderSchoolBoard(view.getModel().getLocalPlayer());
    }


    private void renderSchoolBoardHorizontal(){
        List<String> row0 = new ArrayList<>();
        List<String> row1 = new ArrayList<>();
        List<String> row2 = new ArrayList<>();
        List<String> row3 = new ArrayList<>();
        List<String> row4 = new ArrayList<>();
        List<String> row5 = new ArrayList<>();
        List<String> total = new ArrayList<>();

        MockPlayer player1 = null;
        MockPlayer player2 = null;

        createHorizontalSchoolBoard(getView().getModel().getLocalPlayer(), row0, row1, row2, row3, row4, row5);

        int i = 0;
        for(MockPlayer mockPlayer : getView().getModel().getPlayers().values()){
            if(! mockPlayer.equals(getView().getModel().getLocalPlayer())){
                createHorizontalSchoolBoard(mockPlayer, row0, row1, row2, row3, row4, row5);
                if(i == 0){
                    player1 = mockPlayer;
                } else{
                    player2 = mockPlayer;
                }
                i++;
            }
        }

        total.addAll(row0);
        total.addAll(row1);
        total.addAll(row2);
        total.addAll(row3);
        total.addAll(row4);
        total.addAll(row5);

        Formatter formatter = new Formatter();

        if(getView().getNumPlayers() == 2){
                if(getView().getModel().getLocalPlayer().isAssistantCardValue()){
                    if(player1 != null && player1.isAssistantCardValue()){
                        System.out.println(formatter.format(ASCIIArt.SCHOOL_BOARD_HORIZONTAL_2_0, total.toArray()));
                    } else{
                        System.out.println(formatter.format(ASCIIArt.SCHOOL_BOARD_HORIZONTAL_2_1, total.toArray()));
                    }
                } else{
                if(player1 != null && player1.isAssistantCardValue()){
                    System.out.println(formatter.format(ASCIIArt.SCHOOL_BOARD_HORIZONTAL_2_2, total.toArray()));
                } else{
                    System.out.println(formatter.format(ASCIIArt.SCHOOL_BOARD_HORIZONTAL_2_3, total.toArray()));
                }
            }
        } else{
            if(getView().getModel().getLocalPlayer().isAssistantCardValue()){
                if(player1 != null && player1.isAssistantCardValue()){
                    if(player2 != null && player2.isAssistantCardValue()){
                        System.out.println(formatter.format(ASCIIArt.SCHOOL_BOARD_HORIZONTAL_3_0, total.toArray()));
                    } else{
                        System.out.println(formatter.format(ASCIIArt.SCHOOL_BOARD_HORIZONTAL_3_1, total.toArray()));
                    }
                } else{
                    if(player2 != null && player2.isAssistantCardValue()){
                        System.out.println(formatter.format(ASCIIArt.SCHOOL_BOARD_HORIZONTAL_3_2, total.toArray()));
                    } else{
                        System.out.println(formatter.format(ASCIIArt.SCHOOL_BOARD_HORIZONTAL_3_3, total.toArray()));
                    }
                }
            } else{
                if(player1 != null && player1.isAssistantCardValue()){
                    if(player2 != null && player2.isAssistantCardValue()){
                        System.out.println(formatter.format(ASCIIArt.SCHOOL_BOARD_HORIZONTAL_3_4, total.toArray()));
                    } else{
                        System.out.println(formatter.format(ASCIIArt.SCHOOL_BOARD_HORIZONTAL_3_5, total.toArray()));
                    }
                } else{
                    if(player2 != null && player2.isAssistantCardValue()){
                        System.out.println(formatter.format(ASCIIArt.SCHOOL_BOARD_HORIZONTAL_3_6, total.toArray()));
                    } else{
                        System.out.println(formatter.format(ASCIIArt.SCHOOL_BOARD_HORIZONTAL_3_7, total.toArray()));
                    }
                }
            }
        }


    }

    private void createHorizontalSchoolBoard(MockPlayer player, List<String> row0, List<String> row1, List<String> row2, List<String> row3, List<String> row4, List<String> row5){
        HashMap<Colour, Integer> entrance = player.getSchoolBoard().getEntrance();
        HashMap<Colour, Integer> diningRoom = player.getSchoolBoard().getDiningRoom();
        HashMap<Colour, Boolean> professor = player.getSchoolBoard().getProfessorTable();

        if(getView().getModel().getLocalPlayer().equals(player)){
            row0.add("You");
        } else{
            row0.add(player.getNickname());
        }

        int count = 0;
        for(Colour colour: Colour.values()){
            for(int i = 0; i < entrance.get(colour); i++){
                count = getCount(row1, row2, row3, row4, row5, count, colour);
            }
        }
        while(count < 9){
            switch(count){
                case 0, 1 -> row1.add(" ");
                case 2, 3 -> row2.add(" ");
                case 4, 5 -> row3.add(" ");
                case 6, 7 -> row4.add(" ");
                case 8 -> row5.add(" ");
            }
            count ++;
        }

        for(Colour colour: Colour.values()){
            for(int i = 0; i < 10; i++){
                if(diningRoom.get(colour) > i){
                    colour(row1, row2, row3, row4, row5, colour);
                } else{
                    addEmptySpace(row1, row2, row3, row4, row5, colour);
                }
            }
        }

        for(Colour colour : Colour.values()){
            if(professor.get(colour)){
                colour(row1, row2, row3, row4, row5, colour);
            } else {
                addEmptySpace(row1, row2, row3, row4, row5, colour);
            }
        }

        count = 0;
        for(int i = 0; i < player.getSchoolBoard().getTowers(); i++){
            switch(count){
                case 0, 1 -> selectTowerColour(row1, player.getTowerColour());
                case 2, 3 -> selectTowerColour(row2, player.getTowerColour());
                case 4, 5 -> selectTowerColour(row3, player.getTowerColour());
                case 6, 7 -> selectTowerColour(row4, player.getTowerColour());
            }
            count++;
        }

        while(count < 8){
            switch(count){
                case 0, 1 -> row1.add(" ");
                case 2, 3 -> row2.add(" ");
                case 4, 5 -> row3.add(" ");
                case 6, 7 -> row4.add(" ");
            }
            count ++;
        }

        if(player.isAssistantCardValue()){
            if(player.getCurrentAssistantCard().getValue() < 9){
                row1.add(player.getCurrentAssistantCard().getValue() + " ");
            } else{
                row1.add(player.getCurrentAssistantCard().getValue() + "");
            }
            row1.add(player.getCurrentAssistantCard().getMotherNatureMovement() + "");
        }

    }


    private void addEmptySpace(List<String> row1, List<String> row2, List<String> row3, List<String> row4, List<String> row5, Colour colour) {
        switch(colour){
            case GREEN -> row1.add(" ");
            case RED -> row2.add(" ");
            case BLUE -> row3.add(" ");
            case YELLOW -> row4.add(" ");
            case PINK -> row5.add(" ");
        }
    }

    private void colour(List<String> row1, List<String> row2, List<String> row3, List<String> row4, List<String> row5, Colour colour) {
        switch(colour){
            case GREEN -> selectingColour(row1, colour);
            case RED -> selectingColour(row2, colour);
            case BLUE -> selectingColour(row3, colour);
            case YELLOW -> selectingColour(row4, colour);
            case PINK -> selectingColour(row5, colour);
        }
    }

    private int getCount(List<String> row1, List<String> row2, List<String> row3, List<String> row4, List<String> row5, int count, Colour colour) {
        switch(count){
            case 0, 1 -> selectingColour(row1, colour);
            case 2, 3 -> selectingColour(row2, colour);
            case 4, 5 -> selectingColour(row3, colour);
            case 6, 7 -> selectingColour(row4, colour);
            case 8 -> selectingColour(row5, colour);
        }
        count++;
        return count;
    }

    private void selectTowerColour(List<String> towers, TowerColour colour){
        switch (colour){
            case WHITE -> towers.add("\u001b[97;1m●\u001b[0m");
            case BLACK -> towers.add("\u001b[30m●\u001b[0m");
            case GREY -> towers.add("\u001b[37;1m●\u001b[0m");
        }
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
        renderAssistantCard(view.getModel().getLocalPlayer().getCurrentAssistantCard().getValue(),
                view.getModel().getLocalPlayer().getCurrentAssistantCard().getMotherNatureMovement());
    }

    public void printAvailableAssistantCards(){
        System.out.println("Your assistant cards still available");
        printAssistantCards(view.getModel().getLocalPlayer());
    }

    /**
     * Prints the available assistant cards
     */
    private void printAssistantCards(MockPlayer player) {
        List<String> assistantCardNumber = new ArrayList<>();
        List<String> assistantCardValues = new ArrayList<>();
        List<String> total = new ArrayList<>();
        HashMap<Integer, AssistantCard> cards = player.getCards();
        Formatter formatter = new Formatter();

        for(int i = 0; i < 10; i++){
            if(cards.containsKey(i)){
                assistantCardNumber.add(i + "");
                if(i < 9){
                    assistantCardValues.add(cards.get(i).getValue() + " ");
                } else{
                    assistantCardValues.add(cards.get(i).getValue() + "");
                }
                assistantCardValues.add(cards.get(i).getMotherNatureMovement() + "");
            }

            if(assistantCardNumber.size() == 5 && i < 9){
                total.addAll(assistantCardValues);
                total.addAll(assistantCardNumber);
                System.out.println(formatter.format(ASCIIArt.ASSISTANT_CARDS_5, total.toArray()));
                assistantCardNumber = new ArrayList<>();
                assistantCardValues = new ArrayList<>();
                total = new ArrayList<>();
                formatter = new Formatter();
            }
        }

        total.addAll(assistantCardValues);
        total.addAll(assistantCardNumber);

        switch (assistantCardNumber.size()){
            case(1) -> System.out.println(formatter.format(ASCIIArt.ASSISTANT_CARDS_1, total.toArray()));
            case(2) -> System.out.println(formatter.format(ASCIIArt.ASSISTANT_CARDS_2, total.toArray()));
            case(3) -> System.out.println(formatter.format(ASCIIArt.ASSISTANT_CARDS_3, total.toArray()));
            case(4) -> System.out.println(formatter.format(ASCIIArt.ASSISTANT_CARDS_4, total.toArray()));
            case(5) -> System.out.println(formatter.format(ASCIIArt.ASSISTANT_CARDS_5, total.toArray()));
        }
    }

    /**
     * Prints the islands
     */
    public void printIslands() {
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
                motherNature = "\u001b[31m●\u001b[0m";
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
                List<String> total1 = createTotal(influenceText1, motherNatureText1, colour11, colour21, groupIslandText1);
                List<String> total2 =createTotal(influenceText2, motherNatureText2, colour12, colour22, groupIslandText2);
                List<String> total = new ArrayList<>();
                total.addAll(total1);
                total.addAll(total2);
                System.out.println(formatter.format(ASCIIArt.ISLAND, total.toArray()));

            }
        }

    }

    private void influence(int count, List<String> influenceText1, List<String> influenceText2, MockGroupIsland groupIsland, String influence) {
        if(groupIsland.getInfluentPlayer() != null){
            switch (getView().getModel().getPlayerByNickname(groupIsland.getInfluentPlayer()).getTowerColour()){
                case WHITE -> influence = "\u001b[97;1m●\u001b[0m";
                case BLACK -> influence = "\u001b[30m●\u001b[0m";
                case GREY -> influence = "\u001b[37;1m●\u001b[0m";
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
        List<String> cloudTilesNumber = new ArrayList<>();
        List<String> cloudTilesStudents = new ArrayList<>();

        int i = 0;

        for (MockCloudTile cloud : view.getModel().getTable().getShownCloudTiles()) {
            cloudTilesNumber.add(i + " ");

            for (Colour colour : Colour.values()) {
                for(int j = 0; j < cloud.getMockCloudTile().get(colour) ; j++){
                    addStudentColour(cloudTilesStudents, colour);
                }
            }
            i++;
        }

        List<String> total = new ArrayList<>();
        total.addAll(cloudTilesStudents);
        total.addAll(cloudTilesNumber);

        Formatter formatter = new Formatter();

        if(view.getNumPlayers() == 2){
            switch(view.getModel().getTable().getShownCloudTiles().size()) {
                case (1) -> System.out.println(formatter.format(ASCIIArt.CLOUD_TILE_2_1, total.toArray()));
                case (2) -> System.out.println(formatter.format(ASCIIArt.CLOUD_TILE_2_2, total.toArray()));
            }
        } else{
            switch(view.getModel().getTable().getShownCloudTiles().size()) {
                case (1) -> System.out.println(formatter.format(ASCIIArt.CLOUD_TILE_3_1, total.toArray()));
                case (2) -> System.out.println(formatter.format(ASCIIArt.CLOUD_TILE_3_2, total.toArray()));
                case (3) -> System.out.println(formatter.format(ASCIIArt.CLOUD_TILE_3_3, total.toArray()));
            }
        }
    }

    private void addStudentColour(List<String> cloudTilesStudents, Colour colour) {
        switch (colour){
            case RED -> cloudTilesStudents.add("\u001b[31;1m●\u001b[0m");
            case GREEN -> cloudTilesStudents.add("\u001b[32;1m●\u001b[0m");
            case YELLOW -> cloudTilesStudents.add("\u001b[33;1m●\u001b[0m");
            case BLUE -> cloudTilesStudents.add("\u001b[34m●\u001b[0m");
            case PINK -> cloudTilesStudents.add("\u001b[35;1m●\u001b[0m");
        }
    }

    /**
     * Prints the active character card
     */
    public void printActiveCharacterCard() {
        if (view.getModel().getCurrentCharacterCard().getType() != CharacterCardEnumeration.BASIC_STATE) {
            System.out.println("The active character card is:");
            renderActiveCharacter(view.getModel().getCurrentCharacterCard());
        } else {
            showGameMessage(ViewString.NO_ACTIVE_CHARACTER_CARD);
        }
    }

    /**
     * Prints the character cards
     */
    public void printCharacterCards() {
        System.out.println("The character cards available are:");
        List<String> row1 = new ArrayList<>();
        List<String> row2 = new ArrayList<>();
        List<String> row3 = new ArrayList<>();
        List<String> row4 = new ArrayList<>();
        List<String> row5 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            renderCharacter(i, view.getModel().getCharacterCardByIndex(i), row1, row2, row3, row4, row5);
        }

        List<String> total = new ArrayList<>();
        total.addAll(row1);
        total.addAll(row2);
        total.addAll(row3);
        total.addAll(row4);
        total.addAll(row5);

        System.out.printf((ASCIIArt.CHARACTER_CARD), total.toArray());
    }

    /**
     * Prints the selected character card
     *
     * @param card      the card to print
     */
    private void renderCharacter(int i, MockCard card, List<String> row1, List<String> row2, List<String> row3, List<String> row4, List<String> row5) {
        row1.add(i + "");
        row2.add(card.getType().name());
        row3.add(card.getCost() + "");

        if (card.getNumberOfNoEntryTile() > 0) {
            for (int j = 0; j < card.getNumberOfNoEntryTile(); j++) {
                row4.add("o");
            }
        }
        while (row4.size() < 4 * (i + 1)) {
            row4.add(" ");
        }

        if (card.getNumberOfStudentsOnTheCard() > 0) {
            for (Colour colour : Colour.values()) {
                for (int j = 0; j < card.getStudents().get(colour); j++) {
                    addStudentColour(row5, colour);
                }
            }
        }

        while (row5.size() < 6 * (i + 1)) {
            row5.add(" ");
        }

    }


    private void renderActiveCharacter(MockCard card) {
        List<String> total = new ArrayList<>();
        total.add(card.getType().name());
        total.add(card.getCost() + "");

        if (card.getNumberOfNoEntryTile() > 0) {
            for (int j = 0; j < card.getNumberOfNoEntryTile(); j++) {
                total.add("▬");
            }
        }
        while (total.size() < 6) {
            total.add(" ");
        }

        if (card.getNumberOfStudentsOnTheCard() > 0) {
            for (Colour colour : Colour.values()) {
                for (int j = 0; j < card.getStudents().get(colour); j++) {
                    addStudentColour(total, colour);
                }
            }
        }

        while (total.size() < 12) {
            total.add(" ");
        }

        Formatter formatter = new Formatter();

        System.out.println(formatter.format(ASCIIArt.ACTIVE_CHARACTER_CARD, total.toArray()));
    }

    /**
     * Prints the available table coins
     */
    public void printTableCoins() {
        String coins = "";
        coins = coins.concat("Available coins in the bank: " + view.getModel().getCoins());
        System.out.println(coins);
    }

    /**
     * Prints the professors available on the table
     */
    public void printTableProfessors() {
        System.out.println("Available professors: ");
        HashMap<Colour, Boolean> prof = view.getModel().getTable().getProfessorsAvailable();

        List<String> professorsAvailable = new ArrayList<>();

        for (Colour colour : Colour.values()) {
            if(prof.get(colour)){
                addStudentColour(professorsAvailable, colour);
            }
        }

        while(professorsAvailable.size() < 5){
            professorsAvailable.add("");
        }

        Formatter formatter = new Formatter();
        System.out.println(formatter.format(ASCIIArt.AVAILABLE_PROFESSOR, professorsAvailable.toArray()));
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
        renderSchoolBoard(view.getModel().getPlayerByNickname(playerName));
    }

    /**
     * Prints the current assistant card of another player
     *
     * @param playerName the nickname of the player
     * @throws IllegalArgumentException if the nickname typed is not present in this game
     */
    public void printOthersCurrentAssistantCard(String playerName) throws IllegalArgumentException {
        renderAssistantCard(view.getModel().getPlayerByNickname(playerName).getCurrentAssistantCard().getValue(),
                view.getModel().getPlayerByNickname(playerName).getCurrentAssistantCard().getMotherNatureMovement());
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
                    case WHITE -> influence = "\u001b[97;1m●\u001b[0m";
                    case BLACK -> influence = "\u001b[30m●\u001b[0m";
                    case GREY -> influence = "\u001b[37;1m●\u001b[0m";
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
        addStudentColour(entranceText1, colour);
    }

    /**
     * Prints the coins of a player
     *
     * @param coins       the number of coins
     * @param numberCoins the string to print
     */
    private void renderCoins(int coins, String numberCoins) {
        numberCoins = numberCoins.concat(AnsiColour.GOLD + "Your coins: " + coins + AnsiColour.RESET + "\n");
        System.out.println(numberCoins);
    }

    /**
     * Prints the assistant card
     *
     * @param value         the assistant card value
     * @param steps         the steps that mother nature can do
     */
    private void renderAssistantCard(int value, int steps) {
        String v = value + "";
        if(value < 10){
            v = v.concat(" ");
        }

        System.out.printf((ASCIIArt.CURRENT_ASSISTANT_CARD) + "%n", v, steps);
    }

    private void printTable(){
        for(Colour colour : Colour.values()){
            if(getView().getModel().getTable().getProfessorsAvailable().get(colour)){
                printTableProfessors();
                break;
            }
        }
        renderSchoolBoardHorizontal();
        printIslands();
    }

    public void printSituation(){

        switch (getView().getModel().getTurnPhase()) {
            case PLAY_ASSISTANT_CARD -> printTable();
            case MOVE_STUDENT, MOVE_MOTHER_NATURE -> {
                if(getView().getGameMode()){
                    if(getView().getModel().getCurrentCharacterCard().getType() != CharacterCardEnumeration.BASIC_STATE){
                        getView().getRenderer().printActiveCharacterCard();
                    } else{
                        getView().getRenderer().printCharacterCards();
                    }
                    getView().getRenderer().printTableCoins();
                    getView().getRenderer().printLocalPlayerCoins();
                }
                printTable();
            }
            case CHOOSE_CLOUD_TILE -> {
                printTable();
                getView().getRenderer().printCloudTiles();
            }
        }

    }

}
