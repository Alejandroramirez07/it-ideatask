package com.solvd.it.company;

public class BudgetService {

    private final CostEstimator estimator;

    public BudgetService(CostEstimator estimator) {
        this.estimator = estimator;
    }

    public boolean isBudgetFeasible(Team team, ProjectProcess process, int hoursPerDay, float maxBudget) {
        try {
            float estimatedCost = estimator.estimateCost(team, process, hoursPerDay);
            return estimatedCost <= maxBudget;
        } catch (Exception e) {
            return false;
        }
    }
}
