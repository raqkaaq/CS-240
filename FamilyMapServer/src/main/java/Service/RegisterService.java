package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Person;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResult;
import Data.FillData;

import java.io.FileNotFoundException;
import java.sql.Connection;

/**
 * A class that handles the register requests
 */
public class RegisterService {
    /**
     * A register result
     */
    private RegisterResult register;
    private RegisterRequest req;
    FillData fillData;
    Database db;
    Connection conn;
    UserDAO ud;
    PersonDAO pd;
    EventDAO ed;
    boolean registerSuccess;
    /**
     * A constructor that takes a register request
     * @param req a register request
     */
    public RegisterService(RegisterRequest req) throws DataAccessException {
        this.req = req;
        registerSuccess = true;
        db = new Database();
        try{
            conn = ServicePack.createConnection(db);
            ud = new UserDAO(conn);
            pd = new PersonDAO(conn);
            ed = new EventDAO(conn);
            FillService fill = new FillService();
            fillData = fill.getFillData();
            User user = createUser(req); //create a new user from the register request
            ud.insert(user); //insert the user into the database
            ServicePack.closeConnection(db, true);
        } catch (DataAccessException e) {
            ServicePack.closeConnection(db, false);
            registerSuccess = false;
            register = new RegisterResult(e.getMessage());
        }
    }

    public RegisterService() {}

    public void registerRun(){
        try{
            conn  = ServicePack.createConnection(db);
            ud = new UserDAO(conn);
            pd = new PersonDAO(conn);
            ed = new EventDAO(conn);
            User user = ud.find(req.getUsername()); //get the user from the database
            Person p = new Person(user); //create a new person
            ed.insert(ServicePack.generateUserEvents(user, fillData)); //generate events for the user
            ServicePack.fillGenerations(p, 4, 1980, pd, ed, fillData); //generate generations for the user
            pd.insert(p); //insert the users person into the database
            AuthToken auth = new AuthToken(ServicePack.createRandomString(), user.getUsername()); //create an authtoken
            AuthTokenDAO authd = new AuthTokenDAO(conn);
            authd.insert(auth);
            register = new RegisterResult(auth.getAuthToken(), user.getUsername(), user.getPersonID()); //return relevant data
            ServicePack.closeConnection(db, true);
        } catch (DataAccessException e) {
            register = new RegisterResult(e.getMessage());
            try{
                ServicePack.closeConnection(db, false);
            } catch (DataAccessException ex) {
                register = new RegisterResult(ex.getMessage());
            }
        }
    }

    /**
     * A function that returns a register result
     * @return a register result
     */
    public RegisterResult post(){
        return this.register;
    }

    //creates a user from a request
    public User createUser(RegisterRequest req){
        User user = new User(req.getUsername(), req.getPassword(), req.getEmail(), req.getFirstName(), req.getLastName(), req.getGender(), ServicePack.createRandomString());
        return user;
    }
    //used for debugging to force open the database
    public void openDatabase() throws DataAccessException, FileNotFoundException {
        conn = ServicePack.createConnection(db);
        ud = new UserDAO(conn);
        pd = new PersonDAO(conn);
        ed = new EventDAO(conn);
        fillData = new FillData();
    }
    //used for debugging to force close the database
    public void stopConnection() throws DataAccessException {
        ServicePack.closeConnection(db, true);
    }

    public RegisterResult getRegister() {
        return register;
    }

    public boolean isRegisterSuccess() {
        return registerSuccess;
    }

    public void setRegisterSuccess(boolean registerSuccess) {
        this.registerSuccess = registerSuccess;
    }
}
