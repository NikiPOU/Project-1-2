package twentyone.Classes;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class hill_climb{
    public static Euler eu = new Euler();
    public static List<Vector3d> create_RandneighboorVector(Vector3d ourVelo, int quantity){
        ArrayList<Vector3d> neigsVect = new ArrayList<Vector3d>();
        Vector3d vex = ourVelo;
        for (int i = 0; i < quantity; i++){
            vex.setX(randomOperation());
            vex.setY(randomOperation());
            vex.setZ(randomOperation());
            neigsVect.add(vex);
            //System.out.println(vex.toString());
        }    
        return neigsVect;
    }

    public static List<Double> create_Randneighboortime(int quantity){
        ArrayList<Double> neigsTime = new ArrayList<Double>();
        Random rand = new Random();
        for (int i = 0; i < quantity; i++) {
            double a = (double)rand.nextInt(4);
            neigsTime.add(a);
        }
        return neigsTime;
    }

    private static double randomOperation() {
        Random rand = new Random();
        int a = rand.nextInt(2);
        double b =  100 * rand.nextDouble(); // i might be a problem
        if(a == 1){
            return b;
        }
        b= -b;
        return b;
    }

    public static CelestialBody[] initializeSpace(Vector3d initialVelProbe, Vector3d initialPosProbe){
        CelestialBody[] bodies = new CelestialBody[12];
        Vector3d sunvel = new Vector3d(0,0,0);
        Vector3d sunpos = new Vector3d(0,0,0);

        Vector3d merpos = new Vector3d((long) 7833268.43923962, (long) 44885949.3703908, (long) 2867693.20054382);
        Vector3d mervel = new Vector3d(-57.4967480139828, 11.52095127176, 6.21695374334136);

        Vector3d venusvel = new Vector3d(-34.0236737066136, -8.96521274688838, 1.84061735279188);
        Vector3d venuspos = new Vector3d((long) -28216773.9426889, (long) 103994008.541512, (long) 3012326.64296788);
        
        Vector3d earthvel = new Vector3d(5.05251577575409, -29.3926687625899,  0.00170974277401292);
        Vector3d earthpos = new Vector3d((long) -148186906.893642, (long) -27823158.5715694, (long) 33746.8987977113);

        Vector3d moonvel = new Vector3d(4.34032634654904, -30.0480834180741, -0.0116103535014229);
        Vector3d moonpos = new Vector3d((long) -148458048.395164, (long) -27524868.1841142, (long) 70233.6499287411);

        Vector3d marsvel = new Vector3d(-17.6954469224752, -13.4635253412947, 0.152331928200531);
        Vector3d marspos = new Vector3d((long) -159116303.422552, (long) 189235671.561057, (long) 7870476.08522969);

        Vector3d jupivel = new Vector3d(-4.71443059866156, 12.8555096964427, 0.0522118126939208);
        Vector3d jupipos = new Vector3d((long) 692722875.928222, (long) 258560760.813524, (long) -16570817.7105996);

        Vector3d satuvel = new Vector3d(4.46781341335014, 8.23989540475628,  -0.320745376969732);
        Vector3d satupos = new Vector3d((long) 1253801723.95465, (long) -760453007.810989, (long) -36697431.1565206);

        Vector3d titvel = new Vector3d(8.99593229549645, 11.1085713608453, -2.25130986174761);
        Vector3d titpos = new Vector3d((long) 1254501624.95946, (long) -761340299.067828, (long) -36309613.8378104);

        Vector3d nepvel = new Vector3d(0.447991656952326, 5.44610697514907, -0.122638125365954);
        Vector3d neppos = new Vector3d((long) 4454487339.09447, (long) -397895128.763904, (long) -94464151.3421107);

        Vector3d uravel = new Vector3d(-5.12766216337626, 4.22055347264457, 0.0821190336403063);
        Vector3d urapos = new Vector3d((long) 1958732435.99338, (long) 2191808553.21893, (long) -17235283.8321992);

        Vector3d probevel = initialVelProbe;
        Vector3d probepos = initialPosProbe;
 
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
        bodies[11] = new Rocket(probevel, probepos);
        return bodies;
    }


    public static Vector3d hllClimb(Vector3d ourforce, CelestialBody[] celo){
        boolean x = true;
        CelestialBody[] dupa = celo;
        ((Rocket) dupa[11]).simulateEngine(ourforce   ,1, 0);
        for(int i  = 0; i<=dupa.length;i++){
            dupa = eu.Eulers(dupa, i, 100);
        }
        double basedistance = dupa[11].getPosition().dist(dupa[8].getPosition());
        dupa = celo;
        double possibleDist = 0;
        int failureCount = 0;
        while(x){
            List<Vector3d> neighsVect = create_RandneighboorVector(ourforce, 11);
            for (int i = 0; i <=dupa.length; i++){
                Vector3d fo = neighsVect.get(i);
                ((Rocket) dupa[11]).simulateEngine(fo, 1, 0);
                for(int j = 0; j<=dupa.length;j++){
                    dupa = eu.Eulers(dupa, j, 10);
                }
                possibleDist = dupa[11].getPosition().dist(dupa[8].getPosition());
                if(possibleDist<basedistance){
                    basedistance = possibleDist;
                    ourforce = fo; 
                    
                    x = false;
                }
                dupa = celo;
            }
            failureCount = failureCount + 1;
            if(failureCount == 20){
                x = false;
            }
        }
        return ourforce;
    }





    public static CelestialBody hllClimb2(Vector3d ourforce, CelestialBody[] celo){
        
        //the setup
        boolean x = true;
        CelestialBody[] dupa = celo;
        CelestialBody finala = ((Rocket) celo[11]);
        ((Rocket) dupa[11]).simulateEngine(ourforce, 100, 0);
        int neighsSize = 10000;


        //the setup simulation with "ideal" best value for base distance
        for(int i  = 0; i<dupa.length;i++){
            dupa = eu.Eulers(dupa, i, 10);
            //System.out.println("xar");
        }
        //System.out.println("zen");
        double basedistance = dupa[11].getPosition().dist(dupa[8].getPosition());
        dupa = celo;

        double possibleDist = 0;
        //used to limit neighbour attempts
        int failureCount = 0;

        //the shitshow
        while(x){

            //generate neighbours
            List<Vector3d> neighsVect = create_RandneighboorVector(ourforce, neighsSize);
            List<Double> neighstime = create_Randneighboortime(neighsSize);

            //compare our neighbour to our ideal base distance
            for (int i = 0; i <neighsVect.size(); i++){
                Vector3d fo = neighsVect.get(i);
                double ax = neighstime.get(i);
                ((Rocket) dupa[11]).settime(ax);
                ((Rocket) dupa[11]).simulateEngine(fo, ((Rocket) dupa[11]).gettime(), 0);
                for(int j = 0; j<dupa.length;j++){
                    dupa = eu.Eulers(dupa, j, 10);
                }
                possibleDist = dupa[11].getPosition().dist(dupa[8].getPosition());
                if(possibleDist<basedistance){
                    basedistance = possibleDist;
                    finala.setNewForce(fo);
                    ((Rocket)finala).settime(ax);
                    x = false;
                }else{
                    //System.out.println("failuer : " + failureCount);
                }
                dupa = celo;
            }

            //if we get failures
            failureCount = failureCount + 1;
            if(failureCount == 1000){
                x = false;
                System.out.println("fialure");
                return finala;
            }
        }
        return finala;
    }

    public static void main(String[] args) {
        long spaceloops = Integer.MAX_VALUE;
        double k = 0;
        int crossgen = 0;
        double ans = 0;
        Vector3d pos = new Vector3d(-1.48186916893642E8, -2.78168419715694E7, 33693.2987977113);
        hill_climb a = new hill_climb();
        Vector3d initForce = new Vector3d(-90,11.5, 1.7);
        CelestialBody[] kk = a.initializeSpace(new Vector3d(100, 100, 100),pos);
        double basedistance = kk[11].getPosition().dist(kk[8].getPosition());
        double xistance = 0;
        while(true){    
            if(k>1000){
                kk[11] = a.hllClimb2(initForce, kk);
                ((Rocket) kk[11]).boostedVelo(initForce, ((Rocket) kk[11]).gettime(), 0);
                ans = k/365;
                k = 0;
            }
            for (int j = 0; j < kk.length; j++) {
                kk = a.eu.Eulers(kk, j, 100);
            }
            k = k + 10;
            xistance = kk[11].getPosition().dist(kk[8].getPosition());
            if(xistance<basedistance){
                basedistance = xistance;
                
                System.out.println(xistance);
            }else{
                System.out.println("cross :  " +xistance + "     ---   crosses;;" +  crossgen++);
            }
        }
    }


}