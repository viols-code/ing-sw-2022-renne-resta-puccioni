package it.polimi.ingsw.model.messages;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.view.View;

import java.io.Serial;
import java.util.HashMap;

public class ProfessorTableUpdate extends PlayerUpdate {
    @Serial
    private static final long serialVersionUID = 3311215655398702341L;
    private final String playerName;
    private final HashMap<Colour, Boolean> professors;

    public ProfessorTableUpdate(String playerName, HashMap<Colour, Boolean> professors) {
        this.playerName = playerName;
        this.professors = professors;
    }


    @Override
    public void process(View view) {
        view.getModelUpdateHandler().updateProfessorTable(playerName, professors);
    }
}
