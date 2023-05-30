package twentyone.Classes;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SolverTest {
    CelestialBody earth = new CelestialBody(new Vector3d(1, 2, 3), new Vector3d(4, 5, 6), 5);
    CelestialBody moon = new CelestialBody(new Vector3d(3, 2, 1), new Vector3d(6, 5, 4), 1);
    CelestialBody sun = new CelestialBody(new Vector3d(7, 8, 9), new Vector3d(10, 11, 12), 20);

    CelestialBody[] bodies = {moon, earth, sun};

    Solver s = new Solver() {};

    @Test
    public void testCalculateForce() {
        Vector3d res = s.calculateForce(moon, earth);

        assertTrue(res.equals(new Vector3d(2.9496517435458584E-20, 0.0, -2.9496517435458584E-20)));
    }

    @Test
    public void testSumOf_Forces() {
        Vector3d res = s.sumOf_Forces(bodies, 0);

        assertTrue(res.equals(new Vector3d(-2.9496517435458584E-20, -0.0, 2.9496517435458584E-20)), res.toString());
    }
}
