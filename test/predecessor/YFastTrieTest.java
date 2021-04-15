package predecessor;


public class YFastTrieTest extends TestPredecessorInteger{
    @Override public Predecessor<Integer> emptyInstance(int x) {
        return new YFastTrie(x);
   }
}
