package twentyone.Classes;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/*
 * Generate a testfile of a class by going inside the class and click right mouse button -> click source action ... -> click generate tests
 * Don't forget to set everything to public otherwise jacoco won't find them
 * run in terminal: 'mvn verify' to update the jacoco report
 */
public class Vector3dTest {

    @Test
    public void testAdd() {
        Vector3d a = new Vector3d(1, 2, 3);
        Vector2d a2 = new Vector2d(4, 5);
        Vector3d b = new Vector3d(a2, 6);

        Vector3d res = a.add(b);
        assertTrue(res.equals(new Vector3d(5, 7, 9)));
    }

    @Test
    public void testAddMul() {
        Vector3d a = new Vector3d(1, 2, 3);
        Vector2d a2 = new Vector2d(4, 5);
        Vector3d b = new Vector3d(a2, 6);

        Vector3d res = a.addMul(2, b);
        assertTrue(res.equals(new Vector3d(9, 12, 15)));
    }

    @Test
    public void testClone() {
        Vector3d a = new Vector3d(1, 2, 3);

        assertTrue(a.equals(a.clone()));
    }

    @Test
    public void testDist() {
        Vector3d a = new Vector3d(1, 2, 3);
        Vector2d a2 = new Vector2d(4, 5);
        Vector3d b = new Vector3d(a2, 6);

        double res = a.dist(b);
        assertTrue(Math.abs(res - 5.196152) < 0.0001);
    }

    @Test
    public void testGetVector2d() {
        Vector3d a = new Vector3d(1, 2, 3);

        Vector2d res = a.getVector2d();
        assertTrue(res.equals(new Vector2d(1,2), 0.0001));
    }

    @Test
    public void testGet() {
        Vector3d a = new Vector3d(1, 2, 3);

        double res1 = a.getX();
        double res2 = a.getY();
        double res3 = a.getZ();

        assertTrue(res1==1.0);
        assertTrue(res2==2.0);
        assertTrue(res3==3.0);
    }

    @Test
    public void testMul() {
        Vector3d a = new Vector3d(1, 2, 3);

        Vector3d res = a.mul(3);
        assertTrue(res.equals(new Vector3d(3, 6, 9)));
    }

    @Test
    public void testMulVector() {
        Vector3d a = new Vector3d(1, 2, 3);
        Vector2d a2 = new Vector2d(4, 5);
        Vector3d b = new Vector3d(a2, 6);

        Vector3d res = a.mulVector(b);
        assertTrue(res.equals(new Vector3d(4, 10, 18)));
    }

    @Test
    public void testNorm() {
        Vector3d a = new Vector3d(1, 2, 3);

        double res = a.norm();
        assertTrue(Math.abs(res - 3.741657) < 0.0001);
    }

    @Test
    public void testSet() {
        Vector3d a = new Vector3d(1, 2, 3);

        Vector3d res = a.clone();
        res.setX(7);
        res.setY(8);
        res.setZ(9);
        assertTrue(res.equals(new Vector3d(7, 8, 9)));
    }

    @Test
    public void testSub() {
        Vector3d a = new Vector3d(1, 2, 3);
        Vector2d a2 = new Vector2d(4, 5);
        Vector3d b = new Vector3d(a2, 6);

        Vector3d res = b.sub(a);
        assertTrue(res.equals(new Vector3d(3, 3, 3)));
    }

    @Test
    public void testToString() {
        Vector3d a = new Vector3d(1, 2, 3);

        String res = a.toString();
        assertTrue(res.equals("1.0, 2.0, 3.0"));
    }

    @Test
    public void testEquals() {
        Vector3d a = new Vector3d(1, 2, 3);

        assertTrue(a.equals(a));
        assertFalse(a.equals(null));
        assertFalse(a.equals(5));
        Vector3d res = a.clone();
        res.setX(5);
        assertFalse(a.equals(res));
        a.setX(5);
        res.setY(5);
        assertFalse(a.equals(res));
        a.setY(5);
        res.setZ(6);
        assertFalse(a.equals(res));
    }
}
