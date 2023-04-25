package twentyone.Classes;


public class engineSimu extends CelestialBody{
    private Vector3d currentForce = new Vector3d(0, 0, 0);
    private Unreal_Engine imLazy = new Unreal_Engine();
    public engineSimu(Vector3d initialVelocity, Vector3d initialPosition){
        super(initialVelocity, initialPosition, 50000);
    }
   
    public Vector3d calculateImpulse(CelestialBody[] testing, double stepSizer){
        //aight let's go through the process...
        //firstly for velocity use our current velocity
        Vector3d firstVelo = super.getVelocity();
        //aight then get the next velocity we will use euler
        Vector3d derivVelo = imLazy.sumOf_Forces(testing, 11); //1 is a random number just not to get error, it represents probe in array
        Vector3d newVelocityOfDesiredPlanet = new Vector3d(0,0, 0);

        for(int i = 0; i < newVelocityOfDesiredPlanet.length; i++){//O(k) in which k is the length of the array to represent a 3d vector
            newVelocityOfDesiredPlanet[i] = firstVelo[i] - (stepSizer * (derivVelo[i]));
        }

        //ez right now lets see we should have all we need to do m(Vn+1  -  Vn) to get impulse
        Vector3d impulse_vector = new Vector3d(0, 0, 0);

        for(int i = 0; i < impulse_vector.length; i++){//O(k) in which k is the length of the array to represent a 3d vector
            impulse_vector[i] = super.getMass()*(newVelocityOfDesiredPlanet[i] - firstVelo[i]);
        }
        return impulse_vector;
    }

    public void finalCombinedForce(CelestialBody[] testobago, double gun){
        //aight I can't be bothered so Imma call the functions to get the forces
        Vector3d force = imLazy.sumOf_Forces(testobago, 11);
        Vector3d impulse = calculateImpulse(testobago, gun);
        Vector3d finalforce = new Vector3d(0, 0, 0);
        for(int i = 0; i < finalforce.length; i++){//O(k) in which k is the length of the array to represent a 3d vector
            finalforce[i] = force[i] + impulse[i];
        }
        super.setNewForce(finalforce);
    }
    
}
