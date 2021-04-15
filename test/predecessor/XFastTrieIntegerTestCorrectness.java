package predecessor;



public class XFastTrieIntegerTestCorrectness extends TestPredecessorInteger{
    
    //In hereited rest of classa nd make new object
    
    @Override public Predecessor<Integer> emptyInstance(int x) {
       return new XFastTreeIntegers(x);
  }

}
