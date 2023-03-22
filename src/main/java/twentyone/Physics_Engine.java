package twentyone;

import javafx.scene.control.Separator;

//import java.util.Arrays;
class Physics_Engine{
    private final double g = 1;

    //here are the 
    public double[] getForce(CelestialBody3 theIbody, CelestialBody3 theJbody){
        double[] position_Ibody = theIbody.getPosition();
        double[] position_Jbody = theJbody.getPosition();
        double[] returnForce = new double[3];
        double massI = theIbody.getMass();
        double massJ = theJbody.getMass();
        
        double multiplication = massI * massJ * g;
        returnForce = subtract(position_Ibody, position_Jbody);
        double[] bottom = copyArr(returnForce);
        returnForce = div(returnForce, bottom, multiplication);
        
        return returnForce;
    }


    public CelestialBody3 euler_solver(CelestialBody3 objectWeLookAt, double stepSize){
        double[] derivative_of_Velocity = multiply_vector_With_Number(objectWeLookAt.getForce(), (1 / objectWeLookAt.getMass()));
        double[] newVelocity = new double[3];
        double[] newPosition = new double[3];
        for (int i = 0; i < newVelocity.length; i++) {
            newVelocity[i] = newVelocity[i] + stepSize * (derivative_of_Velocity[i]);
        }
        
        for (int i = 0; i < newPosition.length; i++) {
            newPosition[i] = newPosition[i] + stepSize * (objectWeLookAt.getVelocity()[i]);
        }
        objectWeLookAt.setNewVelocity(newVelocity);
        objectWeLookAt.setNewPostion(newPosition);
        return objectWeLookAt;
    }

    
    public double[] sumUpdate(CelestialBody3[] theBodies, int planetIndex){
        double[] sumReturn = {0, 0, 0};
        for (int i = 0; i < theBodies.length; i++){
            if(planetIndex != i){
                sumReturn = sumvectors(sumReturn , getForce(theBodies[planetIndex], theBodies[i]));
            }
        }
        sumReturn = multiply_vector_With_Number(sumReturn, -1);
        return sumReturn;
    }



    private double[] multiply_vector_With_Number(double[] vector, double number){
        for (int i = 0; i < vector.length; i++) {
            vector[i] = number * vector[i];
        }
        return vector;
    }


    private double[] subtract(double[] position_Ibody, double[] position_Jbody){
        double[] finalreturn = new double[3];
        for(int i = 0; i < position_Ibody.length; i++){
            finalreturn[i] = position_Ibody[i] - position_Jbody[i];
        }
        return finalreturn;
    }

    private double[] div(double[] top, double[] bottom, double mult){
        double[] finalreturn = new double[3];
        for(int i = 0; i < bottom.length; i++){
            bottom[i] = Math.pow(Math.abs(bottom[i]), 3);
        }
        for (int i = 0; i < finalreturn.length; i++) {
            if(top[i] == 0&& bottom[i] == 0){
                finalreturn[i] = 0;
            }else{
                finalreturn[i] = mult*(top[i] / bottom[i]);    
            }        
        }
        return finalreturn; 
    }

    private double[] copyArr(double[] tocopy){
        double[] returnArray = new double[3];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = tocopy[i];
        }
        return returnArray;
        
    }



    private double[] sumvectors(double[] vector1, double[] vector2){
        for (int i = 0; i < vector1.length; i++) {
            vector1[i] = vector1[i] + vector2[i];
        }
        return vector1;
    }

}