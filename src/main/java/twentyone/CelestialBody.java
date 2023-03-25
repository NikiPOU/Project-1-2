package twentyone;

import java.util.Arrays;

public class CelestialBody{
    
    private double[] initialVelocity = new double[3];
    private double[] initialPosition = new double[3];
    private double mass;
    private double[] currentForce = new double[3];

    public CelestialBody(double[] initialVelocity, double[] initialPosition, double mass){
        this.initialVelocity = initialVelocity;
        this.initialPosition = initialPosition;
        this.mass = mass;
    }

    public void setNewVelocity(double[] newVelocity){
        this.initialVelocity = newVelocity;
    }

    public void setNewPostion(double[] newPosition) {
        this.initialPosition = newPosition;
    }

    public void setNewForce(double[] newForce){
        this.currentForce = newForce;
    }

    public double[] getVelocity(){
        return initialVelocity;
    }

    public double[] getPosition(){
        return initialPosition;
    }

    public double getMass(){
        return mass;
    }

    public double[] getForce(){
        return currentForce;
    }

    public String toString() {
        String pos = Arrays.toString(initialPosition);
        String vel = Arrays.toString(initialVelocity);
        return "[Celestial body: position: " + pos + ", velocity: " + vel + ", mass: " + mass;
    }
    
}