package UserManagement;

import java.util.Scanner;

public class ExpenseManager {

    static Scanner input =
            new Scanner(java.lang.System.in);

    public static void addExpense(User user) {

        java.lang.System.out.println(
                "\n========== ADD SPENDING ==========");

        java.lang.System.out.println("1. Food & Drink");
        java.lang.System.out.println("2. Transport");
        java.lang.System.out.println("3. Shopping");
        java.lang.System.out.println("4. Entertainment");
        java.lang.System.out.println("5. Others");

        java.lang.System.out.print("Choose category: ");

        int category = input.nextInt();

        java.lang.System.out.print("Enter amount: RM ");

        double amount = input.nextDouble();

        if(amount > user.balance) {

            java.lang.System.out.println(
                    "Insufficient balance.");

            return;
        }

        user.balance -= amount;
        user.totalSpent += amount;

        switch(category) {

            case 1:
                user.food += amount;
                break;

            case 2:
                user.transport += amount;
                break;

            case 3:
                user.shopping += amount;
                break;

            case 4:
                user.entertainment += amount;
                break;

            case 5:
                user.others += amount;
                break;

            default:

                java.lang.System.out.println(
                        "Invalid category.");
        }

        java.lang.System.out.println(
                "Spending added successfully.");
    }

    public static void addIncome(User user) {

        java.lang.System.out.print(
                "\nEnter income amount: RM ");

        double income = input.nextDouble();

        user.balance += income;
        user.monthlyIncome += income;

        java.lang.System.out.println(
                "Income added successfully.");
    }
}