package Model;

import java.util.Objects;

/**
 * A class that models the AuthToken database table
 */
public class AuthToken {
    /**
     * An authorization toke
     */
    private String authToken;
    /**
     * A username
     */
    private String userName;

    /**
     * A constructor that sets the authToken and username
     * @param authToken An authToken
     * @param userName A username
     */
    public AuthToken(String authToken, String userName) {
        this.authToken = authToken;
        this.userName = userName;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken1 = (AuthToken) o;
        return Objects.equals(authToken, authToken1.authToken) && Objects.equals(userName, authToken1.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authToken, userName);
    }
}
