package Service;

import Data.FillData;
import Data.LocationData;
import Data.Name;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import DataAccess.PersonDAO;
import Model.Event;
import Model.Person;
import Model.User;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class ServicePack {
    public static Connection createConnection(Database db) throws DataAccessException {
        db.openConnection();
        return db.getConnection();
    }
    public static void closeConnection(Database db, boolean val) throws DataAccessException {
        db.closeConnection(val);
    }
    public static String createRandomString(){
        return UUID.randomUUID().toString();
    }
    public static List<Event> generateUserEvents(User user, FillData fillData) {
        List<Event> list = new ArrayList<>();
        LocationData location = getRandomLocation(fillData);
        list.add(new Event(createRandomString(), user.getUsername(), user.getPersonID(), location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(), "birth", 1990));
        location = getRandomLocation(fillData);
        list.add(new Event(createRandomString(), user.getUsername(), user.getPersonID(), location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(), "baptism", 1999));
        return list;
    }
    public static LocationData getRandomLocation(FillData fillData){
        List<LocationData> a = fillData.getLocations();
        int size = a.size();
        int num = getRandomNumber(0,size - 1);
        return a.get(num);
    }
    public static Name getRandomName(String gender, FillData fillData) throws DataAccessException {
        Name name = new Name();

        if(gender.toLowerCase(Locale.ROOT) == "f"){
            name.setFirstName(fillData.getFnames().get(getRandomNumber(0,fillData.sizeF())).getName());
        } else if (gender.toLowerCase(Locale.ROOT) == "m") {
            name.setFirstName(fillData.getMnames().get(getRandomNumber(0,fillData.sizeM())).getName());
        } else {
            throw new DataAccessException("The gender is in the wrong format");
        }
        name.setLastName(fillData.getSnames().get(getRandomNumber(0, fillData.sizeS())).getName());
        return name;
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    public static void fillGenerations(Person orphan, int numGenerations, int birthYear, PersonDAO pd, EventDAO ed, FillData fillData) throws DataAccessException {
        Person father = makeFather(orphan, fillData);
        Person mother = makeMother(orphan, fillData);
        updateSpouse(father, mother, birthYear - 10, ed, fillData);
        fillEvents(mother, birthYear - 30, ed, fillData);
        fillEvents(father, birthYear - 30, ed, fillData);
        numGenerations--;
        if(numGenerations > 0) {
            fillGenerations(father, numGenerations, birthYear - 30, pd, ed, fillData);
            fillGenerations(mother, numGenerations, birthYear - 30, pd, ed, fillData);
        }
        pd.insert(father);
        pd.insert(mother);
    }

    public static Person makeFather(Person orphan, FillData fillData) throws DataAccessException {
        Name name = getRandomName("m", fillData);
        Person father = new Person(createRandomString(), orphan.getAssociatedUsername(), name.getFirstName(), name.getLastName(), "m", "", "", "");
        orphan.setFatherID(father.getPersonID());
        return father;
    }
    public static Person makeMother(Person orphan, FillData fillData) throws DataAccessException {
        Name name = getRandomName("f", fillData);
        Person mother =  new Person(createRandomString(), orphan.getAssociatedUsername(), name.getFirstName(), name.getLastName(), "f", "", "", "");
        orphan.setMotherID(mother.getPersonID());
        return mother;
    }

    public static void updateSpouse(Person father, Person mother, int marriageYear, EventDAO ed, FillData fillData) throws DataAccessException {
        father.setSpouseID(mother.getPersonID());
        mother.setSpouseID(father.getPersonID());
        LocationData marriage = getRandomLocation(fillData);
        Event fatherMarriage = new Event(createRandomString(), father.getAssociatedUsername(), father.getPersonID(), marriage.getLatitude(), marriage.getLongitude(), marriage.getCountry(), marriage.getCity(), "marriage", marriageYear);
        Event motherMarriage = new Event(createRandomString(), mother.getAssociatedUsername(), mother.getPersonID(), marriage.getLatitude(), marriage.getLongitude(), marriage.getCountry(), marriage.getCity(), "marriage", marriageYear);
        ed.insert(fatherMarriage);
        ed.insert(motherMarriage);
    }
    private static void fillEvents(Person orphan, int birthYear, EventDAO ed, FillData fillData) throws DataAccessException {
        LocationData location = getRandomLocation(fillData);
        LocationData location2 = getRandomLocation(fillData);
        Event birth = new Event(createRandomString(), orphan.getAssociatedUsername(), orphan.getPersonID(), location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(), "birth", birthYear);
        Event death = new Event(createRandomString(), orphan.getAssociatedUsername(), orphan.getPersonID(), location2.getLatitude(), location2.getLongitude(), location2.getCountry(), location2.getCity(), "death", birthYear + 60);
        ed.insert(birth);
        ed.insert(death);
    }
}
