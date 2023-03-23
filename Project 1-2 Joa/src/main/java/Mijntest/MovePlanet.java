package Mijntest;

//35,492777846
//49,108746014

public class MovePlanet {
    static double fd;
    static double ld;
    static double midx = 0;
    static double midy = 0;
    public static double[] move(double x, double y, double z, double vx, double vy, double vz){
        x+=vx;
        y+=vy;
        z+=vz;
        System.out.println("x: " + x + "\ny: " + y + "\nz: " + z);
        double[] d = {x, y, z};
        return d;
    }

    public static void main(String[] args) {
        //move(7833268.439, 44885949.37, 2867693.201, -57.49674801, 11.52095127, 6.216953743);
        double x = 7833268.439;
        double y = 44885949.37;
        double z = 2867693.201;
        // for (int i = 0; i < 100; i++) {
        //     double[] d = move(x, y, z, -57.49674801, 11.52095127, 6.216953743);
        //     x = d[0];
        //     y = d[1];
        //     z = d[2];
        // }
        int xs = 5000;
        double tem = 0;
        for (int i = 0; i < xs; i++) {
            double[] d = circleMove(x, y, -57.49674801, 11.52095127);
            x = d[0];
            y = d[1];
            if(i == 0){
                fd = x - 7833268.439;
            } else if(i == xs-2){
                tem = x;
            } else if(i == xs-1){
                ld = x - tem;
            }
        }
        System.out.println("First difference: " + fd + "\nLast difference: " + ld);
    }

    public static double[] circleMove(double x, double y, double vx, double vy){
        double a = (y - midy)/(x - midx);
        double alpha = Math.sin(a);
        vx*=alpha;
        vy*=alpha;
        x+=vx;
        y+=vy;
        System.out.println("x: " + x);
        // System.out.println("y: " + y);
        // System.out.println("vx: " + vx);
        // System.out.println("vy: " + vy);
        double[] d = {x, y};
        return d;
    }
}
