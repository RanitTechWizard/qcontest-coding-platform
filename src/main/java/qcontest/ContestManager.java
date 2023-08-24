package qcontest;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

/**
 * The ContestManager class manages the contests available on the QContest platform.
 * It allows adding contests, listing contests, and filtering contests by difficulty level.
 */
public class ContestManager {
    private static final List<Contest> contests = new ArrayList<>(); // List to manage contests

    /**
     * Adds a new contest to the list of contests.
     *
     * @param title The title of the contest.
     * @param level The difficulty level of the contest (LOW, MEDIUM, HIGH).
     * @param creator The creator of the contest.
     * @param numQuestions The number of questions for the contest.
     * @param questionManager The QuestionManager instance to retrieve questions.
     */
    public static Contest addContest(String title, DifficultyLevel level, User creator, int numQuestions, QuestionManager questionManager) {
        Contest contest = new Contest(title, level, creator, numQuestions, questionManager);
        contests.add(contest);
        return contest;
    }

    /**
     * Lists all available contests.
     *
     * @return A list of all contests.
     */
    public static List<Contest> listAllContests() {
        return new ArrayList<>(contests);
    }

    /**
     * Lists contests based on the specified difficulty level.
     *
     * @param level The difficulty level of contests to be listed.
     * @return A list of contests with the specified difficulty level.
     */
    public static List<Contest> listContestsByDifficulty(DifficultyLevel level) {
        List<Contest> filteredContests = new ArrayList<>();
        for (Contest contest : contests) {
            if (contest.getLevel() == level) {
                filteredContests.add(contest);
            }
        }
        return filteredContests;
    }
    /**
     * Retrieves a list of contestants for a given contest ID in descending order of their current contest points.
     *
     * @param contestId The ID of the contest for which contestants are to be retrieved.
     * @return A list of Contestant objects in descending order based on their current contest points.
     *         Returns null if the contest with the specified ID is not found.
     */
    public static List<Contestant> getContestantsInDescendingOrder(int contestId) {
        for (Contest contest : contests) {
            if (contest.getId() == contestId) {
                List<Contestant> contestants = contest.getParticipants();

                // Sort contestants in descending order based on current contest points
                contestants.sort(Comparator.comparingInt(contest::calculateCurrentContestPoints).reversed());

                return contestants;
            }
        }
        return null; // Contest not found
    }

    /**
     * Retrieves a leaderboard of users in descending order of their scores.
     *
     * @return A list of User objects sorted by score in descending order.
     */
    public static List<User> getLeaderboard() {
        List<User> allUsers = new ArrayList<>();

        // Collect all participants from all contests
        for (Contest contest : contests) {
            allUsers.addAll(contest.getParticipants());
        }

        // Sort users by score in descending order
        allUsers.sort(Comparator.comparingInt(User::getScore).reversed());

        return allUsers;
    }

    public List<User> getLeaderboard(String order, Contest contest) {
        List<User> leaderboard = new ArrayList<>(contest.getParticipants());

        if (order.equalsIgnoreCase("ASC")) {
            leaderboard.sort(Comparator.comparingInt(User::getScore));
        } else if (order.equalsIgnoreCase("DESC")) {
            leaderboard.sort((user1, user2) -> Integer.compare(user2.getScore(), user1.getScore()));
        }

        return leaderboard;
    }
}
