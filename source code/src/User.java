// ============================================================
//  User.java
//  Represents a GXBank user (student / fresh graduate).
//  Stores profile, balance, income, spending categories,
//  savings goals, and a transaction history.
// ============================================================

import java.util.ArrayList;
import java.util.List;

public class User {

    // ---- Identity ----
    public String userId;
    public String name;
    public String email;
    public String passwordHash;   // SHA-256 hex (simulated)
    public String profileType;    // "student" | "fresh_graduate"

    // ---- Financial state ----
    public double balance       = 0.0;
    public double monthlyIncome = 0.0;
    public int    creditScore   = 650;   // 300-850 range
    public double totalDebt     = 0.0;

    // ---- Behaviour data ----
    public List<Transaction> transactions = new ArrayList<>();
    public List<SavingsGoal> goals        = new ArrayList<>();
    public List<BudgetRule>  budgetRules  = new ArrayList<>();

    // ---- Gamification ----
    public int    xpPoints   = 0;
    public int    streakDays = 0;
    public String badge      = "";   // e.g. "Saver Rookie", "Debt Slayer"

    // ---- Constructors ----
    public User() {}

    public User(String userId, String name, String email,
                String passwordHash, String profileType, double monthlyIncome) {
        this.userId        = userId;
        this.name          = name;
        this.email         = email;
        this.passwordHash  = passwordHash;
        this.profileType   = profileType;
        this.monthlyIncome = monthlyIncome;
    }

    // ---- Helpers ----

    /** Returns total expenses in the last 30 days (positive value). */
    public double getMonthlyExpenses() {
        long now        = System.currentTimeMillis() / 1000L;
        long thirtyDays = 30L * 86400L;
        double total    = 0.0;
        for (Transaction t : transactions) {
            if (t.amount < 0 && (now - t.timestamp) <= thirtyDays) {
                total += -t.amount;
            }
        }
        return total;
    }

    /** Returns spending in the given category over the last 30 days. */
    public double getCategorySpending(String cat) {
        long now        = System.currentTimeMillis() / 1000L;
        long thirtyDays = 30L * 86400L;
        double total    = 0.0;
        for (Transaction t : transactions) {
            if (cat.equals(t.category) && t.amount < 0
                    && (now - t.timestamp) <= thirtyDays) {
                total += -t.amount;
            }
        }
        return total;
    }

    /** Returns savings rate as a value between 0.0 and 1.0. */
    public double getSavingsRate() {
        if (monthlyIncome <= 0) return 0.0;
        double expenses = getMonthlyExpenses();
        double saved    = monthlyIncome - expenses;
        return Math.max(0.0, saved / monthlyIncome);
    }

    /** Returns true if the user has exceeded the budget for the given category. */
    public boolean isOverBudget(String cat) {
        for (BudgetRule rule : budgetRules) {
            if (rule.category.equals(cat)) {
                return getCategorySpending(cat) > rule.monthlyLimit;
            }
        }
        return false;
    }
    
 // ---- Gamification Methods ----

// Add XP when user saves money
public void addXP(int points) {
    xpPoints += points;
    System.out.println("+" + points + " XP earned!");

    checkBadge(); // update badge automatically
}

// Update streak (call daily)
public void updateStreak(boolean savedToday) {
    if (savedToday) {
        streakDays++;
        System.out.println("🔥 Streak: " + streakDays + " days");
        addXP(10); // reward streak
    } else {
        streakDays = 0;
        System.out.println("❌ Streak reset!");
    }
}

// Simple badge system
public void checkBadge() {
    if (xpPoints >= 500) {
        badge = "💎 Money Master";
    } else if (xpPoints >= 200) {
        badge = "🔥 Smart Saver";
    } else if (xpPoints >= 50) {
        badge = "🌱 Saver Rookie";
    }
}

// Show gamification status
public void showGamification() {
    System.out.println("\n=== GAMIFICATION STATUS ===");
    System.out.println("XP Points: " + xpPoints);
    System.out.println("Streak Days: " + streakDays);
    System.out.println("Badge: " + badge);
}
}