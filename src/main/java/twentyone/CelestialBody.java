package twentyone;

import java.util.ArrayList;

public class CelestialBody {

  public static Double bigG = 6.6743/1e11; //gravitational constant

  //ArrayList containing all our planet objects. Printed out by calling CelestialBody.printArrayList() in Main.
  static ArrayList<CelestialBody> planets = new ArrayList<CelestialBody>();
  //Array containing all our planet objects. Printed out by calling CelestialBody.printArray() in Main.
  static CelestialBody[] planetss = new CelestialBody[11];

  private double[] initialVelocity = new double[3];
  private double[] initialPosition = new double[3];
  private double[] currentForce = new double[3];

  private double mass;
  String name;

  double velocityX;
  double velocityY;
  double velocityZ;

  double acceleration; //

  long positionX;
  long positionY;
  long positionZ;

  double intialvelocity;

  double radius;

  double diameter = radius*2;
  

  CelestialBody(String name, double mass, long pX, long pY, long pZ, double vX, double vY, double vZ){ 

    this.name = name;
    this.mass = mass;
    this.positionX = pX;
    this.positionY = pY;
    this.positionZ = pZ;
    this.velocityX = vX;
    this.velocityY = vY;
    this.velocityZ = vZ;

    initialPosition = new double[] {pX, pY, pZ};
    initialVelocity = new double[] {vX, vY, vZ};

    planets.add(this);

    for(int i = 0; i <11; i ++){
      if(planetss[i] == null){
        planetss[i] = this;
        return;
      }
    }
  }

  public void setNewVelocity(double[] newVelocity){
    this.initialVelocity = newVelocity;
  }

  public void setNewPostion(double[] newPosition) {
    this.initialPosition = newPosition;
  }

  public void setNewForce(double[] newForce){
    this.currentForce = newForce;
  }

  public double[] getVelocity(){
    return initialVelocity;
  }
  
/**
 * @return the initial position of the celestial body in 3D vector form.
 */
  public double[] getPosition(){
    return initialPosition;
  }

  /**
 * @return the mass of the celestial body.
 */
  public double getMass(){
    return mass;
  }

  public double[] getForce(){
    return currentForce;
  }

  /**
 * @return an ArrayList containing all the celestial bodies.
 */
  public static ArrayList<CelestialBody> getArrayList(){
    return planets;
  }

  /**
 * @return an Array containing all the celestial bodies.
 */
  public static CelestialBody[] getArray(){
    return planetss;
  }

  /**
 * @return our celestial body object details as string.
 * Used by printArrayList() and printArray().
 */
  public String toString() {
    return name + ", mass: " + mass + ", initial position: [" + positionX + " " + positionY + " " + positionZ + "], initial velocity: [" + velocityX + " " + velocityY + " " + velocityZ + "]";
  }

  /**
   * For printing our objects in our ArrayList planets.
   */
  public static void printArrayList(){
    for (CelestialBody obj : planets) {
      System.out.println(obj.toString());
    }
  }

    /**
   * For printing our objects in our Array planetss.
   */
  public static void printArray(){
    for (CelestialBody obj : planetss) {
      System.out.println(obj.toString());
    }
  }
}
