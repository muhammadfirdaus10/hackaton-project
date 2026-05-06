import java.util.Scanner;

import SmartAI.*;
import UserManagement.*;

public class App {

    public static void main(String[] args) {

        Scanner input = new Scanner(java.lang.System.in);

        User currentUser = null;

        while (currentUser == null) {

            java.lang.System.out.println("\n=================================");
            java.lang.System.out.println("         SmartHabit AI");
            java.lang.System.out.println("=================================");
            java.lang.System.out.println("1. Sign Up");
            java.lang.System.out.println("2. Login");
            java.lang.System.out.println("3. Exit");
            java.lang.System.out.print("Choose: ");

            int option = input.nextInt();

            switch (option) {

                case 1:
                    LoginManager.signUp();
                    break;

                case 2:
                    currentUser = LoginManager.login();
                    break;

                case 3:
                    java.lang.System.out.println("Thank you for using SmartHabit AI!");
                    input.close();
                    return;

                default:
                    java.lang.System.out.println("Invalid choice.");
            }
        }

        int choice;

        do {

            java.lang.System.out.println("\n=================================");
            java.lang.System.out.println("         SmartHabit AI");
            java.lang.System.out.println("=================================");
            java.lang.System.out.println("Welcome, " + currentUser.username);
            java.lang.System.out.println("1. Dashboard");
            java.lang.System.out.println("2. AI Insights");
            java.lang.System.out.println("3. Auto Save");
            java.lang.System.out.println("4. Gamification");
            java.lang.System.out.println("5. Piggy Bank");
            java.lang.System.out.println("6. Add Spending");
            java.lang.System.out.println("7. Add Income");
            java.lang.System.out.println("8. Logout");
            java.lang.System.out.println("9. Exit");

            java.lang.System.out.print("Choose: ");
            choice = input.nextInt();

            switch (choice) {

                case 1:
                    Dashboard.showDashboard(currentUser);
                    break;

                case 2:
                    AIAnalyzer.showInsights(currentUser);
                    break;

                case 3:
                    AutoSave.showAutoSave(currentUser);
                    break;

                case 4:
                    Gamification.showGamification(currentUser);
                    break;

                case 5:
                    PiggyBank.showPiggyBank(currentUser);
                    break;

                case 6:
                    ExpenseManager.addExpense(currentUser);
                    break;

                case 7:
                    ExpenseManager.addIncome(currentUser);
                    break;

                case 8:
                    java.lang.System.out.println("Logged out successfully.");
                    currentUser = null;
                    main(args);
                    return;

                case 9:
                    java.lang.System.out.println("Thank you for using SmartHabit AI!");
                    break;

                default:
                    java.lang.System.out.println("Invalid choice.");
            }

        } while (choice != 9);

        input.close();
    }
}