package predecessor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author daniel
 *
 * Implements a linked List map that stores a linked list in a hash table
 */
public class LinkedListMap {
    
    //stores the max value allowed in the list
    private final int maxVal;
    //the head and tail node
    private Node head;
    private Node tail;
    //stores a map of node objects
    private Map<Integer, Node> mapList = new HashMap<Integer, Node>();
    
    /**
     * create a new linked list that support values strictly between
     * -1 and maxVal
     * @param maxVal the upper value of integers allowed insided. Not inclusive.
     */
    public LinkedListMap(int maxVal) {
        //this is an UPPER BOUND on the size of the list
        //we can only contain elements with keys from [0, maxVal-1]
        //a total of maxVal elements
        this.maxVal = maxVal;
        
        head = new Node(-1);
        tail = new Node(maxVal);
        head.setNext(tail);
        tail.setPrev(head);
        mapList.put(-1, head);
        mapList.put(maxVal, tail);
    }
    
    /**
     * 
     * @param key the Node to search for. Must be within range 0<= key < maxVal.
     * @return The Node associated with key. If key not in list return Null
     */
    public Node getValue(int key) {
        assert 0 <= key && key < maxVal : "Key:" + key + " is invalid";
        return mapList.get(key);
    }
    
    /**
     * Insert a new node with value val after the node with key
     * 
     * @param key the key of the node to insert after. -1 is allowed as thats the head node.
     * @param val the value of new node to create
     */
    public void insertAfter(int key, int val) {
        //Assert that new key is in range
        assert -1 <= key && key < maxVal : "Key:" + key + " is invalid";
        if(mapList.containsKey(val)) {
            throw new AssertionError("Insert element that already exists| key: " + key + " |value:" + val);
        }
        
        Node newNode = new Node(val);
        Node curNode = mapList.get(key);
        Node nextNode = curNode.getNext();
        
        curNode.setNext(newNode);
        nextNode.setPrev(newNode);
        
        newNode.setPrev(curNode);
        newNode.setNext(nextNode);
        
        mapList.put(val, newNode);
        
        
    }
    
    public void insertBefore(int key, int val) {
      //Assert that new key is in range
        assert 0 <= key && key <= maxVal : "Key:" + key + " is invalid";
        if(mapList.containsKey(val)) {
            throw new AssertionError("Insert element that already exists");
        }
        
        Node newNode = new Node(val);
        Node curNode = mapList.get(key);
        Node prevNode = curNode.getPrev();
        
        curNode.setPrev(newNode);
        prevNode.setNext(newNode);
        
        newNode.setNext(curNode);
        newNode.setPrev(prevNode);
        
        mapList.put(val, newNode);

   }
    
    /**
     * Delete the element with assocaited key (if it does not exist do nothing)
     * @param key key of node to delete
     */
    public void delete(int key) {
        assert 0 <= key && key < maxVal;
        Node deleteNode = mapList.get(key);
        if(deleteNode == null) {
            return; //cant delete an element that does not exist
        }
        Node prevNode = deleteNode.getPrev();
        Node nextNode = deleteNode.getNext();
        
        prevNode.setNext(nextNode);
        nextNode.setPrev(prevNode);
        
        //delet ethe node and set it to be collected by garabage
        deleteNode = null;
        mapList.remove(key);
    }
    
    /**
     * 
     * @param key the Node to search for. Must be within range 0<= key < maxVal.
     * @return The Node associated with key. If key not in list return Null
     */
    public Node getNode(int key){
      //Assert that new key is in range
        assert 0 <= key && key < maxVal : "KeyEror" + key + "MaxValue " + maxVal;
        return mapList.get(key);
    }
    
    @Override public boolean equals(Object obj) {
        return obj instanceof LinkedListMap && this.sameValue((LinkedListMap) obj);
    }

    private boolean sameValue(LinkedListMap that) {
        return this.mapList.equals(that.mapList);
    }
    
    @Override public int hashCode() {
        return this.maxVal;
    }
    
    public String toString() {
        String retString = "";
        Node pointer = head;
        while(pointer != tail) {
            if(pointer == head) {
                retString = "[ Start <-> ";
            }else if (pointer == tail) {
                retString += "End ]";
            }else {
                retString +=  pointer.toString() + " <-> ";
            }
            pointer = pointer.getNext();
            
        }
        retString += "End ]";
        return retString;
        
    }
    
    
}

class Node{
    
    private int value;
    //pointesr to next and preivous ndoe
    private Node  next;
    private Node prev;
    
    public Node(int val) {
        this.value = val;
        this.next = null;
        this.prev = null;
    }
    
    
    public int getValue() {
        return value;
    }
    public Node getNext() {
        return next;
    }
    public Node getPrev() {
        return prev;
    }
    public void setNext(Node next) {
        this.next = next;
    }
    public void setPrev(Node prev) {
        this.prev = prev;
    }
    
    @Override public boolean equals(Object obj) {
        return obj instanceof Node && this.sameValue((Node) obj);
    }

    private boolean sameValue(Node obj) {
        return this.value == obj.value;
    }
    
    @Override public int hashCode() {
        return this.value;
    }
    
    public String toString() {
        return "" +value ;
    }
       
}
