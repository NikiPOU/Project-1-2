package twentyone;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        //g = 6.6743E-20;
        CelestialBody sun1 = new CelestialBody(1.991E30, 0, 0, 0, 0, 0, 0);
        CelestialBody mercury1 = new CelestialBody(3.302E+23, (long) 7833268.43923962, (long) 44885949.3703908, (long) 2867693.20054382, -57.4967480139828, 11.52095127176, 6.21695374334136);
        CelestialBody venus1 = new CelestialBody(4.8685E+24, (long) -28216773.9426889, (long) 103994008.541512, (long) 3012326.64296788, -34.0236737066136, -8.96521274688838, 1.84061735279188);
        CelestialBody earth1 = new CelestialBody(5.97219E+24, (long) -148186906.893642, (long) -27823158.5715694, (long) 33746.8987977113, 5.05251577575409, -29.3926687625899,  0.00170974277401292);
        CelestialBody moon1 = new CelestialBody(7.3491E22, (long) -148458048.395164, (long) -27524868.1841142, (long) 33746.8987977113, 4.34032634654904, -30.0480834180741, -0.0116103535014229);
        CelestialBody mars1 = new CelestialBody(6.4171E+23, (long) -159116303.422552, (long) 189235671.561057, (long) 7870476.08522969, -17.6954469224752, -13.4635253412947, 0.152331928200531);
        CelestialBody jupiter1 = new CelestialBody(1.89819E+27, (long) 692722875.928222, (long) 258560760.813524, (long) -16570817.7105996, -4.71443059866156, 12.8555096964427, 0.0522118126939208);
        CelestialBody saturn1 = new CelestialBody(5.6834E+26, (long) 1253801723.95465, (long) -760453007.810989, (long) -36697431.1565206, 4.46781341335014, 8.23989540475628,  -0.320745376969732);
        CelestialBody titan1 = new CelestialBody(1.34553E+23, (long) 1254501624.95946, (long) -761340299.067828, (long) -36309613.8378104 , 8.99593229549645, 11.1085713608453, -2.25130986174761);
        CelestialBody neptune1 = new CelestialBody( 1.02409E+26, (long) 4454487339.09447, (long) -397895128.763904, (long) -94464151.3421107, 0.447991656952326, 5.44610697514907, -0.122638125365954);
        CelestialBody uranus1 = new CelestialBody(86.813E+24, (long) 1958732435.99338, (long) 2191808553.21893, (long) -17235283.8321992, -5.12766216337626, 4.22055347264457, 0.0821190336403063);
    
        Probe skeldVent21 = new Probe(0, 0, 0, 0, 0, 0);

        CelestialBody3 sun = translateBodies.translate(sun1);
        CelestialBody3 mercury = translateBodies.translate(mercury1);
        CelestialBody3 venus = translateBodies.translate(venus1);
        CelestialBody3 earth = translateBodies.translate(earth1);
        CelestialBody3 moon = translateBodies.translate(moon1);
        CelestialBody3 mars = translateBodies.translate(mars1);
        CelestialBody3 jupiter = translateBodies.translate(jupiter1);
        CelestialBody3 saturn = translateBodies.translate(saturn1);
        CelestialBody3 titan = translateBodies.translate(titan1);
        CelestialBody3 neptune = translateBodies.translate(neptune1);
        CelestialBody3 uranus = translateBodies.translate(uranus1);

        Physics_Engine physics = new Physics_Engine();

        CelestialBody3[] bodies = {sun, mercury, venus, earth, moon, mars, jupiter, saturn, titan, neptune, uranus};

        for (int i = 0; i < 10; i++) {
            //for (int j = 0; j < 11; j++) {
                //System.out.println(physics.euler_solver(bodies, j, 0.1).toString()); 
            //}
            System.out.println(Arrays.toString(physics.euler_solver(bodies, 1, 1).getPosition()));
        }

    }   

    public static double[] getTotalForce(Physics_Engine unreal_engine, CelestialBody[] bs, int planet) {
        double[] sumReturn = {0, 0, 0};
        for (int i = 0; i < bs.length; i++){
            if(planet != i){
                //sumReturn = sumvectors(sumReturn ,unreal_engine.getForce(bs[planet], bs[i]));
            }
        }
        sumReturn = multiplyByNeg(sumReturn);
        return sumReturn;
    }

    public static double[] sumvectors(double[] vector1, double[] vector2){      
        for (int i = 0; i < vector1.length; i++) {
            vector1[i] = vector1[i] + vector2[i];
        }
        return vector1;
    }

    public static double[] multiplyByNeg(double[] summedVector){
        for (int i = 0; i < summedVector.length; i++) {
            summedVector[i] = -1*summedVector[i];
        }
        return summedVector;
    }
}


