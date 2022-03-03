package Result;

import java.util.Objects;

/**
 * A class that models the login json result
 */
public class LoginResult extends Result{
    /**
     * An auth token
     */
    private String authtoken;
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
        this.authtoken = null;
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
        this.authtoken = authToken;
        this.username = username;
        this.personID = personID;
    }

    public void setAuthToken(String authToken) {
        this.authtoken = authToken;
    }
    public String getAuthToken() {
        return authtoken;
    }

    public String getUsername() {
        return username;
    }

    public String getPersonID() {
        return personID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginResult that = (LoginResult) o;
        return Objects.equals(authtoken, that.authtoken) && Objects.equals(username, that.username) && Objects.equals(personID, that.personID) && Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(authtoken, username, personID);
    }
}
