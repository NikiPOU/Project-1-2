package twentyone;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

//1 unit is 15474 km

public class SolarSceneController implements Initializable {

    int probeCoords2;
    int distanceTitan2;
    int launchCoords2;
    int launchVelocity2;

    int sunx = 845;
    int suny = 445;

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
    @FXML
    ImageView spaceprobe;
    @FXML
    AnchorPane root;
    @FXML
    Group dots;

    int i = 180;
    int days = -1;
    int months = -1;
    int years = 0;
    double ex;
    double ey;
    double spax;
    double spay;
    double tx;
    double ty;
    double sx;
    double sy;
    boolean isPressed = false;
    ArrayList<Circle> dotList = new ArrayList<>();

    private Timeline timeline;

    //1 = 2000000

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dots = new Group();
        root.getChildren().add(dots);
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
                ex = 120*Math.cos(Math.toRadians(i))+sunx;
                ey = 120*Math.sin(Math.toRadians(i))+suny;
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
                sx = 700*Math.cos(Math.toRadians(0.034*i))+sunx;
                sy = 700*Math.sin(Math.toRadians(0.034*i))+suny;
                tx = 40*Math.cos(Math.toRadians(22.64*i))+sx+40;
                ty = 40*Math.sin(Math.toRadians(22.64*i))+sy+20;
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
                i++;
            }
        };
        EventHandler<ActionEvent> spaceMovement = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                double satdis = Math.sqrt(((sx-spax)*(sx-spax))+((sy-spay)*(sy-spay)));
                double titandis = Math.sqrt(((tx-spax)*(tx-spax))+((ty-spay)*(ty-spay)));
                double angle;
                if(titandis <= 20){
                    spax = tx + 2;
                    spay = ty + 2;
                    spaceprobe.setLayoutX(spax);
                    spaceprobe.setLayoutY(spay);
                } else if(isPressed){
                    if(satdis > 40){
                        double dx = sx - spax;
                        double dy = sy - spay;
                        angle = Math.atan2(dy, dx);
                        spax += 2*Math.cos(angle);
                        spay += 2*Math.sin(angle);
                        spaceprobe.setLayoutX(spax);
                        spaceprobe.setLayoutY(spay);
                        spaceprobe.setRotate(Math.toDegrees(angle)+90);
                    } else {
                        double dx = tx - spax;
                        double dy = ty - spay;
                        angle = Math.atan2(dy, dx);
                        spax += 2*Math.cos(angle);
                        spay += 2*Math.sin(angle);
                        spaceprobe.setLayoutX(spax);
                        spaceprobe.setLayoutY(spay);
                        spaceprobe.setRotate(Math.toDegrees(angle)+90);
                    }
                    Circle circle = new Circle();
                    circle.setCenterX(spax+10*Math.sin(angle));
                    circle.setCenterY(spay+10*Math.cos(angle));
                    circle.setRadius(2);
                    circle.setFill(Color.LIME);
                    circle.setOpacity(0.4);
                    dotList.add(circle);
                    dots.getChildren().add(circle);
                } else {
                    spax = ex+2;
                    spay = ey+2;
                    spaceprobe.setLayoutX(spax);
                    spaceprobe.setLayoutY(spay);
                }
                probeCoords.setText("Currect probe coords: " + spax + " " + spay);
                distanceTitan.setText("Distance to Titan: " + titandis);
            }
            
        };
         
        KeyFrame keyFrame = new KeyFrame(Duration.millis(100), movement);
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(100), spaceMovement);
         
        //add the keyframe to the timeline
        timeline.getKeyFrames().add(keyFrame);
        timeline.getKeyFrames().add(keyFrame2);
        timeline.play();
    }

    @FXML
    public void spaceHandler(KeyEvent ke) {
        if(ke.getCode().equals(KeyCode.BACK_SPACE)){
            isPressed = true;
        } else if(ke.getCode().equals(KeyCode.R)){
            spax = ex + 2;
            spay = ey + 2;
            isPressed = false;
        }
    };

    @FXML
    private void switchToStart() throws IOException {
        App.setRoot("StartScene");
    }
}
