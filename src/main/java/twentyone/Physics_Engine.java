package twentyone;
//import java.util.Arrays;
class Physics_Engine{
    private final double g = 4;

    public double[] getForce(CelestialBody theIbody, CelestialBody theJbody){
        double[] position_Ibody = {theIbody.velocityX, theIbody.velocityY, theIbody.velocityZ};
        double[] position_Jbody = {theJbody.velocityX, theJbody.velocityY, theJbody.velocityZ};
        double[] returnForce = new double[3];
        double massI = theIbody.mass;
        double massJ = theJbody.mass;
        
        double multiplication = massI * massJ * g;
        returnForce = subtract(position_Ibody, position_Jbody);
        double[] bottom = copyArr(returnForce);
        returnForce = div(returnForce, bottom, multiplication);
        
        return returnForce;
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
            if(top[i] == 0){
                top[i] = 0.00001;
            }
            if(bottom[i] == 0){
                bottom[i] = 0.00001;
            }
            finalreturn[i] = mult*(top[i] / bottom[i]);
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
}