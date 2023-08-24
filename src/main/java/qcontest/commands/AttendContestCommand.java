package qcontest.commands;

import qcontest.User;
import qcontest.Contest;
import qcontest.Question;
import qcontest.Contestant;

import java.util.List;

public class AttendContestCommand implements Command {
    private final int contestId;
    private final String contestantUsername;
    private final List<User> userList;
    private final List<Contest> contestList;

    public AttendContestCommand(int contestId, String contestantUsername, List<User> userList, List<Contest> contestList) {
        this.contestId = contestId;
        this.contestantUsername = contestantUsername;
        this.userList = userList;
        this.contestList = contestList;
    }

    @Override
    public void execute() {
        Contestant contestant = (Contestant) userList.stream()
                .filter(user -> user instanceof Contestant && user.getUsername().equals(contestantUsername))
                .findFirst()
                .orElse(null);

        Contest contest = contestList.stream()
                .filter(c -> c.getId() == contestId)
                .findFirst()
                .orElse(null);

        if (contestant != null && contest != null) {
            contestant.participateInContest(contest); // Attend the contest
            List<Question> attemptedQuestions = contest.getAttemptedQuestions(contestant);
            System.out.println("Contestant [" + contestant + ", contest=" + contest.getId() +
                    ", currentContestPoints=" + contest.calculateCurrentContestPoints(contestant) +
                    ", attemptedQuestions=" + attemptedQuestions + "]");
        } else {
            System.out.println("Contestant or Contest not found.");
        }
    }
}
