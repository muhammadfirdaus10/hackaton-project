package UserManagement;

public class PiggyGoal {

    public String goalName;
    public double targetAmount;
    public double savedAmount;

    public PiggyGoal(String goalName, double targetAmount) {
        this.goalName = goalName;
        this.targetAmount = targetAmount;
        this.savedAmount = 0.00;
    }

    public double getProgress() {
        if (targetAmount == 0) {
            return 0;
        }

        return (savedAmount / targetAmount) * 100;
    }

    public boolean isCompleted() {
        return savedAmount >= targetAmount;
    }
}