package twentyone;

//package core;

public class Vector3d implements Vector3dInterface{

    private double x;
    private double y;
    private double z;

    public Vector3d(double x, double y, double z){ //constructor to intialize the x, y and z coordinates
        this.x=x;
        this.y=y;
        this.z=z;

    }

    public Vector3d(Vector2d v, double z){ //get the x and y coordinates
        this.x= v.getX();
        this.y= v.getY();
        this.z=z;
    }

    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
    public void setZ(double z){
        this.z = z;
    }

    public double getX(){ return x; }
    public double getY(){
        return y;
    }
    public double getZ(){
        return z;
    }
    public Vector2d getVector2d(){return new Vector2d(x,y);}

    public double dist(Vector3dInterface other){ // distance between 2 cords
        return Math.sqrt(Math.pow((other.getX()-x), 2)+Math.pow((other.getY()-y), 2)+ Math.pow(other.getZ()-z, 2));

    }

    public Vector3dInterface mul(double scalar){ //
        return new Vector3d(this.getX()*scalar,this.getY()*scalar,this.getZ()*scalar);
    }

    public Vector3dInterface add(Vector3dInterface other){
        return new Vector3d(this.x+other.getX(),this.y+other.getY(),this.z+other.getZ());
    }

    public Vector3dInterface sub(Vector3dInterface other){
        return new Vector3d(this.x-other.getX(),this.y-other.getY(),this.z-other.getZ());
    }

    public Vector3dInterface addMul(double scalar, Vector3dInterface other){
        Vector3dInterface addVector= other.mul(scalar);
        return this.add(addVector);

    }

    public double norm(){
        return   Math.sqrt(Math.pow(this.getX(),2)+Math.pow(this.getY(),2)+Math.pow(this.getZ(),2));
    }


    public Vector3d clone(){

        return new Vector3d(this.x,this.y,this.z);
    }

    public String toString(){
        return (x+", "+y+", "+z);

    }







}
