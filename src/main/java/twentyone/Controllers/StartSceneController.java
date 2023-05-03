package twentyone.Controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import twentyone.App;

public class StartSceneController {

    @FXML
    private void switchToSolar() throws IOException {
        App.setRoot("fxml/SolarScene3D");
    }
}
