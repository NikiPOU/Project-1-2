package twentyone.Classes;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GradientDescent {
public static int Count = 0;

    public static double GradientDescentMethod() {

        double StartingPoint = 0;
        double LearningRate = 0.01;
        int NumberOfMaxIterations = 1000;

        for (int i = 0; i < NumberOfMaxIterations; i++) {
            double GradientValue = DerivativeOfFuction(StartingPoint);
            StartingPoint = StartingPoint - LearningRate * GradientValue;
        }

        return StartingPoint;
    }

    // Here is the fuction based on time for fuel consuption
    public static double DerivativeOfFuction(double Time) {
        // We don't have fuel useage fuction yet so I made up some random fuction for
        // fuel conspution = f(x) = x^4 + 3x^3 + 2x^2 + x + 1, f'(x) = 4x^3 + 9x^2 + 4x+ 1
        //  or the the coordinates x,y,z with eurosolver or something
        // Double Time= 32.40;
        // Double Fuction = Math.pow(Time, 4) + 3*Math.pow(Time,3)+ 2*Math.pow(Time, 2)+ Time +1;

        Double DerivativeOfFuctionAsDouble = 4 * Math.pow(Time, 3) + 9 * Math.pow(Time, 2) + 4 * Time + 1;
        return DerivativeOfFuctionAsDouble;
    }

    public static void DataWriter(double Value) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Data.txt", true))) {

             writer.write("-345635635.30 "); // write Coordinate X
             writer.newLine();
             writer.write("246246.1535"); // write Coordinate Y
             writer.newLine();
             writer.write("135135.15"); // write Coordinate Z
             writer.newLine();
             writer.write(String.valueOf(Value));
             writer.newLine(); 

      
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double DataReader() {

        double LowestValue = Double.MAX_VALUE;

        try (BufferedReader reader = new BufferedReader(new FileReader("Data.txt"))) {
            String Line;
           

                while ((Line = reader.readLine()) != null) {
                    Count=Count+1; 
                    
                    if (Count % 4  == 0) {
                    double Value = Double.parseDouble(Line);

                    if (Value < LowestValue) {
                        LowestValue = Value;        

                    }
               }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return LowestValue;
    }

    public static double MethodConnection() {

    if(GradientDescentMethod()<DataReader()){
    DataWriter(GradientDescentMethod());
    return GradientDescentMethod();
        }else{
        return DataReader();
}
    }

    public static void main(String[] args) {
System.out.println(MethodConnection());
    }
}