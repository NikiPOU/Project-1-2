package twentyone.Classes;
import java.util.ArrayList;

public class WindModel{
 
    /**
     * A lagrange formula with a random component and random gusts of wind
     * @param x distance between rocket and surface Titan
     * @param range variance of the stochastic component
     * @return windspeed in km/h
     */
    public static double generateWind(double x, double variance){
        double eden = generateLagrange(x);

        double min = 1-variance/2;
        double s = min + Math.random()*variance;
        eden = eden*s;
        eden = gustMultiplier(x, eden, s);
        return eden;
    }

    private static double generateLagrange(double x) {
        double[] xAra = {0, 13, 160};
        double[] yAra = {1.08, 122.4, 432};
        ArrayList<ArrayList<Double>> liX = new ArrayList<>();
        
        //generate li(x)
        for(int i = 0; i<xAra.length; i++){
            liX.add(generate_lix(xAra, yAra, i));
        }

        //get result of p(x)
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
        ArrayList<Double> lix = new ArrayList<Double>();
        lix.add(0.0);

        double denom = 1;
        for(int i = 0; i<xAra.length; i++){
            if(i != focusL){
                lix.add(xAra[i]);
                denom = denom*(xAra[focusL] - xAra[i]);
            }
        }
        lix.add(denom);
        return lix;
    }

    public static double gustMultiplier(double distance, double legrange, double howStrongIFeel){
        double i = 200;
        double j = 180;
        boolean arx = false;
        while(!(distance<=i && distance>=j) || arx == false){
            if(distance<=i && distance>=j){
                arx = true;
            }
            if(!arx){
                if(j != 20 && !(j<20)){
                    i = j;
                    j = j-20;
                }else if(j == 0.15625){
                    i = j;
                    j = 0;
                    arx = true;
                }else{
                    i = i/2;
                    j = j/2;
                }
            }
        }
        double[] gustDiscrepancy = new double[3];
        for(int s = 0; s<gustDiscrepancy.length; s++){

            gustDiscrepancy[s] =  (1 + ((((legrange)/((i-j))) * Math.random())/100)) + (j/1000.0);
        }
        int gustDecider = (int)(Math.random() * 6);
        switch (gustDecider) {
            case 1:
                legrange = legrange * gustDiscrepancy[gustDecider-1];
                break;
            case 2:
                legrange = legrange * gustDiscrepancy[gustDecider-1];
                break;
            case 3:
                legrange = legrange * gustDiscrepancy[gustDecider-1];
                break;
        }
        return legrange;
    }
}