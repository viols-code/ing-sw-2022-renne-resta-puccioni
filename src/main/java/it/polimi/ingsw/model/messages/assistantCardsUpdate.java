package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.List;

public class assistantCardsUpdate extends DirectReconnectionMessage{

    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -5634156904905153231L;


    /**
     * The assistant cards that have already been used by the player that reconnected
     */
    private final List<Integer> assistantCardsUsed;

    public assistantCardsUpdate(SocketClientConnection recipient, List<Integer> assistantCardsUsed){
        super(recipient);
        this.assistantCardsUsed = assistantCardsUsed;
    }

    /**
     * Updates the list of assistant cards of the player
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateListOfAssistantCards(assistantCardsUsed);
    }
}
