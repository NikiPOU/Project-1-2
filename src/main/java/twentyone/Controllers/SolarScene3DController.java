package twentyone.Controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.util.Duration;
import twentyone.App;
import twentyone.Classes.AdamsBashforth;
import twentyone.Classes.CelestialBody;
import twentyone.Classes.Rocket;
import twentyone.Classes.Vector3d;
import twentyone.Classes.musicPlayer;

public class SolarScene3DController implements Initializable {

    private double sunx;
    private double suny;
    private double sunz;
    private Timeline timeline;
    CelestialBody[] bodies = new CelestialBody[12];
    final double stepsize = 10;
    int eulerLoops = 5000;
    final Vector3d initialPosProbe = new Vector3d(-148186906.893642 + 6370, -27823158.5715694, 33746.8987977113);
    final Vector3d initialVelProbe = new Vector3d(48, -45, 0);
    Vector3d firstprobepos;
    
    double closestTitan = 10E40;

    int TimeStamp = -1;

    int r;
    int g;
    int b;
    int seconds = 0;
    int minutes = 0;
    int hours = 0;
    int days = 0;
    int months = 0;
    int years = 0;
    int divider = 2100000;
    double ex;
    double ey;
    double ez;
    double spax;
    double spay;
    double spaz;
    double tx;
    double ty;
    double tz;
    double sx;
    double sy;
    double sz;
    ArrayList<Circle> dotList = new ArrayList<>();
    Random rand = new Random();
    int k=0;
    private musicPlayer MP;

    @FXML
    private Scene scene;
    @FXML
    private AnchorPane pGroup;
    @FXML
    private PerspectiveCamera pCamera;

    @FXML
    private Group sun;
    @FXML
    private Sphere sunSphere;
    @FXML
    private Group earth;
    @FXML
    private Sphere earthSphere;
    @FXML
    private Group moon;
    @FXML 
    private Sphere moonSphere;
    @FXML
    private Group mercury;
    @FXML
    private Sphere mercurySphere;
    @FXML
    private Group venus;
    @FXML
    private Sphere venusSphere;
    @FXML
    private Group mars;
    @FXML
    private Sphere marsSphere;
    @FXML
    private Group jupiter;
    @FXML
    private Sphere jupiterSphere;
    @FXML
    private Group saturn;
    @FXML
    private Sphere saturnSphere;
    @FXML
    private Group titan;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sunx = (App.width)/2;
        suny = (App.height)/2;
        sunz = 0;
        // sun.setTranslateX(sunx);
        // sun.setTranslateY(suny);
        // earth.setTranslateX(sunx-100);
        // earth.setTranslateY(suny);
        pGroup.setTranslateX(sunx);
        pGroup.setTranslateY(suny); 
        pGroup.setTranslateZ(sunz);
        // pGroup.setRotationAxis(new Point3D(10, 0, 0));
        // pGroup.setRotate(45);
        launchCoords.sceneProperty().addListener((Observable, oldScene, newScene) -> {
            if(newScene != null){
                scene = launchCoords.getScene();
                pCamera = new PerspectiveCamera(true);
                scene.setCamera(pCamera);
                pCamera.setFieldOfView(100);
                pCamera.setFarClip(6000);
                pCamera.setNearClip(0.01);
                pCamera.setTranslateZ(-400);
                pCamera.setTranslateX(sunx);
                pCamera.setTranslateY(suny);
                // pGroup.getChildren().add(pCamera);
                Stage stage = (Stage) launchCoords.getScene().getWindow();
                stage.addEventHandler(ScrollEvent.SCROLL, event -> {
                    double delta = event.getDeltaY();
                    pGroup.setTranslateZ(pGroup.getTranslateZ() + delta);
                });
                sunSphere.setMaterial(new PhongMaterial(Color.ORANGE));
                earthSphere.setMaterial(new PhongMaterial(Color.BLUE));
                moonSphere.setMaterial(new PhongMaterial(Color.GREY));
                mercurySphere.setMaterial(new PhongMaterial(Color.LIGHTBLUE));
                venusSphere.setMaterial(new PhongMaterial(new Color(0.78, 0.62, 0.16, 1)));
                marsSphere.setMaterial(new PhongMaterial(Color.MAROON));
                jupiterSphere.setMaterial(new PhongMaterial(Color.BURLYWOOD));
                saturnSphere.setMaterial(new PhongMaterial(Color.DARKGOLDENROD));
                titanSphere.setMaterial(new PhongMaterial(Color.DARKCYAN));
            }
        });
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
        EventHandler<ActionEvent> movement = new Movement();
        initiateCB();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(300), movement);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        MP = new musicPlayer("Interstellar.mp3");
        MP.run();
    }

    /**
     * When a key gets pressed, only "w", "a", "s", "d", "." and ","
     * @param ke
     */
    @FXML
    public void keyPress(KeyEvent ke) {
        if(ke.getCode().equals(KeyCode.W)){
            pGroup.setTranslateY(pGroup.getTranslateY() - 100);
        } else if(ke.getCode().equals(KeyCode.S)){
            pGroup.setTranslateY(pGroup.getTranslateY() + 100);
        } else if(ke.getCode().equals(KeyCode.A)){
            pGroup.setTranslateX(pGroup.getTranslateX() - 100);
        } else if(ke.getCode().equals(KeyCode.D)){
            pGroup.setTranslateX(pGroup.getTranslateX() + 100);
        } else if(ke.getCode().equals(KeyCode.PERIOD)){
            if(eulerLoops == 5000){
                eulerLoops = 10000;
                MP.speedUp();
            } else if(eulerLoops == 10000){
                eulerLoops = 20000;
                MP.speedUp();
            } else {}
        } else if(ke.getCode().equals(KeyCode.COMMA)){
            if(eulerLoops == 20000){
                eulerLoops = 10000;
                MP.speedDown();
            } else if(eulerLoops == 10000){
                eulerLoops = 5000;
                MP.speedDown();
            } else {}
        }
    }


    /**
     * Initiates all the planets and the probe. It gives the correct positions, velocities and masses.
     */
    private void initiateCB(){
        Vector3d sunvel = new Vector3d(0,0,0);
        Vector3d sunpos = new Vector3d(0,0,0);

        Vector3d merpos = new Vector3d((long) 7833268.43923962, (long) 44885949.3703908, (long) 2867693.20054382);
        Vector3d mervel = new Vector3d(-57.4967480139828, 11.52095127176, 6.21695374334136);

        Vector3d venusvel = new Vector3d(-34.0236737066136, -8.96521274688838, 1.84061735279188);
        Vector3d venuspos = new Vector3d((long) -28216773.9426889, (long) 103994008.541512, (long) 3012326.64296788);
        
        Vector3d earthvel = new Vector3d(5.05251577575409, -29.3926687625899,  0.00170974277401292);
        Vector3d earthpos = new Vector3d((long) -148186906.893642, (long) -27823158.5715694, (long) 33746.8987977113);

        Vector3d moonvel = new Vector3d(4.34032634654904, -30.0480834180741, -0.0116103535014229);
        Vector3d moonpos = new Vector3d((long) -148458048.395164, (long) -27524868.1841142, (long) 70233.6499287411);

        Vector3d marsvel = new Vector3d(-17.6954469224752, -13.4635253412947, 0.152331928200531);
        Vector3d marspos = new Vector3d((long) -159116303.422552, (long) 189235671.561057, (long) 7870476.08522969);

        Vector3d jupivel = new Vector3d(-4.71443059866156, 12.8555096964427, 0.0522118126939208);
        Vector3d jupipos = new Vector3d((long) 692722875.928222, (long) 258560760.813524, (long) -16570817.7105996);

        Vector3d satuvel = new Vector3d(4.46781341335014, 8.23989540475628,  -0.320745376969732);
        Vector3d satupos = new Vector3d((long) 1253801723.95465, (long) -760453007.810989, (long) -36697431.1565206);

        Vector3d titvel = new Vector3d(8.99593229549645, 11.1085713608453, -2.25130986174761);
        Vector3d titpos = new Vector3d((long) 1254501624.95946, (long) -761340299.067828, (long) -36309613.8378104);

        Vector3d nepvel = new Vector3d(0.447991656952326, 5.44610697514907, -0.122638125365954);
        Vector3d neppos = new Vector3d((long) 4454487339.09447, (long) -397895128.763904, (long) -94464151.3421107);

        Vector3d uravel = new Vector3d(-5.12766216337626, 4.22055347264457, 0.0821190336403063);
        Vector3d urapos = new Vector3d((long) 1958732435.99338, (long) 2191808553.21893, (long) -17235283.8321992);

        Vector3d probevel = initialVelProbe;
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
     */
    @FXML
    public void onReturnButton() throws IOException{
        MP.fadeOut();
        App.setRoot("fxml/StartScene");
    }
    
    public class Movement implements EventHandler<ActionEvent>{

        /**
         * Calculates the movement of all the celestial bodies and the probe 
         */
        @Override
        public void handle(ActionEvent event) {
            
            // Unreal_Engine unreal = new Unreal_Engine();
            AdamsBashforth a = new AdamsBashforth();

            //Run the Euler's method to get the next position and velocity of all celestial bodies + probe
            for (int i = 0; i < eulerLoops; i++) {
                for (int j = 0; j < bodies.length; j++) {
                    // bodies = unreal.Eulers(bodies, j, stepsize);
                    bodies = a.adams(bodies, j, stepsize);
                }

                //Keep track of the time
                seconds += stepsize;
                if(seconds >= 60){
                    minutes++;
                    seconds = 0;
                    if(minutes % 60 == 0){
                        hours++;
                        k++;
                        minutes = 0;
                        if(k == TimeStamp){
                            System.out.println("Time Stamp: " + k + " hours");
                            System.out.println("Position of the probe: x: " + bodies[11].getPosition().getX() + " y: " + bodies[11].getPosition().getY() + " z: " + bodies[11].getPosition().getZ());
                            System.out.println("Distance Traveled: x: " + (bodies[11].getPosition().getX() - firstprobepos.getX() + " km y: " + (bodies[11].getPosition().getY()-firstprobepos.getY()) + " km z: " + (bodies[11].getPosition().getZ()-firstprobepos.getZ()) + " km"));
                            System.out.println("Total distance: " + Math.sqrt(Math.pow(bodies[11].getPosition().getX()-firstprobepos.getX(), 2)+Math.pow(bodies[11].getPosition().getY()-firstprobepos.getY(), 2)+Math.pow(bodies[11].getPosition().getZ()-firstprobepos.getZ(), 2))+ " km");
                        }
                        if(hours % 24 == 0){
                            days++;
                            hours = 0;
                            if(days % 30 == 0){
                                months++;
                                days = 0;
                                if(months/12 == 1){
                                    months = 0;
                                    years++;
                                }
                            }
                        }
                    }
                }
            }
            TimeElapsed.setText("Time Elapsed: " + years + " years, " + months + " months, " + days + " days and " + hours + "hours");  

            //Get and set the GUI coords and paths of the celestial bodies
            ex = bodies[3].getPosition().getX()/divider;
            ey = bodies[3].getPosition().getY()/divider;
            ez = bodies[3].getPosition().getZ()/divider;
            // circlemaker(3, ex, ey, ez);
            setGUIcoords(earth, ex, ey, ez);
            double mx = bodies[4].getPosition().getX()/divider;
            double my = bodies[4].getPosition().getY()/divider;
            double mz = bodies[4].getPosition().getZ()/divider;
            // circlemaker(4, mx, my);
            setGUIcoords(moon, mx, my, mz);
            double mex = bodies[1].getPosition().getX()/divider;
            double mey = bodies[1].getPosition().getY()/divider;
            double mez = bodies[1].getPosition().getZ()/divider;
            // circlemaker(1, mex, mey);
            setGUIcoords(mercury, mex, mey, mez);
            double vx = bodies[2].getPosition().getX()/divider;
            double vy = bodies[2].getPosition().getY()/divider;
            double vz = bodies[2].getPosition().getZ()/divider;
            // circlemaker(2, vx, vy);
            setGUIcoords(venus, vx, vy, vz);
            double max = bodies[5].getPosition().getX()/divider;
            double may = bodies[5].getPosition().getY()/divider;
            double maz = bodies[5].getPosition().getZ()/divider;
            // circlemaker(5, max, may);
            setGUIcoords(mars, max, may, maz);
            double jx = bodies[6].getPosition().getX()/divider;
            double jy = bodies[6].getPosition().getY()/divider;
            double jz = bodies[6].getPosition().getZ()/divider;
            // circlemaker(6, jx, jy);
            setGUIcoords(jupiter, jx, jy, jz);
            sx = bodies[7].getPosition().getX()/divider;
            sy = bodies[7].getPosition().getY()/divider;
            sz = bodies[7].getPosition().getZ()/divider;
            // circlemaker(7, sx, sy);
            setGUIcoords(saturn, sx, sy, sz);
            tx = bodies[8].getPosition().getX()/divider;
            ty = bodies[8].getPosition().getY()/divider;
            tz = bodies[8].getPosition().getZ()/divider;
            // circlemaker(8, tx, ty);
            setGUIcoords(titan, tx, ty, tz);

            //Get and set the probes GUI coords and adapt the texts based on it and its distance to Titan
            Vector3d spaps = bodies[11].getPosition();
            probeCoords.setText("Currect probe coords: [x " + spaps.getX() + ",y " + spaps.getY() + ",z " + spaps.getZ() + "]");
            double spax = sunx + spaps.getX()/divider;
            double spay = suny + spaps.getY()/divider;
            double titandis = Math.sqrt(((tx-spax)*(tx-spax))+((ty-spay)*(ty-spay)));
            distanceTitan.setText("Distance to Titan: " + titandis*divider + " km");
            // spaceprobe.setLayoutX(spax);
            // spaceprobe.setLayoutY(spay-20);
            if (closestTitan > titandis) {
                closestdistanceTitan.setText("Closest distance to Titan: " + titandis*divider + " km");
                titanMoment.setText("Moment closest distance to Titan: " + years + " years, " + months + " months, " + days + " days and " + hours + "hours");
                closestTitan = titandis;
            }

            //Draw the path of the probe
            // Circle circle = new Circle();
            // circle.setCenterX(spax);
            // circle.setCenterY(spay);
            // circle.setRadius(2);
            // circle.setFill(Color.rgb(r, g, b));
            // circle.setOpacity(0.4);
            // dotList.add(circle);
            // dots.getChildren().add(circle);
        }

        /**
         * Makes the trails of the planets.
         * @param index
         * @param x
         * @param y
         */
        private void circlemaker(int index, double x, double y){
            Circle circle = new Circle();
            if(index == 1){
                circle.setLayoutX(x+5);
                circle.setLayoutY(y+5);
                circle.setFill(Color.LIGHTGRAY);
            } else if(index == 2){
                circle.setLayoutX(x+10);
                circle.setLayoutY(y+10);
                circle.setFill(Color.DARKORANGE);
            } else if(index == 3){
                circle.setCenterX(x+10);
                circle.setCenterY(y+10);
                circle.setFill(Color.GREEN);
            } else if(index == 4){
                circle.setCenterX(x+2);
                circle.setCenterY(y+2);
                circle.setFill(Color.WHITE);
            } else if(index == 5){
                circle.setLayoutX(x+8);
                circle.setLayoutY(y+8);
                circle.setFill(Color.CRIMSON);
            } else if(index == 6){
                circle.setLayoutX(x+30);
                circle.setLayoutY(y+30);
                circle.setFill(Color.KHAKI);
            } else if(index == 7){
                circle.setLayoutX(x+48);
                circle.setLayoutY(y+24);
                circle.setFill(Color.MAROON);
            } else if(index == 8){
                circle.setLayoutX(x+9);
                circle.setLayoutY(y+9);
                circle.setFill(Color.CYAN);
            }
            circle.setRadius(1);
            circle.setOpacity(0.3);
            // path.getChildren().add(circle);
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
    }

    //Unused method
    public static void writeToFile(String filePath, int x, int y) {
        try(FileWriter fw = new FileWriter("src\\main\\resources\\twentyone\\venusCoords.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(x + " " + y);

        } catch (IOException e) {
            System.out.println("file not found");
        }
    }

    //Unused method
    public static int[][] readFromFile(int index, String s) {
        int[][] coords = new int[index][2];
        try {
            File file = new File("src\\main\\resources\\twentyone\\" + s);
            Scanner sc = new Scanner(file);
            int i = 0;
            while (sc.hasNextLine()) {
              String str = sc.nextLine();
              String[] split = str.split(" ");
              coords[i][0] = Integer.parseInt(split[0]);
              coords[i][1] = Integer.parseInt(split[1]);
              i++;
            }
            sc.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
          return coords;
    }


}
