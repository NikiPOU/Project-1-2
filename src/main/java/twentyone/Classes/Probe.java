package twentyone.Classes;
public class Probe extends CelestialBody{
     /*
     * Potential launching positions (x, y, z):
     *  north pole: -148186906.893642, -27823158.5715694, 33746.8987977113 + 6370
     *  south pole: -148186906.893642, -27823158.5715694, 33746.8987977113 - 6370
     *  pacific ocean: -148186906.893642 + 6370, -27823158.5715694, 33746.8987977113
     *  Africa: -148186906.893642 - 6370, -27823158.5715694, 33746.8987977113
     *  South America: -148186906.893642, -27823158.5715694 + 6370, 33746.8987977113
     *  Asia: -148186906.893642, -27823158.5715694 - 6370, 33746.8987977113
     * 
     * velocity with a 90 degrees angle from the earths surface
     */
    
    public Probe(double[] velocity, double[] position){
        super(velocity, position, 50000);
    }

    //this is just to make life ez. we will add the engine stuff later
}