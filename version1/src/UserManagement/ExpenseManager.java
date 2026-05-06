package UserManagement;
import java.util.Scanner;

import SmartAI.AutoSave;

public class ExpenseManager {

    static Scanner input = new Scanner(System.in);

    public static void addExpense(User user) {

        System.out.print("Enter expense amount: RM ");

        double amount = input.nextDouble();

        user.balance -= amount;

        user.totalSpent += amount;

        user.food += amount;

        System.out.println("Expense added.");
    }

    public static void addIncome(User user) {

        System.out.print("Enter income amount: RM ");

        double amount = input.nextDouble();

        user.balance += amount;

        user.income += amount;

        System.out.println("Income added.");

        AutoSave.applyAutoSave(user);
    }
}