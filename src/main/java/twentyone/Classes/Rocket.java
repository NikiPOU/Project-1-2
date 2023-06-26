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
    private double fuel = 0;
    /**
     * Constructor for the {@code Rocket Class}.
     * @param initialVelocity as a {@code Vector3d Class}
     * @param initialPosition as a {@code Vector3d Class}
     */
    public Rocket(Vector3d initialVelocity, Vector3d initialPosition){
        super(initialVelocity, initialPosition, 50000);
    }
    
    /**
     * Get the norm two of a vector.
     * @param vect a 3d vector
     * @return a double norm two of a vector
     */
    private double norm_Two(Vector3d vect){
        double finala = 0;
        finala = Math.sqrt(vect.getX()*vect.getX() + vect.getY()*vect.getY() + vect.getZ()*vect.getZ());
        return finala;
    } 

    /**
     * Calculates and returns an impulse if the force is small enough.
     * @param force the total force the impulse must have
     * @param startTime the start time of the impulse
     * @param endtime the end time of the impulse
     * @return a 3D vector of the calculated impulse
     */
    public Vector3d getAnImpulse(Vector3d force, double startTime, double endtime){
        if(norm_Two(force) <= 3*Math.pow(10, 7)){
            Vector3d impulse = force.mul(endtime).sub(force.mul(startTime)); 
            return impulse;
        }
        return new Vector3d(0,0,0);
    }

    public void newEngine(Vector3d newVelocity, double start, double end) {
        Vector3d impulse = newVelocity.sub(super.getVelocity());
        fuel += impulse.norm() * end-start;
        super.setNewVelocity(newVelocity);
    }

    /**
     * The velocity of the rocket with the impulse added to it
     * @param force the force decided to give the rocket
     * @param start the start time of the impulse
     * @param end the end time of the impulse
     */
    public void boostedVelo(Vector3d force, double start, double end){
        Vector3d currentVelo = super.getVelocity();
        consumeForce(force, (end-start));
        Vector3d impulse = getAnImpulse(force, start, end);
        consumeImpulse(impulse, (end-start));
        Vector3d finalVelo = currentVelo.add(impulse.mul(1/super.getMass()));
        super.setNewVelocity(finalVelo);
    }

    /**
     * Subtracts the fuel used during an impulse
     * @param imp the impulse
     * @param timeInterval the time the impulse took
     */
    public void consumeImpulse(Vector3d imp, double timeInterval){
        double foir = norm_Two(imp)*timeInterval;
        fuel = fuel - foir;
        //to consume fuel multiply with 1 m/s
        //our velocity is in km/s so what we could do is convert the final velo of boosted to 
        //m/s so then we can use it to consume 
    }

    /**
     * Subtracts the fuel used during a force
     * @param force the force
     * @param timeInterval the time the force took
     */
    public void consumeForce(Vector3d force, double timeInterval){
        double foir = norm_Two(force)*timeInterval;
        fuel = fuel - foir;
        //to consume fuel multiply with 1 m
        //we could use displacement
    }

    /**
     * Get the fuel of the rocket
     * @return the amount of fuel
     */
    public double getFuel() {
        return fuel;
    }

        private double time = 1;

    public Vector3d simulateEngine(Vector3d force, double start, double end){
        Vector3d currentVelo = super.getVelocity();
        Vector3d impulse = getAnImpulse(force, start, end);
        Vector3d finalVelo = currentVelo.add(impulse.mul(1/super.getMass()));
        return finalVelo;
    }

    public double gettime(){
        return time;
    }

    public void settime(double time){
        this.time = time;
    }

    public void resetTime(){
        time = 1;
    }
    
    public double consumeImpulseSimu(Vector3d imp, double timeInterval){
        double foir = norm_Two(imp)*timeInterval;
        return foir;
        //to consume fuel multiply with 1 m/s
        //our velocity is in km/s so what we could do is convert the final velo of boosted to 
        //m/s so then we can use it to consume 
    }

    /**
     * Subtracts the fuel used during a force
     * @param force the force
     * @param timeInterval the time the force took
     */
    public double consumeForceSimu(Vector3d force, double timeInterval){
        double foir = norm_Two(force)*timeInterval;
        return foir;
    }

    public double boostedVeloSimulation(Vector3d force, double start, double end){
        double x = 0;
        Vector3d currentVelo = super.getVelocity();
        Vector3d impulse = getAnImpulse(force, start, end);
        Vector3d finalVelo = currentVelo.add(impulse.mul(1/super.getMass()));
        super.setNewVelocity(finalVelo);
        return x;
    }  
}