package twentyone;

import java.awt.Toolkit;
import javafx.geometry.Rectangle2D;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import twentyone.Classes.CelestialBody;
import twentyone.Classes.PositionCalculationThread;
import twentyone.Classes.Vector3d;
import twentyone.Classes.musicPlayer;

import java.io.IOException;

/**
 * JavaFX App
 * <ul>
 * <li>This extends the {@code Application}
 * </ul>
 * @see Application
 */
public class App extends Application {

    //Needed for PositionCalculationThread.java
    public static int eulerLoops = 5000;
    public static CelestialBody[] bodies;
    public static PositionCalculationThread PCT;
    //Needed for PositionCalculationThread.java

    public static int totalSeconds;
    /**
     * Amount of {@code seconds} in the current {@code minute}.
     * @see {@link twentyone.Controllers.SolarScene3DController#minutes minutes}
     */
    public static int seconds = 0;
    /**
     * Amount of {@code minutes} in the current {@code hour}.
     * @see {@link twentyone.Controllers.SolarScene3DController#hours hours}
     */
    public static int minutes = 0;
    /**
     * Amount of {@code hours} in the current {@code day}.
     * @see {@link twentyone.Controllers.SolarScene3DController#days days}
     */
    public static int hours = 0;
    /**
     * Amount of {@code days} in the current {@code month}.
     * @see {@link twentyone.Controllers.SolarScene3DController#months months}
     */
    public static int days = 0;
    /**
     * Amount of {@code months} in the current {@code year}.
     * @see {@link twentyone.Controllers.SolarScene3DController#years years}
     */
    public static int months = 0;
    /**
     * Amount of {@code years}
     */
    public static int years = 0;
    

    private static Scene scene;

    public static double width;
    public static double height;
    public static double resolution;

    public static Vector3d initialPosProbe;
    public static Vector3d initialVelProbe;

    public static int stepSize;
    public static int chosenStepsize;
    
    public static int timeStamp;

    public static int chosenSolver;
    public static musicPlayer MP = new musicPlayer("StarWars.mp3");


    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("fxml/StartScene"));
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");

        Rectangle2D bounds = Screen.getPrimary().getBounds();
        resolution = Toolkit.getDefaultToolkit().getScreenResolution()/100;
        width = bounds.getWidth();
        height = bounds.getHeight();
        System.out.println("width: " + width + " height: " + height);
        stage.setScene(scene);
        stage.setTitle("Titanic Space Odyssey"); 
        stage.show();
    }

    /**
     * Set a new scene.
     * @param fxml (the file name)
     * @throws IOException
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Loads the fxml file.
     * @param fxml (the file name)
     * @return Parent
     * @throws IOException
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Launches the app.
     */
    public static void launcher() {
        launch();
    }

}