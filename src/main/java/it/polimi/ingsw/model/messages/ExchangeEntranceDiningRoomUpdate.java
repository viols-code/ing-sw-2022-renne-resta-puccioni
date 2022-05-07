package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;

public class ExchangeEntranceDiningRoomUpdate extends CardUpdate {
    @Serial
    private static final long serialVersionUID = 5511475659820901831L;
    private Colour colourEntrance;
    private Colour colourDiningRoom;

    public ExchangeEntranceDiningRoomUpdate(Colour colourEntrance, Colour colourDiningRoom) {
        this.colourDiningRoom = colourDiningRoom;
        this.colourEntrance = colourEntrance;
    }

    @Override
    public void process(View view) {

    }
}
