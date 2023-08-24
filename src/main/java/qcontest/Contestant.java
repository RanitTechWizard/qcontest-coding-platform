package qcontest;

import java.util.ArrayList;
import java.util.List;

public class Contestant extends User {
    public Contestant(String username) {
        super(username);
    }

    /**
     * Allows a contestant to participate in a contest.
     *
     * @param contest The contest in which the contestant wants to participate.
     */
    public void participateInContest(Contest contest) {
        contest.registerUser(this);
    }

    /**
     * Withdraws a user from the contest if conditions are met.
     *
     * @param contest The contest from which user has to be withdrawn.
     * @return True if the withdrawal is successful, false otherwise.
     */
    public boolean withdrawFromContest(Contest contest) {

        return contest.withdrawContestant(this);
    }

    /**
     * Allows a contestant to solve a question.
     *
     * @param contest The contest in which the question is being solved.
     * @param question The question the contestant is solving.
     */
    public void solveQuestion(Contest contest, Question question) {

        if (getScore() >= question.getScore()) {
            contest.markQuestionAsSolved(question.getId(), this);
            System.out.println(getUsername() + " solved " + question.getTitle() +
                    " and earned " + question.getScore() + " points.");
        } else {
            System.out.println(getUsername() + " does not have enough points to solve " + question.getTitle());
        }
    }




    @Override
    public String toString() {
        return "Contestant[id=" + getId() + ", username=" + getUsername() + ", score=" + getScore() + "]";
    }
}
