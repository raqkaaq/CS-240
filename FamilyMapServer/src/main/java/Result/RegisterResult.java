package Result;

/**
 * A class that models a register json result
 */
public class RegisterResult extends Result{
    /**
     * An authtoken
     */
    private final String authToken;
    /**
     * A username
     */
    private final String username;
    /**
     * A person id
     */
    private final String personId;

    /**
     * A constructor that creates a successful result
     * @param authToken
     * @param username
     * @param personId
     */
    public RegisterResult(String authToken, String username, String personId) {
        super(null, true);
        this.authToken = authToken;
        this.username = username;
        this.personId = personId;
    }

    /**
     * A class that creates a failed result
     * @param message
     */
    public RegisterResult(String message){
        super(message, false);
        this.authToken = null;
        this.username = null;
        this.personId = null;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getUsername() {
        return username;
    }

    public String getPersonId() {
        return personId;
    }
}
