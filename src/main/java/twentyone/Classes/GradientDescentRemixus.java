package twentyone.Classes;

import java.util.Arrays;

import javafx.scene.chart.PieChart.Data;

public class GradientDescentRemixus {
    
    public static CelestialBody[] allBodies = new CelestialBody[12];

    public static Vector3d gradDescentTraject(Vector3d currVelo, CelestialBody[] bods, double stepsizer, double endtime){

        Euler euler = new Euler();
        double simulatedFule = 0;
        int loopers = 50000;
        double LearingRate = 230;
        Vector3d currVeloClone = currVelo;
        CelestialBody[] saveSpaceState = new CelestialBody[12];
        saveSpaceState = copier(bods);
        double fuelconsu = 2000000000E111;
        Vector3d force = new Vector3d(0,0,0);
        Vector3d returnforce = new Vector3d(0,0,0);
        //System.out.println(bods[0].getPosition().toString());
        
        for (int i = 0; i < bods.length; i++) {
            saveSpaceState = euler.Eulers(saveSpaceState, i, stepsizer);    
        }
       
        double supposedBestdist = saveSpaceState[11].getPosition().dist(saveSpaceState[8].getPosition());

        saveSpaceState = copier(bods);
        

        double newsupposedBestdist = 0;
        for (int i = 0; i < loopers; i++) {
            
            currVeloClone = currVeloClone.sub(euler.sumOf_Forces(saveSpaceState, 11).mul(LearingRate));
            saveSpaceState[11].setNewVelocity(currVeloClone);
            for (int j = 0; j < bods.length; j++) {
                saveSpaceState = euler.Eulers(saveSpaceState, j, stepsizer);
            }
            newsupposedBestdist = saveSpaceState[11].getPosition().dist(saveSpaceState[8].getPosition()); 
            saveSpaceState = copier(bods);

       
            simulatedFule = 0;
            if(newsupposedBestdist < supposedBestdist){

                force = currVeloClone.sub(currVelo).mul(saveSpaceState[11].getMass());
                simulatedFule = simulatedFule + ((Rocket) saveSpaceState[11]).consumeImpulseSimu(force, endtime);
                force = force.mul(1/endtime);
        
                simulatedFule = simulatedFule + ((Rocket) saveSpaceState[11]).consumeForceSimu(force, endtime);
                if(fuelconsu > simulatedFule){
                    fuelconsu = simulatedFule;
                    returnforce = force;
                    System.out.println("change");
                }
            }
            //System.out.println(bods[0].getPosition().toString() + "    1");
            //simulatedFule = 0;
        }
        //System.out.println(bods[0].getPosition().toString());
        return returnforce;
    }


    public static CelestialBody[] copier(CelestialBody[] toCopy) {
        CelestialBody[] returnMe = new CelestialBody[12];
        for (int i = 0; i < returnMe.length; i++) {
            if(!(i == returnMe.length-1)){
                returnMe[i] = new CelestialBody(toCopy[i].getVelocity(), toCopy[i].getPosition(), toCopy[i].getMass());
            }else{
                returnMe[i] = new Rocket(toCopy[i].getVelocity(), toCopy[i].getPosition());
            }    
        }
        return returnMe;
    }


    public static Vector3d simulator(Vector3d force, Vector3d[] options, CelestialBody[] saveSpace, Euler lags, double stepsizer){
        CelestialBody[] saveSpaceState = new CelestialBody[12];
        saveSpaceState = copier(saveSpace);
        
        Vector3d returnforce = force;

        ((Rocket)saveSpaceState[11]).boostedVeloSimulation(force, 0, stepsizer);

        for (int i = 0; i < saveSpace.length; i++) {
            saveSpaceState = lags.Eulers(saveSpaceState, i, stepsizer);    
        }

        double supposedBestdist = saveSpaceState[11].getPosition().dist(saveSpaceState[8].getPosition());
        saveSpaceState = copier(saveSpace);
        double newsupposedBestdist = 0; 

        for (int i = 0; i < options.length; i++) {
            ((Rocket)saveSpaceState[11]).boostedVeloSimulation(options[i], 0, stepsizer);

            for (int j = 0; j < saveSpace.length; j++) {
                saveSpaceState = lags.Eulers(saveSpaceState, j, stepsizer);    
            }

            newsupposedBestdist = saveSpaceState[11].getPosition().dist(saveSpaceState[8].getPosition());
            saveSpaceState = copier(saveSpace);
          
            if(newsupposedBestdist<supposedBestdist){
                supposedBestdist = newsupposedBestdist;
                returnforce = options[i];
                System.out.println(i + " won");
            }

        }
        return returnforce;
    }


    public static void initiateCB(double x, double y, double z, double xVelo, double yVelo, double zVelo) {

        //(double x, double y, double z, double xVelo, double yVelo, double zVelo)  are for user to put and check

        Vector3d sunvel = new Vector3d(0, 0, 0);
        Vector3d sunpos = new Vector3d(0, 0, 0);

        Vector3d merpos = new Vector3d((long) 7833268.43923962, (long) 44885949.3703908, (long) 2867693.20054382);
        Vector3d mervel = new Vector3d(-57.4967480139828, 11.52095127176, 6.21695374334136);

        Vector3d venusvel = new Vector3d(-34.0236737066136, -8.96521274688838, 1.84061735279188);
        Vector3d venuspos = new Vector3d((long) -28216773.9426889, (long) 103994008.541512, (long) 3012326.64296788);

        Vector3d earthvel = new Vector3d(5.05251577575409, -29.3926687625899, 0.00170974277401292);
        Vector3d earthpos = new Vector3d((long) -148186906.893642, (long) -27823158.5715694, (long) 33746.8987977113);

        Vector3d moonvel = new Vector3d(4.34032634654904, -30.0480834180741, -0.0116103535014229);
        Vector3d moonpos = new Vector3d((long) -148458048.395164, (long) -27524868.1841142, (long) 70233.6499287411);

        Vector3d marsvel = new Vector3d(-17.6954469224752, -13.4635253412947, 0.152331928200531);
        Vector3d marspos = new Vector3d((long) -159116303.422552, (long) 189235671.561057, (long) 7870476.08522969);

        Vector3d jupivel = new Vector3d(-4.71443059866156, 12.8555096964427, 0.0522118126939208);
        Vector3d jupipos = new Vector3d((long) 692722875.928222, (long) 258560760.813524, (long) -16570817.7105996);

        Vector3d satuvel = new Vector3d(4.46781341335014, 8.23989540475628, -0.320745376969732);
        Vector3d satupos = new Vector3d((long) 1253801723.95465, (long) -760453007.810989, (long) -36697431.1565206);

        Vector3d titvel = new Vector3d(8.99593229549645, 11.1085713608453, -2.25130986174761);
        Vector3d titpos = new Vector3d((long) 1254501624.95946, (long) -761340299.067828, (long) -36309613.8378104);

        Vector3d nepvel = new Vector3d(0.447991656952326, 5.44610697514907, -0.122638125365954);
        Vector3d neppos = new Vector3d((long) 4454487339.09447, (long) -397895128.763904, (long) -94464151.3421107);

        Vector3d uravel = new Vector3d(-5.12766216337626, 4.22055347264457, 0.0821190336403063);
        Vector3d urapos = new Vector3d((long) 1958732435.99338, (long) 2191808553.21893, (long) -17235283.8321992);

        Vector3d probeposs = new Vector3d(x, y, z);
        Vector3d probevelo = new Vector3d(xVelo, yVelo, zVelo);

        allBodies[0] = new CelestialBody(sunvel, sunpos, 1.991E30);
        allBodies[1] = new CelestialBody(mervel, merpos, 3.302E+23);
        allBodies[2] = new CelestialBody(venusvel, venuspos, 4.8685E+24);
        allBodies[3] = new CelestialBody(earthvel, earthpos, 5.97219E+24);
        allBodies[4] = new CelestialBody(moonvel, moonpos, 7.3491E22);
        allBodies[5] = new CelestialBody(marsvel, marspos, 6.4171E+23);
        allBodies[6] = new CelestialBody(jupivel, jupipos, 1.89819E+27);
        allBodies[7] = new CelestialBody(satuvel, satupos, 5.6834E+26);
        allBodies[8] = new CelestialBody(titvel, titpos, 1.34553E+23);
        allBodies[9] = new CelestialBody(nepvel, neppos, 1.02409E+26);
        allBodies[10] = new CelestialBody(uravel, urapos, 86.813E+24);
        allBodies[11] = new Rocket(probevelo, probeposs);
    }


    public static void main(String[] args) {
        //simulate universe
        double xCoor = -1.48186916893642E8;
        double yCoor = -2.78168419715694E7;
        double zCoor = 33693.2987977113;

        double xVelo = 10000;
        double yVelo = -11.1085713608453;
        double zVelo = -2.25130986174761;
        //FileHandler axax = new FileHandler();
        int cont = 0;
        initiateCB(xCoor, yCoor, zCoor, xVelo, yVelo, zVelo);
        Euler euler = new Euler();
        Vector3d exos = new Vector3d(0, 0, 0);
        double newsupposedBestdist = allBodies[11].getPosition().dist(allBodies[8].getPosition());
        double newsupposedBestdist2 = allBodies[11].getPosition().dist(allBodies[8].getPosition());
        Vector3d[] exia = new Vector3d[3];
        while(newsupposedBestdist>=newsupposedBestdist2){
            //System.out.println(allBodies[0].getPosition().toString());
            exos = gradDescentTraject(allBodies[11].getVelocity(), allBodies, 100, 100);
            //System.out.println(allBodies[0].getPosition().toString());
            
            exia[0] = gradDescentTraject(new Vector3d(-xVelo, -yVelo, zVelo), allBodies, 100, 100);
            //System.out.println(allBodies[0].getPosition().toString());
            exia[1] = gradDescentTraject(new Vector3d(-xVelo, yVelo, zVelo), allBodies, 100, 100);
            //System.out.println(allBodies[0].getPosition().toString());
            exia[2] = gradDescentTraject(new Vector3d(xVelo, -yVelo, zVelo), allBodies, 100, 100);
            //System.out.println(allBodies[0].getPosition().toString());
            exos = simulator(exos, exia, allBodies, euler, 100);
            //axax.writeForces("Data.txt", exos.getX(), exos.getY(), exos.getZ());
            ((Rocket) allBodies[11]).boostedVelo(exos, 0, 100);

                for (int j = 0; j < allBodies.length; j++) {
                    allBodies = euler.Eulers(allBodies, j, 100);
                }          
            /*  
            cont++;
            if(cont == 1000){
                double newsupposedBestdist = allBodies[11].getPosition().dist(allBodies[8].getPosition());
                cont = 0;
                System.out.println(newsupposedBestdist);
            } */
            newsupposedBestdist = newsupposedBestdist2;
            newsupposedBestdist2 = allBodies[11].getPosition().dist(allBodies[8].getPosition());
            System.out.println(" ");
            System.out.println(newsupposedBestdist);
            
            System.out.println(" ");
            //System.out.println(((Rocket)allBodies[11]).getFuel());
        }
        // in the text file save the stepSize final distance to titan and fuel consumption
        //axax.writeResuts("Data.txt", 230, ((Rocket)allBodies[11]).getFuel(), newsupposedBestdist);


        //assumption for the rocket going far away from titan at a certain distance 
        //possibility one : most likely one based on trends seen from testing the probe is either "above" or "underneath"
        //titan so that means it's harder to get to titan with gradient descent because we are working with a "horizontal"
        //vector so what would be needed is a "vertical" vector to get there
        //proposal : have a set of vectors of velocity to test direction change when necessary
        //example : have a vector capable of forcing the ship in an "upwards" or "downwards" direction
    }
}
