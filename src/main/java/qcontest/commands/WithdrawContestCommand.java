package qcontest.commands;

import qcontest.Contest;
import qcontest.Contestant;
import qcontest.User;

import java.util.List;
public class WithdrawContestCommand implements Command {

    private final int contestId;
    private String username;
    private List<User> userList;
    private List<Contest> contestList;

    public WithdrawContestCommand(int contestId, String contestantUsername, List<User> userList, List<Contest> contestList) {
        this.contestId = contestId;
        this.username = contestantUsername;
        this.userList = userList;
        this.contestList = contestList;
    }

    @Override
    public void execute() {
        // Find the Contestant and Contest
        Contestant contestant = (Contestant) userList.stream()
                .filter(user -> user instanceof Contestant && user.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);

        Contest contest = contestList.stream()
                .filter(c -> c.getId() == contestId)
                .findFirst()
                .orElse(null);

        if (contestant != null && contest != null) {
            boolean removed = contest.withdrawContestant(contestant);
            if (removed) {
                System.out.println("Contestant with name " + username + " for " + contest.getTitle() + " deleted");
            } else {
                System.out.println("Contestant not found in the contest");
            }
        } else {
            System.out.println("Contestant or contest not found");
        }
    }
}


