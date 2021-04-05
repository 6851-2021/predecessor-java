package predecessor;


public interface Predecessor<T> {

    
    // Type T must be well defined with a compareTo Function.
    // Given two elements a,b of Type T that are not equal one must state that a>b or b>a.
    // additonaly elements of Type T must be decoable into a BIT value of a fixed size.
    
    /**
     * 
     * @param newObject The new object to add to our X Trie
     */
    public void insert(T newObject);
    
    /**
     * 
     * @param delObject The object in the tree to delete
     * If object is not found raise a ElementNotFoundException
     */
    public void delete(T delObject);
    
    /**
     * 
     * @param keyObject The object to find the previous value of
     * @return the predecssor element before this one. If there is no predecessor return itself
     */
    public T predcessor(T keyObject);
    
    /**
     * 
       @param keyObject The object to find the previous value of
     * @return the succesor element before this one. If there is no succesor return itself

     */
    public T sucessor(T keyObject);
    
    
}
