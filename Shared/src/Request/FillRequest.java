package Request;

import java.util.Objects;

/**
 * A class that models the fill json request
 */
public class FillRequest {
    /**
     * A username
     */
    private String username;
    /**
     * The number of generations to fill
     */
    private String generations;

    /**
     * A constructor that creates a FillRequest
     * @param username
     * @param generations
     */
    public FillRequest(String username, String generations) {
        this.username = username;
        this.generations = generations;
    }

    public String getUsername() {
        return username;
    }

    public String getGenerations() {
        return generations;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FillRequest that = (FillRequest) o;
        return Objects.equals(username, that.username) && Objects.equals(generations, that.generations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, generations);
    }
}
