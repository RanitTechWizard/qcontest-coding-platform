package qcontest.commands;

import qcontest.User;
import qcontest.Contest;

import java.util.List;

public class LeaderboardCommand implements Command {
    private int contestId;
    private String order;
    private List<Contest> contestList;

    public LeaderboardCommand(int contestId, String order, List<Contest> contestList) {
        this.contestId = contestId;
        this.order = order;
        this.contestList = contestList;
    }

    @Override
    public void execute() {
        Contest contest = contestList.stream()
                .filter(c -> c.getId() == contestId)
                .findFirst()
                .orElse(null);

        if (contest != null) {
            List<User> leaderboard = contest.getLeaderboardForContest(order);
            System.out.println("Leaderboard for Contest " + contestId + ":");
            for (User user : leaderboard) {
                System.out.println(user);
            }
        } else {
            System.out.println("Contest not found.");
        }
    }
}

