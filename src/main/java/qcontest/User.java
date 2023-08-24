package qcontest;

/**
 * The User class represents a registered user on the QContest platform.
 * Each user has a unique ID, a username, and a score associated with them.
 */
public class User {
    private static int nextUserId = 1;
    private int id;          // Unique user ID
    private int score;       // User's score
    private String username; // Username of the user

    /**
     * Constructs a new User with the provided username and a default score of 1000.
     *
     * @param username The username of the user.
     */
    public User(String username) {
        this.id = nextUserId++;
        this.username = username;
        this.score = 1000; // Default score
    }

    /**
     * Retrieves the user's ID.
     *
     * @return The unique ID of the user.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the user's username.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the user's score.
     *
     * @return The current score of the user.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the user's score to the provided value.
     *
     * @param score The new score to be set for the user.
     */
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "User[id=" + getId() + "]";
    }
}
