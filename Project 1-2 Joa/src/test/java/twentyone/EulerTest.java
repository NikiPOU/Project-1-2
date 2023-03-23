package twentyone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EulerTest {
    
    @Test  
    public void testAdd() {  
          int res = Euler.add(5, 6);
          assertEquals(res, 11);  
    } 
}
