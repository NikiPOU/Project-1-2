package twentyone;

class Unreal_Engine{
    private final double gravity = 6.6743 * Math.pow(10, -20);

    /**
     * Calculates the force on the first Celestial Body.
     * @param _IcelBody
     * @param _JcelBody
     * @return an array of the final force in form of a vector
     */
    public double[] calculateForce(CelestialBody3 _IcelBody, CelestialBody3 _JcelBody){
        //get the vars needed
        double mass_of_Jobject = _JcelBody.getMass();
        double[] finalForce = new double[3];
        double[] ipositionVector = _IcelBody.getPosition();
        double[] jpositionVector = _JcelBody.getPosition();
        double denominatorResult = 0;


        //multiply G with Mj
        double multiple = mass_of_Jobject * gravity; // no need to multiply by Mi


        //calculate difference between vectors
        for(int i = 0; i<ipositionVector.length; i++){//O(k) in which k is the length of the array to represent a 3d vector
            finalForce[i] = ipositionVector[i] - jpositionVector[i];
        }


        //get denominator 
        for(int i = 0; i<finalForce.length; i++){//O(k) in which k is the length of the array to represent a 3d vector
            denominatorResult = denominatorResult + (finalForce[i] * finalForce[i]);
        }
        denominatorResult = Math.pow(Math.sqrt(denominatorResult), 3);

        //get final number
        for(int i = 0; i<finalForce.length; i++){//O(k) in which k is the length of the array to represent a 3d vector
            finalForce[i] = multiple * (finalForce[i] / denominatorResult);
        }



        //return vector
        return finalForce;

        //the final BigO here is O(k) in which k is the length of the array to represent a 3d vector
    }

    /**
     * It gets the sum of the forces of all the Celestial Bodies onto the given Celestial Body
     * @param theCelBodies
     * @param desiredPlanet
     * @return an array with the sum of the forces
     */
    public double[] sumOf_Forces(CelestialBody3[] theCelBodies, int desiredPlanet){
        //create the variables
        double[] sumForces = new double[3];
        double[] vector = new double[3];

        //get the force between your desiredPlanet and ith planet and then add it to sumForces which is the total sum
        for(int i = 0; i<theCelBodies.length; i++){//O(n) in which n is the amount of celestial bodies
            if(i != desiredPlanet){
                vector = calculateForce(theCelBodies[desiredPlanet], theCelBodies[i]); //O(k) as stated before
                for(int j = 0; j<sumForces.length; j++){//O(k) in which k is the length of the array to represent a 3d vector
                    sumForces[j] += vector[j];
                }
            }
        }

        //if there is a problem with the values multiply sumForces by -1 here

        //return the total force
        return sumForces;
        //the final BigO of this function is O(nk) in which n is the amount of celestial bodies, in which k is the length of the array to represent a 3d vector
    }

    /**
     * Calculates the new positions and velocities of the Celestial Bodies.
     * @param theCelBodies
     * @param theDesired
     * @param stepSizer
     * @return the array of Celestial Bodies with updated positions and velocities
     */
    public CelestialBody3[] Eulers(CelestialBody3[] theCelBodies, int theDesired, double stepSizer){
        //get the values you need bro and set up

            //our V' by this I mean first derivative of velocity
        double[] derivativeOfVelo = sumOf_Forces(theCelBodies, theDesired); // no need to divide by Mi ... as stated before O(nk)
        
            //our Vn and Xn
        double[] velocityOfDesiredPlanet = theCelBodies[theDesired].getVelocity();
        double[] positionOfDesiredPlante = theCelBodies[theDesired].getPosition();
        
            //our Vn+1 and Xn+1
        double[] newVelocityOfDesiredPlanet = new double[3];
        double[] newPositionOfDesiredPlanet = new double[3];


        //get Xn+1 which is for position 
            //we have to do Xn+1 = Xn + h(Vn)
        for(int i = 0; i < newPositionOfDesiredPlanet.length; i++){//O(k) in which k is the length of the array to represent a 3d vector
            newPositionOfDesiredPlanet[i] = positionOfDesiredPlante[i] + (stepSizer*(velocityOfDesiredPlanet[i]));
        }



        //get Vn+1 which is for velocity
            //we have to do Vn+1 = Vn + h(V'n)
        for(int i = 0; i < newVelocityOfDesiredPlanet.length; i++){//O(k) in which k is the length of the array to represent a 3d vector
            newVelocityOfDesiredPlanet[i] = velocityOfDesiredPlanet[i] - (stepSizer * (derivativeOfVelo[i]));
        }



        //update the celestial body you are predicting
        theCelBodies[theDesired].setNewPostion(newPositionOfDesiredPlanet);
        theCelBodies[theDesired].setNewVelocity(newVelocityOfDesiredPlanet);
        
        return theCelBodies;
        //we take largest BigO in this function so it's O(nk)
    }

}