package edu.cit.agramon.vicci.campusequimentloan.loan.Penalty;

public class LatePenaltyStrategy implements penalty {

    // Define a penalty rate per day (₱50/day)
    private static final double DAILY_PENALTY_RATE = 50.0;

    @Override
    public double calculatePenalty(long overdueDays) {
        if (overdueDays > 0) {
            return overdueDays * DAILY_PENALTY_RATE;
        }
        return 0.0; // No penalty if the loan was returned on time
    }
}