package predecessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class XFastTreeBitString implements Predecessor<String> {

    
    
    //im ging to test this with bit strings 
    //Aka we inout a string of 01 values that correspond to the path. Yes i know its dumb but should be a good test
    
    
    //max bits
    private final int maxBits;
    
    
    //wait im just using strings this is dumb. Dont need a size map.
    private final Map<String, String> hashMap = new HashMap<String, String>();
    //store the hash set of the final elements in the tree.
    private final Set<String> finalLevel = new HashSet<String>();
    
    /**
     * Make a new XFastTreeBitString 
     * @param maxBits
     */
    public XFastTreeBitString(int maxBits) {
        this.maxBits=maxBits;
        hashMap.clear();
    }
    
    @Override
    public void insert(String newObject) {
        //our new string must have proper bits
        assert newObject.length()==maxBits;
        finalLevel.add(newObject);
        //insert the string object into the hash table
        int i = newObject.length();
        while(i>0) {
            final String subStr = newObject.substring(0, i);
            if(hashMap.containsKey(subStr)) {
                break;
            }else {
                hashMap.put(subStr, subStr);
                i = i-1;
            }
        }
    }

    @Override
    public void delete(String delObject) {
        assert delObject.length()==maxBits;
        
        finalLevel.remove(delObject);
        
        int i = delObject.length();
        while (i>0) {
            final String subStr = delObject.substring(0, i);
            if(hashMap.containsKey(subStr)) {
                hashMap.remove(subStr);
                //check final bit check
                final char lastChar = subStr.charAt(i-1);
                final char otherChar = (lastChar=='1') ? '0' : '1';
                final String checkString = subStr.substring(0, i-1) + otherChar;
                
                if (hashMap.containsKey(checkString)) {
                    break;
                }
                else {
                    i = i -1;
                }
                
                
            }

        }

        // TODO Auto-generated method stub
        
    }

    @Override
    public String predcessor(String keyObject) {
        assert keyObject.length()==maxBits;

        int i = keyObject.length();
        //work on this later
        while(i>0) {
            final String subStr = keyObject.substring(0,i);
            final char lastChar = subStr.charAt(i-1);
            ;
        }
        
        int j = i;
        while (j < keyObject.length()) {
            ;
        }
        
        return null;
    }

    @Override
    public String sucessor(String keyObject) {
        assert keyObject.length()==maxBits;

        // TODO Auto-generated method stub
        return null;
    }

}
