package qcontest.commands;

import qcontest.User;
import qcontest.Contest;
import qcontest.Question;
import qcontest.Contestant;

import java.util.List;

public class ContestHistoryCommand implements Command {
    private final int contestId;
    private final List<User> userList;
    private final List<Contest> contestList;

    public ContestHistoryCommand(int contestId, List<User> userList, List<Contest> contestList) {
        this.contestId = contestId;
        this.userList = userList;
        this.contestList = contestList;
    }

    @Override
    public void execute() {
        Contest contest = contestList.stream()
                .filter(c -> c.getId() == contestId)
                .findFirst()
                .orElse(null);

        if (contest != null) {
            List<Contestant> contestants = contest.getParticipants();
            for (Contestant contestant : contestants) {
                List<Question> attemptedQuestions = contest.getAttemptedQuestions(contestant);
                System.out.println("Contestant [" + contestant + ", contest=" + contest.getId() +
                        ", currentContestPoints=" + contest.calculateCurrentContestPoints(contestant) +
                        ", attemptedQuestions = " + formatAttemptedQuestions(attemptedQuestions) + "]");
            }
        } else {
            System.out.println("Contest not found.");
        }
    }

    // Helper method to format attempted questions
    private String formatAttemptedQuestions(List<Question> questions) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < questions.size(); i++) {
            sb.append("Question[id=").append(questions.get(i).getId()).append("]");
            if (i < questions.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}

