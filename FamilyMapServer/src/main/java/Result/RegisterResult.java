package Result;

/**
 * A class that models a register json result
 */
public class RegisterResult extends Result{
    /**
     * An authtoken
     */
    private final String authtoken;
    /**
     * A username
     */
    private final String username;
    /**
     * A person id
     */
    private final String personID;

    /**
     * A constructor that creates a successful result
     * @param authToken
     * @param username
     * @param personId
     */
    public RegisterResult(String authToken, String username, String personId) {
        super(null, true);
        this.authtoken = authToken;
        this.username = username;
        this.personID = personId;
    }

    /**
     * A class that creates a failed result
     * @param message
     */
    public RegisterResult(String message){
        super(message, false);
        this.authtoken = null;
        this.username = null;
        this.personID = null;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public String getUsername() {
        return username;
    }

    public String getPersonID() {
        return personID;
    }

    @Override
    public String toString() {
        return "RegisterResult{" +
                "authToken='" + authtoken + '\'' +
                ", username='" + username + '\'' +
                ", personId='" + personID + '\'' +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}
