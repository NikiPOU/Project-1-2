package twentyone.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import twentyone.App;
import twentyone.Classes.musicPlayer;

public class StartSceneController implements Initializable{


    @FXML
    private void switchToSolar() throws IOException {
        // App.setRoot("fxml/SolarScene3D");
        App.setRoot("fxml/solverChooserScreen");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        App.MP.run();
    }
}
