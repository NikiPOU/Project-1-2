package twentyone.Controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import twentyone.App;

public class solverChooserScreenController {
    
    @FXML
    public void onAdamsButton() throws IOException{
        App.chosenSolver = 0;
        App.MP.fadeOut();
        // App.setRoot("fxml/SolarScene3D");
        App.setRoot("fxml/numberChooserScreen");
    }

    @FXML
    public void onEulerButton() throws IOException{
        App.chosenSolver = 1;
        App.MP.fadeOut();
        App.setRoot("fxml/SolarScene3D");
    }

    @FXML
    public void onVerletButton() throws IOException{
        App.chosenSolver = 2;
        App.MP.fadeOut();
        App.setRoot("fxml/SolarScene3D");
    }

    @FXML
    public void onRungeButton() throws IOException{
        App.chosenSolver = 0;
        App.MP.fadeOut();
        App.setRoot("fxml/SolarScene3D");
    }
}
