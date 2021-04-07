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
     * Deleting an object not in the key should do nothing
     */
    public void delete(T delObject);
    
    /**
     * 
     * @param keyObject The object to find the previous value of
     * @return the predecssor element before this one. Not Strict (if keyObject is inside return keyObject)
     * If no element is before keyObject throw NoElementException
    */
    public T predcessor(T keyObject);
    
    /**
     * 
       @param keyObject The object to find the previous value of
     * @return the succesor element before this one. Not Strict (if keyObject is inside return keyObject)

     */
    public T sucessor(T keyObject);
    
    
}
