package qcontest;

public class Creator extends User {
    public Creator(String username) {
        super(username);
    }

    /**
     * Creates a new contest.
     *
     * @param title           The title of the contest.
     * @param level           The difficulty level of the contest.
     * @param numQuestions    The number of questions in the contest.
     * @param questionManager The question manager for selecting questions.
     */
    public Contest createContest(String title, DifficultyLevel level, int numQuestions, QuestionManager questionManager) {
        return ContestManager.addContest(title, level, this, numQuestions, questionManager);
    }

    @Override
    public String toString() {
        return "Creator[id=" + getId() + ", username=" + getUsername() + ", score=" + getScore() + "]";
    }
}
