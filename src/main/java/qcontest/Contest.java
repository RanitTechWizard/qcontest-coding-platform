package qcontest;

import java.util.*;

/**
 * The Contest class represents a coding contest on the QContest platform.
 * Each contest has a unique ID, a title, a difficulty level, a creator, and a list of questions.
 */
public class Contest {
    private static int nextContestId = 1; // Start contest IDs from 1

    private int id;                // Unique contest ID
    private String title;          // Title of the contest
    private DifficultyLevel level; // Difficulty level of the contest
    private User creator;        // Creator of the contest
    private List<Question> questions; // List of questions for the contest
    private List<Contestant> participants = new ArrayList<>(); // List of contestants

    private Map<Integer, User> questionSolvedByMap = new HashMap<>(); // Map to track which question IDs were solved by which users

    /**
     * Constructs a new Contest with the provided title, difficulty level, creator, number of questions, and question database.
     * Random questions are selected from the question database and assigned to the contest.
     *
     * @param title The title of the contest.
     * @param level The difficulty level of the contest (LOW, MEDIUM, HIGH).
     * @param creator The creator of the contest.
     * @param numQuestions The number of questions for the contest.
     * @param questionManager The question manager from whose DB we can get random questions.
     */
    public Contest(String title,
                   DifficultyLevel level,
                   User creator, int numQuestions,
                   QuestionManager questionManager) {
        this.id = nextContestId++;
        this.title = title;
        this.level = level;
        this.creator = creator;
        this.questions = questionManager.getRandomQuestions(numQuestions);
    }

    /**
     * Retrieves the unique ID of the contest.
     *
     * @return The contest ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the title of the contest.
     *
     * @return The contest title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the difficulty level of the contest.
     *
     * @return The contest difficulty level.
     */
    public DifficultyLevel getLevel() {
        return level;
    }

    /**
     * Retrieves the creator of the contest.
     *
     * @return The contest creator.
     */
    public User getCreator() {
        return creator;
    }

    /**
     * Retrieves the list of questions assigned to the contest.
     *
     * @return The list of questions.
     */
    public List<Question> getQuestions() {
        return new ArrayList<>(questions);
    }

    @Override
    public String toString() {
        return "Contest[id=" + id + ", title=" + title + ", level=" + level +
                ", creator=" + creator + ", questions=" + questions + "]";
    }

    /**
     * Registers a user as a participant in the contest.
     *
     * @param contestant The user to be registered as a contestant.
     */
    public void registerUser(Contestant contestant) {
        participants.add(contestant);
    }

    /**
     * Retrieves the list of participants who have registered for the contest.
     *
     * @return A list of participants registered for the contest.
     */
    public List<Contestant> getParticipants() {
        return new ArrayList<>(participants);
    }

    public Map<Integer, User> getQuestionSolvedByMap() {
        return questionSolvedByMap;
    }

    /**
     * Removes a contestant from the list of participants in the contest.
     *
     * @param contestant The contestant to be withdrawn from the contest.
     * @return True if the contestant was successfully withdrawn, false if the contestant was not a participant or is the creator.
     */
    public boolean withdrawContestant(Contestant contestant) {
        if (participants.contains(contestant) && !creator.equals(contestant)) {
            participants.remove(contestant);
            return true;
        }
        return false;
    }

    /**
     * Runs the contest based on the user's role.
     *
     * @param user The user (either Contestant or Creator) initiating the contest.
     * @return A list of updated contestants in descending order of scores.
     */
    public List<User> runContest(User user) {
        if (user instanceof Creator) {
            Creator creator = (Creator) user;
            // Only creators can start the contest
            if (!creator.equals(getCreator())) {
                System.out.println("Only the contest creator can start the contest.");
                return null;
            }

            List<User> updatedContestants = new ArrayList<>();
            int totalContestPoints = 0;

            for (Question question : questions) {
                totalContestPoints += question.getScore(); // Sum of scores of all questions
            }

            for (User participant : participants) {
                int currentContestPoints = calculateCurrentContestPoints(participant);
                int newScore;

                switch (level) {
                    case LOW:
                        newScore = participant.getScore() + (currentContestPoints - 50);
                        break;
                    case MEDIUM:
                        newScore = participant.getScore() + (currentContestPoints - 30);
                        break;
                    case HIGH:
                    default:
                        newScore = participant.getScore() + currentContestPoints;
                }

                participant.setScore(newScore); // Update the user's score
                updatedContestants.add(participant);
            }

            // Sort the contestants by their updated scores in descending order
            updatedContestants.sort(Comparator.comparingInt(User::getScore).reversed());

            return updatedContestants;
        } else if (user instanceof Contestant) {
            Contestant contestant = (Contestant) user;
            // Contestants can participate in the contest
            System.out.println(contestant.getUsername() + " is participating in the contest.");
            registerUser(contestant);
            return null; // Contestants cannot start contests
        }

        return null;
    }

    /**
     * Marks a specific question as solved by a user.
     *
     * @param questionId The questionId to mark as solved.
     * @param user The user who solved the question.
     */
    public void markQuestionAsSolved(Integer questionId, User user) {
        questionSolvedByMap.put(questionId, user);
    }

    /**
     * Checks if a specific question was solved by a specific user.
     *
     * @param questionId The ID of the question to check.
     * @param user The user to check for solving the question.
     * @return True if the question was solved by the user, false otherwise.
     */
    public boolean isQuestionSolvedBy(Integer questionId, User user) {

        return questionSolvedByMap.containsKey(questionId) &&
                questionSolvedByMap.get(questionId) == user;
    }

    /**
     * Calculates the current contest points for a participant.
     * Contest points are the sum of scores of questions solved by the participant.
     *
     * @param participant The participant for whom to calculate contest points.
     * @return The current contest points of the participant.
     */
    public int calculateCurrentContestPoints(User participant) {
        int contestPoints = 0;
        for (Question question : questions) {
            if (isQuestionSolvedBy(question.getId(), participant)) {
                contestPoints += question.getScore();
            }
        }
        return contestPoints;
    }

    /**
     * Retrieves the list of attempted questions by a specific contestant in the contest.
     *
     * @param contestant The contestant for whom to retrieve attempted questions.
     * @return A list of attempted questions by the contestant.
     */
    public List<Question> getAttemptedQuestions(Contestant contestant) {
        List<Question> attemptedQuestions = new ArrayList<>();

        for (Map.Entry<Integer, User> entry : questionSolvedByMap.entrySet()) {
            if (entry.getValue() == contestant) {
                int questionId = entry.getKey();
                Question attemptedQuestion = questions.stream()
                        .filter(question -> question.getId() == questionId)
                        .findFirst()
                        .orElse(null);

                if (attemptedQuestion != null) {
                    attemptedQuestions.add(attemptedQuestion);
                }
            }
        }

        return attemptedQuestions;
    }


    /**
     * Retrieves a question in the contest by its ID.
     *
     * @param questionId The ID of the question to retrieve.
     * @return The question with the specified ID, or null if not found.
     */
    public Question getQuestionById(int questionId) {
        return questions.stream()
                .filter(question -> question.getId() == questionId)
                .findFirst()
                .orElse(null);
    }

    public List<User> getLeaderboardForContest(String order) {
        List<User> leaderboard = new ArrayList<>(participants);

        if (order.equalsIgnoreCase("ASC")) {
            leaderboard.sort(Comparator.comparingInt(user -> calculateCurrentContestPoints(user)));
        } else if (order.equalsIgnoreCase("DESC")) {
            leaderboard.sort((user1, user2) -> Integer.compare(
                    calculateCurrentContestPoints(user2),
                    calculateCurrentContestPoints(user1)
            ));
        }

        return leaderboard;
    }
}

