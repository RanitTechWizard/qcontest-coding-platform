package qcontest;

import java.util.Map;
import java.util.HashMap;

/**
 * The Question class represents a coding question that can be included in contests on the QContest platform.
 * Each question has a unique ID, a title, a difficulty level, and a score associated with it.
 */
public class Question {
    private static int nextQuestionId = 1;

    private int id;                // Unique question ID
    private String title;          // Title of the question
    private DifficultyLevel level; // Difficulty level of the question
    private int score;             // Score assigned to the question

    /**
     * Constructs a new Question with the provided title, difficulty level, and score.
     *
     * @param title The title of the question.
     * @param level The difficulty level of the question (LOW, MEDIUM, HIGH).
     * @param score The score assigned to the question.
     */
    public Question(String title, DifficultyLevel level, int score) {
        this.id = nextQuestionId++;
        this.title = title;
        this.level = level;
        this.score = score;
    }

    /**
     * Retrieves the question's unique ID.
     *
     * @return The unique ID of the question.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the question's title.
     *
     * @return The title of the question.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the question's difficulty level.
     *
     * @return The difficulty level of the question (LOW, MEDIUM, HIGH).
     */
    public DifficultyLevel getLevel() {
        return level;
    }

    /**
     * Retrieves the score assigned to the question.
     *
     * @return The score of the question.
     */
    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Question[id=" + id + "]";
    }
}
