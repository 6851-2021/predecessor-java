package predecessor;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

public class XFastTreeSpeedTest {
    
    //Predecessor<Integer> check = new XFastTreeIntegers(5);
    
    
//    @Test 
    public void insertSpeed() {
        
        Predecessor<Integer> check = new XFastTreeIntegers(10);
        Set<Integer> bench = new TreeSet<Integer>();
        
        Set<Integer> insertSet = new HashSet<Integer>();
        for(int i = 0; i<700; i++) {
            insertSet.add((int) (Math.random()*(1024)));
        }
        
        long start;
        long delta;
        
        start = System.currentTimeMillis();
        for (int s : insertSet) {
            bench.add(s);
        }
        delta = (System.currentTimeMillis() - start)*1000;
        System.out.println("Bench Time"  + delta);
        
        start = System.currentTimeMillis();
        for(int s : insertSet) {
            check.insert(s);
        }
        delta =  (System.currentTimeMillis() - start)*1000;
        System.out.println("XFast Time" + delta);
    }
    
    @Test 
    public void predecessorSpeed() {
        
        
        final int maxBits = 16;
        
        Predecessor<Integer> check = new XFastTreeIntegers(maxBits);
        Predecessor<Integer> bench = new TreeSetWrapper();
        
        final int scale = (int) Math.pow(2, maxBits);
        Set<Integer> insertSet = new HashSet<Integer>();
        for(int i = 0; i<Math.pow(2, maxBits-7); i++) {
            final int s = (int) (Math.random()*scale);
            if(!insertSet.contains(s)) {
                insertSet.add(s);
                bench.insert(s);
                check.insert(s);
            }
        }
        
        long start;
//        long delta;
        
        start = System.nanoTime();
//        for (int s : bench) {
            bench.predcessor(scale/2);
//        }
        long deltaB = (System.nanoTime() - start)*1000;
        System.out.println("Bench Time"  + deltaB);
        
        start = System.nanoTime();
//        for(int s : bench) {
            check.predcessor(scale/2);
//        }
        long deltaX =  (System.nanoTime() - start)*1000;
        System.out.println("XFast Time" + deltaX);
        System.out.println(deltaX < deltaB);
    }
    

}
