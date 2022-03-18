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
    public void removeStudentBag(Colour colour) {
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
            bag.replace(Colour.YELLOW, bag.get(Colour.YELLOW), bag.get(Colour.YELLOW) - 1);
            return Colour.YELLOW;
        } else if (n > getBagStudent(Colour.YELLOW) && n <= getBagStudent(Colour.YELLOW) + getBagStudent(Colour.BLUE)) {
            bag.replace(Colour.BLUE, bag.get(Colour.BLUE), bag.get(Colour.BLUE) - 1);
            return Colour.BLUE;
        } else if (n > getBagStudent(Colour.YELLOW) + getBagStudent(Colour.BLUE) && n <= getBagStudent(Colour.YELLOW) + getBagStudent(Colour.BLUE) + getBagStudent(Colour.PINK)) {
            bag.replace(Colour.PINK, bag.get(Colour.PINK), bag.get(Colour.PINK) - 1);
            return Colour.PINK;
        } else if (n > getBagStudent(Colour.YELLOW) + getBagStudent(Colour.BLUE) + getBagStudent(Colour.PINK)
                && n <= getBagStudent(Colour.YELLOW) + getBagStudent(Colour.BLUE) + getBagStudent(Colour.PINK) + getBagStudent(Colour.RED)) {
            bag.replace(Colour.RED, bag.get(Colour.RED), bag.get(Colour.RED) - 1);
            return Colour.RED;
        } else {
            bag.replace(Colour.GREEN, bag.get(Colour.GREEN), bag.get(Colour.GREEN) - 1);
            return Colour.GREEN;
        }
    }
}
