package SmartAI;

import UserManagement.User;

public class Dashboard {

    public static void showDashboard(User user) {

        java.lang.System.out.println("\n========== DASHBOARD ==========");

        java.lang.System.out.println("Hello, " + user.username);

        java.lang.System.out.printf("Balance: RM %.2f\n", user.balance);

        java.lang.System.out.printf("Savings: RM %.2f\n", user.savings);

        java.lang.System.out.printf("Monthly Spending: RM %.2f\n", user.totalSpent);

        java.lang.System.out.println("\nSpending Categories:");

        java.lang.System.out.printf("Food & Drink : RM %.2f\n", user.food);
        java.lang.System.out.printf("Transport    : RM %.2f\n", user.transport);
        java.lang.System.out.printf("Shopping     : RM %.2f\n", user.shopping);
        java.lang.System.out.printf("Entertainment: RM %.2f\n", user.entertainment);
        java.lang.System.out.printf("Others       : RM %.2f\n", user.others);

        double predicted = AIAnalyzer.predictSpending(user);

        java.lang.System.out.printf("\nPredicted Spending: RM %.2f\n", predicted);

        if(predicted > user.monthlyIncome) {

            java.lang.System.out.println("WARNING: You may overspend this month.");
        }
        else {

            java.lang.System.out.println("Financial status is healthy.");
        }
    }
}