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

    int sunx = 800;
    int suny = 400;

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
    @FXML
    ImageView moon;
    @FXML
    ImageView mercury;
    @FXML
    ImageView venus;
    @FXML
    ImageView mars;
    @FXML
    ImageView jupiter;
    @FXML
    ImageView saturn;

    int i = 180;

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
                double ex = 300*Math.cos(Math.toRadians(i))+sunx;
                double ey = 300*Math.sin(Math.toRadians(i))+suny;
                double mx = 50*Math.cos(Math.toRadians(12*i))+ex+20;
                double my = 50*Math.sin(Math.toRadians(12*i))+ey+10;
                double mex = 80*Math.cos(Math.toRadians(4*i))+sunx+30;
                double mey = 80*Math.sin(Math.toRadians(4*i))+suny+50;
                double vx = 170*Math.cos(Math.toRadians(1.7*i))+sunx;
                double vy = 170*Math.sin(Math.toRadians(1.7*i))+suny;
                double max = 380*Math.cos(Math.toRadians(0.52*i))+sunx;
                double may = 380*Math.sin(Math.toRadians(0.52*i))+suny;
                double jx = 550*Math.cos(Math.toRadians(0.08*i))+sunx;
                double jy = 550*Math.sin(Math.toRadians(0.08*i))+suny;
                double sx = 700*Math.cos(Math.toRadians(0.03*i))+sunx;
                double sy = 700*Math.sin(Math.toRadians(0.03*i))+suny;
                earth.setLayoutX(ex);
                earth.setLayoutY(ey);
                moon.setLayoutX(mx);
                moon.setLayoutY(my);
                mercury.setLayoutX(mex);
                mercury.setLayoutY(mey);
                venus.setLayoutX(vx);
                venus.setLayoutY(vy);
                mars.setLayoutX(max);
                mars.setLayoutY(may);
                jupiter.setLayoutX(jx);
                jupiter.setLayoutY(jy);
                saturn.setLayoutX(sx);
                saturn.setLayoutY(sy);
                i++;
            }
        };
         
        KeyFrame keyFrame = new KeyFrame(Duration.millis(100), movement);
         
        //add the keyframe to the timeline
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    @FXML
    private void switchToStart() throws IOException {
        App.setRoot("StartScene");
    }
}