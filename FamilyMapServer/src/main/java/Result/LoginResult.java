package Result;

/**
 * A class that models the login json result
 */
public class LoginResult extends Result{
    /**
     * An auth token
     */
    private String authToken;
    /**
     * A username
     */
    private String username;
    /**
     * A person id
     */
    private String personID;

    /**
     * A constructor that creates a login failed result
     * @param message
     */
    public LoginResult(String message){
        super(message, false);
        this.authToken = null;
        this.username = null;
        this.personID = null;
    }

    /**
     * A constructor that creates a login success result
     * @param authToken
     * @param username
     * @param personID
     */
    public LoginResult(String authToken, String username, String personID) {
        super(null, true);
        this.authToken = authToken;
        this.username = username;
        this.personID = personID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getUsername() {
        return username;
    }

    public String getPersonID() {
        return personID;
    }
}
