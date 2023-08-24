package qcontest.commands;

import qcontest.Creator;
import qcontest.QuestionManager;
import qcontest.DifficultyLevel;

public class CreateQuestionCommand implements Command {
    private String title;
    private DifficultyLevel level;
    private int score;
    private QuestionManager questionManager;
    private Creator creator; // Add a reference to the Creator

    public CreateQuestionCommand(String title, DifficultyLevel level, int score, QuestionManager questionManager, Creator creator) {
        this.title = title;
        this.level = level;
        this.score = score;
        this.questionManager = questionManager;
        this.creator = creator;
    }

    @Override
    public void execute() {
        // Ensure that the user executing the command is a Creator
        if (creator != null) {
            // Add the question using the Creator's method
            creator.addQuestion(title, level, score, questionManager);
        } else {
            System.out.println("Only a Creator can add questions.");
        }
    }
}
