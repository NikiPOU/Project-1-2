package twentyone;

import java.io.IOException;
import javafx.fxml.FXML;

public class StartSceneController {

    @FXML
    private void switchToSolar() throws IOException {
        App.setRoot("SolarScene");
    }
}
