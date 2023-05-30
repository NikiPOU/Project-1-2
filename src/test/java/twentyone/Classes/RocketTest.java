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
        double res = 100000000-50000*2;
        assertTrue(rocket.getFuel()==res);
    }

    @Test
    public void testConsumeImpulse() {
        Rocket rocket = new Rocket(new Vector3d(1, 2, 3), new Vector3d(2, 2, 2));
        rocket.consumeImpulse(new Vector3d(50000, 0, 0), 2);
        assertTrue(rocket.getFuel()==100000000-50000*2);
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

        assertTrue(impulse.equals(new Vector3d(0,0, 0)));
    }

    @Test
    public void testTime() {
        Rocket r = new Rocket(new Vector3d(0,0, 0), new Vector3d(0,0, 0));
        r.settime(5);
        assertTrue(r.gettime() == 5.0);

        r.resetTime();
        assertTrue(r.gettime() == 1.0);
    }

    @Test
    public void testSimulateEngine() {
        Rocket rocket = new Rocket(new Vector3d(1, 2, 3), new Vector3d(2, 2, 2));

        rocket.simulateEngine(new Vector3d(50000, 0, 0), 0, 1);

        assertTrue(rocket.getVelocity().equals(new Vector3d(1,2, 3)));
    }
}
