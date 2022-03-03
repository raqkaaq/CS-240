package DataAccess;

import Model.Event;
import Model.Person;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that passes a Person into the database
 */
public class PersonDAO {
    /**
     * An sql connection variable
     */
    private final Connection conn;

    /**
     * A constructor that takes an sql connection
     * @param conn An sql connection variable
     */
    public PersonDAO(Connection conn) {this.conn = conn;}

    /**
     * Inserts a Person into the database
     * @param person A Person object
     * @throws DataAccessException
     */
    public void insert(Person person) throws DataAccessException
    {
        String sql = "INSERT INTO person (personID, associatedUsername, firstName, lastName," +
                " gender, fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e){
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Finds all Person Objects stored with the id personID
     * @param personID an id specifying a Person object
     * @return a person object with the designated person id
     * @throws DataAccessException
     */
    public Person find(String personID) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM person WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("fatherID"), rs.getString("motherID"),
                        rs.getString("spouseID"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /**
     * A function that returns all person objects associated with a given username
     * @param username
     * @return A person list object
     * @throws DataAccessException
     */
    public List<Person> getAllPersons(String username) throws DataAccessException {
        List<Person> persons = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM person WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                if(persons == null)
                    persons = new ArrayList<>();
                Person person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstname"), rs.getString("LastName"), rs.getString("gender"), rs.getString("fatherid"),
                        rs.getString("motherid"), rs.getString("spouseid"));
                persons.add(person);
            }
            if(persons != null)
                return persons;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /**
     * Clears data in person table
     * @return true if successful, else false
     * @throws DataAccessException
     */
    public boolean clear() throws DataAccessException {
       return Database.clear("person", conn);
    }
}
