package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.server.IServerPacket;

import java.io.Serial;
import java.io.Serializable;

public abstract class ModelUpdate implements Serializable, IServerPacket {

    @Serial
    private static final long serialVersionUID = 7611410949820701831L;
}
