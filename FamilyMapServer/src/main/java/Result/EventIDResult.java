package Result;

/**
 * A class that models the event id json result
 */
public class EventIDResult extends Result{
    /**
     * A username
     */
    private String username;
    /**
     * An event id
     */
    private String eventID;
    /**
     * A person id
     */
    private String personID;
    /**
     * A latitude
     */
    private float latitude;
    /**
     * A longitude
     */
    private float longitude;
    /**
     * A country
     */
    private String country;
    /**
     * A city
     */
    private String city;
    /**
     * An event type
     */
    private String eventType;
    /**
     * A year
     */
    private int year;

    /**
     * A constructor that creates the success result
     * @param username
     * @param eventID
     * @param personID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     */
    public EventIDResult(String username, String eventID, String personID, float latitude, float longitude, String country, String city, String eventType, int year) {
        super(null, true);
        this.username = username;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    /**
     * A constructor that creates a failed request
     * @param message
     */
    public EventIDResult(String message) {
        super(message, false);
    }

    public String getUsername() {
        return username;
    }

    public String getEventID() {
        return eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public int getYear() {
        return year;
    }
}
