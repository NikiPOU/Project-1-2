package twentyone;

import java.io.IOException;
import javafx.fxml.FXML;

public class SolarSceneController {

    @FXML
    private void switchToStart() throws IOException {
        App.setRoot("StartScene");
    }
}