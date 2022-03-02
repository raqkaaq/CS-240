package Service;

import Data.FillData;
import Data.LocationData;
import Data.Name;
import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.FillRequest;
import Result.FillResult;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * A class that handles a fill request
 */
public class FillService {
    /**
     * A fill result
     */
    private FillResult fill;
    private FillRequest filled;
    private FillData fillData;
    UserDAO ud;
    PersonDAO pd;
    EventDAO ed;
    Database db;

    /**
     * A constructor that takes a fill request
     * @param filled
     */
    public FillService(FillRequest filled) {
        try {
            this.filled = filled;
            db = new Database();
            Connection conn = ServicePack.createConnection(db);
            ud = new UserDAO(conn);
            pd = new PersonDAO(conn);
            ed = new EventDAO(conn);
            fillData = new FillData();
        } catch (FileNotFoundException e) {
            fill = new FillResult("Internal Server Error", false);
        } catch (DataAccessException e) {
            fill = new FillResult("Internal Server Error", false);
        }
    }
    public void fillRun(){
        try{
            User user = ud.find(filled.getUsername());
            int generations = 0;
            if(filled.getGenerations() == null) {
                generations = 4;
            } else {
                generations = Integer.parseInt(filled.getGenerations());
            }
            if((generations < 5 && generations > 0)){
                if(user != null){
                    ud.insert(remake(user));//deletes person id of the user, and other pertinent information
                    Person p = makePerson(user);
                    pd.insert(p); // Inserts a new person with the new person id
                    ed.insert(generateUserEvents(user));
                    if(generations > 0)
                        fillGenerations(p, generations, 1990); //birth year is 1990, maybe change this to a select later
                    double g = (double) generations;
                    double people = (double) (Math.pow(2.0,(generations + 1.0)) - 1.0);
                    int numPeople = (int) people;
                    int numEvents = 3 * numPeople;
                    fill = new FillResult("Successfully added " + numPeople + " persons and " + numEvents + " events to the database", true);

                } else {
                    fill = new FillResult("Invalid username", false);
                }
            } else {
                fill = new FillResult("Invalid generations parameter", false);
            }


        } catch (DataAccessException e) {
            fill = new FillResult(e.getMessage(), false);
            try{
                ServicePack.closeConnection(db, false);
            } catch (DataAccessException ex) {
                fill = new FillResult(ex.getMessage(), false);
            }
        }
    }

    /**
     * A function that returns the fill result
     * @return a fill result
     */
    public FillResult post(){
        return this.fill;
    }
    private void fillGenerations(Person orphan, int numGenerations, int birthYear) throws DataAccessException {
        Person father = makeFather(orphan);
        Person mother = makeMother(orphan);
        updateSpouse(father, mother, birthYear - 21);
        fillEvents(mother, birthYear - 42);
        fillEvents(father, birthYear - 42);
        pd.insert(father);
        pd.insert(mother);
        numGenerations--;
        fillGenerations(father, numGenerations, birthYear - 42);
        fillGenerations(mother, numGenerations, birthYear - 42);
    }

    public Person makeFather(Person orphan) throws DataAccessException {
        Name name = getRandomName("m");
        return new Person(UUID.randomUUID().toString(), orphan.getAssociatedUsername(), name.getFirstName(), name.getLastName(), "m", "", "", "");
    }
    public Person makeMother(Person orphan) throws DataAccessException {
        Name name = getRandomName("f");
        return new Person(UUID.randomUUID().toString(), orphan.getAssociatedUsername(), name.getFirstName(), name.getLastName(), "f", "", "", "");
    }

    public void updateSpouse(Person father, Person mother, int marriageYear) throws DataAccessException {
        father.setSpouseID(mother.getPersonID());
        mother.setSpouseID(father.getPersonID());
        LocationData marriage = getRandomLocation();
        Event fatherMarriage = new Event(UUID.randomUUID().toString(), father.getAssociatedUsername(), father.getPersonID(), marriage.getLatitude(), marriage.getLongitude(), marriage.getCountry(), marriage.getCity(), "marriage", marriageYear);
        Event motherMarriage = new Event(UUID.randomUUID().toString(), mother.getAssociatedUsername(), mother.getPersonID(), marriage.getLatitude(), marriage.getLongitude(), marriage.getCountry(), marriage.getCity(), "marriage", marriageYear);
        ed.insert(fatherMarriage);
        ed.insert(motherMarriage);
    }
    private void fillEvents(Person orphan, int birthYear) throws DataAccessException {
        LocationData location = getRandomLocation();
        LocationData location2 = getRandomLocation();
        Event birth = new Event(UUID.randomUUID().toString(), orphan.getAssociatedUsername(), orphan.getPersonID(), location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(), "birth", birthYear);
        Event death = new Event(UUID.randomUUID().toString(), orphan.getAssociatedUsername(), orphan.getPersonID(), location2.getLatitude(), location2.getLongitude(), location2.getCountry(), location2.getCity(), "death", birthYear + 60);
        ed.insert(birth);
        ed.insert(death);
    }

    public User remake(User user) throws DataAccessException {
        user.setPersonID(UUID.randomUUID().toString());
        ud.deleteUser(user.getUsername());
        return user;
    }
    public Person makePerson(User user){
        return new Person(user);
    }
    public List<Event> generateUserEvents(User user) {
        List<Event> list = new ArrayList<>();
        LocationData location = getRandomLocation();
        list.add(new Event(UUID.randomUUID().toString(), user.getUsername(), user.getPersonID(), location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(), "birth", 1990));
        location = getRandomLocation();
        list.add(new Event(UUID.randomUUID().toString(), user.getUsername(), user.getPersonID(), location.getLatitude(), location.getLongitude(), location.getCountry(), location.getCity(), "baptism", 1999));
        return list;
    }
    public LocationData getRandomLocation(){
        List<LocationData> a = fillData.getLocations();
        int size = a.size();
        int num = getRandomNumber(0,size - 1);
        return a.get(num);
    }
    public Name getRandomName(String gender) throws DataAccessException {
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

    public FillResult getFill() {
        return fill;
    }
}
