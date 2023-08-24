package qcontest.commands;

import qcontest.Question;
import qcontest.QuestionManager;
import qcontest.DifficultyLevel;

public class CreateQuestionCommand implements Command {
    private String title;
    private DifficultyLevel level;
    private int score;
    private QuestionManager questionManager;

    public CreateQuestionCommand(String title, DifficultyLevel level, int score, QuestionManager questionManager) {
        this.title = title;
        this.level = level;
        this.score = score;
        this.questionManager = questionManager;
    }

    @Override
    public void execute() {
        questionManager.addQuestion(title, level, score);
        Question addedQuestion = questionManager.listQuestions().get(questionManager.listQuestions().size() - 1);
        System.out.println(addedQuestion);
    }
}
