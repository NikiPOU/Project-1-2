package twentyone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    public static double width;
    public static double height;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("fxml/StartScene"));
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        Rectangle2D bounds = Screen.getPrimary().getBounds();
        width = bounds.getWidth();
        height = bounds.getHeight();
        System.out.println("width: " + width + " height: " + height);
        stage.setScene(scene);
        stage.setTitle("Titanic Space Odyssey"); 
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void launcher() {
        launch();
    }

}