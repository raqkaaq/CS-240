package Service;

import DataAccess.*;
import Model.AuthToken;
import Result.PersonResult;

import java.sql.Connection;

/**
 * A class that handles a person request
 */
public class PersonService {
    /**
     * A persons result
     */
    private PersonResult persons;

    /**
     * A constructor that takes a person id request
     * @param req
     */
    public PersonService(String req){
        Database db = new Database();
        try{
            Connection conn = ServicePack.createConnection(db);
            AuthTokenDAO authd = new AuthTokenDAO(conn);
            PersonDAO pd = new PersonDAO(conn);
            AuthToken auth = authd.find(req);
            if(auth != null){
                persons = new PersonResult(pd.getAllPersons(auth.getUserName()));
            } else {
                throw new DataAccessException("error Invalid authorization: You do not have access to this resource");
            }
            ServicePack.closeConnection(db, true);
        } catch (DataAccessException e) {
            persons =  new PersonResult(e.getMessage());
            try{
                ServicePack.closeConnection(db, false);
            } catch (DataAccessException ex) {
                persons =  new PersonResult(ex.getMessage());
            }
        }
    }

    /**
     * A function that returns a person result
     * @return a person result
     */
    public PersonResult post(){
        return this.persons;
    }
}
