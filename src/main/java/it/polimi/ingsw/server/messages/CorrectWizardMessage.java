package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.server.SocketClientConnection;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.List;

public class CorrectWizardMessage extends DirectServerMessage{
    @Serial
    private static final long serialVersionUID = -7978594154814429984L;

    private final Wizard wizard;
    private final List<Wizard> takenWizard;

    /**
     * Constructs a new PlayerConnectMessage for the player with the given name.
     *
     * @param wizard     the wizard chosen by the player
     * @param takenWizard the list of wizards already taken
     */
    public CorrectWizardMessage(SocketClientConnection recipient, Wizard wizard, List<Wizard> takenWizard) {
        super(recipient);
        this.wizard = wizard;
        this.takenWizard = takenWizard;
    }

    @Override
    public void process(View view) {
        view.handleCorrectWizard(wizard, takenWizard);
    }
}
