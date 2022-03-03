package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Request.EventIDRequest;
import Result.EventIDResult;

import java.sql.Connection;
import java.util.Objects;

/**
 * A class that handles the event id request
 */
public class EventIDService {
    /**
     * An event id result
     */
    private EventIDResult event;

    /**
     * A class that takes an event id request
     * @param req
     */
    public EventIDService(EventIDRequest req){
        Database db = new Database();
        try{
            Connection conn = ServicePack.createConnection(db);
            EventDAO pd = new EventDAO(conn);
            AuthTokenDAO authd = new AuthTokenDAO(conn);
            AuthToken auth = authd.find(req.getAuthToken());//verify authentication
            Event eve = pd.find(req.getEventID()); //find the event
            if(auth != null){ //has authorization
                if(eve != null){ //event exists
                    if(Objects.equals(eve.getAssociatedUsername(), auth.getUserName())){
                        event = new EventIDResult(eve.getAssociatedUsername(), eve.getEventID(), eve.getPersonID(), eve.getLatitude(), eve.getLongitude(), eve.getCountry(), eve.getCity(), eve.getEventType(), eve.getYear());
                        ServicePack.closeConnection(db, true); //get the event and close the connection
                    } else {
                        ServicePack.closeConnection(db,false);
                        event = new EventIDResult("error Invalid authorization: You do not have access to this event");
                        //event exists but authorization to it is not allowed due to wrong user
                    }
                } else {
                    ServicePack.closeConnection(db,false);
                    event = new EventIDResult("error Invalid event id");
                    //event does not exist
                }
            } else {
                ServicePack.closeConnection(db,false);
                event = new EventIDResult("error Invalid authorization: You do not have access to this resource");
                //authorization does not exist
            }
        } catch (DataAccessException e) {
            event = new EventIDResult(e.getMessage());
            try{
                ServicePack.closeConnection(db,false);
            } catch (DataAccessException ex) {
                event = new EventIDResult(ex.getMessage());
            }
        }
    }

    /**
     * A class that returns the result
     * @return an event id request
     */
    public EventIDResult post(){
        return this.event;
    }
}
