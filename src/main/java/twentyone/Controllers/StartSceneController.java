package twentyone.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import twentyone.App;

public class StartSceneController implements Initializable{


    /**
     * When the button is pressed, the scene will be changed to the next screen.
     * @throws IOException
     * @see javafx.scene.control.Button
     */
    @FXML
    private void switchToSolar() throws IOException {
        App.setRoot("fxml/solverScreen");
    }

    /**
     * This method will run as soon as the scene is started. In this case, only the music will be started.
     * @see twentyone.Classes.MusicPlayer
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        App.MP.run();
    }
}
