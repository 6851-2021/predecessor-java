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
    
    
    private final String DEFAULT_MAX = Integer.toString(-1);
    private final String DEFAULT_MIN = Integer.toBinaryString(Integer.MAX_VALUE);
    private final TrieNode<String> defaultNode = new TrieNode<String>("") {{
         this.max = DEFAULT_MAX;
         this.min = DEFAULT_MIN;
    }};
    
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
        rootNode.max = DEFAULT_MAX;
        rootNode.min = DEFAULT_MIN;
//        
        nodeMap=new HashMap<String, TrieNode<String>>();
        nodeMap.put("", rootNode);
    }
    
    
    @Override
    public void insert(String newObject) {
        //check max bits is proper
        assert newObject.length()==maxBits;
        //get Integer value of our new object
        final int newVal = Integer.parseInt(newObject, 2);
        
        //start at bottom of tree
        int i = this.maxBits;
        while(i>=0) {
            final String subStr = newObject.substring(0,i);
            if(arrayMap.get(i).contains(subStr)) {
              //get Trie Node in the tree
              final TrieNode<String> ancestorInside = nodeMap.get(subStr);
              final int oldMax = Integer.parseInt(ancestorInside.max, 2);
              final int oldMin = Integer.parseInt(ancestorInside.min, 2);
              //update max and min values respectively.
              if (newVal > oldMax) {
                  ancestorInside.max = newObject;
              }
              if (newVal < oldMin) {
                  ancestorInside.min = newObject;
              }
              
            }else {
                //add new node to tree and set its max and min properly
                TrieNode<String> newNode = new TrieNode<String>(subStr);
                newNode.max = newObject;
                newNode.min = newObject;
                arrayMap.get(i).add(subStr);
                nodeMap.put(subStr, newNode);
            }
            //move back up the tree
            i=i-1;
//              arrayMap.get(i).add(subStr);
        }
    }
    
    @Override
    public void delete(String delObject) {
        assert delObject.length()==maxBits;
        
        final int newVal = Integer.parseInt(delObject, 2);
        //start at bottom of tree and delete nodes
        int i = this.maxBits;
        
        while(i>=0) {
            final String subStr = delObject.substring(0,i);
            
            final String child0 = subStr + "0";
            final String child1 = subStr + "1";
            //check if any child in the map.
            //use node map so checking MaxBit+1 values wont result in errors
            final boolean child0In = nodeMap.containsKey(child0);
            final boolean child1In = nodeMap.containsKey(child1);
            
            if(child0In || child1In) {
                
                TrieNode<String> child0Node = nodeMap.getOrDefault(child0, defaultNode);
                TrieNode<String> child1Node = nodeMap.getOrDefault(child1, defaultNode);
                
                final int child0Max = (child0In) ? Integer.parseInt(child0Node.max,2) : 0;
                final int child1Max = (child1In) ? Integer.parseInt(child1Node.max,2) : 0;

                final int child0Min = (child0In) ? Integer.parseInt(child0Node.min,2) : Integer.MAX_VALUE;
                final int child1Min = (child1In) ? Integer.parseInt(child1Node.min,2) : Integer.MAX_VALUE;
                
                final int maxVal = Math.max(child0Max, child1Max);
                final int minVal = Math.min(child0Min, child1Min);
                
                nodeMap.get(subStr).max = Integer.toBinaryString(maxVal);
                nodeMap.get(subStr).min = Integer.toBinaryString(minVal);

            }else {
                nodeMap.remove(subStr);
                arrayMap.get(i).remove(subStr);
            }
                       
            i=i-1;

        }
        
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
        
//        int tick = 0;
        while(high-low>1) {
//            tick = tick + 1;
//            if (tick>100) {
//                return null; //TIME OUT ERROR
//            }
            //Binary search to find common ancestor
            int i = (high + low )/2;
            
            System.out.println("i:" + i + "| low: " + low + "| high: " + high );
//            System.out.println((high-low)/2);

//            System.out.print(low);
//            System.out.print(high);

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
        
        
        final String commonAncestor = findCommonAncestor(keyObject);
        return nodeMap.get(commonAncestor).min;
        

    }

    @Override
    public String sucessor(String keyObject) {
        assert keyObject.length()==maxBits;

        final String commonAncestor = findCommonAncestor(keyObject);
        return nodeMap.get(commonAncestor).max;

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




