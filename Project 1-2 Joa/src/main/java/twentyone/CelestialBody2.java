package twentyone;

public class CelestialBody2 {

  public static Double bigG = 6.6743/1e11; //gravitational constant

  int planetSize;

  double mass; //

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

  public int x, y; // position on our 2D plane


  CelestialBody2(double mass, long positionX, long positionY, long positionZ, double d, double e, double f){ 

    this.mass = mass;
    this.positionX = positionX;
    this.positionY = positionY;
    this.positionZ = positionZ;
    this.velocityX = d;
    this.velocityY = e;
    this.velocityZ = f;
  }

  public void acceleration(){
    
  }


  public double gravitationalForce(double m1, double m2, double r) {
    

    double F = (bigG * m1 * m2) / r * r;

    return F;

  } 

  public void trajectory(double intialvelocity, double velocity, double mass, double position, double gravitationalForce) { // celestial bodies moving in orbit
    intialvelocity = 60; //at most 60km/s

    //use euler's method

    double stepSize = 0.1; //step size
    double x = 0;
    //double x = 0;
    double v = 60; //initial velocity
    
    int numSteps = 0;


    for (int i = 0; i<1; i++) {
      
    }


  }

  //to find the trajectory of the probe, we need them mass, the gravitational force acting on the probe, 
  //and the distance between the probe and the object it is orbiting.

  
}