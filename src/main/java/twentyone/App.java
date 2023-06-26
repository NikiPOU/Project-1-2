package twentyone;

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
import twentyone.Classes.MusicPlayer;

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
    public static int eulerLoops;
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
    /**
     * Check for when we want to go back from Titan to Earth
     */
    public static boolean goingBack;

    /**
     * A {@code boolean} to check wether we chose the numbers for Titan or not.
     */
    public static boolean titanChosen;
    /**
     * The position of the {@code Rocket} when entering Titan's orbit
     * @see Vector3d
     */
    public static Vector3d titanPos;
    /**
     * The velocity of the {@code Rocket} when entering Titan's orbit
     * @see Vector3d
     */
    public static Vector3d titanVel;
    
    private static Scene scene;

    /**
     * The width of the screen
     */
    public static double width;
    /**
     * The height of the screen
     */
    public static double height;

    /**
     * The initial position of the {@code Rocket} when leaving earth
     * @see Vector3d
     */
    public static Vector3d initialPosProbe;
    /**
     * The initial velocity of the {@code Rocket} when leaving earth
     * @see Vector3d
     */
    public static Vector3d initialVelProbe;

    /**
     * The initial velocity of the {@code Rocket} when going back to earth
     * @see Vector3d
     */
    public static Vector3d goBackVelProbe = new Vector3d(-30, 0, 0);

    /**
     * The current step size (as a {@code Integer})
     */
    public static int stepSize;
    /**
     * The initial chosen step size (as a {@code Integer})
     */
    public static int chosenStepsize;
    
    /**
     * The time stamp for where we want the extra information (as a {@code Integer}) in seconds
     */
    public static int timeStamp;

    /**
     * The chosen {@code Solver}. This could be one of the following:
     * <ul>
     * <li> {@code Adams-Bashforth Solver}
     * <li> {@code Adams-Moulton Solver}
     * <li> {@code Euler Solver}
     * <li> {@code Verlet Solver}
     * <li> {@code Runge-Kutta Solver}
     * </ul>
     * @see twentyone.Classes.AdamsBashforth
     * @see twentyone.Classes.AdamsMoulton
     * @see twentyone.Classes.Euler
     * @see twentyone.Classes.Verlet
     * @see twentyone.Classes.RungeKutta
     */
    public static int chosenSolver;
    /**
     * The music player to play some background music
     * @see MusicPlayer
     */
    public static MusicPlayer MP = new MusicPlayer("StarWars.mp3");


    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("fxml/StartScene"));
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");

        eulerLoops = 5000;

        Rectangle2D bounds = Screen.getPrimary().getBounds();
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