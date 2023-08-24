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

    /**
     * Adds a new question with the provided title, difficulty level, and score.
     *
     * @param title The title of the question.
     * @param level The difficulty level of the question.
     * @param score The score assigned to the question.
     */
    public void addQuestion(String title, DifficultyLevel level, int score, QuestionManager questionManager) {
        questionManager.addQuestion(title, level, score);
        System.out.println("Question added by Creator: " + title);
    }



    @Override
    public String toString() {
        return "Creator[id=" + getId() + ", username=" + getUsername() + ", score=" + getScore() + "]";
    }
}
