package com.solvd.it.compAbstract;

public abstract class Procedures {
    public int timeOffScheduled;

    public Procedures(int timeOffScheduled){
        this.timeOffScheduled = timeOffScheduled;
    }

    public int getTimeOffScheduled() {
        return timeOffScheduled;
    }

    public void setTimeOffScheduled(int timeOffScheduled) {
        this.timeOffScheduled = timeOffScheduled;
    }
}
