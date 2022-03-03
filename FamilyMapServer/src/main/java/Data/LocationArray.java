package Data;

//An array of the location data used in full and register
public class LocationArray {
    private LocationData[] data;
    public LocationArray(){
        data = new LocationData[978];
    }

    public LocationData[] getData() {
        return data;
    }
}
