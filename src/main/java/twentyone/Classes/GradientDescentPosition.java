package twentyone.Classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Finding the best launching coordinates
 * using coordinates and velocity
 */
public class GradientDescentPosition {

    public static int Count = 0;
    public static CelestialBody[] allBodies = new CelestialBody[12];

    public static void main(String[] args) {

        //example of coordinates

        double xCoor = -148186906.893642 + 6370;
        double yCoor = -27823158.5715694;
        double zCoor = 33746.8987977113;

        double xVelo = 8.99593229549645;
        double yVelo = 11.1085713608453;
        double zVelo = -2.25130986174761;

        System.out.println(Arrays.toString(GradientDescentMethod(xCoor, yCoor, zCoor, xVelo, yVelo, zVelo, allBodies)));

        //System.out.println(MethodConnection(xCoor, yCoor, zCoor, xVelo, yVelo, zVelo, allBodies));
        //double[] xar = {0, 13, 160};
        //double[] yar = {1.08, 122.4, 432};
        //System.out.println(lagrange(xar, yar, 100) + " this is in km/h");
    }

     /**
     * Gradient descent is for finding the best launching coordinates and velocity. It uses the 
     * following formula: {@code XCoor = xCoor -xVelo * LearingRate}.
     * @param xCoor x.
     * @param yCoor y.
     * @param zCoor z.
     * @param xVelo x1.
     * @param yVelo y2.
     * @param zVelo z3.     
     * @param allBodies as a {@code CelestialBody[]}.
     * @return a double array of x,y,z coordinates and x,y,z nad the distance to the Titan from rocket .
     */
    public static double[] GradientDescentMethod(double xCoor, double yCoor, double zCoor, double xVelo, double yVelo,
            double zVelo, CelestialBody[] allBodies) {

        Euler euler = new Euler();
                // jaime don't focus on removing unneccessary lines focus on fixing the assumptions
        initiateCB(xCoor, yCoor, zCoor, xVelo, yVelo, zVelo); 

        int LoopTimes = 100000; // does it truly affect it
        double LearingRate = 0.0001; //does step size affect what happens

                //Starting values

        //jaime's testers
        double finCordx = 0;
        double finCordy = 0;
        double finCordz = 0;
        double findistCord = 0;
        double findistCord2 = 0;
        int ticks = 0;

        //ends here

        double prevOutput = (allBodies[11].getPosition().dist(allBodies[8].getPosition()));
        double OutputVelo = (allBodies[11].getPosition().dist(allBodies[8].getPosition()));
        double OutputCoor = (allBodies[11].getPosition().dist(allBodies[8].getPosition()));

        double XOutput = xCoor;
        double YOutput = yCoor;
        double ZOutput = zCoor;

        double dfdx = xCoor;
        double dfdy = yVelo;
        double dfdz = zVelo;

        double dfdxOutput = dfdx;
        double dfdyOutput = dfdy;
        double dfdzOutput = dfdz;

                //Gradient Descent for coordinates 

        for (int i = 0; i < LoopTimes; i++) {

            initiateCB(xCoor, yCoor, zCoor, xVelo, yVelo, zVelo);

            for (int j = 0; j <= 11; j++) {

                // applying the gradient descent for coordinates

                euler.eulers(allBodies, j, 10);
                xCoor = xCoor - LearingRate * dfdx;
                yCoor = yCoor - LearingRate * dfdy;
                zCoor = zCoor - LearingRate * dfdz;

                //calculating the distance

                OutputCoor = (allBodies[11].getPosition().dist(allBodies[8].getPosition()));

                //checking if the new distance is smaller then the previous one

                if (prevOutput > OutputCoor) {

                    if (Math.sqrt(xCoor * xCoor + yCoor * yCoor + zCoor * zCoor) >= 148108199.283
                            && Math.sqrt(xCoor * xCoor + yCoor * yCoor + zCoor * zCoor) <= 151100284.117) {

                        // check if the coordinates are on the surface of earth with error of 1%

                        // Saving the lowest value
                        if(!(ticks > 100)){
                            findistCord = prevOutput;
                        }
                        
                        prevOutput = OutputCoor;
                        findistCord2 = OutputCoor;
                        finCordx = xCoor;
                        finCordy = yCoor;
                        finCordz = zCoor;

                        XOutput = xCoor;
                        YOutput = yCoor;
                        ZOutput = zCoor;

                    }
                }
            }
            ticks++;
        }

         //Gradient Descent for velocity 

        for (int y = 0; y < LoopTimes; y++) {

            initiateCB(xCoor, yCoor, zCoor, xVelo, yVelo, zVelo);

            for (int z = 0; z <= 11; z++) {

                euler.eulers(allBodies, z, 10);

                // Second derivative of coordinates and first derivative of velocity

                double dfdxdx = euler.sumOfForces(allBodies, 11).getX() / allBodies[11].getMass();
                double dfdydy = euler.sumOfForces(allBodies, 11).getY() / allBodies[11].getMass();
                double dfdzdz = euler.sumOfForces(allBodies, 11).getZ() / allBodies[11].getMass();

                // applying the gradient descent for velocity

                dfdx = dfdx - LearingRate * dfdxdx;
                dfdy = dfdy - LearingRate * dfdydy;
                dfdz = dfdz - LearingRate * dfdzdz;

                OutputVelo = (allBodies[11].getPosition().dist(allBodies[8].getPosition()));

                // Saving the lowest value

                if (prevOutput > OutputVelo) {

                    prevOutput = OutputVelo;
                    dfdxOutput = dfdx;
                    dfdyOutput = dfdy;
                    dfdzOutput = dfdz;

                }
            }
        }
        System.out.println("\n" + "from finals...  \n"  +  "x : " + finCordx + "\n" +
                                "y : " + finCordy + "\n" +  
                                 "z : " + finCordz + "\n" +
                                 "fin dist1 NOT ticks > 100 : " + findistCord + "\n" + 
                                 "fin dist2 : " + findistCord2 + "\n" +
                                  "fin prevOutput : " +prevOutput+"\n" + "\n");
        return new double[] { XOutput, YOutput, ZOutput, dfdxOutput, dfdyOutput, dfdzOutput, OutputCoor, OutputVelo }; // where 

    }

     /**
     * DataWriter saves the data in the data.txt file 
     * @param xCoor x.
     * @param yCoor y.
     * @param zCoor z.
     * @param xVelo x1.
     * @param yVelo y2.
     * @param zVelo z3.     
    * @param allBodies as a {@code CelestialBody[]}.
     */
    public static void DataWriter(double xCoor, double yCoor, double zCoor, double xVelo, double yVelo,
            double zVelo, CelestialBody[] allBodies) {

        double[] GradientDescentOutput = GradientDescentMethod(xCoor, yCoor, zCoor, xVelo, yVelo, zVelo, allBodies);

                //calculating the distance

        double XOutput = GradientDescentOutput[0];
        double YOutput = GradientDescentOutput[1];
        double zOutput = GradientDescentOutput[2];
        double xVeloOutput = GradientDescentOutput[3];
        double yVeloOutput = GradientDescentOutput[4];
        double zVeloOutput = GradientDescentOutput[5];
        double OutputOutput = GradientDescentOutput[6];

        // Writing the info when it is smalller then the ones recored 

        if (OutputOutput < DataReader()) {

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Data.txt",
                    true))) {

                writer.write(String.valueOf(XOutput)); // write Coordinate X
                writer.newLine();
                writer.write(String.valueOf(YOutput)); // write Coordinate Y
                writer.newLine();
                writer.write(String.valueOf(zOutput)); // write Coordinate Z
                writer.newLine();
                writer.write(String.valueOf(xVeloOutput)); // write Velo X
                writer.newLine();
                writer.write(String.valueOf(yVeloOutput)); // write Velo Y
                writer.newLine();
                writer.write(String.valueOf(zVeloOutput)); // write Velo Z
                writer.newLine();
                writer.write(String.valueOf(OutputOutput)); // write the distance
                writer.newLine();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


     /**
     * DataReader reads the data from the data.txt file and saves the the smallest distance from rocket to Titan
     * it reads every 7th line as the distance from rocket to Titan because only in every 7th line is the distance recorded
     * @return LowestValue lowestvalue.
     */

    public static double DataReader() {


        double LowestValue = Double.MAX_VALUE;

        try (BufferedReader reader = new BufferedReader(new FileReader("Data.txt"))) {
            String Line;

            while ((Line = reader.readLine()) != null) {
                Count = Count + 1;

                if (Count % 7 == 0) { 

                    // 7 because 1-6 are the coordinates and velocity and the 7th is the distance 

                    double Value = Double.parseDouble(Line);

                     // Reading and keeping the lowest value of the distance

                    if (Value < LowestValue) {

                        LowestValue = Value;

                    }
                }
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }

        return LowestValue;
    }

     /**
     * MethodConnection is for easier way to input data and get the the best output 
     * it merges the classes GradientDescent,DataReader,DataWriter
     * @param xCoor x.
     * @param yCoor y.
     * @param zCoor z.
     * @param xVelo x1.
     * @param yVelo y2.
     * @param zVelo z3.     
     * @param allBodies as a {@code CelestialBody[]}.
     * @return a distance to the Titan from rocket as double.
     */
    public static double MethodConnection(double xCoor, double yCoor, double zCoor, double xVelo, double yVelo,
            double zVelo, CelestialBody[] allBodies) {

                // Method which connect every method so it would work 

        double[] GradientDescentOutput = GradientDescentMethod(xCoor, yCoor, zCoor, xVelo, yVelo, zVelo, allBodies);

        double OutputOutput = GradientDescentOutput[6];

                // Checking if the values in Data.txt file are smaller then the outcome of gradient descent
                // if yes then it returns the gradient descent values and saves them in Data.txt file

        if (OutputOutput < DataReader()) {

            initiateCB(xCoor, yCoor, zCoor, xVelo, yVelo, zVelo);

            DataWriter(xCoor, yCoor, zCoor, xVelo, yVelo, zVelo, allBodies);

            return OutputOutput;

        } else {

            //if the gradient descent values are bigger, it returns the (smallest) values in Data.txt file

            return DataReader();
        }
    }

     /**
     * initiateCB is for user to put x,y,z coordinates and x,y,z velocity for the rocket 
     * @param xCoor x.
     * @param yCoor y.
     * @param zCoor z.
     * @param xVelo x1.
     * @param yVelo y2.
     * @param zVelo z3.
    * Initiates all the {@code Celestial Bodies}. It gives the correct {@code positions}, {@code velocities} and {@code masses}.
     * @see Vector3d
     */
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





    public static double lagrange(double[] xAra, double[] yAra, double x){
        ArrayList<ArrayList<Double>> liX = new ArrayList<>();
        
        //generate li(x)
        for(int i = 0; i<xAra.length; i++){
            liX.add(generate_lix(xAra, yAra, i));
        }
        

        //get reult of p(x)
        double apex = 1;
        double eden = 0;
        for(int i = 0; i<liX.size(); i++){
            for(int j = 0; j<liX.get(i).size(); j++){
                if(j == (liX.get(i).size()-1)){
                    apex = apex / liX.get(i).get(j);
                    apex = apex * yAra[i];
                    eden = eden + apex;
                    apex = 1;
                }else if(j != 0){
                    apex = apex * (x - liX.get(i).get(j));
                }
            }
        }
        return eden;
    }


    public static ArrayList<Double> generate_lix(double[] xAra, double[] yAra, int focusL){
        ArrayList<Double> returnMe = new ArrayList<Double>();
        returnMe.add(0.0);

        double denom = 1;
        //double rahal = 1;
        for(int i = 0; i<xAra.length; i++){
            if(i != focusL){
                returnMe.add(xAra[i]);
                denom = denom*(xAra[focusL] - xAra[i]);
            }
        }
        returnMe.add(denom);
        return returnMe;
    }



        public static double lagrangeRandom(double[] xAra, double[] yAra, double x){
        ArrayList<ArrayList<Double>> liX = new ArrayList<>();
        
        //generate li(x)
        for(int i = 0; i<xAra.length; i++){
            liX.add(generate_lix(xAra, yAra, i));
        }
        

        //get reult of p(x)
        double apex = 1;
        double eden = 0;
        for(int i = 0; i<liX.size(); i++){
            for(int j = 0; j<liX.get(i).size(); j++){
                if(j == (liX.get(i).size()-1)){
                    apex = apex / liX.get(i).get(j);
                    apex = apex * yAra[i];
                    eden = eden + apex;
                    apex = 1;
                }else if(j != 0){
                    apex = apex * (x - liX.get(i).get(j));
                }
            }
        }
        double s = (Math.random()) + 0.5;
        eden = eden * s;
        return eden;
    }



}
