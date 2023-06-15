package twentyone.Controllers;

import java.beans.VetoableChangeSupport;
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
import twentyone.Classes.UnidentifiedFlyingObject;

public class LandingScreenController implements Initializable {

    private Timeline timeline;

    private double screenCenterX;
    private double screenCenterY;

    private UnidentifiedFlyingObject ufo;

    @FXML
    private Group Titan;
    @FXML
    private Group Saturn;

    @FXML
    private ImageView Milkyway;
    @FXML
    private ImageView rocket;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ufo = new UnidentifiedFlyingObject();

        screenCenterX = App.width/2;
        screenCenterY = App.height/2;
        Titan.setLayoutX(screenCenterX);
        Titan.rotateProperty().set(1);
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
            ufo.feedbackController();
            Titan.rotateProperty().set(-ufo.getPosition().getX());
            Saturn.setLayoutX(Saturn.getLayoutX() + ufo.getPosition().getX()*5e-3);
            Saturn.rotateProperty().set(Saturn.getRotate() - 0.05);

            rocket.setLayoutX(screenCenterX-100);
            //System.out.println(ufo.getPosition().getX());
            System.out.println(ufo.getVelocity().getY());
            rocket.setLayoutY(screenCenterY + 50 - ufo.getPosition().getY());
            //System.out.println(ufo.getPosition().getY());
            rocket.rotateProperty().set(ufo.getRotation()*180/Math.PI);
            //System.out.println(ufo.getRotation()*180/Math.PI);
            //System.out.println();
        }
    }
}
