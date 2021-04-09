package predecessor;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;


class XFastTreeBitStringTest {

    
    
    @Test void test() {
        Predecessor<String> x = new XFastTreeBitString(5);
    }
    
    @Test void testInsert() {
        
        boolean Print = false;
        
        Predecessor<String> x = new XFastTreeBitString(5);
        x.insert("00000");
        x.insert("00000");
        x.insert("00101");
        x.insert("10000");
        x.insert("10110");
        
        assertEquals("00000", x.predcessor("00001"), "Wrong Predecsotrr");
        
        return;
    }
    
    @Test
    void testDelete() {
        Predecessor<String> x = new XFastTreeBitString(5);
        x.insert("00000");
        x.insert("00011");
        x.insert("00010");
        x.insert("00101");
//        x.insert("10000");
        x.insert("10001");
        x.insert("10011");
        x.insert("10110");
        
        x.delete("10000");
        
        assertEquals("10001",x.sucessor("10000"));
        assertEquals("00101",x.predcessor("10000"));

    }
    
    @Test
    void testPredecssor() {
        Predecessor<String> x = new XFastTreeBitString(5);

        x.insert("01010");
        
        Set<String> testSet = Set.of("11111", "01011", "01100", "01010");
        
        for(String s : testSet) { 
            assertEquals("01010", x.predcessor(s));
         }
    }
    
    @Test
    void testException() {
        Predecessor<String> x = new XFastTreeBitString(5);

        x.insert("01010");
        
        Set<String> testSet = Set.of("00111", "01001", "00011", "00100", "00010");
        
        for(String s : testSet) { 
            assertThrows(NoElementException.class,() ->{x.predcessor(s);}, "Input " +  s + "  lacked error: OUtput");
//            assertEquals("01010", x.predcessor(s));
         }
    }
    
    @Test void testSucessor() {
        Predecessor<String> x = new XFastTreeBitString(5);

        x.insert("11101");
        
        Set<String> testSet = Set.of("11101", "01011", "01100", "11100");
        
        Set<String> errorSet = Set.of("11110", "11111");
        
        for(String s : testSet) { 
            assertEquals("11101", x.sucessor(s));
         }
        
        for(String s : errorSet) {
            assertThrows(NoElementException.class, () -> {x.sucessor(s);},"Inputs "+s+ "lackeed Erorr:");
         }
    }
    
    

}
