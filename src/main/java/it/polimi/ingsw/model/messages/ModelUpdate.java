package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.server.IServerPacket;

import java.io.Serial;
import java.io.Serializable;

/**
 * Abstract class for all the updates in the model
 */
public abstract class ModelUpdate implements Serializable, IServerPacket {
    /**
     * The serial version UID
     */
    @Serial
    private static final long serialVersionUID = 7611410949820701831L;
}
