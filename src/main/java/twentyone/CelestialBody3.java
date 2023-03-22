package twentyone;
class CelestialBody3{
    
    private double[] initialVelocity = new double[3];
    private double[] initialPosition = new double[3];
    private double mass;
    private double[] currentForce = new double[3];

    public CelestialBody3(double[] initialVelocity, double[] initialPosition, double mass){
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
    
}