package twentyone.Classes;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Vector2dTest {
    @Test
    public void testAdd() {
        Vector2d a = new Vector2d(1, 2);
        Vector2d b = new Vector2d(3, 4);

        Vector2d res = a.add(b);

        assertTrue(res.equals(new Vector2d(4.0, 6.0), 0.001));
    }

    @Test
    public void testAddMul() {
        Vector2d a = new Vector2d(1, 2);
        Vector2d b = new Vector2d(3, 4);

        Vector2d res = a.addMul(2, b);

        assertTrue(res.equals(new Vector2d(7.0, 10.0), 0.001));
    }

    @Test
    public void testClone() {
        Vector2d a = new Vector2d(1, 2);

        assertFalse(a.clone().equals(a));
    }

    @Test
    public void testDist() {
        Vector2d a = new Vector2d(1, 2);
        Vector2d b = new Vector2d(1, 4);

        double res = a.dist(b);

        assertTrue(res == 2.0);

    }

    @Test
    public void testEquals() {
        Vector2d a = new Vector2d(1, 2);
        Vector2d b = new Vector2d(3, 4);

        assertFalse(a.equals(b, 0.001));
    }

    @Test
    public void testMul() {
        Vector2d a = new Vector2d(1, 2);
        Vector2d res = a.mul(2);

        assertTrue(res.equals(new Vector2d(2.0, 4.0), 0.001));
    }

    @Test
    public void testNorm() {
        Vector2d a = new Vector2d(0, 2);
        double res = a.norm();

        assertTrue(res == 2.0, res + " ");
    }

    @Test
    public void testLength() {
        Vector2d a = new Vector2d(0, 2);
        a.setLength(5.0);

        assertTrue(a.getLength() == 5.0);
    }

    @Test
    public void testGettersAndSetters() {
        Vector2d a = new Vector2d(0, 2);
        a.setX(4);
        a.setY(5);

        assertTrue(a.getX() == 4.0);
        assertTrue(a.getY() == 5.0);
    }

    @Test
    public void testSub() {
        Vector2d a = new Vector2d(1, 2);
        Vector2d b = new Vector2d(3, 4);

        Vector2d res = b.sub(a);

        assertTrue(res.equals(new Vector2d(2, 2), 0.001));
    }

    @Test
    public void testToString() {
        Vector2d a = new Vector2d(1, 2);

        assertTrue(a.toString().equals("(1.0,2.0)"));
    }
}
