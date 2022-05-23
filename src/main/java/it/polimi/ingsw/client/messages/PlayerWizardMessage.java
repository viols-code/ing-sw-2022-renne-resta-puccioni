package it.polimi.ingsw.client.messages;

import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.server.LobbyController;

import java.io.Serial;

/**
 * ClientMessage notifying the server of the chosen wizard
 */
public class PlayerWizardMessage extends ClientMessage {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 7311855340201090917L;
    /**
     * The wizard chosen by the player
     */
    private final Wizard wizard;

    /**
     * Constructs a new PlayerNameMessage.
     *
     * @param wizard the wizard name that the client has chosen
     */
    public PlayerWizardMessage(Wizard wizard) {
        this.wizard = wizard;
    }

    /**
     * Gets the wizard
     *
     * @return wizard
     */
    public Wizard getWizard() {
        return wizard;
    }

    /**
     * Processes the packet
     *
     * @param lobbyController the lobbyController that will process the packet
     */
    @Override
    public void process(LobbyController lobbyController) {
        lobbyController.setPlayerWizard(getClientConnection(), wizard);
    }
}
