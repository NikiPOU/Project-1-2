package twentyone;

import java.util.Arrays;

class CelestialBody3{
    
    private double[] initialVelocity = new double[3];
    private double[] initialPosition = new double[3];
    private double mass;
    private double[] currentForce = new double[3];

    /**
     * Makes the Celestial Body
     * @param initialVelocity
     * @param initialPosition
     * @param mass
     */
    public CelestialBody3(double[] initialVelocity, double[] initialPosition, double mass){
        this.initialVelocity = initialVelocity;
        this.initialPosition = initialPosition;
        this.mass = mass;
    }

    /**
     * Sets the new velocity for the Celestial Body
     * @param newVelocity
     */
    public void setNewVelocity(double[] newVelocity){
        this.initialVelocity = newVelocity;
    }

    /**
     * Sets the new position for the Celestial Body
     * @param newPosition
     */
    public void setNewPostion(double[] newPosition) {
        this.initialPosition = newPosition;
    }

    /**
     * Sets the new force for the Celestial Body
     * @param newForce
     */
    public void setNewForce(double[] newForce){
        this.currentForce = newForce;
    }

    /**
     * It returns the current velocity of the Celestial Body.
     * @return an array of the current velocity
     */
    public double[] getVelocity(){
        return initialVelocity;
    }

    /**
     * It returns the current position of the Celestial Body.
     * @return an array of the current position
     */
    public double[] getPosition(){
        return initialPosition;
    }

    /**
     * It returns the mass of the Celestial Body.
     * @return the mass
     */
    public double getMass(){
        return mass;
    }

    /**
     * It returns the current force of the Celestial Body.
     * @return an array of the current force
     */
    public double[] getForce(){
        return currentForce;
    }

    /**
     * It returns the position, velocity and mass of the Celestial Body.
     * @return a string with the position, velocity and mass of the Celestial Body
     */
    public String toString() {
        String pos = Arrays.toString(initialPosition);
        String vel = Arrays.toString(initialVelocity);
        return "[Celestial body: position: " + pos + ", velocity: " + vel + ", mass: " + mass;
    }
    
}