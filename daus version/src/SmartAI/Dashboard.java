package SmartAI;
import UserManagement.User;

public class Dashboard {

    public static void showDashboard(User user) {

        System.out.println("\n===== DASHBOARD =====");

        System.out.println("Hello, " + user.name);

        System.out.printf("Balance: RM %.2f\n", user.balance);

        System.out.printf("Total Spending: RM %.2f\n", user.totalSpent);

        System.out.printf("Savings: RM %.2f\n", user.savings);

        System.out.println("\nSpending Categories:");

        System.out.printf("Food: RM %.2f\n", user.food);
        System.out.printf("Transport: RM %.2f\n", user.transport);
        System.out.printf("Shopping: RM %.2f\n", user.shopping);
        System.out.printf("Entertainment: RM %.2f\n", user.entertainment);
        System.out.printf("Others: RM %.2f\n", user.others);

        double predicted = AIAnalyzer.predictSpending(user);

        System.out.printf("\nPredicted Spending: RM %.2f\n", predicted);
    }
}