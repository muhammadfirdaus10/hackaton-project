package UserManagement;

import java.util.ArrayList;

public class User {

    public String username;
    public String password;

    public double balance;
    public double monthlyIncome;
    public double savings;
    public double totalSpent;

    public double food;
    public double transport;
    public double shopping;
    public double entertainment;
    public double others;

    public int points;

    public boolean autoSaveOn;
    public double autoSavePercentage;

    public int rewardsClaimed;

    public ArrayList<PiggyGoal> piggyGoals;

    public User(String username, String password, double monthlyIncome) {

        this.username = username;
        this.password = password;

        this.monthlyIncome = monthlyIncome;
        this.balance = monthlyIncome;

        this.savings = 0.00;
        this.totalSpent = 0.00;

        this.food = 0.00;
        this.transport = 0.00;
        this.shopping = 0.00;
        this.entertainment = 0.00;
        this.others = 0.00;

        this.points = 0;

        this.autoSaveOn = false;
        this.autoSavePercentage = 10.0;

        this.rewardsClaimed = 0;

        /*
         * Initial Piggy Bank target is null.
         * User has no goal at first.
         * User can create many goals later.
         */
        this.piggyGoals = new ArrayList<>();
    }

    public int getLevel() {
        return (points / 100) + 1;
    }

    public int getPointsToNextLevel() {
        return 100 - (points % 100);
    }

    public int getBadgeCount() {
        return getLevel();
    }

    public int getAvailableRewardCount() {
        return getBadgeCount() / 3;
    }
}