package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;

import java.sql.Connection;
import java.util.UUID;

public class ServicePack {
    public static Connection createConnection(Database db) throws DataAccessException {
        db.openConnection();
        return db.getConnection();
    }
    public static void closeConnection(Database db, boolean val) throws DataAccessException {
        db.closeConnection(val);
    }
    public static String createRandomString(){
        return UUID.randomUUID().toString();
    }
}
