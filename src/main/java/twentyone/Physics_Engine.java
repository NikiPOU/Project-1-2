package twentyone;

class Physics_Engine{
<<<<<<< HEAD
    private final double g = 6.6743 * Math.pow(10, -20);
=======
>>>>>>> a79e88a081d22e7c7388040e955cb0aa3c93bb3c

    private final double g = 6.6743E-20;

    /**
     * 
     * @param theIbody is the celestial body i.
     * @param theJbody is the celestial body j.
     * @return our the force on celestial body i by celestial body j, without -.
     */
    public double[] getForce(CelestialBody theIbody, CelestialBody theJbody){
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

    /**
     * Used in getForce() for subtraction.
     * @param position_Ibody
     * @param position_Jbody
     * @return celestial body i subtracted by celestial body j.
     */
    private double[] subtract(double[] position_Ibody, double[] position_Jbody){
        double[] finalreturn = new double[3];
        for(int i = 0; i < position_Ibody.length; i++){
            finalreturn[i] = position_Ibody[i] - position_Jbody[i];
        }
        return finalreturn;
    }

<<<<<<< HEAD
    public CelestialBody3[] euler_solver(CelestialBody3[] objectWeLookAt, int planetIndex, double stepSize){
        double[] derivative_of_Velocity = sumUpdate(objectWeLookAt, planetIndex);
=======
    /**
     * Used in getForce() for copying our top values (3D vector) to the bottom, used for division.
     * @param position_Ibody
     * @param position_Jbody
     * @return a copy of our array (3D vector).
     */
    private double[] copyArr(double[] tocopy){
        double[] returnArray = new double[3];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = tocopy[i];
        }
        return returnArray;       
    }

    /**
     * Used in getForce().
     * @param position_Ibody
     * @param position_Jbody
     * @return celestial body i divided by celestial body j.
     */
    private double[] div(double[] top, double[] bottom, double mult){
        double[] finalreturn = new double[3];
        double result = 0;

        for(int i = 0; i < bottom.length; i++){
            bottom[i] = Math.pow(Math.abs(bottom[i]), 3);
        }
        result = Math.sqrt(result);

        for (int i = 0; i < finalreturn.length; i++) {
            if(top[i] == 0  && bottom[i] == 0){
                finalreturn[i] = 0;
            }else{
                finalreturn[i] = mult*(top[i] / bottom[i]);    
            }        
        }
        return finalreturn; 
    }

    /**
     * 
     * @param theBodies is our CelestialBody[] Array containing our celestial bodies.
     * @param planetIndex represents the int index.
     * @return the net force, that is the sum forces of all the celestial bodies on our planet using recursion, including the method sumVectors(). At the end adds the negative value from our formula for force using multiply_vector_With_Number().
     */
    public double[] sum(CelestialBody[] theBodies, int planetIndex){
        double[] sumReturn = {0, 0, 0};
        for (int i = 0; i < theBodies.length; i++){
            if(planetIndex != i){
                sumReturn = sumvectors(sumReturn, getForce(theBodies[planetIndex], theBodies[i]));
            }
        }
        sumReturn = multiply_vector_With_Number(sumReturn, -1);
        return sumReturn;
    }

    /**
     * 
     * @param vector1 is our return vector from sumUpdate(). This is a double[] sumReturn, initially empty, but supposed to hold all the vector values as net force on celestial body i.
     * @param vector2 is our return vector from getForce(). This is a double[] returnForce containing the force on celestial body i by celestial body j, without -.
     * @return our vector1 with added force.
     */
    private double[] sumvectors(double[] vector1, double[] vector2){
        for (int i = 0; i < vector1.length; i++) {
            vector1[i] = vector1[i] + vector2[i];
        }
        return vector1;
    }

    /**
     * 
     * @param vector
     * @param number
     * @return force * (-1)
     */
    //WHY IS THERE A METHOD FOR JUST MAKING OUR VALUE NEGATIVE, LOL
    private double[] multiply_vector_With_Number(double[] vector, double number){
        for (int i = 0; i < vector.length; i++) {
            vector[i] = number * vector[i];
        }
        return vector;
    }

    public CelestialBody euler_solver(CelestialBody[] objectWeLookAt, int planetIndex, double stepSize){
        double[] derivative_of_Velocity = multiply_vector_With_Number(sum(objectWeLookAt, planetIndex), (1 / objectWeLookAt[planetIndex].getMass()));
>>>>>>> a79e88a081d22e7c7388040e955cb0aa3c93bb3c
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
        return objectWeLookAt[planetIndex];
    }
<<<<<<< HEAD

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
=======
}
>>>>>>> a79e88a081d22e7c7388040e955cb0aa3c93bb3c
