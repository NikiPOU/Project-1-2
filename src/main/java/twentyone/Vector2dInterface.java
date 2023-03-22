package twentyone;

public interface Vector2dInterface {

    public void setX(double x);
    public void setY(double y);

    public double getX();
    public double getY();

    public Vector2dInterface add(Vector2dInterface other);
    public Vector2dInterface sub(Vector2dInterface other);
    public Vector2dInterface mul(double scalar);

    /**
     * Scalar x vector multiplication, followed by an addition
     *
     * @param scalar the double used in the multiplication step
     * @param other  the vector used in the multiplication step
     * @return the result of the multiplication step added to this vector,
     * for example:
     *
     *       Vector3d a = Vector();
     *       double h = 2;
     *       Vector3d b = Vector();
     *       ahb = a.addMul(h, b);
     *
     * ahb should now contain the result of this mathematical operation:
     *       a+h*b
     */
    public Vector2dInterface addMul(double scalar, Vector2dInterface other);

    /**
     * @return the Euclidean norm of a vector
     */
    public double norm();

    public double dist(Vector2dInterface other);

    /**
     * @return A string in this format:
     * Vector3d(-1.0, 2, -3.0) should print out (-1.0,2.0,-3.0)
     */
    public String toString();
}
