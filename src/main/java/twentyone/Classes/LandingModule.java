package twentyone.Classes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LandingModule {

    //Main thruster class
    // x is the horizontal position 
    // y is the vertical position
    // θ is the angle of rotation
    // u is the acceleration provided by the main thruster
    // v is the total torque provided by the side thrusters
    // g ≈ 1.352e-3 km s^(-2), is the acceleration due to gravity on titan.
    // uMax = 10g, is the maximum acceleration provided by the main thruster.
    // vMax = 1 rad s^2, is the maximum torque.

    // The differential equations describing the motion are:
    // x" (acceleration x) = u sin(θ)
    // y" (acceleration y) = u cos(θ) − g
    // θ" = v

    // Tolerance values:
    // |x| ≤ δx
    // |θ mod 2π| ≤ δθ
    // |x'| ≤ εx
    // |y'| ≤ εy
    // |θ'| = εθ

    // Where:
    // δx = 10^(−4) km 
    // δθ = 0.02 (stops here?)
    
    // εx = 10^(−4) km s^(-1)
    // εy = 10^(−4) km s^(-1)
    // εθ = 0.01 rad s^(-1)

    //Rocket length: 30m

    // Vector3d position = new Vector3d(200, 200, theta);
    // Vector3d velocity = new Vector3d(starting?velocityX, starting?velocityY, thetaDerivative);
   
    // or silmply

    // Vector3d position = new Vector3d(200, 200, rotation);
    // Vector3d velocity = new Vector3d(starting?velocityX, starting?velocityY, rotationVelocity);

    //Starting from the orbit, we need x, y position and velocity
    Vector3d position;
    Vector3d velocity;
    double fuel = 0;
    public boolean hasLanded = false;

    // OUR CONSTANTS
    static final double g = 1.352e-3;
    // Maximum acceleration provided by the main thruster.
    static final double uMax = 10*g; // km s^(-2)
    // Maximum torque.
    static final double vMax = 1; // rad s^2
    // Tolerance values (upper bound)
    private static final double deltaX = 1e-4; // km ≥ |x|
    private static final double deltaTheta = 0.02; // ≥ |θ mod 2π|
    private static final double epsilonX = 1e-4; // km s^(-1) ≥ |x'|
    private static final double epsilonY = 1e-4; // km s^(-1) ≥ |y'|
    private static final double epsilonTheta = 0.01; // rad s^(-1) = |θ'|

    List<Double> mainThrusts = new ArrayList<>();
    List<Double> miniThrusts = new ArrayList<>();


    public LandingModule(Vector3d position, Vector3d velocity){
        this.position = position;
        this.velocity = velocity;
    }

    double stepSize = 0.00001;
    // in km / s
    public static void main(String[] args) {
        LandingModule ufo = new LandingModule(new Vector3d(50, 200, -Math.PI/2),new Vector3d(-0.5, 0, 0));
        Euler e = new Euler();

        while (ufo.getPosition().getY() != 0.0) {
            ufo.openLoopController(e, new Vector3d(50, 200, -Math.PI/2),new Vector3d(-0.5, 0, 0));
            System.out.println(ufo.getPosition().toString());
            System.out.println(ufo.getVelocity().toString());
            System.out.println();
        }
    }

    public Vector3d getPosition() {
        return position;
    }

    public void setPosition(Vector3d position) {
        this.position = position;
    }

    public double getRotation() {
        return position.getZ();
    }

    public void setRotation(double rotation) {
        position.setZ(rotation);
    }

    public Vector3d getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3d velocity) {
        this.velocity = velocity;
    }

    public double getRotationVelocity() {
        return velocity.getZ();
    }

    public void setRotationVelocity(double rotationVelocity) {
        velocity.setZ(rotationVelocity);
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    public void feedbackController(Euler e) {
        if (!hasLanded) {
            //double wind = WindModel.generateWind(0, 0.2);
            double mainThrust = 0;
            double miniThrust = 0;

            for (int i = 0; i < 1/stepSize; i++) {
                if (position.getX() > 50 && velocity.getX() >= 0) {
                    mainThrust = 0.01352;
                }
                else if (Math.abs(position.getX()) < deltaX*0.9){
                    mainThrust = -Math.abs(velocity.getX())/stepSize;
                }

                if (position.getX() < 10e-5 && position.getZ() == -90*Math.PI/180) {
                    miniThrust = 1;
                }
                if (Math.abs(position.getZ()) < deltaTheta*0.0001) {
                    miniThrust = -(Math.abs(velocity.getZ())/0.03/stepSize);
                }

                if (Math.abs(position.getZ()) < deltaTheta*0.0001 && velocity.getY() < -0.5) {
                    mainThrust = g*3.5;
                }
                if (position.getY() < 1e-4) {
                    mainThrust = Math.abs(velocity.getY())/stepSize - g;
                }
                
                e.landingEuler(this, stepSize, mainThrust, miniThrust);

                if (position.getY() < 1e-4) {
                    boundChecks();
                    System.out.println("Total used fuel: " + fuel);
                    hasLanded = true;
                    velocity.setZ(0);
                    velocity.setX(0);
                    velocity.setY(0);
                    position.setY(0);
                    break;
                }
            }
            writeToFile(mainThrust, miniThrust, "src\\main\\resources\\twentyone\\thrusts.txt");
        }
    }

    private void boundChecks() {
        if (Math.abs(position.getX()) < deltaX) {
            System.out.println("X Coordinate is within bounds");
        }
        else {
            System.out.println("X Coordinate is NOT within bounds");
        }
        if (Math.abs(position.getZ()) < deltaTheta) {
            System.out.println("Rotation is within bounds");
        }
        else {
            System.out.println("Rotation is NOT within bounds");
        }
        if (Math.abs(velocity.getX()) < epsilonX) {
            System.out.println("X Velocity is within bounds");
        }
        else {
            System.out.println("X Velocity is NOT within bounds");
        }
        if (Math.abs(velocity.getY()) < epsilonY) {
            System.out.println("Y Velocity is within bounds");
        }
        else {
            System.out.println("Y Velocity is NOT within bounds");
        }
        if (Math.abs(velocity.getZ()) < epsilonTheta) {
            System.out.println("Rotation Velocity is within bounds");
        }
        else {
            System.out.println("Rotation Velocity is NOT within bounds");
        }
    }

    public void openLoopController(Euler e, Vector3d initialPosition, Vector3d initialVelocity) {
        if (mainThrusts.size() == 0) {
            try {
                File file = new File("src\\main\\resources\\twentyone\\thrusts.txt");
                Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String thrusts = sc.nextLine();
                String[] subthrusts = thrusts.split(" ");
                double mainThrust = Double.parseDouble(subthrusts[0]);
                double miniThrust = Double.parseDouble(subthrusts[1]);

                mainThrusts.add(mainThrust);
                miniThrusts.add(miniThrust);
            }
            sc.close();
            } catch (FileNotFoundException ex) {
                System.out.println("An error occurred.");
                ex.printStackTrace();
            }
        }
        double wind = WindModel.generateWind(0, 0.2);
        double main = mainThrusts.get(0);
        double mini = miniThrusts.get(0);
        e.landingEuler(this, stepSize, main-wind, mini);
        mainThrusts.remove(0);
        miniThrusts.remove(0);
    }

    public static void writeToFile(double main, double mini, String filename) {
        try(FileWriter fw = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(main + " " + mini);

        } catch (Exception e) {
            System.out.println("file not found");
        }
    }}
