package Mijntest;

public class SinCos {
    public static void sincos(int i){
        System.out.println("Sin: " + Math.sin(i));
        System.out.println("Cos: " + Math.cos(i));
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            sincos(i);
        }
    }
}
