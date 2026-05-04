// ============================================================
//  SavingsGoal.java
//  Represents a user's savings goal (e.g. "Emergency Fund").
// ============================================================

public class SavingsGoal {
    public String  name;
    public double  targetAmount;
    public double  savedAmount;
    public long    deadline;    // Unix epoch; 0 = no deadline
    public boolean completed;

    public SavingsGoal() {}

    public SavingsGoal(String name, double targetAmount, double savedAmount,
                       long deadline, boolean completed) {
        this.name         = name;
        this.targetAmount = targetAmount;
        this.savedAmount  = savedAmount;
        this.deadline     = deadline;
        this.completed    = completed;
    }
}