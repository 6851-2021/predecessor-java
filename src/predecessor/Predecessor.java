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
     * Check if a given object in the set. Return true if it is. False Otherwise
     * 
     * @param keyObject Object to check if its in the structure
     * @return True if objects inside, False otheriwse
     */
    public boolean contains(T keyObject);
    
    /**
     * 
     * @param keyObject The object to find the previous value of
     * @return the predecssor element before this one. Returns Strict Predecessor. (Does not return itself)
     * If no element raise NoElementException
    */
    public T predcessor(T keyObject);
    
    /**
     * 
       @param keyObject The object to find the previous value of
     * @return the succesor element before this one. Returns strict sucessor. (Does not return itself).
     * If no element raise NoElementException

     */
    public T sucessor(T keyObject);
    
    
}
