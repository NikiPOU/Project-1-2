package twentyone.Classes;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CelestialBodyTest {
    public CelestialBody earth = new CelestialBody(new Vector3d(5, -29,  0.001),
        new Vector3d((long) -148186906.893642, (long) -27823158.5715694, (long) 33746.8987977113), 5.97219E+24);

    @Test
    public void testGetForce() {
        CelestialBody earth = new CelestialBody(new Vector3d(5, -29,  0.001),
        new Vector3d((long) -148186906.893642, (long) -27823158.5715694, (long) 33746.8987977113), 5.97219E+24);
        
        Vector3d res = earth.getForce();
        assertTrue(res.equals(new Vector3d(0, 0, 0)));
    }

    @Test
    public void testGetMass() {
        double res = earth.getMass();
        assertTrue(Math.abs(res - 5.97219E+24) < 0.0001);
    }

    @Test
    public void testToString() {
        earth = new CelestialBody(new Vector3d(5.05251577575409, -29.3926687625899,  0.00170974277401292),
        new Vector3d((long) -148186906.893642, (long) -27823158.5715694, (long) 33746.8987977113), 5.97219E+24);
        String res = earth.toString();
        assertTrue(res.equals("[Celestial body: [position: -1.48186906E8, -2.7823158E7, 33746.0]," + 
            " [velocity: 5.05251577575409, -29.3926687625899, 0.00170974277401292], [mass: 5.97219E24]]"));
    }

    @Test
    public void testSetNewForce() {
        earth.setNewForce(new Vector3d(1,2,3));
        Vector3d res = earth.getForce();
        assertTrue(res.equals(new Vector3d(1,2,3)));
    }

    @Test
    public void testSetNewPostion() {
        earth.setNewPostion(new Vector3d(1,2,3));
        Vector3d res = earth.getPosition();
        assertTrue(res.equals(new Vector3d(1,2,3)));
    }

    @Test
    public void testSetNewVelocity() {
        earth.setNewVelocity(new Vector3d(1,2,3));
        Vector3d res = earth.getVelocity();
        assertTrue(res.equals(new Vector3d(1,2,3)));
    }
}
