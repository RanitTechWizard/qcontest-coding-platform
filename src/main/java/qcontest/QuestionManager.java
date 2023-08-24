package qcontest;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

/**
 * The QuestionManager class manages the questions available on the QContest platform.
 * It allows adding questions and listing questions based on difficulty level.
 */
public class QuestionManager {
    private static QuestionManager instance = null;
    private List<Question> questions = new ArrayList<>();

    // Private constructor to enforce singleton pattern
    private QuestionManager() {}

    /**
     * Adds a new question with the provided title, difficulty level, and score to the list of questions.
     *
     * @param title The title of the question.
     * @param level The difficulty level of the question (LOW, MEDIUM, HIGH).
     * @param score The score assigned to the question.
     */
    public void addQuestion(String title, DifficultyLevel level, int score) {
        questions.add(new Question(title, level, score));
    }

    /**
     * Gets the singleton instance of QuestionManager.
     *
     * @return The singleton instance of QuestionManager.
     */
    public static QuestionManager getInstance() {
        if (instance == null) {
            synchronized (QuestionManager.class) { // Use synchronized block for double-checked locking
                if (instance == null) {
                    instance = new QuestionManager();
                }
            }
        }
        return instance;
    }

    /**
     * Lists all available questions.
     *
     * @return A list of all questions.
     */
    public List<Question> listQuestions() {
        return new ArrayList<>(questions);
    }

    /**
     * Lists questions based on the specified difficulty level.
     *
     * @param level The difficulty level of questions to be listed.
     * @return A list of questions with the specified difficulty level.
     */
    public List<Question> listQuestions(DifficultyLevel level) {
        List<Question> filteredQuestions = new ArrayList<>();
        for (Question question : questions) {
            if (question.getLevel() == level) {
                filteredQuestions.add(question);
            }
        }
        return filteredQuestions;
    }

    /**
     * Retrieves a list of random questions from the database.
     *
     * @param numQuestions The number of random questions to retrieve.
     * @return A list of random questions.
     */
    public List<Question> getRandomQuestions(int numQuestions) {
        List<Question> selectedQuestions = new ArrayList<>(questions);
        if (numQuestions < selectedQuestions.size()) {
            Random random = new Random();
            for (int i = selectedQuestions.size() - 1; i >= numQuestions; i--) {
                selectedQuestions.remove(random.nextInt(i + 1));
            }
        }
        return selectedQuestions;
    }
}
