package twentyone.Classes;

public class Vector2d {

    private double x;
    private double y;
    private double length;

    public Vector2d(double x, double y){
        this.x=x;
        this.y=y;
        length=this.norm();
    }

    public Vector2d(Vector2d otherVector){
        this.x=otherVector.getX();
        this.y=otherVector.getY();
        this.length=otherVector.getLength();
    }


    public void setX(double x){this.x=x;}

    public void setY(double y){this.y=y;}

    public double getX(){ return this.x;}

    public double getY(){ return this.y;}

    public double getLength(){ return this.length;}


    public void setLength(double length){
        this.length=length;
    }


    public double dist(Vector2d target){
        return Math.sqrt(Math.pow((x-target.getX()), 2)+Math.pow((y-target.getY()),2));
    }


    public boolean equals(Vector2d a, double tolerance){
        if(Math.sqrt(Math.pow((a.getX()-this.x), 2)+Math.pow((a.getY()-this.y), 2))<=tolerance){
            return true;
        }
        return false;
    }


    public boolean doesntEqual(Vector2d a, double tolerance) {
        if (Math.sqrt(Math.pow((x - a.getX()), 2) + Math.pow((y - a.getY()), 2)) > tolerance) {
            return true;
        }
        return false;
    }

    public Vector2d mul(double scalar){
        return new Vector2d(this.getX()*scalar,this.getY()*scalar);
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x+other.getX(),this.y+other.getY());
    }

    public Vector2d sub(Vector2d other){
        return new Vector2d(this.x-other.getX(),this.y-other.getY());
    }

    //@returns this+scalar*other
    public Vector2d addMul(double scalar, Vector2d other){
        Vector2d addVector= other.mul(scalar);
        return this.add(addVector);
    }

    //the distance from the origin to the vector //length of the vector // Pythagoras Theorem
    public double norm(){ return   Math.sqrt(Math.pow(this.getX(),2)+Math.pow(this.getY(),2)); }

    public Vector2d clone(){
        return new Vector2d(this.x,this.y);
    }

    public String toString(){
        return "("+this.getX()+","+this.getY()+")";
    }
}
