package twentyone;
import java.util.Arrays;


class mainPhysics{
    public static double[] sum(Physics_Engine unreal_engine, CelestialBody3[] theBodies, int planetIndex){
        double[] sumReturn = {0, 0, 0};
        for (int i = 0; i < theBodies.length; i++){
            if(planetIndex != i){
                sumReturn = sumvectors(sumReturn ,unreal_engine.getForce(theBodies[planetIndex], theBodies[i]));
            }
        }
        sumReturn = multiplyByNeg(sumReturn);
        return sumReturn;
    }
    public static double[] sumvectors(double[] vector1, double[] vector2){
        
        for (int i = 0; i < vector1.length; i++) {
            vector1[i] = vector1[i] + vector2[i];
        }
        return vector1;
    }
    public static double[] multiplyByNeg(double[] summedVector){
        for (int i = 0; i < summedVector.length; i++) {
            summedVector[i] = -1*summedVector[i];
        }
        return summedVector;
    }

    public static void main(String[] args) {
        double[] v = {1, 4, 5};
        double[] t = {1, 2, 3};
        double[] y = {3, 2, 4};
        double[] x = {8, 2, 6};
        double[] i = {5, 6, 1};
        double[] o = {4, 1, 2};
        CelestialBody3 sun = new CelestialBody3(v, v, 3);
        CelestialBody3 earth = new CelestialBody3(t, t, 2);
        CelestialBody3 venus = new CelestialBody3(y, y, 4);
        CelestialBody3 mars = new CelestialBody3(i, o, 4);
        Physics_Engine physics = new Physics_Engine();

        CelestialBody3[] bodies = new CelestialBody3[3];
        bodies[0] = sun;
        bodies[1] = earth;
        bodies[2] = venus;
        //bodies[3] = mars;
        System.out.println(Arrays.toString(physics.getForce(earth, sun)));
        System.out.println(Arrays.toString(physics.getForce(earth, venus)));
        System.out.println(Arrays.toString(physics.getForce(earth, mars)));
        System.out.println(Arrays.toString(sum(physics, bodies, 1)));
        
    }

}