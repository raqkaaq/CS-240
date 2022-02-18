package Result;

/**
 * A class that models the person id json result
 */
public class PersonIDResult extends Result{
    /**
     * A username
     */
    private String username;
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
        this.username = username;
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

    public String getUsername() {
        return username;
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
}
