package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.IProcessablePacket;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.io.Serializable;

public abstract class ModelUpdate implements Serializable, IProcessablePacket<View> {

    @Serial
    private static final long serialVersionUID = 7611410949820701831L;
}
