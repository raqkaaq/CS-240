package Service;

import Data.FillData;
import DataAccess.*;
import Model.Person;
import Model.User;
import Request.FillRequest;
import Result.FillResult;

import java.io.FileNotFoundException;
import java.sql.Connection;

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
    Connection conn;

    public FillService() {
        try{
            fillData = new FillData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * A constructor that takes a fill request
     * @param filled
     */
    public FillService(FillRequest filled) {
        try {
            this.filled = filled;
            db = new Database();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void fillRun(){
        try{
            conn = ServicePack.createConnection(db);
            ud = new UserDAO(conn);
            pd = new PersonDAO(conn);
            ed = new EventDAO(conn);
            fillData = new FillData();
            User user = ud.find(filled.getUsername()); //finds the user
            int generations = 0;
            if(filled.getGenerations() == null) { //parse the generations parameter
                generations = 4;
            } else {
                try {
                    generations = Integer.parseInt(filled.getGenerations());
                } catch (NumberFormatException e) {
                    throw new DataAccessException("Invalid generations parameter");
                }
            }
            if((generations <= 5 && generations > 0)){
                if(user != null){ //if user exists
                    User use = remake(user); //add a new personid to the user
                    ud.insert(use);//deletes person id of the user, and other pertinent information
                    Person p = makePerson(use); //makes a corresponding person to the user
                    ed.insert(ServicePack.generateUserEvents(use, fillData)); //generates default user data
                    if(generations > 0) //fills in the family data for generations
                        ServicePack.fillGenerations(p, generations, 1990, pd, ed, fillData); //birth year is 1990, maybe change this to a select later
                    pd.insert(p); // Inserts a new person with the new person id
                    double g = (double) generations;
                    double people = (double) (Math.pow(2.0,(generations + 1.0)) - 1.0); //gets the number of people 2^(generations +1) - 1
                    int numPeople = (int) people;
                    int numEvents = 3 * numPeople; //since adding 3 events per person
                    ServicePack.closeConnection(db, true);
                    fill = new FillResult("Successfully added " + numPeople + " persons and " + numEvents + " events to the database", true);

                } else {
                    ServicePack.closeConnection(db, false);
                    fill = new FillResult("Invalid username", false);
                }
            } else {
                ServicePack.closeConnection(db, false);
                fill = new FillResult("Invalid generations parameter", false);
            }


        } catch (DataAccessException e) {
            fill = new FillResult(e.getMessage(), false);
            try{
                ServicePack.closeConnection(db, false);
            } catch (DataAccessException ex) {
                fill = new FillResult(ex.getMessage(), false);
            }
        } catch (FileNotFoundException e) {
            fill = new FillResult("Internal Server Error", false);
        }
    }

    /**
     * A function that returns the fill result
     * @return a fill result
     */
    public FillResult post(){
        return this.fill;
    }

    public User remake(User user) throws DataAccessException {
        user.setPersonID(ServicePack.createRandomString());
        ud.deleteUser(user.getUsername());
        return user;
    }
    public Person makePerson(User user){
        return new Person(user);
    }

    public FillResult getFill() {
        return fill;
    }
    public void closeDatabase() throws DataAccessException {
        ServicePack.closeConnection(db, true);
    }
    public void openDatabase() throws DataAccessException, FileNotFoundException {
        conn = ServicePack.createConnection(db);
        ud = new UserDAO(conn);
        pd = new PersonDAO(conn);
        ed = new EventDAO(conn);
        fillData = new FillData();
    }

    public FillData getFillData() {
        return fillData;
    }

    public EventDAO getEd() {
        return ed;
    }
}
