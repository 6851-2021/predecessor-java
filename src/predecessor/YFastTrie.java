package predecessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class YFastTrie implements Predecessor<Integer>{
    
    
    
    private XFastTreeIntegers base;
    private Map<Integer, TreeSet<Integer>> treeMap;
    
    private final int maxBits;
//    private final int xTrieBits;
    
    private final int minTreeSize;
    private final int maxTreeSize;
    
    //aska bout this since im kinda dstuck
    
    
    public YFastTrie(int bitLength) {
        this.treeMap = new HashMap<Integer, TreeSet<Integer>>();
        this.maxBits=bitLength;
        
//        this.xTrieBits = (int) Math.floor((bitLength/Math.log10(bitLength)));
        this.base = new XFastTreeIntegers(bitLength);
        
        this.minTreeSize = maxBits/2;
        this.maxTreeSize = maxBits * 2;
     
        return;
    }
    
    
    /**
     * Merge the trees assoicated with paramters x and y
     * If resulting tree has more than max size length
     * Updates the map and rep values in the xFastTrie
     * Tree should be next to each other
     * 
     * @param x
     * @param y
     */
    private void mergeTrees(int x, int y) {
        
        if(treeMap.get(x).size() >= this.minTreeSize && treeMap.get(y).size() >= this.minTreeSize && this.treeMap.size()>1) {
            return;
        }
        else {
            final int minKey = Math.min(x, y);
            final int maxKey = Math.max(x, y);          
            TreeSet<Integer> maxTree = treeMap.get(maxKey);
            maxTree.addAll(treeMap.get(minKey));
            treeMap.remove(minKey);
            base.delete(minKey);
            splitTree(maxKey);
        }  
        return;
    }
    
    /**
     * Split the tree asocaited with paratmer z into 2 smaller trees
     * Does nothing if the tree assocaited with z is not above the max size
     * Updates the map and rep values in the xFastTrie
     * 
     * @param z the key of the tree to split (or check to split it)
     */
    private void splitTree(int z) {
        if(treeMap.get(z).size() <= this.maxTreeSize) {
            //tree size not large enough do nothing
            return;
        }else {
//            System.out.println("SPLITTING" + treeMap.get(z).size());
            TreeSet<Integer> oldSet = new TreeSet<Integer>(treeMap.get(z));
            final int oldSize = oldSet.size();
            TreeSet<Integer> smallerSet = new TreeSet<Integer>();
            TreeSet<Integer> largerSet = new TreeSet<Integer>();
            
            //add half the elements to the smaller set
            for(int i =0; i<oldSize/2; i++) {
                final int smallElement = oldSet.first();
                oldSet.remove(smallElement);
                smallerSet.add(smallElement);
            }
            //add the remaing elements to the larger set
            largerSet.addAll(oldSet);
            
            //assertion
            assert smallerSet.size() + largerSet.size() == oldSize : "Did not split tree properly";
            
//            System.out.println(largerSet.toString());
//            System.out.println(smallerSet.toString());
            
            treeMap.remove(z);
            //use final element of smallert set as its repersenative
            treeMap.put(smallerSet.last(), smallerSet);
            //use the old larger repersantive for the largesr set
            treeMap.put(z, largerSet);  
            base.insert(smallerSet.last());
        }
        
        
    }

    @Override
    public void insert(Integer newObject) {
        int newKey = newObject;
        
        int treeKey;
        
        try {
//            System.out.println(newKey);
            treeKey = this.base.sucessor(newKey-1);
//            System.out.println("FOUND:" + treeKey);
        } catch(NoElementException e) {
            //if there is no sucessor Tree create one
            treeKey = (int) (Math.pow(2, maxBits)-1);
//            System.out.println(base.toString());

//            System.out.println("NOT FOUND: " +base + " SERACHING "+newKey + " error: " + e);
            this.base.insert(treeKey);
            TreeSet<Integer> newSet = new TreeSet<Integer>();
            newSet.add(newKey);
            treeMap.put(treeKey, newSet);
        }
        
        
        treeMap.get(treeKey).add(newKey);
        splitTree(treeKey);
        return;
        
    }

    @Override
    public void delete(Integer delObject) {
        int delKey = delObject;
        
        int treeKey;
        try {
            //have to subtract -1 since sucessor is strict
            treeKey = base.sucessor(delKey-1);
        } catch(NoElementException e) {
            //its not in the BST do nothing
            return;
        }
        TreeSet<Integer> treeSet = treeMap.get(treeKey);
        treeSet.remove(delKey);
        
        
        try {
            //merge with sucessor
            int nextTreeKey = base.sucessor(treeKey);
            this.mergeTrees(treeKey, nextTreeKey);
            
        } catch (NoElementException e) {
            try {
                //memrge with predecessor
                int prevTreeKey = base.predcessor(treeKey);
                this.mergeTrees(treeKey, prevTreeKey);
            }
            catch(NoElementException ee) {
                //this is only tree so no need to merge
                return;
                
            }
        }
        
        
    }

    @Override
    public Integer predcessor(Integer keyObject) {
        int keyVal = keyObject;
        
        int treeKey;
        
        try {
            treeKey = this.base.predcessor(keyVal+1);
        }catch (NoElementException e) {
            treeKey = this.base.sucessor(keyVal);
        }
        
        TreeSet<Integer> treeSet = treeMap.get(treeKey);
//        int returnVal = ;
        if (treeSet.lower(keyVal)==null) {
            throw new NoElementException("No sucessor");
        }else {
            return treeSet.lower(keyVal);
        }
               
        
    }

    @Override
    public Integer sucessor(Integer keyObject) {
        int keyVal = keyObject;
        
        int treeKey;
        
        try {
            treeKey = this.base.sucessor(keyVal);
        }catch (NoElementException e) {
            treeKey = this.base.predcessor(keyVal+1);
        }
        
        TreeSet<Integer> treeSet = treeMap.get(treeKey);
//        int returnVal = ;
        if (treeSet.higher(keyVal)==null) {
            throw new NoElementException("No sucessor");
        }else {
            return treeSet.higher(keyVal);
        }
        
        
    }
    
    @Override
    public String toString() {
        String retString = base.toString() + "\n";
        for(int k : treeMap.keySet()) {
            retString += k + " : " + treeMap.get(k).toString() + "\n";
        }
        return retString;
    }

}
