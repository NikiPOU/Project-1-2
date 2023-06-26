package twentyone.Controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import twentyone.App;
import twentyone.Classes.AdamsBashforth;
import twentyone.Classes.AdamsMoulton;
import twentyone.Classes.CelestialBody;
import twentyone.Classes.Euler;
import twentyone.Classes.PositionCalculationThread;
import twentyone.Classes.Rocket;
import twentyone.Classes.RungeKutta;
import twentyone.Classes.Vector3d;
import twentyone.Classes.Verlet;
import twentyone.Classes.MusicPlayer;

public class SolarScene3DController implements Initializable {

    /**
     * Is true if the rocket has been around Titan already.
     */
    private boolean titanOrbit = false;

    /**
     * The {@code Adams-Bashforth Solver}
     * @see twentyone.Classes.AdamsBashforth
     */
    private AdamsBashforth a = new AdamsBashforth();
    /**
     * The {@code Euler Solver}
     * @see twentyone.Classes.Euler
     */
    private Euler unreal = new Euler();
    /**
     * The {@code Verlet Solver}
     * @see twentyone.Classes.Verlet
     */
    private Verlet verlet = new Verlet();
    /**
     * The {@code Runge-Kutta Solver}
     * @see twentyone.Classes.RungeKutta
     */
    private RungeKutta rungeKutta = new RungeKutta();
    /**
     * The {@code Adams-Moulton Solver}
     * @see twentyone.Classes.AdamsMoulton
     */
    private AdamsMoulton aMoulton = new AdamsMoulton();

    /**
     * The {@code Music Player}
     * @see twentyone.Classes.MusicPlayer
     */
    private MusicPlayer MP;
    /**
     * The chosen {@code Solver}. This could be one of the following:
     * <ul>
     * <li> {@code Adams-Bashforth Solver}
     * <li> {@code Adams-Moulton Solver}
     * <li> {@code Euler Solver}
     * <li> {@code Verlet Solver}
     * <li> {@code Runge-Kutta Solver}
     * </ul>
     * @see twentyone.Classes.AdamsBashforth
     * @see twentyone.Classes.AdamsMoulton
     * @see twentyone.Classes.Euler
     * @see twentyone.Classes.Verlet
     * @see twentyone.Classes.RungeKutta
     */
    private int chosenSolver = App.chosenSolver;
    /**
     * The intitial {@code position} of the {@code Probe}
     */
    final Vector3d initialPosProbe = App.initialPosProbe;
    /**
     * The intitial {@code velocity} of the {@code Probe}
     */
    final Vector3d initialVelProbe = App.initialVelProbe;
    
    /**
     * The current {@code position} of the {@code Probe}
     */
    Vector3d firstprobepos;
    /**
     * The selected planet is a {@code CelestialBody}. This will be used to focus the camera on that {@code CelestialBody}
     * @see twentyone.Classes.CelestialBody
     * @see {@link twentyone.Controllers.SolarScene3DController#focusCamera(CelestialBody) focusCamera(CelestialBody)}
     * @see {@link twentyone.Controllers.SolarScene3DController#sunFocus() sunFocus()}
     * @see {@link twentyone.Controllers.SolarScene3DController#mercuryFocus() mercuryFocus()}
     * @see {@link twentyone.Controllers.SolarScene3DController#venusFocus() venusFocus()}
     * @see {@link twentyone.Controllers.SolarScene3DController#earthFocus() earthFocus()}
     * @see {@link twentyone.Controllers.SolarScene3DController#marsFocus() marsFocus()} 
     * @see {@link twentyone.Controllers.SolarScene3DController#jupiterFocus() jupiterFocus()} 
     * @see {@link twentyone.Controllers.SolarScene3DController#saturnFocus() saturnFocus()}
     * @see {@link twentyone.Controllers.SolarScene3DController#probeFocus() probeFocus()}
     */
    CelestialBody selectedPlanet;

    /**
     * The used {@code Timeline} to keep the {@code Movement} going.
     * @see Timeline
     * @see {@link twentyone.Controllers.SolarScene3DController.Movement Movement}
     */
    private Timeline timeline;
    /**
     * The array of {@code Celestial Bodies} which stores all the used {@code Celestial Bodies}. They get used for the calculations.
     * @see CelestialBody
     */
    CelestialBody[] bodies = new CelestialBody[12];
    /**
     * The current {@code stepsize}. It can be changed using {@code MenuItems}.
     * @see MenuItem
     * @see {@link twentyone.Controllers.SolarScene3DController#onStepsize1() onStepsize1()}
     * @see {@link twentyone.Controllers.SolarScene3DController#onStepsize10() onStepsize10()}
     * @see {@link twentyone.Controllers.SolarScene3DController#onStepsize100() onStepsize100()}
     * @see {@link twentyone.Controllers.SolarScene3DController#onStepsizeChosen() onStepsizeChosen()}
     */
    private double stepsize = App.stepSize;
    /**
     * The chosen amount of {@code loops}. This is the amount of times the next {@code position} gets calculated before updating the GUI.
     * @see {@link twentyone.Controllers.SolarScene3DController.Movement#handle(ActionEvent) handle}
     */
    int eulerLoops = 5000;

    /**
     * A {@code boolean} to see if the camera is focused.
     */
    boolean focused;
    /**
     * A {@code boolean} to see if the camera is already reset.
     */
    boolean resetCheck;
    
    /**
     * The closest distance from the {@code Probe} and the {@code CelestialBody} {@code Titan}.
     */
    double closestTitan = 10E40;

    /**
     * The {@code times tamp} chosen by the user.
     * @see {@link twentyone.Controllers.NumberScreenController#setTimestamp() setTimestamp()}
     */
    int TimeStamp = App.timeStamp;
    /**
     * A {@code boolean} to see if the {@code time stamp} has already been reached.
     * @see {@link twentyone.Controllers.SolarScene3DController#TimeStamp TimeStamp}
     */
    boolean timestampCheck = true;

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
    /**
     * The amount the actual {@code positions} get divided by to fit the GUI.
     */
    int divider = 2100000;

    /**
     * An {@code doulbe[]} of the positions of the sun.
     */
    double[] sunPos = new double[3];
    /**
     * An {@code doulbe[]} of the positions of mercury.
     */
    double[] merPos = new double[3];
    /**
     * An {@code doulbe[]} of the positions of venus.
     */
    double[] venPos = new double[3];
    /**
     * An {@code doulbe[]} of the positions of the earth.
     */
    double[] earPos = new double[3];
    /**
     * An {@code doulbe[]} of the positions of the moon.
     */
    double[] mooPos = new double[3];
    /**
     * An {@code doulbe[]} of the positions of mars.
     */
    double[] marPos = new double[3];
    /**
     * An {@code doulbe[]} of the positions of jupiter.
     */
    double[] jupPos = new double[3];
    /**
     * An {@code doulbe[]} of the positions of saturn.
     */
    double[] satPos = new double[3];
    /**
     * An {@code doulbe[]} of the positions of titan.
     */
    double[] titPos = new double[3];

    /**
     * The current time in seconds. This one won't be reset to {@code 0} in contrary to {@link twentyone.Controllers.SolarScene3DController#seconds seconds}.
     */
    int k=0;

    /**
     * The last position of the space probe. This is used to show the path of the probe.
     */
    double[] lastSpacePos = new double[3];
    

    @FXML
    private Scene scene;
    /**
     * The group of all the planets.
     * @see CelestialBody
     * @see {@link twentyone.Controllers.SolarScene3DController#bodies bodies}
     */
    @FXML
    private AnchorPane pGroup;
    @FXML
    private PerspectiveCamera pCamera;

    /**
     * The group of the {@code Sphere} and {@code Text (as ImageView)} of the sun
     * @see Sphere
     * @see {@link javafx.scene.image.ImageView ImageView}
     */
    @FXML
    private Group sun;
    /**
     * The 3D representation of the sun. 
     * @see Sphere
     */
    @FXML
    private Sphere sunSphere;
    /**
     * The group of the {@code Sphere} and {@code Text (as ImageView)} of earth
     * @see Sphere
     * @see {@link javafx.scene.image.ImageView ImageView}
     */
    @FXML
    private Group earth;
    /**
     * The 3D representation of earth. 
     * @see Sphere
     */
    @FXML
    private Sphere earthSphere;
    /**
     * The group of the {@code Sphere} and {@code Text (as ImageView)} of the moon
     * @see Sphere
     * @see {@link javafx.scene.image.ImageView ImageView}
     */
    @FXML
    private Group moon;
    /**
     * The 3D representation of the moon. 
     * @see Sphere
     */
    @FXML 
    private Sphere moonSphere;
    /**
     * The group of the {@code Sphere} and {@code Text (as ImageView)} of mercury
     * @see Sphere
     * @see {@link javafx.scene.image.ImageView ImageView}
     */
    @FXML
    private Group mercury;
    /**
     * The 3D representation of mercury. 
     * @see Sphere
     */
    @FXML
    private Sphere mercurySphere;
    /**
     * The group of the {@code Sphere} and {@code Text (as ImageView)} of venus
     * @see Sphere
     * @see {@link javafx.scene.image.ImageView ImageView}
     */
    @FXML
    private Group venus;
    /**
     * The 3D representation of venus. 
     * @see Sphere
     */
    @FXML
    private Sphere venusSphere;
    /**
     * The group of the {@code Sphere} and {@code Text (as ImageView)} of mars
     * @see Sphere
     * @see {@link javafx.scene.image.ImageView ImageView}
     */
    @FXML
    private Group mars;
    /**
     * The 3D representation of mars. 
     * @see Sphere
     */
    @FXML
    private Sphere marsSphere;
    /**
     * The group of the {@code Sphere} and {@code Text (as ImageView)} of jupiter
     * @see Sphere
     * @see {@link javafx.scene.image.ImageView ImageView}
     */
    @FXML
    private Group jupiter;
    /**
     * The 3D representation of jupiter. 
     * @see Sphere
     */
    @FXML
    private Sphere jupiterSphere;
    /**
     * The group of the {@code Sphere} and {@code Text (as ImageView)} of saturn
     * @see Sphere
     * @see {@link javafx.scene.image.ImageView ImageView}
     */
    @FXML
    private Group saturn;
    /**
     * The 3D representation of saturn. 
     * @see Sphere
     */
    @FXML
    private Sphere saturnSphere;
    /**
     * The group of the {@code Sphere} and {@code Text (as ImageView)} of titan
     * @see Sphere
     * @see {@link javafx.scene.image.ImageView ImageView}
     */
    @FXML
    private Group titan;
    /**
     * The 3D representation of titan. 
     * @see Sphere
     */
    @FXML
    private Sphere titanSphere;

    @FXML
    private Label launchCoords;
    @FXML
    private Label launchVelocity;
    @FXML
    private Label TimeElapsed;
    @FXML
    private Label probeCoords;
    @FXML
    private Label distanceTitan;
    @FXML
    private Label titanMoment;
    @FXML
    private Label closestdistanceTitan;
    @FXML
    private Group timestampGroup;
    @FXML
    private Label timestampLabel;
    @FXML
    private Label positionLabel;
    @FXML
    private Label distanceLabel;
    @FXML
    private Label totalDistanceLabel;

    @FXML
    private AnchorPane probe;
    @FXML
    private Box fire1;
    @FXML
    private Box fire2;
    @FXML
    private Box fire3;
    @FXML
    private Box fire4;
    @FXML
    private Box fire5;
    @FXML
    private Box fire6;
    @FXML
    private Box fire7;
    @FXML
    private Box fire8;
    @FXML
    private Group fire;

    @FXML
    private MenuItem stepsizeButton;

    @FXML
    private AnchorPane mainScreen;
    @FXML
    private Group path;
    @FXML
    private ImageView milkyway;
    @FXML
    private VBox data;
    @FXML
    private VBox menu;
    
    /**
     * Starts when the scene is started.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stepsizeButton.setText(stepsizeButton.getText() + App.chosenStepsize);

        lastSpacePos[0] = 5E40;

        sunPos[0] = (App.width)/2;
        sunPos[1] = (App.height)/2;
        sunPos[2] = 0;
        focused = false;
        resetCheck = false;
        path = new Group();
        pGroup.getChildren().add(path);
        pGroup.setTranslateX(sunPos[0]);
        pGroup.setTranslateY(sunPos[1]); 
        pGroup.setTranslateZ(sunPos[2]);
        milkyway.setFitWidth(App.width);
        milkyway.setFitHeight(App.height);
        launchCoords.sceneProperty().addListener((Observable, oldScene, newScene) -> {
            if(newScene != null){
                scene = launchCoords.getScene();
                pCamera = new PerspectiveCamera(true);
                scene.setCamera(pCamera);
                pCamera.setFieldOfView(100);
                pCamera.setFarClip(6000);
                pCamera.setNearClip(0.01);
                pCamera.setTranslateZ(-400);
                pCamera.setTranslateX(sunPos[0]);
                pCamera.setTranslateY(sunPos[1]);
                Stage stage = (Stage) launchCoords.getScene().getWindow();
                stage.addEventHandler(ScrollEvent.SCROLL, event -> {
                    if(!selectedPlanet.equals(bodies[0])){
                        resetCheck = true;
                        focused = false;
                        selectedPlanet = bodies[0];
                    }
                    double delta = event.getDeltaY();
                    pGroup.setTranslateZ(pGroup.getTranslateZ() + delta);
                });
                String sunString = "";
                String mercuryString = "";
                String venusString = "";
                String earthString = "";
                String moonString = "";
                String marsString = "";
                String jupiterString = "";
                String saturnString = "";
                String titanString = "";
                try {
                    sunString = (new File("src/main/resources/twentyone/Images/sunMap2k.jpg").toURI().toURL()).toString();
                    mercuryString = (new File("src/main/resources/twentyone/Images/mercuryMap2k.jpg").toURI().toURL()).toString();
                    venusString = (new File("src/main/resources/twentyone/Images/venusMap2k.jpg").toURI().toURL()).toString();
                    earthString = (new File("src/main/resources/twentyone/Images/earthMap2k.jpg").toURI().toURL()).toString();
                    moonString = (new File("src/main/resources/twentyone/Images/moonMap2k.jpg").toURI().toURL()).toString();
                    marsString = (new File("src/main/resources/twentyone/Images/marsMap2k.jpg").toURI().toURL()).toString();
                    jupiterString = (new File("src/main/resources/twentyone/Images/jupiterMap2k.jpg").toURI().toURL()).toString();
                    saturnString = (new File("src/main/resources/twentyone/Images/saturnMap2k.jpg").toURI().toURL()).toString();
                    titanString = (new File("src/main/resources/twentyone/Images/Titan.png").toURI().toURL()).toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                sunSphere.setMaterial(phongMaker(sunString));
                mercurySphere.setMaterial(phongMaker(mercuryString));
                venusSphere.setMaterial(phongMaker(venusString));
                earthSphere.setMaterial(phongMaker(earthString));
                moonSphere.setMaterial(phongMaker(moonString));
                marsSphere.setMaterial(phongMaker(marsString));
                jupiterSphere.setMaterial(phongMaker(jupiterString));
                saturnSphere.setMaterial(phongMaker(saturnString));
                titanSphere.setMaterial(phongMaker(titanString));
                
                fire1.setMaterial(new PhongMaterial(Color.RED));
                fire2.setMaterial(new PhongMaterial(Color.RED));
                fire3.setMaterial(new PhongMaterial(Color.RED));
                fire4.setMaterial(new PhongMaterial(Color.RED));
                fire5.setMaterial(new PhongMaterial(Color.ORANGE));
                fire6.setMaterial(new PhongMaterial(Color.ORANGE));
                fire7.setMaterial(new PhongMaterial(Color.ORANGE));
                fire8.setMaterial(new PhongMaterial(Color.YELLOW));
            }
        });
        setPlanetRotation(mercurySphere, 0);
        setPlanetRotation(venusSphere, 177.3);
        setPlanetRotation(earthSphere, 23.26);
        setPlanetRotation(marsSphere, 25.19);
        setPlanetRotation(jupiterSphere, 3.13);
        setPlanetRotation(saturnSphere, 26.73);
        setPlanetRotation(sunSphere, 7.25);
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
        EventHandler<ActionEvent> movement = new Movement();
        if(App.goingBack){
            App.PCT.stop();
            bodies = App.bodies;
            setReturnRocket();
        } else {
            initiateCB();
        }
        selectedPlanet = bodies[0];
        KeyFrame keyFrame = new KeyFrame(Duration.millis(300), movement);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        if(App.MP.isRunning()){

        } else {
            App.MP = new MusicPlayer("Interstellar.mp3");
            App.MP.run();
        }
    }

    /**
     * Sets the angle of the given {@code planet} and then sets the {@code Rotation axis} to the Y-axis.
     * @param planet as a {@code Sphere} - The given planet
     * @param angle as a {@code double} - The given angle
     * @see Sphere
     * @see Rotate
     */
    private void setPlanetRotation(Sphere planet, double angle){
        planet.setRotationAxis(Rotate.X_AXIS);
        planet.rotateProperty().set(angle);
        planet.setRotationAxis(Rotate.Y_AXIS);
    }

    /**
     * Sets the {@code PhongMaterial} of a 3D Object.
     * @param url this is the {@code URL} of the image used. This is a {@code String}
     * @return a {@code PhongMaterial}
     * @see URL
     * @see PhongMaterial
     * @see Image
     */
    private PhongMaterial phongMaker(String url){
        PhongMaterial phong = new PhongMaterial();
        Image image = new Image(url);
        phong.setDiffuseMap(image);
        return phong;
    }

    /**
     * Sets the position of the {@code pGroup}.
     * @param x as a {@code double}
     * @param y as a {@code double}
     * @param z as a {@code double}
     * @see {@link twentyone.Controllers.SolarScene3DController#pGroup pGroup}
     */
    public void setCameraPos(double x, double y, double z){
        pGroup.setTranslateX(x);
        pGroup.setTranslateY(y);
        pGroup.setTranslateZ(z);
    }

    /**
     * Used to focus on certain {@code Celestial Bodies}.
     * @param planet as a {@code CelestialBody}
     */
    public void focusCamera(CelestialBody planet){
        if(planet.equals(bodies[0])){
            focused = false;
            setCameraPos(sunPos[0], sunPos[1], sunPos[2]);
        } else if(planet.equals(bodies[1])){
            focused = true;
            setCameraPos(sunPos[0] - mercury.getTranslateX(), sunPos[1] - mercury.getTranslateY(), -200);
        } else if(planet.equals(bodies[2])){
            focused = true;
            setCameraPos(sunPos[0] - venus.getTranslateX(), sunPos[1] - venus.getTranslateY(), -200);
        } else if(planet.equals(bodies[3])){
            focused = true;
            setCameraPos(sunPos[0] - earth.getTranslateX(), sunPos[1] - earth.getTranslateY(), -200);
        } else if(planet.equals(bodies[5])){
            focused = true;
            setCameraPos(sunPos[0] - mars.getTranslateX(), sunPos[1] - mars.getTranslateY(), -200);
        } else if(planet.equals(bodies[6])){
            focused = true;
            setCameraPos(sunPos[0] - jupiter.getTranslateX(), sunPos[1] - jupiter.getTranslateY(), -200);
        } else if(planet.equals(bodies[7])){
            focused = true;
            setCameraPos(sunPos[0] - saturn.getTranslateX(), sunPos[1] - saturn.getTranslateY(), -200);
        } else if(planet.equals(bodies[11])){
            focused = true;
            setCameraPos(sunPos[0] - probe.getTranslateX(), sunPos[1] - probe.getTranslateY(), -300);
            
        } else {setCameraPos(sunPos[0], sunPos[1], sunPos[2]);}
    }

    /**
     * When a key gets pressed, only "w", "a", "s", "d", "r", "v", "." and ","
     * @param ke as a {@link KeyEvent}
     */
    @FXML
    public void keyPress(KeyEvent ke) {
        if(ke.getCode().equals(KeyCode.PERIOD) || ke.getCode().equals(KeyCode.COMMA) || ke.getCode().equals(KeyCode.F) || ke.getCode().equals(KeyCode.V) || ke.getCode().equals(KeyCode.Q)){
            if(ke.getCode().equals(KeyCode.PERIOD)){
                if(eulerLoops == 5000){
                    eulerLoops = 10000;
                    App.MP.speedUp();
                } else if(eulerLoops == 10000){
                    eulerLoops = 20000;
                    App.MP.speedUp();
                } else {}
            } else if(ke.getCode().equals(KeyCode.COMMA)){
                if(eulerLoops == 20000){
                    eulerLoops = 10000;
                    App.MP.speedDown();
                } else if(eulerLoops == 10000){
                    eulerLoops = 5000;
                    App.MP.speedDown();
                } else {}
            } else if(ke.getCode().equals(KeyCode.F)){
                if(fire.isVisible()){
                    fire.setVisible(false); 
                } else {
                    fire.setVisible(true);
                }
            } else if(ke.getCode().equals(KeyCode.V)){
                if(path.isVisible()){
                    path.setVisible(false); 
                } else {
                    path.setVisible(true);
                }
            } else if(ke.getCode().equals(KeyCode.Q)){
                enterTitanOrbit();
            }
        } else {
            if(focused){
                focused = false;
                resetCheck = true;
                selectedPlanet = bodies[0];
            }
            if(ke.getCode().equals(KeyCode.W)){
                pGroup.setTranslateY(pGroup.getTranslateY() - 100);
            } else if(ke.getCode().equals(KeyCode.S)){
                pGroup.setTranslateY(pGroup.getTranslateY() + 100);
            } else if(ke.getCode().equals(KeyCode.A)){
                pGroup.setTranslateX(pGroup.getTranslateX() - 100);
            } else if(ke.getCode().equals(KeyCode.D)){
                pGroup.setTranslateX(pGroup.getTranslateX() + 100);
            } else if(ke.getCode().equals(KeyCode.R)){
                pGroup.setTranslateX(sunPos[0]);
                pGroup.setTranslateY(sunPos[1]); 
                pGroup.setTranslateZ(sunPos[2]);
            }
        }
    }

    private void setReturnRocket(){
        bodies[11].setNewVelocity(App.goBackVelProbe);
        bodies[11].setNewPostion(bodies[8].getPosition());
    }

    private void enterTitanOrbit(){
        try {
            if(App.titanChosen){
                titanOrbit = true;
                App.bodies = bodies;
                App.PCT = new PositionCalculationThread();
                Thread thread = new Thread(App.PCT);
                thread.start();
                timeline.stop();
                App.setRoot("fxml/LandingScreen");
            } else {
                App.bodies = bodies;
                timeline.stop();
                App.setRoot("fxml/TitanChooserScreen");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initiates all the {@code Celestial Bodies}. It gives the correct {@code positions}, {@code velocities} and {@code masses}.
     * @see Vector3d
     */
    private void initiateCB(){
        Vector3d sunvel = new Vector3d(0,0,0);
        Vector3d sunpos = new Vector3d(0,0,0);

        Vector3d merpos = new Vector3d( 7833268.43923962,  44885949.3703908,  2867693.20054382);
        Vector3d mervel = new Vector3d(-57.4967480139828, 11.52095127176, 6.21695374334136);

        Vector3d venusvel = new Vector3d(-34.0236737066136, -8.96521274688838, 1.84061735279188);
        Vector3d venuspos = new Vector3d( -28216773.9426889,  103994008.541512,  3012326.64296788);
        
        Vector3d earthvel = new Vector3d(5.05251577575409, -29.3926687625899,  0.00170974277401292);
        Vector3d earthpos = new Vector3d( -148186906.893642,  -27823158.5715694,  33746.8987977113);

        Vector3d moonvel = new Vector3d(4.34032634654904, -30.0480834180741, -0.0116103535014229);
        Vector3d moonpos = new Vector3d( -148458048.395164,  -27524868.1841142,  70233.6499287411);

        Vector3d marsvel = new Vector3d(-17.6954469224752, -13.4635253412947, 0.152331928200531);
        Vector3d marspos = new Vector3d( -159116303.422552,  189235671.561057,  7870476.08522969);

        Vector3d jupivel = new Vector3d(-4.71443059866156, 12.8555096964427, 0.0522118126939208);
        Vector3d jupipos = new Vector3d( 692722875.928222,  258560760.813524,  -16570817.7105996);

        Vector3d satuvel = new Vector3d(4.46781341335014, 8.23989540475628,  -0.320745376969732);
        Vector3d satupos = new Vector3d( 1253801723.95465,  -760453007.810989,  -36697431.1565206);

        Vector3d titvel = new Vector3d(8.99593229549645, 11.1085713608453, -2.25130986174761);
        Vector3d titpos = new Vector3d( 1254501624.95946,  -761340299.067828,  -36309613.8378104);

        Vector3d nepvel = new Vector3d(0.447991656952326, 5.44610697514907, -0.122638125365954);
        Vector3d neppos = new Vector3d( 4454487339.09447,  -397895128.763904,  -94464151.3421107);

        Vector3d uravel = new Vector3d(-5.12766216337626, 4.22055347264457, 0.0821190336403063);
        Vector3d urapos = new Vector3d( 1958732435.99338,  2191808553.21893,  -17235283.8321992);

        Vector3d probevel = new Vector3d(0,0,0);
        Vector3d probepos = initialPosProbe;

        firstprobepos = initialPosProbe;
        launchCoords.setText("Launch coords: [x " + probepos.getX() + ", y " + probepos.getY() + ", z " + probepos.getZ() + "]");
        launchVelocity.setText("Launch velocity: [x " + probevel.getX() + ", y " + probevel.getY() + ", z " + probevel.getZ() + "]");

        bodies[0] = new CelestialBody(sunvel, sunpos, 1.991E30);
        bodies[1] = new CelestialBody(mervel, merpos, 3.302E+23);
        bodies[2] = new CelestialBody(venusvel, venuspos, 4.8685E+24);
        bodies[3] = new CelestialBody(earthvel, earthpos, 5.97219E+24);
        bodies[4] = new CelestialBody(moonvel, moonpos, 7.3491E22);
        bodies[5] = new CelestialBody(marsvel, marspos, 6.4171E+23);
        bodies[6] = new CelestialBody(jupivel, jupipos, 1.89819E+27);
        bodies[7] = new CelestialBody(satuvel, satupos, 5.6834E+26);
        bodies[8] = new CelestialBody(titvel, titpos, 1.34553E+23);
        bodies[9] = new CelestialBody(nepvel, neppos, 1.02409E+26);
        bodies[10] = new CelestialBody(uravel, urapos, 86.813E+24);
        bodies[11] = new Rocket(probevel, probepos);
    }

    /**
     * Returns to the start screen when pressed.
     * @throws IOException
     * @see javafx.scene.control.Button
     * @see MusicPlayer
     */
    @FXML
    public void onReturnButton() throws IOException{
        MP.fadeOut();
        App.setRoot("fxml/StartScene");
    }

    /**
     * This method checks which Solver method is chosen. It then calculates the next position using that chosen Solver method.
     * @param celestialBodies
     * @param chosenBody
     * @param chosenStepsize
     * @return the next position.
     */
    public CelestialBody[] solvers(CelestialBody[] celestialBodies, int chosenBody, double chosenStepsize){
        if(chosenSolver == 0){
            return a.adams(celestialBodies, chosenBody, chosenStepsize);
        } else if(chosenSolver == 1){
            return unreal.eulers(celestialBodies, chosenBody, chosenStepsize);
        } else if(chosenSolver == 2){
            return verlet.verlet(celestialBodies, chosenBody, chosenStepsize);
        } else if(chosenSolver == 3){
            return rungeKutta.rungKutta(celestialBodies, chosenBody, chosenStepsize);
        } else if(chosenSolver == 4){
            return aMoulton.adams(celestialBodies, chosenBody, chosenStepsize);
        } else {
            return celestialBodies;
        }
    }  
    /**
     * The class that calculates the movement of the {@code Celestial Bodies} and updates the time. 
     */
    public class Movement implements EventHandler<ActionEvent>{

        /**
         * Calculates the movement of all the celestial bodies and the probe 
         */
        @Override
        public void handle(ActionEvent event) {
            //Run the Euler's method to get the next position and velocity of all celestial bodies + probe
            for (int i = 0; i < eulerLoops; i++) {
                for (int j = 0; j < bodies.length; j++) {
                    bodies = solvers(bodies, j, stepsize);
                    // if(App.goingBack){
                    //     System.out.println(j);
                    // }
                }

                if(!App.goingBack){
                    bodies = rocketTrajectory(bodies, stepsize, 0);
                }

                //Keep track of the time
                App.seconds += stepsize;
                k += stepsize;
                App.totalSeconds = k;
                if(k >= TimeStamp && timestampCheck && TimeStamp != -1){
                    String timeStampString = "Time Stamp: " + k + " seconds (" + getTime(TimeStamp) + ")";
                    String positionString = "Position of the probe: x: " + bodies[11].getPosition().getX() + " y: " + bodies[11].getPosition().getY() + " z: " + bodies[11].getPosition().getZ();
                    String distanceString = "Distance Traveled: x: " + (bodies[11].getPosition().getX() - firstprobepos.getX() + " km y: " + (bodies[11].getPosition().getY()-firstprobepos.getY()) + " km z: " + (bodies[11].getPosition().getZ()-firstprobepos.getZ()) + " km");
                    String totalDistanceString = "Total distance: " + Math.sqrt(Math.pow(bodies[11].getPosition().getX()-firstprobepos.getX(), 2)+Math.pow(bodies[11].getPosition().getY()-firstprobepos.getY(), 2)+Math.pow(bodies[11].getPosition().getZ()-firstprobepos.getZ(), 2))+ " km";
                    timestampLabel.setText(timeStampString);
                    positionLabel.setText(positionString);
                    distanceLabel.setText(distanceString);
                    totalDistanceLabel.setText(totalDistanceString);
                    System.out.println(timeStampString);
                    System.out.println(positionString);
                    System.out.println(distanceString);
                    System.out.println(totalDistanceString);
                    timestampGroup.setVisible(true);
                    timestampCheck = false;
                }
                if(App.seconds >= 60){
                    App.minutes++;
                    App.seconds = 0;
                    if(App.minutes % 60 == 0){
                        //==================== Rotation of planets ====================
                        earthSphere.rotateProperty().set(earthSphere.getRotate() + 15);
                        mercurySphere.rotateProperty().set(mercurySphere.getRotate() + 0.256);
                        venusSphere.rotateProperty().set(venusSphere.getRotate() + 0.062);
                        marsSphere.rotateProperty().set(marsSphere.getRotate() + 14.4);
                        jupiterSphere.rotateProperty().set(jupiterSphere.getRotate() + 36);
                        saturnSphere.rotateProperty().set(saturnSphere.getRotate() + 32.727);
                        sunSphere.rotateProperty().set(sunSphere.getRotate() + 0.556);
                        //==================== Rotation of planets ====================
                        App.hours++;
                        App.minutes = 0;
                        if(App.hours % 24 == 0){
                            App.days++;
                            App.hours = 0;
                            if(App.days % 30 == 0){
                                App.months++;
                                App.days = 0;
                                if(App.months/12 == 1){
                                    App.months = 0;
                                    App.years++;
                                }
                            }
                        }
                    }
                }
            }

            TimeElapsed.setText("Time Elapsed: " + App.years + " years, " + App.months + " months, " + App.days + " days and " + App.hours + "hours");
            if(!selectedPlanet.equals(bodies[0])){
                focusCamera(selectedPlanet);
            } else if(resetCheck){
                pGroup.setTranslateX(sunPos[0]);
                pGroup.setTranslateY(sunPos[1]); 
                pGroup.setTranslateZ(sunPos[2]);
                resetCheck = false;
            } 

            //Get and set the GUI coords and paths of the celestial bodies
            earPos[0] = bodies[3].getPosition().getX()/divider;
            earPos[1] = bodies[3].getPosition().getY()/divider;
            earPos[2] = bodies[3].getPosition().getZ()/divider;
            setGUIcoords(earth, earPos[0], earPos[1], earPos[2]);

            mooPos[0] = bodies[4].getPosition().getX()/divider;
            mooPos[1] = bodies[4].getPosition().getY()/divider;
            mooPos[2] = bodies[4].getPosition().getZ()/divider;
            setGUIcoords(moon, mooPos[0], mooPos[1], mooPos[2]);

            merPos[0] = bodies[1].getPosition().getX()/divider;
            merPos[1] = bodies[1].getPosition().getY()/divider;
            merPos[2] = bodies[1].getPosition().getZ()/divider;
            setGUIcoords(mercury, merPos[0], merPos[1], merPos[2]);

            venPos[0] = bodies[2].getPosition().getX()/divider;
            venPos[1] = bodies[2].getPosition().getY()/divider;
            venPos[2] = bodies[2].getPosition().getZ()/divider;
            setGUIcoords(venus, venPos[0], venPos[1], venPos[2]);

            marPos[0] = bodies[5].getPosition().getX()/divider;
            marPos[1] = bodies[5].getPosition().getY()/divider;
            marPos[2] = bodies[5].getPosition().getZ()/divider;
            setGUIcoords(mars, marPos[0], marPos[1], marPos[2]);

            jupPos[0] = bodies[6].getPosition().getX()/divider;
            jupPos[1] = bodies[6].getPosition().getY()/divider;
            jupPos[2] = bodies[6].getPosition().getZ()/divider;
            setGUIcoords(jupiter, jupPos[0], jupPos[1], jupPos[2]);

            satPos[0] = bodies[7].getPosition().getX()/divider;
            satPos[1] = bodies[7].getPosition().getY()/divider;
            satPos[2] = bodies[7].getPosition().getZ()/divider;
            setGUIcoords(saturn, satPos[0], satPos[1], satPos[2]);

            titPos[0] = bodies[8].getPosition().getX()/divider;
            titPos[1] = bodies[8].getPosition().getY()/divider;
            titPos[2] = bodies[8].getPosition().getZ()/divider;
            setGUIcoords(titan, titPos[0], titPos[1], titPos[2]);

            //Get and set the probes GUI coords and adapt the texts based on it and its distance to Titan
            Vector3d spaps = bodies[11].getPosition();
            double spax = spaps.getX();
            double spay = spaps.getY();
            double spaz = spaps.getZ();
            probeCoords.setText("Currect rocket coords: [x " + spax + ",y " + spay + ",z " + spaz + "]");
            launchVelocity.setText("Currect rocket velocity: [x " + bodies[11].getVelocity().getX() + ", y " + bodies[11].getVelocity().getY() + ", z " + bodies[11].getVelocity().getZ() + "]");
            
            double titandis = spaps.dist(bodies[8].getPosition());
            distanceTitan.setText("Distance to Titan: " + titandis + " km");
            if (closestTitan > titandis) {
                closestTitan = titandis;
                closestdistanceTitan.setText("Closest distance to Titan: " + closestTitan + " km");
                titanMoment.setText("Moment closest distance to Titan: " + years + " years, " + months + " months, " + days + " days and " + hours + "hours");
            }
            spax /= divider;
            spay /= divider;
            spaz /= divider;
            setGUIcoords(probe, spax, spay, spaz);
            if(App.goingBack){
                double probeAngleX = Math.atan2(earth.getTranslateY() - probe.getTranslateY(), earth.getTranslateX() - probe.getTranslateX());
                probe.setRotate(probeAngleX*90);
            } else {
                double probeAngleX = Math.atan2(titan.getTranslateY() - probe.getTranslateY(), titan.getTranslateX() - probe.getTranslateX());
                probe.setRotate(90 + probeAngleX*90);
            }
            
            if(lastSpacePos[0] == 5E40){

            } else {
                Point3D p1 = new Point3D(probe.getTranslateX(), probe.getTranslateY(), probe.getTranslateZ());
                Point3D p2 = new Point3D(lastSpacePos[0], lastSpacePos[1], lastSpacePos[2]);

                path.getChildren().add(createConnection(p1, p2));
            }
        
            lastSpacePos[0] = probe.getTranslateX();
            lastSpacePos[1] = probe.getTranslateY();
            lastSpacePos[2] = probe.getTranslateZ();
        }

        /**
         * Get the last and current position of the rocket. Then make a line ({@code Cylinder}) between those points.
         * @param origin as a {@code Point3D}
         * @param target as a {@code Point3D}
         * @return a {@code Cylinder}
         * @see Cylinder
         * @see Point3D
         */
        public Cylinder createConnection(Point3D origin, Point3D target) {
            Point3D yAxis = new Point3D(0, 1, 0);
            Point3D diff = target.subtract(origin);
            double height = diff.magnitude();
        
            Point3D mid = target.midpoint(origin);
            Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());
        
            Point3D axisOfRotation = diff.crossProduct(yAxis);
            double angle = Math.acos(diff.normalize().dotProduct(yAxis));
            Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);
        
            Cylinder line = new Cylinder(1, height);
            line.setMaterial(new PhongMaterial(Color.GREEN));
        
            line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);
        
            return line;
        }

        /**
         * Get the x and y GUI coords of a celestial body
         * @param g the image of the celestial body
         * @param x the GUI x coord
         * @param y the GUI y coord
         * @param z the GUI z coord
         */
        private void setGUIcoords(Group g, double x, double y, double z){
            g.setTranslateX(x);
            g.setTranslateY(y*0.2);
            g.setTranslateZ(z);
        }

        /**
         * Get the x and y GUI coords of a celestial body
         * @param a the AnchorPane of the celestial body
         * @param x the GUI x coord
         * @param y the GUI y coord
         * @param z the GUI z coord
         */
        private void setGUIcoords(AnchorPane a, double x, double y, double z){
            a.setTranslateX(x);
            a.setTranslateY(y*0.2);
            a.setTranslateZ(z);
        }
    }

    /**
     * Transfers the time from seconds to the following format:<p>
     * years, months, days, hours, minutes, seconds
     * @param secs
     * @return a string in the above mentioned format
     */
    private String getTime(int secs){
        String string = "";
        
        int yea = 0;
        int mont = 0;
        int das = 0;
        int hour = 0;
        int minute = 0;
        int sec = 0;
        int temporary;
        yea = secs / 31104000;
        if(yea != 0){
            string += yea + "years, ";
        }
        temporary = (secs - (yea * 31104000));
        mont = temporary / 2592000;
        if(mont != 0){
            string += mont + "months, ";
        }
        temporary -= mont * 2592000;
        das = temporary  / 86400;
        if(das != 0){
            string += das + "days, ";
        }
        temporary -= das * 86400;
        hour = temporary / 3600;
        if(hour != 0){
            string += hour + "hours, ";
        }
        temporary -= hour * 3600;
        minute = temporary / 60;
        if(minute != 0){
            string += minute + "minutes, ";
        }
        temporary -= minute * 60;
        sec = temporary;
        if(sec != 0){
            string += sec + "seconds";
        }
        return string;
        }

    /**
     * Focusses on the sun in the GUI.
     * @see MenuItem
     */
    @FXML
    public void sunFocus(){
        selectedPlanet = bodies[0];
        resetCheck = true;
    }
    /**
     * Focusses on mercury in the GUI.
     * @see MenuItem
     */
    @FXML
    public void mercuryFocus(){
        selectedPlanet = bodies[1];
    }
    /**
     * Focusses on venus in the GUI.
     * @see MenuItem
     */
    @FXML
    public void venusFocus(){
        selectedPlanet = bodies[2];
    }
    /**
     * Focusses on earth in the GUI.
     * @see MenuItem
     */
    @FXML
    public void earthFocus(){
        selectedPlanet = bodies[3];
    }
    /**
     * Focusses on mars in the GUI.
     * @see MenuItem
     */
    @FXML
    public void marsFocus(){
        selectedPlanet = bodies[5];
    }
    /**
     * Focusses on jupiter in the GUI.
     * @see MenuItem
     */
    @FXML
    public void jupiterFocus(){
        selectedPlanet = bodies[6];
    }
    /**
     * Focusses on saturn in the GUI.
     * @see MenuItem
     */
    @FXML
    public void saturnFocus(){
        selectedPlanet = bodies[7];
    }
    /**
     * Focusses on the probe in the GUI.
     * @see MenuItem
     */
    @FXML
    public void probeFocus(){
        selectedPlanet = bodies[11];
    }

    /**
     * Exits the program.
     * @see MenuItem
     */
    @FXML
    public void onExit(){System.exit(0);}

    /**
     * Sets the used Solver method to Adams Bashfort.
     * @see MenuItem
     * @see twentyone.Classes.AdamsBashforth
     */
    @FXML
    public void onAdamsButton(){
        chosenSolver = 0;
    }

    /**
     * Sets the used Solver method to the Euler method.
     * @see MenuItem
     * @see twentyone.Classes.Euler
     */
    @FXML
    public void onEulerButton(){
        chosenSolver = 1;
    }

    /**
     * Sets the used Solver method to Verlet.
     * @see MenuItem
     * @see twentyone.Classes.Verlet
     */
    @FXML
    public void onVerletButton(){
        chosenSolver = 2;
    }

    /**
     * Sets the used Solver method to Runge Kutta.
     * @see MenuItem
     * @see twentyone.Classes.RungeKutta
     */
    @FXML
    public void onRungeButton(){
        chosenSolver = 3;
    }

    /**
     * Sets the used Solver method to Adams-Moulton.
     * @see MenuItem
     * @see twentyone.Classes.AdamsMoulton
     */
    @FXML
    public void onMoultonButton(){
        chosenSolver = 4;
    }

    /**
     * Sets the stepsize to 1.
     * @see MenuItem
     * @see twentyone.Controllers.SolarScene3DController#stepsize
     */
    @FXML
    public void onStepsize1(){
        stepsize = 1;
    }

    /**
     * Sets the stepsize to 10.
     * @see MenuItem
     */
    @FXML
    public void onStepsize10(){
        stepsize = 10;
    }

    /**
     * Sets the stepsize to 100.
     * @see MenuItem
     */
    @FXML
    public void onStepsize100(){
        stepsize = 100;
    }

    /**
     * Sets the stepsize to the initially chosen stepsize.
     * @see MenuItem
     */
    @FXML
    public void onStepsizeChosen(){
        stepsize = App.chosenStepsize;
    }

    private CelestialBody[] rocketTrajectory(CelestialBody[] bodies, double stepSize, double startTime) {
        double s = 0;
        double e = stepSize;

        Vector3d dis = null;
        Vector3d force = null;

        if (!titanOrbit) {
            dis = bodies[11].getPosition().sub(bodies[8].getPosition());
            force = dis.mul(-1.5/dis.norm());

            if (dis.norm() < 2.5e8 && dis.norm() > 1.4e7) {
                force = bodies[11].getVelocity();
                force = force.mul(-10/force.norm());
            } 
            else if (dis.norm() < 1.3e7) {
                enterTitanOrbit();
            }
        }

        if (titanOrbit) {
            dis = bodies[11].getPosition().sub(bodies[3].getPosition());
            force = dis.mul(-1.5/dis.norm());

            if (dis.norm() < 2.5e8 && dis.norm() > 1.4e7) {
                force = bodies[11].getVelocity();
                force = force.mul(-10/force.norm());
            } 
        }
        
        ((Rocket) bodies[11]).boostedVelo(force, s, e);
        
        return bodies;
    }
}
