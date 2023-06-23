package twentyone.Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UnidentifiedFlyingObject {

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
    boolean hasLanded = false;

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


    public UnidentifiedFlyingObject(Vector3d position, Vector3d velocity){
        this.position = position;
        this.velocity = velocity;
    }

    //UnidentifiedFlyingObject ufo;

    double stepSize = 0.00001;
    // in km / s
    public static void main(String[] args) {
        UnidentifiedFlyingObject ufo = new UnidentifiedFlyingObject(new Vector3d(200, 200, -Math.PI/2),new Vector3d(0, 0, 0));
        Euler e = new Euler();

        while (ufo.getPosition().getY() != 0.0) {
            ufo.feedbackController(e);
            //System.out.println(ufo.getPosition().toString());
            //System.out.println(ufo.getVelocity().toString());
            //System.out.println();
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

    /*
    //For now just Euler
    public void solver(double stepSize, double mainThrust, double miniThrust) {
        //update position x and y 
        double newPosX = position.getX() + velocity.getX()*stepSize;
        position.setX(newPosX);
        double newPosY = position.getY() + velocity.getY()*stepSize;
        position.setY(newPosY);
        
        //update velocity with given acceleration formulas
        double newX = velocity.getX() + mainThrust * Math.sin(rotation) * stepSize;
        velocity.setX(newX);
        double newY = velocity.getY() + (mainThrust * Math.cos(rotation) - g) * stepSize;
        //0.5 + (x * 1 - g) * stepSize = 0
        //0.5/stepsize = x-g
        //v/stepsize-g
        velocity.setY(newY);

        //update rotation
        rotation = rotation + rotationVelocity*stepSize; 

        //update rotation velocity with torqe formula = length rocket * force (either + or -) * sin(rotation)
        rotationVelocity = rotationVelocity + 0.03 * miniThrust * Math.sin(Math.PI/2) * stepSize;
    } */

    public void feedbackController(Euler e) {
        if (!hasLanded) {
            double wind = Lagrange.lagrangeRandomGusted(0);
            double mainThrust = 0;
            double miniThrust = 0;

            for (int i = 0; i < 1/stepSize; i++) {
                if (position.getX() > 50 && velocity.getX() >= 0) {
                    mainThrust = 1.5;
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
                
                e.landing(this, stepSize, mainThrust-wind, miniThrust);

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
                File file = new File("src\\main\\resources\\twentyone\\testThrusts.txt");
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
        double wind = Lagrange.lagrangeRandomGusted(0);
        double main = mainThrusts.get(0);
        double mini = miniThrusts.get(0);
        e.landing(this, stepSize, main-wind, mini);
        mainThrusts.remove(0);
        miniThrusts.remove(0);

    }
}
