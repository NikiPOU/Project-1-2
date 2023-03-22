package twentyone;

public class translateBodies {

    public static CelestialBody3 translate(CelestialBody b) {
        double[] position = {b.positionX, b.positionY, b.positionZ};
        double[] velocity = {b.velocityX, b.velocityY, b.velocityZ};

        return new CelestialBody3(velocity, position, b.mass);
    }

    
}
