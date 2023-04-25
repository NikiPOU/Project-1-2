package twentyone.Classes;

public class CelestialBody{
    
    private Vector3d velocity = new Vector3d(0.0, 0.0, 0.0);
    private Vector3d position = new Vector3d(0.0, 0.0, 0.0);
    private double mass;

    public CelestialBody(Vector3d velocity, Vector3d position, double mass){
        this.velocity = velocity;
        this.position = position;
        this.mass = mass;
    }

    public void setNewVelocity(Vector3d newVelocity){
        this.velocity = newVelocity;
    }

    public void setNewPostion(Vector3d newPosition) {
        this.position = newPosition;
    }

    public Vector3d getVelocity(){
        return velocity;
    }

    public Vector3d getPosition(){
        return position;
    }

    public double getMass(){
        return mass;
    }

    public String toString() {
        String pos = position.toString();
        String vel = velocity.toString();
        return "[Celestial body: position: " + pos + ", velocity: " + vel + ", mass: " + mass;
    }
    
}