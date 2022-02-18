package Result;

/**
 * A class that models the clear result json result
 */
public class ClearResult extends Result{
    /**
     * A constructor that creates a clear result object
     * @param message
     * @param success
     */
    public ClearResult(String message, boolean success) {
        super(message, success);
    }
}
