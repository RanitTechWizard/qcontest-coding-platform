package qcontest.commands;

import qcontest.Contest;
import qcontest.Question;
import qcontest.Contestant;

public class SolveQuestionCommand implements Command {
    private final Contest contest;
    private final Contestant contestant;
    private final int questionId;

    public SolveQuestionCommand(Contest contest, Contestant contestant, int questionId) {
        this.contest = contest;
        this.contestant = contestant;
        this.questionId = questionId;
    }

    @Override
    public void execute() {
        Question question = contest.getQuestionById(questionId);
        if (question != null) {
            contestant.solveQuestion(contest, question);
        } else {
            System.out.println("Question not found: " + questionId);
        }
    }
}


