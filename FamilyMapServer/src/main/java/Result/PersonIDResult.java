package Result;

import java.util.Objects;

/**
 * A class that models the person id json result
 */
public class PersonIDResult extends Result{
    /**
     * A username
     */
    private String associatedUsername;
    /**
     * A person id
     */
    private String personID;
    /**
     * A first name
     */
    private String firstName;
    /**
     * A last name
     */
    private String lastName;
    /**
     *  A gender
     */
    private String gender;
    /**
     * A father
     */
    private String father;
    /**
     * A mother
     */
    private String mother;
    /**
     * A spouse
     */
    private String spouse;

    /**
     * A constructor that creates a personID request
     * @param username
     * @param personID
     * @param firstName
     * @param lastName
     * @param gender
     * @param father can be null
     * @param mother can be null
     * @param spouse can be null
     */
    public PersonIDResult(String username, String personID, String firstName, String lastName, String gender, String father, String mother, String spouse) {
        super(null, true);
        this.associatedUsername = username;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
    }

    public PersonIDResult(String message) {
        super(message, false);
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getPersonID() {
        return personID;
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

    public String getFather() {
        return father;
    }

    public String getMother() {
        return mother;
    }

    public String getSpouse() {
        return spouse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonIDResult that = (PersonIDResult) o;
        return Objects.equals(associatedUsername, that.associatedUsername) && Objects.equals(personID, that.personID) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(gender, that.gender) && Objects.equals(father, that.father) && Objects.equals(mother, that.mother) && Objects.equals(spouse, that.spouse) && Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(associatedUsername, personID, firstName, lastName, gender, father, mother, spouse);
    }
}
