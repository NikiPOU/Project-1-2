package twentyone.Classes;

/**
 * Calculation of the celestial bodies coordinates using an Adams-Bashforth solver.
 */
public class AdamsBashforth extends Solver {
    //Placeholder for the old velocity, used because Adams-Bashforth is a two-step method 
    //(using both the last velocity and the one before that)
    private Vector3d[] oldVelocity = {new Vector3d(0,0, 0), new Vector3d(0,0, 0), new Vector3d(0,0, 0), new Vector3d(0,0, 0),
        new Vector3d(0,0, 0),new Vector3d(0,0, 0),new Vector3d(0,0, 0),new Vector3d(0,0, 0),new Vector3d(0,0, 0),new Vector3d(0,0, 0),new Vector3d(0,0, 0),new Vector3d(0,0, 0)};

    /**
     * Adams-Bashforth differential equation solver for the position and velocity of a celestial body. It uses the 
     * following formula Yn+2 = Yn+1 + 3/2 * h * Yn+1' - 1/2 * h * Yn'.
     * @param allBodies an array of all celestial bodies.
     * @param bodyIndex the index of the celestial body for which the position and velocity will be calculated.
     * @param stepsize h.
     * @return the updated array of all celestial bodies.
     */
    public CelestialBody[] adams(CelestialBody[] allBodies, int bodyIndex, double stepsize) {

        //The derivative of velocity is the sum of all forces since a = V' and a = F/m (/m happens inside sumOf_Forces)
        Vector3d velocity0Derivative = sumOf_Forces(allBodies, bodyIndex);

        Vector3d velocity0 = new Vector3d(0,0,0);
        //For the first run Vn is the initial velocity that is given, after that it is the velocity saved in the placeholder
        if (oldVelocity[bodyIndex].getX() == 0.0) {
            velocity0 = allBodies[bodyIndex].getVelocity();
        }
        else {
            velocity0 = oldVelocity[bodyIndex];
        }

        Vector3d velocity1 = new Vector3d(0,0,0);
        Vector3d position1 = new Vector3d(0,0,0);
        //For the first run Vn+1 and Xn+1 do not exist yet, so the Euler solver is used to calculate them. After that
        //they are the last calculated velocity and position saved in the array with all celestial bodies
        if (oldVelocity[bodyIndex].getX() == 0.0) {
            Euler engine = new Euler();
            velocity1 = engine.Eulers(allBodies, bodyIndex, stepsize, false)[bodyIndex].getVelocity();
            position1 = engine.Eulers(allBodies, bodyIndex, stepsize, false)[bodyIndex].getPosition();
        }
        else {
            velocity1 = allBodies[bodyIndex].getVelocity();
            position1 = allBodies[bodyIndex].getPosition();
        }
        
        //Now that the velocity is updated, the Vn+1' can be calculated the same way as Vn'
        Vector3d velocity1Derivative = sumOf_Forces(allBodies, bodyIndex); 
        //The old velocity is now Vn+1 instead of Vn
        oldVelocity[bodyIndex] = velocity1;
        
        Vector3d position2 = new Vector3d(0, 0,0);
        //for the position, the formula is Xn+2 = Xn+1 + 3/2 * h * Vn+1 - 1/2 * h * Vn (since V = X')
        position2 = position1.add(velocity1.mul(3/2*stepsize).sub(velocity0.mul(1/2*stepsize)));

        Vector3d velocity2 = new Vector3d(0, 0,0);
        //for the velocity, the formula is Vn+2 = Vn+1 + 3/2 * h * Vn+1' - 1/2 * h * Vn'
        velocity2 = velocity1.add(velocity1Derivative.mul(3/2*stepsize).sub(velocity0Derivative.mul(1/2*stepsize)));

        //Updates the velocity and position of the given celestial body in the array of celestial bodies
        allBodies[bodyIndex].setNewPostion(position2);
        allBodies[bodyIndex].setNewVelocity(velocity2);
        
        return allBodies;
    }
}
