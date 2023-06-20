package twentyone.Classes;

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
    Vector3d position = new Vector3d(200, 200, 0);
    double rotation = -Math.PI/2;
    Vector3d velocity = new Vector3d(5.570e-3, 0, 0); //in kilometers per sec
    double rotationVelocity = 0;
    double fuel;
    boolean hasLanded = false;


    // OUR CONSTANTS

    static final double g = 1.352e-3;
    // Maximum acceleration provided by the main thruster.
    private static final double uMax = 10*g; // km s^(-2)
    // Maximum torque.
    private static final double vMax = 1; // rad s^2
    // Tolerance values (upper bound)
    private static final double deltaX = 1e-4; // km ≥ |x|
    private static final double deltaTheta = 0.02; // ≥ |θ mod 2π|
    private static final double epsilonX = 1e-4; // km s^(-1) ≥ |x'|
    private static final double epsilonY = 1e-4; // km s^(-1) ≥ |y'|
    private static final double epsilonTheta = 0.01; // rad s^(-1) = |θ'|

    public UnidentifiedFlyingObject(Vector3d position, Vector3d velocity){
        this.position = position;
        this.velocity = velocity;
    }

    UnidentifiedFlyingObject ufo;


    double stepSize = 1;
    // in km / s
    public static void main(String[] args) {
        UnidentifiedFlyingObject ufo = new UnidentifiedFlyingObject(new Vector3d(200, 200, -Math.PI/2),new Vector3d(5.570e-3, 0, 0));
        
        while (ufo.getPosition().getY() > 0) {
            ufo.feedbackController();
        }
    }

    //final 0,0, 0

    public Vector3d getPosition() {
        return position;
    }

    public void setPosition(Vector3d position) {
        this.position = position;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public Vector3d getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3d velocity) {
        this.velocity = velocity;
    }

    public double getRotationVelocity() {
        return rotationVelocity;
    }

    public void setRotationVelocity(double rotationVelocity) {
        this.rotationVelocity = rotationVelocity;
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

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
        velocity.setY(newY);

        //update rotation
        rotation = rotation + rotationVelocity*stepSize; 

        //update rotation velocity with torqe formula = length rocket * force (either + or -) * sin(rotation)
        rotationVelocity = rotationVelocity + 0.03 * miniThrust * Math.sin(Math.PI/2) * stepSize;
    }

    public void feedbackController() {
        Euler euler = new Euler();
        if (!hasLanded) {
            if (position.getY() <= 0) {
                rotationVelocity = 0;
                velocity.setX(0);
                velocity.setY(0);
                hasLanded = true;
                //The rocket doesn't launch from Titan yet despite this below
                //solver(stepSize, 1, 0);
                euler.landing(this, stepSize, 1, 0);
            }
            else {
                double mainThrust = 0;
                double miniThrust = 0;

                if (position.getX() > 50 && velocity.getX() > 5e-3) {
                    mainThrust = 1.5;
                }
                if (Math.abs(velocity.getX()) > 10e-3 && position.getX() < 17){
                    mainThrust = velocity.getX()/10;
                }
                if (position.getX() < 20e-4){
                    mainThrust = velocity.getX();
                }

                if (position.getX() < 0.1 && rotation == -90*Math.PI/180) {
                    miniThrust = 0.5;
                }
                else if (rotation > -2*Math.PI/180 && rotation < -0.1*Math.PI/180) {
                    miniThrust = -rotationVelocity/0.03;
                }

                if (Math.abs(rotation) < 0.1 && position.getY() < 10) {
                    mainThrust = -velocity.getY()/10;
                }
                if (position.getY() < 10e-4) {
                    mainThrust = velocity.getY();
                }
                
            //solver(stepSize, mainThrust, miniThrust);
            euler.landing(this, stepSize, mainThrust, miniThrust);
            }
        }
    }

    public void openLoopController() {
        //create a file with all thrusts during the landing 
        //while file has next line, run that line with those thrusts
    }
}
