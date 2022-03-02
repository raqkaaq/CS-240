package Service;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;

import java.sql.Connection;
import java.util.List;

/**
 * A class that handles the load requests
 */
public class LoadService {
    /**
     * A load result
     */
    private LoadResult load;

    /**
     * A constructor that takes a load request
     * @param req
     */
    public LoadService(LoadRequest req){
        Database db = new Database();
        try{
            Connection conn = ServicePack.createConnection(db);
            UserDAO ud = new UserDAO(conn);
            PersonDAO pd = new PersonDAO(conn);
            EventDAO ed = new EventDAO(conn);
            List<User> ul = req.getUsers();
            List<Person> pl = req.getPerson();
            List<Event> el = req.getEvents();
            int uc = 0;
            int pc = 0;
            int ec = 0;
            for(int i = 0; i < ul.size(); i++){
                ud.insert(ul.get(i));
                uc = i + 1;
            }
            for(int i = 0; i < pl.size(); i++){
                pd.insert(pl.get(i));
                pc = i + 1;
            }
            for(int i = 0; i < el.size(); i++){
                ed.insert(el.get(i));
                ec = i + 1;
            }
            load = new LoadResult("Successfully added " + uc + " users, " + pc + " persons, and " + ec + " events to the database", true);
            ServicePack.closeConnection(db, true);
        } catch (DataAccessException e) {
            load = new LoadResult(e.getMessage(), false);
            try{
                ServicePack.closeConnection(db, false);
            } catch (DataAccessException ex) {
                load = new LoadResult(ex.getMessage(), false);
            }
        }
    }

    /**
     * A function that returns a load result
     * @return a load result
     */
    public LoadResult post(){
        return this.load;
    }
}
