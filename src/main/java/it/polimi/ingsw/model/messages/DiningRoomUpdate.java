package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.HashMap;

public class DiningRoomUpdate extends PlayerUpdate {
    @Serial
    private static final long serialVersionUID = 5500455658898702341L;
    private final String playerName;
    private final HashMap<Colour, Integer> students;

    public DiningRoomUpdate(String playerName, HashMap<Colour, Integer> students) {
        this.playerName = playerName;
        this.students = students;
    }


    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateDiningRoom(playerName,students);
    }
}
