import DataAccess.*;
import Model.AuthToken;
import Model.Person;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelloWorld {
    public static void main(String args[]) throws DataAccessException, SQLException {
        System.out.println("Hello world!");
        System.out.println("You can delete this file.");
        Database data = new Database();
        data.openConnection();
        Connection s = data.getConnection();
        UserDAO a = new UserDAO(s);
        User p = new User("1234", "raqkaaq", "Raqkaaq", "Raqkaaq", "m", "3245", "2342");
        a.insert(p);
        User w = a.find("1234");
        boolean b = a.clear();
        data.closeConnection(false);
    }
}
