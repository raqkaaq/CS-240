package Data;
//An array for storing the name data
public class NameArray {
    String[] data;

    public NameArray(int size) {
        data = new String[size];
    }

    public String[] getNames() {
        return data;
    }
}
