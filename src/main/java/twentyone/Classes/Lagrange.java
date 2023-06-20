package twentyone.Classes;
import java.util.ArrayList;

public class Lagrange{

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
        double rahal = 1;
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
        double s = (Math.random()*0.8) + 0.5;
        eden = eden * s;
        return eden;
    }



    public static double lagrangeRandomGusted(double[] xAra, double[] yAra, double x){
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
        double s = (Math.random()*0.8) + 0.5;
        eden = gustMultiplier(x, eden, s);
        return eden;
    }

    //reduce by 20 
    // when we get to 20 just half it

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
            //System.out.println("this is i : " + i + "  ....this is j : " + j);
        }
        double[] gustDiscrepancy = new double[3];
        for(int s = 0; s<gustDiscrepancy.length; s++){

            gustDiscrepancy[s] =  (1 + ((((legrange)/((i-j))) * Math.random())/100)) + (j/1000.0);
            //System.out.println(gustDiscrepancy[s]);
        }
        int gustDecider = (int)(Math.random() * 6);
        switch (gustDecider) {
            case 1:
                legrange = legrange * gustDiscrepancy[gustDecider-1];
                //System.out.println("GUST!");
                break;
            case 2:
                legrange = legrange * gustDiscrepancy[gustDecider-1];
                //System.out.println("GUST!");
                break;
            case 3:
                legrange = legrange * gustDiscrepancy[gustDecider-1];
                //System.out.println("GUST!");
                break;
        }
        return legrange;
    }

    public static double simulation(double[] xAra, double[] yAra, double amountOfSimulations, double distanceAwayFromSurface){
        // test only with one distance
        if(distanceAwayFromSurface>200 || distanceAwayFromSurface < 0){
            System.out.println("hit");
            return 0;
        }
    
        double i = 200;
        double j = 180;
        boolean arx = false;
        while(!(distanceAwayFromSurface<=i && distanceAwayFromSurface>=j) || arx == false){
            if(distanceAwayFromSurface<=i && distanceAwayFromSurface>=j){
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
            //System.out.println("this is i : " + i + "  ....this is j : " + j);
        }

        double goodSimulations = 0.0;
        int fail = 0;
        double jado = (i+j)/2.0;
        double averageBounder = lagrangeRandomGusted(xAra, yAra, jado);
        double gustDiscrepancy =  (1 + ((((averageBounder)/((i-j))) * Math.random())/100)) + (j/1000.0);
        for (int k = 0; k < amountOfSimulations; k++) {
            double simulatedInterGustWind = lagrangeRandomGusted(xAra, yAra, distanceAwayFromSurface);
            //System.out.println(simulatedInterGustWind + "gust of simulation" + (k+1));
            if(averageBounder >= simulatedInterGustWind){
                goodSimulations = goodSimulations + 1;
            }
            if(k%50 == 0){
                System.out.println("wind is : " + ((goodSimulations/k) * averageBounder) * gustDiscrepancy + " after simulations : " + k);
            }
        }
        //System.out.println(goodSimulations + " " + amountOfSimulations + " " + averageBounder);
        double windium = (goodSimulations/amountOfSimulations) * averageBounder * gustDiscrepancy;
        double average = goodSimulations/amountOfSimulations;
        System.out.println(average);
        System.out.println("for comparison here is our bounder : " + averageBounder);
        /*
        goodSimulations = 0;
        for (int s = 0; s < amountOfSimulations; s++) {
            double simulatedInterWind = lagrangeRandomGusted(xAra, yAra, distanceAwayFromSurface);
            //System.out.println(simulatedInterWind);
            if(distanceAwayFromSurface >= 100 && distanceAwayFromSurface <= 200){
                if(simulatedInterWind <= 432){
                    goodSimulations = goodSimulations + 1;
                    //System.out.println("hit 1");
                }
            }else if(distanceAwayFromSurface<100 && distanceAwayFromSurface >= 40){
                if(simulatedInterWind <= 230){
                    goodSimulations++;
                    //System.out.println("hit 2");
                }
            }else if(distanceAwayFromSurface < 40 && distanceAwayFromSurface >= 10){
                if(simulatedInterWind <=122.4){
                    goodSimulations++;
                    //System.out.println("hit 3");
                }
            }else if(distanceAwayFromSurface<10 && distanceAwayFromSurface>= 0){
                if(simulatedInterWind <= 1.08){
                    goodSimulations++;
                    //System.out.println("hit 4");
                }
            }
        }
        //System.out.println(goodSimulations + "amount of good simu");
        //System.out.println(amountOfSimulations + "amount simu");
        //System.out.println(goodSimulations/amountOfSimulations + " average");
        double average = goodSimulations/amountOfSimulations;
        */
        return windium;
    }

    public static void main(String[] args) {
        double[] xar = {0, 13, 160};
        double[] yar = {1.08, 122.4, 432};
        double vari = 0.25;
        //System.out.println(lagrange(xar, yar, vari) + " NORMAL this is in km/h" + vari);
        //System.out.println(lagrangeRandom(xar, yar, 5) + " this is in km/h");
        //System.out.println("good simu % : " + simulation(xar, yar, 1000, 1));
        //gustMultiplier(0.1, vari, 1);
        //System.out.println(1+(((500/20) * Math.random())/100));
        //   test number :    1    2  3   4  5   6   7   8   9    10   11   12   13   14
        
        double[] test_nums = {0, 0.5, 1, 5, 10, 13, 14, 20, 21, 100, 110, 130, 150, 170, 190};
        for (int i = 0; i < test_nums.length; i++) {
            //double vex = lagrangeRandomGusted(xar, yar, test_nums[i]);
            //double vex = lagrangeRandom(xar, yar, test_nums[i]);
            //System.out.println("test : " + (i+1) + "  . distance : " + test_nums[i] + "km");
            //gustMultiplier(test_nums[i], vex, 2);
            //System.out.println("from monte carlo attempt : "+ simulation(xar, yar, 100000.0, test_nums[i]));    
            //System.out.println("from just lagrangeGusted : " + vex);
            //System.out.println("end : " + "\n" + "\n");
        }
        //double c = 10.0/1000.0;
        //System.out.println(c);
        System.out.println("test : " + (13+1) + "  . distance : " + test_nums[13] + "km");
            //gustMultiplier(test_nums[i], vex, 2);
        System.out.println("from monte carlo attempt : "+ simulation(xar, yar, 10000.0, test_nums[12]));    
        System.out.println("end : " + "\n" + "\n");
    }
}
    /* 
    max 120m/s    height 120KM  https://www.esa.int/Science_Exploration/Space_Science/Cassini-Huygens/First_measurement_of_Titan_s_winds_from_Huygens#:~:text=Winds%20on%20Titan%20are%20found,altitude%20of%20about%20120%20km.

    0km    0.3m/s

    34 m/s max from ten clouds on middle and lower troposphere http://web.archive.org/web/20070717081303/http://www.astrobio.net/news/article1480.html
    */