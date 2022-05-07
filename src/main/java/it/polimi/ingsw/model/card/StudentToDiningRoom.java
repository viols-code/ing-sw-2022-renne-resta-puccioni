package it.polimi.ingsw.model.card;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.messages.StudentToDiningRoomUpdate;
import it.polimi.ingsw.view.beans.CharacterCardEnumeration;

import java.util.HashMap;

public class StudentToDiningRoom extends CharacterCard {
    private final HashMap<Colour, Integer> students;
    private Colour colour;

    /**
     * Constructor
     *
     * @param game the Game
     */
    public StudentToDiningRoom(Game game) {
        super(game);
        actualCost = 2;
        students = new HashMap<>();
        type = CharacterCardEnumeration.STUDENT_TO_DINING_ROOM;
    }

    public void setting() {
        for (Colour colour1 : Colour.values()) {
            students.put(colour1, 0);
        }

        for (int i = 0; i < 4; i++) {
            try {
                Colour colour1 = game.getTable().getBag().bagDrawStudent();
                students.replace(colour1, students.get(colour1), students.get(colour1) + 1);

            } catch (IllegalAccessError ex) {
                ex.printStackTrace();
            }
        }
        notify(new StudentToDiningRoomUpdate(students));
    }

    /**
     * Activates the effect of the CharacterCard
     */
    @Override
    protected void effect() {
        game.getCurrentPlayer().getSchoolBoard().addStudentToDiningRoom(colour);

        if (((game.getCurrentPlayer().getSchoolBoard().getDiningRoom(colour) + 1) % 3) == 0) {
            game.getCurrentPlayer().addCoins(1);
            game.setCoins(game.getCoins() - 1);
        }

        checkProfessor(colour);

        students.replace(colour, students.get(colour), students.get(colour) - 1);

        try {
            Colour colour1 = game.getTable().getBag().bagDrawStudent();
            students.replace(colour1, students.get(colour1), students.get(colour1) + 1);
        } catch (IllegalAccessError ex) {
            ex.printStackTrace();
        }
        notify(new StudentToDiningRoomUpdate(students));
        game.setActiveCharacterCard(game.getBasicState());
    }

    /**
     * Set the colour of the chosen student
     *
     * @param colour the colour to be set
     */
    @Override
    public void setColour(Colour colour) {
        this.colour = colour;
        this.effect();
    }

    protected int getStudents(Colour colour) {
        return students.get(colour);
    }

}
