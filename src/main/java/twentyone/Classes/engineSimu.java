package twentyone;
import javax.naming.InitialContext;

public class engineSimu extends CelestialBody33backup{
    private double[] currentForce = new double[3];
    private Unreal_Engine imLazy = new Unreal_Engine();
    public engineSimu(double[] initialVelocity, double[] initialPosition){
        super(initialVelocity, initialPosition, 50000);
    }
   
    public double[] calculateImpulse(CelestialBody33backup[] testing, double stepSizer){
        //aight let's go through the process...
        //firstly for velocity use our current velocity
        double[] firstVelo = super.getVelocity();
        //aight then get the next velocity we will use euler
        double[] derivVelo = imLazy.sumOf_Forces(testing, 11); //1 is a random number just not to get error, it represents probe in array
        double[] newVelocityOfDesiredPlanet = new double[3];

        for(int i = 0; i < newVelocityOfDesiredPlanet.length; i++){//O(k) in which k is the length of the array to represent a 3d vector
            newVelocityOfDesiredPlanet[i] = firstVelo[i] - (stepSizer * (derivVelo[i]));
        }

        //ez right now lets see we should have all we need to do m(Vn+1  -  Vn) to get impulse
        double[] impulse_vector = new double[3];

        for(int i = 0; i < impulse_vector.length; i++){//O(k) in which k is the length of the array to represent a 3d vector
            impulse_vector[i] = super.getMass()*(newVelocityOfDesiredPlanet[i] - firstVelo[i]);
        }
        return impulse_vector;
    }

    public void finalCombinedForce(CelestialBody33backup[] testobago, double gun){
        //aight I can't be bothered so Imma call the functions to get the forces
        double[] force = imLazy.sumOf_Forces(testobago, 11);
        double[] impulse = calculateImpulse(testobago, gun);
        double[] finalforce = new double[3];
        for(int i = 0; i < finalforce.length; i++){//O(k) in which k is the length of the array to represent a 3d vector
            finalforce[i] = force[i] + impulse[i];
        }
        setNewForce(finalforce);
    }
    
}
