package predecessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public abstract class TestPredecessorInteger {
    
    
    
    public abstract Predecessor<Integer> emptyInstance(int x);
    
    @Test 
    public void testInsert() {
        Predecessor<Integer> check = emptyInstance(5);
        check.insert(0);
        
//        System.out.println(check.toString());        


        check.insert(5);
        check.insert(20);
        check.insert(10);
        check.insert(15);
        check.insert(5+1);
        check.insert(20+1);
        check.insert(10+1);
        check.insert(15+2);
        check.insert(5-1);
        check.insert(20-1);
        check.insert(10-1);
//        System.out.println("TEST:" + check.toString());        
       
//        System.out.println("INSERTING 14");
        check.insert(14);
        
        assertTrue(check.contains(5));
        assertTrue(check.contains(10));
        assertTrue(check.contains(15));
        assertFalse(check.contains(1));


//        System.out.println(check.toString());        
    }
    
    @Test
    public void testDelete() {
        Predecessor<Integer> check = emptyInstance(5);
        check.insert(0);
//        System.out.println(check.toString());

        check.insert(5);
        check.insert(20);
        check.insert(10);
        check.insert(15);
        check.insert(5+1);
        check.insert(20+1);
        check.insert(10+1);
        check.insert(15+2);
        check.insert(5-1);
        check.insert(20-1);
        check.insert(10-1);
//        System.out.println(check.toString());      

//        System.out.println("DelTEST" + check.toString());    
        
        check.delete(10);
        check.delete(15);
//        System.out.println(check.toString());      
        
        
        check.delete(0);
        check.delete(4);
        check.delete(5);
        check.delete(9);
        assertFalse(check.contains(5));
        assertFalse(check.contains(10));
        assertFalse(check.contains(15));
        assertFalse(check.contains(1));
//        System.out.println(check.toString());      

        
//        System.out.println("DelTEST" + check.toString());      
    }
    
    
//    
    @Test
    public void simplePredCheck() {
        Predecessor<Integer> check = emptyInstance(5);
//        check.insert(0);
        check.insert(5);
        check.insert(10);
        check.insert(15);
        check.insert(20);
        
//        System.out.println(((XFastTreeIntegers)check).displayMap());
//      
//        assertThrows(NoElementException.class,() ->{x.predcessor(s);}, "Input " +  s + "  lacked error: OUtput");

        assertThrows(NoElementException.class,() ->{check.predcessor(0);});
        assertThrows(NoElementException.class,() ->{check.predcessor(1);});
        assertThrows(NoElementException.class,() ->{check.predcessor(2);});
        assertThrows(NoElementException.class,() ->{check.predcessor(3);});
        assertThrows(NoElementException.class,() ->{check.predcessor(5);});

        
//        System.out.println(check.toString());      

        assertEquals(5, check.predcessor(7), "7 failed");
//        System.out.println("-------");
        assertEquals(5, check.predcessor(8), "8 failed");
        assertEquals(10, check.predcessor(14), "14 failed");
        assertEquals(15, check.predcessor(16), "16 failed");
        assertEquals(20, check.predcessor(31), "31 failed");




        
    }
    
    @Test
    public void testSucessor() {
        Predecessor<Integer> check = emptyInstance(5);
        check.insert(5);
        check.insert(10);
        check.insert(15);
        check.insert(20);

        assertEquals(10, check.sucessor(5), "5 failed");
        assertEquals(10, check.sucessor(7), "7 failed");
//        System.out.println("-------");
        assertEquals(10, check.sucessor(8), "8 failed");
        assertEquals(15, check.sucessor(14), "14 failed");
        assertEquals(20, check.sucessor(16), "16 failed");
        assertThrows(NoElementException.class,() ->{check.sucessor(31);});




        
    }
    
    @Test
    public void testInsertDelete() {
        Predecessor<Integer> check = emptyInstance(5);
        check.insert(0);
        check.insert(5);
        check.insert(10);
        check.insert(15);
        check.insert(20);
        
        assertEquals(10, check.predcessor(11), "previous 14");
        check.delete(10);
//        System.out.println(((XFastTreeIntegers)check).displayMap());

        assertEquals(5, check.predcessor(11), "Delete 14");
        
    
    }
    
    @Test public void testEdgeCases() {
        Predecessor<Integer> check = emptyInstance(5);

        check.insert(5);
        check.insert(20);
        check.insert(10);
        check.insert(15);
        check.insert(5+1);
        check.insert(20+1);
        check.insert(10+1);
        check.insert(15+2);
        check.insert(5-1);
        check.insert(20-1);
        check.insert(10-1);
        
        check.delete(10);
        check.delete(9);
        check.delete(11);
        
//        System.out.println(check.toString());      

        
        assertEquals(6, check.predcessor(10));
        assertEquals(15, check.sucessor(10));
        
        assertEquals(6, check.predcessor(11));
        assertEquals(15, check.sucessor(11));
        
        assertEquals(6, check.predcessor(9));
        assertEquals(15, check.sucessor(9));

        
    }

}




