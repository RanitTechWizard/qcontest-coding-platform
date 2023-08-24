package qcontest.commands;

import qcontest.Contest;
import qcontest.ContestManager;
import qcontest.DifficultyLevel;

import java.util.List;

public class ListContestCommand implements Command {
    private List<Contest> contestList;
    private DifficultyLevel filterLevel;

    public ListContestCommand(List<Contest> contestList) {
        this.contestList = contestList;
        this.filterLevel = null;
    }

    public ListContestCommand(List<Contest> contestList, DifficultyLevel filterLevel) {
        this.contestList = contestList;
        this.filterLevel = filterLevel;
    }

    @Override
    public void execute() {
        List<Contest> contests;

        if (filterLevel == null) {
            contests = ContestManager.listAllContests();
        } else {
            contests = ContestManager.listContestsByDifficulty(filterLevel);
        }

        System.out.println(contests);
    }
}

