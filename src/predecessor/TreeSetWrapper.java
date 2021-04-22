package predecessor;

import java.util.TreeSet;

public class TreeSetWrapper implements Predecessor<Integer> {

    
    private final TreeSet<Integer> treeSet;
    
    public TreeSetWrapper() {
        this.treeSet  = new TreeSet<Integer>();
    }

    @Override
    public void insert(Integer newObject) {
        this.treeSet.add(newObject);        
    }

    @Override
    public void delete(Integer delObject) {
        this.treeSet.remove(delObject);
    }
    
    @Override
    public boolean contains(Integer keyObject) {
        return this.treeSet.contains(keyObject);
    }

    @Override
    public Integer predcessor(Integer keyObject) {
        return this.treeSet.lower(keyObject);
    }

    @Override
    public Integer sucessor(Integer keyObject) {
        return this.treeSet.higher(keyObject);
    }

    
 
    
    

}
