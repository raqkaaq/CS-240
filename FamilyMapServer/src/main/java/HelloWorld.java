import Data.FillData;
import Data.LocationArray;
import Data.LocationData;
import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Model.User;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

public class HelloWorld {
    public static void main(String args[]) throws DataAccessException, SQLException, FileNotFoundException {
        AuthToken authToken = new AuthToken("Hello", "nope");
        Database db = new Database();
        db.openConnection();
        Connection conn = db.getConnection();
        AuthTokenDAO auth = new AuthTokenDAO(conn);
        auth.insert(authToken);
        db.closeConnection(true);
    }
}
