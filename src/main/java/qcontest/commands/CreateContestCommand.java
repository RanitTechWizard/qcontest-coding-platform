package qcontest.commands;

import qcontest.User;
import qcontest.Contest;
import qcontest.ContestManager;
import qcontest.DifficultyLevel;
import qcontest.QuestionManager;


import java.util.List;

public class CreateContestCommand implements Command {
    private String contestTitle;
    private DifficultyLevel contestLevel;
    private User contestCreator;
    private int numQuestions;
    private QuestionManager questionManager;
    private List<Contest> contestList;

    public CreateContestCommand(String contestTitle, DifficultyLevel contestLevel, User contestCreator, int numQuestions, QuestionManager questionManager, List<Contest> contestList) {
        this.contestTitle = contestTitle;
        this.contestLevel = contestLevel;
        this.contestCreator = contestCreator;
        this.numQuestions = numQuestions;
        this.questionManager = questionManager;
        this.contestList = contestList;
    }

    @Override
    public void execute() {
        Contest newContest = ContestManager.addContest(contestTitle, contestLevel, contestCreator, numQuestions, questionManager);
        contestList.add(newContest);
        System.out.println("Created Contest: " + newContest);
    }
}
