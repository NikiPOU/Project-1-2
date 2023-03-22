package twentyone;

public class Probe {
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
