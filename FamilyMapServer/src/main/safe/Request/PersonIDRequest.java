package Request;

/**
 * A class that models the person and person idjson request
 */
public class PersonIDRequest {
    /**
     * A person id
     */
    private String personID;
    /**
     * An auth token
     */
    private String authToken;

    /**
     * A constructor that creates a personIDRequest
     * @param personID
     * @param authToken
     */
    public PersonIDRequest(String personID, String authToken) {
        this.personID = personID;
        this.authToken = authToken;
    }

    public String getPersonID() {
        return personID;
    }

    public String getAuthToken() {
        return authToken;
    }
}
