package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.IProcessablePacket;

import java.io.Serial;
import java.io.Serializable;

public abstract class ModelUpdate implements Serializable, IProcessablePacket {

    @Serial
    private static final long serialVersionUID = 7611410949820701831L;
}
