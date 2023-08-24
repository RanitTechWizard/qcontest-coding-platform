package qcontest.commands;

import qcontest.*;

import java.util.List;

public class RunContestCommand implements Command {
    private final int contestId;
    private final String creatorUsername;
    private final List<User> userList;
    private final List<Contest> contestList;
    private final QuestionManager questionManager;

    public RunContestCommand(int contestId, String creatorUsername, List<User> userList, List<Contest> contestList, QuestionManager questionManager) {
        this.contestId = contestId;
        this.creatorUsername = creatorUsername;
        this.userList = userList;
        this.contestList = contestList;
        this.questionManager = questionManager;
    }

    @Override
    public void execute() {
        Creator creator = (Creator) userList.stream()
                .filter(user -> user instanceof Creator && user.getUsername().equals(creatorUsername))
                .findFirst()
                .orElse(null);

        Contest contest = contestList.stream()
                .filter(c -> c.getId() == contestId)
                .findFirst()
                .orElse(null);

        if (creator != null && contest != null) {
            if (contest.getCreator() != creator) {
                System.out.println("Only the contest creator can run the contest.");
            } else {
                List<User> updatedContestants = contest.runContest(creator);

                if (updatedContestants != null) {
                    for (User user : updatedContestants) {
                        if (user instanceof Contestant) {
                            Contestant contestant = (Contestant) user;
                            List<Question> attemptedQuestions = contest.getAttemptedQuestions(contestant);
                            System.out.println("Contestant [" + contestant + ", contest=" + contest.getId() +
                                    ", currentContestPoints=" + contest.calculateCurrentContestPoints(contestant) +
                                    ", attemptedQuestions=" + attemptedQuestions + "]");
                        }
                    }
                }
            }
        } else {
            System.out.println("Creator or Contest not found.");
        }
    }
}


