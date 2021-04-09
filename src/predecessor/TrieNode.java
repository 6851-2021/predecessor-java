package predecessor;


public class TrieNode<T> {

    //Binary Trie Node. That has 2 children.
    
    //making these public cause why the hell not
    //bad coding practices
    
    private final T value;
//        public final TrieNode parent;
    public T max;
    public T min;


    
    public TrieNode(T val) {
        this.value=val;
    }    
    
    public T getValue() {
        return value;
    }
    
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof TrieNode && this.sameValue((TrieNode)that);
    }
    
    private boolean sameValue(TrieNode that) {
        return this.value.equals(that.value);
    }


    public String toString() {
        return value.toString() + " Max: " + max + " Min: " +min;
    }




}
