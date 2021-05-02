package predecessor;

import java.util.Set;
import java.util.HashSet;



public class SpeedTesting {

    
    private static void insertSpeed(int maxBits, int n) {
        Predecessor<Integer> xFast = new XFastTreeIntegers(maxBits);
        Predecessor<Integer> yFast = new YFastTrie(maxBits);

        Set<Integer> addValues = new HashSet<Integer>();
        final int scale = (int)Math.pow(2, maxBits);
        while(addValues.size() < n) {
            int i = (int) (Math.random() * scale);
            addValues.add(i);
        }
        final long xFastStart;
        final long xFastEnd;
        final long yFastStart;
        final long yFastEnd;
        xFastStart = System.nanoTime();
        for(int i : addValues) {
            xFast.insert(i);
        }
        xFastEnd = System.nanoTime();
        yFastStart = System.nanoTime();
        for(int i : addValues) {
            yFast.insert(i);
        }
        yFastEnd = System.nanoTime();
        final long xDelta = xFastEnd - xFastStart;
        final long yDelta = yFastEnd - yFastStart;

        System.out.println("----");
        System.out.println("For a bit length of " +maxBits);
        System.out.println("XFast insertion took " +xDelta);
        System.out.println("Average of " +xDelta/n);
        System.out.println("YFast insertion took " +yDelta);
        System.out.println("Average of " +yDelta/n);    
        System.out.println("----");

        
    }
    
    
    public static void main(String args[]) {
        insertSpeed(10,10);
        insertSpeed(10,20);
        insertSpeed(10,40);
        insertSpeed(10,80);
    }
}
