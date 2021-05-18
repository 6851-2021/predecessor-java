package predecessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public abstract class TestPredecessorInteger {
    
    
    
    public abstract Predecessor<Integer> emptyInstance(int x);
    
    
    @Test public void testEquality() {
        Predecessor<Integer> check = emptyInstance(5);
        Predecessor<Integer> check2 = emptyInstance(5);
        check.insert(5);
        check.insert(7);
        check2.insert(5);
        check2.insert(7);
        assertTrue(check.equals(check2));


    }
    
    @Test 
    public void testInsert() {
        Predecessor<Integer> check = emptyInstance(5);
        check.insert(0);
        


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
    
    @Test 
    public void deleteNonExistingElements() {
        Predecessor<Integer> check = emptyInstance(5);

        check.delete(3);
        check.delete(12);

        check.delete(15);

        
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

        assertThrows(NoElementException.class,() ->{check.predecessor(0);});
        assertThrows(NoElementException.class,() ->{check.predecessor(1);});
        assertThrows(NoElementException.class,() ->{check.predecessor(2);});
        assertThrows(NoElementException.class,() ->{check.predecessor(3);});
        assertThrows(NoElementException.class,() ->{check.predecessor(5);});

        
//        System.out.println(check.toString());      

        assertEquals(5, check.predecessor(7), "7 failed");
//        System.out.println("-------");
        assertEquals(5, check.predecessor(8), "8 failed");
        assertEquals(10, check.predecessor(14), "14 failed");
        assertEquals(15, check.predecessor(16), "16 failed");
        assertEquals(20, check.predecessor(31), "31 failed");




        
    }
    
    @Test
    public void testSucessor() {
        Predecessor<Integer> check = emptyInstance(5);
        check.insert(5);
        check.insert(10);
        check.insert(15);
        check.insert(20);

        assertEquals(10, check.successor(5), "5 failed");
        assertEquals(10, check.successor(7), "7 failed");
//        System.out.println("-------");
        assertEquals(10, check.successor(8), "8 failed");
        assertEquals(15, check.successor(14), "14 failed");
        assertEquals(20, check.successor(16), "16 failed");
        assertThrows(NoElementException.class,() ->{check.successor(31);});
        
    }
    
    @Test
    public void testInsertDelete() {
        Predecessor<Integer> check = emptyInstance(5);
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

        
        assertEquals(6, check.predecessor(10));
        assertEquals(15, check.successor(10));
        
        assertEquals(6, check.predecessor(11));
        assertEquals(15, check.successor(11));
        
        assertEquals(6, check.predecessor(9));
        assertEquals(15, check.successor(9));
        
    }

    //TODO: Find a better name for this test csae
    @Test public void testFullySaturatedOrEmptySubtrees() {
        Predecessor<Integer> check = emptyInstance(5);

        check.insertAll(Set.of(0,1,2,3,15,14,13,12));
        
        for(int i  = 4; i<12; i++){ 
            assertEquals(3, check.predecessor(i));
            assertEquals(12, check.successor(i));  
        }
      


        
    }
    
    //large scale comparison where we compare toa  tree set
    @Test public void testCompareToTreeSet() {
        
        final int universeBits = 10;
        final int numberOfElements = 1000;
        final int queries = 1000;
        
        final int scale = (int)  Math.pow(2, universeBits) ;
        
        //create a list of element to put inside
        final Set<Integer> additionSet = new HashSet<Integer>();
        for(int i = 0; i < numberOfElements; i++ ) {
            final int newValue = (int) (Math.random() * scale);
            additionSet.add(newValue);
        }
//        System.out.println(additionSet);
//        create list of elements to query
        final Set<Integer> querySet = new HashSet<Integer>();
        for(int i = 0; i < queries; i++ ) {
            final int newValue = (int) (Math.random() * scale);
            additionSet.add(newValue);
        }

        
        Predecessor<Integer> testPredecessor = emptyInstance(universeBits);
        Predecessor<Integer> comparisonPredecessor = new TreeSetWrapper();
        
        testPredecessor.insertAll(additionSet);
        comparisonPredecessor.insertAll(additionSet);
        
        for(int q : querySet) {
            assertEquals(comparisonPredecessor.successor(q),
                    testPredecessor.successor(q));
            assertEquals(comparisonPredecessor.predecessor(q),
                    testPredecessor.predecessor(q));
        }
        
        
    }
}




