package it.polimi.ingsw.model.table;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.messages.EmptyBagUpdate;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.IServerPacket;

import java.util.HashMap;
import java.util.Random;

public class Bag extends Observable<IServerPacket> {
    /**
     * A Map containing the number of students for each colour in the bag
     */
    private final HashMap<Colour, Integer> bag;
    /**
     * True if we tried to get student from an empty bag
     */
    private boolean noStudent;

    public Bag() {
        bag = new HashMap<>();
        bag.put(Colour.GREEN, 0);
        bag.put(Colour.RED, 0);
        bag.put(Colour.YELLOW, 0);
        bag.put(Colour.PINK, 0);
        bag.put(Colour.BLUE, 0);

        this.noStudent = false;
    }

    /**
     * Get the number of student of the given colour
     *
     * @param colour the colour of the number of student to return
     * @return the number of student of the given colour
     */
    public int getBagStudent(Colour colour) {
        return bag.get(colour);
    }

    /**
     * Add a student to the bag
     *
     * @param colour the colour of the student to be added
     */
    public void addStudentBag(Colour colour) {
        bag.replace(colour, bag.get(colour), bag.get(colour) + 1);
    }

    public void setNoStudent() {
        this.noStudent = true;
        notify(new EmptyBagUpdate());
    }

    /**
     * Draw a student from the bag
     *
     * @return the student drawn
     * @throws IllegalAccessError if there are no student in the bag
     */
    public Colour bagDrawStudent() throws IllegalAccessError {
        if (isBagEmpty()) {
            if (!this.noStudent) {
                setNoStudent();
            }
            throw new IllegalAccessError("There is no student in the bag");
        } else {
            int bag_size = 0;

            for (Colour colour : Colour.values()) {
                bag_size += bag.get(colour);
            }

            Random rand = new Random();
            int upperbound = bag_size;
            int n;
            n = rand.nextInt(upperbound) + 1;

            if (n <= getBagStudent(Colour.YELLOW)) {
                this.removeStudentBag(Colour.YELLOW);
                return Colour.YELLOW;
            } else if (n > getBagStudent(Colour.YELLOW) && n <= getBagStudent(Colour.YELLOW) + getBagStudent(Colour.BLUE)) {
                this.removeStudentBag(Colour.BLUE);
                return Colour.BLUE;
            } else if (n > getBagStudent(Colour.YELLOW) + getBagStudent(Colour.BLUE) && n <= getBagStudent(Colour.YELLOW) + getBagStudent(Colour.BLUE) + getBagStudent(Colour.PINK)) {
                this.removeStudentBag(Colour.PINK);
                return Colour.PINK;
            } else if (n > getBagStudent(Colour.YELLOW) + getBagStudent(Colour.BLUE) + getBagStudent(Colour.PINK)
                    && n <= getBagStudent(Colour.YELLOW) + getBagStudent(Colour.BLUE) + getBagStudent(Colour.PINK) + getBagStudent(Colour.RED)) {
                this.removeStudentBag(Colour.RED);
                return Colour.RED;
            } else {
                this.removeStudentBag(Colour.GREEN);
                return Colour.GREEN;
            }
        }
    }

    /**
     * Checks if the bag is empty
     *
     * @return true if there are no students in the bag
     */
    public boolean isBagEmpty() {
        for (Colour colour : Colour.values()) {
            if (getBagStudent(colour) > 0)
                return false;
        }
        return true;
    }

    /**
     * Remove a student from the bag
     *
     * @param colour the colour of the student to be removed
     */
    private void removeStudentBag(Colour colour) {
        bag.replace(colour, bag.get(colour), bag.get(colour) - 1);
    }

    /**
     * Return noStudent
     *
     * @return noStudent
     */
    public boolean getNoStudent() {
        return this.noStudent;
    }
}
