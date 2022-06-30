package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.player.TowerColour;
import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.HashMap;
import java.util.List;

public class SchoolBoardUpdate extends DirectReconnectionMessage{
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -5634156904905157931L;
    /**
     * The nickname of the player
     */
    private final String playerName;
    /**
     * The HashMap with the students in the entrance
     */
    private final HashMap<Colour, Integer> entrance;
    /**
     * The HashMap with the students in the diningRoom
     */
    private final HashMap<Colour, Integer> diningRoom;
    /**
     * The number of towers
     */
    private final int towers;
    /**
     * The colour of the tower
     */
    private final TowerColour towerColour;
    /**
     * The HashMap of player's professor table
     */
    private final HashMap<Colour, Boolean> professors;

    /**
     * The number of coins of the player
     */
    private final int coins;

    /**
     * The value of the current assistant card
     */
    private final int currentAssistantCard;

    /**
     * Constructs a new SchoolBoardUpdate for the given recipient
     *
     * @param recipient the client connection that this message will be sent to
     * @param playerName the name of the player
     * @param entrance the entrance of the player
     * @param diningRoom the diningRoom of the player
     * @param towers the number of towers of the player
     * @param towerColour the colour of the tower of the player
     * @param professors the professors of the player
     * @param currentAssistantCard the value of the current assistant card of the player
     */
    public SchoolBoardUpdate(SocketClientConnection recipient, String playerName, HashMap<Colour, Integer> entrance, HashMap<Colour, Integer> diningRoom,
                             int towers, TowerColour towerColour, HashMap<Colour, Boolean> professors, int coins, int currentAssistantCard) {
        super(recipient);
        this.playerName = playerName;
        this.entrance = entrance;
        this.diningRoom = diningRoom;
        this.towers = towers;
        this.towerColour = towerColour;
        this.professors = professors;
        this.coins = coins;
        this.currentAssistantCard = currentAssistantCard;
    }

    /**
     * Update the view with the schoolBoard of the player
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateDiningRoom(playerName, diningRoom);
        view.getModelUpdateHandler().updateEntrance(playerName, entrance);
        view.getModelUpdateHandler().updateTowers(playerName, towers);
        view.getModelUpdateHandler().updateTowerColour(playerName, towerColour);
        view.getModelUpdateHandler().updateProfessorTable(playerName, professors);
        view.getModelUpdateHandler().updatePlayerCoins(playerName, coins);
        view.getModelUpdateHandler().updateCurrentAssistantCardReconnected(playerName, currentAssistantCard);
    }
}
