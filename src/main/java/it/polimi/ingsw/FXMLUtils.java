package it.polimi.ingsw;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * Class used for FXML loading.
 */
public class FXMLUtils {

    /**
     * Loads an FXML page
     *
     * @param fxml the path of the fxml file
     * @return the loaded page
     */
    public static Parent loadFXML(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return new Label("Something went wrong");
        }
    }

    /**
     * Load a widget's FXML file
     *
     * @param component the controller for the widget
     * @param <T>       the type that is the controller for the widget
     */
    public static <T extends Parent> void loadWidgetFXML(T component) {
        String fileName = "/widget/" + component.getClass().getSimpleName() + ".fxml";
        loadFXMLComponent(component, fileName);
    }

    /**
     * Load a widget's FXML.
     *
     * @param component the controller for the widget
     * @param fileName  the path to the fxml file
     * @param <T>       the type that is the controller for the widget
     */
    public static <T extends Parent> void loadFXMLComponent(T component, String fileName) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fileName));
        loader.setRoot(component);
        loader.setControllerFactory(theClass -> component);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
