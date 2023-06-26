package twentyone.Classes;

/**
 * Calculation of the {@code Celestial Bodies'} coordinates using an Euler solver.
 * This class extends the {@code Solver} Class.
 * @see twentyone.Classes.Solver
 */
public class Euler extends Solver{

    public CelestialBody[] Eulers(CelestialBody[] theCelBodies, int theDesired, double stepSizer){
        //get the values you need bro and set up

            //our V' by this I mean first derivative of velocity
        Vector3d derivativeOfVelo = sumOf_Forces(theCelBodies, theDesired); // no need to divide by Mi

            //our Vn and Xn
        Vector3d velocityOfDesiredPlanet = theCelBodies[theDesired].getVelocity();
        Vector3d positionOfDesiredPlanet = theCelBodies[theDesired].getPosition();
        
            //our Vn+1 and Xn+1
        Vector3d newVelocityOfDesiredPlanet = new Vector3d(0,0,0);
        Vector3d newPositionOfDesiredPlanet = new Vector3d(0,0,0);


        //get Xn+1 which is for position 
            //we have to do Xn+1 = Xn + h(Vn)
        newPositionOfDesiredPlanet = positionOfDesiredPlanet.add(velocityOfDesiredPlanet.mul(stepSizer));

        //get Vn+1 which is for velocity
            //we have to do Vn+1 = Vn + h(V'n)
        newVelocityOfDesiredPlanet = velocityOfDesiredPlanet.add(derivativeOfVelo.mul(stepSizer));

        //update the celestial body you are predicting
        theCelBodies[theDesired].setNewPostion(newPositionOfDesiredPlanet);
        theCelBodies[theDesired].setNewVelocity(newVelocityOfDesiredPlanet);
        
        return theCelBodies;
    }

        //Euler solver for landing
    public void landing(LandingModule ufo, double stepSize, double mainThrust, double miniThrust) {
        if (mainThrust > LandingModule.uMax) {
            //System.out.println("Main thrust too high");
            //mainThrust = UnidentifiedFlyingObject.uMax;
        }
        if (0.03 * miniThrust > 1) {
            System.out.println("Mini thrust is too high");
            miniThrust = 1/0.03;
        }

        ufo.fuel += mainThrust*stepSize;
        ufo.fuel += miniThrust* stepSize;
        
        Vector3d position = ufo.getPosition();
        Vector3d velocity = ufo.getVelocity();
    
        double newX = position.getX() + velocity.getX() * stepSize;
        double newY = position.getY() + velocity.getY() * stepSize;
    
        double newVelocityX = velocity.getX() + mainThrust * Math.sin(position.getZ()) * stepSize;
        double newVelocityY = velocity.getY() + (mainThrust * Math.cos(position.getZ()) - LandingModule.g) * stepSize;

        double newRotation = position.getZ() + velocity.getZ() * stepSize;
        
        double newRotationVelocity = velocity.getZ() + 0.03 * miniThrust * Math.sin(Math.PI / 2) * stepSize;
    
        Vector3d newPosition = new Vector3d(newX, newY, newRotation);
        Vector3d newVelocity = new Vector3d(newVelocityX, newVelocityY, newRotationVelocity);
    
        ufo.setPosition(newPosition);
        ufo.setVelocity(newVelocity);
    }

        
}

