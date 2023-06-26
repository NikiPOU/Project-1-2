package twentyone.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import twentyone.App;
import twentyone.Classes.Euler;
import twentyone.Classes.LandingModule;
import twentyone.Classes.Vector3d;

public class LandingScreenController implements Initializable {
    /**
     * The used {@code Timeline} to keep the {@code Movement} going.
     * @see Timeline
     * @see {@link twentyone.Controllers.LandingScreenController.Movement Movement}
     */
    private Timeline timeline;
    /**
     * The center of the screen in width (as a {@code double})
     */
    private double screenCenterX;
    /**
     * The center of the screen in height (as a {@code double})
     */
    private double screenCenterY;
    /**
     * The Rocket object as a {@code LandingModule}
     * @see {@link twentyone.Classes.LandingModule UFO}
     */
    private LandingModule ufo;
    /**
     * The {@code Euler Solver}
     * @see twentyone.Classes.Euler
     */
    private Euler e;

    @FXML
    private Group Titan;
    @FXML
    private Group Saturn;

    @FXML
    private AnchorPane mainScreen;

    @FXML
    private ImageView Milkyway;
    @FXML
    private ImageView rocket;

    @FXML
    private Label timeLabel;
    @FXML
    private Label speedLabel;
    @FXML
    private Label heightLabel;

    @FXML
    private MenuItem chosenStepsizeMenuItem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ufo = new LandingModule(new Vector3d(App.titanPos.getX(), App.titanPos.getY(), App.titanPos.getZ()),
                new Vector3d(App.titanVel.getX(), App.titanVel.getY(), App.titanVel.getZ()));
        e = new Euler();

        App.eulerLoops = 60;
        App.stepSize = 1;

        // convertTime(App.totalSeconds);

        chosenStepsizeMenuItem.setText(chosenStepsizeMenuItem.getText() + App.chosenStepsize);

        screenCenterX = App.width / 2;
        screenCenterY = App.height / 2;
        Titan.setLayoutX(screenCenterX);
        Titan.rotateProperty().set(1);
        Saturn.setLayoutX(screenCenterX + 200);
        Saturn.setLayoutY(screenCenterY - 120);

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
    public void onExit() {
        App.PCT.stop();
        System.exit(0);
    }

    @FXML
    public void keyPress(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.Q)) {
            System.out.println("yo");
            try {
                App.goingBack = true;
                App.eulerLoops = 5000;
                App.stepSize = App.chosenStepsize;
                App.setRoot("fxml/SolarScene3D");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

public class Movement implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent event) {
        // Choose in GUI which controller to use
        if (true) {
            ufo.feedbackController(e);
        } //else {
          //  ufo.openLoopController(e, null, null);
        //}
        Titan.rotateProperty().set(-ufo.getPosition().getX());
        // Saturn.setLayoutX(Saturn.getLayoutX() + ufo.getPosition().getX()*5e-3);
        Saturn.setLayoutX(Saturn.getLayoutX() - 0.5);
        Saturn.rotateProperty().set(Saturn.getRotate() - 0.05);

        rocket.setLayoutX(screenCenterX - 100);
        // System.out.println(ufo.getPosition().getX());
        // System.out.println(ufo.getVelocity().getY());
        rocket.setLayoutY(screenCenterY + 50 - ufo.getPosition().getY());
        // System.out.println(ufo.getPosition().getY());
        rocket.rotateProperty().set(ufo.getPosition().getZ() * 180 / Math.PI);
        // System.out.println(ufo.getRotation()*180/Math.PI);
        // System.out.println();

        // convertTime(App.totalSeconds);

        timeLabel.setText("Time Elapsed: " + App.years + " years, " + App.months + " months, " + App.days + " days, "
                + App.hours + " hours, " + App.minutes + " minutes and " + App.seconds + " seconds");
        speedLabel.setText("Speed: " + -ufo.getVelocity().getX() + " km/s");
        heightLabel.setText("Height: " + ufo.getPosition().getY() + " km");

        if(ufo.hasLanded){
            timeline.stop();
            try {
                App.goingBack = true;
                App.eulerLoops = 5000;
                App.stepSize = App.chosenStepsize;
                App.setRoot("fxml/SolarScene3D");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    }

    /**
     * Sets the used Solver method to Adams-Bashforth.
     * 
     * @see MenuItem
     * @see twentyone.Classes.AdamsBashforth
     */
    @FXML
    public void onAdamsButton() {
        App.chosenSolver = 0;
    }

    /**
     * Sets the used Solver method to the Euler method.
     * 
     * @see MenuItem
     * @see twentyone.Classes.Euler
     */
    @FXML
    public void onEulerButton() {
        App.chosenSolver = 1;
    }

    /**
     * Sets the used Solver method to Verlet.
     * 
     * @see MenuItem
     * @see twentyone.Classes.Verlet
     */
    @FXML
    public void onVerletButton() {
        App.chosenSolver = 2;
    }

    /**
     * Sets the used Solver method to Runge Kutta.
     * 
     * @see MenuItem
     * @see twentyone.Classes.RungeKutta
     */
    @FXML
    public void onRungeButton() {
        App.chosenSolver = 3;
    }

    /**
     * Sets the used Solver method to Adams-Moulton.
     * 
     * @see MenuItem
     * @see twentyone.Classes.AdamsMoulton
     */
    @FXML
    public void onMoultonButton() {
        App.chosenSolver = 4;
    }

    /**
     * Sets the stepsize to 1.
     * 
     * @see MenuItem
     * @see twentyone.Controllers.SolarScene3DController#stepsize
     */
    @FXML
    public void onStepsize1() {
        App.stepSize = 1;
    }

    /**
     * Sets the stepsize to 10.
     * 
     * @see MenuItem
     */
    @FXML
    public void onStepsize10() {
        App.stepSize = 10;
    }

    /**
     * Sets the stepsize to 100.
     * 
     * @see MenuItem
     */
    @FXML
    public void onStepsize100() {
        App.stepSize = 100;
    }

    /**
     * Sets the stepsize to the initially chosen stepsize.
     * 
     * @see MenuItem
     */
    @FXML
    public void onStepsizeChosen() {
        App.stepSize = App.chosenStepsize;
    }
}
