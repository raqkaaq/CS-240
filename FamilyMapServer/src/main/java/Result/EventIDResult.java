package Result;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventIDResult that = (EventIDResult) o;
        return Float.compare(that.getLatitude(), getLatitude()) == 0 && Float.compare(that.getLongitude(), getLongitude()) == 0 && getYear() == that.getYear() && Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getEventID(), that.getEventID()) && Objects.equals(getPersonID(), that.getPersonID()) && Objects.equals(getCountry(), that.getCountry()) && Objects.equals(getCity(), that.getCity()) && Objects.equals(getEventType(), that.getEventType()) && Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getEventID(), getPersonID(), getLatitude(), getLongitude(), getCountry(), getCity(), getEventType(), getYear());
    }
}
