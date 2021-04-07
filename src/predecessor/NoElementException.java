package predecessor;


public class NoElementException extends RuntimeException{
    /**
     * Make a new interval conflict exception with the given detail message.
     * 
     * @param message the detail message
     */
    public NoElementException(String message) {
        super(message);
    }
}
