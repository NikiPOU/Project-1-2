package twentyone;

import java.awt.Toolkit;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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

        // initialPosProbe = new Vector3d(-148186906.893642 + 6370, -27823158.5715694, 33746.8987977113);
        // initialVelProbe = new Vector3d(48, -45, 0);

        // stepSize = 10;

        DisplayMode mode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();

        // Rectangle2D bounds = Screen.getPrimary().getBounds();
        resolution = Toolkit.getDefaultToolkit().getScreenResolution()/100;
        // width = bounds.getWidth();
        // height = bounds.getHeight();
        width = mode.getWidth();
        height = mode.getHeight();
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