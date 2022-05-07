package it.polimi.ingsw.client.messages;

import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.server.LobbyController;

import java.io.Serial;

public class PlayerWizardMessage extends ClientMessage {
    @Serial
    private static final long serialVersionUID = 7311855340201090917L;
    private final Wizard wizard;

    /**
     * Constructs a new PlayerNameMessage.
     *
     * @param wizard the wizard name that the client has chosen
     */
    public PlayerWizardMessage(Wizard wizard) {
        this.wizard = wizard;
    }

    public Wizard getWizard() {
        return wizard;
    }

    @Override
    public void process(LobbyController lobbyController) {
        lobbyController.setWizard(getClientConnection(), wizard);
        System.out.println("Siamo sul server - settiamo il wizard " + wizard);
    }
}
