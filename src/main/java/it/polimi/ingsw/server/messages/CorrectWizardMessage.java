package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.List;

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
     * The list of wizards already taken
     */
    private final List<Wizard> takenWizard;

    /**
     * Constructs a new CorrectWizardMessage for the player with the given wizard
     *
     * @param recipient   the client connection to send this message to
     * @param wizard      the wizard chosen by the player
     * @param takenWizard the list of wizards already taken
     */
    public CorrectWizardMessage(SocketClientConnection recipient, Wizard wizard, List<Wizard> takenWizard) {
        super(recipient);
        this.wizard = wizard;
        this.takenWizard = takenWizard;
    }

    /**
     * Notifying a client that they set successfully the wizard
     *
     * @param view the view
     */
    @Override
    public void process(View view) {
        view.handleCorrectWizard(wizard, takenWizard);
    }
}
