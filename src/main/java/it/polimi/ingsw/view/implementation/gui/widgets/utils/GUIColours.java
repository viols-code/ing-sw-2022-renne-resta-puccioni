package it.polimi.ingsw.view.implementation.gui.widgets.utils;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.player.TowerColour;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.HashMap;
import java.util.Map;

/**
 * Class used to change the colours on the GUI
 */
public class GUIColours {
    /**
     * Sets the colours of the students
     */
    private static final HashMap<Colour, Paint> studentRGBColours = new HashMap<>(Map.ofEntries(
            Map.entry(Colour.GREEN, Color.rgb(0, 255, 0)),
            Map.entry(Colour.RED, Color.rgb(255, 77, 77)),
            Map.entry(Colour.YELLOW, Color.rgb(255, 204, 0)),
            Map.entry(Colour.PINK, Color.rgb(255, 102, 204)),
            Map.entry(Colour.BLUE, Color.rgb(0, 204, 255))
    ));

    /**
     * Sets the colours of the towers
     */
    private static final HashMap<TowerColour, Paint> towerRGBColours = new HashMap<>(Map.ofEntries(
            Map.entry(TowerColour.WHITE, Color.rgb(255, 255, 255)),
            Map.entry(TowerColour.BLACK, Color.rgb(0, 0, 0)),
            Map.entry(TowerColour.GREY, Color.rgb(179, 179, 179))
    ));

    /**
     * Sets the colour of mother nature
     */
    private static final Paint motherNatureColour = Color.rgb(255, 102, 0);

    /**
     * Sets the colour of the no entry tile mark
     */
    private static final Paint noEntryTileMarkColour = Color.rgb(255, 255, 255);

    /**
     * Sets the colour of the no entry tiles' background
     */
    private static final Paint getNoEntryTileBackGroundColour = Color.rgb(0, 100, 255);

    /**
     * Gets the colours of the students
     */
    public static Paint getStudentRGBColour(Colour colour) {
        return studentRGBColours.get(colour);
    }

    /**
     * Gets the colours of the towers
     */
    public static Paint getTowerRGBColour(TowerColour colour) {
        return towerRGBColours.get(colour);
    }

    /**
     * Gets the colour of mother nature
     */
    public static Paint getMotherNatureColour() {
        return motherNatureColour;
    }

    /**
     * Gets the colour of the no entry tile mark
     */
    public static Paint getNoEntryTileMarkColour() {
        return noEntryTileMarkColour;
    }

    /**
     * Gets the colour of the no entry tiles' background
     */
    public static Paint getGetNoEntryTileBackGroundColour() {
        return getNoEntryTileBackGroundColour;
    }

}
