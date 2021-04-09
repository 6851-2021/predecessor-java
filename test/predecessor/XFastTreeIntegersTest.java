package predecessor;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class XFastTreeIntegersTest {
    
    @Test
    public void simplePredCheck() {
        Predecessor<Integer> check = new XFastTreeIntegers(5);
        check.insert(0);
        check.insert(5);
        check.insert(10);
        check.insert(15);
        check.insert(20);
        
        System.out.println(((XFastTreeIntegers)check).displayMap());
        
        assertEquals(0, check.predcessor(0), "0 failed");
        assertEquals(0, check.predcessor(1), "1 failed");
        assertEquals(0, check.predcessor(2), "2 failed");
        assertEquals(0, check.predcessor(3), "3 failed");
//        System.out.println(check.predcessor(8)+"TEST");
        assertEquals(5, check.predcessor(5), "5 failed");
        assertEquals(5, check.predcessor(7), "7 failed");
        System.out.println("-------");
        assertEquals(5, check.predcessor(8), "8 failed");
        assertEquals(10, check.predcessor(12), "12 failed");
        assertEquals(15, check.predcessor(16), "16 failed");
        assertEquals(20, check.predcessor(31), "32 failed");




        
    }

}
