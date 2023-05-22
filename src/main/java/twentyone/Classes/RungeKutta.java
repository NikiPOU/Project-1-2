package twentyone.Classes;

/**
 * Calculation of the {@code Celestial Bodies'} coordinates using a Runge-Kutta solver.
 * This class extends the {@code Solver} Class.
 * @see twentyone.Classes.Solver
 */
public class RungeKutta extends Solver{

    public CelestialBody[] rungKutta(CelestialBody[] allBodies, int bodyIndex, double stepSize) {

        //getting the initial position and velocity of the desired object from the array of all objects
        Vector3d currentPos = allBodies[bodyIndex].getPosition();
        Vector3d currentVel = allBodies[bodyIndex].getVelocity();

        //step size for 1 iteration
        double h= stepSize;


        //Rung-Kutta integration formulas
        double k1x = h * velocityX(currentVel.getX());
        double k1y = h * velocityY(currentVel.getY());
        double k1z = h * velocityZ(currentVel.getZ());

        double k2x = h * velocityX(currentVel.getX() + 0.5 * k1x);
        double k2y = h * velocityY(currentVel.getY() + 0.5 * k1y);
        double k2z = h * velocityZ(currentVel.getZ() + 0.5 * k1z);

        double k3x = h * velocityX(currentVel.getX() + 0.5 * k2x);
        double k3y = h * velocityY(currentVel.getY() + 0.5 * k2y);
        double k3z = h * velocityZ(currentVel.getZ() + 0.5 * k2z);

        double k4x = h * velocityX(currentVel.getX() + k3x);
        double k4y = h * velocityY(currentVel.getY() + k3y);
        double k4z = h * velocityZ(currentVel.getZ() + k3z);


        double newX = currentPos.getX() + (k1x + 2.0 * k2x + 2.0 * k3x + k4x) / 6.0;
        double newY = currentPos.getY() + (k1y + 2.0 * k2y + 2.0 * k3y + k4y) / 6.0;
        double newZ = currentPos.getZ() + (k1z + 2.0 * k2z + 2.0 * k3z + k4z) / 6.0;
        Vector3d newPosition = new Vector3d(newX,newY,newX);


        double newVX = velocityX(currentVel.getX() + k1x);
        double newVY = velocityY(currentVel.getY() + k1y);
        double newVZ = velocityZ(currentVel.getZ() + k1z);
        Vector3d newVelocity = new Vector3d(newVX,newVY,newVZ);

        allBodies[bodyIndex].setNewPostion(newPosition);
        allBodies[bodyIndex].setNewVelocity(newVelocity);



        return allBodies;
    }



    // Define the velocity functions for each dimension
    public static double velocityX(double vx) {
        // Add any relevant forces or constraints affecting the x velocity
        // For example, you can add acceleration or drag forces here
        return vx;
    }

    public static double velocityY(double vy) {
        // Add any relevant forces or constraints affecting the y velocity
        return vy;
    }

    public static double velocityZ(double vz) {
        // Add any relevant forces or constraints affecting the z velocity
        return vz;
    }


}
