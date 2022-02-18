package DataAccess;

/**
 * A custom exception that assists with accessing the database
 */
public class DataAccessException extends Exception {
    /**
     * A constructor that adds a message to the exception
     * @param message a message
     */
    DataAccessException(String message)
    {
        super(message);
    }

    DataAccessException()
    {
        super();
    }
}