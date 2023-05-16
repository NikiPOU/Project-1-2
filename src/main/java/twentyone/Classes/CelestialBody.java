package twentyone.Classes;

/**
 * The {@code CelestialBody Class} is used for the celestial bodies.
 */
public class CelestialBody{
    
    private Vector3d velocity = new Vector3d(0.0, 0.0, 0.0);
    private Vector3d position = new Vector3d(0.0, 0.0, 0.0);
    private double mass;
    private Vector3d currentForce = new Vector3d(0,0,0);

    /**
     * Constructor for the {@code CelestialBody Class} where 
     * {@code velocity} is the velocity of the celestial body, 
     * {@code position} is the position of the celestial body and
     * {@code mass} is the mass of the celestial body.
     * @param velocity as a {@code Vector3d Class}
     * @param position as a {@code Vector3d Class}
     * @param mass as a {@code double}
     */
    public CelestialBody(Vector3d velocity, Vector3d position, double mass){
        this.velocity = velocity;
        this.position = position;
        this.mass = mass;
    }

    /**
     * Sets a new {@code velocity} for the {@code CelestialBody Class}.
     * @param newVelocity as a {@code Vector3d Class}
     */
    public void setNewVelocity(Vector3d newVelocity){
        this.velocity = newVelocity;
    }

    /**
     * Sets a new {@code position} for the {@code CelestialBody Class}.
     * @param newPosition as a {@code Vector3d Class}
     */
    public void setNewPostion(Vector3d newPosition) {
        this.position = newPosition;
    }

    /**
     * Sets a new {@code Force} for the {@code CelestialBody Class}.
     * @param newForce as a {@code Vector3d Class}
     */
    public void setNewForce(Vector3d newForce){
        this.currentForce = newForce;
    }

    /**
     * Gets the {@code velocity} of the {@code CelestialBody Class}.
     * @return the velocity as a {@code Vector3d Class}
     */
    public Vector3d getVelocity(){
        return velocity;
    }

    /**
     * Gets the {@code position} of the {@code CelestialBody Class}.
     * @return the position as a {@code Vector3d Class}
     */
    public Vector3d getPosition(){
        return position;
    }

    /**
     * Gets the {@code mass} of the {@code CelestialBody Class}.
     * @return the mass as a {@code double}
     */
    public double getMass(){
        return mass;
    }

    /**
     * Gets the {@code force} of the {@code CelestialBody Class}.
     * @return the force as a {@code Vector3d Class}
     */
    public Vector3d getForce(){
        return currentForce;
    }

    /**
     * Returns a string of the {@code CelestialBody}.
     * @return a string in the following format:<p>
     * [Celestial body: [position: ], [velocity: ], [mass: ]]
     */
    public String toString() {
        String pos = position.toString();
        String vel = velocity.toString();
        return "[Celestial body: [position: " + pos + "], [velocity: " + vel + "], [mass: " + mass + "]]";
    }
}