package Data;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FillData {
    List<LocationData> locations;
    List<Names> mnames;
    List<Names> fnames;
    List<Names> snames;
    public FillData() throws FileNotFoundException {
        Gson gson = new Gson();
        locations = new ArrayList<>();
        mnames = new ArrayList<>();
        fnames = new ArrayList<>();
        snames = new ArrayList<>();
        LocationArray temp = gson.fromJson(new FileReader("json/locations.json"), LocationArray.class);
        for (int i = 0; i < 978; i++) {
            locations.add(temp.getData()[i]);
        }
        NameArray nameTemp = new NameArray(144);
        nameTemp = gson.fromJson(new FileReader("json/mnames.json"), NameArray.class);
        for (int i = 0; i < 144; i++) {
            mnames.add(new Names(nameTemp.data[i]));
        }
        nameTemp = new NameArray(147);
        nameTemp = gson.fromJson(new FileReader("json/fnames.json"), NameArray.class);
        for (int i = 0; i < 147; i++) {
            fnames.add(new Names(nameTemp.data[i]));
        }
        nameTemp = new NameArray(152);
        nameTemp = gson.fromJson(new FileReader("json/snames.json"), NameArray.class);
        for (int i = 0; i < 152; i++) {
            snames.add(new Names(nameTemp.data[i]));
        }
    }

    public List<LocationData> getLocations() {
        return locations;
    }

    public List<Names> getMnames() {
        return mnames;
    }

    public List<Names> getFnames() {
        return fnames;
    }

    public List<Names> getSnames() {
        return snames;
    }

    public int sizeLocation(){
        return locations.size();
    }
    public int sizeF(){
        return fnames.size();
    }
    public int sizeM(){
        return mnames.size();
    }
    public int sizeS(){
        return snames.size();
    }
}
