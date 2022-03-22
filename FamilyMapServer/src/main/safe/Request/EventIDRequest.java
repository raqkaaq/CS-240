package Request;

/**
 * A class that models the event ID json request
 */
public class EventIDRequest {
    /**
     * An event id
     */
    private String eventID;
    /**
     * An authorization token
     */
    private String authToken;

    /**
     * A constructor that creates and eventIDRequest with eventID and authToken
     * @param eventID
     * @param authToken
     */
    public EventIDRequest(String eventID, String authToken) {
        this.eventID = eventID;
        this.authToken = authToken;
    }

    public String getEventID() {
        return eventID;
    }

    public String getAuthToken() {
        return authToken;
    }
}
