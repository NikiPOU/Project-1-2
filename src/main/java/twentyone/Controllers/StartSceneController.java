package twentyone.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import twentyone.App;
import twentyone.Classes.musicPlayer;

public class StartSceneController implements Initializable{

    //musicPlayer MP;

    @FXML
    private void switchToSolar() throws IOException {
        //MP.fadeOut();
        App.setRoot("fxml/SolarScene");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //MP = new musicPlayer("StarWars.mp3");
        //MP.run();
    }
}
