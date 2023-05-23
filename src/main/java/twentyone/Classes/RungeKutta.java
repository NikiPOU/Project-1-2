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
        Vector3d newPosition = new Vector3d(newX,newY,newZ);

        Vector3d velocityDerivative = sumOf_Forces(allBodies, bodyIndex); 



        double kv1x = h * velocityX(velocityDerivative.getX());
        double kv1y = h * velocityY(velocityDerivative.getY());
        double kv1z = h * velocityZ(velocityDerivative.getZ());

        double kv2x = h * velocityX(velocityDerivative.getX() + 0.5 * kv1x);
        double kv2y = h * velocityY(velocityDerivative.getY() + 0.5 * kv1y);
        double kv2z = h * velocityZ(velocityDerivative.getZ() + 0.5 * kv1z);

        double kv3x = h * velocityX(velocityDerivative.getX() + 0.5 * kv2x);
        double kv3y = h * velocityY(velocityDerivative.getY() + 0.5 * kv2y);
        double kv3z = h * velocityZ(velocityDerivative.getZ() + 0.5 * kv2z);

        double kv4x = h * velocityX(velocityDerivative.getX() + kv3x);
        double kv4y = h * velocityY(velocityDerivative.getY() + kv3y);
        double kv4z = h * velocityZ(velocityDerivative.getZ() + kv3z);


        double newvX = currentVel.getX() + (kv1x + 2.0 * kv2x + 2.0 * kv3x + kv4x) / 6.0;
        double newvY = currentVel.getY() + (kv1y + 2.0 * kv2y + 2.0 * kv3y + kv4y) / 6.0;
        double newvZ = currentVel.getZ() + (kv1z + 2.0 * kv2z + 2.0 * kv3z + kv4z) / 6.0;
        Vector3d newVelocity = new Vector3d(newvX,newvY,newvZ);

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
