package twentyone.Classes;

/**
 * A {@code Abstract Class} that gets extended by other classes.
 * @see twentyone.Classes.AdamsBashforth
 * @see twentyone.Classes.Euler
 * @see twentyone.Classes.VerletSolver
 */
public abstract class Solver {
    /**Gravitational constant*/
    private final double G = 6.6743E-20;

    /**
     * Constructor for the {@code Solver Class}.
     */
    public Solver() {
    }

    /**
     * Unreal_Engine method to calculate force.
     * @param iCelBody as a {@code Celestial Body}
     * @param jCelBody as a {@code Celestial Body}
     * @return the force as a {@code Vector3d Class}
     */
    public Vector3d calculateForce(CelestialBody iCelBody, CelestialBody jCelBody){
        //get the vars needed
        double mass_of_Jobject = jCelBody.getMass();
        Vector3d finalForce = new Vector3d(0,0,0);
        double denominatorResult = 0;

        //multiply G with Mj
        double multiple = mass_of_Jobject * G; // no need to multiply by Mi

        //calculate difference between vectors
        finalForce = iCelBody.getPosition().sub(jCelBody.getPosition());


        //get denominator 
        Vector3d sqrt = finalForce.mulVector(finalForce);
        denominatorResult = denominatorResult + sqrt.getX() + sqrt.getY() + sqrt.getZ();

        denominatorResult = Math.pow(Math.sqrt(denominatorResult), 3);

        //get final number
        finalForce = finalForce.mul(multiple/denominatorResult);

        //return vector
        return finalForce;
    }

    /**
     * Unreal_Engine method to sum all forces (multiplied by -1 at the end).
     * @param theCelBodies as a {@code Celestial Body Array}
     * @param desiredPlanet as an {@code integer}
     * @return the sum of all forces as a {@code Vector3d Class}
     */
    public Vector3d sumOf_Forces(CelestialBody[] theCelBodies, int desiredPlanet){
        //create the variables
        Vector3d sumForces = new Vector3d(0, 0, 0);
        Vector3d vector = new Vector3d(0, 0, 0);

        //get the force between your desiredPlanet and ith planet and then add it to sumForces which is the total sum
        for(int i = 0; i<theCelBodies.length-1; i++){
            if(i != desiredPlanet){
                vector = calculateForce(theCelBodies[desiredPlanet], theCelBodies[i]);
                sumForces = sumForces.add(vector);
            }
        }

        //if there is a problem with the values multiply sumForces by -1 here
        //-1 with adams but not euler
        sumForces = sumForces.mul(-1);
        //return the total force
        return sumForces;  
    }
}
