package twentyone.Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Solver {
    //gravitational constant
    private final double G = 6.6743E-20;
    ArrayList<Vector3d> impulses = new ArrayList<>();

    public Solver() {
    }

    /**
     * Unreal_Engine method to calculate force.
     * @param iCelBody
     * @param jCelBody
     * @return
     */
    public Vector3d calculateForce(CelestialBody iCelBody, CelestialBody jCelBody){
        //get the vars needed
        double mass_of_Jobject = jCelBody.getMass();
        Vector3d finalForce = new Vector3d(0,0,0);
        double denominatorResult = 0;

        //multiply G with Mj
        double multiple = mass_of_Jobject * G; // no need to multiply by Mi

        //calculate difference between vectors
        finalForce = iCelBody.getPosition().sub(jCelBody.getPosition());


        //get denominator 
        Vector3d sqrt = finalForce.mulVector(finalForce);
        denominatorResult = denominatorResult + sqrt.getX() + sqrt.getY() + sqrt.getZ();

        denominatorResult = Math.pow(Math.sqrt(denominatorResult), 3);

        //get final number
        finalForce = finalForce.mul(multiple/denominatorResult);

        //return vector
        return finalForce;
    }

    /**
     * Unreal_Engine method to sum all forces (multiplied by -1 at the end).
     * @param theCelBodies
     * @param desiredPlanet
     * @return
     */
    public Vector3d sumOf_Forces(CelestialBody[] theCelBodies, int desiredPlanet){
        //create the variables
        Vector3d sumForces = new Vector3d(0, 0, 0);
        Vector3d vector = new Vector3d(0, 0, 0);

        //get the force between your desiredPlanet and ith planet and then add it to sumForces which is the total sum
        for(int i = 0; i<theCelBodies.length; i++){
            if(i != desiredPlanet){
                vector = calculateForce(theCelBodies[desiredPlanet], theCelBodies[i]);
                sumForces = sumForces.add(vector);
            }
        }

        //if there is a problem with the values multiply sumForces by -1 here
        //-1 with adams but not euler
        sumForces = sumForces.mul(-1);
        //return the total force
        return sumForces;  
    }

    public Vector3d getImpulse() {
        if (impulses.size() == 0) {
            try {
                File file = new File("src\\main\\resources\\twentyone\\data.txt");
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                  String str = sc.nextLine();
                  String[] split = str.split(" ");
                  Vector3d impulse = new Vector3d(Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]));
    
                  impulses.add(impulse);
                }
                sc.close();
              } catch (FileNotFoundException e) {
                System.out.println("Data file was removed");
                e.printStackTrace();
              }
        }

        return impulses.remove(0);
    }

}
