package twentyone;

public class Main {

    public static void main(String[] args) {
        CelestialBody sun = new CelestialBody(1.991E30, 0, 0, 0, 0, 0, 0);
        CelestialBody mercury = new CelestialBody(3.302E+23, (long) 7833268.439, (long) 44885949.37, (long) 2867693.201, -57.49674801, 11.52095127, 6.216953743);
        CelestialBody venus = new CelestialBody(4.8685E+24, (long) -28216773.94, (long) 103994008.5, (long) 3012326.643, -34.02367371, -8.965212747, 1.840617353);
        CelestialBody earth = new CelestialBody(5.97219E+24, (long) -148186906.9, (long) -27823158.57, (long) 33746.8988, 5.052515776, -29.39266876,  0.001709743);
        CelestialBody moon = new CelestialBody(7.3491E22, (long) -148458048.4, (long) -27524868.18, (long) 70233.64993,4.340326347, -30.04808342, -0.011610354);
        CelestialBody mars = new CelestialBody(6.4171E+23, (long) -159116303.4, (long) 189235671.6, (long) 7870476.085, -17.69544692, -13.46352534, 0.152331928);
        CelestialBody jupiter = new CelestialBody(1.89819E+27, (long) 692722875.9, (long) 258560760.8, (long) -16570817.71, -4.714430599, 12.8555097, 0.052211813);
        CelestialBody saturn = new CelestialBody(5.6834E+26, (long) 1253801724, (long) -760453007.8, (long) -36697431.16, 4.467813413, 8.239895405,  -0.320745377);
        CelestialBody titan = new CelestialBody(1.34553E+23, (long) 1254501625, (long) -761340299.1, (long) -36309613.84, 8.995932295, 11.10857136, -2.251309862);
        CelestialBody neptune = new CelestialBody( 1.02409E+26, (long) 4454487339L, (long) -397895128.8, (long) -94464151.34, 0.447991657, 5.446106975, -0.122638125);
        CelestialBody uranus = new CelestialBody(0, (long) 1958732436, (long) 2191808553L, (long) -17235283.83, -5.127662163, 4.220553473, 0.082119034);
    }   
}

