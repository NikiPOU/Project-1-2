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

//1 unit is 15474 km

public class SolarSceneController implements Initializable {

    int probeCoords2;
    int distanceTitan2;
    int launchCoords2;
    int launchVelocity2;

    int sunx = 840;
    int suny = 440;

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
    @FXML
    ImageView titan;
    @FXML
    Label TimeElapsed;

    int i = 180;
    int days = -1;
    int months = -1;
    int years = 0;

    private Timeline timeline;

    int[] xs = {355, 601, 355, 140};
    int[] ys = {49, 230, 415, 230};

    //1 = 2000000



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
                double ex = 120*Math.cos(Math.toRadians(i))+sunx;
                double ey = 120*Math.sin(Math.toRadians(i))+suny;
                double mx = 15*Math.cos(Math.toRadians(12*i))+ex+7;
                double my = 15*Math.sin(Math.toRadians(12*i))+ey+7;
                double mex = 59*Math.cos(Math.toRadians(4*i))+sunx;
                double mey = 59*Math.sin(Math.toRadians(4*i))+suny;
                double vx = 80*Math.cos(Math.toRadians(1.7*i))+sunx;
                double vy = 80*Math.sin(Math.toRadians(1.7*i))+suny;
                double max = 180*Math.cos(Math.toRadians(0.52*i))+sunx;
                double may = 180*Math.sin(Math.toRadians(0.52*i))+suny;
                double jx = 419*Math.cos(Math.toRadians(0.08*i))+sunx;
                double jy = 419*Math.sin(Math.toRadians(0.08*i))+suny;
                double sx = 700*Math.cos(Math.toRadians(0.034*i))+sunx;
                double sy = 700*Math.sin(Math.toRadians(0.034*i))+suny;
                double tx = 40*Math.cos(Math.toRadians(22.64*i))+sx+40;
                double ty = 40*Math.sin(Math.toRadians(22.64*i))+sy+20;
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
                titan.setLayoutX(tx);
                titan.setLayoutY(ty);
                days++;
                if(days % 30 == 0){
                    months++;
                    days = 0;
                    if(months/12 == 1){
                        months = 0;
                        years++;
                    }
                }
                TimeElapsed.setText("Time Elapsed: " + years + " years, " + months + " months and " + days + " days");
                probeCoords.setText("Currect probe coords: " + ex + " " + ey);
                distanceTitan.setText("Distance to Titan: " + Math.sqrt(((tx-ex)*(tx-ex))+((ty-ey)*(ty-ey))));
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