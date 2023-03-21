package twentyone;

public class Probe {

    double mass = 50000;

    long positionX;
    long positionY;
    long positionZ;

    double velocityX;
    double velocityY;
    double velocityZ;
    

    Probe (long positionX, long positionY, long positionZ, double d, double e, double f) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.positionZ = positionZ;
        this.velocityX = d;
        this.velocityY = e;
        this.velocityZ = f; 
    }

    public long[] getPosition() {
        long[] position = {positionX, positionY, positionZ};
        return position;
    }

    public double[] getVelocity() {
        double[] velocity = {velocityX, velocityY, velocityZ};
        return velocity;
    }



}
