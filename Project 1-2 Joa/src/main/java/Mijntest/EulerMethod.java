package Mijntest;

public class EulerMethod {
    public static double calc(double h, double t, double y0, double x0){
        if(t == 0){
            return y0 + x0;
        } else {
            double yn = calc(h, t-1, y0, x0);
            double ans = yn + (1 + yn) * h;
            return ans;
        }
    }

    public static void main(String[] args) {
        System.out.println(calc(0.1, 1, 1, 0));
    }
}


