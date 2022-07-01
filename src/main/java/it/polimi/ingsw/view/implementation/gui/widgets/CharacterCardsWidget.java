package it.polimi.ingsw.view.implementation.gui.widgets;

import it.polimi.ingsw.FXMLUtils;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.TurnPhase;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;
import it.polimi.ingsw.view.beans.MockCard;
import it.polimi.ingsw.view.implementation.gui.GUI;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.*;

/**
 * Widget representing the character cards available in the game
 */
public class CharacterCardsWidget extends StackPane {
    @FXML
    private HBox box;

    /**
     * The students on the card with index 0
     */
    @FXML
    private GridPane studentsOnCard0;

    /**
     * The students on the card with index 1
     */
    @FXML
    private GridPane studentsOnCard1;

    /**
     * The students on the card with index 2
     */
    @FXML
    private GridPane studentsOnCard2;

    /**
     * The number of coins on the table
     */
    @FXML
    private Label numberCoinsTable;

    /**
     * The updated cost of the card with index 0
     */
    @FXML
    private Label costCard0;

    /**
     * The updated cost of the card with index 1
     */
    @FXML
    private Label costCard1;

    /**
     * The updated cost of the card with index 2
     */
    @FXML
    private Label costCard2;

    @FXML
    private AnchorPane anchorPane;

    /**
     * List containing the imageViews of the character cards
     */
    private final List<ImageView> imageViewList = new ArrayList<>();

    private final List<FlowPane> flowPaneList = new ArrayList<>();

    private final List<ImageView> student0 = new ArrayList<>();
    private final List<ImageView> student1 = new ArrayList<>();
    private final List<ImageView> student2 = new ArrayList<>();
    private final List<FlowPane> pane0 = new ArrayList<>();
    private final List<FlowPane> pane1 = new ArrayList<>();
    private final List<FlowPane> pane2 = new ArrayList<>();
    private final HashMap<ImageView, Colour> studentColour0 = new HashMap<>();
    private final HashMap<ImageView, Colour> studentColour1 = new HashMap<>();
    private final HashMap<ImageView, Colour> studentColour2 = new HashMap<>();

    /**
     * Creates the CharacterCardsWidget
     */
    public CharacterCardsWidget() {
        FXMLUtils.loadWidgetFXML(this);
    }

    /**
     * Init method called by the FXMLLoader
     * It calls all the init method of this class
     */
    @FXML
    private void initialize() {
        for (int i = 0; i < 3; i++) {
            FlowPane flowPane = new FlowPane();
            box.getChildren().add(flowPane);
            flowPane.getStyleClass().add("assistantCards");
            flowPane.setMaxHeight(300.0);
            flowPane.setMaxWidth(196.5);
            ImageView imageView = new ImageView();
            flowPane.getChildren().add(imageView);
            flowPaneList.add(flowPane);

            imageView.setFitHeight(300.0);
            imageView.setFitWidth(196.5);

            int a = i;
            imageView.setOnMouseClicked(event -> playCharacterCard(a));
            imageViewList.add(imageView);
            HBox.setMargin(flowPane, new Insets(5.0, 5.0, 5.0, 5.0));


            imageView.setImage(new Image(Objects.requireNonNull(CharacterCardsWidget.class.getResourceAsStream(
                    "/images/characterCards/" + GUI.instance().getModel().getCharacterCardByIndex(i).getType().name() + ".jpg"))));

            if (GUI.instance().getModel().getCharacterCardByIndex(i).getNumberOfStudentsOnTheCard() > 0) {

                if (i == 0) {
                    studentsOnCard0.getChildren().clear();
                } else if (i == 1) {
                    studentsOnCard1.getChildren().clear();
                } else {
                    studentsOnCard2.getChildren().clear();
                }

                initStudentsOnCard(i);

                int card = i;

                //adds a listener to the students on the card in order to update them when they change
                GUI.instance().getModel().getCharacterCardByIndex(i).getStudentsProperty().addListener((MapChangeListener<? super Colour, ? super Integer>) listener ->

                        Platform.runLater(() -> {
                                    if (card == 0) {
                                        studentsOnCard0.getChildren().clear();
                                    } else if (card == 1) {
                                        studentsOnCard1.getChildren().clear();
                                    } else {
                                        studentsOnCard2.getChildren().clear();
                                    }
                                    initStudentsOnCard(card);
                                }
                        ));

            }
        }

        costCard0.setText(String.valueOf(GUI.instance().getModel().getCharacterCardByIndex(0).getCost()));
        //adds a listener to the cost of the character card with index 0 in order to update it when the card is played by a player
        GUI.instance().getModel().getCharacterCardByIndex(0).getCostProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> costCard0.setText(String.valueOf(GUI.instance().getModel().getCharacterCardByIndex(0).getCost()))));

        costCard1.setText(String.valueOf(GUI.instance().getModel().getCharacterCardByIndex(1).getCost()));
        //adds a listener to the cost of the character card with index 1 in order to update it when the card is played by a player
        GUI.instance().getModel().getCharacterCardByIndex(1).getCostProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> costCard1.setText(String.valueOf(GUI.instance().getModel().getCharacterCardByIndex(1).getCost()))));

        costCard2.setText(String.valueOf(GUI.instance().getModel().getCharacterCardByIndex(2).getCost()));
        //adds a listener to the cost of the character card with index 2 in order to update it when the card is played by a player
        GUI.instance().getModel().getCharacterCardByIndex(2).getCostProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> costCard2.setText(String.valueOf(GUI.instance().getModel().getCharacterCardByIndex(2).getCost()))));

        numberCoinsTable.setText(String.valueOf(GUI.instance().getModel().getCoins()));
        //adds a listener to the coins on the table in order to update it when they change
        GUI.instance().getModel().getCoinsProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> numberCoinsTable.setText(String.valueOf(GUI.instance().getModel().getCoins()))));

        if (GUI.instance().getModel().getCurrentCharacterCard().getType() != CharacterCardEnumeration.BASIC_STATE) {
            int a = -1;
            for (int i = 0; i < 3; i++) {
                if (GUI.instance().getModel().getCharacterCardByIndex(i).getType() == GUI.instance().getModel().getCurrentCharacterCard().getType()) {
                    a = i;
                    break;
                }
            }

            if (GUI.instance().getPlayerName().equals(GUI.instance().getModel().getCurrentPlayer().getNickname())) {
                switch (GUI.instance().getModel().getCurrentCharacterCard().getType()) {
                    case NO_COLOUR, THREE_STUDENT -> imageViewList.get(a).setOnMouseClicked(event -> Platform.runLater(() -> GUI.instance().showColourDecision()));
                    case PROTECT_ISLAND, ISLAND_INFLUENCE, STUDENT_TO_ISLAND -> imageViewList.get(a).setOnMouseClicked(event -> Platform.runLater(() -> GUI.instance().showGroupIslandDecision()));
                    case EXCHANGE_ENTRANCE_DINING_ROOM -> imageViewList.get(a).setOnMouseClicked(event -> Platform.runLater(() -> GUI.instance().showExchangeEntranceDiningRoom()));
                }

                if (a == 0) {
                    setImageEvent(student0, pane0, studentColour0, student1, student2);
                } else if (a == 1) {
                    setImageEvent(student1, pane1, studentColour1, student0, student2);
                } else {
                    setImageEvent(student2, pane2, studentColour2, student0, student1);
                }
            }

            flowPaneList.get(a).getStyleClass().remove("assistantCards");
            flowPaneList.get(a).getStyleClass().add("characterCardSelected");
        }

        GUI.instance().getModel().currentCharacterCardProperty().addListener((ChangeListener<? super MockCard>) (change, oldVal, newVal) -> Platform.runLater(() -> {
            if (newVal.getType() == CharacterCardEnumeration.BASIC_STATE) {
                for (int i = 0; i < 3; i++) {
                    if (oldVal.getType() == GUI.instance().getModel().getCharacterCardByIndex(i).getType()) {
                        int a = i;
                        imageViewList.get(i).setOnMouseClicked(event -> playCharacterCard(a));
                        flowPaneList.get(a).getStyleClass().remove("characterCardSelected");
                        flowPaneList.get(a).getStyleClass().add("assistantCards");
                    }
                }

                for (ImageView image : student0) {
                    image.setOnMouseClicked(event -> GUI.instance().getRenderer().showErrorMessage("You must pay the character card first"));
                }
                for (ImageView image : student1) {
                    image.setOnMouseClicked(event -> GUI.instance().getRenderer().showErrorMessage("You must pay the character card first"));
                }
                for (ImageView image : student2) {
                    image.setOnMouseClicked(event -> GUI.instance().getRenderer().showErrorMessage("You must pay the character card first"));
                }

            } else {
                int a = -1;
                //The index of the current character card is saved in the variable a
                for (int i = 0; i < 3; i++) {
                    if (GUI.instance().getModel().getCharacterCardByIndex(i).getType() == newVal.getType()) {
                        a = i;
                        break;
                    }
                }

                if (GUI.instance().getPlayerName().equals(GUI.instance().getModel().getCurrentPlayer().getNickname())) {
                    switch (newVal.getType()) {
                        case NO_COLOUR, THREE_STUDENT -> imageViewList.get(a).setOnMouseClicked(event -> Platform.runLater(() -> GUI.instance().showColourDecision()));
                        case PROTECT_ISLAND, ISLAND_INFLUENCE, STUDENT_TO_ISLAND -> imageViewList.get(a).setOnMouseClicked(event -> Platform.runLater(() -> GUI.instance().showGroupIslandDecision()));
                        case EXCHANGE_ENTRANCE_DINING_ROOM -> imageViewList.get(a).setOnMouseClicked(event -> Platform.runLater(() -> GUI.instance().showExchangeEntranceDiningRoom()));
                    }

                    if (a == 0) {
                        setImageEvent(student0, pane0, studentColour0, student1, student2);
                    } else if (a == 1) {
                        setImageEvent(student1, pane1, studentColour1, student0, student2);
                    } else {
                        setImageEvent(student2, pane2, studentColour2, student0, student1);
                    }
                }

                flowPaneList.get(a).getStyleClass().remove("assistantCards");
                flowPaneList.get(a).getStyleClass().add("characterCardSelected");
            }
        }));


        initWinner();
    }

    /**
     * Set the image event
     *
     * @param student0       the List of image view containing the students of the first card
     * @param pane0          the List of Pane Flow containing the images of the students of the first card
     * @param studentColour0 the HashMap containing the ImageView and the Colour of the student
     * @param student1       the List of image view containing the students of the second card
     * @param student2       the List of image view containing the students of the third card
     */
    private void setImageEvent(List<ImageView> student0, List<FlowPane> pane0, HashMap<ImageView, Colour> studentColour0, List<ImageView> student1, List<ImageView> student2) {
        for (ImageView image : student0) {
            image.setOnMouseClicked(event -> {
                pane0.get(student0.indexOf(image)).getStyleClass().add("studentSelected");
                setStudent(studentColour0.get(image));
            });
        }

        for (ImageView image : student1) {
            image.setOnMouseClicked(event -> GUI.instance().getRenderer().showErrorMessage("This is not the current character card"));
        }

        for (ImageView image : student2) {
            image.setOnMouseClicked(event -> GUI.instance().getRenderer().showErrorMessage("This is not the current character card"));
        }
    }

    /**
     * Plays the character card at the position i
     *
     * @param i the position of the character card
     */
    @FXML
    private void playCharacterCard(int i) {
        GUI.instance().getActionSender().playCharacterCard(GUI.instance().getPlayerName(), i);
    }

    /**
     * Saves the colour of the student selected in the mockModel
     *
     * @param colour the colour of the student selected
     */
    @FXML
    private void setStudent(Colour colour) {
        if (GUI.instance().getPlayerName().equals(GUI.instance().getModel().getCurrentPlayer().getNickname())) {
            if (GUI.instance().getModel().getCurrentCharacterCard().getType().equals(CharacterCardEnumeration.STUDENT_TO_ISLAND) ||
                    GUI.instance().getModel().getCurrentCharacterCard().getType().equals(CharacterCardEnumeration.STUDENT_TO_DINING_ROOM) ||
                    GUI.instance().getModel().getCurrentCharacterCard().getType().equals(CharacterCardEnumeration.STUDENT_TO_ENTRANCE)) {
                GUI.instance().getModel().setStudentOnCardSelected(colour);
            }
        }
    }

    /**
     * This method is called when the player clicks on the button "GO BACK TO SCHOOL BOARD". It brings the player back to the school board view
     */
    @FXML
    private void showSchoolBoard() {
        GUI.instance().showPlayerBoard();
    }

    /**
     * Method that places the students on the cards
     *
     * @param i the index of the card
     */
    @FXML
    private void initStudentsOnCard(int i) {
        cleanImages(i);
        int c, r;
        c = 0;
        r = 0;

        for (Colour colour : Colour.values()) {
            for (int j = 0; j < GUI.instance().getModel().getCharacterCardByIndex(i).getStudents().get(colour); j++) {
                FlowPane pane = new FlowPane();
                pane.getStyleClass().add("student");
                ImageView imageViewStudent = new ImageView();
                pane.getChildren().add(imageViewStudent);
                if (i == 0) {
                    student0.add(imageViewStudent);
                    studentColour0.put(imageViewStudent, colour);
                    pane0.add(pane);
                } else if (i == 1) {
                    student1.add(imageViewStudent);
                    studentColour1.put(imageViewStudent, colour);
                    pane1.add(pane);
                } else {
                    student2.add(imageViewStudent);
                    studentColour2.put(imageViewStudent, colour);
                    pane2.add(pane);
                }
                imageViewStudent.setImage(new Image(Objects.requireNonNull(CharacterCardsWidget.class.getResourceAsStream(
                        "/images/students/student_" + colour.name().toLowerCase(Locale.ROOT) + ".png"))));

                if (GUI.instance().getModel().getCurrentCharacterCard().getType() == CharacterCardEnumeration.BASIC_STATE) {
                    imageViewStudent.setOnMouseClicked(event -> GUI.instance().getRenderer().showErrorMessage("You must pay the character card first"));
                } else if (GUI.instance().getModel().getCurrentCharacterCard().getType() != GUI.instance().getModel().getCharacterCardByIndex(i).getType()) {
                    imageViewStudent.setOnMouseClicked(event -> GUI.instance().getRenderer().showErrorMessage("This is not the current character card"));
                } else {
                    if (GUI.instance().getPlayerName().equals(GUI.instance().getModel().getCurrentPlayer().getNickname())) {
                        imageViewStudent.setOnMouseClicked(event -> {
                            pane.getStyleClass().add("studentSelected");
                            setStudent(colour);
                        });
                    }
                }

                if (i == 0) {
                    studentsOnCard0.add(pane, r, c);
                } else if (i == 1) {
                    studentsOnCard1.add(pane, r, c);
                } else {
                    studentsOnCard2.add(pane, r, c);
                }
                if (r == 2) {
                    c += 2;
                    r = 0;
                } else {
                    r = 2;
                }
            }
        }
    }


    /**
     * Resets the List of the given character card
     *
     * @param i the position of the given character card
     */
    private void cleanImages(int i) {
        if (i == 0) {
            student0.clear();
            studentColour0.clear();
            pane0.clear();
        } else if (i == 1) {
            student1.clear();
            studentColour1.clear();
            pane1.clear();
        } else {
            student2.clear();
            studentColour2.clear();
            pane2.clear();
        }
    }

    /**
     * A method which checks if the game has ended (if the turn phase is ENDGAME) and, if so, calls the method which shows the winner
     */
    private void initWinner() {

        if (GUI.instance().getModel().getTurnPhase().equals(TurnPhase.ENDGAME)) {
            printWinner();
        }

        //Adds a listener to the turn phase in order to know if the game has ended
        GUI.instance().getModel().getTurnPhaseProperty().addListener((change, oldVal, newVal) -> Platform.runLater(() -> {

            if (GUI.instance().getModel().getTurnPhase().equals(TurnPhase.ENDGAME)) {
                printWinner();
            }
        }));
    }

    /**
     * The layout of the images that appear once the game has ended to show who is the winner
     */
    private void printWinner() {
        if (GUI.instance().getModel().getWinner().getNickname().equals(GUI.instance().getPlayerName())) {
            ImageView winner = new ImageView();
            anchorPane.getChildren().add(winner);
            winner.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                    "/images/winner.png"))));
            winner.setLayoutY(153);
            winner.setLayoutX(263);
            winner.setFitWidth(547);
            winner.setFitHeight(395);
        } else {
            ImageView loser = new ImageView();
            anchorPane.getChildren().add(loser);
            loser.setImage(new Image(Objects.requireNonNull(SchoolBoardWidget.class.getResourceAsStream(
                    "/images/loser2.png"))));
            loser.setLayoutY(171);
            loser.setLayoutX(74);
            loser.setFitWidth(934);
            loser.setFitHeight(290);
            Label winnerName = new Label();
            winnerName.setText(GUI.instance().getModel().getWinner().getNickname());
            winnerName.setStyle("-fx-font: 96 Stsyem;");
            winnerName.setLayoutX(671);
            winnerName.setLayoutY(257);
            anchorPane.getChildren().add(winnerName);
        }
    }


}
