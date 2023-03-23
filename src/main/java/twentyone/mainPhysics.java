package twentyone;
import java.util.Arrays;


class mainPhysics{
    public static double[] sum(Physics_Engine unreal_engine, CelestialBody3[] theBodies, int planetIndex){
        double[] sumReturn = {0, 0, 0};
        for (int i = 0; i < theBodies.length; i++){
            if(planetIndex != i){
                sumReturn = sumvectors(sumReturn ,unreal_engine.getForce(theBodies[planetIndex], theBodies[i]));
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

    public static void main(String[] args) {
        double[] v = {0, 0, 0};
        double[] t = {-1, 2, -3};
        double[] y = {3, -2, 4};
        double[] x = {8, 2, 6};
        double[] i = {5, 6, 1};
        double[] o = {4, -6, 0};
        double[] p = {4, 7, -9};


        double[] merc = {(long) 7833268.43923962, (long) 44885949.3703908, (long) 2867693.20054382};
        double[] morc = {-57.4967480139828, 11.52095127176, 6.21695374334136};

        double[] venusvel = {-34.0236737066136, -8.96521274688838, 1.84061735279188};
        double[] venuspos = {(long) -28216773.9426889, (long) 103994008.541512, (long) 3012326.64296788};

        double[] sunvel = {0, 0, 0};
        double[] sunpos = {0, 0, 0};
        

        double[] earthvel = {5.05251577575409, -29.3926687625899,  0.00170974277401292};
        double[] earthpos = {(long) -148186906.893642, (long) -27823158.5715694, (long) 33746.8987977113};


        double[] moonvel = {4.34032634654904, -30.0480834180741, -0.0116103535014229};
        double[] moonpos = {(long) -148458048.395164, (long) -27524868.1841142, (long) 33746.8987977113};

        double[] marsvel = {-17.6954469224752, -13.4635253412947, 0.152331928200531};
        double[] marspos = {(long) -159116303.422552, (long) 189235671.561057, (long) 7870476.08522969};


        double[] jupivel = {-4.71443059866156, 12.8555096964427, 0.0522118126939208};
        double[] jupipos = {(long) 692722875.928222, (long) 258560760.813524, (long) -16570817.7105996};

        double[] satuvel = {4.46781341335014, 8.23989540475628,  -0.320745376969732};
        double[] satupos = {(long) 1253801723.95465, (long) -760453007.810989, (long) -36697431.1565206};

        double[] titvel = {8.99593229549645, 11.1085713608453, -2.25130986174761};
        double[] titpos = {(long) 1254501624.95946, (long) -761340299.067828, (long) -36309613.8378104};

        double[] nepvel = {0.447991656952326, 5.44610697514907, -0.122638125365954};
        double[] neppos = {(long) 4454487339.09447, (long) -397895128.763904, (long) -94464151.3421107};

        double[] uravel = {-5.12766216337626, 4.22055347264457, 0.0821190336403063};
        double[] urapos = {(long) 1958732435.99338, (long) 2191808553.21893, (long) -17235283.8321992};

        CelestialBody3 sun = new CelestialBody3(sunvel, sunpos, 1.991E30);
        CelestialBody3 earth = new CelestialBody3(earthvel, earthpos, 5.97219E+24);
        CelestialBody3 venus = new CelestialBody3(venusvel, venuspos, 4.8685E+24);
        CelestialBody3 mercury = new CelestialBody3(morc, merc, 3.302E+23);
        CelestialBody3 moon = new CelestialBody3(moonvel, moonpos, 7.3491E22);
        CelestialBody3 mars = new CelestialBody3(marsvel, marspos, 6.4171E+23);
        CelestialBody3 jupiter = new CelestialBody3(jupivel, jupipos, 1.89819E+27);
        CelestialBody3 saturn = new CelestialBody3(satuvel, satupos, 5.6834E+26);
        CelestialBody3 titan = new CelestialBody3(titvel, titpos, 1.34553E+23);
        CelestialBody3 neptune = new CelestialBody3(nepvel, neppos, 1.02409E+26);
        CelestialBody3 uranus = new CelestialBody3(uravel, urapos, 86.813E+24);
        
        Physics_Engine physics = new Physics_Engine();
        Unreal_Engine supereme = new Unreal_Engine();
        CelestialBody3[] bodies = new CelestialBody3[11];
        bodies[0] = sun;
        bodies[1] = earth;
        bodies[2] = venus;
        bodies[3] = mars;
        bodies[4] = uranus;
        bodies[5] = neptune;
        bodies[6] = mercury;
        bodies[7] = moon;
        bodies[8] = titan;
        bodies[9] = saturn;
        bodies[10] = jupiter;
        //System.out.println(Arrays.toString(physics.getForce(earth, sun)));
        //System.out.println(Arrays.toString(physics.getForce(earth, venus)));
        //System.out.println(Arrays.toString(physics.getForce(earth, mars)));
        System.out.println("old sum : " + Arrays.toString(sum(physics, bodies, 1)));
        System.out.println("new sum : " + Arrays.toString(physics.sumUpdate(bodies, 1)));

        System.out.println("new position : " + Arrays.toString(bodies[1].getPosition()));

        
        for (int j = 0; j < 31536000; j++) {
            bodies = supereme.Eulers(bodies, 1, 1);
            //System.out.println("new velocity : " + Arrays.toString(bodies[1].getVelocity()));
            if(j == 1 || j == (31536000))
                System.out.println("new position : " + Arrays.toString(bodies[1].getPosition()));
        }
        
            
            
    }

}