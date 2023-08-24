package qcontest.commands;

import qcontest.User;
import qcontest.Contestant;
import qcontest.Creator;

import qcontest.commands.Command;

import java.util.List;

public class CreateUserCommand implements Command {
    private String username;
    private String role; // Add role as an instance variable
    private List<User> userList;

    public CreateUserCommand(String username, String role, List<User> userList) {
        this.username = username;
        this.role = role;
        this.userList = userList;
    }

    @Override
    public void execute() {
        User newUser;

        // Check the role to determine the type of user to create
        switch (role.toUpperCase()) {
            case "CREATOR":
                newUser = new Creator(username);
                break;
            case "CONTESTANT":
                newUser = new Contestant(username);
                break;
            default:
                newUser = new User(username);
        }

        userList.add(newUser);
        System.out.println(newUser); // Print the created user
    }
}
