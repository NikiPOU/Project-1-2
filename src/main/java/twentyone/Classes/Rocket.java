package twentyone.Classes;

/**
 * The {@code Rocket Class} extends {@code CelestialBody Class}
 */
public class Rocket extends CelestialBody{
    /*
     * Potential launching positions (x, y, z):
     *  north pole: -148186906.893642, -27823158.5715694, 33746.8987977113 + 6370
     *  south pole: -148186906.893642, -27823158.5715694, 33746.8987977113 - 6370
     *  pacific ocean: -148186906.893642 + 6370, -27823158.5715694, 33746.8987977113
     *  Africa: -148186906.893642 - 6370, -27823158.5715694, 33746.8987977113
     *  South America: -148186906.893642, -27823158.5715694 + 6370, 33746.8987977113
     *  Asia: -148186906.893642, -27823158.5715694 - 6370, 33746.8987977113
     * 
     * velocity with a 90 degrees angle from the earths surface
     */
    private Vector3d currentForce = new Vector3d(0, 0, 0);
    private Euler imLazy = new Euler();
    private double fuel = 100000000;
    /**
     * Constructor for the {@code Rocket Class}.
     * @param initialVelocity as a {@code Vector3d Class}
     * @param initialPosition as a {@code Vector3d Class}
     */
    public Rocket(Vector3d initialVelocity, Vector3d initialPosition){
        super(initialVelocity, initialPosition, 50000);
    }
   
    /**
     * Calculates the impulse for the {@code Rocket Class}.
     * @param testing as a {@code Celestial Body Array}
     * @param stepSizer as a {@code double}
     * @return the impulse as a {@code Vector3d Class}
     */
    public Vector3d calculateImpulse(CelestialBody[] testing, double stepSizer){
        //aight let's go through the process...
        //firstly for velocity use our current velocity
        Vector3d firstVelo = super.getVelocity();
        //aight then get the next velocity we will use euler
        Vector3d derivVelo = imLazy.sumOf_Forces(testing, 11); //1 is a random number just not to get error, it represents probe in array
        Vector3d newVelocityOfDesiredPlanet = new Vector3d(0,0, 0);

        newVelocityOfDesiredPlanet = firstVelo.sub(derivVelo.mul(stepSizer));

        //ez right now lets see we should have all we need to do m(Vn+1  -  Vn) to get impulse
        Vector3d impulse_vector = new Vector3d(0, 0, 0);

        impulse_vector = (newVelocityOfDesiredPlanet.sub(firstVelo)).mul(super.getMass());
        return impulse_vector;
    }

    /**Coming soon */
    public void finalCombinedForce(CelestialBody[] testobago, double gun){
        //aight I can't be bothered so Imma call the functions to get the forces
        Vector3d force = imLazy.sumOf_Forces(testobago, 11);
        Vector3d impulse = calculateImpulse(testobago, gun);
        Vector3d finalforce = new Vector3d(0, 0, 0);

        finalforce = force.add(impulse);//O(k) in which k is the length of the array to represent a 3d vector

        super.setNewForce(finalforce);
    }

    
    private double norm_Two(Vector3d vect){
        double finala = 0;
        finala = Math.sqrt(vect.getX() + vect.getY() + vect.getZ());
        return finala;
    } 


    public Vector3d getAnImpulse(Vector3d force, double startTime, double endtime){
        if(norm_Two(force) <= 3*Math.pow(10, 7)){
            Vector3d impulse = force.mul(endtime).sub(force.mul(startTime)); 
            return impulse;
        }
        return null;
    }


    public void boostedVelo(Vector3d force, double start, double end){
        Vector3d currentVelo = super.getVelocity();
        consumeForce(force, (end-start));
        Vector3d impulse = getAnImpulse(force, start, end);
        consumeImpulse(impulse, (end-start));
        Vector3d finalVelo = currentVelo.add(impulse.mul(1/super.getMass()));
        super.setNewVelocity(finalVelo);
    }

    public void consumeImpulse(Vector3d imp, double timeInterval){
        double foir = norm_Two(imp)*timeInterval;
        fuel = fuel - foir;
        //to consume fuel multiply with 1 m/s
        //our velocity is in km/s so what we could do is convert the final velo of boosted to 
        //m/s so then we can use it to consume 
    }

    public void consumeForce(Vector3d force, double timeInterval){
        double foir = norm_Two(force)*timeInterval;
        fuel = fuel - foir;
        //to consume fuel multiply with 1 m
        //we could use displacement
    }

    public double getFuel() {
        return fuel;
    }

}
