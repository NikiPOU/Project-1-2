package twentyone.Classes;

/**
 * Calculation of the {@code Celestial Bodies'} coordinates using a Runge-Kutta solver.
 * This class extends the {@code Solver} Class.
 * @see twentyone.Classes.Solver
 */
public class RungeKutta extends Solver{

    public CelestialBody[] rungKutta(CelestialBody[] allBodies, int bodyIndex, double stepSize) {

        //getting the initial position and velocity of the desired object from the array of all objects
        Vector3d position0 = allBodies[bodyIndex].getPosition();
        Vector3d velocity0 = allBodies[bodyIndex].getVelocity();

        Vector3d k1Position = velocity0.mul(stepSize);
        Vector3d k2Position = velocity0.mul(stepSize);
        Vector3d k3Position = velocity0.mul(stepSize);
        Vector3d k4Position = velocity0.mul(stepSize);
        Vector3d newPos = position0.add((k1Position.add(k2Position).add(k3Position).add(k4Position)).mul(1/4.0));

        Vector3d velocityDerivative = sumOf_Forces(allBodies, bodyIndex); 

        Vector3d k1Velocity = velocityDerivative.mul(stepSize);
        Vector3d k2Velocity = velocityDerivative.mul(stepSize);
        Vector3d k3Velocity = velocityDerivative.mul(stepSize);
        Vector3d k4Velocity = velocityDerivative.mul(stepSize);
        Vector3d newVel = velocity0.add((k1Velocity.add(k2Velocity).add(k3Velocity).add(k4Velocity)).mul(1/4.0));

        allBodies[bodyIndex].setNewPostion(newPos);
        allBodies[bodyIndex].setNewVelocity(newVel);

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
