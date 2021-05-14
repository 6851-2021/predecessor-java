package predecessor;

import java.util.Collection;
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

    @Override
    public Integer predcessor(Integer keyObject) {
        return this.treeSet.lower(keyObject);
    }

    @Override
    public Integer sucessor(Integer keyObject) {
        return this.treeSet.higher(keyObject);
    }

    
 
    
    

}
