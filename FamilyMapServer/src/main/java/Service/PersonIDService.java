package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDAO;
import Model.AuthToken;
import Model.Person;
import Request.PersonIDRequest;
import Result.PersonIDResult;

import java.sql.Connection;
import java.util.Objects;

/**
 * A class that handles the person id requests
 */
public class PersonIDService {
    /**
     * A person id result
     */
    private PersonIDResult person;

    /**
     * A constructor that takes a person id request
     * @param req
     */
    public PersonIDService(PersonIDRequest req){
        Database db = new Database();
        try{
            Connection conn = ServicePack.createConnection(db);
            PersonDAO pd = new PersonDAO(conn);
            AuthTokenDAO authd = new AuthTokenDAO(conn);
            AuthToken auth = authd.find(req.getAuthToken());
            Person pers = pd.find(req.getPersonID());
            if(auth != null){ //check authorization
                if(pers != null){ //person exists
                    if(Objects.equals(pers.getAssociatedUsername(), auth.getUserName())){ //person exists and usernames correspond
                        person = new PersonIDResult(pers.getAssociatedUsername(), pers.getPersonID(), pers.getFirstName(), pers.getLastName(), pers.getGender(), pers.getFatherID(), pers.getMotherID(), pers.getSpouseID());
                        ServicePack.closeConnection(db, true);
                    } else { //person exists but not authorized
                        ServicePack.closeConnection(db,false);
                        person = new PersonIDResult("error Invalid authorization: You do not have access to this person");
                    }
                } else { //person does not exist
                    ServicePack.closeConnection(db,false);
                    person = new PersonIDResult("error Invalid person id");
                }
            } else { //authorization does not exist
                ServicePack.closeConnection(db,false);
                person = new PersonIDResult("error Invalid authorization: You do not have access to this resource");
            }
        } catch (DataAccessException e) {
            person = new PersonIDResult(e.getMessage());
            try{
                ServicePack.closeConnection(db,false);
            } catch (DataAccessException ex) {
                person = new PersonIDResult(ex.getMessage());
            }
        }
    }

    /**
     * A person id result returner
     * @return a person id result
     */
    public PersonIDResult post(){
        return this.person;
    }
}
