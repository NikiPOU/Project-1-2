package twentyone;
public class EulerSolver {
    public static double stepSize;

    public void setStepSize(double h){ stepSize=h;}
    public double getStepSize(){return stepSize;}

    public Vector2d newPosition(Vector2d currentPos, Vector2d velocity){
        double x= currentPos.getX() + stepSize* velocity.getX();
        double y= currentPos.getY() + stepSize* velocity.getY();

        return new Vector2d(x,y);
    }
}

