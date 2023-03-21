package twentyone;
class CelestialBody3{
    
    private double[] initialVelocity = new double[3];
    private double[] initialPosition = new double[3];
    private double mass;

    public CelestialBody3(double[] initialVelocity, double[] initialPosition, double mass){
        this.initialVelocity = initialVelocity;
        this.initialPosition = initialPosition;
        this.mass = mass;
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
    
}