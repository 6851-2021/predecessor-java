package predecessor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class XFastTreeBitString implements Predecessor<String> {

    

    //this is a simple test where the input is a bit string
    // "01011" yes its a string of numbers of all 0 and 1.
    // Very Inefficent but only a constnat factor worse.
    // Made it to test things later
    
    
    //max bits
    private final int maxBits;
    //since the number of hash tables we have is fixed used a list to store them
    private final List<Set<String>> arrayMap;
    private final Map<String, TrieNode<String>> nodeMap;
    
    //mabe get a better Trie Structure.
    //Store Next, Prev pointer + Decadent pointer.
    
        
    /**
     * Make a new XFastTreeBitString 
     * @param maxBits max bits of strings.
     */
    public XFastTreeBitString(int maxBits) {
        this.maxBits=maxBits;
        //creating an empty set for each level of the map
        arrayMap = new ArrayList<Set<String>>();
        for(int i = 0; i < maxBits+1; i++) {
            arrayMap.add(new HashSet<String>());
        }
        arrayMap.get(0).add("");
        
        TrieNode<String> rootNode = new TrieNode<String>("");
//        
//        //give the root node min and max values OUTSIDE the universe
        rootNode.max = Integer.toString(-1);
        rootNode.min = Integer.toBinaryString((int)Math.pow(2,maxBits+2)+1);
//        
        nodeMap=new HashMap<String, TrieNode<String>>();
        nodeMap.put("", rootNode);
    }
    
    
    @Override
    public void insert(String newObject) {
        assert newObject.length()==maxBits;

        final int newVal = Integer.parseInt(newObject, 2);
        
        int i = this.maxBits;
        while(i>=0) {
            final String subStr = newObject.substring(0,i);
            if(arrayMap.get(i).contains(subStr)) {
              i=i-1; //end loop
              
              final TrieNode<String> ancestorInside = nodeMap.get(subStr);
              final int oldMax = Integer.parseInt(ancestorInside.max, 2);
              final int oldMin = Integer.parseInt(ancestorInside.min, 2);
              
              if (newVal > oldMax) {
                  ancestorInside.max = newObject;
              }
              if (newVal < oldMin) {
                  ancestorInside.min = newObject;
              }
              
            }else {
                TrieNode<String> newNode = new TrieNode<String>(subStr);
                newNode.max = newObject;
                newNode.min = newObject;
                arrayMap.get(i).add(subStr);
                nodeMap.put(subStr, newNode);
                i=i-1;
            }
//              arrayMap.get(i).add(subStr);
        }
    }
    
    @Override
    public void delete(String delObject) {
        assert delObject.length()==maxBits;
        return;


        
    }

    /**
     * Find the section in the key with the commono anestor of key. Smallest height possible
     * 
     * @param keyObject
     * @return
     */
    private String findCommonAncestor(String keyObject) {
        
        int high = maxBits;
        int low = 0;
        String retStr="";
        
        while(high-low>0) {
            //Binary search to find common ancestor
            int i = (high - low )/2;
            
            final String subStr = keyObject.substring(0,i);
            if(arrayMap.get(i).contains(subStr)) {
                low = i;
                retStr = subStr;
            }else {
                high = i;
            }   
        }
        return retStr;
    }
    
    @Override
    public String predcessor(String keyObject) {
        
        assert keyObject.length()==maxBits;
        
        
        int i = maxBits/2;
        

        //we need to do a binary search on the height of the tree
        
        
        return null;
    }

    @Override
    public String sucessor(String keyObject) {
        assert keyObject.length()==maxBits;

        // TODO Auto-generated method stub
        return null;
    }


    //Used for testing
    
    
    //Helper functions that expose the rep. 
    //For bug testing
    public List<Set<String>> exposeRepList(){
        return Collections.unmodifiableList(arrayMap);
    }
    
    public Map<String, TrieNode<String>> exposeRepMap(){
        return Collections.unmodifiableMap(nodeMap);
    }

}


class TrieNode<T>{
    
    
    //Binary Trie Node. That has 2 children.
    
    //making these public cause why the hell not
    //bad coding practices
    
    private final T value;
//    public final TrieNode parent;
    public T max;
    public T min;


    
    public TrieNode(T val) {
        this.value=val;
//        parent =null;
    }
    
    
    public T getValue() {
        return value;
    }
    
    public int hashcode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof TrieNode && this.sameValue((TrieNode)that);
    }
    
    private boolean sameValue(TrieNode that) {
        // TODO Auto-generated method stub
        return this.value.equals(that.value);
    }


    public String toString() {
        return value.toString() + " Max: " + max + " Min: " +min;
    }
    
    
    
}




