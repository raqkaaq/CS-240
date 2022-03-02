package Result;

/**
 * A class that models the fill json result
 */
public class FillResult extends Result{
    @Override
    public String toString() {
        return getMessage() + " " + getSuccess();
    }

    /**
     * A constructor that creates the fill result
     * @param message
     * @param success
     */
    public FillResult(String message, boolean success) {
        super(message, success);
    }
}
