package qcontest.commands;

import qcontest.Question;
import qcontest.DifficultyLevel;
import qcontest.QuestionManager;

import java.util.List;

public class ListQuestionsCommand implements Command {
    private QuestionManager questionManager;
    private DifficultyLevel filterLevel;

    public ListQuestionsCommand(QuestionManager questionManager) {
        this.questionManager = questionManager;
        this.filterLevel = null;
    }

    public ListQuestionsCommand(QuestionManager questionManager, DifficultyLevel filterLevel) {
        this.questionManager = questionManager;
        this.filterLevel = filterLevel;
    }

    @Override
    public void execute() {
        List<Question> questions;

        if (filterLevel == null) {
            questions = questionManager.listQuestions();
        } else {
            questions = questionManager.listQuestions(filterLevel);
        }

        System.out.println(questions);
    }
}

