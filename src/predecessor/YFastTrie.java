package predecessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
     * Merge the trees associated with parameters x and y
     * If resulting tree has more than max size length
     * Updates the map and rep values in the xFastTrie
     * Tree should be next to each other
     * 
     * @param x
     * @param y
     */
    private void mergeTrees(int x, int y) {
        
        if( treeMap.get(x).size() >= this.minTreeSize &&
            treeMap.get(y).size() >= this.minTreeSize &&
            this.treeMap.size()>1) {
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
     * Split the tree associated with parameter z into 2 smaller trees
     * Does nothing if the tree associated with z is not above the max size
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
            treeMap.remove(z);
            //use final element of smaller set as its representative
            treeMap.put(smallerSet.last(), smallerSet);
            //use the old larger representative for the larger set
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
            treeKey = this.base.successor(newKey-1);
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
            treeKey = base.successor(delKey-1);
        } catch(NoElementException e) {
            //its not in the BST do nothing
            return;
        }
        TreeSet<Integer> treeSet = treeMap.get(treeKey);
        treeSet.remove(delKey);
        
        
        try {
            //merge with sucessor
            int nextTreeKey = base.successor(treeKey);
            this.mergeTrees(treeKey, nextTreeKey);
            
        } catch (NoElementException e) {
            try {
                //memrge with predecessor
                int prevTreeKey = base.predecessor(treeKey);
                this.mergeTrees(treeKey, prevTreeKey);
            }
            catch(NoElementException ee) {
                //this is only tree so no need to merge
                return;
                
            }
        }
        
        
    }

    @Override
    public boolean contains(Integer keyObject) {
        int keyVal = keyObject;
//        TreeSet<Integer> bothSets = new TreeSet<Integer>();
        //Store the given trees
        List<TreeSet<Integer>> treeSets = new ArrayList<TreeSet<Integer>>();

        int treeKey1;
        int treeKey2;
        
        try {
            treeKey1 = this.base.predecessor(keyVal+1);
            treeSets.add(treeMap.get(treeKey1));
//            bothSets.addAll(treeMap.get(treeKey1));
        }catch (NoElementException e) {
            
        }try {
            treeKey2 =  this.base.successor(keyVal);
//            bothSets.addAll(treeMap.get(treeKey2));
            treeSets.add(treeMap.get(treeKey2));
        } catch (NoElementException e)  {
            
        }
        
        for (TreeSet<Integer> ts : treeSets) {
            if(ts.contains(keyObject)) {
                return true;
            }
        }

        return false;
    }
    
    @Override
    public Integer predecessor(Integer keyObject) {
        int keyVal = keyObject;
        
        int treeKey1;
        int treeKey2;
        
//        TreeSet<Integer> bothSets = new TreeSet<Integer>();
        List<TreeSet<Integer>> treeSets = new ArrayList<TreeSet<Integer>>();
        
        try {
            treeKey1 = this.base.predecessor(keyVal+1);
            treeSets.add(treeMap.get(treeKey1));
            
//            bothSets.addAll(treeMap.get(treeKey1));
            int treeKey3 = this.base.predecessor(treeKey1);
            treeSets.add(treeMap.get(treeKey3));
//            bothSets.addAll(treeMap.get(treeKey3));
        }catch (NoElementException e) {
            
        }try {
            treeKey2 =  this.base.successor(keyVal);
            treeSets.add(treeMap.get(treeKey2));
//            bothSets.addAll(treeMap.get(treeKey2));
        } catch (NoElementException e)  {
            
        }

        //query given trees
        int retVal = -1;
        for (TreeSet<Integer> ts : treeSets) {
            if(ts.lower(keyVal)!= null) {
                retVal = Math.max(retVal, ts.lower(keyVal));
            }
        }
        
        
        if (retVal==-1) {
            throw new NoElementException("No sucessor");
        }else {
            return retVal;
        }
               
        
    }

    @Override
    public Integer successor(Integer keyObject) {
        int keyVal = keyObject;
        
//        int treeKey;
        
        int treeKey1;
        int treeKey2;
        
//        TreeSet<Integer> bothSets = new TreeSet<Integer>();
        List<TreeSet<Integer>> treeSets = new ArrayList<TreeSet<Integer>>();

        
        try {
            treeKey1 = this.base.predecessor(keyVal+1);
            treeSets.add(treeMap.get(treeKey1));

//            bothSets.addAll(treeMap.get(treeKey1));
        }catch (NoElementException e) {
            
        }try {
            treeKey2 =  this.base.successor(keyVal);
            treeSets.add(treeMap.get(treeKey2));

//            bothSets.addAll(treeMap.get(treeKey2));
            int treeKey3 = this.base.successor(treeKey2);
            treeSets.add(treeMap.get(treeKey3));

//            bothSets.addAll(treeMap.get(treeKey3));
        } catch (NoElementException e)  {
            
        }
        
         //query given trees
        int retVal = (int)Math.pow(2, this.maxBits);
        for (TreeSet<Integer> ts : treeSets) {
            if(ts.higher(keyVal)!= null) {
                retVal = Math.min(retVal, ts.higher(keyVal));
            }
        }
        
        
        if (retVal==(int)Math.pow(2, this.maxBits)) {
            throw new NoElementException("No sucessor");
        }else {
            return retVal;
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
        return obj instanceof YFastTrie && this.sameValue((YFastTrie) obj);
    }

    private boolean sameValue(YFastTrie obj) {
        Set<Integer> allValues = new HashSet<Integer>();
        Set<Integer> theirValues = new HashSet<Integer>();
        for(int i : this.treeMap.keySet()) {
            allValues.addAll(this.treeMap.get(i));
            theirValues.addAll(obj.treeMap.get(i));
        }
        
        
        return this.base.equals(obj.base) &&
                this.treeMap.keySet().equals(obj.treeMap.keySet()) &&
                allValues.equals(theirValues);
    }
    
    @Override public int hashCode() {
        return this.maxBits;
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
