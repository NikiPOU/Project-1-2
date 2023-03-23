package twentyone;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.awt.*;

//1 unit is 15474 km

public class SolarSceneController implements Initializable {

    int probeCoords2;
    int distanceTitan2;
    double[] launchCoords2;
    double[] launchVelocity2;  
    double closestTitan = 10E40;
    String momentTitan;

    int sunx = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 + 45;
    int suny = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 + 45;

    int r;
    int g;
    int b;
    CelestialBody3 sunCB;
    CelestialBody3 mercuryCB;
    CelestialBody3 venusCB;
    CelestialBody3 earthCB;
    CelestialBody3 moonCB;
    CelestialBody3 marsCB;
    CelestialBody3 jupiterCB;
    CelestialBody3 saturnCB;
    CelestialBody3 titanCB;
    CelestialBody3 neptuneCB;
    CelestialBody3 uranusCB;
    CelestialBody3 probe;
    CelestialBody3[] bodies = new CelestialBody3[12];

    @FXML
    Label probeCoords;
    @FXML
    Label distanceTitan;
    @FXML
    Label launchCoords;
    @FXML
    Label launchVelocity;
    @FXML
    Label titanMoment;
    @FXML
    ImageView sun;
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
    @FXML
    Group path;
    @FXML
    VBox data;
    @FXML
    Label closestdistanceTitan;

    int seconds = -1;
    int minutes = -1;
    int hours = -1;
    int days = -1;
    int months = -1;
    int years = 0;
    int divider = 2100000;
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

    Random rand = new Random();

    private Timeline timeline;

    int k=0;

    //1 = 2000000



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sun.setLayoutX(sunx - 45);
        sun.setLayoutY(suny - 45);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        data.setLayoutX(25);
        data.setLayoutY(screenSize.getHeight() - 200);
        dots = new Group();
        path = new Group();
        root.getChildren().add(dots);
        root.getChildren().add(path);
        Unreal_Engine unreal = new Unreal_Engine();
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
                for (int i = 0; i < 25000; i++) {
                    for (int j = 0; j < bodies.length; j++) {
                        bodies = unreal.Eulers(bodies, j, 1);
                    }
                    seconds++;
                    if(seconds % 60 == 0){
                        minutes++;
                        seconds = 0;
                        if(minutes % 60 == 0){
                            hours++;
                            k++;
                            minutes = 0;
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
                
                // physics.sumUpdate(bodies, 3);
                // physics.euler_solver(bodies, 3, 1);
                double[] meps = bodies[1].getPosition();
                double[] veps = bodies[2].getPosition();
                double[] eaps = bodies[3].getPosition();
                double[] mops = bodies[4].getPosition();
                double[] maps = bodies[5].getPosition();
                double[] jups = bodies[6].getPosition();
                double[] saps = bodies[7].getPosition();
                double[] tips = bodies[8].getPosition();
                ex = sunx + eaps[0]/divider;
                ey = suny + eaps[1]/divider;
                circlemaker(3, ex, ey);
                double mx = sunx + 8 + 15*Math.cos(k*0.009) + mops[0]/divider; //8+ 15*Math.cos(i) + 
                double my = suny + 8 + 15*Math.sin(k*0.009) + mops[1]/divider; //8+ 15*Math.sin(i) +
                circlemaker(4, mx, my);
                double mex = sunx + meps[0]/divider;
                double mey = suny + meps[1]/divider;
                circlemaker(1, mex, mey);
                double vx = sunx + veps[0]/divider;
                double vy = suny + veps[1]/divider;
                circlemaker(2, vx, vy);
                double max = sunx + maps[0]/divider;
                double may = suny + maps[1]/divider;
                circlemaker(5, max, may);
                double jx = sunx + jups[0]/divider;
                double jy = suny + jups[1]/divider;
                circlemaker(6, jx, jy);
                sx = sunx + saps[0]/divider;
                sy = suny + saps[1]/divider;
                circlemaker(7, sx, sy);
                tx = sunx + 40 + 23*Math.cos(k*0.0168) + tips[0]/divider;
                ty = suny + 15 + 23*Math.sin(k*0.0168) + tips[1]/divider;
                circlemaker(8, tx, ty);
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

                double[] spaps = bodies[11].getPosition();
                probeCoords.setText("Currect probe coords: [x " + spaps[0] + ",y " + spaps[1] + ",z " + spaps[2] + "]");

                double spax = sunx + spaps[0]/divider;
                double spay = suny + spaps[1]/divider;
                double titandis = Math.sqrt(((tx-spax)*(tx-spax))+((ty-spay)*(ty-spay)));
                distanceTitan.setText("Distance to Titan: " + titandis*divider + " km");
                spaceprobe.setLayoutX(spax);
                spaceprobe.setLayoutY(spay);
                if (closestTitan > titandis) {
                    closestdistanceTitan.setText("Closest distance to Titan: " + titandis*divider + " km");
                    titanMoment.setText("Moment closest distance to Titan: " + years + " years, " + months + " months, " + days + " days and " + hours + "hours");
                    closestTitan = titandis;
                } else if(titandis < 10){
                    titanMoment.setText("Moment of impact: " + years + " years, " + months + " months, " + days + " days and " + hours + " hours");
                }

                Circle circle = new Circle();
                circle.setCenterX(spax);
                circle.setCenterY(spay);
                circle.setRadius(2);
                circle.setFill(Color.rgb(r, g, b));
                circle.setOpacity(0.4);
                dotList.add(circle);
                dots.getChildren().add(circle);             
            }
        };

        initiateCB();
        launchCoords.setText("Launch coords: [x " + launchCoords2[0] + ", y " + launchCoords2[1] + ", z " + launchCoords2[2] + "]");
        launchVelocity.setText("Launch velocity: [x " + launchVelocity2[0] + ", y " + launchVelocity2[1] + ", z " + launchVelocity2[2] + "]");

        KeyFrame keyFrame = new KeyFrame(Duration.millis(100), movement);
        r = rand.nextInt(255);
        g = rand.nextInt(255);
        b = rand.nextInt(255);
        //add the keyframe to the timeline
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    /**
     * Checks if there is a used key is pressed and acts accordingly.
     * @param ke
     */
    @FXML
    public void spaceHandler(KeyEvent ke) {
        if(ke.getCode().equals(KeyCode.BACK_SPACE)){
            isPressed = true;
        } else if(ke.getCode().equals(KeyCode.R)){
            spax = ex + 2;
            spay = ey + 2;
            isPressed = false;
            r = rand.nextInt(255);
            g = rand.nextInt(255);
            b = rand.nextInt(255);
        } else if(ke.getCode().equals(KeyCode.V)){
            if(dots.isVisible()){
                dots.setVisible(false);
            } else {
                dots.setVisible(true);
            }
        } else if(ke.getCode().equals(KeyCode.P)){
            if(path.isVisible()){
                path.setVisible(false);
            } else {
                path.setVisible(true);
            }
        }
    };

    /**
     * Makes the trails of the planets.
     * @param index
     * @param x
     * @param y
     */
    public void circlemaker(int index, double x, double y){
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
        path.getChildren().add(circle);

    }

    /**
     * Switches to the home screen.
     * @throws IOException
     */
    @FXML
    private void switchToStart() throws IOException {
        App.setRoot("StartScene");
    }

    /**
     * Initiates all the planets and the probe. It gives the correct positions, velocities and masses.
     */
    private void initiateCB(){
        double[] merpos = {(long) 7833268.43923962, (long) 44885949.3703908, (long) 2867693.20054382};
        double[] mervel = {-57.4967480139828, 11.52095127176, 6.21695374334136};

        double[] venusvel = {-34.0236737066136, -8.96521274688838, 1.84061735279188};
        double[] venuspos = {(long) -28216773.9426889, (long) 103994008.541512, (long) 3012326.64296788};

        double[] sunvel = {0, 0, 0};
        double[] sunpos = {0, 0, 0};
        

        double[] earthvel = {5.05251577575409, -29.3926687625899,  0.00170974277401292};
        double[] earthpos = {(long) -148186906.893642, (long) -27823158.5715694, (long) 33746.8987977113};


        double[] moonvel = {4.34032634654904, -30.0480834180741, -0.0116103535014229};
        double[] moonpos = {(long) -148458048.395164, (long) -27524868.1841142, (long) 70233.6499287411};

        double[] marsvel = {-17.6954469224752, -13.4635253412947, 0.152331928200531};
        double[] marspos = {(long) -159116303.422552, (long) 189235671.561057, (long) 7870476.08522969};


        double[] jupivel = {-4.71443059866156, 12.8555096964427, 0.0522118126939208};
        double[] jupipos = {(long) 692722875.928222, (long) 258560760.813524, (long) -16570817.7105996};

        double[] satuvel = {4.46781341335014, 8.23989540475628,  -0.320745376969732};
        double[] satupos = {(long) 1253801723.95465, (long) -760453007.810989, (long) -36697431.1565206};

        double[] titvel = {8.99593229549645, 11.1085713608453, -2.25130986174761};
        double[] titpos = {(long) 1254501624.95946, (long) -761340299.067828, (long) -36309613.8378104};

        double[] nepvel = {0.447991656952326, 5.44610697514907, -0.122638125365954};
        double[] neppos = {(long) 4454487339.09447, (long) -397895128.763904, (long) -94464151.3421107};

        double[] uravel = {-5.12766216337626, 4.22055347264457, 0.0821190336403063};
        double[] urapos = {(long) 1958732435.99338, (long) 2191808553.21893, (long) -17235283.8321992};

        double[] probevel = {48, -45, 0};
        double[] probepos = {-148186906.893642 + 6370, -27823158.5715694, 33746.8987977113}; 

        launchCoords2 = probepos;
        launchVelocity2 = probevel;

        sunCB = new CelestialBody3(sunvel, sunpos, 1.991E30);
        earthCB = new CelestialBody3(earthvel, earthpos, 5.97219E+24);
        venusCB = new CelestialBody3(venusvel, venuspos, 4.8685E+24);
        mercuryCB = new CelestialBody3(mervel, merpos, 3.302E+23);
        moonCB = new CelestialBody3(moonvel, moonpos, 7.3491E22);
        marsCB = new CelestialBody3(marsvel, marspos, 6.4171E+23);
        jupiterCB = new CelestialBody3(jupivel, jupipos, 1.89819E+27);
        saturnCB = new CelestialBody3(satuvel, satupos, 5.6834E+26);
        titanCB = new CelestialBody3(titvel, titpos, 1.34553E+23);
        neptuneCB = new CelestialBody3(nepvel, neppos, 1.02409E+26);
        uranusCB = new CelestialBody3(uravel, urapos, 86.813E+24);
        probe = new Probe2(probevel, probepos, 50000);


        bodies[0] = sunCB;
        bodies[3] = earthCB;
        bodies[2] = venusCB;
        bodies[5] = marsCB;
        bodies[9] = uranusCB;
        bodies[10] = neptuneCB;
        bodies[1] = mercuryCB;
        bodies[4] = moonCB;
        bodies[8] = titanCB;
        bodies[7] = saturnCB;
        bodies[6] = jupiterCB;
        bodies[11] = probe;
    }
}