package Mijntest;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class InfoPlanets {
    //"Sun", "Mercury", "Venus", "Earth", "Moon", "Mars", "Jupiter", "Saturn", "Titan", "Neptune", "Uranus"
    static ArrayList<String> str = new ArrayList<String>();
    public static void getInfo(String planet){
        str.add("sun");
        str.add("mercury");
        str.add("venus");
        str.add("earth");
        str.add("moon");
        str.add("mars");
        str.add("jupiter");
        str.add("saturn");
        str.add("titan");
        str.add("neptune");
        str.add("uranus");
        Scanner scanner;
        ArrayList<String> lines = new ArrayList<String>();
        try {
            scanner = new Scanner(new File("src/main/resources/twentyone/IC.txt"));
            while(scanner.hasNextLine()){
                lines.add(scanner.nextLine());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        if(str.contains(planet)){
            int i = str.indexOf(planet);
            String[] s = lines.get(i).split(" ");
            System.out.println("Name: " + s[0] + "\nx0: " + s[1] + "\ny0: " + s[2] + "\nz0: " + s[3] + "\nvx1: " + s[4] + "\nvy1: " + s[5] + "\nvz1: " + s[6] + "\nm: " + s[7]);
        } else {
            System.out.println("Not a planet");
        }
       
    }
    public static void main(String[] args) {
        String planet = "saturn".toLowerCase();
        getInfo(planet);
    }
}
