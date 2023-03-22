package twentyone;
public class EulerSolver {
    public static double stepSize;

    public void setStepSize(double h){ stepSize=h;}
    public double getStepSize(){return stepSize;}

    public Vector3d newPosition(Vector3d currentPos, Vector3d velocity){
        double x= currentPos.getX() + stepSize* velocity.getX();
        double y= currentPos.getY() + stepSize* velocity.getY();
        double z= currentPos.getZ() + stepSize* velocity.getZ();

        return new Vector3d(x,y,z);
    }
}
