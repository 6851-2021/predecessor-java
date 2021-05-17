package predecessor;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class XFastTreeIntegersTest {
    
    
    
    
    
    @Test 
    public void testInsert() {
        Predecessor<Integer> check = new XFastTreeIntegers(5);
        check.insert(0);
//        System.out.println(check.toString());

        check.insert(5);
        check.insert(20);
        check.insert(10);
        check.insert(15);

//        System.out.println(check.toString());        
    }
    
    @Test
    public void testDelete() {
        Predecessor<Integer> check = new XFastTreeIntegers(5);
        check.insert(0);

        check.insert(5);
        check.insert(20);
        check.insert(10);
        check.insert(15);
        
        check.delete(10);
        check.delete(15);
        
        //how do we handle deleting elements that dont exist
    }
    
    
//    
    @Test
    public void simplePredCheck() {
        Predecessor<Integer> check = new XFastTreeIntegers(5);
        
        
        assertThrows(NoElementException.class,() ->{check.predecessor(12);});

//        check.insert(0);
        check.insert(5);
        check.insert(10);
        check.insert(15);
        check.insert(20);
        
//        System.out.println(((XFastTreeIntegers)check).displayMap());
//      
//        assertThrows(NoElementException.class,() ->{x.predcessor(s);}, "Input " +  s + "  lacked error: OUtput");

        assertThrows(NoElementException.class,() ->{check.predecessor(0);});
        assertThrows(NoElementException.class,() ->{check.predecessor(1);});
        assertThrows(NoElementException.class,() ->{check.predecessor(2);});
        assertThrows(NoElementException.class,() ->{check.predecessor(3);});
        assertThrows(NoElementException.class,() ->{check.predecessor(5);});

        assertEquals(5, check.predecessor(7), "7 failed");
//        System.out.println("-------");
        assertEquals(5, check.predecessor(8), "8 failed");
        assertEquals(10, check.predecessor(14), "14 failed");
        assertEquals(15, check.predecessor(16), "16 failed");
        assertEquals(20, check.predecessor(31), "31 failed");




        
    }
    
    @Test
    public void testSucessor() {
        Predecessor<Integer> check = new XFastTreeIntegers(5);
//        check.insert(0);
        
        assertThrows(NoElementException.class,() ->{check.successor(12);});

        
        check.insert(5);
        check.insert(10);
        check.insert(15);
        check.insert(20);
        
//        System.out.println(((XFastTreeIntegers)check).displayMap());

        assertEquals(10, check.successor(5), "5 failed");
        assertEquals(10, check.successor(7), "7 failed");
//        System.out.println("-------");
        assertEquals(10, check.successor(8), "8 failed");
        assertEquals(15, check.successor(14), "14 failed");
        assertEquals(20, check.successor(16), "16 failed");
        assertThrows(NoElementException.class,() ->{check.successor(31);});

//        assertEquals(32, check.sucessor(31), "31 failed");




        
    }
    
    @Test
    public void testInsertDelete() {
        Predecessor<Integer> check = new XFastTreeIntegers(5);
        
        check.insert(31);
        assertEquals(31, check.successor(5));
        check.delete(31);
        
        check.insert(0);
        check.insert(5);
        check.insert(10);
        check.insert(15);
        check.insert(20);
        
        assertEquals(10, check.predecessor(11), "previous 14");
        check.delete(10);
//        System.out.println(((XFastTreeIntegers)check).displayMap());

        assertEquals(5, check.predecessor(11), "Delete 14");
        
    
    }
    
    @Test
    public void testQ() {
        Predecessor<Integer> check = new XFastTreeIntegers(5);

        check.insert(31);
        assertEquals(31, check.successor(5));
        assertEquals(31, check.successor(10));

    }

}
