package Request;

import java.util.Objects;

/**
 * A class that models the json register request
 */
public class RegisterRequest {
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
     * A string with values 'm' or 'f' that designates gender
     */
    private String gender;

    /**
     * A constructor that creates a class that models the register json request
     * @param username
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param gender
     */
    public RegisterRequest(String username, String password, String email, String firstName, String lastName, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public String getGender() {
        return gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterRequest that = (RegisterRequest) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(email, that.email) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(gender, that.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email, firstName, lastName, gender);
    }
}
