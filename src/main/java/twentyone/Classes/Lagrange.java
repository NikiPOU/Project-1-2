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


    public static void main(String[] args) {
        double[] xar = {2, 2.5, 4};
        double[] yar = {0.5, 0.4, 0.25};
        System.out.println(lagrange(xar, yar, 3));
    }
}



/* 
max 120m/s    height 120KM  https://www.esa.int/Science_Exploration/Space_Science/Cassini-Huygens/First_measurement_of_Titan_s_winds_from_Huygens#:~:text=Winds%20on%20Titan%20are%20found,altitude%20of%20about%20120%20km.

0km    0.3m/s

34 m/s max from ten clouds on middle and lower troposphere http://web.archive.org/web/20070717081303/http://www.astrobio.net/news/article1480.html
 */