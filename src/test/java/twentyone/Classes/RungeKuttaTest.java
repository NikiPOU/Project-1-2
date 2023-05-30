package twentyone.Classes;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RungeKuttaTest {
    private CelestialBody[] bodies = new CelestialBody[11];
    final double THRESHOLD = .0001;

    @Test
    public void testRungKutta() {
        initiateCB();
        RungeKutta r = new RungeKutta();

        bodies = r.rungKutta(bodies, 3, 1);
        Vector3d earth1sec = bodies[3].getPosition();

        Vector3d res = earth1sec.sub(new Vector3d(-1.481869018411237E+08, -2.782318796423767E+07, 3.374690050745569E+04));

        assertTrue(Math.abs(res.getX()) < THRESHOLD && Math.abs(res.getY()) < THRESHOLD && Math.abs(res.getZ()) < THRESHOLD, earth1sec.toString());

        bodies = r.rungKutta(bodies, 3, 1);
        Vector3d earth2sec = bodies[3].getPosition();

        res = earth2sec.sub(new Vector3d(-1.481868967885992E+08, -2.782321735690475E+07, 3.374690221720189E+04));

        assertTrue(Math.abs(res.getX()) < THRESHOLD && Math.abs(res.getY()) < THRESHOLD && Math.abs(res.getZ()) < THRESHOLD, earth2sec.toString());
    }

    public void initiateCB(){
        Vector3d sunvel = new Vector3d(0,0,0);
        Vector3d sunpos = new Vector3d(0,0,0);

        Vector3d merpos = new Vector3d( 7833268.43923962,  44885949.3703908,  2867693.20054382);
        Vector3d mervel = new Vector3d(-57.4967480139828, 11.52095127176, 6.21695374334136);

        Vector3d venusvel = new Vector3d(-34.0236737066136, -8.96521274688838, 1.84061735279188);
        Vector3d venuspos = new Vector3d( -28216773.9426889,  103994008.541512,  3012326.64296788);
        
        Vector3d earthvel = new Vector3d(5.05251577575409, -29.3926687625899,  0.00170974277401292);
        Vector3d earthpos = new Vector3d( -148186906.893642,  -27823158.5715694,  33746.8987977113);

        Vector3d moonvel = new Vector3d(4.34032634654904, -30.0480834180741, -0.0116103535014229);
        Vector3d moonpos = new Vector3d( -148458048.395164,  -27524868.1841142,  70233.6499287411);

        Vector3d marsvel = new Vector3d(-17.6954469224752, -13.4635253412947, 0.152331928200531);
        Vector3d marspos = new Vector3d( -159116303.422552,  189235671.561057,  7870476.08522969);

        Vector3d jupivel = new Vector3d(-4.71443059866156, 12.8555096964427, 0.0522118126939208);
        Vector3d jupipos = new Vector3d( 692722875.928222,  258560760.813524,  -16570817.7105996);

        Vector3d satuvel = new Vector3d(4.46781341335014, 8.23989540475628,  -0.320745376969732);
        Vector3d satupos = new Vector3d( 1253801723.95465,  -760453007.810989,  -36697431.1565206);

        Vector3d titvel = new Vector3d(8.99593229549645, 11.1085713608453, -2.25130986174761);
        Vector3d titpos = new Vector3d( 1254501624.95946,  -761340299.067828,  -36309613.8378104);

        Vector3d nepvel = new Vector3d(0.447991656952326, 5.44610697514907, -0.122638125365954);
        Vector3d neppos = new Vector3d( 4454487339.09447,  -397895128.763904,  -94464151.3421107);

        Vector3d uravel = new Vector3d(-5.12766216337626, 4.22055347264457, 0.0821190336403063);
        Vector3d urapos = new Vector3d( 1958732435.99338,  2191808553.21893,  -17235283.8321992);

        bodies[0] = new CelestialBody(sunvel, sunpos, 1.991E30);
        bodies[1] = new CelestialBody(mervel, merpos, 3.302E+23);
        bodies[2] = new CelestialBody(venusvel, venuspos, 4.8685E+24);
        bodies[3] = new CelestialBody(earthvel, earthpos, 5.97219E+24);
        bodies[4] = new CelestialBody(moonvel, moonpos, 7.3491E22);
        bodies[5] = new CelestialBody(marsvel, marspos, 6.4171E+23);
        bodies[6] = new CelestialBody(jupivel, jupipos, 1.89819E+27);
        bodies[7] = new CelestialBody(satuvel, satupos, 5.6834E+26);
        bodies[8] = new CelestialBody(titvel, titpos, 1.34553E+23);
        bodies[9] = new CelestialBody(nepvel, neppos, 1.02409E+26);
        bodies[10] = new CelestialBody(uravel, urapos, 86.813E+24);
    }
}
