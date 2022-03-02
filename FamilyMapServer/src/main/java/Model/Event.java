package Model;

import Data.FillData;
import Data.LocationArray;
import Data.LocationData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A class that models the event table in the database
 */
public class Event {
    /**
     * An event id
     */
    private String eventID;
    /**
     * A username
     */
    private String username;
    /**
     * A personID
     */
    private String personID;
    /**
     * A latitude value
     */
    private float latitude;
    /**
     * A longitude value
     */
    private float longitude;
    /**
     * A string representing the events country
     */
    private String country;
    /**
     * The events city
     */
    private String city;
    /**
     * The events type
     */
    private String eventType;
    /**
     * The events year
     */
    private int year;


    /**
     * A constructor that creates an event value
     * @param eventID
     * @param username
     * @param personID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     */
    public Event(String eventID, String username, String personID, float latitude, float longitude, String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.username = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Float.compare(event.latitude, latitude) == 0 && Float.compare(event.longitude, longitude) == 0 && year == event.year && Objects.equals(eventID, event.eventID) && Objects.equals(username, event.username) && Objects.equals(personID, event.personID) && Objects.equals(country, event.country) && Objects.equals(city, event.city)&& Objects.equals(eventType, event.eventType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventID, username, personID, latitude, longitude, country, eventType, year);
    }
}
