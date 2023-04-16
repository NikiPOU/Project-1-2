package twentyone.Classes;

/**
 * Calculation of the celestial bodies coordinates using an Adams-Bashforth solver.
 */
public class AdamsBashforth {
    //gravitational constant
    private final double G = 6.6743E-20;

    //Placeholder for the old velocity, used because Adams-Bashforth is a two-step method 
    //(using both the last velocity and the one before that)
    private double[][] oldVelocity = new double[12][3];

    /**
     * Unreal_Engine method to calculate force.
     * @param _IcelBody
     * @param _JcelBody
     * @return
     */
    public double[] calculateForce(CelestialBody _IcelBody, CelestialBody _JcelBody){
        //get the vars needed
        double mass_of_Jobject = _JcelBody.getMass();
        double[] finalForce = new double[3];
        double[] ipositionVector = _IcelBody.getPosition();
        double[] jpositionVector = _JcelBody.getPosition();
        double denominatorResult = 0;


        //multiply G with Mj
        double multiple = mass_of_Jobject * G; // no need to multiply by Mi


        //calculate difference between vectors
        for(int i = 0; i<ipositionVector.length; i++){
            finalForce[i] = ipositionVector[i] - jpositionVector[i];
        }


        //get denominator 
        for(int i = 0; i<finalForce.length; i++){
            denominatorResult = denominatorResult + (finalForce[i] * finalForce[i]);
        }
        denominatorResult = Math.pow(Math.sqrt(denominatorResult), 3);

        //get final number
        for(int i = 0; i<finalForce.length; i++){
            finalForce[i] = multiple * (finalForce[i] / denominatorResult);
        }

        //return vector
        return finalForce;
    }

    /**
     * Unreal_Engine method to sum all forces (multiplied by -1 at the end).
     * @param theCelBodies
     * @param desiredPlanet
     * @return
     */
    public double[] sumOf_Forces(CelestialBody[] theCelBodies, int desiredPlanet){
        //create the variables
        double[] sumForces = new double[3];
        double[] vector = new double[3];

        //get the force between your desiredPlanet and ith planet and then add it to sumForces which is the total sum
        for(int i = 0; i<theCelBodies.length; i++){
            if(i != desiredPlanet){
                vector = calculateForce(theCelBodies[desiredPlanet], theCelBodies[i]);
                for(int j = 0; j<sumForces.length; j++){
                    sumForces[j] += vector[j];
                }
            }
        }

        //if there is a problem with the values multiply sumForces by -1 here
        for (int i = 0; i < 3; i++) {
            sumForces[i] = -sumForces[i];
        }
        //return the total force
        return sumForces;
        
    }

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
        double[] velocity0Derivative = sumOf_Forces(allBodies, bodyIndex); 

        double[] velocity0 = new double[3];
        //For the first run Vn is the initial velocity that is given, after that it is the velocity saved in the placeholder
        if (oldVelocity[bodyIndex][0] == 0.0) {
            velocity0 = allBodies[bodyIndex].getVelocity();
        }
        else {
            velocity0 = oldVelocity[bodyIndex];
        }

        double[] velocity1 = new double[3];
        double[] position1 = new double[3];
        //For the first run Vn+1 and Xn+1 do not exist yet, so the Euler solver is used to calculate them. After that
        //they are the last calculated velocity and position saved in the array with all celestial bodies
        if (oldVelocity[bodyIndex][0] == 0.0) {
            Unreal_Engine engine = new Unreal_Engine();
            velocity1 = engine.Eulers(allBodies, bodyIndex, stepsize)[bodyIndex].getVelocity();
            position1 = engine.Eulers(allBodies, bodyIndex, stepsize)[bodyIndex].getPosition();
        }
        else {
            velocity1 = allBodies[bodyIndex].getVelocity();
            position1 = allBodies[bodyIndex].getPosition();
        }
        
        //Now that the velocity is updated, the Vn+1' can be calculated the same way as Vn'
        double[] velocity1Derivative = sumOf_Forces(allBodies, bodyIndex); 
        //The old velocity is now Vn+1 instead of Vn
        oldVelocity[bodyIndex] = velocity1;
        
        double[] position2 = new double[3];
        //for the position, the formula is Xn+2 = Xn+1 + 3/2 * h * Vn+1 - 1/2 * h * Vn (since V = X')
        for(int i = 0; i < 3; i++){
            position2[i] = position1[i] + (3/2*stepsize*velocity1[i]) - (1/2 * stepsize * velocity0[i]);
        }

        double[] velocity2 = new double[3];
        //for the velocity, the formula is Vn+2 = Vn+1 + 3/2 * h * Vn+1' - 1/2 * h * Vn'
        for(int i = 0; i < 3; i++){
            velocity2[i] = velocity1[i] + (3/2 * stepsize * velocity1Derivative[i]) - (1/2 * stepsize * velocity0Derivative[i]);
        }

        //Updates the velocity and position of the given celestial body in the array of celestial bodies
        allBodies[bodyIndex].setNewPostion(position2);
        allBodies[bodyIndex].setNewVelocity(velocity2);
        
        return allBodies;
    }
}
