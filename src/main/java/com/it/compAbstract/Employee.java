package com.it.compAbstract;

public abstract class Employee {
    public int holidaysScheduled;

    public Employee(int holidaysScheduled) {
        this.holidaysScheduled =holidaysScheduled;
    }

    public int getHolidaysScheduled() {
        return holidaysScheduled;
    }

    public void setHolidaysScheduled(int holidaysScheduled){
        this.holidaysScheduled=holidaysScheduled;
    }

    public abstract String getRole();

}
