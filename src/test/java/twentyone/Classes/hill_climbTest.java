package twentyone.Classes;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class hill_climbTest {
    @Test
    public void testCreate_RandneighboorVector() {
        List<Vector3d> l = hill_climb.create_RandneighboorVector(new Vector3d(1, 2, 3), 5);

        assertTrue(l.size()==5);
    }

    @Test
    public void testCreate_Randneighboortime() {
        List<Double> l = hill_climb.create_Randneighboortime(5);

        assertTrue(l.size()==5);
    }
}
