package predecessor;

import java.util.HashMap;
import java.util.Map;

public class LinkedListMap {
//    private Map<>
    
    private final int maxVal;
    private Node head;
    private Node tail;
    
    private Map<Integer, Node> mapList = new HashMap<Integer, Node>();
    
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
    
    //insert a new node after a given one
    public void insertAfter(int key, int val) {
        //Assert that new key is in range
        assert -1 <= key && key < maxVal : "Key:" + key + " is invalid";
        
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
        
        Node newNode = new Node(val);
        Node curNode = mapList.get(key);
        Node prevNode = curNode.getPrev();
        
        curNode.setPrev(newNode);
        prevNode.setNext(newNode);
        
        newNode.setNext(curNode);
        newNode.setPrev(prevNode);
        
        mapList.put(val, newNode);

   }
    
    public void delete(int key) {
        assert 0 <= key && key < maxVal;
        Node deleteNode = mapList.get(key);
        Node prevNode = deleteNode.getPrev();
        Node nextNode = deleteNode.getNext();
        
        prevNode.setNext(nextNode);
        nextNode.setPrev(prevNode);
        
        //delet ethe node and set it to be collected by garabage
        deleteNode = null;
        mapList.remove(key);
    }
    
    public Node getNode(int key){
      //Assert that new key is in range
        assert 0 <= key && key < maxVal;
        return mapList.get(key);
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
    
    public String toString() {
        return "" +value ;
    }
    
    
    
}
