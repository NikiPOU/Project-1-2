package twentyone.Classes;

/**
 * The <code>Vector2d Class</code> is a 2D array of double values.
 */
public class Vector2d {

    private double x;
    private double y;
    private double length;

    /**
     * Constructor to initialize the {@code x} and {@code y} values.
     * @param x as a {@code double}
     * @param y as a {@code double}
     */
    public Vector2d(double x, double y){
        this.x=x;
        this.y=y;
        length=this.norm();
    }

    /**
     * Sets the {@code x} value of the <code>Vector2d Class</code>.
     * @param x as a {@code double}
     */
    public void setX(double x){this.x=x;}
    /**
     * Sets the {@code y} value of the <code>Vector2d Class</code>.
     * @param y as a {@code double}
     */
    public void setY(double y){this.y=y;}
    /**
     * Gets the {@code x} value of the <code>Vector2d Class</code>.
     * @return the {@code x} value as a {@code double}
     */
    public double getX(){ return this.x;}
    /**
     * Gets the {@code y} value of the <code>Vector2d Class</code>.
     * @return the {@code y} value as a {@code double}
     */
    public double getY(){ return this.y;}
    /**
     * Gets the {@code length} of the <code>Vector2d Class</code>.
     * @return the {@code length} as a {@code double}
     */
    public double getLength(){ return this.length;}

    /**
     * Sets the {@code length} of the <code>Vector2d Class</code>.
     * @param length as a {@code double}
     */
    public void setLength(double length){
        this.length=length;
    }

    /**
     * Calculates the distance between this <code>Vector2d Class</code> and the given <code>Vector2d Class</code>.
     * @param targetVector2dClass as a {@code Vector2d Class}
     * @return the distance between this <code>Vector2d Class</code> and the given <code>Vector2d Class</code> as a double
     */
    public double dist(Vector2d targetVector2dClass){
        return Math.sqrt(Math.pow((x-targetVector2dClass.getX()), 2)+Math.pow((y-targetVector2dClass.getY()),2));
    }

    /**
     * Checks if this <code>Vector2d Class</code> and the given <code>Vector2d Class</code> are equal. They are considered equal if the following is true:<p>
     * <code> Math.sqrt(Math.pow((a.getX()-this.x), 2)+Math.pow((a.getY()-this.y), 2))<=tolerance </code>
     * @param otherVector2dClass as a {@code Vector2d Class}
     * @param tolerance as a {@code double}
     * @return {@code true} if considered equal
     * <li> {@code false} if considered not equal
     */
    public boolean equals(Vector2d otherVector2dClass, double tolerance){
        if(Math.sqrt(Math.pow((otherVector2dClass.getX()-this.x), 2)+Math.pow((otherVector2dClass.getY()-this.y), 2))<=tolerance){
            return true;
        }
        return false;
    }

    /**
     * Multiplies this <code>Vector2d Class</code> with the given scalar.
     * @param scalar as a {@code double}
     * @return the multiplied <code>Vector2d Class</code>
     */
    public Vector2d mul(double scalar){
        return new Vector2d(this.getX()*scalar,this.getY()*scalar);
    }

    /**
     * Adds another <code>Vector2d Class</code> to this <code>Vector2d Class</code>.
     * @param otherVector2dClass as a {@code Vector2d Class}
     * @return the added <code>Vector2d Class</code>
     */
    public Vector2d add(Vector2d otherVector2dClass){
        return new Vector2d(this.x+otherVector2dClass.getX(),this.y+otherVector2dClass.getY());
    }

    /**
     * Substracts the given <code>Vector2d Class</code> from this <code>Vector2d Class</code>.
     * @param otherVector2dClass
     * @return the substracted <code>Vector2d Class</code>
     */
    public Vector2d sub(Vector2d otherVector2dClass){
        return new Vector2d(this.x-otherVector2dClass.getX(),this.y-otherVector2dClass.getY());
    }

    //@returns this+scalar*other
    /**
     * Adds a multiplied <code>Vector2d Class</code> to this <code>Vector2d Class</code>.
     * @param scalar as a {@code double}
     * @param otherVector2dClass as a {@code Vector2d Class}
     * @return the <code>Vector2d Class</code> which has a multiplied <code>Vector2d Class</code> added to it
     * @see #mul(double)
     * @see #add(Vector2d)
     */
    public Vector2d addMul(double scalar, Vector2d otherVector2dClass){
        Vector2d addVector= otherVector2dClass.mul(scalar);
        return this.add(addVector);
    }

    //the distance from the origin to the vector //length of the vector // Pythagoras Theorem
    /**Makes it so that the length of the vector is equal to one
     * @return The normalized vector as a {@code double}
     */
    public double norm(){ return   Math.sqrt(Math.pow(this.getX(),2)+Math.pow(this.getY(),2)); }

    /**
     * Makes an exact copy of the <code>Vector2d Class</code> at this point in time.
     * @return the cloned <code>Vector2d Class</code>
     */
    public Vector2d clone(){
        return new Vector2d(this.x,this.y);
    }

    public String toString(){
        return "("+this.getX()+","+this.getY()+")";
    }
}
