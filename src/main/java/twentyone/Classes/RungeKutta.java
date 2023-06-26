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
        Vector3d velocity0Derivative = sumOfForces(allBodies, bodyIndex); 

        //next velocity
        Vector3d velocity1 = velocity0.add(velocity0Derivative.mul(stepSize));

        //velocity at point y 
        Vector3d k1Position = velocity0.mul(stepSize);
        //velocity at point y+0.5*k1
        Vector3d k2Position = derivativeFormula(velocity0, velocity1, stepSize, position0.add(k1Position.mul(0.5))).mul(stepSize);
        //velocity at point y+0.5*k2
        Vector3d k3Position = derivativeFormula(velocity0, velocity1, stepSize, position0.add(k2Position.mul(0.5))).mul(stepSize);
        //velocity at point y+k3
        Vector3d k4Position = derivativeFormula(velocity0, velocity1, stepSize, position0.add(k3Position)).mul(stepSize);

        Vector3d newPos = position0.add((k1Position.add(k2Position.mul(2.0)).add(k3Position.mul(2.0)).add(k4Position)).mul(1/6.0));

        allBodies[bodyIndex].setNewPostion(newPos);
        allBodies[bodyIndex].setNewVelocity(velocity1);
        Vector3d velocity1Derivative = sumOfForces(allBodies, bodyIndex); 


        Vector3d k1Velocity = velocity0Derivative.mul(stepSize);
        Vector3d k2Velocity = derivativeFormula(velocity0Derivative, velocity1Derivative, stepSize, position0.add(k1Velocity.mul(0.5))).mul(stepSize);
        Vector3d k3Velocity = derivativeFormula(velocity0Derivative, velocity1Derivative, stepSize, position0.add(k2Velocity.mul(0.5))).mul(stepSize);
        Vector3d k4Velocity = derivativeFormula(velocity0Derivative, velocity1Derivative, stepSize, position0.add(k1Velocity)).mul(stepSize);
        Vector3d newVel = velocity0.add((k1Velocity.add(k2Velocity.mul(2.0)).add(k3Velocity.mul(2.0)).add(k4Velocity)).mul(1/6.0));

        allBodies[bodyIndex].setNewPostion(newPos);
        allBodies[bodyIndex].setNewVelocity(newVel);

        return allBodies;
    }

    public Vector3d derivativeFormula(Vector3d oldy, Vector3d newy, double h, Vector3d y) {
        Vector3d derivative = (newy.sub(oldy)).mul(1/h);
        if (y.norm() != 0.0) {
            y = y.mul(1/y.norm());
        }
        return oldy.add(derivative.mulVector(y));
    }
}
