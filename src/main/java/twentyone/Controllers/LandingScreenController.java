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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import twentyone.App;
import twentyone.Classes.Euler;
import twentyone.Classes.UnidentifiedFlyingObject;
import twentyone.Classes.Vector3d;

public class LandingScreenController implements Initializable {

    /**
     * Amount of {@code seconds} in the current {@code minute}.
     * @see {@link twentyone.Controllers.SolarScene3DController#minutes minutes}
     */
    int seconds = 0;
    /**
     * Amount of {@code minutes} in the current {@code hour}.
     * @see {@link twentyone.Controllers.SolarScene3DController#hours hours}
     */
    int minutes = 0;
    /**
     * Amount of {@code hours} in the current {@code day}.
     * @see {@link twentyone.Controllers.SolarScene3DController#days days}
     */
    int hours = 0;
    /**
     * Amount of {@code days} in the current {@code month}.
     * @see {@link twentyone.Controllers.SolarScene3DController#months months}
     */
    int days = 0;
    /**
     * Amount of {@code months} in the current {@code year}.
     * @see {@link twentyone.Controllers.SolarScene3DController#years years}
     */
    int months = 0;
    /**
     * Amount of {@code years}
     */
    int years = 0;

    private Timeline timeline;

    private double screenCenterX;
    private double screenCenterY;

    private UnidentifiedFlyingObject ufo;
    private Euler e;

    @FXML
    private Group Titan;
    @FXML
    private Group Saturn;

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
        ufo = new UnidentifiedFlyingObject(new Vector3d(200, 200, -Math.PI/2),new Vector3d(5.570e-3, 0, 0));
        e = new Euler();

        // convertTime(App.totalSeconds);

        chosenStepsizeMenuItem.setText(chosenStepsizeMenuItem.getText() + App.chosenStepsize);

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
            ufo.feedbackController(e);
            Titan.rotateProperty().set(-ufo.getPosition().getX());
            Saturn.setLayoutX(Saturn.getLayoutX() + ufo.getPosition().getX()*5e-3);
            //Saturn.setLayoutX(Saturn.getLayoutX() - 0.5);
            Saturn.rotateProperty().set(Saturn.getRotate() - 0.05);

            rocket.setLayoutX(screenCenterX-100);
            //System.out.println(ufo.getPosition().getX());
            //System.out.println(ufo.getVelocity().getY());
            rocket.setLayoutY(screenCenterY + 50 - ufo.getPosition().getY());
            //System.out.println(ufo.getPosition().getY());
            rocket.rotateProperty().set(ufo.getPosition().getZ()*180/Math.PI);
            //System.out.println(ufo.getRotation()*180/Math.PI);
            //System.out.println();

            // convertTime(App.totalSeconds);

            timeLabel.setText("Time Elapsed: " + App.years + " years, " + App.months + " months, " + App.days + " days and " + App.hours + "hours");
            speedLabel.setText("Speed: " + -ufo.getVelocity().getX() + " km/s");
            heightLabel.setText("Height: " + ufo.getPosition().getY() + " km");
        }
    }

    /**
     * Sets the used Solver method to Adams Bashfort.
     * @see MenuItem
     * @see twentyone.Classes.AdamsBashforth
     */
    @FXML
    public void onAdamsButton(){
        App.chosenSolver = 0;
    }

    /**
     * Sets the used Solver method to the Euler method.
     * @see MenuItem
     * @see twentyone.Classes.Euler
     */
    @FXML
    public void onEulerButton(){
        App.chosenSolver = 1;
    }

    /**
     * Sets the used Solver method to Verlet.
     * @see MenuItem
     * @see twentyone.Classes.VerletSolver
     */
    @FXML
    public void onVerletButton(){
        App.chosenSolver = 2;
    }

    /**
     * Sets the used Solver method to Runge Kutta.
     * @see MenuItem
     * @see twentyone.Classes.RungeKutta
     */
    @FXML
    public void onRungeButton(){
        App.chosenSolver = 3;
    }

    /**
     * Sets the used Solver method to Adams-Moulton.
     * @see MenuItem
     * @see twentyone.Classes.AdamsMoulton
     */
    @FXML
    public void onMoultonButton(){
        App.chosenSolver = 4;
    }

    /**
     * Sets the stepsize to 1.
     * @see MenuItem
     * @see twentyone.Controllers.SolarScene3DController#stepsize
     */
    @FXML
    public void onStepsize1(){
        App.stepSize = 1;
    }

    /**
     * Sets the stepsize to 10.
     * @see MenuItem
     */
    @FXML
    public void onStepsize10(){
        App.stepSize = 10;
    }

    /**
     * Sets the stepsize to 100.
     * @see MenuItem
     */
    @FXML
    public void onStepsize100(){
        App.stepSize = 100;
    }

    /**
     * Sets the stepsize to the initially chosen stepsize.
     * @see MenuItem
     */
    @FXML
    public void onStepsizeChosen(){
        App.stepSize = App.chosenStepsize;
    }
}
