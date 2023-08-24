package qcontest;

import java.util.List;

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

    /**
     * Runs a contest created by the creator.
     *
     * @param contest The contest to be run.
     * @return A list of updated contestants after running the contest.
     */
    public List<User> runContest(Contest contest) {
        if (contest.getCreator() == this) {
            List<User> updatedContestants = contest.runContest(this);
            System.out.println(contest.getTitle() + " run by creator: " + getUsername());
            return updatedContestants;
        } else {
            System.out.println("You are not the creator of this contest.");
            return null;
        }
    }

    @Override
    public String toString() {
        return "Creator[id=" + getId() + ", username=" + getUsername() + ", score=" + getScore() + "]";
    }
}
