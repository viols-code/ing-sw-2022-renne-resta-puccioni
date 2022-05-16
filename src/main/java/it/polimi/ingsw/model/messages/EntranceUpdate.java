package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.HashMap;

public class EntranceUpdate extends PlayerUpdate {
    @Serial
    private static final long serialVersionUID = 4422435658892342341L;
    private final String playerName;
    private final HashMap<Colour, Integer> students;

    public EntranceUpdate(String playerName, HashMap<Colour, Integer> students) {
        this.playerName = playerName;
        this.students = students;
    }

    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateEntrance(playerName, students);
    }
}
