package twentyone.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import twentyone.App;

public class LandingScreenController implements Initializable {

    private Timeline timeline;

    private double screenCenterX;
    private double screenCenterY;

    private double rotationSpeed = 2;

    @FXML
    private Group Titan;
    @FXML
    private Group Saturn;

    @FXML
    private ImageView Milkyway;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        screenCenterX = App.width/2;
        screenCenterY = App.height/2;
        Titan.setLayoutX(screenCenterX);
        Saturn.setLayoutX(screenCenterX+200);
        Saturn.setLayoutY(screenCenterY-120);

        Milkyway.setFitWidth(App.width);
        Milkyway.setFitHeight(App.height);

        Titan.setRotationAxis(Rotate.Z_AXIS);
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
        EventHandler<ActionEvent> movement = new Movement();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(100), movement);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    @FXML
    public void onExit(){
        System.exit(0);
    }

    public class Movement implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            Titan.rotateProperty().set(Titan.getRotate() - rotationSpeed);
            Saturn.setLayoutX(Saturn.getLayoutX() - 0.5);
            Saturn.rotateProperty().set(Saturn.getRotate() - 0.05);

            rotationSpeed -= 0.01;
        }

    }
    
}
