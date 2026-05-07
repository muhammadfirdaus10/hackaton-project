package UserManagement;

import java.util.ArrayList;
import java.util.Scanner;

public class LoginManager {

    static Scanner input = new Scanner(java.lang.System.in);

    static ArrayList<User> userList = new ArrayList<>();

    public static void signUp() {

        //input.nextLine();

        java.lang.System.out.println("\n========== SIGN UP ==========");

        java.lang.System.out.print("Create username: ");
        String username = input.nextLine();

        if (isUsernameTaken(username)) {
            java.lang.System.out.println("Username already exists. Please choose another username.");
            return;
        }

        java.lang.System.out.print("Create password: ");
        String password = input.nextLine();

        java.lang.System.out.print("Enter monthly income: RM ");
        double monthlyIncome = input.nextDouble();

        if (monthlyIncome < 20) {
            java.lang.System.out.println("Monthly income must be more than RM20.00");
            while(monthlyIncome <= 0){
                java.lang.System.out.println("Account is not created as the monthly income must be more than RM20.00");
                java.lang.System.out.print("Enter monthly income: RM ");
                monthlyIncome = input.nextDouble();
            }
            return;
        }

        User newUser = new User(username, password, monthlyIncome);

        userList.add(newUser);

        java.lang.System.out.println("\nAccount created successfully!");
        java.lang.System.out.println("You can now login using your username and password.");
    }

    public static User login() {

        input.nextLine();

        java.lang.System.out.println("\n========== LOGIN ==========");

        java.lang.System.out.print("Username: ");
        String username = input.nextLine();

        java.lang.System.out.print("Password: ");
        String password = input.nextLine();

        for (User user : userList) {

            if (user.username.equals(username) && user.password.equals(password)) {

                java.lang.System.out.println("\nLogin successful!");
                java.lang.System.out.println("Welcome back, " + user.username + "!");

                return user;
            }
        }

        java.lang.System.out.println("Invalid username or password.");
        java.lang.System.out.println("If you do not have an account, please sign up first.");

        return null;
    }

    public static boolean isUsernameTaken(String username) {

        for (User user : userList) {

            if (user.username.equals(username)) {
                return true;
            }
        }

        return false;
    }
}