package qcontest;

import qcontest.commands.*;

import java.util.List;
import java.util.ArrayList;

public class QContestApp {
    public static void main(String[] args) {
        List<String> inputCommands = new ArrayList<>();
        inputCommands.add("CREATE_QUESTION Question1 LOW 10");
        inputCommands.add("CREATE_QUESTION Question2 MEDIUM 20");
        inputCommands.add("CREATE_QUESTION Question3 HIGH 30");
        inputCommands.add("CREATE_QUESTION Question4 LOW 15");
        inputCommands.add("CREATE_QUESTION Question5 MEDIUM 25");
        inputCommands.add("CREATE_QUESTION Question6 HIGH 35");
        inputCommands.add("CREATE_USER Ross");
        inputCommands.add("CREATE_USER Jacob");
        inputCommands.add("CREATE_USER Ranit CREATOR");
        inputCommands.add("CREATE_USER Jina CREATOR");
        inputCommands.add("CREATE_USER Rahul CONTESTANT");
        inputCommands.add("CREATE_USER Dhruv CONTESTANT");
        inputCommands.add("LIST_QUESTION");
        inputCommands.add("LIST_QUESTION MEDIUM");
        inputCommands.add("CREATE_CONTEST Contest1 LOW Ranit 5");
        inputCommands.add("CREATE_CONTEST Contest2 HIGH Jina 6");
        inputCommands.add("LIST_CONTEST");
        inputCommands.add("LIST_CONTEST MEDIUM");
        inputCommands.add("ATTEND_CONTEST 1 Rahul");
        inputCommands.add("ATTEND_CONTEST 1 Dhruv");
        inputCommands.add("SOLVE_QUESTION 1 1 Rahul");
        inputCommands.add("SOLVE_QUESTION 1 2 Rahul");
        inputCommands.add("RUN_CONTEST 1 Ranit");
        inputCommands.add("CONTEST_HISTORY 1");
        inputCommands.add("LEADERBOARD 1 DESC");

        QContestPlatform platform = new QContestPlatform();
        QuestionManager questionManager = new QuestionManager();
        List<User> userList = new ArrayList<>();
        List<Contest> contestList = new ArrayList<>();

        for (String inputCommand : inputCommands) {
            String[] parts = inputCommand.split(" ");
            String commandType = parts[0];

            switch (commandType) {
                case "CREATE_QUESTION" -> {
                    // Parse the input and create a CreateQuestionCommand
                    String title = parts[1];
                    DifficultyLevel level = DifficultyLevel.valueOf(parts[2]);
                    int score = Integer.parseInt(parts[3]);
                    Command createQuestionCommand = new CreateQuestionCommand(title, level, score, questionManager);
                    platform.setCommand(createQuestionCommand);
                    platform.executeCommand();
                }
                case "CREATE_USER" -> {
                    if (parts.length < 2) {
                        System.out.println("Invalid command format. Usage: CREATE_USER <username> [role]");
                        break;
                    }

                    String username = parts[1];
                    String role = (parts.length > 2) ? parts[2] : "REGULAR";

                    Command createUserCommand = new CreateUserCommand(username, role, userList);
                    platform.setCommand(createUserCommand);
                    platform.executeCommand();
                    break;
                }
                case "LIST_QUESTION" -> {
                    // Parse the input and create a ListQuestionsCommand
                    DifficultyLevel filterLevel = null;
                    if (parts.length > 1) {
                        filterLevel = DifficultyLevel.valueOf(parts[1]);
                    }
                    Command listQuestionsCommand = new ListQuestionsCommand(questionManager, filterLevel);
                    platform.setCommand(listQuestionsCommand);
                    platform.executeCommand();
                }
                case "CREATE_CONTEST" -> {
                    String contestTitle = parts[1];
                    DifficultyLevel contestLevel = DifficultyLevel.valueOf(parts[2]);
                    String creatorUsername = parts[3];
                    int numQuestions = Integer.parseInt(parts[4]);

                    Creator contestCreator = (Creator) userList.stream()
                            .filter(user -> user instanceof Creator && user.getUsername().equalsIgnoreCase(creatorUsername))
                            .findFirst()
                            .orElse(null);

                    if (contestCreator != null) {
                        Command createContestCommand = new CreateContestCommand(contestTitle, contestLevel, contestCreator, numQuestions, questionManager, contestList);
                        platform.setCommand(createContestCommand);
                        platform.executeCommand();
                    } else {
                        System.out.println("Creator not found: " + creatorUsername);
                    }
                }
                case "LIST_CONTEST" -> {
                    if (parts.length == 1) {
                        List<Contest> allContests = ContestManager.listAllContests();
                        System.out.println("\nList of All Contests:");
                        System.out.println(allContests);
                    } else if (parts.length == 2) {
                        String difficulty = parts[1];
                        DifficultyLevel contestLevel = DifficultyLevel.valueOf(difficulty);
                        List<Contest> filteredContests = ContestManager.listContestsByDifficulty(contestLevel);
                        System.out.println("\nList of Contests with Difficulty " + contestLevel + ":");
                        System.out.println(filteredContests);
                    }
                }
                case "ATTEND_CONTEST" -> {
                    int contestId = Integer.parseInt(parts[1]);
                    String contestantUsername = parts[2];

                    User contestant = userList.stream()
                            .filter(user -> user.getUsername().equalsIgnoreCase(contestantUsername))
                            .findFirst()
                            .orElse(null);

                    Contest contest = contestList.stream()
                            .filter(c -> c.getId() == contestId)
                            .findFirst()
                            .orElse(null);

                    if (contestant != null && contest != null) {
                        if (contestant instanceof Contestant) {
                            Contestant contestantUser = (Contestant) contestant;

                            Command attendContestCommand = new AttendContestCommand(contestId, contestantUsername, userList, contestList);
                            platform.setCommand(attendContestCommand);
                            platform.executeCommand();
                        } else {
                            System.out.println("User is not a contestant: " + contestantUsername);
                        }
                    } else {
                        System.out.println("Contestant or contest not found.");
                    }
                }
                case "WITHDRAW" -> {
                    int contestId = Integer.parseInt(parts[2]);
                    String contestantUsername = parts[3];

                    Command withdrawCommand = new WithdrawContestCommand(contestId, contestantUsername, userList, contestList);
                    platform.setCommand(withdrawCommand);
                    platform.executeCommand();
                }
                case "RUN_CONTEST" -> {
                    int contestId = Integer.parseInt(parts[1]);
                    String creatorUsername = parts[2];

                    Command command = new RunContestCommand(contestId, creatorUsername, userList, contestList, questionManager);
                    platform.setCommand(command);
                    platform.executeCommand();
                    break;
                }
                case "SOLVE_QUESTION" -> {
                    int contestId = Integer.parseInt(parts[1]);
                    int questionId = Integer.parseInt(parts[2]);
                    String contestantUsername = parts[3];

                    User contestant = userList.stream()
                            .filter(user -> user.getUsername().equalsIgnoreCase(contestantUsername))
                            .findFirst()
                            .orElse(null);

                    Contest contest = contestList.stream()
                            .filter(c -> c.getId() == contestId)
                            .findFirst()
                            .orElse(null);

                    if (contestant != null && contest != null) {
                        if (contestant instanceof Contestant) {
                            Contestant contestantUser = (Contestant) contestant;
                            Command solveQuestionCommand = new SolveQuestionCommand(contest, contestantUser, questionId);
                            platform.setCommand(solveQuestionCommand);
                            platform.executeCommand();
                        } else {
                            System.out.println("User is not a contestant: " + contestantUsername);
                        }
                    } else {
                        System.out.println("Contestant or contest not found.");
                    }
                    break;
                }
                case "CONTEST_HISTORY" -> {
                    int historyContestId = Integer.parseInt(parts[1]);

                    Command contestHistoryCommand = new ContestHistoryCommand(historyContestId, userList, contestList);
                    platform.setCommand(contestHistoryCommand);
                    platform.executeCommand();
                }
                case "LEADERBOARD" -> {
                    int contestId = Integer.parseInt(parts[1]);
                    String order = parts[2];

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

                default -> System.out.println("Unknown command.");
            }
        }
    }
}
