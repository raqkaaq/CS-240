package Model;

import java.util.Objects;

/**
 * A class that models the user table in the database
 */
public class User {
    /**
     * A username
     */
    private String username;
    /**
     * A password
     */
    private String password;
    /**
     * An email
     */
    private String email;
    /**
     * A first name
     */
    private String firstName;
    /**
     * A last name
     */
    private String lastName;
    /**
     * A string containing either 'm' or 'f' designating a persons gender
     */
    private String gender;
    /**
     * A person id to connect to the person table
     */
    private String personID;

    /**
     * A constructor that creates a user object
     * @param username
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param gender
     * @param personID
     */
    public User(String username, String password, String email, String firstName, String lastName, String gender, String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return gender.equals(user.gender) && username.equals(user.username) && password.equals(user.password) && email.equals(user.email) && firstName.equals(user.firstName) && lastName.equals(user.lastName) && personID.equals(user.personID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email, firstName, lastName, gender, personID);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(username);
        s.append('\n');
        s.append(password);
        s.append('\n');
        s.append(email);
        s.append('\n');
        s.append(firstName);
        s.append('\n');
        s.append(lastName);
        s.append('\n');
        s.append(gender);
        s.append('\n');
        s.append(personID);
        s.append('\n');
        return s.toString();
    }
}
