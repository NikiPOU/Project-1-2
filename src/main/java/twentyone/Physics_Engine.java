package twentyone;



//import java.util.Arrays;
class Physics_Engine{
    private final double g = 6.6743 * Math.pow(10, -20);

    //here are the 
    public double[] getForce(CelestialBody3 theIbody, CelestialBody3 theJbody){
        double[] position_Ibody = theIbody.getPosition();
        double[] position_Jbody = theJbody.getPosition();
        double[] returnForce = new double[3];
        double massI = theIbody.getMass();
        double massJ = theJbody.getMass();
        
        double multiplication =  massJ * g;
        returnForce = subtract(position_Ibody, position_Jbody);
        double[] bottom = copyArr(returnForce);
        returnForce = div(returnForce, bottom, multiplication);
        
        return returnForce;
    }


    public CelestialBody3[] euler_solver(CelestialBody3[] objectWeLookAt, int planetIndex, double stepSize){
        double[] derivative_of_Velocity = sumUpdate(objectWeLookAt, planetIndex);
        double[] newVelocity = new double[3];
        double[] newPosition = new double[3];
        
    
        for (int i = 0; i < newPosition.length; i++) {
            newPosition[i] = objectWeLookAt[planetIndex].getPosition()[i] + stepSize * (objectWeLookAt[planetIndex].getVelocity()[i]);
        }

        for (int i = 0; i < newVelocity.length; i++) {
            newVelocity[i] = objectWeLookAt[planetIndex].getVelocity()[i] + stepSize * (derivative_of_Velocity[i]);
        }

        objectWeLookAt[planetIndex].setNewVelocity(newVelocity);
        objectWeLookAt[planetIndex].setNewPostion(newPosition);
        return objectWeLookAt;
    }

    public void accell(){
        double[] acel = {0,0,0};

        for (int i = 0; i < acel.length; i++) {
            
        }
    }

/*
    public CelestialBody3[] euler_definitive_edition(CelestialBody3[] bodies, int index, double stepSize){
        double[] derovatove = new double[3];
        double[] numa = new double[3];
        numa = sumUpdate(bodies, index);
        derovatove = multiply_vector_With_Number(numa, (1 / bodies[index].getMass()));
        double[] newpos = new double[3];
        double[] newvelo = new double[3];
        double[] xn = bodies[index].getPosition();
        double[] vn = bodies[index].getVelocity();
        double[] orang = multiply_vector_With_Number(vn, stepSize);
        for (int i = 0; i < numa.length; i++) {
            newpos = xn + ;
        }
        return null;
    }
 */   
    public double[] sumUpdate(CelestialBody3[] theBodies, int planetIndex){
        double[] sumReturn = {0, 0, 0};
        for (int i = 0; i < theBodies.length; i++){
            if(planetIndex != i){
                sumReturn = sumvectors(sumReturn , getForce(theBodies[planetIndex], theBodies[i]));
            }
        }
        sumReturn = multiply_vector_With_Number(sumReturn, -1); // multiply is sus
        theBodies[planetIndex].setNewForce(sumReturn);
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
        double result = 0;
        for(int i = 0; i < bottom.length; i++){
            result += bottom[i]*bottom[i];
        }
        result = Math.sqrt(result);
        for (int i = 0; i < finalreturn.length; i++) {
            if( result == 0){
                finalreturn[i] = 0;
            }else{
                finalreturn[i] = mult*(top[i] / result);    
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
        double[] reVector = new double[3];
        for (int i = 0; i < vector1.length; i++) {
            reVector[i] = vector1[i] + vector2[i];
        }
        return reVector;
    }

}