package com.solvd.it.compAbstract;

public abstract class TimeMetrics {
    private String deadlineDelivery;

    public TimeMetrics(String deadlineDelivery){
        this.deadlineDelivery =deadlineDelivery;
    }

    public String getDeadlineDelivery() {
        return deadlineDelivery;
    }

    public void setDeadlineDelivery(String deadlineDelivery) {
        this.deadlineDelivery = deadlineDelivery;
    }
}
