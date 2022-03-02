package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Result.ClearResult;

/**
 * A class to handle the clear request and return a clear result
 */
public class ClearService {
    private ClearResult clear;

    /**
     * A class that returns the clear result after clearing the database
     * @return a clear result object
     */
    public ClearResult clearTables(){
        Database db = new Database();
        try{
            db.openConnection();
            db.clearTables();
            db.closeConnection(true);
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            clear = new ClearResult("Internal server error", false);
            try{
                db.closeConnection(false);
            } catch (DataAccessException ex) {
                clear = new ClearResult(ex.getMessage(), false);
                return clear;
            }
        }
        clear = new ClearResult("Clear successful", true);
        return clear;
    }
}
