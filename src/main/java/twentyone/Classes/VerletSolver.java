package twentyone.Classes;

/**
 * Calculation of the {@code Celestial Bodies'} coordinates using a Verlet solver.
 * This class extends the {@code Solver} Class.
 * @see twentyone.Classes.Solver
 */
public class VerletSolver extends Solver {

    private double oldMainThrust = 0;
    private double oldMiniThrust = 0;

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

        // return allBodies

    }

    public void landing(UnidentifiedFlyingObject ufo, double stepSize, double mainThrust, double miniThrust) {
            
        Vector3d pos = ufo.getPosition(); 
        Vector3d vel = ufo.getVelocity();

        //Vector3d newPos = prevPos.add(prevVel.mul(dt)).add(prevA.mul(0.5 * dt * dt));

        double x = pos.getX() + vel.getX()*stepSize + mainThrust * Math.sin(vel.getZ()) * stepSize * stepSize * 0.5;
        double y = pos.getY() + vel.getY()*stepSize + mainThrust * (Math.cos(vel.getZ()) - UnidentifiedFlyingObject.g) * stepSize * stepSize * 0.5;
        double o = pos.getZ() + ufo.getVelocity().getZ()*stepSize + 0.03 * miniThrust * Math.sin(Math.PI/2) * stepSize * stepSize * 0.5;

        Vector3d newPosition = new Vector3d(x,y,o); // x, y, θ

        //Vector3d newVel = prevVel.add(newA.add(prevA).mul(0.5 * dt));
        double xV = vel.getX() + (mainThrust * Math.sin(vel.getZ()) +  oldMainThrust * Math.sin(o)) * 0.5 * stepSize;
        double yV = vel.getY() + (mainThrust * (Math.cos(vel.getZ()) - UnidentifiedFlyingObject.g) + oldMainThrust * (Math.cos(o) - UnidentifiedFlyingObject.g)) * 0.5 * stepSize;
        double oV = vel.getZ() + (0.03 * miniThrust * Math.sin(Math.PI/2) + 0.03 * oldMiniThrust * Math.sin(Math.PI/2)) * stepSize * 0.5;

        Vector3d newVelocity = new Vector3d(xV,yV,oV); // x',y',θ'


        ufo.setPosition(newPosition);
        ufo.setVelocity(newVelocity);
    }

}
