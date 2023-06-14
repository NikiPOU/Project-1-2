package twentyone.Classes;

public class UnidentifiedFlyingObject {
    //Main thruster class
    // x position 
    // y position
    // g ≈ 1.352 m s-2
    // x¨ = u sin(θ)
    // y¨ = u cos(θ) − g
    // umax = 10g
    // four smaller side thrusters
    // theta is rotation 
    // θ¨ = v
    //  vmax = 1
    // |x| ≤ δx
    // |θ mod 2π| ≤ δθ
    // |x˙| ≤ εx
    // |y˙| ≤ εy
    // |θ˙| = εθ
    //Take tolerance values δx = 0.1 m = 10−4 km, δθ = 0.02 ε
    //decided length of the rocket: 30m

    //Starting from the orbit, we need x, y position and velocity
    Vector3d position = new Vector3d(200, 200, 0);
    double rotation = Math.PI/2;
    Vector3d velocity = new Vector3d(5.570e-3, 0, 0); //in kilometers per sec
    double rotationVelocity = 0;
    double fuel;

    double g = 1.352e-3;
    double stepSize = 1;
    // in km / s

    public static void main(String[] args) {
        UnidentifiedFlyingObject ufo = new UnidentifiedFlyingObject();
        
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
        double u = 0;
        double miniThrust = 0;

        if (position.getX() > 0) {
            u = 1e-3;
        }
        else {
            u = -1e-3;
        }

        if (rotation > 0) {
            miniThrust = -5e-3;
        }
        else {
            miniThrust = 5e-3;
        }

        solver(stepSize, u, miniThrust);
    }

    public void openLoopController() {
        //create a file with all thrusts during the landing 
        //while file has next line, run that line with those thrusts
    }
}
