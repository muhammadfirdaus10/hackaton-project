package UserManagement;

public class User {

    public String name;
    public double balance;
    public double income;
    public double totalSpent;
    public double savings;

    public double food;
    public double transport;
    public double shopping;
    public double entertainment;
    public double others;

    public int points;
    public int streak;
    public int level;

    public User(String name, double balance) {

        this.name = name;
        this.balance = balance;

        income = 3000;
        totalSpent = 980.50;
        savings = 320.50;

        food = 330;
        transport = 220;
        shopping = 180;
        entertainment = 150;
        others = 100;

        points = 1245;
        streak = 12;
        level = 12;
    }
}