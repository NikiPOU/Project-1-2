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
        public void landing(UnidentifiedFlyingObject ufo, double stepSize, double mainThrust, double miniThrust) {
            
            Vector3d pos = ufo.getPosition(); 
            Vector3d vel = ufo.getVelocity();

            double x = pos.getX() + pos.getX()*stepSize;
            double y = pos.getY() + pos.getY()*stepSize;
            double o = pos.getZ() + ufo.getVelocity().getZ()*stepSize;

            Vector3d newPosition = new Vector3d(x,y,o); // x, y, θ

            double xV = vel.getX() + mainThrust * Math.sin(vel.getZ()) * stepSize;
            double yV = vel.getY() + mainThrust * (Math.cos(vel.getZ()) - UnidentifiedFlyingObject.g) * stepSize;
            double oV = vel.getZ() + 0.03 * miniThrust * Math.sin(Math.PI/2) * stepSize;

            Vector3d newVelocity = new Vector3d(xV,yV,oV); // x',y',θ'


            ufo.setPosition(newPosition);
            ufo.setVelocity(newVelocity);
        }
}

