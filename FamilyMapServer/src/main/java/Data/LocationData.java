package Data;

import java.util.Objects;
//A mirror of the location.json data format
public class LocationData {
    private String country;
    private String city;
    private float latitude;
    private float longitude;

    public LocationData() {
        this.country = null;
        this.city = null;
        this.latitude = 0;
        this.longitude = 0;
    }

    public LocationData(String country, String city, float latitude, float longitude) {
        this.country = country;
        this.city = city;
        this.latitude = latitude;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationData that = (LocationData) o;
        return Float.compare(that.getLatitude(), getLatitude()) == 0 && Float.compare(that.getLongitude(), getLongitude()) == 0 && Objects.equals(getCountry(), that.getCountry()) && Objects.equals(getCity(), that.getCity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountry(), getCity(), getLatitude(), getLongitude());
    }
}
