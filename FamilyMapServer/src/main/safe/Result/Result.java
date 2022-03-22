package Result;

/**
 * A base class for modeling json results
 */
public class Result {
    /**
     * A message
     */
    private final String message;
    /**
     * A success or failed boolean
     */
    private boolean success;

    /**
     * A constructor to create the result base
     * @param message
     * @param success
     */
    public Result(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }
}
