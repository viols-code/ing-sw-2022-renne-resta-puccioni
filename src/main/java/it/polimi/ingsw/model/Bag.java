package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Random;

public class Bag {
    /**
     * A Map containing the number of students for each colour in the bag
     */
    private final HashMap<Colour, Integer> bag;


    public Bag() {
        bag = new HashMap<>();
        bag.put(Colour.GREEN, 24);
        bag.put(Colour.RED, 24);
        bag.put(Colour.YELLOW, 24);
        bag.put(Colour.PINK, 24);
        bag.put(Colour.BLUE, 24);
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

    /**
     * Remove a student from the bag
     *
     * @param colour the colour of the student to be removed
     */
    private void removeStudentBag(Colour colour) {
        bag.replace(colour, bag.get(colour), bag.get(colour) - 1);
    }

    /**
     * Draw a student from the bag
     *
     * @return the student drawn
     */
    public Colour bagDrawStudent() {
        int bag_size = 0;

        for (Colour colour : Colour.values()) {
            bag_size += bag.get(colour);
        }

        Random rand = new Random();
        int upperbound = bag_size - 1;
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
