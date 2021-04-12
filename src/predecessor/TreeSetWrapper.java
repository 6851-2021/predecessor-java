package predecessor;

import java.util.TreeSet;

public class TreeSetWrapper implements Predecessor<Integer> {

    
    private final TreeSet<Integer> treeSet;
    
    public TreeSetWrapper() {
        this.treeSet  = new TreeSet<Integer>();
    }

    
    public void insert(Integer newObject) {
        this.treeSet.add(newObject);        
    }

    public void delete(Integer delObject) {
        this.treeSet.remove(delObject);
        
    }

    public Integer predcessor(Integer keyObject) {
        return this.treeSet.lower(keyObject);
    }

    public Integer sucessor(Integer keyObject) {
        return this.treeSet.higher(keyObject);
    }

    
 
    
    

}
