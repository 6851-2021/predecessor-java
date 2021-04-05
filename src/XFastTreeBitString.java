import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XFastTreeBitString implements XTrieCodeSpec<String> {

    
    
    //im ging to test this with bit strings 
    //Aka we inout a string of 01 values that correspond to the path. Yes i know its dumb but should be a good test
    
    
    //max bits
    private final int maxBits;
    
    
    //wait im just using strings this is dumb. Dont need a size map.
    private final Map<String, String> hashMap = new HashMap<String, String>();
    
    
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
        
        //insert the string object into the hash table
        int i = newObject.length();
        while(i>=0) {
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

        // TODO Auto-generated method stub
        
    }

    @Override
    public String predcessor(String keyObject) {
        assert keyObject.length()==maxBits;

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String sucessor(String keyObject) {
        assert keyObject.length()==maxBits;

        // TODO Auto-generated method stub
        return null;
    }

}
