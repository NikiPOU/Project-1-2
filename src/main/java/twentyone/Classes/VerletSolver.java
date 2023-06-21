package twentyone.Classes;

/**
 * Calculation of the {@code Celestial Bodies'} coordinates using a Verlet solver.
 * This class extends the {@code Solver} Class.
 * @see twentyone.Classes.Solver
 */
public class VerletSolver extends Solver {

    public CelestialBody[] verlet(CelestialBody[] allBodies, int bodyIndex, double stepsize) {

        // take values from the rest of the program (CelestialBody)

        // position verlet

        // instance variables
        Vector3d prevPos = allBodies[bodyIndex].getPosition();
        Vector3d prevVel = allBodies[bodyIndex].getVelocity();

        // Dividing by the mass already happens inside sumOf_Forces
        Vector3d prevA = sumOf_Forces(allBodies, bodyIndex); // computing the acceleration

        // double time = 0.0;

        double dt = stepsize; // time step determining time passed between each iteration

        Vector3d newPos = prevPos.add(prevVel.mul(dt)).add(prevA.mul(0.5 * dt * dt)); // equation for finding the new x
                                                                                      // according to verlet PosX + v *
                                                                                      // dt + 0.5 * a * dt * dt;

        allBodies[bodyIndex].setNewPostion(newPos); // adding the new position to the array allBodies

        // compute the new acceleration
        // Dividing by the mass already happens inside sumOf_Forces + it's a Vector3d
        // not a double
        Vector3d newA = sumOf_Forces(allBodies, bodyIndex); // computing the acceleration

        Vector3d newVel = prevVel.add(newA.add(prevA).mul(0.5 * dt));// equation to find the next velocity according to
                                                                     // verlet prevVel + 0.5 * (newa + preva) * dt;
        // Bodies array
        // Change the velocity of the body that we calculate for, not for all bodies
        allBodies[bodyIndex].setNewVelocity(newVel); // add the new velocity to the Celestial Bodies array

        return allBodies;
    }
}
