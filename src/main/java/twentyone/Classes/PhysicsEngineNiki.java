package twentyone.Classes;

class Physics_Engine{

    private Vector3d acceleration;

    private final double g = 6.6743E-20;


    public void acceleration(double[] totalVector, double mass){
  
        Vector3d a = new Vector3d(totalVector[0]/mass,totalVector[1]/mass,totalVector[2]/mass); // 

        acceleration = a;
    }

    public Vector3d getAcceleration(){
        return acceleration;
    }
    
    /**
     * Gravitational forces of all
     * @param celestialBodies
     * @param i
     */


    public Vector3d gravitationalForce(CelestialBodyNiki planet, CelestialBodyNiki[] celestialBodies) {
        double vectors[][] = new double[11][3];
        double[] totalVector = {0,0,0};
        Vector3d total3dVector;
        

        double[] positionVectorI = planet.getPositionArr();

        double[] positionVectorJ;

        double m1 = planet.getMass();
    
        for(int j = 0; j < 11; j++){
            for(int k = 0; k < 3; k++){
                if(!planet.equals(celestialBodies[j])){

                positionVectorJ = celestialBodies[j].getPositionArr();

                double xORyORzi = positionVectorI[k];
                double xORyORzj = positionVectorJ[k];

                double m2 = celestialBodies[j].getMass();
        
                double one = g * m1 * m2;
                double two = xORyORzi - xORyORzj;
                double three;
                if (xORyORzi != xORyORzj) {
                    three = Math.pow(Math.abs(xORyORzi - xORyORzj), 3);
                } else {
                    three = Double.POSITIVE_INFINITY;
                }
        
                double f = one * (two/three);

                vectors[j][k] = f;
                }
            }
        }
        for (int b = 0; b < 3; b++){
          for (int j = 0; j < 11; j++){
            totalVector[b] += vectors[j][b];

            if(j == 10){
                totalVector[b] = totalVector[b]*(-1);
            }
        }
      } 

      total3dVector = new Vector3d(totalVector[0],totalVector[1],totalVector[2]);

      acceleration(totalVector, m1);
      return total3dVector;
    }
}

// public Vector3d gravitationalForce(CelestialBody planet, CelestialBody[] celestialBodies) {
//     double vectors[][] = new double[11][3];
//     double[] totalVector = {0,0,0};
//     Vector3d total3dVector;
    

//     double[] positionVectorI = celestialBodies[i].getPositionArr();

//     double[] positionVectorJ;

//     double m1 = celestialBodies[i].getMass();

//     for(int j = 0; j < 11; j++){
//         for(int k = 0; k < 3; k++){
//             if(j != i){

//             positionVectorJ = celestialBodies[j].getPositionArr();

//             double xORyORzi = positionVectorI[k];
//             double xORyORzj = positionVectorJ[k];

//             double m2 = celestialBodies[j].getMass();
    
//             double one = g * m1 * m2;
//             double two = xORyORzi - xORyORzj;
//             double three;
//             if (xORyORzi != xORyORzj) {
//                 three = Math.pow(Math.abs(xORyORzi - xORyORzj), 3);
//             } else {
//                 three = Double.POSITIVE_INFINITY;
//             }
    
//             double f = one * (two/three);

//             vectors[j][k] = f;
//             }
//         }
//     }
//     for (int b = 0; b < 3; b++){
//       for (int j = 0; j < 11; j++){
//         totalVector[b] += vectors[j][b];

//         if(j == 10){
//             totalVector[b] = totalVector[b]*(-1);
//         }
//     }
//   } 

//   total3dVector = new Vector3d(totalVector[0],totalVector[1],totalVector[2]);

//   acceleration(totalVector, m1);
//   return total3dVector;
// }



