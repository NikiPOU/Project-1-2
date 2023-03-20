package twentyone;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SolarSceneController implements Initializable {

    int probeCoords2;
    int distanceTitan2;
    int launchCoords2;
    int launchVelocity2;

    @FXML
    Label probeCoords;
    @FXML
    Label distanceTitan;
    @FXML
    Label launchCoords;
    @FXML
    Label launchVelocity;
    @FXML
    ImageView earth;

    int i = 0;

    private Timeline timeline;

    int[] xs = {355, 601, 355, 140};
    int[] ys = {49, 230, 415, 230};



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        probeCoords.setText("Currect probe coords: " + probeCoords2);
        distanceTitan.setText("Distance to Titan: " + distanceTitan2);
        launchCoords.setText("Launch coords: " + launchCoords2);
        launchVelocity.setText("Launch velocity: " + launchVelocity2);

        //create a timeline for moving the circle
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);

        //one can add a specific action when the keyframe is reached
        EventHandler<ActionEvent> movement = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                earth.setLayoutX(xs[i]);
                earth.setLayoutY(ys[i]);
                i = (i+1)%4;
            }
        };
         
        KeyFrame keyFrame = new KeyFrame(Duration.millis(700), movement);
         
        //add the keyframe to the timeline
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    @FXML
    private void switchToStart() throws IOException {
        App.setRoot("StartScene");
    }
}