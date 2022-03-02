package Service;

import DataAccess.AuthTokenDAO;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDAO;
import Model.AuthToken;
import Result.EventResult;

import java.sql.Connection;

/**
 * A class that handles the event request
 */
public class EventService {
    /**
     * An event result
     */
    private EventResult events;

    /**
     * A constructor that takes an authorization token
     * @param req
     */
    public EventService(String req){
        Database db = new Database();
        try{
            Connection conn = ServicePack.createConnection(db);
            AuthTokenDAO authd = new AuthTokenDAO(conn);
            EventDAO ed = new EventDAO(conn);
            AuthToken auth = authd.find(req);
            if(auth != null){
                events = new EventResult(ed.getAllEvents(auth.getUserName()));
            } else {
                throw new DataAccessException("Invalid authorization: You do not have access to this resource");
            }
            ServicePack.closeConnection(db, false);
        } catch (DataAccessException e) {
            events =  new EventResult(e.getMessage());
            try{
                ServicePack.closeConnection(db, false);
            } catch (DataAccessException ex) {
                events =  new EventResult(ex.getMessage());
            }
        }
    }

    /**
     * A function that returns an event result object
     * @return an event result
     */
    public EventResult post(){
        return this.events;
    }
}
