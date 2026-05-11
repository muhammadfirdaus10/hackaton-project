import UserManagement.*;
import SmartAI.*;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        User user = new User("Aiman", 2580.00);

        int choice;

        do {
            System.out.println("\n===== SmartHabit AI =====");
            System.out.println("1. Dashboard");
            System.out.println("2. AI Insights");
            System.out.println("3. Auto Save");
            System.out.println("4. Gamification");
            System.out.println("5. Piggy Bank");
            System.out.println("6. Add Spending");
            System.out.println("7. Add Income");
            System.out.println("8. Exit");

            System.out.print("Choose: ");
            choice = input.nextInt();

            switch(choice) {

                case 1:
                    Dashboard.showDashboard(user);
                    break;

                case 2:
                    AIAnalyzer.showInsights(user);
                    break;

                case 3:
                    AutoSave.showAutoSave(user);
                    break;

                case 4:
                    Gamification.showGamification(user);
                    break;

                case 5:
                    PiggyBank.showPiggyBank(user);
                    break;

                case 6:
                    ExpenseManager.addExpense(user);
                    break;

                case 7:
                    ExpenseManager.addIncome(user);
                    break;

                case 8:
                    System.out.println("Thank you for using SmartHabit AI!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while(choice != 8);
        input.close();
    }
}