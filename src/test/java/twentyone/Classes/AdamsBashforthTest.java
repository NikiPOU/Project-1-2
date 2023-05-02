package twentyone.Classes;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class AdamsBashforthTest {
    @Test
    public void testAdams() {
        Vector3d earthvel = new Vector3d(5.05251577575409, -29.3926687625899,  0.00170974277401292);
        Vector3d earthpos = new Vector3d((long) -148186906.893642, (long) -27823158.5715694, (long) 33746.8987977113);

        Vector3d moonvel = new Vector3d(4.34032634654904, -30.0480834180741, -0.0116103535014229);
        Vector3d moonpos = new Vector3d((long) -148458048.395164, (long) -27524868.1841142, (long) 70233.6499287411);

        CelestialBody earth = new CelestialBody(earthvel, earthpos, 5.97219E+24);
        CelestialBody moon = new CelestialBody(moonvel, moonpos, 7.3491E22);

        CelestialBody[] bodies = {earth, moon};

        AdamsBashforth a = new AdamsBashforth();

        bodies = a.adams(bodies, 1, 1);
        Vector3d res = bodies[1].getPosition();

        assertFalse(res.equals(moonpos));

        bodies = a.adams(bodies, 1, 1);

        assertFalse(bodies[1].getPosition().equals(res));
    }
}
