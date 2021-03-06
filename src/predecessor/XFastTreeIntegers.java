package predecessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


public class XFastTreeIntegers implements Predecessor<Integer>{
    
    //max number of bits to store
    private final int maxBits;
    //map to nodes on the tree
    private final Map<IntPair, TrieNode<Integer>> valueMap = new HashMap<IntPair, TrieNode<Integer>>();
    //linked List to store leaves
    private final LinkedListMap leafList;
    //default node to store  max and min values for stuff not in map
    private final TrieNode<Integer> defaultNode = new TrieNode<Integer>(0);
    //Max(val,defaultMax)= val for valid inputs. Same with min
    private final int defaultMax;
    private final int defaultMin;

    /**
     * Create a new XFastTree that supports integers from 0 to 2^maxBits-1
     * @param maxBits the maximum bits of acceptable integers
     * Cause of Java used signed bits must be 0 to 31 or we get errors
     */
    public XFastTreeIntegers(int maxBits) {
        
        defaultMax = -1;
        defaultMin = (int) Math.pow(2, maxBits);
        defaultNode.max = defaultMax;
        defaultNode.min = defaultMin;
        //set the max bits
        this.maxBits=maxBits;     
        leafList = new LinkedListMap( (int) Math.pow(2, maxBits));
        TrieNode<Integer> rootNode = new TrieNode<Integer>(0);
        rootNode.max = defaultMax;
        rootNode.min = defaultMin;
        valueMap.put(new IntPair(0, 0), rootNode);      
    }
    
    @Override
    public void insert(Integer newVal) {
        int wrapVal = newVal;
        assert 0 <= wrapVal && wrapVal < Math.pow(2, maxBits);
        
        if(valueMap.containsKey(new IntPair(maxBits,wrapVal))) {
            return; //kill if insert element that already exist.
        }
        //store predecessor
        int prevVal = -1;        
        //start at bottom of list and work your way back up
        for(int i = maxBits; i>=0; i--) {
            //use modular to cut off significant bits
//            int cutValue =  (int) (wrapVal / (Math.pow(2, maxBits-i)));
            int cutValue = wrapVal >> maxBits - i;
            IntPair currPair = new IntPair(i, cutValue);
            //if inside tree update max and min
            if(valueMap.containsKey(currPair)) {
                TrieNode<Integer> updateNode = valueMap.get(new IntPair(i, cutValue));
                int curMax = updateNode.max;
                int curMin = updateNode.min;
                
                prevVal = Math.max(prevVal, (curMin < newVal) ? curMin : prevVal);
                prevVal = Math.max(prevVal, (curMax < newVal) ? curMax : prevVal);
           
                updateNode.max = Math.max(curMax, wrapVal);
                updateNode.min = Math.min(curMin, wrapVal);
            //if its not in tree add it.
            }else {
//                arraySets.get(i).add(cutValue);
                TrieNode<Integer> newNode = new TrieNode(cutValue);
                newNode.max = wrapVal;
                newNode.min = wrapVal;
                valueMap.put(currPair, newNode);
            }
        }
        
        leafList.insertAfter(prevVal, newVal);   
        return;
    }
    
    @Override
    public void delete(Integer delVal) {
        int wrapVal = delVal;
        assert 0 <= wrapVal && wrapVal < Math.pow(2, maxBits);
        for(int i = maxBits; i>=0; i--) {
            int cutValue = wrapVal>> maxBits - i;
            IntPair checkPair = new IntPair(i,cutValue);
            //if node exists
            if(valueMap.containsKey(checkPair)) {
                IntPair leftChild = new IntPair(i+1,cutValue*2);
                IntPair rightChild = new IntPair(i+1,(int) (cutValue*2+1));
                if(valueMap.containsKey(leftChild) || valueMap.containsKey(rightChild)) {
//                    System.out.println("checking children | " + checkPair.toString());
                    //get the new min - max value by comparing children
                    int newMax = Math.max(valueMap.getOrDefault(leftChild, defaultNode).max,
                                          valueMap.getOrDefault(rightChild, defaultNode).max);
                    int newMin = Math.min(valueMap.getOrDefault(leftChild, defaultNode).min,
                                          valueMap.getOrDefault(rightChild, defaultNode).min);
                    //update current pair to take in account that children are gone
                    valueMap.get(checkPair).max=newMax;
                    valueMap.get(checkPair).min=newMin;
                }else {
                    //if neither children are inside delete the element from the map
                    valueMap.remove(checkPair);
//                    System.out.println("REMOVING" + checkPair.toString());
                }
            }else {
//                System.out.println("NOT INSIDE");
            }
        }
        leafList.delete(delVal);
        return;
    }
    
    @Override
    public boolean contains(Integer keyObject) {
        return leafList.getNode(keyObject)!=null;
    }

    private IntPair findCommonAncestor(int keyObject) {
        int low = 0;
        int high = this.maxBits;
        IntPair retPair = null;
        int value = 0;
        while(high - low > 1) {
            final int i = (high + low ) /2;
            final int cutValue = keyObject >> maxBits - i;
//            System.out.println(i + "|" + cutValue);
            final IntPair checkPair = new IntPair(i, cutValue);
            if(valueMap.containsKey(checkPair)) {
                retPair = checkPair;
                low = i;
            }else {
                high = i;
            }
        }
        if(retPair == null) {
            return new IntPair(0, 0);
//            throw new NoElementException("Common Ancestor is null");
//          
        }
        return retPair;
    }
    
    
    public Integer predecessor(Integer keyObject) {
        
        assert 0 <= keyObject && keyObject <= Math.pow(2, maxBits);

        IntPair commonAncestor = findCommonAncestor(keyObject);
//        System.out.println(commonAncestor.toString());
//        System.out.println(valueMap.get(commonAncestor).toString());

        final int checkMax;
        final int checkMin;
        try {
            checkMax = valueMap.get(commonAncestor).max;
            checkMin = valueMap.get(commonAncestor).min;
        }catch(NullPointerException e){
            throw new NoElementException("Common Ancestor is null");
            
        }
        //check empty list
        if(checkMin == this.defaultMin && checkMax == this.defaultMax) {
            throw new NoElementException("Empty List");
        }
        //if max is smalle rits predecssor
        else if(checkMax < keyObject) {
            return checkMax;
        }else {
            //if not check min node and move left
            Node minNode = leafList.getNode(checkMin);
            
            final int check2  = minNode.getPrev().getValue();
            if(check2 == -1) {
                throw new NoElementException("No Predecessor");
            }
            return check2;
            
        }
        
    }
    
    public Integer successor(Integer keyObject) {
        assert -1 <= keyObject && keyObject < Math.pow(2, maxBits);

        IntPair commonAncestor = findCommonAncestor(keyObject);

        final int checkMax;
        final int checkMin;
        try {
            checkMax = valueMap.get(commonAncestor).max;
            checkMin = valueMap.get(commonAncestor).min;
        }catch(NullPointerException e){
            throw new NoElementException("Common Ancestor is null");
            
        }
            
        if(checkMin == this.defaultMin && checkMax == this.defaultMax) {
            throw new NoElementException("Empty List");
        }
        else if(checkMin > keyObject) {
            return checkMin;
        }else {

            Node minNode = leafList.getNode(checkMax);
            
            final int check2  = minNode.getNext().getValue();
            if(check2 == (int)Math.pow(2, maxBits)) {
                throw new NoElementException("No Sucesssor");
            }
            return check2;
            
        }
    }
    
    @Override
    public void insertAll(Collection<? extends Integer> objects) {
        for(Integer c : objects) {
            this.insert(c);
        }
    }
    
    @Override
    public void deleteAll(Collection<? extends Integer> objects) {
        for(Integer c : objects) {
            this.delete(c);
        }
    }
    @Override
    public boolean containsAll(Collection<? extends Integer> objects) {
        for(Integer c : objects) {
            if(!this.contains(c)) {
                return false;
            }
        }
        return true;
    }
    
    
    @Override public boolean equals(Object obj) {
        return obj instanceof XFastTreeIntegers && this.sameValue((XFastTreeIntegers) obj);
    }

    private boolean sameValue(XFastTreeIntegers that) {
        //TODO implement sameValue match
        return this.leafList.equals(that.leafList);
    }

    @Override public int hashCode() {
        return this.maxBits;
    }
//    public Integer get(Integer key) {
//        return leafList.getValue(key).getValue();
//    }
    
    public String displayMap() {
        String retString ="";
        List<IntPair> list = new ArrayList<IntPair>(valueMap.keySet());
        list.sort((IntPair a1, IntPair a2) -> Integer.compare(a1.getFirstInt(), a2.getFirstInt()));
        for(IntPair i : list) {
            retString += i.toString() +"| "+ valueMap.get(i).toString() + "\n";
        }
        return retString;
    }
    
    @Override 
    public String toString() {
        return leafList.toString();
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
    
    @Override 
    public boolean equals(Object obj) {
        return obj instanceof IntPair && this.sameValue((IntPair) obj);
    }

    public int getFirstInt() {
        return firstInt;
    }
    
    public int getSecondInt() {
        return secondInt;
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
        return "("+firstInt + " | " + Integer.toBinaryString(secondInt)+")";
    }
    
}




