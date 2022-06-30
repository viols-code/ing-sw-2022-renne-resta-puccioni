package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.HashMap;
import java.util.List;

public class TableReconnectUpdate extends DirectReconnectionMessage{
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -5351934793905157931L;
    /**
     * The recipient
     */
    protected final transient SocketClientConnection recipient;
    /**
     * The number of groupIslands
     */
    private final int groupIsland;
    /**
     * True if the groupIslands are expert, false otherwise
     */
    private final boolean expert;

    /**
     * List containing the influent players in every group island
     */
    private final List<String> influentPlayers;

    /**
     * List containing the number of noEntryTiles for every groupIsland
     */
    private final List<Integer> noEntryTiles;

    /**
     * List containing the number of singleIslands for every groupIsland
     */
    private final List<Integer> numberOfSingleIslands;

    /**
     * A HashMap containing the students for every singleIsland
     */
    private final HashMap<Integer, HashMap<Colour, Integer>> students;

    /**
     * The position of mother nature
     */
    private final int motherNaturePosition;

    /**
     * A HashMap containing the students for every cloud tile
     */
    private final HashMap<Integer, HashMap<Colour, Integer>> studentsOnCloudTiles;

    /**
     * Constructs a new TableReconnectUpdate for the given recipient
     *
     * @param recipient the client connection that this message will be sent to
     */
    public TableReconnectUpdate(SocketClientConnection recipient, int groupIsland, boolean expert, List<String> influentPlayers, List<Integer> noEntryTiles,
                                List<Integer> numberOfSingleIslands, HashMap<Integer, HashMap<Colour, Integer>> students, int motherNaturePosition,
                                HashMap<Integer, HashMap<Colour, Integer>> studentsOnCloudTiles) {
        super(recipient);
        this.recipient = recipient;
        this.groupIsland = groupIsland;
        this.expert = expert;
        this.influentPlayers = influentPlayers;
        this.noEntryTiles = noEntryTiles;
        this.numberOfSingleIslands = numberOfSingleIslands;
        this.students = students;
        this.motherNaturePosition = motherNaturePosition;
        this.studentsOnCloudTiles = studentsOnCloudTiles;
    }

    /**
     * Gets the recipient of this message
     *
     * @return the client connection that is the recipient of this message
     */
    public SocketClientConnection getRecipient() {
        return recipient;
    }

    /**
     * Update the view with the islands information
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateIslands(groupIsland, expert);
        view.getModelUpdateHandler().updateInfluentPlayers(influentPlayers);
        view.getModelUpdateHandler().updateNoEntryTiles(noEntryTiles);
        view.getModelUpdateHandler().updateSingleIslands(numberOfSingleIslands);
        view.getModelUpdateHandler().updateStudents(students);
        view.getModelUpdateHandler().updateMotherNaturePosition(motherNaturePosition);
        view.getModelUpdateHandler().updateStudentsOnShownCloudTiles(studentsOnCloudTiles);
    }
}
