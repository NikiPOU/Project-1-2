package twentyone.Classes;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RocketTest {
    @Test
    public void testBoostedVelo() {
        Rocket rocket = new Rocket(new Vector3d(1, 2, 3), new Vector3d(2, 2, 2));

        rocket.boostedVelo(new Vector3d(50000, 0, 0), 0, 1);

        assertTrue(rocket.getVelocity().equals(new Vector3d(2,2, 3)));
    }

    @Test
    public void testConsumeForce() {
        Rocket rocket = new Rocket(new Vector3d(1, 2, 3), new Vector3d(2, 2, 2));
        rocket.consumeForce(new Vector3d(50000, 0, 0), 2);
        assertTrue(rocket.getFuel()==100000000-Math.sqrt(50000)*2);
    }

    @Test
    public void testConsumeImpulse() {
        Rocket rocket = new Rocket(new Vector3d(1, 2, 3), new Vector3d(2, 2, 2));
        rocket.consumeImpulse(new Vector3d(50000, 0, 0), 2);
        assertTrue(rocket.getFuel()==100000000-Math.sqrt(50000)*2);
    }

    @Test
    public void testGetAnImpulse() {
        Rocket rocket = new Rocket(new Vector3d(1, 2, 3), new Vector3d(2, 2, 2));
        Vector3d impulse = rocket.getAnImpulse(new Vector3d(50000, 0, 0), 0, 2);

        assertTrue(impulse.equals(new Vector3d(100000, 0, 0)));
    }

    @Test
    public void testGetAnImpulseForceTooHigh() {
        Rocket rocket = new Rocket(new Vector3d(1, 2, 3), new Vector3d(2, 2, 2));
        Vector3d impulse = rocket.getAnImpulse(new Vector3d(3e7*3e7+5, 3e7+5, 3e7+5), 0, 2);

        assertTrue(impulse==null);
    }
}
