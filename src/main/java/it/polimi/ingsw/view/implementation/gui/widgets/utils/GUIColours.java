package it.polimi.ingsw.view.implementation.gui.widgets.utils;

import it.polimi.ingsw.model.Colour;
import it.polimi.ingsw.model.player.TowerColour;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.HashMap;
import java.util.Map;

public class GUIColours {
    private static final HashMap<Colour, Paint> studentRGBColours = new HashMap<>(Map.ofEntries(
            Map.entry(Colour.GREEN, Color.rgb(0, 255, 0)),
            Map.entry(Colour.RED, Color.rgb(255, 77, 77)),
            Map.entry(Colour.YELLOW, Color.rgb(255, 204, 0)),
            Map.entry(Colour.PINK, Color.rgb(255, 102, 204)),
            Map.entry(Colour.BLUE, Color.rgb(0, 204, 255))
    ));

    private static final HashMap<TowerColour, Paint> towerRGBColours = new HashMap<>(Map.ofEntries(
            Map.entry(TowerColour.WHITE,Color.rgb(255, 255, 255)),
            Map.entry(TowerColour.BLACK, Color.rgb(0, 0, 0)),
            Map.entry(TowerColour.GREY, Color.rgb(179, 179, 179))
    ));

    private static final Paint motherNatureColour = Color.rgb(255, 102, 0);

    private static final Paint noEntryTileMarkColour = Color.rgb(255, 50, 0);

    private static final Paint getNoEntryTileBackGroundColour = Color.rgb(0, 100, 255);

    public static Paint getStudentRGBColour(Colour colour){
        return studentRGBColours.get(colour);
    }

    public static Paint getTowerRGBColour(TowerColour colour){
        return towerRGBColours.get(colour);
    }

    public static Paint getMotherNatureColour(){
        return motherNatureColour;
    }

    public static Paint getNoEntryTileMarkColour(){
        return noEntryTileMarkColour;
    }

    public static Paint getGetNoEntryTileBackGroundColour(){
        return getNoEntryTileBackGroundColour;
    }

}
