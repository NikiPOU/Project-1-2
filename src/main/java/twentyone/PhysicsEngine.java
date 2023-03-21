package twentyone;
public class PhysicsEngine
{
    double velocityX;
    double velocityY;
    double velocityZ;
    double acceleration; //don't have thrust, ..

    long positionX;
    long positionY;
    long positionZ;

    double intialvelocity;

    double radius;

    double diameter = radius*2;

    int x0 = 0;
    int y = 0;
    int z = 0;

    //double xi = 1;
    //double xj = 2;


    double F = 0;

    public static final double GC = 6.67430e-11; //gravitational constant


    //m1 and m2 are the two masses and the distance r is between them
    public double realForce(double m1, double m2, double xi, double xj)
    {
        double tophalf = xi - xj;



        double bottomhalf = (Math.sqrt(xi * xi)) - (Math.sqrt(xj * xj));

        return (tophalf/bottomhalf) * GC;
        
    }

    public double acceleration (double velocityX, double velocityY) //change in velocity/ change in time 
    {
        double velocityChange = velocityX - velocityY;

        //time

        //we already have based on the formula

        return 0;
    } 
}