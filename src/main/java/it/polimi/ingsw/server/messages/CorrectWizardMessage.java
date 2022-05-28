package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.view.View;

import java.io.Serial;

/**
 * DirectServerMessage notifying a client that they set successfully the wizard
 */
public class CorrectWizardMessage extends DirectServerMessage {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = -7978594154814429984L;
    /**
     * The wizard chosen by the player
     */
    private final Wizard wizard;

    /**
     * Constructs a new CorrectWizardMessage for the player with the given wizard
     *
     * @param recipient the client connection to send this message to
     * @param wizard    the wizard chosen by the player
     */
    public CorrectWizardMessage(SocketClientConnection recipient, Wizard wizard) {
        super(recipient);
        this.wizard = wizard;
    }

    /**
     * Notifying a client that they set successfully the wizard
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.handleCorrectWizard(wizard);
    }
}
