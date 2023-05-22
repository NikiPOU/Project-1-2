package twentyone.Classes;

import twentyone.Interfaces.Vector3dInterface;

/**
 * The <code>Vector3d Class</code> is a 3D array of positions used to calculate the next positions and velocities.
 */
public class Vector3d implements Vector3dInterface{

    private double x;
    private double y;
    private double z;

    /**
     * Contructor to initialize the x, y and z coordinates.
     * @param x as a {@code double}
     * @param y as a {@code double}
     * @param z as a {@code double}
     */
    public Vector3d(double x, double y, double z){ //constructor to intialize the x, y and z coordinates
        this.x=x;
        this.y=y;
        this.z=z;
    }

    /**
     * Constructor to initialize the x, y and z coordinates. The x and y coordinates will be drawn from the <code>Vector2d Class</code> whereas the z value will be given.
     * @param v as a {@code Vector2d Class}
     * @param z as a {@code double}
     * @see twentyone.Classes.Vector2d#Vector2d(Vector2d)
     * @see twentyone.Classes.Vector2d#Vector2d(double, double)
     */
    public Vector3d(Vector2d v, double z){ //get the x and y coordinates
        this.x= v.getX();
        this.y= v.getY();
        this.z=z;
    }
    /**
     * Set the x value for the <code>Vector3d Class</code>.
     * @param x as a {@code double}
     */
    public void setX(double x){
        this.x = x;
    }
    /**
     * Set the y value for the <code>Vector3d Class</code>.
     * @param y as a {@code double}
     */
    public void setY(double y){
        this.y = y;
    }
    /**
     * Set the z value for the <code>Vector3d Class</code>.
     * @param z as a {@code double}
     */
    public void setZ(double z){
        this.z = z;
    }
    /**
     * Get the x value for the <code>Vector3d Class</code>.
     * @param x as a {@code double}
     */
    public double getX(){ 
        return x; 
    }
    /**
     * Get the y value for the <code>Vector3d Class</code>.
     * @param y as a {@code double}
     */
    public double getY(){
        return y;
    }
    /**
     * Get the z value for the <code>Vector3d Class</code>.
     * @param z as a {@code double}
     */
    public double getZ(){
        return z;
    }
    
    /**
     * Get a {@code Vector2d Class}. The {@code Vector2d Class} includes the x and y values of the <code>Vector3d Class</code>.
     * @return a new {@code Vector2d Class}
     * @see twentyone.Classes.Vector2d#Vector2d(Vector2d)
     * @see twentyone.Classes.Vector2d#Vector2d(double, double)
     */
    public Vector2d getVector2d(){
        return new Vector2d(x,y);
    }

    /**
     * Calculates the distance between this <code>Vector3d Class</code> and another given <code>Vector3d Class</code>.
     * @param otherVector3dClass as a {@code Vector3d Class}
     * @return the distance between the 2 <code>Vector3d Classes</code> as a {@code double}
     */
    public double dist(Vector3d otherVector3dClass){
        return Math.sqrt(Math.pow((otherVector3dClass.getX()-x), 2)+Math.pow((otherVector3dClass.getY()-y), 2)+ Math.pow(otherVector3dClass.getZ()-z, 2));
    }

    /**
     * Mulitply the <code>Vector3d Class</code> with a given scalar.
     * @param scalar as a {@code double}
     * @return the multiplied <code>Vector3d Class</code>
     */
    public Vector3d mul(double scalar){
        return new Vector3d(this.getX()*scalar,this.getY()*scalar,this.getZ()*scalar);
    }

    /**
     * Multiply the <code>Vector3d Class</code> with another <code>Vector3d Class</code>.
     * @param otherVector3dClass as a {@code Vector3d Class}
     * @return the multiplied <code>Vector3d Class</code>
     */
    public Vector3d mulVector(Vector3d otherVector3dClass){
        return new Vector3d(this.getX()*otherVector3dClass.getX(),this.getY()*otherVector3dClass.getY(),this.getZ()*otherVector3dClass.getZ());
    }

    /**
     * Add another <code>Vector3d Class</code> to this <code>Vector3d Class</code>.
     * @param otherVector3dClass as a {@code Vector3d Class}
     * @return a new <code>Vector3d Class</code> with added positions
     */
    public Vector3d add(Vector3d otherVector3dClass){
        return new Vector3d(this.x+otherVector3dClass.getX(),this.y+otherVector3dClass.getY(),this.z+otherVector3dClass.getZ());
    }

    /**
     * Substract another <code>Vector3d Class</code> from this <code>Vector3d Class</code>.
     * @param otherVector3dClass as a {@code Vector3d Class}
     * @return a new <code>Vector3d Class</code> with substracted positions
     */
    public Vector3d sub(Vector3d otherVector3dClass){
        return new Vector3d(this.x-otherVector3dClass.getX(),this.y-otherVector3dClass.getY(),this.z-otherVector3dClass.getZ());
    }

    /**
     * Add a multiplied <code>Vector3d Class</code>. The other, given, <code>Vector3d Class</code> will first be multiplied by the given scalar. It will then be added to this <code>Vector3d Class</code>.
     * @param scalar as a {@code double}
     * @param otherVector3dClass as a {@code Vector3d Class}
     * @return the <code>Vector3d Class</code> with the added values
     * @see #mul(double)
     * @see #add(Vector3d)
     */
    public Vector3d addMul(double scalar, Vector3d otherVector3dClass){
        Vector3d addVector= otherVector3dClass.mul(scalar);
        return this.add(addVector);
    }
    /**Makes it so that the length of the vector is equal to one
     * @return The normalized vector as a {@code double}
     */
    public double norm(){
        return Math.sqrt(Math.pow(this.getX(),2)+Math.pow(this.getY(),2)+Math.pow(this.getZ(),2));
    }

    /**
     * Make a clone of this <code>Vector3d Class</code>.
     * @return an exact copy of the <code>Vector3d Class</code> at this point in time
     */
    public Vector3d clone(){
        return new Vector3d(this.x,this.y,this.z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vector3d other = (Vector3d) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
            return false;
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
            return false;
        if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
            return false;
        return true;
    }

    public String toString(){
        return (x+", "+y+", "+z);
    }
}
