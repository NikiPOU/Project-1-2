package twentyone;

import java.util.ArrayList;

public class CelestialBodyNiki {

  public static Double bigG = 6.6743/1e11; //gravitational constant

  //ArrayList containing all our planet objects. Printed out by calling CelestialBody.printArrayList() in Main.
  static ArrayList<CelestialBodyNiki> planets = new ArrayList<CelestialBodyNiki>();
  //Array containing all our planet objects. Printed out by calling CelestialBody.printArray() in Main.
  static CelestialBodyNiki[] planetss = new CelestialBodyNiki[11];

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

  Vector3d initialPosition;
  Vector3d initialVelocity;

  double[] initialPositionArr;
  double[] initialVelocityArr;
  

  CelestialBodyNiki(String name, double mass, long pX, long pY, long pZ, double vX, double vY, double vZ){ 

    this.name = name;
    this.mass = mass;
    this.positionX = pX;
    this.positionY = pY;
    this.positionZ = pZ;
    this.velocityX = vX;
    this.velocityY = vY;
    this.velocityZ = vZ;

    initialPositionArr = new double[] {pX, pY, pZ};
    initialVelocityArr = new double[] {vX, vY, vZ};

    initialPosition = new Vector3d(pX, pY, pZ);
    initialVelocity = new Vector3d(vX, vY, vZ);


    planets.add(this);

    for(int i = 0; i <11; i ++){
      if(planetss[i] == null){
        planetss[i] = this;
        return;
      }
    }
  }

  public void setNewVelocity(Vector3d newVelocity){
    this.initialVelocity = newVelocity;
  }

  public void setNewPostion(Vector3d newPosition) {
    this.initialPosition = newPosition;
  }

  public void setNewForce(double[] newForce){
    this.currentForce = newForce;
  }

  public Vector3d getVelocity(){
    return initialVelocity;
  }
  public double[] getVelocityArr(){
    return initialVelocityArr;
  }
  
/**
 * @return the initial position of the celestial body in 3D vector form.
 */
  public Vector3d getPosition(){
    return initialPosition;
  }
  
  public double[] getPositionArr(){
    return initialPositionArr;
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
  public static ArrayList<CelestialBodyNiki> getArrayList(){
    return planets;
  }

  /**
 * @return an Array containing all the celestial bodies.
 */
  public static CelestialBodyNiki[] getArray(){
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
    for (CelestialBodyNiki obj : planets) {
      System.out.println(obj.toString());
    }
  }

    /**
   * For printing our objects in our Array planetss.
   */
  public static void printArray(){
    for (CelestialBodyNiki obj : planetss) {
      System.out.println(obj.toString());
    }
  }
}

