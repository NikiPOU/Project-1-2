package twentyone.Controllers;

import java.awt.Toolkit;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import twentyone.App;
import twentyone.Classes.AdamsBashforth;
import twentyone.Classes.CelestialBody;
import twentyone.Classes.Probe;
import twentyone.Classes.Unreal_Engine;

public class SolarSceneController implements Initializable {
    final double stepsize = 10;
    final int eulerLoops = 7500;
    final double[] initialPosProbe = {-148186906.893642 + 6370, -27823158.5715694, 33746.8987977113};
    final double[] initialVelProbe = {48, -45, 0};
    //in hours  
    int TimeStamp = 27;

    int probeCoords2;
    int distanceTitan2;
    double[] launchCoords2;
    double[] launchVelocity2;  
    double closestTitan = 10E40;
    String momentTitan;
    double[] firstprobepos;
    int sunx = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 + 45;
    int suny = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 + 45;

    CelestialBody[] bodies = new CelestialBody[12];

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
    double spax;
    double spay;
    double tx;
    double ty;
    double sx;
    double sy;
    ArrayList<Circle> dotList = new ArrayList<>();
    Random rand = new Random();
    private Timeline timeline;
    int k=0;

    @FXML
    AnchorPane root;
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
    ImageView spaceprobe;
    @FXML
    VBox data;
    @FXML
    Label TimeElapsed;
    @FXML
    Label closestdistanceTitan;
    @FXML
    Label probeCoords;
    @FXML
    Label distanceTitan;
    @FXML
    Label titanMoment;
    @FXML
    Label launchCoords;
    @FXML
    Label launchVelocity;
    @FXML
    Group dots;
    @FXML
    Group path;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Setup basic attributes
        sun.setLayoutX(sunx - 45);
        sun.setLayoutY(suny - 45);
        data.setLayoutX(25);
        data.setLayoutY(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 200);
        dots = new Group();
        path = new Group();
        root.getChildren().add(dots);
        root.getChildren().add(path);
        r = rand.nextInt(255);
        g = rand.nextInt(255);
        b = rand.nextInt(255);
      
        //create a timeline for moving the circle
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);

        //one can add a specific action when the keyframe is reached (you can find class movement below)
        EventHandler<ActionEvent> movement = new Movement();

        //Create all celestial bodies and the probe
        initiateCB();

        //add the keyframe to the timeline
        KeyFrame keyFrame = new KeyFrame(Duration.millis(300), movement);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        addMusic();
    }

    /**
     * Switches to the home screen.
     * @throws IOException
     */
    @FXML
    private void switchToStart() throws IOException {
        App.setRoot("fxml/StartScene");
    }

    /**
     * Checks if there is a used key is pressed and acts accordingly.
     * @param ke
     */
    @FXML
    public void spaceHandler(KeyEvent ke) {
        if(ke.getCode().equals(KeyCode.R)){
            spax = ex + 2;
            spay = ey + 2;
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
     * Initiates all the planets and the probe. It gives the correct positions, velocities and masses.
     */
    private void initiateCB(){
        double[] sunvel = {0, 0, 0};
        double[] sunpos = {0, 0, 0};

        double[] merpos = {(long) 7833268.43923962, (long) 44885949.3703908, (long) 2867693.20054382};
        double[] mervel = {-57.4967480139828, 11.52095127176, 6.21695374334136};

        double[] venusvel = {-34.0236737066136, -8.96521274688838, 1.84061735279188};
        double[] venuspos = {(long) -28216773.9426889, (long) 103994008.541512, (long) 3012326.64296788};
        
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

        double[] probevel = initialVelProbe;
        double[] probepos = initialPosProbe;

        firstprobepos = probepos;
        launchCoords.setText("Launch coords: [x " + probepos[0] + ", y " + probepos[1] + ", z " + probepos[2] + "]");
        launchVelocity.setText("Launch velocity: [x " + probevel[0] + ", y " + probevel[1] + ", z " + probevel[2] + "]");

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
        bodies[11] = new Probe(probevel, probepos);
    }

    public class Movement implements EventHandler<ActionEvent>{

        /**
         * Calculates the movement of all the celestial bodies and the probe 
         */
        @Override
        public void handle(ActionEvent event) {
            //Unreal_Engine unreal = new Unreal_Engine();
            AdamsBashforth a = new AdamsBashforth();

            //Run the Euler's method to get the next position and velocity of all celestial bodies + probe
            for (int i = 0; i < eulerLoops; i++) {
                for (int j = 0; j < bodies.length; j++) {
                    //bodies = unreal.Eulers(bodies, j, stepsize);
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
                            System.out.println("Position of the probe: x: " + bodies[11].getPosition()[0] + " y: " + bodies[11].getPosition()[1] + " z: " + bodies[11].getPosition()[2]);
                            System.out.println("Distance Traveled: x: " + (bodies[11].getPosition()[0]-firstprobepos[0]) + " km y: " + (bodies[11].getPosition()[1]-firstprobepos[1]) + " km z: " + (bodies[11].getPosition()[2]-firstprobepos[2]) + " km");
                            System.out.println("Total distance: " + Math.sqrt(Math.pow(bodies[11].getPosition()[0]-firstprobepos[0], 2)+Math.pow(bodies[11].getPosition()[1]-firstprobepos[1], 2)+Math.pow(bodies[11].getPosition()[2]-firstprobepos[2], 2))+ " km");
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
            ex = sunx + bodies[3].getPosition()[0]/divider;
            ey = suny + bodies[3].getPosition()[1]/divider;
            circlemaker(3, ex, ey);
            setGUIcoords(earth, ex, ey);
            double mx = sunx + 8 + 15*Math.cos(k*0.009) + bodies[4].getPosition()[0]/divider;
            double my = suny + 8 + 15*Math.sin(k*0.009) + bodies[4].getPosition()[1]/divider;
            circlemaker(4, mx, my);
            setGUIcoords(moon, mx, my);
            double mex = sunx + bodies[1].getPosition()[0]/divider;
            double mey = suny + bodies[1].getPosition()[1]/divider;
            circlemaker(1, mex, mey);
            setGUIcoords(mercury, mex, mey);
            double vx = sunx + bodies[2].getPosition()[0]/divider;
            double vy = suny + bodies[2].getPosition()[1]/divider;
            circlemaker(2, vx, vy);
            setGUIcoords(venus, vx, vy);
            double max = sunx + bodies[5].getPosition()[0]/divider;
            double may = suny + bodies[5].getPosition()[1]/divider;
            circlemaker(5, max, may);
            setGUIcoords(mars, max, may);
            double jx = sunx + bodies[6].getPosition()[0]/divider;
            double jy = suny + bodies[6].getPosition()[1]/divider;
            circlemaker(6, jx, jy);
            setGUIcoords(jupiter, jx, jy);
            sx = sunx + bodies[7].getPosition()[0]/divider;
            sy = suny + bodies[7].getPosition()[1]/divider;
            circlemaker(7, sx, sy);
            setGUIcoords(saturn, sx, sy);
            tx = sunx + 40 + 23*Math.cos(k*0.0168) + bodies[8].getPosition()[0]/divider;
            ty = suny + 15 + 23*Math.sin(k*0.0168) + bodies[8].getPosition()[1]/divider;
            circlemaker(8, tx, ty);
            setGUIcoords(titan, tx, ty);

            //Get and set the probes GUI coords and adapt the texts based on it and its distance to Titan
            double[] spaps = bodies[11].getPosition();
            probeCoords.setText("Currect probe coords: [x " + spaps[0] + ",y " + spaps[1] + ",z " + spaps[2] + "]");
            double spax = sunx + spaps[0]/divider;
            double spay = suny + spaps[1]/divider;
            double titandis = Math.sqrt(((tx-spax)*(tx-spax))+((ty-spay)*(ty-spay)));
            distanceTitan.setText("Distance to Titan: " + titandis*divider + " km");
            spaceprobe.setLayoutX(spax);
            spaceprobe.setLayoutY(spay-20);
            if (closestTitan > titandis) {
                closestdistanceTitan.setText("Closest distance to Titan: " + titandis*divider + " km");
                titanMoment.setText("Moment closest distance to Titan: " + years + " years, " + months + " months, " + days + " days and " + hours + "hours");
                closestTitan = titandis;
            }

            //Draw the path of the probe
            Circle circle = new Circle();
            circle.setCenterX(spax);
            circle.setCenterY(spay);
            circle.setRadius(2);
            circle.setFill(Color.rgb(r, g, b));
            circle.setOpacity(0.4);
            dotList.add(circle);
            dots.getChildren().add(circle);
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
            path.getChildren().add(circle);
        }

        /**
         * Get the x and y GUI coords of a celestial body
         * @param i the image of the celestial body
         * @param x the GUI x coord
         * @param y the GUI y coord
         */
        private void setGUIcoords(ImageView i, double x, double y) {
            i.setLayoutX(x);
            i.setLayoutY(y);
        }
    }

    public void addMusic() {
        String path = "src\\main\\resources\\twentyone\\Music\\StarWars.mp3";  
        Media media = new Media(new File(path).toURI().toString());          
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);  
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
