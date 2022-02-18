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
     * A class that returns the clear result
     * @return a clear result object
     */
    public ClearResult clearResult(){
        return clear;
    }
}
