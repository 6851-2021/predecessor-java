package predecessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


public class XFastTreeIntegers implements Predecessor<Integer>{
    
    private final int maxBits;
    
    //only use one
//    private final List<Set<Integer>> arraySets = new ArrayList<Set<Integer>>();
    private final Map<IntPair, TrieNode<Integer>> valueMap = new HashMap<IntPair, TrieNode<Integer>>();
    
    private final TrieNode<Integer> defaultNode = new TrieNode<Integer>(0);
    
    
    
    public XFastTreeIntegers(int maxBits) {
        defaultNode.max = Integer.MIN_VALUE;
        defaultNode.min = Integer.MAX_VALUE;
        //set the max bits
        this.maxBits=maxBits;
        //create an empty map set
//        for (int i = 0; i<=maxBits; i++) {
//            arraySets.set(i, new HashSet<Integer>());
//        }
        
        TrieNode<Integer> rootNode = new TrieNode<Integer>(0);
        rootNode.max = Integer.MIN_VALUE;
        rootNode.min = Integer.MAX_VALUE;
        valueMap.put(new IntPair(0, 0), rootNode);
//        arraySets.get(0).add(0);
    }
    
    @Override
    public void insert(Integer newVal) {
        int wrapVal = newVal;
        assert 0 <= wrapVal && wrapVal < Math.pow(2, maxBits);
        //start at bottom of list and work your way back up
        for(int i = maxBits; i>=0; i--) {
            //use modular to cut off signficant bits
            int cutValue = (int) (wrapVal % Math.pow(2, i));
            IntPair currPair = new IntPair(i, cutValue);
            //if inside tree update max and min
            if(valueMap.containsKey(currPair)) {
                TrieNode<Integer> updateNode = valueMap.get(new IntPair(i, cutValue));
                updateNode.max = Math.max(updateNode.max, wrapVal);
                updateNode.min = Math.min(updateNode.min, wrapVal);
            //if its not in tree add it.
            }else {
//                arraySets.get(i).add(cutValue);
                TrieNode<Integer> newNode = new TrieNode(cutValue);
                newNode.max = wrapVal;
                newNode.min = wrapVal;
                valueMap.put(currPair, newNode);
            }
        }
        return;
    }
    
    @Override
    public void delete(Integer delVal) {
        int wrapVal = delVal;
        assert 0 <= wrapVal && wrapVal < Math.pow(2, maxBits);
        for(int i = maxBits; i>=0; i--) {
            int cutValue = (int) (wrapVal % Math.pow(2, i));
            IntPair checkPair = new IntPair(i,cutValue);
            //if node exists
            if(valueMap.containsKey(checkPair)) {
                IntPair leftChild = new IntPair(i+1,cutValue);
                IntPair rightChild = new IntPair(i+1,(int) (cutValue+Math.pow(2, i+1)));
                if(valueMap.containsKey(leftChild) || valueMap.containsKey(rightChild)) {
                    //get the new min - max value by comapring chidlrne
                    int newMax = Math.max(valueMap.getOrDefault(leftChild, defaultNode).max,
                                          valueMap.getOrDefault(leftChild, defaultNode).max);
                    int newMin = Math.min(valueMap.getOrDefault(leftChild, defaultNode).min,
                                          valueMap.getOrDefault(leftChild, defaultNode).min);
                    //update current pair to take in account that chidlren are gone
                    valueMap.get(checkPair).max=newMax;
                    valueMap.get(checkPair).min=newMin;
                }else {
                    //if nethier childrena are inside delete the element from the map
                    valueMap.remove(checkPair);
                }
            }
        }
        return;
    }
    
    private IntPair findCommonAncestor(int keyObject) {
        int low = 0;
        int high = this.maxBits;
        IntPair retPair = null;
        while(high - low > 1) {
            final int i = (high + low ) /2;
            final int cutValue = (int)(keyObject % Math.pow(2,i));
            final IntPair checkPair = new IntPair(i, cutValue);
            if(valueMap.containsKey(checkPair)) {
                retPair = checkPair;
                low = i;
            }else {
                high = i;
            }
        }
        return retPair;
    }
    
    
    public Integer predcessor(Integer keyObject) {
        
        IntPair commonAncestor = findCommonAncestor(keyObject);
        System.out.println(commonAncestor.toString());
        if(commonAncestor == null) {
            throw new NoElementException("Null Error, No Predecessor Element");
        }
        final int check = valueMap.get(commonAncestor).min;
        if(check <= keyObject) {
            return check;
        }else {
            final int newCheckVal = (int) (check - Math.pow(2, commonAncestor.getFirstInt()));
            final int secondCheck = valueMap.get(findCommonAncestor(newCheckVal)).max;
            if (secondCheck <= keyObject) {
                return secondCheck;
            }else {
                throw new NoElementException("No Predecessor Element" + secondCheck + "|" + check);

            }
        }
        
    }
    
    public Integer sucessor(Integer keyObject) {
        return null;
    }

    
    public String displayMap() {
        return valueMap.toString();
    }
}

class IntPair{
    
    //store a pair of integers.
    
    private final int firstInt;
    private final int secondInt;
    
    public IntPair(int one, int two) {
        firstInt = one;
        secondInt = two;
    }
    
    @Override public boolean equals(Object obj) {
        return obj instanceof IntPair && this.sameValue((IntPair) obj);
    }

    public int getFirstInt() {
        return firstInt;
    }
    
    private boolean sameValue(IntPair that) {
       return this.firstInt==that.firstInt && this.secondInt ==that.secondInt;
    }
    
    @Override
    public int hashCode() {
        return firstInt+secondInt;
    }
    
    @Override
    public String toString() {
        return "("+firstInt + " | " + secondInt+")";
    }
    
}



